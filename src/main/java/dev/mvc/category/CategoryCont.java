package dev.mvc.category;

import java.util.ArrayList;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dev.mvc.admin_stock.Admin_StockProcInter;
import dev.mvc.contents_stock.Contents_Stock;
import dev.mvc.contents_stock.Contents_StockProcInter;
import dev.mvc.contents_stock.Contents_StockVO;
import dev.mvc.tool.Tool;

@Controller
public class CategoryCont {
  
  @Autowired // 객체를 만들어 자동으로 할당해라
  @Qualifier("dev.mvc.category.CategoryProc")       // 현재 cateDAO랑 다른점 이거 하나
  private CategoryProcInter categoryProc;
  
  @Autowired // 객체를 만들어 자동으로 할당해라
  @Qualifier("dev.mvc.admin_stock.Admin_StockProc")       // 현재 cateDAO랑 다른점 이거 하나
  private Admin_StockProcInter admin_stockProc;
  
  @Autowired // 객체를 만들어 자동으로 할당해라
  @Qualifier("dev.mvc.contents_stock.Contents_StockProc")
  private Contents_StockProcInter contents_stockProc;
  
  public CategoryCont() {
    System.out.println("-> CategoryCont created.");
  }
  
//  // FORM 출력, http://localhost:9092/cate/create.do
//  @RequestMapping(value = "/cate/create.do", method = RequestMethod.GET)
//  @ResponseBody // 단순 문자열로 출력 jsp 파일명 조합이 발생하지 않음
//  public String create() {
//    return "GET 방식 FORM 출력";
//  }
  
//@RequestMapping(value = "/cate/create.do", method = RequestMethod.GET)
//public String create() {
//  
//  return "/cate/create";
//}
  
//FORM 출력, http://localhost:9092/cate/create.do
  @RequestMapping(value = "/category/create.do", method = RequestMethod.GET)
  public ModelAndView create(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if(this.admin_stockProc.isAdmin(session) == true) {
      mav.setViewName("/category/create"); // /WEB-INF/views/cate/create.jsp
    } else {
      mav.setViewName("/admin_stock/login_need");
    }

    return mav;
  }
  
//FORM 출력, http://localhost:9092/cate/create.do
  @RequestMapping(value = "/category/create.do", method = RequestMethod.POST)
  public ModelAndView create(CategoryVO categoryVO) { // 자동으로 cateVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    
    int cnt = this.categoryProc.create(categoryVO);
    System.out.println("-> cnt: " + cnt);
    
    if(cnt == 1) {
      // mav.addObject("code","create_success");
      // mav.addObject("name",cateVO.getName());
      mav.setViewName("redirect:/category/list_all.do"); // 주소 자동 이동
    } else {
      mav.addObject("code","create_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/cate/msg.jsp
    }
    
    mav.addObject("cnt",cnt); // request.setAttribute("cnt",cnt);
    
    return mav;
  }
  
  @RequestMapping(value = "/category/list_all.do", method = RequestMethod.GET)
  public ModelAndView list_all(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    if(this.admin_stockProc.isAdmin(session) == true) {
      mav.setViewName("/category/list_all"); // /WEB-INF/views/category/list_all.jsp
      
      ArrayList<CategoryVO> list = this.categoryProc.list_all();
      mav.addObject("list",list);
    } else {
      mav.setViewName("/admin_stock/login_need");
    }

    return mav;
  }
  /**
   * 조회
   * http://localhost:9092/category/read.do
   * @return
   */
  @RequestMapping(value = "/category/read.do", method = RequestMethod.GET)
  public ModelAndView read(int cate_id) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/category/read"); // /WEB-INF/views/category/read.jsp
    
    CategoryVO categoryVO = this.categoryProc.read(cate_id);
    mav.addObject("categoryVO",categoryVO);
    
    return mav;
  }
  
  /**
   * 수정
   * http://localhost:9092/category/update.do?cate_id=2
   * @param cateVO 수정할 내용
   * @return 수정된 레코드 갯수
   */
  @RequestMapping(value="/category/update.do",method = RequestMethod.GET)
  public ModelAndView update(HttpSession session, int cate_id) {  // int cate_id = (int)request.getParameter("cate_id"); 이걸 spring이 자동으로 해준다.
    ModelAndView mav = new ModelAndView();
    if(this.admin_stockProc.isAdmin(session) == true) {
      mav.setViewName("/category/list_all_update");// /WEB-INF/views/cate/update.jsp
      
      CategoryVO categoryVO = this.categoryProc.read(cate_id);
      mav.addObject("categoryVO",categoryVO);
      
      ArrayList<CategoryVO> list = this.categoryProc.list_all();
      mav.addObject("list",list);
    } else {
      mav.setViewName("/admin_stock/login_need");
    }
    return mav;
  }
  
  /**
   * 수정폼
   * http://localhost:9092/category/update.do
   * @return
   */
  @RequestMapping(value = "/category/update.do", method = RequestMethod.POST)
  public ModelAndView update(CategoryVO categoryVO) { // 자동으로 cateVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    
    int cnt = this.categoryProc.update(categoryVO);
    System.out.println("-> cnt: " + cnt);
    
    if(cnt == 1) {
//      mav.addObject("code","update_success");
//      mav.addObject("name",categoryVO.getName());
      mav.setViewName("redirect:/category/list_all.do"); // /WEB-INF/views/cate/msg.jsp
    } else {
      mav.addObject("code","update_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/cate/msg.jsp
    }
    
    mav.addObject("cnt",cnt); // request.setAttribute("cnt",cnt);
    
    return mav;
  }
  
  /**
   * 삭제폼
   * http://localhost:9092/category/delete.do
   * @param cateVO 수정할 내용
   * @return 수정된 레코드 갯수
   */
  @RequestMapping(value="/category/delete.do",method = RequestMethod.GET)
  public ModelAndView delete(HttpSession session, int cate_id) {  // int cate_id = (int)request.getParameter("cate_id"); 이걸 spring이 자동으로 해준다.
    ModelAndView mav = new ModelAndView();
    if(this.admin_stockProc.isAdmin(session) == true) {
      mav.setViewName("/category/list_all_delete");// /WEB-INF/views/category/list_all_delete.jsp
      
      CategoryVO categoryVO = this.categoryProc.read(cate_id);
      mav.addObject("categoryVO",categoryVO);
      
      ArrayList<CategoryVO> list = this.categoryProc.list_all();
      mav.addObject("list",list);
      
   // 특정 카테고리에 속한 레코드 갯수를 리턴
      int count_by_cate_id = this.contents_stockProc.count_by_cate_id(cate_id);
      mav.addObject("count_by_cate_id", count_by_cate_id);
      
    } else {
      mav.setViewName("/admin_stock/login_need");
    }

    return mav;
  }
  
//삭제 처리, 수정 처리를 복사하여 개발
 // 자식 테이블 레코드 삭제 -> 부모 테이블 레코드 삭제
 /**
  * 카테고리 삭제
  * @param session
  * @param cate_id 삭제할 카테고리 번호
  * @return
  */
 @RequestMapping(value="/category/delete.do", method=RequestMethod.POST)
 public ModelAndView delete_proc(HttpSession session, int cate_id) { // <form> 태그의 값이 자동으로 저장됨
//   System.out.println("-> cateno: " + cateVO.getCateno());
//   System.out.println("-> name: " + cateVO.getName());
   
   ModelAndView mav = new ModelAndView();
   
   if (this.admin_stockProc.isAdmin(session) == true) {
     ArrayList<Contents_StockVO> list = this.contents_stockProc.list_by_cate_id(cate_id); // 자식 레코드 목록 읽기
     
     for(Contents_StockVO contents_stockVO : list) { // 자식 레코드 관련 파일 삭제
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
     
     this.contents_stockProc.delete_by_cate_id(cate_id); // 자식 레코드 삭제     
           
     int cnt = this.categoryProc.delete(cate_id); // 카테고리 삭제
     
     if (cnt == 1) {
       mav.setViewName("redirect:/category/list_all.do");       // 자동 주소 이동, Spring 재호출
       
     } else {
       mav.addObject("code", "delete_fail");
       mav.setViewName("/category/msg"); // /WEB-INF/views/category/msg.jsp
     }
     
     mav.addObject("cnt", cnt);
     
   } else {
     mav.setViewName("/admin_stock/login_need"); // /WEB-INF/views/admin/login_need.jsp
   }
   
   return mav;
 }
  
  
  
  
  

  /**
   * 수정폼 <!-- 우선순위 높임, 10등 -> 1등 -->
   * http://localhost:9092/category/update_seqno_forward.do?cate_id=1
   * @param cateVO 수정할 내용
   * @return
   */
  @RequestMapping(value = "/category/update_seqno_forward.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_foward(int cate_id) { // 자동으로 cateVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    
    int cnt = this.categoryProc.update_seqno_forward(cate_id);
    System.out.println("-> cnt: " + cnt);
    
    if(cnt == 1) {
//      mav.addObject("code","update_success");
//      mav.addObject("name",categoryVO.getName());
      mav.setViewName("redirect:/category/list_all.do"); 
    } else {
      mav.addObject("code","update_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/cate/msg.jsp
    }
    
    mav.addObject("cnt",cnt); // request.setAttribute("cnt",cnt);
    
    return mav;
  }
  
  /**
   * 수정폼 <!-- 우선순위 높임, 10등 -> 1등 -->
   * http://localhost:9092/cate/update.seqno_forward.do?cate_id=1
   * @param cateVO 수정할 내용
   * @return
   */
  @RequestMapping(value = "/category/update_seqno_backward.do", method = RequestMethod.GET)
  public ModelAndView update_seqno_backward(int cate_id) { // 자동으로 cateVO 객체가 생성되고 폼의 값이 할당됨
    ModelAndView mav = new ModelAndView();
    
    
    int cnt = this.categoryProc.update_seqno_backward(cate_id);
    System.out.println("-> cnt: " + cnt);
    
    if(cnt == 1) {
//      mav.addObject("code","update_success");
//      mav.addObject("name",cateVO.getName());
      mav.setViewName("redirect:/category/list_all.do"); // /WEB-INF/views/category/msg.jsp
    } else {
      mav.addObject("code","update_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/cate/msg.jsp
    }
    
    mav.addObject("cnt",cnt); // request.setAttribute("cnt",cnt);
    
    return mav;
  }
  
  
  
  
  /**
   * 수정폼 <!-- 카테고리 공개 설정 -->
   * http://localhost:9092/cate/update_visible_y.do?cate_id=1
   * @param cate_id 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value = "/category/update_visible_y.do", method = RequestMethod.GET)
  public ModelAndView update_visible_y(int cate_id) {
    ModelAndView mav = new ModelAndView();
    
    
    int cnt = this.categoryProc.update_visible_y(cate_id);
    System.out.println("-> cnt: " + cnt);
    
    if(cnt == 1) {
//      mav.addObject("code","update_success");
//      mav.addObject("name",categoryVO.getName());
      mav.setViewName("redirect:/category/list_all.do"); // /WEB-INF/views/category/msg.jsp
    } else {
      mav.addObject("code","update_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/category/msg.jsp
    }
    
    mav.addObject("cnt",cnt); // request.setAttribute("cnt",cnt);
    
    return mav;
  }
  
  
  
  
  /**
   * 수정폼 <!-- 카테고리 공개 설정 -->
   * http://localhost:9092/category/update_visible_n.do?cate_id=1
   * @param cate_id 수정할 레코드 PK 번호
   * @return
   */
  @RequestMapping(value = "/category/update_visible_n.do", method = RequestMethod.GET)
  public ModelAndView update_visible_n(int cate_id) {
    ModelAndView mav = new ModelAndView();
    
    
    int cnt = this.categoryProc.update_visible_n(cate_id);
    System.out.println("-> cnt: " + cnt);
    
    if(cnt == 1) {
//      mav.addObject("code","update_success");
//      mav.addObject("name",categoryVO.getName());
      mav.setViewName("redirect:/category/list_all.do"); // /WEB-INF/views/category/msg.jsp
    } else {
      mav.addObject("code","update_fail");
      mav.setViewName("/category/msg"); // /WEB-INF/views/category/msg.jsp
    }
    
    mav.addObject("cnt",cnt); // request.setAttribute("cnt",cnt);
    
    return mav;
  }
  
  

  
  // FORM 데이터 처리
}
