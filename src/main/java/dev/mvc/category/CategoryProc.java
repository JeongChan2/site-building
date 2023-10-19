package dev.mvc.category;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.category.CategoryProc")
public class CategoryProc implements CategoryProcInter {
  @Autowired
  private CategoryDAOInter categoryDAO;
  @Override
  public int create(CategoryVO categoryVO) {
    int cnt = this.categoryDAO.create(categoryVO);
    return cnt;
  }
  @Override
  public ArrayList<CategoryVO> list_all() {
    ArrayList<CategoryVO> list = this.categoryDAO.list_all();
    
    return list;
  }
  @Override
  public CategoryVO read(int cate_id) {
    CategoryVO categoryVO = this.categoryDAO.read(cate_id);
    
    return categoryVO;
  }
  @Override
  public int update(CategoryVO categoryVO) {
    int cnt = this.categoryDAO.update(categoryVO);
    return cnt;
  }
  @Override
  public int delete(int cate_id) {
    int cnt = this.categoryDAO.delete(cate_id);
    return cnt;
  }
  @Override
  public int update_seqno_forward(int cate_id) {
    int cnt = this.categoryDAO.update_seqno_forward(cate_id);
    return cnt;
  }
  @Override
  public int update_seqno_backward(int cate_id) {
    int cnt = this.categoryDAO.update_seqno_backward(cate_id);
    return cnt;
  }
  @Override
  public int update_visible_y(int cate_id) {
    int cnt = this.categoryDAO.update_visible_y(cate_id);
    return cnt;
  }
  @Override
  public int update_visible_n(int cate_id) {
    int cnt = this.categoryDAO.update_visible_n(cate_id);
    return cnt;
  }
  @Override
  public ArrayList<CategoryVO> list_all_y() {
    ArrayList<CategoryVO> list = this.categoryDAO.list_all_y();
    return list;
  }

}
