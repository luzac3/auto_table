package wolfnetTwei.autoTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    String line = "";
    List<String> clmnLst = new ArrayList<>();

    List<String> fileLoad(String filePath){
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
        return clmnLst;
    }
}
