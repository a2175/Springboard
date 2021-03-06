<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body style="width:1000px">
	<h2 style="color: black">게시글 작성</h2>

    <form id="frm" name="frm" enctype="multipart/form-data">
        <table class="board_view">
            <colgroup>
                <col width="15%">
                <col width="*"/>
            </colgroup>

            <tbody>
                <tr>
                    <th scope="row">제목</th>
                    <td><input type="text" id="TITLE" name="title" class="wdp_90"></input></td>
                </tr>
                <tr>
                    <td colspan="2" class="view_text">
                        <textarea rows="20" cols="100" title="내용" id="CONTENTS" name="contents"></textarea>
                        <p id="content_length"></p>
                    </td>
                </tr>
            </tbody>
        </table>
		
        <div id="fileDiv">
            <p>
                <input type="file" id="file" name="file_0">
                <a href="#this" class="btn" id="delete_0">삭제</a>
            </p>
        </div>
		첨부파일의 용량은 최대 1MB이며 최대 3개까지 등록 가능합니다.<br/><br/>
        
        <a href="#this" class="btn" id="addFile">파일 추가</a>
        <a href="#this" class="btn" id="write">작성하기</a>
        <a href="#this" class="btn" id="list">목록으로</a>
    </form>
     
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
        var gfv_count = 1; 	
     	
        $(document).ready(function(){
        	fn_doContentCount();
        	
            $("#list").on("click", function(e){ //목록으로 버튼
                e.preventDefault();
                fn_openBoardList();
            });
             
            $("#write").on("click", function(e){ //작성하기 버튼
                e.preventDefault();
                fn_insertBoard();
            });
             
            $("#addFile").on("click", function(e){ //파일 추가 버튼
                e.preventDefault();
                fn_addFile();
            });
             
            $("a[id='delete_0']").on("click", function(e){ //삭제 버튼
                e.preventDefault();
                fn_deleteFile($(this));
            });
            
            $("#CONTENTS").on("change paste keyup", function(e){
            	fn_doContentCount();
        	});
        });
         
        function fn_openBoardList(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/board/openBoardList.do' />");
            comSubmit.submit();
        }
        
        function fn_checkBoard(){
        	var filearray = $("input[type='file']");
        	var totalFileSize = 0;
        	
        	if($("#TITLE").val().length < 1) { alert("제목을 입력해주세요."); return false }
        	if($("#TITLE").val().length > 100) { alert("제목이 너무 깁니다."); return false }
        	if($("#CONTENTS").val().length == 0) { alert("게시글 내용을 입력해주세요."); return false }	
        	if($("#CONTENTS").val().length > 255) { alert("게시글 내용은 255자를 넘을 수 없습니다."); return false }	
        	for(i=0; i<filearray.length; i++) {
        		if(filearray[i].files[0] != undefined) {
        			totalFileSize += filearray[i].files[0].size;
            		if(totalFileSize > 1000000) {
            			alert("첨부파일이 최대용량을 넘습니다."); 
            			return false;
            		}
        		}
        	}
        	
        	return true;
        }
         
        function fn_insertBoard(){
        	if(fn_checkBoard()) {
            	var comSubmit = new ComSubmit("frm");
            	comSubmit.setUrl("<c:url value='/board/insertBoard.do' />");
            	comSubmit.setMethod("POST");
            	comSubmit.submit();
        	}
        }
         
        function fn_addFile(){
        	if($("input[type='file']").length < 3) {
            	var str = "<p>"+
            				"<input type='file' name='file_"+(gfv_count)+"'>"+
            				"&nbsp" + "<a href='#this' class='btn' name='delete_"+(gfv_count)+"'>삭제</a>"+
            			  "</p>";
            	$("#fileDiv").append(str);
            	$("a[name='delete_"+(gfv_count)+"']").on("click", function(e){ //삭제 버튼
                	e.preventDefault();
                	fn_deleteFile($(this));
            	});
                        	
            	gfv_count++;
        	}
        	else {
        		alert("첨부파일은 최대 3개까지 등록 할 수 있습니다.");
        	}
        }
        
        function fn_deleteFile(obj){
            obj.parent().remove();
        }
          
        function fn_doContentCount(){
        	var str = $("#CONTENTS").val().length + "/255";
        	$("#content_length").empty();
    		$("#content_length").append(str);
        }
    </script>
</body>
</html>