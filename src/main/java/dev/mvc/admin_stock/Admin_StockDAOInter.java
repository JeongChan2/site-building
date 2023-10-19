package dev.mvc.admin_stock;

public interface Admin_StockDAOInter {
  /**
   * 로그인
   * @param Admin_StockVO
   * @return
   */
  public int login(Admin_StockVO admin_stockVO);
  
  /**
   * 관리자 정보
   * @param String
   * @return
   */
  public Admin_StockVO read_by_id(String admin_id);
  
  /**
   * 관리자 정보
   * @param int
   * @return
   */
  public Admin_StockVO read(int admin_num);
  
}