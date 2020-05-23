<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body style="position: absolute;">
	<h2 style="color: black">게시글 상세</h2>

    <table class="board_view">
        <colgroup>
            <col width="15%"/>
            <col width="35%"/>
            <col width="15%"/>
            <col width="35%"/>
        </colgroup>

        <tbody>
            <tr>
                <th scope="row">글 번호</th>
                <td>${detail.idx }</td>
                <th scope="row">조회수</th>
                <td>${detail.hit_cnt }</td>
            </tr>
            <tr>
                <th scope="row">작성자</th>
                <td>${detail.nickname }</td>
                <th scope="row">작성시간</th>
                <td>${detail.crea_dtm }</td>
            </tr>
            <tr>
                <th scope="row">제목</th>
                <td colspan="3">${detail.title }</td>
            </tr>
            <tr>
                <td colspan="4">${detail.contents }</td>
            </tr>
            <tr>
                <th scope="row">첨부파일</th>
                <td colspan="3">
                	<c:if test="${detail.files.size() eq 0}">
    					첨부파일이 없습니다.
					</c:if>
                    <c:forEach var="row" items="${detail.files }">
                    	<p>
                        	<input type="hidden" id="IDX" value="${row.idx }">
                        	<a href="#this" id="file">${row.original_file_name }</a>
                        	(${row.file_size }kb)
                        </p>
                    </c:forEach> 
                </td>
            </tr>
        </tbody>
    </table>
    <br/>
    
    <div id="board_comment" class="board_comment">
    </div>
        
   	<br>
	<textarea style="width:890px; overflow:hidden" rows="1" cols="60" <sec:authorize access="isAnonymous()"> placeholder="로그인이 필요합니다." </sec:authorize> title="댓글" id="COMMENT" name="COMMENT"></textarea>
   	<a href="#this" class="btn" id="submit">댓글등록</a>
        
    <br>
    <a href="#this" class="btn" id="list">목록으로</a>
   	<sec:authorize access="isAuthenticated()">
	    <c:if test="${detail.crea_id eq userInfo.id}">
	    	<a href="#this" class="btn" id="update">수정하기</a>
	    	<a href="#this" class="btn" id="delete">삭제하기</a>
		</c:if>
	</sec:authorize>
	<br><br>
	
	<c:choose>
    	<c:when test="${prevBoard.idx ne null}">
       		 <a href="#this" id="prev" style="color: black">이전글: ${prevBoard.title}</a>
    	</c:when>
    	<c:when test="${prevBoard.idx eq null}">
       		 <p style="color: gray">이전글: 이전글이 없습니다.</p>
    	</c:when>
	</c:choose>
	
	<c:choose>
    	<c:when test="${nextBoard.idx ne null}">
       		 <p><a href="#this" id="next" style="color: black">다음글: ${nextBoard.title}</a></p>
    	</c:when>
    	<c:when test="${nextBoard.idx eq null}">
       		 <p style="color: gray">다음글: 다음글이 없습니다.</p>
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
    		var idx = "${detail.idx}";
    		var comment = $("#COMMENT").val();
    		
    		if(gfn_isNull("${userInfo}")) {
        		alert("로그인 해주세요.");
    		}
    		else if(comment.length > 255) {
    			alert("댓글내용은 255자를 넘을 수 없습니다.");
    		}
    		else if(comment.length < 1) {
    			alert("댓글내용을 입력해주세요.");
    		}
    		else {
    			$("#COMMENT").val("");
                var comAjax = new ComAjax();
                comAjax.setUrl("<c:url value='/comment/insertComment.do' />");
                comAjax.setCallback("fn_selectCommentList");
                comAjax.addParam("board_idx", idx);    
                comAjax.addParam("contents", comment);
                comAjax.ajax();
    		}
    	}
    	
    	function fn_openBoardList(){
    		var page_index = "${param.pageIdx}";
    		var keyword = "${param.keyword}";
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/board/openBoardList.do' />");
        	comSubmit.addParam("pageIdx", page_index);
            if(!gfn_isNull(keyword))
            	comSubmit.addParam("keyword", keyword);
        	comSubmit.setMethod("get");
        	comSubmit.submit();
    	}

    	function fn_openBoardUpdate(){
        	var idx = "${detail.idx}";
        	var id = "${detail.crea_id}";
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/board/openBoardUpdate.do' />");     	
        	comSubmit.addParam("idx", idx);
        	comSubmit.submit();
    	}
    	
    	function fn_deleteBoard(){
    		if (confirm("정말 삭제하시겠습니까??") == true) {
            	var idx = "${detail.idx}";
            	var id = "${detail.crea_id}";
                var comSubmit = new ComSubmit();
                comSubmit.setUrl("<c:url value='/board/deleteBoard.do' />");
                comSubmit.addParam("idx", idx);
                comSubmit.submit();
    		}	
        }
     
    	function fn_downloadFile(obj){
        	var idx = obj.parent().find("#IDX").val();
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/board/downloadFile.do' />");
			comSubmit.addParam("idx", idx);
        	comSubmit.submit();
		}
    	
    	function fn_openBoardNext(){
        	var idx = "${nextBoard.idx}";
        	var page_index = "${param.pageIdx}";
        	var keyword = "${param.keyword}";
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/board/openBoardDetail.do' />");
			comSubmit.addParam("idx", idx);
			comSubmit.addParam("pageIdx", page_index);
            if(!gfn_isNull(keyword))
            	comSubmit.addParam("keyword", keyword);
			comSubmit.setMethod("get");
        	comSubmit.submit();
		}
    	
    	function fn_openBoardPrev(){
        	var idx = "${prevBoard.idx}";
        	var page_index = "${param.pageIdx}";
        	var keyword = "${param.keyword}";
        	var comSubmit = new ComSubmit();
        	comSubmit.setUrl("<c:url value='/board/openBoardDetail.do' />");
			comSubmit.addParam("idx", idx);
			comSubmit.addParam("pageIdx", page_index);
            if(!gfn_isNull(keyword))
            	comSubmit.addParam("keyword", keyword);
			comSubmit.setMethod("get");
        	comSubmit.submit();
		}
    	
    	function fn_selectCommentList(){
    		var idx = "${detail.idx}";
            var comAjax = new ComAjax();
            comAjax.setUrl("<c:url value='/comment/selectCommentList.do' />");
            comAjax.setCallback("fn_selectCommentListCallback");
            comAjax.addParam("board_idx", idx);
            comAjax.ajax();
        }
         
        function fn_selectCommentListCallback(data){
            var body = $("div[id='board_comment']");
            body.empty();

            if(data.list.length == 0){
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
                	if(value.nickname == "${userInfo.nickname}"){
                    	str += "<table>" +
                    				"<tr>" +
                    					"<th colspan='2' width='700'>" +
										"<font size='2'>" + value.nickname + "</font>" +
        								"</th>" +
        								"<td class='thumbs'>" +
        									"<a href='#this' id='thumbs_up'>" +
        										"<img src='<c:url value='/img/like.jpg' />'>" +
        										"<font id='upcount' color='blue' size='3'>" + value.thumbsup_cnt + "</font>" +
                        					"</a>" +
                        					"<a href='#this' id='thumbs_down'>" +
                								"<img src='<c:url value='/img/dislike.jpg' />'>" +
                								"<font id='downcount' color='red' size='3'>" + value.thumbsdown_cnt + "</font>" +
                							"</a>" +
                							"<input type='hidden' id='IDX' value=" + value.idx + ">" +
                                		"</td>" +
                                		"<td>" +
                                			"<a href='#this' id='delete_cmt'>" +
                                				"<img src='<c:url value='/img/delete.jpg' />' style='width: 30px; height: 30px; float:right;'>" +
                                			"</a>" +
                            				"<input type='hidden' id='IDX' value=" + value.idx + ">" +
                            			"</td>" +
        							"</tr>" +
        						"</table>" +
        						"<table>" +
        							"<tr>" +
                                		"<td height='50'>" + value.contents + "</td>" +
                                		"<td class='CREA_DTM'>" + value.crea_dtm + "</td>" +
                           			"</tr>" +
                           		"</table>";
                	}
                	else{
                		str += "<table>" +
                					"<tr>" +
    									"<th colspan='2' width='700'>" +
											"<font size='2'>" + value.nickname + "</font>" +
										"</th>" +
										"<td class='thumbs'>" +
											"<a href='#this' id='thumbs_up'>" +
												"<img src='<c:url value='/img/like.jpg' />'>" +
												"<font id='upcount' color='blue' size='3'>" + value.thumbsup_cnt + "</font>" +
        									"</a>" +
        									"<a href='#this' id='thumbs_down'>" +
												"<img src='<c:url value='/img/dislike.jpg' />'>" +
												"<font id='downcount' color='red' size='3'>" + value.thumbsdown_cnt + "</font>" +
											"</a>" +
											"<input type='hidden' id='IDX' value=" + value.idx + ">" +
                						"</td>" +
                						"<td width='33'></td>" +
									"</tr>" +
								"</table>" +
								"<table>" +
									"<tr>" +
                						"<td height='50'>" + value.contents + "</td>" +
                						"<td class='CREA_DTM'>" + value.crea_dtm + "</td>" +
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
                var comAjax = new ComAjax();
                comAjax.setUrl("<c:url value='/comment/deleteComment.do' />");
                comAjax.setCallback("fn_selectCommentList");
                comAjax.addParam("idx", cmt_idx);
                comAjax.ajax();
    		}
        }
        
        var thumbsindex = "";
        function fn_thumbsUp(obj){
        	if(gfn_isNull("${userInfo}"))
        		alert("로그인 해주세요.");
        	else {
        		var cmt_idx = obj.parent().find("#IDX").val();
            	var comAjax = new ComAjax();
            	thumbsindex = obj.find("#upcount");
            
            	comAjax.setUrl("<c:url value='/comment/thumbsUp.do' />");
            	comAjax.setCallback("fn_thumbsUpCallback");
            	comAjax.addParam("idx", cmt_idx);
            	comAjax.ajax();
        	}
        }
        
        function fn_thumbsDown(obj){
        	if(gfn_isNull("${userInfo}"))
        		alert("로그인 해주세요.");
        	else {
        		var cmt_idx = obj.parent().find("#IDX").val();
            	var comAjax = new ComAjax();
            	thumbsindex = obj.find("#downcount");
            
            	comAjax.setUrl("<c:url value='/comment/thumbsDown.do' />");
            	comAjax.setCallback("fn_thumbsDownCallback");
            	comAjax.addParam("idx", cmt_idx);
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