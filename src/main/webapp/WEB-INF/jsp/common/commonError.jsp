<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
	<script type="text/javascript">
    		alert("${MSG}");
    		location.href = "${ADDRESS}";
    </script>
</body>
</html>