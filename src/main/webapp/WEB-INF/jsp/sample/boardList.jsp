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
    <h2><a href="/first/sample/openBoardList.do" style="color: black">게시판 목록</a></h2>
	
	<c:choose>
    	<c:when test="${ID eq null}">
       		 <c:import url="/login/openLoginPage.do" ></c:import>
    	</c:when>
    	<c:when test="${ID ne null}">
			<p align="right">
				환영합니다. <b>${NICKNAME}</b>님. 
				<a href="#this" class="btn" id="logout">로그아웃</a>
			</p>
    	</c:when>
	</c:choose>
	
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
		<span>
			<c:if test="${ID ne null}">
				<a href="#this" class="btn" id="write">글쓰기</a>
			</c:if>
			제목 검색: <input type="text" id="KEYWORD" name="KEYWORD" value="${KEYWORD}"></input>
        	<a href="#this" class="btn" id="search">검색</a>
		</span>
    </p>
	
	<c:if test="${ID ne null}">
		<%@ include file="/WEB-INF/include/include-body.jspf" %>
	</c:if>
    
    <script type="text/javascript">
    	var page_index = "${PAGE_INDEX}";
    	var keyword = "${KEYWORD}";
    	
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
            
            $("#logout").on("click", function(e){ //로그아웃 버튼
                e.preventDefault();
                fn_doLogout();
            });
        });
         
        function fn_openBoardWrite(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/openBoardWrite.do' />");
            comSubmit.submit();
        }
        
        function fn_doLogout(){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/login/doLogout.do' />");
            comSubmit.submit();
        }
         
        function fn_openBoardDetail(obj){
            var comSubmit = new ComSubmit();
            comSubmit.setUrl("<c:url value='/sample/openBoardDetail.do' />");
            comSubmit.addParam("IDX", obj.parent().find("#IDX").val());
            comSubmit.addParam("PAGE_INDEX", $("#PAGE_INDEX").val());
            if(!gfn_isNull(keyword))
            	comSubmit.addParam("KEYWORD", keyword);
            comSubmit.setMethod("get");
            comSubmit.submit();
        }
         
        function fn_selectBoardList(pageNo){
            var comAjax = new ComAjax();
            comAjax.setUrl("<c:url value='/sample/selectBoardList.do' />");
            comAjax.setCallback("fn_selectBoardListCallback");
            comAjax.addParam("PAGE_INDEX", pageNo);
            comAjax.addParam("PAGE_ROW", 15);
            comAjax.ajax();
        }
        
        function fn_selectBoardSearchList(pageNo){
        	keyword = $("#KEYWORD").val();
            var comAjax = new ComAjax();
            comAjax.setUrl("<c:url value='/sample/selectBoardSearchList.do' />");
            comAjax.setCallback("fn_selectBoardListCallback");
            comAjax.addParam("KEYWORD", keyword);
            comAjax.addParam("PAGE_INDEX", pageNo);
            comAjax.addParam("PAGE_ROW", 15);
            comAjax.ajax();
        }
        
        function fn_selectBoardListCallback(data){
            var total = data.TOTAL;
            var body = $("table>tbody");
            body.empty();
            if(total == 0){
                var str = "<tr>" +
                                "<td colspan='4'>조회된 결과가 없습니다.</td>" +
                            "</tr>";
                body.append(str);
            }
            else{
                var params = {
                    divId : "PAGE_NAVI",
                    pageIndex : "PAGE_INDEX",
                    totalCount : total,
                    eventName : gfn_isNull(keyword) == true ? "fn_selectBoardList" : "fn_selectBoardSearchList"
                };
                gfn_renderPaging(params);
                
                var str = "";
                $.each(data.list, function(key, value){
                	var comment_cnt;
                	if(value.COMMENT_CNT > 0)
                		comment_cnt = " [" + value.COMMENT_CNT +"]";
                	else
                		comment_cnt = "";
                    str += "<tr>" +
                                "<td>" + value.IDX + "</td>" +
                                "<td class='title'>" +
                                    "<a href='#this' name='title'>" + value.TITLE + comment_cnt +"</a>" +
                                    "<input type='hidden' id='IDX' value=" + value.IDX + ">" +
                                "</td>" +
                                "<td>" + value.NICKNAME + "</td>" +
                                "<td>" + value.HIT_CNT + "</td>" +
                                "<td>" + value.CREA_DTM + "</td>" +
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