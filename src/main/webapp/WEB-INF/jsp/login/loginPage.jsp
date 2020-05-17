<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<sec:authorize access="hasRole('ROLE_ANONYMOUS')"> 
		<form id="loginfrm" name="loginfrm" method="POST" action="<c:url value='/login'/>">
			<p align="right">
				아이디: <input style="width:100px" type="text" id="loginid" name="loginid" value="" />
			 	비밀번호: <input style="width:100px" type="password" id="loginpwd" name="loginpwd" value="" />     
		       	<a href="#this" class="btn" id="loginbtn">로그인</a>
		       	<a href="#this" class="btn" id="signup">회원가입</a>
		    	<c:if test="${not empty param.fail}">
					<p><font color="red">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</font></p>
					<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION"/>
		    	</c:if>
	    	</p>
		</form>	
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
		<p align="right">
			환영합니다. <b><sec:authentication property="principal.nickname"/></b>님. 
			<a href="#this" class="btn" id="logout">로그아웃</a>
		</p>
	</sec:authorize>
  
	<script type="text/javascript">
		if("${param.loginRequired}" == "true")
			alert("로그인이 필요합니다.");

		$(document).ready(function(){	
		   	$("#logout").on("click", function(e){ //로그인 버튼
		       	e.preventDefault();
		       	fn_doLogout();
		   	});
		    
		   	$("#signup").on("click", function(e){ //회원가입 버튼
		       	e.preventDefault();
		       	fn_openLoginSignup();
		   	});
		});
		
		function fn_doLogout(){
			window.location.href = "<c:url value='/logout' />";
		}
		
		function fn_openLoginSignup(){
		   	var comSubmit = new ComSubmit();
		   	comSubmit.setUrl("<c:url value='/login/openLoginSignup.do' />");
		   	comSubmit.submit();
		}  
		
		$("#loginbtn").click(function(e){
			if($("#loginid").val() == "") {
				e.preventDefault();
				alert("로그인 아이디를 입력해주세요");
				$("#loginid").focus();
			}
			else if($("#loginpwd").val() == "") {
				e.preventDefault();
				alert("로그인 비밀번호를 입력해주세요");
				$("#loginpwd").focus();
			}
			else {
				$.ajax({
		            url : "<c:url value='/login.ajax'/>",   
		            type : "POST",  
		            data : $("#loginfrm").serialize(),
		            async : false,
		            success : function(data, status) {
		                if(data.success == false)
		                	alert("로그인에 실패했습니다.");
		                else
		                	location.reload(true);
		            }
		        });
			}
		});
	</script>