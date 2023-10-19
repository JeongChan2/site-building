package dev.mvc.admin_stock;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.admin_stock.Admin_StockProc")
public class Admin_StockProc implements Admin_StockProcInter {
  @Autowired
  private Admin_StockDAOInter admin_stockDAO;
  
  @Override
  public int login(Admin_StockVO admin_stockVO) {
    int cnt = this.admin_stockDAO.login(admin_stockVO);
    return cnt;
  }

  @Override
  public Admin_StockVO read_by_id(String admin_id) {
    Admin_StockVO admin_stockVO = this.admin_stockDAO.read_by_id(admin_id);
    return admin_stockVO;
  }

  @Override
  public boolean isAdmin(HttpSession session) {
    boolean admin = false;
    
    if(session != null) {
      String admin_id = (String)session.getAttribute("admin_id");
      
      if(admin_id != null) {
        admin = true;
      }
    }
    
    return admin;
  }

  
  @Override
  public Admin_StockVO read(int admin_num) {
    Admin_StockVO admin_stockVO = this.admin_stockDAO.read(admin_num);
    return admin_stockVO;
  }
  
}