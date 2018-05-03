package wolfnetTwei.autoTable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

class AutoTable{
    public static void main(String args[]){
        String fileLstPath = "";
        String filePath = "";

        Map<String, Map<String,Object>> tblMap = new HashMap<>();

        if(args.length != 0){
            fileLstPath = args[0];
            File fils = null;
            String csvLst[] = null;

            try{
                // ファイルの取得
                fils = new File(fileLstPath);

                // CSVファイル一覧のリスト
                csvLst = fils.list();
            }catch(SecurityException e){
                System.out.println("アクセス拒否されました");
            }catch(Exception e){
                System.out.println("その他のエラー");
                throw new RuntimeException(e);
            }

            for (String csvName : csvLst){
                // マップに追加するCSVファイルのフルパス
                filePath = fileLstPath + "\\" + csvName;

                FileLoader fileLoader = new FileLoader();

                // ファイルパスからファイルをオープン、中身のカラムリストを取得
                tblMap = fileLoader.fileLoad(filePath,tblMap);
            }
        }else{
            // 引数が取れてないのでエラー
        	throw new RuntimeException("引数がありません");
        }

        // 作成したマップから文字列を作成し、ファイル生成を呼び出す
        SqlMaker sqlMaker = new SqlMaker();

        sqlMaker.makeStr(tblMap);
    }
}