<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>

<script type="text/javascript">
$(document).ready(function (){
	
	$("#loginbtn").click(function(e){
		e.preventDefault();
		if($("#loginid").val() == ""){
			alert("로그인 아이디를 입력해주세요");
			$("#loginid").focus();
		}else if($("#loginpwd").val() == ""){
			alert("로그인 비밀번호를 입력해주세요");
			$("#loginpwd").focus();
		}else{

			$.ajax({
	            url : "<c:url value='/login.ajax'/>",   
	            type : "POST",  
	            data : $("#loginfrm").serialize(),
	            async : false,
	            success : function(data, status) {
	                console.log(data);
	            }
	        });
		}
	});
		
});
</script>
</head>
<body>

<form id="loginfrm" name="loginfrm" method="POST" action="<c:url value='/login'/>">
	<table>
		<tr>
			<td style="width:50px;">id</td>
			<td style="width:150px;">
				<input style="width:145px;" type="text" id="loginid" name="loginid" value="" />
			</td>
			
		</tr>
		<tr>
			<td>pwd</td>
			<td>
				<input style="width:145px;" type="text" id="loginpwd" name="loginpwd" value="" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" id="loginbtn" value="로그인" />
			</td>
		</tr>
    	<c:if test="${not empty param.fail}">
	    	<tr>
	    		<td colspan="2">
					<p><font color="red">Your login attempt was not successful, try again.</font></p>
					<p><font color="red">Reason: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</font></p>
					<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
	    		</td>
	    	</tr>
    	</c:if>
	</table>
</form>

</body>
</html>