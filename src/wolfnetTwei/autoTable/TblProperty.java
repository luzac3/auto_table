package wolfnetTwei.autoTable;

// じゃばどく
// テーブルのプロパティ。主にテーブルのコメント用
public class TblProperty {
    private String tblName;
    private String tblComment;

    void setTblName(String tblName){
        this.tblName = tblName;
    }
    String getTblName(){
        return tblName;
    }

    void setComment(String tblComment){
        this.tblComment = tblComment;
    }
    String getComment(){
        return tblComment;
    }
}
