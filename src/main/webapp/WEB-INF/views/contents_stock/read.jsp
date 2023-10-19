<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="cate_name" value="${categoryVO.cate_name }"></c:set>

<c:set var="cate_id" value="${contents_stockVO.cate_id }"></c:set>
<c:set var="contents_num" value="${contents_stockVO.contents_num }"></c:set>
<c:set var="thumb1" value="${contents_stockVO.thumb1 }"></c:set>
<c:set var="file1saved" value="${contents_stockVO.file1saved }"></c:set>
<c:set var="title" value="${contents_stockVO.title }"></c:set>
<c:set var="rdate" value="${contents_stockVO.rdate }"></c:set>
<c:set var="youtube" value="${contents_stockVO.youtube }"></c:set>
<c:set var="map" value="${contents_stockVO.map }"></c:set>
<c:set var="word" value="${contents_stockVO.word }"></c:set>
<c:set var="file1" value="${contents_stockVO.file1 }"></c:set>
<c:set var="file1saved" value="${contents_stockVO.file1saved }"></c:set>
<c:set var="size1_label" value="${contents_stockVO.size1_label }"></c:set>
<c:set var="content" value="${contents_stockVO.content }"></c:set>


<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Stock world</title>
 
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
   
</head> 
 
<body>
<c:import url="/menu/top.do" />
  <DIV class='title_line'><A href="./list_by_cate_id.do?cate_id=${cate_id }" class='title_link'>${cate_name }</A></DIV>

  <ASIDE class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <%--
      http://localhost:9092/contents_stock/create.do?cate_id=1
      http://localhost:9092/contents_stock/create.do?cate_id=2
      http://localhost:9092/contents_stock/create.do?cate_id=3
      --%>
      <a href="./create.do?cate_id=${cate_id }">등록</a>
      <span class='menu_divide' >│</span>
      <a href="./update_text.do?contents_num=${contents_num}&now_page=${param.now_page}&word=${param.word }">글 수정</a>
      <span class='menu_divide' >│</span>
      <a href="./update_file.do?contents_num=${contents_num}&now_page=${param.now_page}">파일 수정</a>  
      <span class='menu_divide' >│</span>
      <a href="./map.do?cate_id=${cate_id }&contents_num=${contents_num}">지도</a>
      <span class='menu_divide' >│</span>
      <a href="./youtube.do?cate_id=${cate_id }&contents_num=${contents_num}">Youtube</a>
      <span class='menu_divide' >│</span>
      <a href="./delete.do?contents_num=${contents_num}&now_page=${param.now_page}&cate_id=${cate_id}">삭제</a>  
      <span class='menu_divide' >│</span>
    </c:if>

    <A href="javascript:location.reload();">새로고침</A>
	  <span class='menu_divide' >│</span>    
	  <A href="./list_by_cate_id.do?cate_id=${contents_stockVO.cate_id }&now_page=${param.now_page == null ? 1 : param.now_page }&word=${param.word }">목록형</A>    
	  <span class='menu_divide' >│</span>
	  <A href="./list_by_cate_id_grid.do?cate_id=${contents_stockVO.cate_id }&now_page=${param.now_page == null ? 1 : param.now_page }&word=${param.word }">갤러리형</A>
  </ASIDE> 
  
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_cate_id.do'>
      <input type='hidden' name='cate_id' value='${cate_id }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }' class='input_word'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' class='input_word'>
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-info btn-sm'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' class='btn btn-info btn-sm' 
                    onclick="location.href='./list_by_cate_id.do?cate_id=${cate_id}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style="width: 100%; word-break: break-all;">
          <c:choose>
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <%-- /static/contents/storage/ --%>
              <img src="/contents/storage/${file1saved }" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <img src="/contents_stock/images/none1.png" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:otherwise>
          </c:choose>

          <span style="font-size: 1.5em; font-weight: bold;">${title }</span>
          <span style="font-size: 1em;"> ${rdate }</span><br>
          ${content }
        </DIV>
      </li>
      
      <c:if test="${youtube.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style="text-align: center;">
            ${youtube }
          </DIV>
        </li>
      </c:if>
      
      <c:if test="${map.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style='text-align: center; width:640px; height: 360px; margin: 0px auto;'>
            ${map }
          </DIV>
        </li>
      </c:if>
      
      <li class="li_none" style="clear: both;">
        <DIV style='text-decoration: none;'>
          <br>
          검색어(키워드): ${word }
        </DIV>
      </li>
      
      <li class="li_none">
        <div>
          <c:if test="${file1.trim().length() > 0 }">
            첨부 파일: <A href='/download?dir=/contents/storage&filename=${file1saved}&downname=${file1}'>${file1}</A> (${size1_label}) 
            <A href='/download?dir=/contents/storage&filename=${file1saved}&downname=${file1}'><img src="/contents_stock/images/download.png"></a>
          </c:if>
        </div>
      </li>
      
    </ul>
  </fieldset>

</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>