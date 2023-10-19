<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
  
</head>
<body>
<c:import url="/menu/top.do" />
  
  <div class="title_line">카테고리 등록</div>

  <div class='content_body'>
  
  
    <form name='frm' method="post" action='/cate/create.do'>
      <div>
        <label>카테고리 이름</label>
        <input class="form-control form-control-sm" type="text" name="cate_name" value="" required="required" autofocus="autofocus" style="width: 50%">
      </div>
      <div class="content_body_bottom">
        <button type="submit" class="btn btn-info btn-sm" style="color: white">등록</button>
        <button type="button" onclick="location.href='./list_all.do'" class="btn btn-info btn-sm" style="color: white">목록</button>
      </div>
    </form>

    
  </div>
 
<jsp:include page="../menu/bottom.jsp" flush='false' /> 
</body>
</html>