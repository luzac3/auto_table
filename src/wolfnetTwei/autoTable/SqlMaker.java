package wolfnetTwei.autoTable;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SqlMaker {
    boolean overWriteFlg = false;

    void makeStr(Map<String,Map<String,Object>> map){
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            String path = "C:\\Users\\user\\git\\auto_table\\src\\wolfnetTwei\\autoTable\\updater.sql";
            // 一度目は上書き処理
            fw = new FileWriter(path, overWriteFlg);
            // 二度目以降は追記
            overWriteFlg = true;

            pw = new PrintWriter(fw);

            StringBuilder key = null;
            StringBuilder unique = null;
            StringBuilder index = null;

            String tblName = "";
            String clmnName = "";

            Map<String,Object> clmnMapInner = new HashMap<>();

            ClmnProperty clmnProperty;

            for (Map.Entry<String,Map<String,Object>> clmnMap : map.entrySet()){
                tblName = clmnMap.getKey();
                clmnMapInner = clmnMap.getValue();

                // 同一名テーブルの削除文
                pw.println("DROP TABLE IF EXISTS ");
                pw.println(tblName);
                pw.println(";");
                pw.println("\n");

                key = new StringBuilder();
                unique = new StringBuilder();
                index = new StringBuilder();

                key.append("(");
                unique.append("(");
                index.append("(");

                for (Map.Entry<String,Object> flg: clmnMapInner.entrySet()){
                    clmnName = flg.getKey();
                    clmnProperty = (ClmnProperty) flg.getValue();

                    // else if とcontinueどっちがいいですかね？
                    if(clmnProperty.getPrimaryFlg()){
                        key.append(clmnName);
                        key.append(",");
                        continue;
                    }
                    if(clmnProperty.getUniqueFlg()){
                        unique.append(clmnName);
                        unique.append(",");
                        continue;
                    }
                    if(clmnProperty.getIndexFlg()){
                        index.append(clmnName);
                        index.append(",");
                    }
                }
                key.append(")");
                unique.append(")");
                index.append(")");

                pw.println("CREATE TABLE IF NOT EXISTS ");
                pw.println(tblName);
                pw.println("(");

                for (Map.Entry<String,Object> flg: clmnMapInner.entrySet()){
                    clmnName = flg.getKey();
                    clmnProperty = (ClmnProperty) flg.getValue();

                    // カラム名
                    pw.println(clmnName);
                    pw.println(" ");

                    // 型
                    pw.println(clmnProperty.getType());

                    // 長さ
                    pw.println("(");
                    pw.println(clmnProperty.getLength());
                    pw.println(")");
                    pw.println(" ");

                    // nullを許容するか
                    if(clmnProperty.getNullFlg()){
                        pw.println("NULL ");
                    }else{
                        pw.println("NOT NULL ");
                    }

                    // default値が存在するか
                    if(!clmnProperty.getDefaultVal().isEmpty()){
                        pw.println("DEFAULT ");
                        pw.println(clmnProperty.getDefaultVal());
                        pw.println(" ");
                    }

                    // オートインクリメント
                    if(clmnProperty.getIncrementFlg()){
                        pw.println("AUTO_INCREMENT ");
                    }

                    // コメント
                    pw.println("COMMENT ");
                    pw.println(clmnProperty.getComment());

                    // key unique index情報を入力する。先に作っておかないとダメっぽい。
                    // keyの長さが"()"より大きければ存在
                    if(key.length() > 2){
                        // 末尾の二つ前の","を削除
                        key.setLength(key.length() -2);
                        pw.println(key);
                    }
                    if(unique.length() > 2){
                        unique.setLength(unique.length() -2);
                        pw.println(unique);
                    }
                    if(index.length() > 2){
                        index.setLength(index.length() -2);
                        pw.println(index);
                    }
                }
                clmnProperty = null;
                pw.println(";");
            }
            // 文字列の出力
            pw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(pw != null) {
            	pw.close();
            }
            if(fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
