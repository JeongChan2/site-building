<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/category/list_all.do</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  
</head>

<body>
<c:import url="/menu/top.do" />

<div class='title_line'>카테고리 수정</div>


<aside class="aside_right">
    <a href="./create.do?cate_id=${categoryVO.cate_id }">등록</a>
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>
</aside>

<div class="menu_line"></div>


  <div id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <form name='frm_delete' id='frm_delete' method='post' action='./delete.do'>
      <input type="hidden" name="cate_id" value="${param.cate_id }"> <%-- 삭제할 카테고리 번호 --%>

      <c:choose>
        <c:when test="${count_by_cate_id >= 1 }"> <%-- 자식 레코드가 있는 상황 --%>
          <div class="msg_warning">
            관련 자료 ${count_by_cate_id } 건이 발견되었습니다.<br>
            관련 자료와 카테고리를 모두 삭제하시겠습니까?
          </div>
            
          <label>관련 카테고리 이름</label>: ${categoryVO.cate_name }
          <a href="../contents_stock/list_by_cate_id.do?cate_id=${categoryVO.cate_id }&now_page=1" title="관련 카테고리로 이동"><img src='/category/images/link.png'></a>
          &nbsp;      
          <button type="submit" id='submit' class='btn btn-danger btn-sm' style='height: 28px; margin-bottom: 5px;'>관련 자료와 함게 카테고리 삭제</button>
          
        </c:when>
        <c:otherwise> <%-- 자식 레코드가 없는 상황 --%>
          <div class="msg_warning">카테고리를 삭제하면 복구 할 수 없습니다.</div>
          <label>카테고리 이름</label>: ${categoryVO.cate_name }
      
          <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>          
        </c:otherwise>
      </c:choose>      

      <button type="button" onclick="location.href='/category/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </form>
  </div>
    
    
    
<table class="table table-hover">
  <colgroup>
      <col style='width: 10%;'/>
      <col style='width: 40%;'/>
      <col style='width: 10%;'/>    
      <col style='width: 20%;'/>
      <col style='width: 20%;'/>
    </colgroup>
    
    <thead>
      <tr>
        <th class="th_bs">순서</th>
        <th class="th_bs">카테고리 이름</th>
        <th class="th_bs">자료수</th>
        <th class="th_bs">등록일</th>
        <th class="th_bs">기타</th>
      </tr>
    </thead>
    
    <tbody>
    
    
      <!-- ArrayList<CateVO> list = (ArrayList<CateVO>)request.getAttribute("list"); -->

      
      <c:forEach var="categoryVO" items="${list }" varStatus="info">
      
        <c:set var="cate_id" value="${categoryVO.cate_id }"></c:set>
        
        <tr>
        <td class="td_bs">${info.count }</td>
        <td class="td_bs"><a href="./read.do?cate_id=${cate_id }" style="display: block;">${categoryVO.cate_name }</a></td>
        <td class="td_bs">${categoryVO.cate_cnt }</td>
        <td class="td_bs">${categoryVO.rdate.substring(0,10) }</td>
        <td class="td_bs">
          <img src="/category/images/show.png" class="icon">
          <img src="/category/images/decrease.png" class="icon">
          <img src="/category/images/increase.png" class="icon">
          <a href="./update.do?cate_id=${cate_id }"><img src="/category/images/update.png" class="icon"></a>
          <a href="./delete.do?cate_id=${cate_id }"><img src="/category/images/delete.png" class="icon"></a>
          <!-- <img src="/category/images/hide.png" class="icon"> -->
        </td>
      </tr>
      
      </c:forEach>
      
    </tbody>
    
</table>


</div>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>