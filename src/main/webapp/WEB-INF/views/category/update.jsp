<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dev.mvc.category.CategoryVO" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/category/update.do</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  
</head>
<body>
<%
  CategoryVO categoryVO = (CategoryVO)request.getAttribute("categoryVO");
int cate_id = categoryVO.getCate_id();
%>
<jsp:include page="../menu/top.jsp" flush='false' />
<div class='title_line'>카테고리 수정</div>

	<div class='content_body'>
	
		<form name='frm' method="post" action='/category/update.do'>
		<input type='hidden' name='cate_id' value="<%=cate_id %>">
			<div>
				<label>카테고리 이름</label> <input class="form-control form-control-sm"
					type="text" name="name" value="<%=categoryVO.getCate_name() %>" required="required"
					class="form-control form-control-sm" autofocus="autofocus" style="width: 50%">
			</div>
			
			<div>
        <label>글수</label> <input class="form-control form-control-sm"
          type="text" name="cate_cnt" value="<%=categoryVO.getCate_cnt() %>" required="required"
          autofocus="autofocus" style="width: 50%">
      </div>
			
			<div class="content_body_bottom">
				<button type="submit" class="btn btn-info btn-sm"
					style="color: white">저장</button>
				<button type="button" class="btn btn-info btn-sm"
					onclick="history.back();" style="color: white">취소</button>
			</div>
		</form>
		
	</div>

		<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
</html>