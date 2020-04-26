package first.login.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import first.common.common.CommandMap;
import first.login.service.LoginService;

@Controller
public class LoginController {
	Logger log = Logger.getLogger(this.getClass());
    
    private LoginService loginService;
    
    @Autowired
    public LoginController(LoginService loginService) {
    	this.loginService = loginService;
    }
    
    @Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	SecurityContextRepository repository;

	@RequestMapping(value = "/login.ajax", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap login(HttpServletRequest request, HttpServletResponse response,
		@RequestParam(value = "loginid") String username,
		@RequestParam(value = "loginpwd") String password) {

		ModelMap map = new ModelMap();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
			username, password);

		try {
			// 로그인
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			repository.saveContext(SecurityContextHolder.getContext(), request, response);

			map.put("success", true);
			map.put("returnUrl", getReturnUrl(request, response));
		} catch (BadCredentialsException e) {
			map.put("success", false);
			map.put("message", e.getMessage());
		}

		return map;
	}

	/**
	 * 로그인 하기 전의 요청했던 URL을 알아낸다.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String getReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
			return request.getSession().getServletContext().getContextPath();
		}
		return savedRequest.getRedirectUrl();
	}
    
    @RequestMapping(value="/login/openLoginPage.do")
    public ModelAndView openLoginPage(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/login/loginPage");
        
        return mv;
    }
    
    @RequestMapping(value="/login/doLogin.do")
    public ModelAndView doLogin(CommandMap commandMap, HttpServletRequest request) {
    	ModelAndView mv = new ModelAndView("/board/boardList");
    		
    	Map<String,Object> userInfo = loginService.selectLoginCheck(commandMap.getMap());
        if(userInfo == null) {
        	mv.addObject("dologin", false);
        }      
        else {
        	HttpSession session = request.getSession();
        	session.setAttribute("ID", userInfo.get("ID"));
        	session.setAttribute("NICKNAME", userInfo.get("NICKNAME"));
        }
        
        mv.addObject("pageIdx", commandMap.get("pageIdx"));
        mv.addObject("keyword", commandMap.get("keyword"));
        
        return mv;
    }
    
    @RequestMapping(value="/login/doLogout.do")
    public ModelAndView doLogout(CommandMap commandMap, HttpServletRequest request) {
    	ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
    		
        HttpSession session = request.getSession();
        session.invalidate();
        
        return mv;
    }
    
    @RequestMapping(value="/login/doIdDuplicationCheck.do")
    public ModelAndView doIdDuplicationCheck(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
        
        boolean isDuplication = loginService.selectIdCheck(commandMap.getMap());
        mv.addObject("isDuplication", isDuplication);
         
        return mv;
    }
    
    @RequestMapping(value="/login/doNicknameDuplicationCheck.do")
    public ModelAndView doNicknameDuplicationCheck(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
        
        boolean isDuplication = loginService.selectNicknameCheck(commandMap.getMap());
        mv.addObject("isDuplication", isDuplication);
         
        return mv;
    }
    
    @RequestMapping(value="/login/openLoginSignup.do")
    public ModelAndView openLoginSignup(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/login/loginSignup");
        	
        return mv;
    }
    
    @RequestMapping(value="/login/doSubmit.do")
    public ModelAndView doSubmit(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
        
        loginService.insertUser(commandMap.getMap());
        
        return mv;
    }
    
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login() {

        return "/login/login";
    }
}