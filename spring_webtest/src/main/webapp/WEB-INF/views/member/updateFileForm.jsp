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
<%-- <link href="<%=root%>/css/style.css" rel="Stylesheet" type="text/css"> --%>
</head> 

<body>

<div class="container">
<h2>파일수정</h2>
 
<FORM name='frm' method='POST' 
	enctype="multipart/form-data"
	action='./updateFile'>
	<input type="hidden" name="id" value="${param.id}">
	<input type="hidden" name="oldfile" value="${param.oldfile}">
  <TABLE class="table">
  <tr>
   <td colspan="2" style="text-align: center">
   <img src="./storage/${param.oldfile}">
   </td>
  </tr>
    <TR>
      <TH>파일</TH>
      <TD><input type="file" name="fnameMF" required="required"></TD>
    </TR>
  </TABLE>
  
  <DIV class='bottom'>
    <input type='submit' value='수정'>
    <input type='button' value='취소' onclick="history.back()">
  </DIV>
</FORM>
</div>

</body>

</html> 