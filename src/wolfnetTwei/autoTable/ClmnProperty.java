/**
 *
 * FileName : ClmnProperty.java
 *
 * カラムごとのプロパティを保持するクラス<br>
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

/**
 *
 * テーブル生成時に設定できるプロパティを、カラムごとに保持する<br>
 *
 * @since  2018/05/04
 * @author takuto.osugi
 *
 */
public class ClmnProperty {
    private String clmnName;
    private String comment;
    private String type;

    private int length;

    private boolean primaryFlg;
    private boolean uniqueFlg;
    private boolean indexFlg;

    private boolean forignFlg;

    private boolean nullFlg;
    private boolean incrementFlg;

    private String defaultVal;

    /**
     *
     * カラム名を設定する<br>
     *
     *  @param clmnName カラム名
     *
     */
    void setClmnName(String clmnName){
        this.clmnName = clmnName;
    }
    /**
     *
     * カラム名を取得する<br>
     *
     *  @return clmnName カラム名
     *
     */
    String getClmnName(){
        return clmnName;
    }

    /**
     *
     * コメントを設定する<br>
     *
     *  @param comment コメント
     *
     */
    void setComment(String comment){
        this.comment = comment;
    }
    /**
    *
    * コメントを取得する<br>
    *
    *  @return comment コメント
    *
    */
    String getComment(){
        return comment;
    }

    /**
     *
     * 型を設定する<br>
     *
     *  @param type 型
     *
     */
    void setType(String type){
        this.type = type;
    }
    /**
    *
    * 型を取得する<br>
    *
    *  @return type 型
    *
    */
    String getType(){
        return type;
    }

    /**
     *
     * 長さを設定する<br>
     *
     *  @param length 長さ
     *
     */
    void setLength(int length){
        this.length = length;
    }
    /**
    *
    * 長さを取得する<br>
    *
    *  @return length 長さ
    *
    */
    int getLength(){
        return length;
    }

    /**
     *
     * PKフラグを設定する<br>
     * カラムがPKかどうかの判別に使用<br>
     *
     *  @param primaryFlg PKフラグ
     *
     */
    void setPrimaryFlg(boolean primaryFlg){
    	this.primaryFlg = primaryFlg;
    }
    /**
    *
    * PKフラグを取得する<br>
    *
    *  @return primaryFlg PKフラグ
    *
    */
    boolean getPrimaryFlg(){
        return primaryFlg;
    }

    /**
     *
     * ユニークフラグを設定する<br>
     * カラムがユニークかどうかの判別に使用<br>
     *
     *  @param uniqueFlg ユニークフラグ
     *
     */
    void setUniqueFlg(boolean uniqueFlg){
    	this.uniqueFlg = uniqueFlg;
    }
    /**
    *
    * ユニークフラグを取得する<br>
    *
    *  @return uniqueFlg ユニークフラグ
    *
    */
    boolean getUniqueFlg(){
        return uniqueFlg;
    }

    /**
     *
     * インデックスフラグを設定する<br>
     * インデックスを貼るかどうかの判別に使用<br>
     *
     *  @param indexFlg インデックスフラグ
     *
     */
    void setIndexFlg(boolean indexFlg){
    	this.indexFlg = indexFlg;
    }
    /**
    *
    * インデックスフラグを取得する<br>
    *
    *  @return indexFlg インデックスフラグ
    *
    */
    boolean getIndexFlg(){
        return indexFlg;
    }

    /**
     *
     * FKフラグを設定する<br>
     * FKが存在するかどうかの判別に使用<br>
     *
     *  @param forignFlg FKフラグ
     *
     */
    void setForignFlg(boolean forignFlg){
    	this.forignFlg = forignFlg;
    }
    /**
    *
    * FKフラグを取得する<br>
    *
    *  @return forignFlg FKフラグ
    *
    */
    boolean getForignFlg(){
        return forignFlg;
    }

    /**
     *
     * NULLフラグを設定する<br>
     * NULLを許容するかどうかの判別に使用<br>
     *
     *  @param nullFlg NULLフラグ
     *
     */
    void setNullFlg(boolean nullFlg){
    	this.nullFlg = nullFlg;
    }
    /**
    *
    * NULLフラグを取得する<br>
    *
    *  @return nullFlg NULLフラグ
    *
    */
    boolean getNullFlg(){
        return nullFlg;
    }

    /**
     *
     * オートインクリメントフラグを設定する<br>
     * オートインクリメントを設定するかどうかの判別に使用<br>
     *
     *  @param incrementFlg オートインクリメントフラグ
     *
     */
    void setIncrementFlg(boolean incrementFlg){
        this.incrementFlg = incrementFlg;
    }
    /**
    *
    * オートインクリメントフラグを取得する<br>
    *
    *  @return incrementFlg オートインクリメントフラグ
    *
    */
    boolean getIncrementFlg(){
        return incrementFlg;
    }

    /**
     *
     * デフォルト値を設定する<br>
     * デフォルト値が存在すれば設定<br>
     *
     *  @param defaultVal デフォルト値
     *
     */
    void setDefaultVal(String defaultVal){
        this.defaultVal = defaultVal;
    }
    /**
    *
    * デフォルト値を取得する<br>
    *
    *  @return defaultVal デフォルト値
    *
    */
    String getDefaultVal(){
        return defaultVal;
    }
}
