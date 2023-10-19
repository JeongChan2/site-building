package dev.mvc.contents_stock;

import java.util.ArrayList;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin_stock.Admin_StockProcInter;
import dev.mvc.category.CategoryProcInter;
import dev.mvc.category.CategoryVO;
import dev.mvc.contents_stock.Contents_StockVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@Controller
public class Contents_StockCont {
  @Autowired
  @Qualifier("dev.mvc.admin_stock.Admin_StockProc") // @Component("dev.mvc.admin.AdminProc")
  private Admin_StockProcInter admin_stockProc;
  
  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc") // @Component("dev.mvc.cate.CateProc")
  private CategoryProcInter categoryProc;
  
  @Autowired
  @Qualifier("dev.mvc.contents_stock.Contents_StockProc") // @Component("dev.mvc.contents.ContentsProc")
  private Contents_StockProcInter contents_stockProc;
  
  public Contents_StockCont () {
    System.out.println("-> Contents_StockCont created.");
  }
  
  
  /**
   * POST 요청시 JSP 페이지에서 JSTL 호출 기능 지원, 새로고침 방지, EL에서 param으로 접근
   * POST → url → GET → 데이터 전송
   * @return
   */
  @RequestMapping(value="/contents_stock/msg.do", method=RequestMethod.GET)
  public ModelAndView msg(String url){
    ModelAndView mav = new ModelAndView();

    mav.setViewName(url); // forward
    
    return mav; // forward
  }
  
  // 등록 폼, contents 테이블은 FK로 cate_id를 사용함.
  // http://localhost:9092/contents_stock/create.do  X
  // http://localhost:9092/contents_stock/create.do?cate_id=1
  // http://localhost:9092/contents_stock/create.do?cate_id=2
  // http://localhost:9092/contents_stock/create.do?cate_id=3
  @RequestMapping(value="/contents_stock/create.do", method = RequestMethod.GET)
  public ModelAndView create(int cate_id) {
//  public ModelAndView create(HttpServletRequest request,  int cate_id) {
    ModelAndView mav = new ModelAndView();

    CategoryVO categoryVO = this.categoryProc.read(cate_id); // create.jsp에 카테고리 정보를 출력하기위한 목적
    mav.addObject("categoryVO", categoryVO);
//    request.setAttribute("categoryVO", categoryVO);
    
    mav.setViewName("/contents_stock/create"); // /webapp/WEB-INF/views/contents_stock/create.jsp
    
    return mav;
  }
  
  
  /**
   * 등록 처리 http://localhost:9091/contents/create.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents_stock/create.do", method = RequestMethod.POST)
  public ModelAndView create(HttpServletRequest request, HttpSession session, Contents_StockVO contents_stockVO) {
    ModelAndView mav = new ModelAndView();
    
    if (admin_stockProc.isAdmin(session)) { // 관리자로 로그인한경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image
      String file1saved = "";   // 저장된 파일명, image
      String thumb1 = "";     // preview image

      String upDir =  Contents_Stock.getUploadDir(); // 파일을 업로드할 폴더 준비
      System.out.println("-> upDir: " + upDir);
      
      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = contents_stockVO.getFile1MF();
      
      file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 file1: " + file1);
      
      if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
        long size1 = mf.getSize();  // 파일 크기
        
        if (size1 > 0) { // 파일 크기 체크
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
          file1saved = Upload.saveFileSpring(mf, upDir); 
          
          if (Tool.isImage(file1saved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            thumb1 = Tool.preview(upDir, file1saved, 200, 150); 
          }
          
        }    
        
        contents_stockVO.setFile1(file1);   // 순수 원본 파일명
        contents_stockVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
        contents_stockVO.setThumb1(thumb1);      // 원본이미지 축소판
        contents_stockVO.setSize1(size1);  // 파일 크기
        // ------------------------------------------------------------------------------
        // 파일 전송 코드 종료
        // ------------------------------------------------------------------------------
        
        // Call By Reference: 메모리 공유, Hashcode 전달
        int admin_num = (int)session.getAttribute("admin_num"); // adminno FK
        contents_stockVO.setAdmin_num(admin_num);
        int cnt = this.contents_stockProc.create(contents_stockVO); 
        
        // ------------------------------------------------------------------------------
        // PK의 return
        // ------------------------------------------------------------------------------
        // System.out.println("--> contentsno: " + contentsVO.getContentsno());
        // mav.addObject("contentsno", contentsVO.getContentsno()); // redirect parameter 적용
        // ------------------------------------------------------------------------------
        
        if (cnt == 1) {
            mav.addObject("code", "create_success");
            // cateProc.increaseCnt(contentsVO.getCateno()); // 글수 증가
        } else {
            mav.addObject("code", "create_fail");
        }
        mav.addObject("cnt", cnt); // request.setAttribute("cnt", cnt)
        
        // System.out.println("--> cateno: " + contentsVO.getCateno());
        // redirect시에 hidden tag로 보낸것들이 전달이 안됨으로 request에 다시 저장
        mav.addObject("cate_id", contents_stockVO.getCate_id()); // redirect parameter 적용
        
        mav.addObject("url", "/contents_stock/msg"); // msg.jsp, redirect parameter 적용
        mav.setViewName("redirect:/contents_stock/msg.do"); // Post -> Get - param...        
      } else {
        mav.addObject("cnt", "0"); // 업로드 할 수 없는 파일
        mav.addObject("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
        mav.addObject("url", "/contents_stock/msg"); // msg.jsp, redirect parameter 적용
        mav.setViewName("redirect:/contents_stock/msg.do"); // Post -> Get - param...        
      }
    } else {
      mav.addObject("url", "/admin_stock/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/contents_stock/msg.do"); 
    }
    
    return mav; // forward
  }
  
  /**
   * 관리자만 전체목록 확인 가능
   * @param session
   * @return
   */
  @RequestMapping(value = "/contents_stock/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if(this.admin_stockProc.isAdmin(session) == true) {
      mav.setViewName("/contents_stock/list_all"); // /WEB-INF/views/contents_stock/list_all.jsp
      
      ArrayList<Contents_StockVO> list = this.contents_stockProc.list_all();
      
   // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
      for (Contents_StockVO contents_stockVO : list) {
        String title = contents_stockVO.getTitle();
        String content = contents_stockVO.getContent();
        
        title = Tool.convertChar(title);  // 특수 문자 처리
        content = Tool.convertChar(content); 
        
        contents_stockVO.setTitle(title);
        contents_stockVO.setContent(content);  

      }
      mav.addObject("list",list);
    } else {
      mav.setViewName("/admin_stock/login_need");
    }
    
    
    
    return mav;
  }
  
//  /**
//   * 특정 카테고리의 검색 목록
//   * http://localhost:9092/contents_stock/list_by_cate_id.do?cate_id=2
//   * @param session
//   * @return
//   */
//  @RequestMapping(value = "/contents_stock/list_by_cate_id.do", method = RequestMethod.GET)
//  public ModelAndView list_by_cate_id(int cate_id, String word) {
//    ModelAndView mav = new ModelAndView();
//    
////  mav.setViewName("/contents/list_by_cateno"); // /WEB-INF/views/cate/list_all.jsp
//  mav.setViewName("/contents_stock/list_by_cate_id");
//  
//  CategoryVO categoryVO = this.categoryProc.read(cate_id);
//  mav.addObject("categoryVO", categoryVO);
//  // request.setAttribute("cateVO, cateVO);
//
//  // 검색하지 않는 경우
//  // ArrayList<ContentsVO> list = this.contentsProc.list_by_cateno(cateno);
//  
//  // 검색하는 경우
//  HashMap<String, Object> hashMap = new HashMap<String, Object>();
//  hashMap.put("cate_id", cate_id);
//  hashMap.put("word", word);
//  
//  ArrayList<Contents_StockVO> list = this.contents_stockProc.list_by_cate_id_search(hashMap);
//  
//  // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
//  for (Contents_StockVO contents_stockVO : list) {
//    String title = contents_stockVO.getTitle();
//    String content = contents_stockVO.getContent();
//    
//    title = Tool.convertChar(title);  // 특수 문자 처리
//    content = Tool.convertChar(content); 
//    
//    contents_stockVO.setTitle(title);
//    contents_stockVO.setContent(content);  
//
//  }
//
//  mav.addObject("list",list); 
//  
//  return mav;
//}
  
  /**
   * 목록 + 검색 + 페이징 지원
   * http://localhost:9091/contents/list_by_cateno.do?cateno=1&word=&now_page=1
   * http://localhost:9091/contents/list_by_cateno.do?cateno=1&word=서울&now_page=1
   * 
   * @param cateno
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/contents_stock/list_by_cate_id.do", method = RequestMethod.GET)
  public ModelAndView list_by_cateno(Contents_StockVO contents_stockVO) {
    ModelAndView mav = new ModelAndView();
  
    // 검색 목록
    ArrayList<Contents_StockVO> list = contents_stockProc.list_by_cate_id_search_paging(contents_stockVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (Contents_StockVO vo : list) {
      String title = vo.getTitle();
      String content = vo.getContent();
      
      title = Tool.convertChar(title);  // 특수 문자 처리
      content = Tool.convertChar(content); 
      
      vo.setTitle(title);
      vo.setContent(content);  
  
    }
    
    mav.addObject("list", list);
  
    CategoryVO categoryVO = categoryProc.read(contents_stockVO.getCate_id());
    mav.addObject("categoryVO", categoryVO);
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("cate_id", contents_stockVO.getCate_id());
    hashMap.put("word", contents_stockVO.getWord());
  
    int search_count = this.contents_stockProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
    mav.addObject("search_count", search_count);
    
    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param cateno 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @param list_file 목록 파일명
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = contents_stockProc.pagingBox(contents_stockVO.getCate_id(), contents_stockVO.getNow_page(), contents_stockVO.getWord(), "list_by_cate_id.do", search_count);
    mav.addObject("paging", paging);
  
    // mav.addObject("now_page", now_page);
    
    mav.setViewName("/contents_stock/list_by_cate_id");  // /contents/list_by_cateno.jsp
  
    return mav;
  }
  
  /**
   * 목록 + 검색 + 페이징 지원
   * http://localhost:9091/contents/list_by_cateno.do?cateno=1&word=&now_page=1
   * http://localhost:9091/contents/list_by_cateno.do?cateno=1&word=서울&now_page=1
   * 
   * @param cateno
   * @param word
   * @param now_page
   * @return
   */
  @RequestMapping(value = "/contents_stock/list_by_cate_id_grid.do", method = RequestMethod.GET)
  public ModelAndView list_by_cate_id_grid(Contents_StockVO contents_stockVO) {
    ModelAndView mav = new ModelAndView();
  
    // 검색 목록
    ArrayList<Contents_StockVO> list = contents_stockProc.list_by_cate_id_search_paging(contents_stockVO);
    
    // for문을 사용하여 객체를 추출, Call By Reference 기반의 원본 객체 값 변경
    for (Contents_StockVO vo : list) {
      String title = vo.getTitle();
      String content = vo.getContent();
      
      title = Tool.convertChar(title);  // 특수 문자 처리
      content = Tool.convertChar(content); 
      
      vo.setTitle(title);
      vo.setContent(content);  
  
    }
    
    mav.addObject("list", list);
  
    CategoryVO categoryVO = categoryProc.read(contents_stockVO.getCate_id());
    mav.addObject("categoryVO", categoryVO);
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("cate_id", contents_stockVO.getCate_id());
    hashMap.put("word", contents_stockVO.getWord());
  
    int search_count = this.contents_stockProc.search_count(hashMap);  // 검색된 레코드 갯수 ->  전체 페이지 규모 파악
    mav.addObject("search_count", search_count);
    
    /*
     * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 현재 페이지: 11 / 22 [이전] 11 12 13 14 15 16 17
     * 18 19 20 [다음]
     * @param cateno 카테고리번호
     * @param now_page 현재 페이지
     * @param word 검색어
     * @param list_file 목록 파일명
     * @return 페이징용으로 생성된 HTML/CSS tag 문자열
     */
    String paging = contents_stockProc.pagingBox(contents_stockVO.getCate_id(), contents_stockVO.getNow_page(), contents_stockVO.getWord(), "list_by_cate_id_grid.do", search_count);
    mav.addObject("paging", paging);
  
    // mav.addObject("now_page", now_page);
    
    mav.setViewName("/contents_stock/list_by_cate_id_grid");  // /contents/list_by_cateno.jsp
  
    return mav;
  }
  
  /**
   * 조회
   * http://localhost:9092/contents_stock/read.do?contentsno=17
   * @return
   */
  @RequestMapping(value="/contents_stock/read.do", method = RequestMethod.GET)
  public ModelAndView read(int contents_num) { // int cateno = (int)request.getParameter("cateno");
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/contents_stock/read"); // /WEB-INF/views/contents/read.jsp
    
    Contents_StockVO contents_stockVO = this.contents_stockProc.read(contents_num);
    
    String title = contents_stockVO.getTitle();
    String content = contents_stockVO.getContent();
    
    title = Tool.convertChar(title);  // 특수 문자 처리
    content = Tool.convertChar(content); 
    
    contents_stockVO.setTitle(title);
    contents_stockVO.setContent(content);  
    
    long size1 = contents_stockVO.getSize1();
    String size1_label = Tool.unit(size1);
    contents_stockVO.setSize1_label(size1_label);
    
    mav.addObject("contents_stockVO", contents_stockVO);
    
    CategoryVO cateVO = this.categoryProc.read(contents_stockVO.getCate_id());
    mav.addObject(cateVO);
    
    return mav;
  }
  
  /**
   * 맵 등록/수정/삭제 폼
   * http://localhost:9091/contents_stock/map.do?contents_num=1
   * @return
   */
  @RequestMapping(value="/contents_stock/map.do", method=RequestMethod.GET )
  public ModelAndView map(int contents_num) {
    ModelAndView mav = new ModelAndView();

    Contents_StockVO contents_stockVO = this.contents_stockProc.read(contents_num); // map 정보 읽어 오기
    mav.addObject("contents_stockVO", contents_stockVO); // request.setAttribute("contentsVO", contentsVO);

    CategoryVO categoryVO = this.categoryProc.read(contents_stockVO.getCate_id()); // 그룹 정보 읽기
    mav.addObject("categoryVO", categoryVO); 

    mav.setViewName("/contents_stock/map"); // /WEB-INF/views/contents/map.jsp
        
    return mav;
  }
  
  /**
   * MAP 등록/수정/삭제 처리
   * http://localhost:9091/contents/map.do
   * @param contentsVO
   * @return
   */
  @RequestMapping(value="/contents_stock/map.do", method = RequestMethod.POST)
  public ModelAndView map_update(int contents_num, String map) {
    ModelAndView mav = new ModelAndView();
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("contents_num", contents_num);
    hashMap.put("map", map);
    
    this.contents_stockProc.map(hashMap);
    
    mav.setViewName("redirect:/contents_stock/read.do?contents_num=" + contents_num); 
    // /webapp/WEB-INF/views/contents/read.jsp
    
    return mav;
  }
  
  /**
   * Youtube 등록/수정/삭제 폼
   * http://localhost:9092/contents_stock/youtube.do?contentsno=1
   * @return
   */
  @RequestMapping(value="/contents_stock/youtube.do", method=RequestMethod.GET )
  public ModelAndView youtube(int contents_num) {
    ModelAndView mav = new ModelAndView();

    Contents_StockVO contents_stockVO = this.contents_stockProc.read(contents_num); // map 정보 읽어 오기
    mav.addObject("contents_stockVO", contents_stockVO); // request.setAttribute("contentsVO", contentsVO);

    CategoryVO categoryVO = this.categoryProc.read(contents_stockVO.getCate_id()); // 그룹 정보 읽기
    mav.addObject("categoryVO", categoryVO); 

    mav.setViewName("/contents_stock/youtube"); // /WEB-INF/views/contents/map.jsp
        
    return mav;
  }
  
  /**
   * Youtube 등록/수정/삭제 처리
   * http://localhost:9092/contents_stock/youtube.do
   * @param contentsno 글 번호
   * @return
   */
  @RequestMapping(value="/contents_stock/youtube.do", method = RequestMethod.POST)
  public ModelAndView youtube_update(int contents_num, String youtube) {
    ModelAndView mav = new ModelAndView();
    
    if (youtube.trim().length() > 0) {  // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
      youtube = Tool.youtubeResize(youtube, 640);  // youtube 영상의 크기를 width 기준 640 px로 변경
    }
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("contents_num", contents_num);
    hashMap.put("youtube", youtube);
    
    this.contents_stockProc.youtube(hashMap);
    
    mav.setViewName("redirect:/contents_stock/read.do?contents_num=" + contents_num); 
    // /webapp/WEB-INF/views/contents/read.jsp
    
    return mav;
  }
  
  /**
   * 수정 폼
   * http://localhost:9091/contents/update_text.do?contentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/contents_stock/update_text.do", method = RequestMethod.GET)
  public ModelAndView update_text(HttpSession session, int contents_num) {
    ModelAndView mav = new ModelAndView();
    
    if (admin_stockProc.isAdmin(session)) { // 관리자로 로그인한경우
      Contents_StockVO contents_stockVO = this.contents_stockProc.read(contents_num);
      mav.addObject("contents_stockVO", contents_stockVO);
      
      CategoryVO categoryVO = this.categoryProc.read(contents_stockVO.getCate_id());
      mav.addObject("categoryVO", categoryVO);
      
      mav.setViewName("/contents_stock/update_text"); // /WEB-INF/views/contents/update_text.jsp
      // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
      // mav.addObject("content", content);

    } else {
      mav.addObject("url", "/admin_stock/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/contents_stock/msg.do"); 
    }

    return mav; // forward
  }
  
  /**
   * 수정 처리
   * http://localhost:9091/contents/update_text.do?contentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/contents_stock/update_text.do", method = RequestMethod.POST)
  public ModelAndView update_text(HttpSession session, Contents_StockVO contents_stockVO) {
    ModelAndView mav = new ModelAndView();
    
    // System.out.println("-> word: " + contentsVO.getWord());
    
    if (this.admin_stockProc.isAdmin(session)) { // 관리자 로그인 확인
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("contents_num", contents_stockVO.getContents_num());
      hashMap.put("passwd", contents_stockVO.getPasswd());
      
      if (this.contents_stockProc.password_check(hashMap) == 1) { // 패스워드 일치
        this.contents_stockProc.update_text(contents_stockVO); // 글수정  
         
        // mav 객체 이용
        mav.addObject("contents_num", contents_stockVO.getContents_num());
        mav.addObject("cate_id", contents_stockVO.getCate_id());
        mav.setViewName("redirect:/contents_stock/read.do"); // 페이지 자동 이동
        
      } else { // 패스워드 불일치
        mav.addObject("code", "passwd_fail");
        mav.addObject("cnt", 0);    // 다시 시도라는걸 나오게 하기위해서 cnt=0으로 설정
        mav.addObject("url", "/contents_stock/msg"); // msg.jsp, redirect parameter 적용
        mav.setViewName("redirect:/contents_stock/msg.do");  // POST -> GET -> JSP 출력
      }
    } else { // 정상적인 로그인이 아닌 경우 로그인 유도
      mav.addObject("url", "/admin_stock/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/contents_stock/msg.do"); 
    }
    
    mav.addObject("now_page", contents_stockVO.getNow_page()); // POST -> GET: 데이터 분실이 발생함으로 다시하번 데이터 저장 ★
    
    // URL에 파라미터의 전송
    // mav.setViewName("redirect:/contents/read.do?contentsno=" + contentsVO.getContentsno() + "&cateno=" + contentsVO.getCateno());             
    
    return mav; // forward
  }
  
  /**
   * 파일 수정 폼
   * http://localhost:9091/contents/update_file.do?contentsno=1
   * 
   * @return
   */
  @RequestMapping(value = "/contents_stock/update_file.do", method = RequestMethod.GET)
  public ModelAndView update_file(HttpSession session, int contents_num) {
    ModelAndView mav = new ModelAndView();
    
    if(admin_stockProc.isAdmin(session)) {
      Contents_StockVO contents_stockVO = this.contents_stockProc.read(contents_num);
      mav.addObject("contents_stockVO", contents_stockVO);
      
      CategoryVO categoryVO = this.categoryProc.read(contents_stockVO.getCate_id());
      mav.addObject("categoryVO", categoryVO);
      
      mav.setViewName("/contents_stock/update_file"); // /WEB-INF/views/contents/update_file.jsp

    } else {
      mav.addObject("url", "/admin_stock/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/contents_stock/msg.do"); 
    }
    
    return mav; // forward
  }
  
  /**
   * 파일 수정 처리 http://localhost:9092/contents_stock/update_file.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents_stock/update_file.do", method = RequestMethod.POST)
  public ModelAndView update_file(HttpSession session, Contents_StockVO contents_stockVO) {
    ModelAndView mav = new ModelAndView();
    
    if (this.admin_stockProc.isAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      Contents_StockVO contents_stockVO_old = contents_stockProc.read(contents_stockVO.getContents_num());
      
      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = contents_stockVO_old.getFile1saved();  // 실제 저장된 파일명
      String thumb1 = contents_stockVO_old.getThumb1();       // 실제 저장된 preview 이미지 파일명
      long size1 = 0;
         
      String upDir =  Contents_Stock.getUploadDir(); // C:/kd/deploy/resort_v2sbm3c/contents/storage/
      
      Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
      Tool.deleteFile(upDir, thumb1);     // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------
          
      // -------------------------------------------------------------------
      // 파일 전송 시작
      // -------------------------------------------------------------------
      String file1 = "";          // 원본 파일명 image

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF' 
      //           value='' placeholder="파일 선택">
      MultipartFile mf = contents_stockVO.getFile1MF();
          
      file1 = mf.getOriginalFilename(); // 원본 파일명
      size1 = mf.getSize();  // 파일 크기
          
      if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
          thumb1 = Tool.preview(upDir, file1saved, 250, 200); 
        }
        
      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        file1="";
        file1saved="";
        thumb1="";
        size1=0;
      }
          
      contents_stockVO.setFile1(file1);
      contents_stockVO.setFile1saved(file1saved);
      contents_stockVO.setThumb1(thumb1);
      contents_stockVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------
          
      this.contents_stockProc.update_file(contents_stockVO); // Oracle 처리

      mav.addObject("contents_num", contents_stockVO.getContents_num());
      mav.addObject("cate_id", contents_stockVO.getCate_id());
      mav.setViewName("redirect:/contents_stock/read.do"); // request -> param으로 접근 전환
                
    } else {
      mav.addObject("url", "/admin_stock/login_need"); // login_need.jsp, redirect parameter 적용
      mav.setViewName("redirect:/contents_stock/msg.do"); // GET
    }

    // redirect하게되면 전부 데이터가 삭제됨으로 mav 객체에 다시 저장
    mav.addObject("now_page", contents_stockVO.getNow_page());
    
    return mav; // forward
  }
  
  /**
   * 파일 삭제 폼
   * http://localhost:9092/contents_stock/update_file.do?contents_num=1
   * 
   * @return
   */
  @RequestMapping(value = "/contents_stock/delete.do", method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int contents_num) {
    ModelAndView mav = new ModelAndView();
    
    if(admin_stockProc.isAdmin(session)) {
      Contents_StockVO contents_stockVO = this.contents_stockProc.read(contents_num);
      mav.addObject("contents_stockVO", contents_stockVO);
      
      CategoryVO categoryVO = this.categoryProc.read(contents_stockVO.getCate_id());
      mav.addObject("categoryVO", categoryVO);
      
      mav.setViewName("/contents_stock/delete"); // /WEB-INF/views/contents/update_file.jsp

    } else {
      mav.addObject("url", "/admin_stock/login_need"); // /WEB-INF/views/admin/login_need.jsp
      mav.setViewName("redirect:/contents_stock/msg.do"); 
    }
    
    return mav; // forward
  }

  
  /**
   * 삭제 처리 http://localhost:9092/contents_stock/delete.do
   * 
   * @return
   */
  @RequestMapping(value = "/contents_stock/delete.do", method = RequestMethod.POST)
  public ModelAndView delete(Contents_StockVO contents_stockVO) {
    ModelAndView mav = new ModelAndView();
    
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    Contents_StockVO contents_stockVO_read = contents_stockProc.read(contents_stockVO.getContents_num());
        
    String file1saved = contents_stockVO_read.getFile1saved();
    String thumb1 = contents_stockVO_read.getThumb1();
    
    String uploadDir = Contents_Stock.getUploadDir();
    Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
    Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
        
    this.contents_stockProc.delete(contents_stockVO.getContents_num()); // DBMS 삭제
        
    // -------------------------------------------------------------------------------------
    // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
    // -------------------------------------------------------------------------------------    
    // 마지막 페이지의 마지막 10번째 레코드를 삭제후
    // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
    // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
    int now_page = contents_stockVO.getNow_page();
    
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("cate_id", contents_stockVO.getCate_id());
    hashMap.put("word", contents_stockVO.getWord());
    
    if (contents_stockProc.search_count(hashMap) % Contents_Stock.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }
    // -------------------------------------------------------------------------------------

    mav.addObject("cate_id", contents_stockVO.getCate_id());
    mav.addObject("now_page", now_page);
    mav.setViewName("redirect:/contents_stock/list_by_cate_id.do"); 
    
    return mav;
  }   
  
//http://localhost:9092/contents_stock/delete_by_cate_id.do?cate_id=1
 // 파일 삭제 -> 레코드 삭제
 @RequestMapping(value = "/contents_stock/delete_by_cate_id.do", method = RequestMethod.GET)
 public String delete_by_cate_id(int cate_id) {
   ArrayList<Contents_StockVO> list = this.contents_stockProc.list_by_cate_id(cate_id);
   
   for(Contents_StockVO contents_stockVO : list) {
     // -------------------------------------------------------------------
     // 파일 삭제 시작
     // -------------------------------------------------------------------
     String file1saved = contents_stockVO.getFile1saved();
     String thumb1 = contents_stockVO.getThumb1();
     
     String uploadDir = Contents_Stock.getUploadDir();
     Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
     Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
     // -------------------------------------------------------------------
     // 파일 삭제 종료
     // -------------------------------------------------------------------
   }
   
   int cnt = this.contents_stockProc.delete_by_cate_id(cate_id);
   System.out.println("-> count: " + cnt);
   
   return "";
 
 }
  

}