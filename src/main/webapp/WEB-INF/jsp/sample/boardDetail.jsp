<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
    <table class="board_view">
        <colgroup>
            <col width="15%"/>
            <col width="35%"/>
            <col width="15%"/>
            <col width="35%"/>
        </colgroup>
        <caption>게시글 상세</caption>
        <tbody>
            <tr>
                <th scope="row">글 번호</th>
                <td>${map.IDX }</td>
                <th scope="row">조회수</th>
                <td>${map.HIT_CNT }</td>
            </tr>
            <tr>
                <th scope="row">작성자</th>
                <td>${map.NICKNAME }</td>
                <th scope="row">작성시간</th>
                <td>${map.CREA_DTM }</td>
            </tr>
            <tr>
                <th scope="row">제목</th>
                <td colspan="3">${map.TITLE }</td>
            </tr>
            <tr>
                <td colspan="4">${map.CONTENTS }</td>
            </tr>
            <tr>
                <th scope="row">첨부파일</th>
                <td colspan="3">
                	<c:if test="${list.size() eq 0}">
    					첨부파일이 없습니다.
					</c:if>
                    <c:forEach var="row" items="${list }">
                    	<p>
                        	<input type="hidden" id="IDX" value="${row.IDX }">
                        	<a href="#this" id="file">${row.ORIGINAL_FILE_NAME }</a>
                        	(${row.FILE_SIZE }kb)
                        </p>
                    </c:forEach> 
                </td>
            </tr>
        </tbody>
    </table>
    <br/>
    
    <table class="board_comment">
        <tbody id="comment">
             
        </tbody>
    </table>
    
    <p>
		<textarea style="width:45%; overflow:hidden" rows="1" cols="60" title="댓글" id="COMMENT" name="COMMENT"></textarea>
    	<a href="#this" class="btn" id="submit">댓글등록</a>
    </p>
    
    <a href="#this" class="btn" id="list">목록으로</a>
    <c:if test="${map.CREA_ID eq ID}">
    	<a href="#this" class="btn" id="update">수정하기</a>
    	<a href="#this" class="btn" id="delete">삭제하기</a>
	</c:if>
	<br><br>
	
	<c:choose>
    	<c:when test="${nextmap.IDX ne null}">
       		 <p><a href="#this" id="next" style="color: black">다음글: ${nextmap.TITLE}</a></p>
    	</c:when>
    	<c:when test="${nextmap.IDX eq null}">
       		 <p style="color: gray">다음글: 다음글이 없습니다.</p>
    	</c:when>
	</c:choose>
	
	<c:choose>
    	<c:when test="${prevmap.IDX ne null}">
       		 <a href="#this" id="prev" style="color: black">이전글: ${prevmap.TITLE}</a>
    	</c:when>
    	<c:when test="${prevmap.IDX eq null}">
       		 <p style="color: gray">이전글: 이전글이 없습니다.</p>
    	</c:when>
	</c:choose>
	
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
    	$(document).ready(function(){
    		fn_selectCommentList();
    		
    		$("#submit").on("click", function(e){ //댓글등록 버튼
            	e.preventDefault();
            	fn_doSubmitComment();
        	});
    		
        	$("#list").on("click", function(e){ //목록으로 버튼
            	e.preventDefault();
            	fn_openBoardList();
        	});
         
        	$("#update").on("click", function(e){ //수정하기 버튼
            	e.preventDefault();
            	fn_openBoardUpdate();
        	});
        	
        	$("#delete").on("click", function(e){ //삭제하기 버튼
                e.preventDefault();
                fn_deleteBoard();
            });
         
        	$("a[id='file']").on("click", function(e){ //파일 이름
            	e.preventDefault();
            	fn_downloadFile($(this));
        	});
        	
        	$("a[id='next']").on("click", function(e){ //다음글
            	e.preventDefault();
            	fn_openBoardNext();
        	});
        	
        	$("a[id='prev']").on("click", function(e){ //이전글
            	e.preventDefault();
            	fn_openBoardPrev();
        	});
    	});
    	
    	function fn_doSubmitComment(){
    		var idx = "${map.IDX}";
    		var comment = $("#COMMENT").val();
    		
    		if(comment.length < 1) {
    			alert("채팅내용을 입력해주세요.");
    		}
    		else {
    			$("#COMMENT").val("");
                var comAjax = new ComAjax();
                comAjax.setUrl("<c:url value='/sample/insertComment.do' />");
                comAjax.setCallback("fn_selectCommentListCallback");
                comAjax.addParam("IDX", idx);
                comAjax.addParam("COMMENT", comment);
                comAjax.ajax();
    		}
    	}
    	
    	function fn_openBoardList(){
    		var page_index = "${PAGE_INDEX}";
    		var keyword = "${KEYWORD}";
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/sample/openBoardList.do' />");
        	comSubmit.addParam("PAGE_INDEX", page_index);
        	comSubmit.addParam("KEYWORD", keyword);
        	comSubmit.submit();
    	}
     
    	function fn_openBoardUpdate(){
        	var idx = "${map.IDX}"; 
        	var id = "${map.CREA_ID}";
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/sample/openBoardUpdate.do' />");
        	
        	idxInit();
        	
        	comSubmit.addParam("IDX", idx);
        	comSubmit.addParam("CREA_ID", id);
        	comSubmit.submit();
    	}
    	
    	function fn_deleteBoard(){
    		var idx = "${map.IDX}";
    		var id = "${map.CREA_ID}";
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/deleteBoard.do' />");
            
            idxInit();
            
            comSubmit.addParam("IDX", idx);
            comSubmit.addParam("CREA_ID", id);
            comSubmit.submit();
        }
     
    	function fn_downloadFile(obj){
        	var idx = obj.parent().find("#IDX").val();
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");

        	idxInit();

			comSubmit.addParam("IDX", idx);
        	comSubmit.submit();
		}
    	
    	function fn_openBoardNext(){
        	var idx = "${nextmap.IDX}";
        	var page_index = "${PAGE_INDEX}";
        	var keyword = "${KEYWORD}";
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/sample/openBoardDetail.do' />");
        	
        	idxInit();
        	
			comSubmit.addParam("IDX", idx);
			comSubmit.addParam("PAGE_INDEX", page_index);
			comSubmit.addParam("KEYWORD", keyword);
        	comSubmit.submit();
		}
    	
    	function fn_openBoardPrev(){
        	var idx = "${prevmap.IDX}";
        	var page_index = "${PAGE_INDEX}";
        	var keyword = "${KEYWORD}";
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/sample/openBoardDetail.do' />");
        	
        	idxInit();
        	
			comSubmit.addParam("IDX", idx);
			comSubmit.addParam("PAGE_INDEX", page_index);
			comSubmit.addParam("KEYWORD", keyword);
        	comSubmit.submit();
		}
    	
    	function fn_selectCommentList(){
    		var idx = "${map.IDX}";
            var comAjax = new ComAjax();
            comAjax.setUrl("<c:url value='/sample/selectCommentList.do' />");
            comAjax.setCallback("fn_selectCommentListCallback");
            comAjax.addParam("IDX", idx);
            comAjax.ajax();
        }
         
        function fn_selectCommentListCallback(data){
        	var total = data.TOTAL;
            var body = $("table>tbody[id='comment']");
            body.empty();

            if(total == 0){
                var str = "<tr>" +
                                "<td colspan='4'>댓글이 없습니다.</td>" +
                          "</tr>";
                body.append(str);
            }
            else{             
                var str = "";
                $.each(data.list, function(key, value){
                	if(value.NICKNAME == "${NICKNAME}"){
                    	str += "<tr>" +
                                	"<th>" + value.NICKNAME + "</th>" +
                                	"<td>" + value.CONTENTS + "</td>" +
                                	"<td class='CREA_DTM'>" + value.CREA_DTM + "</td>" +
                                	"<td class='DELETE'>" +
                                		"<a href='#this' id='delete_cmt'>삭제</a>" +
                                		"<input type='hidden' id='IDX' value=" + value.IDX + ">" +
                                	"</td>" +
                           		"</tr>";
                	}
                	else{
                		str += "<tr>" +
                    				"<th>" + value.NICKNAME + "</th>" +
                    				"<td>" + value.CONTENTS + "</td>" +
                    				"<td class='CREA_DTM'>" + value.CREA_DTM + "</td>" +
                    				"<td class='DELETE'></td>" +
               					"</tr>";
                	}
                });
                body.append(str);
                $("a[id='delete_cmt']").on("click", function(e){ //삭제 버튼
                    e.preventDefault();
                    fn_deleteComment($(this));
                });
            }
		}
        
        function fn_deleteComment(obj){
			var cmt_idx = obj.parent().find("#IDX").val();
			var idx = "${map.IDX}";
            var comAjax = new ComAjax();
            comAjax.setUrl("<c:url value='/sample/deleteComment.do' />");
            comAjax.setCallback("fn_selectCommentListCallback");
            comAjax.addParam("IDX", idx);
            comAjax.addParam("CMT_IDX", cmt_idx);
            comAjax.ajax();
        }
	</script>
</body>
</html>