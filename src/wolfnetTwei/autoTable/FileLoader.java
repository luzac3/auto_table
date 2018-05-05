/**
 *
 * ファイルリストからファイルの内容を取得するクラス<br>
 *
 * <package name="wolfnetTwei.autoTable" />
 *
 * @version 1.0
 * @since  2018/05/04
 * @author takuto.osugi
 *
 * Copyright (c) DC2I<br>
 *
 */
package wolfnetTwei.autoTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * ファイル(ファイルパス)とテーブルごとのマップを取得<br>
 * 取得したファイルのテーブル名をキーとした、テーブルカラムごとのプロパティをマップに登録して返す<br>
 *
 * @since  2018/05/04
 * @author takuto.osugi
 *
 */
public class FileLoader {
    String line = "";
    List<String> lineLst = new ArrayList<>();

    /**
     *
     * ファイル(ファイルパス)とテーブルごとのマップを取得<br>
     * 取得したファイルのテーブル名をキーとした、テーブルカラムごとのプロパティをマップに登録して返す<br>
     *
     *  @param filePath 内容を取得するファイルのファイルパス
     *  @param tblMap テーブル名をキーとした、テーブルごとのカラムリスト
     *  @return tblMap 取得したファイルの内容を追加したマップ
     *  @Exception
     *
     */
    @SuppressWarnings("deprecation")
	Map<String,List<Object>> fileLoad(String filePath, Map<String,List<Object>> tblMap) throws Exception{
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
                    lineLst.add(line);
                }
                br.close();
            }else if(extention.equals("xls") || extention.equals("xlsx")){
                Workbook workbook = WorkbookFactory.create(input);

                // シート番号取得
                Sheet sheet = workbook.getSheetAt(0);

                // 全行を繰り返し処理
                Iterator<Row> rows = sheet.rowIterator();
                while(rows.hasNext()) {
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
                                cellVal.append(cell.getStringCellValue().trim());
                                break;
                              case Cell.CELL_TYPE_NUMERIC:
                                if(DateUtil.isCellDateFormatted(cell)) {
                                    cellVal.append(cell.getDateCellValue());
                                } else {
                                    cellVal.append((int) cell.getNumericCellValue());
                                }
                                break;
                            }
                            cellVal.append(",");
                        }catch(IllegalStateException e){
                            throw e;
                        }catch(Exception e){
                        	throw e;
                        }
                    }
                    if(cellVal.length() > 0){
                        // 最後のカンマの削除
                        cellVal.setLength(cellVal.length() - 1);
                    }

                    lineLst.add(cellVal.toString());

                }
            }else{
            	input.close();
                throw new RuntimeException("ファイルの拡張子が異常です");
            }
        }catch(IOException e){

        }catch(Exception e){

        }

        String[] tblNameLine = lineLst.get(1).split(",",-1);
        String tblName = tblNameLine[1];

        // 末尾を削除する
        lineLst.remove(lineLst.size() -1 );
        // 内容以外の部分を削除
        for(int i = 5; i >= 0; i-- ){
        	lineLst.remove(i);
        }

        // 取得順のリスト
        List<Object> clmnLst = new ArrayList<>();

        // カラム名をキーとした、プロパティごとのマップ
        // Map <String,Object> clmnMap = new HashMap<>();

        for (String row : lineLst){
            clmnProperty = new ClmnProperty();
            String[] itemArr = row.split(",",-1);

            clmnProperty.setComment(itemArr[0]);
            clmnProperty.setClmnName(itemArr[1]);
            clmnProperty.setType(itemArr[2]);
            try {
                // 数字に変換できたらその数字を、できなければ11を設定
                clmnProperty.setLength(Integer.parseInt(itemArr[3]));
            } catch (NumberFormatException e) {
                clmnProperty.setLength(0);
            }
            clmnProperty.setPrimaryFlg(flgChange(itemArr[5]));
            clmnProperty.setUniqueFlg(flgChange(itemArr[7]));
            clmnProperty.setIndexFlg(flgChange(itemArr[12]));
            // clmnProperty.setForignFlg(flgChange(itemArr[8]));
            clmnProperty.setNullFlg(flgChange(itemArr[6]));
            clmnProperty.setIncrementFlg(flgChange(itemArr[9]));
            clmnProperty.setDefaultVal(itemArr[10]);

            clmnLst.add(clmnProperty);
            clmnProperty = null;
        };
        tblMap.put(tblName,clmnLst);

        return tblMap;
    }

    /**
     *
     * ファイルから取得したPKなどの「○(有)」をフラグに変換する<br>
     *
     *  @param item PKなどのプロパティ("○"か"")
     *  @return flg 取得したプロパティが○ならtrue,なしならfalse
     *
     */
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
