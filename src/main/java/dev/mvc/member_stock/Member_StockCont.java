package dev.mvc.member_stock;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class Member_StockCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private Member_StockProcInter memberProc = null;
  
  public Member_StockCont(){
    System.out.println("-> MemberCont created.");
  }
  
  // http://localhost:9091/member/checkID.do?id=user1
  /**
  * ID 중복 체크, JSON 출력
  * @return {"cnt":0} or {"cnt":1}
  */
  @ResponseBody
  @RequestMapping(value="/member/checkID.do", method=RequestMethod.GET ,
                         produces = "text/plain;charset=UTF-8" )
  public String checkID(String id) {
    int cnt = this.memberProc.checkID(id);
   
    JSONObject json = new JSONObject();
    json.put("cnt", cnt); // 키와 값 = HashMap
   
    return json.toString(); // {"cnt":1} 
  }

  // http://localhost:9091/member/create.do
  /**
  * 등록 폼
  * @return
  */
  @RequestMapping(value="/member/create.do", method=RequestMethod.GET )
  public ModelAndView create() {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("/member/create"); // /WEB-INF/views/member/create.jsp
   
    return mav; // forward
  }

}