<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="<c:url value='/js/common.js'/>" charset="utf-8"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/ui.css'/>" />
	<style>
		input {
			width: 120px;
		}
		#submit {
        	margin-left: 80px;
		}
    </style>
</head>
<body>
	<form id="frm" name="frm">
        <table>
        	<thead><tr><td colspan="2" style="text-align:center;">회원가입</td></tr></thead>
            <tbody>
            	<tr>
                    <th scope="row">아이디</th>
                    <td><input type="text" id="ID" name="id"></input></td>
                    <td id="idcheck"></td>
                </tr>
                <tr>
                	<th scope="row">비밀번호</th>
                    <td><input type="PASSWORD" id="PASSWORD" name="password"></input></td>
                    <td id="pwcheck"></td>
                </tr>
                <tr>
                    <th scope="row">비밀번호 재확인</th>
                    <td><input type="PASSWORD" id="RE_PASSWORD" name="re_password"></input></td>
                    <td id="re_pwcheck"></td>
                </tr>
                <tr>
                	<th scope="row">닉네임</th>
                    <td><input type="text" id="NICKNAME" name="nickname"></input></td>
                    <td id="nicknamecheck"></td>
                </tr>
                <tr>
                	<th scope="row">이메일</th>
                    <td><input type="text" id="EMAIL" name="email"></input></td>
                    <td id="emailcheck"></td>
                </tr> 
            </tbody>
        </table>
        
        <br/>
        <a href="#this" class="btn" id="submit">등록</a>
        <a href="#this" class="btn" id="cancel">취소</a>
    </form>
    
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
    	var idcheck = 0;
    	var pwcheck = 0;
    	var re_pwcheck = 0;
    	var nicknamecheck = 0;
    	var emailcheck = 0;
    	
    	$(document).ready(function(){
        	$("#submit").on("click", function(e){ //등록 버튼
            	e.preventDefault();
            	fn_doSubmit();
        	});
         
        	$("#cancel").on("click", function(e){ //취소 버튼
            	e.preventDefault();
            	fn_doCancel();
        	});
        	
        	$("#ID").on("change paste keyup", function(e){
            	e.preventDefault();
            	fn_doIdDuplicationCheck();
        	});
        	
        	$("#PASSWORD").on("change paste keyup", function(e){
            	e.preventDefault();
            	fn_doPasswordCheck();
        	});
        	
        	$("#RE_PASSWORD").on("change paste keyup", function(e){
            	e.preventDefault();
            	fn_doRePasswordCheck();
        	});
        	
        	$("#NICKNAME").on("change paste keyup", function(e){
            	e.preventDefault();
            	fn_doNicknameDuplicationCheck();
        	});
        	
        	$("#EMAIL").on("change paste keyup", function(e){
            	e.preventDefault();
            	fn_doEmailCheck();
        	});
    	});
     
    	function fn_doSubmit(){
    		if(idcheck == 1 && pwcheck == 1 && re_pwcheck == 1 && nicknamecheck == 1 && emailcheck == 1) {
    			var comSubmit = new ComSubmit("frm");
            	comSubmit.setUrl("<c:url value='/login/doSubmit.do' />");
            	comSubmit.setMethod("POST");
            	comSubmit.submit();
    		}
    		else {
    			alert("가입 조건에 만족하지 않습니다.");
    		}
        	
    	}
     
    	function fn_doCancel(){
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/' />");
        	comSubmit.submit();
    	}
    	
    	function fn_doIdDuplicationCheck(){
            var comAjax = new ComAjax();
            comAjax.setUrl("<c:url value='/login/doIdDuplicationCheck.do' />");
            comAjax.setCallback("fn_doIdDuplicationCheckCallback");
            comAjax.addParam("id", $("#ID").val());
            comAjax.ajax();
        }
         
        function fn_doIdDuplicationCheckCallback(data){
            var isDuplication = data.isDuplication;
            var id = $("#ID").val();
            var length = id.length;;
            var body = $("table>tbody>tr>td[id='idcheck']");
            var pattern = /^[a-zA-Z0-9]+$/;
            
            body.empty();
            idcheck = 0;
            
            if(isDuplication == false && length > 3 && length < 11 && pattern.test(id)) {
            	var str = "";

            	str += "사용 가능한 아이디 입니다.";
            	idcheck = 1;
                body.append(str);
            }
            else if(isDuplication == true && length > 3) {
            	var str = "";

            	str += "이미 사용 중인 아이디 입니다.";

                body.append(str);
            }
            else if(length > 0) {
            	var str = "";

            	str += "아이디는 4~10의 길이이며 영어로 구성 되어야 합니다.";

                body.append(str);
            }
            else {
            	var str = "";

            	str += "";

                body.append(str);
            }        	
		}
        
        function fn_doPasswordCheck(){
        	var password = $("#PASSWORD").val();
        	var length = password.length;
            var body = $("table>tbody>tr>td[id='pwcheck']");
            body.empty();
            pwcheck = 0;
            
            var pattern1 = /[0-9]/;	// 숫자 var 
    		var pattern2 = /[a-z]/;	// 소문자 var
    		var pattern3 = /[A-Z]/;	// 대문자 var 
    		var pattern4 = /[~!@#$%^&*()_+|<>?:{}]/;	// 특수문자 
            
            if(pattern1.test(password) && pattern2.test(password) && pattern3.test(password) && pattern4.test(password) && length > 7 && length < 17) {
            var str = "";

            	str += "조건에 만족합니다.";
            	pwcheck = 1;

                body.append(str);
            }
            else {
            	var str = "";
            	
            	str += "비밀번호는 8~16의 길이이며 하나 이상의 숫자, 대문자, 특수문자가 포함되어야 합니다.";

                body.append(str);
            }        	
		}
        
        function fn_doRePasswordCheck(){
        	var length = $("#RE_PASSWORD").val().length;
            var body = $("table>tbody>tr>td[id='re_pwcheck']");
            body.empty();
            re_pwcheck = 0;
            
            if($("#PASSWORD").val() == $("#RE_PASSWORD").val() && pwcheck == 1) {
            var str = "";

            	str += "";
				
            	re_pwcheck = 1;
            	
                body.append(str);
            }
            else if(length < 1) {
            	var str = "";
            	
            	str += "";

                body.append(str);
            }
            else {
            	var str = "";
            	
            	str += "비밀번호와 일치하지 않습니다.";

                body.append(str);
            }  
		}
        
        function fn_doNicknameDuplicationCheck(){
            var comAjax = new ComAjax();
            comAjax.setUrl("<c:url value='/login/doNicknameDuplicationCheck.do' />");
            comAjax.setCallback("fn_doNicknameDuplicationCheckCallback");
            comAjax.addParam("nickname", $("#NICKNAME").val());
            comAjax.ajax();
        }
         
        function fn_doNicknameDuplicationCheckCallback(data){
            var isDuplication = data.isDuplication;
            var nickname = $("#NICKNAME").val();
            var length = nickname.length;;
            var body = $("table>tbody>tr>td[id='nicknamecheck']");
            body.empty();
            nicknamecheck = 0;
            
            var pattern = /^[a-zA-Z0-9가-힣]+$/;
            if(isDuplication == false && pattern.test(nickname) && length > 3 && length < 13) {
            	var str = "";

            	str += "사용 가능한 닉네임 입니다.";
            	nicknamecheck = 1;
                body.append(str);
            }
            else if(isDuplication == true) {
            	var str = "";

            	str += "이미 사용 중인 닉네임 입니다.";

                body.append(str);
            }
            else if(length == 0) {
            	var str = "";

            	str += "닉네임는 4~12의 길이로 구성 되어야 합니다.";

                body.append(str);
            }
            else {
            	var str = "";

            	str += "";

                body.append(str);
            }        	
		}
        
        function fn_doEmailCheck() {
        	var email = $("#EMAIL").val();
        	var length = email.length;
            var body = $("table>tbody>tr>td[id='emailcheck']");
            emailcheck = 0;
            body.empty();
            
            var pattern1 = /^[a-zA-Z0-9.@]+$/;
            var pattern2 = /[.]{2}/;
            
            var count1 = (email.match(/@/g) || []).length;
            var count2 = (email.match(/[.]/g) || []).length;
            
            var Split1 = email.split('@');
            var str1 = Split1[0].length;
            var str2 = Split1[1];
            
            if(!gfn_isNull(str2)) {
            	var split2 = str2.split('.');
            	var str3 = split2[0].length;
            }
            
            var lastChar = email.substr(email.length - 1);
            
            if(count1 == 1 && (count2 == 1 || count2 == 2) && str1 > 3 && str3 > 3
            		&& pattern1.test(email) && !pattern2.test(email) && length < 31 && lastChar != '.') {
            	var str = "";

            	str += "";
            	
            	emailcheck = 1;
            	
                body.append(str);
            }
            else {
            	var str = "";

            	str += "정확한 이메일 주소를 입력해주세요.";
            	
                body.append(str);
            }    
		}
	</script>
</body>
</html>