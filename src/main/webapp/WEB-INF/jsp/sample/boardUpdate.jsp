<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
    <form id="frm" name="frm" enctype="multipart/form-data">
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
                    <td>
                        ${map.IDX }
                        <input type="hidden" id="IDX" name="IDX" value="${map.IDX }">
                    </td>
                    <th scope="row">조회수</th>
                    <td>${map.HIT_CNT }</td>
                </tr>
                <tr>
                    <th scope="row">작성자</th>
                    <td>${map.CREA_ID }</td>
                    <th scope="row">작성시간</th>
                    <td>${map.CREA_DTM }</td>
                </tr>
                <tr>
                    <th scope="row">제목</th>
                    <td colspan="3">
                        <input type="text" id="TITLE" name="TITLE" class="wdp_90" value="${map.TITLE }"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="4" class="view_text">
                        <textarea rows="20" cols="100" title="내용" id="CONTENTS" name="CONTENTS">${map.CONTENTS }</textarea>
                    </td>
                </tr>
                <tr>
                    <th scope="row">첨부파일</th>
                    <td colspan="3">
                        <div id="fileDiv">               
                            <c:forEach var="row" items="${list }" varStatus="var">
                                <p>
                                    <input type="hidden" name="IDX_${var.index }" value="${row.IDX }">
                                    <a href="#this" id="name_${var.index }">${row.ORIGINAL_FILE_NAME }</a>
                                    <input type="file" id="file_${var.index }" name="file_${var.index }">
                                    (${row.FILE_SIZE }kb)
                                    <a href="#this" class="btn" id="delete_${var.index }">삭제</a>
                                    <input type="hidden" id="fileindex" value="${var.index }">
                                </p>
                            </c:forEach>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
	첨부파일의 용량은 최대 10MB이며 최대 3개까지 등록 가능합니다.<br/><br/>
	
    <a href="#this" class="btn" id="addFile">파일 추가</a>
    <a href="#this" class="btn" id="list">목록으로</a>
    <a href="#this" class="btn" id="update">저장하기</a>
     
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
        var gfv_count = '${fn:length(list)}';
        var file_count = gfv_count;
        var filesizecheck = new Array();
        
        $(document).ready(function(){
            $("#list").on("click", function(e){ //목록으로 버튼
                e.preventDefault();
                fn_openBoardList();
            });
             
            $("#update").on("click", function(e){ //저장하기 버튼
                e.preventDefault();
                fn_updateBoard();
            });
             
            $("#addFile").on("click", function(e){ //파일 추가 버튼
                e.preventDefault();
                fn_addFile();
            });
             
            $("a[id^='delete']").on("click", function(e){ //삭제 버튼
                e.preventDefault();
                fn_deleteFile($(this));
            });
            
            $("input[name^='file']").on('change', function(e){
            	e.preventDefault();
            	fn_checkFileSize($(this));
            });
        });
         
        function fn_openBoardList(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/openBoardList.do' />");
            comSubmit.submit();
        }
         
        function fn_updateBoard(){
        	var filecheck;
        	for(var i=0;i<filesizecheck.length;i++) {
        		if(filesizecheck[i] == 0)
        			filecheck = 0;
        	}
        	
        	if($("#TITLE").val().length < 1)
        		alert("제목을 입력해주세요.");
        	else if(filecheck == 0)
        		alert("첨부파일이 최대용량을 넘습니다.");
        	else {
        		var comSubmit = new ComSubmit("frm");
                comSubmit.setUrl("<c:url value='/sample/updateBoard.do' />");
                comSubmit.submit();
        	}
        }
         
        function fn_addFile(){
        	if(file_count < 3) {
            	var str = "<p>" +
                    "<input type='file' id='file_"+(gfv_count)+"' name='file_"+(gfv_count)+"'>" +
                    "&nbsp" + "<a href='#this' class='btn' id='delete_"+(gfv_count)+"' name='delete_"+(gfv_count)+"'>삭제</a>" +
                    "<input type='hidden' id='fileindex' value='"+(gfv_count)+"'>" +
                	"</p>";
            	$("#fileDiv").append(str);
            	$("#delete_"+(gfv_count)).on("click", function(e){ //삭제 버튼
                	e.preventDefault();
                	fn_deleteFile($(this));
            	});
            
            	$("input[name='file_"+(gfv_count)+"']").on('change', function(e){
            		e.preventDefault();
            		fn_checkFileSize($(this));
            	});
        	
            	gfv_count++;
            	file_count++;
        	}
        	else {
        		alert("첨부파일은 최대 3개까지 등록 할 수 있습니다.");
        	}
        }
         
        function fn_deleteFile(obj){
        	var fileindex = obj.parent().find("#fileindex").val();
        	filesizecheck[fileindex] = 1;
        	file_count--;
            obj.parent().remove();
        }
        
        function fn_checkFileSize(obj){
        	var fileindex = obj.parent().find("#fileindex").val();
        	if(obj[0].files[0].size > 10000000)
        		filesizecheck[fileindex] = 0;
        	else
        		filesizecheck[fileindex] = 1;
        }
    </script>
</body>
</html>