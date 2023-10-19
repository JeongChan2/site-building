<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Stock world</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
   
</head>
 
<body>
<c:import url="/menu/top.do" />
 
	<DIV class='title_line'>${categoryVO.cate_name } > 글 등록</DIV>
	
  <aside class="aside_right">
    <a href="./create.do?cate_id=${categoryVO.cate_id }">등록</A>
    <span class='menu_divide' >│</span>
    <a href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>
    <a href="./list_by_cate_id_search_paging.do?cate_id=${categoryVO.cate_id }">기본 목록형</A>    
    <span class='menu_divide' >│</span>
    <a href="./list_by_cate_id_grid.do?cate_id=${categoryVO.cate_id }">갤러리형</A>
  </aside> 
  
  <div style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_cate_id.do'>
      <input type='hidden' name='cate_id' value='${categoryVO.cate_id }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }' class='input_word'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' class='input_word'>
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-info btn-sm' style='color: white;'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' class='btn btn-info btn-sm' 
                    onclick="location.href='./list_by_cate_id.do?cate_id=${categoryVO.cate_id}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </div>
  
  <DIV class='menu_line'></DIV>
  
  <FORM name='frm' method='POST' action='./create.do' enctype="multipart/form-data">
    <input type="hidden" name="cate_id" value="${param.cate_id }">
    
    <div>
       <label>제목</label>
       <input type='text' name='title' value='서울 우수 야경' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <div>
       <label>내용</label>
       <textarea name='content' required="required" class="form-control" rows="12" style='width: 100%;'>한강 야경보며 멍때리기</textarea>
    </div>
    <div>
       <label>검색어</label>
       <input type='text' name='word' value='서울,야경,힐링,산책,한강,전철,여행,응봉산,응봉역' required="required" 
                 class="form-control" style='width: 100%;'>
    </div>   
    <div>
       <label>이미지</label>
       <input type='file' class="form-control" name='file1MF' id='file1MF' 
                 value='' placeholder="파일 선택">
    </div>   
    <div>
       <label>패스워드</label>
       <input type='password' name='passwd' value='1234' required="required" 
                 class="form-control" style='width: 50%;'>
    </div>   
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-primary">등록</button>
      <button type="button" onclick="location.href='./list_by_cate_id.do?cate_id=${param.cate_id}'" class="btn btn-primary">목록</button>
    </div>
  
  </FORM>
	
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>