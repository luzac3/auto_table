package wolfnetTwei.autoTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FileLoader {
    String line = "";
    List<String> clmnLst = new ArrayList<>();

    @SuppressWarnings("deprecation")
	Map<String,Map<String,Object>> fileLoad(String filePath, Map<String,Map<String,Object>> tblMap) throws Exception{
        // 宣言してNULLを設定
        ClmnProperty clmnProperty = null;

        try{
            File file = new File(filePath);
            FileInputStream input = new FileInputStream(file);

            // ドットの位置を後ろから検索
            int pos = filePath.lastIndexOf(".");

            // インデックス位置をドット直後に設定
            pos++;

            // 拡張子を取得
            String extention = filePath.substring(pos);

            if(extention.equals("csv")){
                InputStreamReader stream = new InputStreamReader(input,"SJIS");
                BufferedReader br = new BufferedReader(stream);
                while ((line = br.readLine()) != null){
                    // バイトで取得
                    byte[] b = line.getBytes();

                    // 取得した文字列をUTF-8に変換
                    line = new String(b, "UTF-8");

                    System.out.println(line);
                    clmnLst.add(line);
                }
                br.close();
            }else if(extention.equals("xls") || extention.equals("xlsx")){
                Workbook workbook = WorkbookFactory.create(input);

                // シート番号取得
                Sheet sheet = workbook.getSheetAt(0);

                // 全行を繰り返し処理
                Iterator<Row> rows = sheet.rowIterator();
                while(rows.hasNext()) {
                    System.out.println(rows.hasNext());
                	// rowオブジェクトを取得
                    Row row = rows.next();

                    // セルの値を保存するオブジェクトを生成
                    StringBuilder cellVal = new StringBuilder();

                    //全セルを繰り返し処理
                    Iterator<Cell> cells = row.cellIterator();
                    while(cells.hasNext()){
                        // cellオブジェクトを取得
                        Cell cell = cells.next();
                        try{
                            switch(cell.getCellType()) {
                              case Cell.CELL_TYPE_STRING:
                                cellVal.append(cell.getStringCellValue());
                                break;
                              case Cell.CELL_TYPE_NUMERIC:
                                if(DateUtil.isCellDateFormatted(cell)) {
                                    cellVal.append(cell.getDateCellValue());
                                } else {
                                    cellVal.append(cell.getNumericCellValue());
                                }
                                break;
                            }
                            cellVal.append(",");
                            System.out.println(cellVal);
                        }catch(IllegalStateException e){
                            throw e;
                        }
                    }
                    if(cellVal.length() > 0){
                        // 最後のカンマの削除
                        cellVal.setLength(cellVal.length() - 1);
                    }
                    System.out.println(cellVal);

                    clmnLst.add(cellVal.toString());

                }
            }else{
            	input.close();
                throw new RuntimeException("ファイルの拡張子が異常です");
            }
        }catch(IOException e){

        }catch(Exception e){

        }

        String[] tblNameLine = clmnLst.get(1).split(",",0);
        String tblName = tblNameLine[1];

        // 末尾を削除する
        clmnLst.remove(clmnLst.size() -1 );
        // 内容以外の部分を削除
        for(int i = 6; i > 0; i-- ){
            clmnLst.remove(i);
        }

        // カラム名をキーとした、プロパティごとのマップ
        Map <String,Object> clmnMap = new HashMap<>();

        for (String row : clmnLst){
            clmnProperty = new ClmnProperty();
            String[] itemArr = row.split(",",0);

            clmnProperty.setComment(itemArr[0]);
            clmnProperty.setClmnName(itemArr[1]);
            clmnProperty.setType(itemArr[2]);
            // 数字に変換できたらその数字を、できなければ11を設定
            try {
                clmnProperty.setLength(Integer.parseInt(itemArr[3]));
            } catch (NumberFormatException e) {
            	clmnProperty.setLength(11);
            }
            clmnProperty.setPrimaryFlg(flgChange(itemArr[5]));
            clmnProperty.setUniqueFlg(flgChange(itemArr[7]));
            clmnProperty.setIndexFlg(flgChange(itemArr[13]));
            clmnProperty.setForignFlg(flgChange(itemArr[8]));
            clmnProperty.setNullFlg(flgChange(itemArr[6]));
            clmnProperty.setIncrementFlg(flgChange(itemArr[9]));
            clmnProperty.setDefaultVal(itemArr[10]);

            clmnMap.put(clmnProperty.getClmnName(),clmnProperty);
            clmnProperty = null;
        };
        tblMap.put(tblName,clmnMap);

        return tblMap;
    }

    private boolean flgChange(String item){
        boolean flg = false;
        if(item.equals("○")){
            flg = true;
        }else if(item == ""){
        	flg = false;
        }
        return flg;
    }
}
