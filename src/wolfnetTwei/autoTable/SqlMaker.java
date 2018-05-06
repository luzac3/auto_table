/**
 *
 * SQL文(CreateTable文)を生成するクラス<br>
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

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 取得したマップを展開し、テーブル数分ループする<br>
 * テーブル名ごとのカラムリストからプロパティを取得し、<br>
 * SQL文として並べ替えてファイルに出力する<br>
 *
 * @since  2018/05/04
 * @author takuto.osugi
 *
 */
public class SqlMaker {

    /**
     *
     * 取得したマップを展開し、テーブル数分ループする<br>
     * テーブル名ごとのカラムリストからプロパティを取得し、<br>
     * SQL文として並べ替えてファイルに出力する<br>
     *
     *  @param Map テーブルごとのカラムリスト/プロパティ
     *  @return なし
     *
     */
    void makeStr(Map<String,List<Object>> map){
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            String path = ""
            		+ "src\\wolfnetTwei\\autoTable\\updater.sql";
            // 常に上書き処理
            fw = new FileWriter(path, false);

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

                    // Lengthがない場合はスキップ
                    if(clmnProperty.getLength() != 0){
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
