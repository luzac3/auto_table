package wolfnetTwei.autoTable;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlMaker {
    boolean overWriteFlg = false;

    void makeStr(Map<String,List<Object>> map){
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
            // StringBuilder foreign = null;
            StringBuilder index = null;

            String tblName = "";
            String clmnName = "";

            List<Object> clmnLstInner = new ArrayList<>();

            ClmnProperty clmnProperty;

            for (Map.Entry<String,List<Object>> clmnMap : map.entrySet()){
                tblName = clmnMap.getKey();
                clmnLstInner = clmnMap.getValue();

                // 同一名テーブルの削除文
                pw.print("DROP TABLE IF EXISTS ");
                pw.println(tblName);
                pw.println(";");

                key = new StringBuilder();
                unique = new StringBuilder();
                index = new StringBuilder();

                key.append("(");
                unique.append("(");
                // foreign.append("(");
                index.append("(");

                for (Object flg: clmnLstInner){
                    clmnProperty = (ClmnProperty) flg;

                    clmnName = clmnProperty.getClmnName();

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
                pw.print("CREATE TABLE IF NOT EXISTS ");
                pw.println(tblName);
                pw.println("(");

                boolean firstRowFlg = true;
                for (Object flg: clmnLstInner){
                    clmnProperty = (ClmnProperty) flg;

                    clmnName = clmnProperty.getClmnName();

                    if(firstRowFlg){
                        firstRowFlg = false;
                    }else{
                        pw.print(",");
                    }
                    // カラム名
                    pw.println(clmnName);

                    // 型
                    pw.print(clmnProperty.getType());
                    pw.print(" ");

                    // DATE型はLengthを設定しない
                    if(!clmnProperty.getType().equals("DATE")){
                        // 長さ
                        pw.print("(");
                        pw.print(clmnProperty.getLength());
                        pw.println(")");
                    }

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
                    pw.print("COMMENT ");
                    pw.print("'");
                    pw.print(clmnProperty.getComment());
                    pw.println("'");

                    //改行
                    pw.print("\r\n");
                }
                // メモリを解放する
                clmnProperty = null;

                // keyの長さが"("より大きければ存在
                if(key.length() > 1){
                    pw.print(",");
                    // 末尾の","を削除
                    key.setLength(key.length() -1);
                    // ()を閉じる
                    key.append(")");
                    pw.print("PRIMARY KEY ");
                    pw.println(key);
                }
                if(unique.length() > 1){
                    pw.print(",");
                    unique.setLength(unique.length() -1);
                    unique.append(")");
                    pw.print("UNIQUE ");
                    pw.println(unique);
                }
                if(index.length() > 1){
                    pw.print(",");
                    index.setLength(index.length() -1);
                    index.append(")");
                    pw.print("INDEX ");
                    pw.println(index);
                }
                pw.println(")");
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
