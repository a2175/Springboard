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
                <col width="15%">
                <col width="*"/>
            </colgroup>
            <caption>게시글 작성</caption>
            <tbody>
                <tr>
                    <th scope="row">제목</th>
                    <td><input type="text" id="TITLE" name="TITLE" class="wdp_90"></input></td>
                </tr>
                <tr>
                    <td colspan="2" class="view_text">
                        <textarea rows="20" cols="100" title="내용" id="CONTENTS" name="CONTENTS"></textarea>
                        <p id="content_length"></p>
                    </td>
                </tr>
            </tbody>
        </table>
		
        <div id="fileDiv">
            <p>
                <input type="file" id="file" name="file_0">
                <a href="#this" class="btn" id="delete_0">삭제</a>
                <input type="hidden" id="fileindex" value="0">
            </p>
        </div>
		첨부파일의 용량은 최대 10MB이며 최대 3개까지 등록 가능합니다.
        <br/><br/>
        <a href="#this" class="btn" id="addFile">파일 추가</a>
        <a href="#this" class="btn" id="write">작성하기</a>
        <a href="#this" class="btn" id="list">목록으로</a>
    </form>
     
    <%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
        var gfv_count = 1;
        var file_count = 1;
     	var filesizecheck = new Array();
     	
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
            
            $("input[name='file_0']").on('change', function(e){
            	e.preventDefault();
            	fn_checkFileSize($(this));
            });
            
            $("#CONTENTS").on("change paste keyup", function(e){
            	fn_doContentCount();
        	});
        });
         
        function fn_openBoardList(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/openBoardList.do' />");
            comSubmit.submit();
        }
         
        function fn_insertBoard(){
        	var filecheck;
        	for(var i=0;i<filesizecheck.length;i++) {
        		if(filesizecheck[i] == 0)
        			filecheck = 0;
        	}
        	
        	if($("#TITLE").val().length < 1)
        		alert("제목을 입력해주세요.");
        	else if($("#TITLE").val().length > 100)
        		alert("제목이 너무 깁니다.");
        	else if($("#CONTENTS").val().length > 255)
        		alert("게시글 내용은 255자를 넘을 수 없습니다.");
        	else if(filecheck == 0)
        		alert("첨부파일이 최대용량을 넘습니다.");
        	else {
            	var comSubmit = new ComSubmit("frm");
            	comSubmit.setUrl("<c:url value='/sample/insertBoard.do' />");
            	comSubmit.submit();
        	}
        }
         
        function fn_addFile(){
        	if(file_count < 3) {
            	var str = "<p>"+
            		"<input type='file' name='file_"+(gfv_count)+"'>"+
            		"&nbsp" + "<a href='#this' class='btn' name='delete_"+(gfv_count)+"'>삭제</a>"+
            		"<input type='hidden' id='fileindex' value='"+(gfv_count)+"'>"+
            		"</p>";
            	$("#fileDiv").append(str);
            	$("a[name='delete_"+(gfv_count)+"']").on("click", function(e){ //삭제 버튼
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
        
        function fn_doContentCount(){
        	var str = $("#CONTENTS").val().length + "/255";
        	$("#content_length").empty();
    		$("#content_length").append(str);
        }
    </script>
</body>
</html>