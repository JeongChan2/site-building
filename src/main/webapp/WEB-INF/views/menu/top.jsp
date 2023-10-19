<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class='container_main'>
  <div class='top_img'>
  <div class="top_menu_label">주식 version version 4.0</div>
    <nav class="top_menu">
      <a href="/" class="menu_link">주식</a><span class="top_menu_sep"> </span>
      
      <c:forEach var="categoryVO" items="${list_top }">
        <a href="/contents_stock/list_by_cate_id.do?cate_id=${categoryVO.cate_id }&now_page=1" class="menu_link">${categoryVO.cate_name }</a><span class="top_menu_sep"> </span>
      </c:forEach>
      
      <c:choose>
        <c:when test="${sessionScope.admin_id == null }">
          <a href="/admin_stock/login.do" class="menu_link">관리자 로그인</a>
        </c:when>
        <c:otherwise>
          <a href="/category/list_all.do" class="menu_link">카테고리 전체 목록</a><span class="top_menu_sep"> </span>
          <a href="/contents_stock/list_all.do" class="menu_link">전체 글 목록</a><span class="top_menu_sep"> </span>
          <a href="/admin_stock/logout.do" class="menu_link">관리자 ${sessionScope.admin_id } 로그아웃</a>
        </c:otherwise>
      </c:choose>
      
    </nav>
  </div>
  <div class='content_body'> <!--  내용 시작 -->  