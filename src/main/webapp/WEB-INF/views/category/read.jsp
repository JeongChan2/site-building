<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dev.mvc.category.CategoryVO" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/category/read.do</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  
</head>
<body>
<jsp:include page="../menu/top.jsp" flush='false' />

<div class="title_line">카테고리 조회</div>

<%
  CategoryVO categoryVO = (CategoryVO)request.getAttribute("categoryVO");
%>

<div class="container mt-3">
    <ul class="list-group list-group-flush">
      <li class="list-group-item">번호: <%=categoryVO.getCate_id() %></li>
      <li class="list-group-item">이름: <%=categoryVO.getCate_name() %></li>
      <li class="list-group-item">등록 글수: <%=categoryVO.getCate_cnt() %></li>
      <li class="list-group-item">등록일: <%=categoryVO.getRdate() %></li>
    </ul>
  </div>

	<div class="content_body_bottom">
		<button type="button" onclick="location.href='./create.do'"
			class="btn btn-info btn-sm" style="color: white">등록</button>
		<button type="button" onclick="location.href='./list_all.do'"
			class="btn btn-info btn-sm" style="color: white">목록</button>
	</div>



	<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>