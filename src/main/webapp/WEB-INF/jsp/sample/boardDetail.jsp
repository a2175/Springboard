<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body style="position: absolute;">
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
    
    <div id="board_comment" class="board_comment">
    </div>
    
    <c:if test="${ID ne null}">
    	<br>
		<textarea style="width:890px; overflow:hidden" rows="1" cols="60" title="댓글" id="COMMENT" name="COMMENT"></textarea>
    	<a href="#this" class="btn" id="submit">댓글등록</a>
    </c:if>
    
    <br>
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
        	
        	$("#next").on("click", function(e){ //다음글
            	e.preventDefault();
            	fn_openBoardNext();
        	});
        	
        	$("#prev").on("click", function(e){ //이전글
            	e.preventDefault();
            	fn_openBoardPrev();
        	});     	
    	});
    	
    	function fn_doSubmitComment(){
    		var idx = "${map.IDX}";
    		var comment = $("#COMMENT").val();
    		
    		if(comment.length < 1) {
    			alert("댓글내용을 입력해주세요.");
    		}
    		else if(comment.length > 255) {
    			alert("댓글내용은 255자를 넘을 수 없습니다.");
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
        	comSubmit.addParam("IDX", idx);
        	comSubmit.addParam("CREA_ID", id);
        	comSubmit.addParam("isUpdate", true);
        	comSubmit.submit();
    	}
    	
    	function fn_deleteBoard(){
    		if (confirm("정말 삭제하시겠습니까??") == true) {
    			var idx = "${map.IDX}";
        		var id = "${map.CREA_ID}";
                var comSubmit = new ComSubmit();
                comSubmit.setUrl("<c:url value='/sample/deleteBoard.do' />");
                comSubmit.addParam("IDX", idx);
                comSubmit.addParam("CREA_ID", id);
                comSubmit.submit();
    		}	
        }
     
    	function fn_downloadFile(obj){
        	var idx = obj.parent().find("#IDX").val();
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("IDX", idx);
        	comSubmit.submit();
		}
    	
    	function fn_openBoardNext(){
        	var idx = "${nextmap.IDX}";
        	var page_index = "${PAGE_INDEX}";
        	var keyword = "${KEYWORD}";
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/sample/openBoardDetail.do' />");
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
            var body = $("div[id='board_comment']");
            body.empty();

            if(total == 0){
                var str = "<table>" +
							"<tr>" +
								"<td colspan='4'>댓글이 없습니다.</td>" +
							"</tr>" +
						  "</table>";
                body.append(str);
            }
            else{             
                var str = "";
                $.each(data.list, function(key, value){
                	if(value.NICKNAME == "${NICKNAME}"){
                    	str += "<table>" +
                    				"<tr>" +
                    					"<th colspan='2' width='700'>" +
										"<font size='2'>" + value.NICKNAME + "</font>" +
        								"</th>" +
        								"<td class='thumbs'>" +
        									"<a href='#this' id='thumbs_up'>" +
        										"<img src='<c:url value='/img/like.jpg' />'>" +
        										"<font id='upcount' color='blue' size='3'>" + value.THUMBSUP_CNT + "</font>" +
                        					"</a>" +
                        					"<a href='#this' id='thumbs_down'>" +
                								"<img src='<c:url value='/img/dislike.jpg' />'>" +
                								"<font id='downcount' color='red' size='3'>" + value.THUMBSDOWN_CNT + "</font>" +
                							"</a>" +
                							"<input type='hidden' id='IDX' value=" + value.IDX + ">" +
                                		"</td>" +
                                		"<td>" +
                                			"<a href='#this' id='delete_cmt'>" +
                                				"<img src='<c:url value='/img/delete.jpg' />' style='width: auto; height: 30px; float:right;'>" +
                                			"</a>" +
                            				"<input type='hidden' id='IDX' value=" + value.IDX + ">" +
                            			"</td>" +
        							"</tr>" +
        						"</table>" +
        						"<table>" +
        							"<tr>" +
                                		"<td height='50'>" + value.CONTENTS + "</td>" +
                                		"<td class='CREA_DTM'>" + value.CREA_DTM + "</td>" +
                           			"</tr>" +
                           		"</table>";
                	}
                	else{
                		str += "<table>" +
                					"<tr>" +
    									"<th colspan='2' width='700'>" +
											"<font size='2'>" + value.NICKNAME + "</font>" +
										"</th>" +
										"<td class='thumbs'>" +
											"<a href='#this' id='thumbs_up'>" +
												"<img src='<c:url value='/img/like.jpg' />'>" +
												"<font id='upcount' color='blue' size='3'>" + value.THUMBSUP_CNT + "</font>" +
        									"</a>" +
        									"<a href='#this' id='thumbs_down'>" +
												"<img src='<c:url value='/img/dislike.jpg' />'>" +
												"<font id='downcount' color='red' size='3'>" + value.THUMBSDOWN_CNT + "</font>" +
											"</a>" +
											"<input type='hidden' id='IDX' value=" + value.IDX + ">" +
                						"</td>" +
									"</tr>" +
								"</table>" +
								"<table>" +
									"<tr>" +
                						"<td height='50'>" + value.CONTENTS + "</td>" +
                						"<td class='CREA_DTM'>" + value.CREA_DTM + "</td>" +
           							"</tr>" +
           						"</table>";
                	}
                });
                body.append(str);
                
                $("a[id='delete_cmt']").on("click", function(e){ //댓글 삭제
                    e.preventDefault();
                    fn_deleteComment($(this));
                });
                
                $("a[id='thumbs_up']").on("click", function(e){ //좋아요
                	e.preventDefault();
                	fn_thumbsUp($(this));
            	});
            	
            	$("a[id='thumbs_down']").on("click", function(e){ //싫어요
                	e.preventDefault();
                	fn_thumbsDown($(this));
            	});
            }
		}
        
        function fn_deleteComment(obj){
        	if (confirm("정말 삭제하시겠습니까??") == true) {
        		var cmt_idx = obj.parent().find("#IDX").val();
    			var idx = "${map.IDX}";
                var comAjax = new ComAjax();
                comAjax.setUrl("<c:url value='/sample/deleteComment.do' />");
                comAjax.setCallback("fn_selectCommentListCallback");
                comAjax.addParam("IDX", idx);
                comAjax.addParam("CMT_IDX", cmt_idx);
                comAjax.ajax();
    		}
        }
        
        var thumbsindex = "";
        function fn_thumbsUp(obj){
        	if(gfn_isNull("${ID}"))
        		alert("로그인 해주세요.");
        	else {
        		var cmt_idx = obj.parent().find("#IDX").val();
            	var comAjax = new ComAjax();
            	thumbsindex = obj.find("#upcount");
            
            	comAjax.setUrl("<c:url value='/sample/thumbsUp.do' />");
            	comAjax.setCallback("fn_thumbsUpCallback");
            	comAjax.addParam("CMT_IDX", cmt_idx);
            	comAjax.ajax();
        	}
        }
        
        function fn_thumbsDown(obj){
        	if(gfn_isNull("${ID}"))
        		alert("로그인 해주세요.");
        	else {
        		var cmt_idx = obj.parent().find("#IDX").val();
            	var comAjax = new ComAjax();
            	thumbsindex = obj.find("#downcount");
            
            	comAjax.setUrl("<c:url value='/sample/thumbsDown.do' />");
            	comAjax.setCallback("fn_thumbsDownCallback");
            	comAjax.addParam("CMT_IDX", cmt_idx);
            	comAjax.ajax();
        	}
        }
        
        function fn_thumbsUpCallback(data){
        	var checkThumbsup = data.checkThumbsup;
            var upcount = thumbsindex.text();

            if(checkThumbsup == 1) {
            	thumbsindex.text(Number(upcount)+1);
            }
            else {
            	alert("이미 좋아요를 한 댓글입니다.");
            }
		}
        
        function fn_thumbsDownCallback(data){
        	var checkThumbsdown = data.checkThumbsdown;
            var downcount = thumbsindex.text();
 			
            if(checkThumbsdown == 1) {
            	thumbsindex.text(Number(downcount)+1);
            }
            else {
            	alert("이미 싫어요를 한 댓글입니다.");
            }
		}
	</script>
</body>
</html>