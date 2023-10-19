package dev.mvc.contents_stock;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

public interface Contents_StockProcInter {
  /**
   * 등록
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
   * @param youtube
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
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param cateno          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명 
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int cate_id, int now_page, String word, String list_file, int search_count);

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