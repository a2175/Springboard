package first;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;
public class LoginControllerTest extends TestConfig {
	
	// 로그인 테스트
	@Test
	public void login_test() throws Exception {
		MvcResult mvcResult = null;
		
		mvcResult = this.mockMvc
            .perform(post("/login.ajax")
	        .param("loginid","test")
	        .param("loginpwd","0000"))
			.andExpect(status().is(200))
			.andReturn();
		
		assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
		
		JSONParser parser = new JSONParser();      
		JSONObject resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		assertEquals(true, resultJson.get("success"));
	}
	
	// 로그인한 상태에서 로그인 시도 테스트
	@Test
	@WithUserDetails(value="test", userDetailsServiceBeanName="userService")
	public void login_twice_test() throws Exception {
		MvcResult mvcResult = null;
		
		mvcResult = this.mockMvc
            .perform(post("/login.ajax")
	        .param("loginid","test")
	        .param("loginpwd","0000"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
			.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
	}

	// 회원가입 페이지로 이동 테스트
	@Test
	public void openLoginSignup_test() throws Exception {
		this.mockMvc
	        .perform(get("/login/openLoginSignup.do"))
			.andExpect(status().is(200))
			.andExpect(view().name("/login/loginSignup"));
	}
	
	// 로그인한 상태에서 회원가입 페이지로 이동 테스트
	@Test
	@WithUserDetails(value="test", userDetailsServiceBeanName="userService")
	public void openLoginSignup_logged_in_test() throws Exception {
		MvcResult mvcResult = null;
		
		mvcResult = this.mockMvc
            .perform(get("/login/openLoginSignup.do"))
			.andExpect(status().is(403))
			.andExpect(view().name("/common/exception"))
			.andReturn();
    	
    	assertEquals(AccessDeniedException.class, mvcResult.getResolvedException().getClass());
    	assertEquals("Access is denied", mvcResult.getResolvedException().getMessage());
	}
	
	// id 중복 체크 테스트
	@Test
	public void doIdDuplicationCheck_test() throws Exception {
		MvcResult mvcResult = null;
		
		mvcResult = this.mockMvc
            .perform(get("/login/doIdDuplicationCheck.do")
    		.param("id", "test"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("jsonView"))
			.andReturn();
		
		assertEquals("application/json", mvcResult.getResponse().getContentType());
		
		JSONParser parser = new JSONParser();      
		JSONObject resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		assertEquals(true, resultJson.get("isDuplication"));
	}
	
	// nickname 중복 체크 테스트
	@Test
	public void doNicknameDuplicationCheck_test() throws Exception {
		MvcResult mvcResult = null;
		
		mvcResult = this.mockMvc
            .perform(get("/login/doNicknameDuplicationCheck.do")
    		.param("nickname", "test"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("jsonView"))
			.andReturn();
		
		assertEquals("application/json", mvcResult.getResponse().getContentType());
		
		JSONParser parser = new JSONParser();      
		JSONObject resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		assertEquals(true, resultJson.get("isDuplication"));
	}
	
	// 회원가입  테스트
	@Test
	public void sign_up_test() throws Exception {
		MvcResult mvcResult = null;

		this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", "insertTest")
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", "insertTest")
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/board/openBoardList.do"));
		
		mvcResult = this.mockMvc
            .perform(post("/login.ajax")
	        .param("loginid","insertTest")
	        .param("loginpwd","q1w2e3r4A!"))
			.andExpect(status().is(200))
			.andReturn();
		
		assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
		
		JSONParser parser = new JSONParser();      
		JSONObject resultJson = (JSONObject)parser.parse(mvcResult.getResponse().getContentAsString());
		assertEquals(true, resultJson.get("success"));
	}
	
	// 회원가입 id 검증 테스트
	@Test
	public void sign_up_id_validation_test() throws Exception {
		MvcResult mvcResult = null;
    	String id = "";
    	
    	// scenario 1) 길이가 짧은 경우
    	id = "abc";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", id)
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", "insertTest")
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'id'"));
    	
    	// scenario 2) 길이가 긴 경우
    	id = "abcdefghijk";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", id)
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", "insertTest")
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'id'"));
    	
    	// scenario 3-1) 한글이 포함 된 경우
    	id = "abcd가efg";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", id)
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", "insertTest")
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'id'"));
    	
    	// scenario 3-2) 한글이 포함 된 경우
    	id = "abcdㄱefg";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", id)
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", "insertTest")
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'id'"));
    	
    	// scenario 4) 특수문자가 포함 된 경우
    	id = "abcd@efgh";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", id)
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", "insertTest")
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'id'"));
	}
	
	// 회원가입 password 검증 테스트
	@Test
	public void sign_up_password_validation_test() throws Exception {
		MvcResult mvcResult = null;
    	String password = "";
    	
    	// scenario 1) 소문자 알파벳을 포함 않했을 경우
    	password = "Q1W2E3R4!";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", "insertTest")
    		.param("password", password)
    		.param("re_password", password)
    		.param("nickname", "insertTest")
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'password'"));
    	
    	// scenario 2) 대문자 알파벳을 포함 않했을 경우
    	password = "q1w2e3r4!";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", "insertTest")
    		.param("password", password)
    		.param("re_password", password)
    		.param("nickname", "insertTest")
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'password'"));
    	
    	// scenario 3) 특수문자를 포함 않했을 경우
    	password = "q1w2e3r4A";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", "insertTest")
    		.param("password", password)
    		.param("re_password", password)
    		.param("nickname", "insertTest")
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'password'"));

    	// scenario 4) 숫자를 포함 않했을 경우
    	password = "qwertyA!";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", "insertTest")
    		.param("password", password)
    		.param("re_password", password)
    		.param("nickname", "insertTest")
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'password'"));
	}
	
	// 회원가입 nickname 검증 테스트
	@Test
	public void sign_up_nickname_validation_test() throws Exception {
		MvcResult mvcResult = null;
    	String nickname = "";
    	
    	// scenario 1) 길이가 짧은 경우
    	nickname = "qwe";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", "insertTest")
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", nickname)
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'nickname'"));
    	
    	// scenario 2) 길이가 긴 경우
    	nickname = "abcdefghijkln";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", "insertTest")
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", nickname)
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'nickname'"));
    	
    	// scenario 3) 한글 초성 글자가 포함된 경우
    	nickname = "abcdㄱ";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", "insertTest")
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", nickname)
    		.param("email", "test@gmail.com"))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'nickname'"));
	}
	
	// 회원가입 email 검증 테스트
	@Test
	public void sign_up_email_validation_test() throws Exception {
		MvcResult mvcResult = null;
    	String email = "";
    	
    	// scenario 1)
    	email = "test";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", "insertTest")
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", "insertTest")
    		.param("email", email))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'email'"));
    	
    	// scenario 2)
    	email = "test@.com";
    	mvcResult = this.mockMvc
            .perform(post("/login/doSubmit.do")
    		.param("id", "insertTest")
    		.param("password", "q1w2e3r4A!")
    		.param("re_password", "q1w2e3r4A!")
    		.param("nickname", "insertTest")
    		.param("email", email))
	        .andExpect(status().is(302))
	        .andExpect(view().name("redirect:/login/openLoginSignup.do"))
			.andReturn();
    	
    	assertEquals(true, mvcResult.getModelAndView().toString().contains("Field error in object 'userVO' on field 'email'"));
	}
}
