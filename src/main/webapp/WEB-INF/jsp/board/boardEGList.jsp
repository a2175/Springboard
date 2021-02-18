<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body style="width:1000px">
    <h2><a href="/first/board/openBoardEGList.do" style="color: black">게시판 목록</a></h2>

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
                            <td>${row.idx }</td>
                            <td class="title">
                                <a href="#this" id="title"><c:out value="${row.title }" /></a>
                                <input type="hidden" id="IDX" value="${row.idx }">
                            </td>
                            <td>${row.nickname }</td>
                            <td>${row.hit_cnt }</td>
                            <td>${row.crea_dtm }</td>
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
		<span style="margin:auto;display:table">
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
        	if(gfn_isNull("${userInfo}"))
        		alert("로그인 해주세요.");
        	else {
	            var comSubmit = new ComSubmit();
	            comSubmit.setUrl("<c:url value='/board/openBoardWrite.do' />");
	            comSubmit.submit();
        	}
        }
        
        function fn_openBoardDetail(obj){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/board/openBoardDetail.do' />");
            comSubmit.addParam("idx", obj.parent().find("#IDX").val());
            comSubmit.submit();
        }
        
        function fn_search(pageNo){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/board/openBoardEGList.do' />");
            comSubmit.addParam("currentPageNo", pageNo);
            comSubmit.addParam("pageRow", 15);
            comSubmit.submit();
        }
    </script>
</body>
</html>