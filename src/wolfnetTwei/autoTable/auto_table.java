package wolfnetTwei.autoTable;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class auto_table{
    public static void main(String args[]){
        String fileLstPath = "";
        String filePath = "";

        Map<String, List<String>> tblLst = new HashMap<>();

        if(args.length != 0){
            fileLstPath = args[0];
            File fils = null;
            String csvLst[] = null;

            try{
                // ファイルの取得
                fils = new File(fileLstPath);

                // CSVファイル一覧のリスト
                // csvLst = fils.listFiles();
                csvLst = fils.list();
            }catch(SecurityException e){
                System.out.println("アクセス拒否");
            }catch(Exception e){
                System.out.println("その他のエラー");
                throw new RuntimeException(e);
            }

            for (String csvName : csvLst){
                // マップに追加するCSVファイルのフルパス
                filePath = fileLstPath + "\\" + csvName;

                FileLoader fileLoader = new FileLoader();

                // ファイルパスからファイルをオープン、中身のカラムリストを取得
                List<String> clmnLst = fileLoader.fileLoad(filePath);

                // テーブル名の入った行の分割
                String[] tblNameLine = clmnLst.get(1).split(",",0);

                // キー値はテーブル名
                String key = tblNameLine[1];
                csvName.substring(0,csvName.length()-4);

                // CSVファイル名(テーブル名)をキーとしたマップにテーブルごとのカラムリストを追加
                tblLst.put(key,clmnLst);
            }
        }else{
            //
        }
    }
}