package dev.mvc.category;

import java.util.ArrayList;

public interface CategoryDAOInter {
  /**
   * 등록, 추상 메소드 -> Spring Boot가 구현함.
   * @param cateVO 객체
   * @return
   */
  public int create(CategoryVO categoryVO);
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<CategoryVO> list_all();
  
  /**
   * 조회
   * @param cate_id
   * @return
   */
  public CategoryVO read(int cate_id);
  
  /**
   * 수정
   * @param cateVO
   * @return
   */
  public int update(CategoryVO categoryVO);
  
  /**
   * 삭제
   * @param cate_id 삭제할 레코드 PK 번호
   * @return
   */
  public int delete(int cate_id);
  
  
  /**
   * 우선순위 높임, 10등 -> 1등
   * @param cateVO
   * @return
   */
  public int update_seqno_forward(int cate_id);
  
  
  /**
   * 우선순위 낮춤, 1등 -> 10등
   * @param cate_id
   * @return
   */
  public int update_seqno_backward(int cate_id);
  
  /**
   * 카테고리 공개 설정
   * @param cate_id
   * @return
   */
  public int update_visible_y(int cate_id);
  
  /**
   * 카테고리 비공개 설정
   * @param cate_id
   * @return
   */
  public int update_visible_n(int cate_id);
  
  
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<CategoryVO> list_all_y();
  
}
