package wolfnetTwei.autoTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileLoader {
    String line = "";
    List<String> clmnLst = new ArrayList<>();

    Map<String,Map<String,Object>> fileLoad(String filePath, Map<String,Map<String,Object>> tblMap){
        // 宣言してNULLを設定
        ClmnProperty clmnProperty = null;

        try{
            File csv = new File(filePath);
            FileInputStream input = new FileInputStream(csv);
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
        if(item == "○"){
            flg = true;
        }else if(item == ""){
        	flg = false;
        }
        return flg;
    }
}
