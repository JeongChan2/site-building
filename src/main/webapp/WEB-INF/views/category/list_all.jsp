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

<div class='title_line'>카테고리</div>


<aside class="aside_right">
    <a href="./create.do?cate_id=${categoryVO.cate_id }">등록</a>
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</a>
</aside>


<div class="menu_line"></div>


<form name='frm' method="post" action='/category/create.do'>
      <div style="text-align: center;">
        <label>카테고리 이름</label>
        <input class="" type="text" name="cate_name" value="" required="required" autofocus="autofocus" style="width: 50%;">
        <button type="submit" class="btn btn-info btn-sm" style="color: white; height: 28px; margin-bottom: 5px;">등록</button>
        <button type="button" onclick="location.href='./list_all.do'" class="btn btn-info btn-sm" style="color: white; height: 28px; margin-bottom: 5px;">목록</button>
      </div>
    </form>
    
    
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
        <c:set var="seqno" value="${categoryVO.seqno }"></c:set>
        
        <tr>
        <td class="td_bs">${seqno }</td>
        <td class="td_bs"><a href="/contents_stock/list_by_cate_id.do?cate_id=${cate_id }&now_page=1" style="display: block;">${categoryVO.cate_name }</a></td>
        <td class="td_bs">${categoryVO.cate_cnt }</td>
        <td class="td_bs">${categoryVO.rdate.substring(0,10) }</td>
        <td class="td_bs">
        
        <a href="../contents_stock/create.do?cate_id=${cate_id }" title="등록">
              <img src="/category/images/create.png" class="icon">
            </a>
        
        <c:choose>
          <c:when test="${categoryVO.visible == 'Y' }">
            <a href="./update_visible_n.do?cate_id=${cate_id }" title="카테고리 공개설정">
              <img src="/category/images/show.png" class="icon">
            </a>
          </c:when>
          <c:otherwise>
            <a href="./update_visible_y.do?cate_id=${cate_id }" title="카테고리 비공개설정">
              <img src="/category/images/hide.png" class="icon">
            </a>
          </c:otherwise>
        </c:choose>
          <a href="./update_seqno_forward.do?cate_id=${cate_id }" title="우선 순위 높임"><img src="/category/images/decrease.png" class="icon"></a>
          <a href="./update_seqno_backward.do?cate_id=${cate_id }" title="우선 순위 낮춤"><img src="/category/images/increase.png" class="icon"></a>
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