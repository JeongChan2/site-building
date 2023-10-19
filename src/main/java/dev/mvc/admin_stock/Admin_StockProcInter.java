package dev.mvc.admin_stock;

import javax.servlet.http.HttpSession;

public interface Admin_StockProcInter {
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
   * 관리자 로그인 체크
   * @param session
   * @return
   */
  public boolean isAdmin(HttpSession session);
  
  /**
   * 관리자 정보
   * @param int
   * @return
   */
  public Admin_StockVO read(int admin_num);
  
}