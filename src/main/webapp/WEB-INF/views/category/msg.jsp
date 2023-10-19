<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Stock world</title>
<link rel="shortcut icon" href="/images/shortcut.png" /> <%-- /static 기준 --%>

<link href="/css/style.css" rel="Stylesheet" type="text/css"> <%-- /static/css/style.css --%> 
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>

</head> 
<body>

<%
String code = (String)request.getAttribute("code");
int cnt = (int)request.getAttribute("cnt");
String cate_name = (String)request.getAttribute("cate_name");
%>

<c:import url="/menu/top.do" />

<div class='title_line'>카테고리 > 알림</div>
<div class='message'>
  <fieldset class='fieldset_basic'>
    <ul>
        <%
          if (code.equals("create_success")) {
        %>
        <li class="li_none">
          <span class="span_success">카테고리를 등록했습니다.</span><br>
          등록된 카테고리: <%=cate_name %>
        </li>
        <%
        } else if (code.equals("create_fail")) {
        %>
        <li class="li_none"><span class="span_fail">카테고리 등록에 실패했습니다.</span>
        등록 실패한 카테고리: <%=cate_name %>
        </li>
        <%
        } else if (code.equals("update_success")) {
        %>
        <li class="li_none"><span class="span_success">카테고리 수정을 성공했습니다.</span>
        수정된 카테고리 카테고리: <%=cate_name %>
        </li>
        <%
        } else if (code.equals("update_fail")) {
        %>
        <li class="li_none"><span class="span_fail">카테고리 수정에 실패했습니다.</span>
        수정 실패한 카테고리: <%=cate_name %>
        </li>
				<%
				} else if (code.equals("delete_success")) {
				%>
				<li class="li_none"><span class="span_success">카테고리 삭제를
						성공했습니다.</span> 삭제된 카테고리: <%=cate_name%></li>
				<%
				} else if (code.equals("delete_fail")) {
				%>
				<li class="li_none"><span class="span_fail">카테고리 삭제에
						실패했습니다.</span> 삭제 실패한 카테고리: <%=cate_name%></li>
				<%
				}
				%>

				<li class="li_none">
          <br>
          <%
            if (cnt == 0) {
          %>
            <button type="button" onclick="history.back()" class="btn btn-info btn-sm">다시 시도</button>
          <%
          }
          %>
          <button type="button" onclick="location.href='./list_all.do'" class="btn btn-info btn-sm">목록</button>
          </li>
    </ul>
  </fieldset>

</div>

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>