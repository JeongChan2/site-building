package dev.mvc.category;

//CREATE TABLE CATEGORY(
//    cate_id                            NUMBER(10)     NOT NULL,
//    cate_name                              VARCHAR2(50)     NOT NULL,
//    cate_cnt                               NUMBER(7)    DEFAULT 0     NOT NULL,
//    rdate                             DATE     NOT NULL
//);



public class CategoryVO {
  private int cate_id;
  private String cate_name;
  private int cate_cnt;
  private String rdate;
  private int seqno;
  private String visible;
  
  public int getCate_id() {
    return cate_id;
  }
  public void setCate_id(int cate_id) {
    this.cate_id = cate_id;
  }
  public String getCate_name() {
    return cate_name;
  }
  public void setCate_name(String cate_name) {
    this.cate_name = cate_name;
  }
  public int getCate_cnt() {
    return cate_cnt;
  }
  public void setCate_cnt(int cate_cnt) {
    this.cate_cnt = cate_cnt;
  }
  public String getRdate() {
    return rdate;
  }
  public void setRdate(String rdate) {
    this.rdate = rdate;
  }
  public int getSeqno() {
    return seqno;
  }
  public void setSeqno(int seqno) {
    this.seqno = seqno;
  }
  public String getVisible() {
    return visible;
  }
  public void setVisible(String visible) {
    this.visible = visible;
  }
  @Override
  public String toString() {
    return "CateVO [cate_id=" + cate_id + ", cate_name=" + cate_name + ", cate_cnt=" + cate_cnt + ", rdate=" + rdate
        + ", seqno=" + seqno + ", visible=" + visible + "]";
  }
  
  
}
