<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0, maximum-scale=10.0, width=device-width" /> 
<title>http://localhost:9092/</title>

<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>

<link href="/css/style.css" rel="Stylesheet" type="text/css"> <%-- /static/css/style.css --%> 
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
 <c:import url="/menu/top.do" />   <%-- <jsp:include page="./menu/top.jsp" flush='false' /> --%>
 
 <div style="width: 70%; margin: 30px auto;">
  <img src="/images/winter13.jpg" style="width: 100%;">
 </div>
 
 <jsp:include page="./menu/bottom.jsp" flush='false' /> 
</body>
</html>