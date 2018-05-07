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
import java.util.Objects;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * 引数で取得したディレクトリのCSVかXLSファイル一覧を取得<br>
 * ファイル(テーブル)ごとにリストを作成する関数を呼び出す<br>
 * 取得したマップをファイルSQLファイルを生成する関数に渡す<br>
 *
 * @since  2018/05/04
 * @author takuto.osugi
 *
 */
class AutoTable{
    /** Logger SLF4J */
    private static Logger log = Logger.getLogger(AutoTable.class);

    public static void main(String args[]){
        DOMConfigurator.configure("./log4j.xml");

        final String FILE_LST_PATH = "..\\file";
        String filePath = "";

        Map<String, List<Object>> tblMap = new HashMap<>();

            File fils = null;
            String csvLst[] = null;

            try{
                log.trace("ファイル取得開始");
                // ファイルの取得
                fils = new File(FILE_LST_PATH);

                // CSVファイル一覧のリスト
                csvLst = fils.list();
            }catch(SecurityException e){
                log.error("アクセス拒否されました：" + e);
                System.out.println("アクセス拒否されました");
            }catch(Exception e){
                System.out.println("その他のエラー");
                log.error("未知のエラーです：" + e);
                throw new RuntimeException(e);
            }

            log.info("ファイル読み込み開始");
            if(Objects.isNull(csvLst)){
                log.info("ファイルがありません");
                return;
            }
            for (String csvName : csvLst){
                // マップに追加するCSVファイルのフルパス
                filePath = FILE_LST_PATH + "\\" + csvName;

                FileLoader fileLoader = new FileLoader();

                try{
                    // ファイルパスからファイルをオープン、中身のカラムリストを取得
                    tblMap = fileLoader.fileLoad(filePath,tblMap);
                }catch(ExtentionException e){
                    // この例外の場合は該当ファイルをスキップ
                    log.info("ファイルをスキップしました");
                    continue;
                }catch(IOException e){
                    log.error(e);
                    throw new RuntimeException(e);
                }catch(Exception e){
                    log.error("未知のエラーです：" + e);
                    throw new RuntimeException("未知のエラーです");
                }
                log.info("ファイル読み込み終了");
            }

        SqlMaker sqlMaker = new SqlMaker();

        log.info("ファイル書き込み開始");
        // 作成したマップを受け取り、ファイル生成する関数の呼び出し
        sqlMaker.makeStr(tblMap);
        log.info("ファイル書き込み完了");
    }
}