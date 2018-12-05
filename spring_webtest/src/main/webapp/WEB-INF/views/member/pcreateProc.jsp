<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file="/ssi/ssi.jsp" %>
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title></title> 
<style type="text/css"> 
*{ 
  font-family: gulim; 
  font-size: 20px; 
} 
</style> 
<%-- <link href="${root}/css/style.css" rel="Stylesheet" type="text/css"> --%>
</head> 
<!-- *********************************************** -->
<body>

<div class="container">

<h2>아이디 및 이메일 중복확인</h2> 

 ${str}
 
<br>
<br>
<input type='button' value='다시시도' onclick="history.back()">

</div> 
</body>
</html> 

