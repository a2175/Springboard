<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
	<style>
      input {
        width: 100px;
      }
    </style>
</head>
<body>
	<form id="frm" name="frm">
		<p align="right">	
			아이디: <input type="text" id="ID" name="id"></input>
		 	비밀번호: <input type="PASSWORD" id="PASSWORD" name="password"></input>      
        	<a href="#this" class="btn" id="login">로그인</a>
        	<a href="#this" class="btn" id="signup">회원가입</a>
    	</p>    
    </form>
    
    <c:if test="${param.login eq false}">
    	<script type="text/javascript">
    		alert("아이디 또는 비밀번호를 다시 확인하세요.");
    	</script>
	</c:if>

    <script type="text/javascript">
    	$(document).ready(function(){
        	$("#login").on("click", function(e){ //로그인 버튼
            	e.preventDefault();
            	fn_doLogin();
        	});
         
        	$("#signup").on("click", function(e){ //회원가입 버튼
            	e.preventDefault();
            	fn_openLoginSignup();
        	});
    	});

    	function fn_doLogin(){
        	var comSubmit = new ComSubmit("frm");
        	comSubmit.setUrl("<c:url value='/login/doLogin.do' />");
        	comSubmit.addParam("pageIdx", $("#PAGE_INDEX").val());
        	if(!gfn_isNull(${param.keyword}))
            	comSubmit.addParam("keyword", "${param.keyword}");
        	comSubmit.submit();
    	}
     
    	function fn_openLoginSignup(){
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/login/openLoginSignup.do' />");
        	comSubmit.submit();
    	}  	
	</script>
</body>
</html>