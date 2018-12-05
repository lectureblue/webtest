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
.search{
	text-align:center;
	margin:3px auto;
}
</style> 
<%-- <link href="<%=root%>/css/style.css" rel="Stylesheet" type="text/css"> --%>
<script type="text/javascript">
function read(id){
	var url = "${root}/member/read";
	url = url + "?id="+id;
	url = url + "&col=${col}";
	url = url + "&word=${word}";
	url = url + "&nowPage=${nowPage}";
	
	location.href=url;
	
}
</script>
</head> 
<!-- *********************************************** -->
<body>
<div class="search">
<FORM name='frm' method='POST' action='./list'>
<select name="col">
	<option value="id"
	<c:if test="${col=='id'}" >selected</c:if>
	>아이디</option>
	<option value="email"
	<c:if test="${col=='email'}" >selected</c:if>
	>이메일</option>
	<option value="mname"
	<c:if test="${col=='mname'}" >selected</c:if>
	>성명</option>
	<option value="total">전체출력</option>
</select>
<input type="text" name="word" value="${word}">
<button>검색</button>
<button type="button" onclick="location.href='${root}/member/create'">회원가입</button>
</FORM>
</div>

<div class="container">
<h2><span class="glyphicon glyphicon-th-list"></span>
회원목록
</h2>
<c:forEach var="dto" items="${list }">
  <TABLE class="table table-hover">
    <TR>
      <td rowspan="5" style="width:25%">
      <img src='${root }/member/storage/${dto.fname}' width="200px" height="200px" ></td>
      <TH style="width:20%">아이디</TH>
      <TD style="width:55%">
      <a href="javascript:read('${dto.id}')">${dto.id}</a></TD>
    </TR>
    <tr>
    	<th>성명</th>
    	<td>${dto.mname}</td>
    </tr>
    <tr>
    	<th>전화번호</th>
    	<td>${dto.tel}</td>
    </tr>
    <tr>
    	<th>이메일</th>
    	<td>${dto.email}</td>
    </tr>
    <tr>
    	<th>주소</th>
    	<td>
    		${dto.address1}
    		${dto.address2}
    	</td>
    </tr>
  </TABLE>
</c:forEach> 
  <DIV class='bottom'>
    ${paging}
  </DIV>
 
</div> 

</body>
<!-- *********************************************** -->
</html> 