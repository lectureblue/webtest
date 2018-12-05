<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file="/ssi/ssi.jsp" %> 

<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title></title>
<script type="text/javascript">
function read(){
	var url = "read.do";
	url = url +"?id=${id}";
	
	location.href=url;
}

</script>
<style type="text/css"> 
*{ 
  font-family: gulim; 
  font-size: 20px; 
} 
</style> 
<%-- <link href="<%=root%>/css/style.css" rel="Stylesheet" type="text/css"> --%>
</head> 
<!-- *********************************************** -->
<body>

<div class="container">
 
<h2>사진변경</h2>

<c:choose>
	<c:when test="${flag }">
	사진변경 했습니다<br><br>
	<input type='button' value='나의정보' onclick="read()">
	</c:when>
	<c:otherwise>
	사진변경을 실패했습니다.<br><br>
	<input type='button' value='다시시도' onclick="history.back()">
	</c:otherwise>
</c:choose>

   
</DIV>

</body>
<!-- *********************************************** -->
</html> 