package first;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;

import first.board.mapper.BoardMapper;
import first.board.vo.BoardVO;
import first.board.vo.FileVO;
import first.common.util.CustomFileUtils;

public class BoardControllerTest extends TestConfig {   
    @Autowired
    private CustomFileUtils customFileUtils;
    
    @Autowired
    private BoardMapper boardMapper;
    
    // 게시글 페이지로 이동 테스트
    @Test
    public void openBoardList_test() throws Exception {
    	this.mockMvc
			.perform(get("/board/openBoardList.do"))
			.andExpect(status().is(200))
			.andExpect(view().name("/board/boardList"));
    }
    
    // 페이징 게시글 목록 가져오기 테스트
    @Test
    public void selectBoardList_test() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/board/selectBoardList.do"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("jsonView"))
	        .andReturn();
	      
        JSONParser parser = new JSONParser(); 
        Object obj = parser.parse(mvcResult.getResponse().getContentAsString());
        
		JSONObject resultJson = (JSONObject)obj;
		assertEquals(new Long(200), resultJson.get("total"));
		
		JSONArray boardList = (JSONArray)resultJson.get("list");
		assertEquals(15, boardList.size());   
	}
    
    // 검색한 페이징 게시글 목록 가져오기 테스트
    @Test
    public void selectBoardSearchList_test() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/board/selectBoardSearchList.do")
        	.param("keyword", "제목 200"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("jsonView"))
	        .andReturn();
	    
        JSONParser parser = new JSONParser(); 
        Object obj = parser.parse(mvcResult.getResponse().getContentAsString());
        
		JSONObject resultJson = (JSONObject)obj;
		assertEquals(new Long(1), resultJson.get("total"));
		
		JSONArray boardList = (JSONArray)resultJson.get("list");
		assertEquals(1, boardList.size());
		
		String boardTitle = (String)((JSONObject)boardList.get(0)).get("title");
		assertEquals("제목 200", boardTitle);
    }
    
    // 글쓰기 페이지로 이동 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void openBoardWrite_test() throws Exception {
    	this.mockMvc
			.perform(get("/board/openBoardWrite.do"))
			.andExpect(status().is(200))
			.andExpect(view().name("/board/boardWrite"));
    }
    
    // 권한이 없는 상태에서 글쓰기 페이지로 이동 테스트
    @Test
    public void openBoardWrite_authority_test() throws Exception {
    	MvcResult mvcResult = this.mockMvc
			.perform(get("/board/openBoardWrite.do"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
			.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
    }
    
    // 게시글 삽입 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void insertBoard_test() throws Exception {
    	MvcResult mvcResult = null;
    	BoardVO vo = null;
    	
    	MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
    	mvcResult =  this.mockMvc
            .perform(fileUpload("/board/insertBoard.do")
            .file(file)
	        .param("title", "insert test")
	        .param("contents", "insert test"))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/board/openBoardList.do"))
        	.andReturn();
    	
    	vo = (BoardVO)mvcResult.getModelAndView().getModel().get("boardVO");
        
    	mvcResult = this.mockMvc
	        .perform(get("/board/openBoardDetail.do")
	        .param("idx", String.valueOf(vo.getIdx())))
	    	.andDo(print())
			.andExpect(status().is(200))
			.andExpect(view().name("/board/boardDetail"))
	    	.andReturn();
    	
    	vo = (BoardVO)mvcResult.getModelAndView().getModel().get("detail");
    	assertEquals("insert test", vo.getTitle());
    	assertEquals("insert test", vo.getContents());
    	
    	FileVO f = vo.getFiles().get(0);
    	assertEquals("orig", f.getOriginal_file_name());
    	assertEquals(true, customFileUtils.isExist(f.getStored_file_name()));
    }
    
    // 권한이 없는 상태에서 게시글 삽입 테스트
    @Test
    public void insertBoard_authority_test() throws Exception {
    	MvcResult mvcResult = null;
    	
    	MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
    	mvcResult =  this.mockMvc
	        .perform(fileUpload("/board/insertBoard.do")
			.file(file)
	        .param("title", "insert test")
	        .param("contents", "insert test"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
	    	.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
    }
    
    // 게시글 보기 테스트
    @Test
    public void openBoardDetail_test() throws Exception {
    	MvcResult mvcResult = null;
    	BoardVO vo = null;
    	
    	mvcResult = this.mockMvc
	        .perform(get("/board/openBoardDetail.do")
	        .param("idx", "100"))
			.andExpect(status().is(200))
			.andExpect(view().name("/board/boardDetail"))
	    	.andReturn();
    	
    	vo = (BoardVO)mvcResult.getModelAndView().getModel().get("detail");
    	assertEquals("제목 100", vo.getTitle());
    	assertEquals("내용 100", vo.getContents());
    	
    	FileVO f = vo.getFiles().get(0);
    	assertEquals("orig", f.getOriginal_file_name());
    	assertEquals(true, customFileUtils.isExist(f.getStored_file_name()));
    	
    	vo = (BoardVO)mvcResult.getModelAndView().getModel().get("prevBoard");
    	assertEquals("제목 101", vo.getTitle());
    	
    	vo = (BoardVO)mvcResult.getModelAndView().getModel().get("nextBoard");
    	assertEquals("제목 99", vo.getTitle());
    }
    
    // 게시글 수정 페이지로 이동 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void openBoardUpdate_test() throws Exception {
    	MvcResult mvcResult = null;
    	BoardVO vo = null;
    	
    	mvcResult = this.mockMvc
	        .perform(get("/board/openBoardUpdate.do")
	        .param("idx", "100"))
			.andExpect(status().is(200))
			.andExpect(view().name("/board/boardUpdate"))
	    	.andReturn();
    	
    	vo = (BoardVO)mvcResult.getModelAndView().getModel().get("detail");
    	assertEquals("제목 100", vo.getTitle());
    	assertEquals("내용 100", vo.getContents());
    }
    
    // 권한이 없는 상태에서 게시글 수정 페이지로 이동 테스트
    @Test
    public void openBoardUpdate_authority_test() throws Exception {
    	MvcResult mvcResult = null;
    	
    	mvcResult = this.mockMvc
	        .perform(get("/board/openBoardUpdate.do")
	        .param("idx", "100"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
	    	.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
    }
    
    // 게시글 수정 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void updateBoard_test() throws Exception {
    	MvcResult mvcResult = null;
    	BoardVO vo = null;
    	
    	MockMultipartFile file = new MockMultipartFile("file", "update file test", null, "bar".getBytes());
    	mvcResult =  this.mockMvc
            .perform(fileUpload("/board/updateBoard.do")
            .file(file)
            .param("idx", "100")
	        .param("title", "update test")
	        .param("contents", "update test"))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/board/openBoardDetail.do"))
        	.andReturn();
    	  
    	mvcResult = this.mockMvc
	        .perform(get("/board/openBoardDetail.do")
	        .param("idx", "100"))
			.andExpect(status().is(200))
			.andExpect(view().name("/board/boardDetail"))
	    	.andReturn();
    	
    	vo = (BoardVO)mvcResult.getModelAndView().getModel().get("detail");
    	assertEquals("update test", vo.getTitle());
    	assertEquals("update test", vo.getContents());
    	
    	FileVO f = vo.getFiles().get(0);
    	assertEquals("update file test", f.getOriginal_file_name());
    	assertEquals(true, customFileUtils.isExist(f.getStored_file_name()));
    }
    
    // 권한이 없는 상태에서 게시글 수정 테스트
    @Test
    public void updateBoard_authority_test() throws Exception {
    	MvcResult mvcResult = null;
    	
    	MockMultipartFile file = new MockMultipartFile("file", "update file test", null, "bar".getBytes());
    	mvcResult =  this.mockMvc
		    .perform(fileUpload("/board/updateBoard.do")
		    .file(file)
		    .param("idx", "100")
		    .param("title", "update test")
		    .param("contents", "update test"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
			.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
    }
    
    // 게시글 삭제 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void deleteBoard_test() throws Exception {
    	MvcResult mvcResult = null;
    	BoardVO vo = null;
    	
    	mvcResult =  this.mockMvc
            .perform(post("/board/deleteBoard.do")
            .param("idx", "100"))
			.andExpect(status().is(302))
			.andExpect(view().name("redirect:/board/openBoardList.do"))
        	.andReturn();
    	  
    	mvcResult = this.mockMvc
	        .perform(get("/board/openBoardDetail.do")
	        .param("idx", "100"))
			.andExpect(status().is(200))
			.andExpect(view().name("/board/boardDetail"))
	    	.andReturn();
    	
    	vo = (BoardVO)mvcResult.getModelAndView().getModel().get("detail");
    	assertEquals(null, vo);
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("idx", "100");
    	
    	List<FileVO> file = boardMapper.selectFileList(map);
    	assertEquals(0, file.size());
    }
    
    // 권한이 없는 상태에서 게시글 삭제 테스트
    @Test
    public void deleteBoard_authority_test() throws Exception {
    	MvcResult mvcResult = null;
    	
    	mvcResult =  this.mockMvc
		    .perform(post("/board/deleteBoard.do")
		    .param("idx", "100"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
			.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
    }
}
