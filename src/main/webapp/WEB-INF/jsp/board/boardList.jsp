<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body style="width:1000px">
    <h2><a href="/first/board/openBoardList.do" style="color: black">게시판 목록</a></h2>
	
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
             
        </tbody>
    </table>
     
    <div id="PAGE_NAVI" style="margin:auto; display:table;"></div>
    <input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>

    <p>
		<span style="margin:auto;display:table">
			<a href="#this" class="btn" id="write">글쓰기</a>
			제목 검색: <input type="text" id="KEYWORD" name="KEYWORD" value="${param.keyword}"></input>
        	<a style="margin-left:5px" href="#this" class="btn" id="search">검색</a>
		</span>
    </p>
	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
    <script type="text/javascript">
    	var page_index = "${param.pageIdx}";
    	var keyword = "${param.keyword}";

        $(document).ready(function(){
        	if(!gfn_isNull(page_index) && gfn_isNull(keyword)) {
        		$("#PAGE_INDEX").val(page_index);
        		fn_selectBoardList(page_index);
        	}
        	else if(!gfn_isNull(page_index) && !gfn_isNull(keyword)) {
        		$("#PAGE_INDEX").val(page_index);
        		fn_selectBoardSearchList(page_index);
        	}
        	else {
        		$("#PAGE_INDEX").val(1);
        		fn_selectBoardList(1);
        	}
        	
            $("#write").on("click", function(e){ //글쓰기 버튼
                e.preventDefault();
                fn_openBoardWrite();
            });
            
            $("#search").on("click", function(e){ //검색 버튼
                e.preventDefault();
                $("#PAGE_INDEX").val(1);
                fn_selectBoardSearchList(1);
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
            comSubmit.addParam("pageIdx", $("#PAGE_INDEX").val());
            if(!gfn_isNull($("#KEYWORD").val()))
            	comSubmit.addParam("keyword", $("#KEYWORD").val());
            comSubmit.setMethod("get");
            comSubmit.submit();
        }
         
        function fn_selectBoardList(pageNo){
            var comAjax = new ComAjax();
            comAjax.setUrl("<c:url value='/board/selectBoardList.do' />");
            comAjax.setCallback("fn_selectBoardListCallback");
            comAjax.addParam("pageIdx", pageNo);
            comAjax.addParam("pageRow", 15);
            comAjax.ajax();
        }
        
        function fn_selectBoardSearchList(pageNo){
            var comAjax = new ComAjax();
            comAjax.setUrl("<c:url value='/board/selectBoardSearchList.do' />");
            comAjax.setCallback("fn_selectBoardListCallback");
            comAjax.addParam("keyword", $("#KEYWORD").val());
            comAjax.addParam("pageIdx", pageNo);
            comAjax.addParam("pageRow", 15);
            comAjax.ajax();
        }
        
        function fn_selectBoardListCallback(data){
            var total = data.total;
            var keyword = $("#KEYWORD").val();
            var body = $("table>tbody");
            body.empty();
            if(data.list.length == 0){
                var str = "<tr>" +
                                "<td colspan='4'>조회된 결과가 없습니다.</td>" +
                            "</tr>";
                body.append(str);
            }
            else{
                var params = {
                    divId : $("#PAGE_NAVI"),
                    pageIndex : $("#PAGE_INDEX"),
                    totalCount : total,
                    eventName : gfn_isNull(keyword) == true ? "fn_selectBoardList" : "fn_selectBoardSearchList"
                };
                gfn_renderPaging(params);
                
                var str = "";
                $.each(data.list, function(key, value){
                	var comment_cnt;
                	if(value.comment_cnt > 0)
                		comment_cnt = " [" + value.comment_cnt +"]";
                	else
                		comment_cnt = "";
                    str += "<tr>" +
                                "<td>" + value.idx + "</td>" +
                                "<td class='title'>" +
                                    "<a href='#this' name='title'>" + value.title + comment_cnt +"</a>" +
                                    "<input type='hidden' id='IDX' value=" + value.idx + ">" +
                                "</td>" +
                                "<td>" + value.nickname + "</td>" +
                                "<td>" + value.hit_cnt + "</td>" +
                                "<td>" + value.crea_dtm + "</td>" +
                            "</tr>";
                });
                body.append(str);
                
                $("a[name='title']").on("click", function(e){ //제목
                    e.preventDefault();
                    fn_openBoardDetail($(this));
                });
            }
        }
    </script>
</body>
</html>