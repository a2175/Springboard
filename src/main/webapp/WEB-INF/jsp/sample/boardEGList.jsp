<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
	<style>
      #search {
        margin-left:5px
      }
      span {
        margin:auto;
        display:table;
      }
    </style>
</head>
<body>
    <h2><a href="/first/board/openBoardEGList.do" style="color: black">게시판 목록</a></h2>
    <p align="right">
		환영합니다. <b>${NICKNAME}</b>님. 
		<a href="#this" class="btn" id="logout">로그아웃</a>
	</p>
    <table class="board_list">
        <colgroup>
            <col width="10%"/>
            <col width="*"/>
            <col width="15%"/>
            <col width="10%"/>
            <col width="20%"/>
        </colgroup>
        <thead>
            <tr>
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">조회수</th>
                <th scope="col">작성일</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${fn:length(list) > 0}">
                    <c:forEach var="row" items="${list}" varStatus="status">
                        <tr>
                            <td>${row.IDX }</td>
                            <td class="title">
                                <a href="#this" id="title">${row.TITLE }</a>
                                <input type="hidden" id="IDX" value="${row.IDX }">
                            </td>
                            <td>${row.NICKNAME }</td>
                            <td>${row.HIT_CNT }</td>
                            <td>${row.CREA_DTM }</td>
                        </tr>
                    </c:forEach> 
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="4">조회된 결과가 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>  
        </tbody>
    </table>
     
    <p>
		<span>
			<c:if test="${not empty paginationInfo}">
        		<ui:pagination paginationInfo = "${paginationInfo}" type="text" jsFunction="fn_search"/>
    		</c:if>
    		<input type="hidden" id="currentPageNo" name="currentPageNo"/><br><br>
		</span>
    </p>
    
    <a href="#this" class="btn" id="write">글쓰기</a>
    
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#write").on("click", function(e){ //글쓰기 버튼
                e.preventDefault();
                fn_openBoardWrite();
            });
             
            $("a[id='title']").on("click", function(e){ //제목
                e.preventDefault();
                fn_openBoardDetail($(this));
            });
        });
        
        function fn_openBoardWrite(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/board/openBoardWrite.do' />");
            comSubmit.submit();
        }
        
        function fn_openBoardDetail(obj){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/board/openBoardDetail.do' />");
            comSubmit.addParam("IDX", obj.parent().find("#IDX").val());
            comSubmit.submit();
        }
         
        function fn_search(pageNo){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/board/openBoardEGList.do' />");
            comSubmit.addParam("currentPageNo", pageNo);
            comSubmit.submit();
        }
    </script>
</body>
</html>