package dev.mvc.admin_stock;

public class Admin_StockVO {
  private int admin_num;
  private String admin_id;
  private String passwd;
  private String mname;
  private String mdate;
  private int grade;
  
  
  public int getAdmin_num() {
    return admin_num;
  }
  public void setAdmin_num(int admin_num) {
    this.admin_num = admin_num;
  }
  public String getAdmin_id() {
    return admin_id;
  }
  public void setAdmin_id(String admin_id) {
    this.admin_id = admin_id;
  }
  public String getPasswd() {
    return passwd;
  }
  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }
  public String getMname() {
    return mname;
  }
  public void setMname(String mname) {
    this.mname = mname;
  }
  public String getMdate() {
    return mdate;
  }
  public void setMdate(String mdate) {
    this.mdate = mdate;
  }
  public int getGrade() {
    return grade;
  }
  public void setGrade(int grade) {
    this.grade = grade;
  }
  @Override
  public String toString() {
    return "AdminVO [admin_num=" + admin_num + ", admin_id=" + admin_id + ", passwd=" + passwd + ", mname=" + mname
        + ", mdate=" + mdate + ", grade=" + grade + "]";
  }
  
  
  
}