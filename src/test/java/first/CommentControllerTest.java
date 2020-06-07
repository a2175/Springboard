package first;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;

public class CommentControllerTest extends TestConfig {

	// 해당 게시물의 댓글 목록 가져오기 테스트
    @Test
    public void selectCommentList_test() throws Exception {
    	MvcResult mvcResult = null;
    	
    	mvcResult = this.mockMvc
			.perform(get("/comment/selectCommentList.do")
			.param("board_idx", "1"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
	    	.andReturn();
    	
    	assertEquals("application/json", mvcResult.getResponse().getContentType());
    	
        JSONParser parser = new JSONParser(); 
		JSONObject resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		JSONArray commentList = (JSONArray)resultJson.get("list");
		
		assertEquals(3, commentList.size());
		
    	for(int i=1; i<=3; i++) {
    		assertEquals("test", ((JSONObject)commentList.get(i-1)).get("crea_id"));
    		assertEquals("댓글  "+String.valueOf(i), ((JSONObject)commentList.get(i-1)).get("contents"));
    	}
    }
    
    // 댓글 삽입 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void insertComment_test() throws Exception {
    	MvcResult mvcResult = null;
    	
		this.mockMvc
	        .perform(post("/comment/insertComment.do")
	        .param("board_idx", "100")
	        .param("contents", "insert comment"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"));
		
    	mvcResult = this.mockMvc
			.perform(get("/comment/selectCommentList.do")
			.param("board_idx", "100"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
	    	.andReturn();
    	
    	assertEquals("application/json", mvcResult.getResponse().getContentType());
    	
        JSONParser parser = new JSONParser(); 
		JSONObject resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		JSONObject comment = (JSONObject)((JSONArray)resultJson.get("list")).get(0);
		
    	assertEquals("insert comment", comment.get("contents"));
    	assertEquals("test", comment.get("crea_id"));
    }
    
    // 댓글 삽입 검증 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void insertComment_validation_test() throws Exception {
    	MvcResult mvcResult = null;
    	String contents = "";
    	
    	mvcResult = this.mockMvc
	        .perform(post("/comment/insertComment.do")
	        .param("board_idx", "100")
	        .param("contents", contents))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
			.andReturn();

    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'commentVO' on field 'contents'"));

    	for(int i=0; i<256; i++)
    		contents += "1";

    	mvcResult = this.mockMvc
	        .perform(post("/comment/insertComment.do")
	        .param("board_idx", "100")
	        .param("contents", contents))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
			.andReturn();
		
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'commentVO' on field 'contents'"));
    }
    
    // 권한이 없는 상태에서 댓글 삽입 테스트
    @Test
    public void insertComment_authority_test() throws Exception {
    	MvcResult mvcResult = null;
    	
    	mvcResult = this.mockMvc
	        .perform(post("/comment/insertComment.do")
	        .param("board_idx", "100")
	        .param("contents", "insert comment"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
			.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
    }
    
    // 댓글 삭제 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void deleteComment_test() throws Exception {
    	MvcResult mvcResult = null;
    	
		this.mockMvc
	        .perform(post("/comment/deleteComment.do")
	        .param("idx", "3"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"));
		
    	mvcResult = this.mockMvc
			.perform(get("/comment/selectCommentList.do")
			.param("board_idx", "1"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
	    	.andReturn();
    	
    	assertEquals("application/json", mvcResult.getResponse().getContentType());
    	
        JSONParser parser = new JSONParser(); 
		JSONObject resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		JSONArray commentList = (JSONArray)resultJson.get("list");
		
		assertEquals(2, commentList.size());
		
    	for(int i=1; i<=2; i++) {
    		assertEquals("test", ((JSONObject)commentList.get(i-1)).get("crea_id"));
    		assertEquals("댓글  "+String.valueOf(i), ((JSONObject)commentList.get(i-1)).get("contents"));
    	}
    }
    
    // 권한이 없는 상태에서 댓글 삭제 테스트
    @Test
    public void deleteComment_authority_test() throws Exception {
    	MvcResult mvcResult = null;

    	mvcResult = this.mockMvc
	        .perform(post("/comment/deleteComment.do")
	        .param("idx", "3"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
			.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
    }
        
    // 댓글 추천 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void thumbsUp_test() throws Exception {
    	MvcResult mvcResult = null;
    	JSONParser parser = new JSONParser(); 
		JSONObject resultJson = null;
    	
    	mvcResult = this.mockMvc
	        .perform(post("/comment/thumbsUp.do")
	        .param("idx", "1"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
	    	.andReturn();

    	assertEquals("application/json", mvcResult.getResponse().getContentType());
    	
		resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		assertEquals(new Long(1), resultJson.get("checkThumbsup"));
		
		mvcResult = this.mockMvc
				.perform(get("/comment/selectCommentList.do")
				.param("board_idx", "1"))
				.andExpect(status().is(200))
				.andExpect(view().name("jsonView"))
		    	.andReturn();

		resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		JSONObject comment = (JSONObject)((JSONArray)resultJson.get("list")).get(0);

		assertEquals(new Long(1), comment.get("thumbsup_cnt"));
    }
    
    // 댓글 중복 추천 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void thumbsUp_reduplication_test() throws Exception {
    	MvcResult mvcResult = null;
    	JSONParser parser = new JSONParser(); 
		JSONObject resultJson = null;
    	
    	mvcResult = this.mockMvc
	        .perform(post("/comment/thumbsUp.do")
	        .param("idx", "1"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
	    	.andReturn();

    	assertEquals("application/json", mvcResult.getResponse().getContentType());
    	
		resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		assertEquals(new Long(1), resultJson.get("checkThumbsup"));
		
    	mvcResult = this.mockMvc
	        .perform(post("/comment/thumbsUp.do")
	        .param("idx", "1"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
	    	.andReturn();

    	assertEquals("application/json", mvcResult.getResponse().getContentType());
    	
		resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		assertEquals(new Long(0), resultJson.get("checkThumbsup"));

		mvcResult = this.mockMvc
				.perform(get("/comment/selectCommentList.do")
				.param("board_idx", "1"))
				.andExpect(status().is(200))
				.andExpect(view().name("jsonView"))
		    	.andReturn();

		resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		JSONObject comment = (JSONObject)((JSONArray)resultJson.get("list")).get(0);

		assertEquals(new Long(1), comment.get("thumbsup_cnt"));
    }
    
    // 권한이 없는 상태에서 댓글 추천 테스트
    @Test
    public void thumbsUp_authority_test() throws Exception {
    	MvcResult mvcResult = null;

    	mvcResult = this.mockMvc
	        .perform(post("/comment/thumbsUp.do")
	        .param("idx", "1"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
			.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
    }
    
    // 댓글 비추천 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void thumbsDown_test() throws Exception {
    	MvcResult mvcResult = null;
    	JSONParser parser = new JSONParser(); 
		JSONObject resultJson = null;
    	
    	mvcResult = this.mockMvc
	        .perform(post("/comment/thumbsDown.do")
	        .param("idx", "1"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
	    	.andReturn();

    	assertEquals("application/json", mvcResult.getResponse().getContentType());
    	
		resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		assertEquals(new Long(1), resultJson.get("checkThumbsdown"));
		
		mvcResult = this.mockMvc
				.perform(get("/comment/selectCommentList.do")
				.param("board_idx", "1"))
				.andExpect(status().is(200))
				.andExpect(view().name("jsonView"))
		    	.andReturn();

		resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		JSONObject comment = (JSONObject)((JSONArray)resultJson.get("list")).get(0);

		assertEquals(new Long(1), comment.get("thumbsdown_cnt"));
    }
    
    // 댓글 중복 비추천 테스트
    @Test
    @WithUserDetails(value="test", userDetailsServiceBeanName="userService")
    public void thumbsDown_reduplication_test() throws Exception {
    	MvcResult mvcResult = null;
    	JSONParser parser = new JSONParser(); 
		JSONObject resultJson = null;
    	
    	mvcResult = this.mockMvc
	        .perform(post("/comment/thumbsDown.do")
	        .param("idx", "1"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
	    	.andReturn();

    	assertEquals("application/json", mvcResult.getResponse().getContentType());
    	
		resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		assertEquals(new Long(1), resultJson.get("checkThumbsdown"));
		
    	mvcResult = this.mockMvc
	        .perform(post("/comment/thumbsDown.do")
	        .param("idx", "1"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
	    	.andReturn();

    	assertEquals("application/json", mvcResult.getResponse().getContentType());
    	
		resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		assertEquals(new Long(0), resultJson.get("checkThumbsdown"));

		mvcResult = this.mockMvc
			.perform(get("/comment/selectCommentList.do")
			.param("board_idx", "1"))
			.andExpect(status().is(200))
			.andExpect(view().name("jsonView"))
	    	.andReturn();

		resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		JSONObject comment = (JSONObject)((JSONArray)resultJson.get("list")).get(0);

		assertEquals(new Long(1), comment.get("thumbsdown_cnt"));
    }
    
    // 권한이 없는 상태에서 댓글 비추천 테스트
    @Test
    public void thumbsDown_authority_test() throws Exception {
    	MvcResult mvcResult = null;

    	mvcResult = this.mockMvc
	        .perform(post("/comment/thumbsDown.do")
	        .param("idx", "1"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
			.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
    }
}
