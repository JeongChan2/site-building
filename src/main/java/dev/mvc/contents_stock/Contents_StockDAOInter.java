package dev.mvc.contents_stock;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import dev.mvc.contents_stock.Contents_StockVO;

public interface Contents_StockDAOInter {
  /**
   * 등록 추상 메소드
   * @param contentsVO
   * @return
   */
  public int create(Contents_StockVO contents_stockVO);

  /**
   * 모든 리스트 추상 메소드
   * @param 
   * @return
   */
  public ArrayList<Contents_StockVO> list_all();
  
  /**
   * 모든 리스트 추상 메소드
   * @param 
   * @return
   */
  public ArrayList<Contents_StockVO> list_by_cate_id(int cate_id);
  
  /**
   * 조회
   * @param contentsno
   * @return
   */
  public Contents_StockVO read(int contents_num);
  
  /**
   * map 등록, 수정, 삭제
   * @param map
   * @return 수정된 레코드 갯수
   */
  public int map(HashMap<String, Object> hashMap);
  
  /**
   * youtube 등록, 수정, 삭제
   * @param map
   * @return 수정된 레코드 갯수
   */
  public int youtube(HashMap<String, Object> hashMap);
  
  /**
   * 검색 목록
   * @param HashMap
   * @return ContentsVO
   */
  public ArrayList<Contents_StockVO> list_by_cate_id_search(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색된 레코드 개수
   * @param HashMap
   * @return 검색된 레코드 갯수
   */
  public int search_count(HashMap<String, Object> hashMap);
  
  /**
   * 검색 목록
   * @param HashMap
   * @return ContentsVO
   */
  public ArrayList<Contents_StockVO> list_by_cate_id_search_paging(Contents_StockVO contents_stockVO);
  
  /**
   * 패스워드 검사 
   * @param hashmap
   * @return 
   */
  public int password_check(HashMap<String, Object> hashmap);
  
  /**
   * 글 정보 수정
   * @param contents_stockVO
   * @return 처리된 레코드 갯수
   */
  public int update_text(Contents_StockVO contents_stockVO);
  
  /**
   * 파일 정보 수정
   * @param contents_stockVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(Contents_StockVO contents_stockVO);
  
  /**
   * 삭제
   * @param contents_num
   * @return 삭제된 레코드 갯수
   */
  public int delete(int contents_num);
  
  /**
   * FK cate_id 값이 같은 레코드 갯수 산출
   * @param cate_id
   * @return
   */
  public int count_by_cate_id(int cate_id);
  
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param cate_id
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_cate_id(int cate_id);

}