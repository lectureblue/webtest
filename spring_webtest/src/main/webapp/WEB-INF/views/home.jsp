<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8"  %>
<c:set var="title" value="나의 여행 블로그"/>
<c:if test="${not empty sessionScope.id && sessionScope.grade =='A' }">
	<c:set var="title" value="관리자 페이지"/>
</c:if>

<c:choose>
	<c:when test="${empty sessionScope.id}">
		<c:set var="str" value="메인페이지 입니다." />
	</c:when>
	<c:otherwise>
		<c:set var="str" value="안녕하세요  ${sessionScope.id} 님" />
	</c:otherwise>
</c:choose>
<html>
<head>
	<title>Home</title>
</head>
<body>
<div class="container">
<P>  The time on the server is ${serverTime}. </P>
<h1>${title}</h1>

<h2>${str}</h2>
<img src="${pageContext.request.contextPath }/images/main.jpg" width="80%"><br><br>

<c:choose>
	<c:when test="${empty sessionScope.id}">
	<button onclick="location.href='member/login'">로그인</button>
	</c:when>
	<c:otherwise>
	<button onclick="location.href='member/logout'">로그아웃</button>
	</c:otherwise>
</c:choose>

</div>
</body>
</html>
