/**
 *
 * CreateTable文自動生成メインクラス<br>
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 引数で取得したディレクトリのCSVファイル一覧を取得<br>
 * ファイル(テーブル)ごとにリストを作成する関数を呼び出す<br>
 * 取得したマップをファイルSQLファイルを生成する関数に渡す<br>
 *
 * @since  2018/05/04
 * @author takuto.osugi
 *
 */
class AutoTable{
    public static void main(String args[]){
        String fileLstPath = "";
        String filePath = "";

        Map<String, List<Object>> tblMap = new HashMap<>();

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

                try{
                    // ファイルパスからファイルをオープン、中身のカラムリストを取得
                    tblMap = fileLoader.fileLoad(filePath,tblMap);
                }catch(ExtentionException e){
                    // この例外の場合は該当ファイルをスキップ
                    continue;
                }catch(IOException e){
                    throw new RuntimeException(e);
                }catch(Exception e){
                	throw new RuntimeException("未知のエラーです");
                }
            }
        }else{
            // 引数が取れてないのでエラー
        	throw new RuntimeException("引数がありません");
        }

        SqlMaker sqlMaker = new SqlMaker();

        // 作成したマップを受け取り、ファイル生成する関数の呼び出し
        sqlMaker.makeStr(tblMap);
    }
}