package wolfnetTwei.autoTable;

// じゃばどく
// カラムごとのプロパティを設定する場所。このオブジェクトをマップに登録して使う
public class ClmnProperty {
    private String clmnName;
    private String comment;
    private String type;

    private int length;
    // private String 少数;

    private boolean primaryFlg;
    private boolean uniqueFlg;
    private boolean indexFlg;

    private boolean forignFlg;

    private boolean nullFlg;
    private boolean incrementFlg;

    private String defaultVal;

    void setClmnName(String clmnName){
        this.clmnName = clmnName;
    }
    String getClmnName(){
        return clmnName;
    }

    void setComment(String comment){
        this.comment = comment;
    }
    String getComment(){
        return comment;
    }

    void setType(String type){
        this.type = type;
    }
    String getType(){
        return type;
    }

    void setLength(int length){
        this.length = length;
    }
    int getLength(){
        return length;
    }

    void setPrimaryFlg(boolean primaryFlg){
    	this.primaryFlg = primaryFlg;
    }
    boolean getPrimaryFlg(){
        return primaryFlg;
    }

    void setUniqueFlg(boolean uniqueFlg){
    	this.uniqueFlg = uniqueFlg;
    }
    boolean getUniqueFlg(){
        return uniqueFlg;
    }

    void setIndexFlg(boolean indexFlg){
    	this.indexFlg = indexFlg;
    }
    boolean getIndexFlg(){
        return indexFlg;
    }

    void setForignFlg(boolean forignFlg){
    	this.forignFlg = forignFlg;
    }
    boolean getForignFlg(){
        return forignFlg;
    }

    void setNullFlg(boolean nullFlg){
    	this.nullFlg = nullFlg;
    }
    boolean getNullFlg(){
        return nullFlg;
    }

    void setIncrementFlg(boolean incrementFlg){
    	this.incrementFlg = incrementFlg;
    }
    boolean getIncrementFlg(){
        return incrementFlg;
    }

    void setDefaultVal(String defaultVal){
        this.defaultVal = defaultVal;
    }
    String getDefaultVal(){
        return defaultVal;
    }
}
