<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/contents_stock/list_all.do</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  
</head>

<body>
<c:import url="/menu/top.do" />

<div class='title_line'>카테고리</div>

<aside class="aside_right">
    <a href="javascript:location.reload();">새로고침</a>
</aside>
<div class="menu_line"></div>
    
  <table class="table table-hover">
    <colgroup>
      <col style="width: 10%;"></col>
      <col style="width: 80%;"></col>
      <col style="width: 10%;"></col>
    </colgroup>
    
    <thead>
      <tr>
        <th style='text-align: center;'>파일</th>
        <th style='text-align: center;'>제목</th>
        <th style='text-align: center;'>기타</th>
      </tr>
    </thead>
    
    <tbody>
    
    
      <!-- ArrayList<CateVO> list = (ArrayList<CateVO>)request.getAttribute("list"); -->

      
      <c:forEach var="contents_stockVO" items="${list }" varStatus="info">
      
        <c:set var="contents_num" value="${contents_stockVO.contents_num }"></c:set>
        <c:set var="cate_id" value="${contents_stockVO.cate_id }"></c:set>
        <c:set var="thumb1" value="${contents_stockVO.thumb1 }"></c:set>
        
        <tr onclick="location.href='./read.do?contents_num=${contents_num}'" style='cursor: pointer;'>
          <td class="td_bs">
           <c:choose>
             <%-- <img src="/contents/storage/${contentsVO.thumb1 }"> --%>
              <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
                <%-- registry.addResourceHandler("/contents/storage/**").addResourceLocations("file:///" +  Contents.getUploadDir()); --%>
                <img src="/contents/storage/${thumb1 }" style="width: 120px; height: 90px;">
              </c:when>
              <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/contents/images/none1.png -->
                <IMG src="/contents_stock/images/none1.png" style="width: 120px; height: 90px;">
              </c:otherwise>
            </c:choose>
           
           
          </td>
          <td class="td_bs_left">
           <span style="font-weight: bold;">${contents_stockVO.title }</span><br>
           
           <c:choose>
             <c:when test="${contents_stockVO.content.length() > 160 }">
               ${contents_stockVO.content.substring(0, 160) } ...
             </c:when>
             <c:otherwise>
               ${contents_stockVO.content }
             </c:otherwise>
           </c:choose>
           
           (${contents_stockVO.rdate.substring(0,16) })
          </td>
          <td class="td_bs">
            <a href="/contents_stock/map.do?cate_id=${cate_id }&contents_num=${contents_num }&now_page=1" title="지도 설정"><img src="/contents_stock/images/map.png" class="icon"></a>
            <a href="/contents_stock/youtube.do?cate_id=${cate_id }&contents_num=${contents_num }&now_page=1" title="Youtube 설정"><img src="/contents_stock/images/youtube.png" class="icon"></a>
            <a href="/contents_stock/delete.do?cate_id=${cate_id }&contents_num=${contents_num }&now_page=1" title="삭제"><img src="/contents_stock/images/delete.png" class="icon"></a>
          </td>
        </tr>
      
      </c:forEach>
      
    </tbody>
    
</table>


</div>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>