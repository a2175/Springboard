package first.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import first.common.common.CommandMap;
import first.login.service.LoginService;
import first.login.vo.UserVO;

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
	
    @PreAuthorize("isAnonymous()")
	@RequestMapping(value = "/login.ajax", method=RequestMethod.POST)
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
    
    @RequestMapping(value="/login/doIdDuplicationCheck.do", method=RequestMethod.GET)
    public ModelAndView doIdDuplicationCheck(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
        
        boolean isDuplication = loginService.selectIdCheck(commandMap.getMap());
        mv.addObject("isDuplication", isDuplication);
         
        return mv;
    }
    
    @RequestMapping(value="/login/doNicknameDuplicationCheck.do", method=RequestMethod.GET)
    public ModelAndView doNicknameDuplicationCheck(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
        
        boolean isDuplication = loginService.selectNicknameCheck(commandMap.getMap());
        mv.addObject("isDuplication", isDuplication);
         
        return mv;
    }
    
    @PreAuthorize("isAnonymous()")
    @RequestMapping(value="/login/openLoginSignup.do", method=RequestMethod.GET)
    public ModelAndView openLoginSignup(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/login/loginSignup");
        	
        return mv;
    }
    
    @PreAuthorize("isAnonymous()")
    @RequestMapping(value="/login/doSubmit.do", method=RequestMethod.POST)
    public ModelAndView doSubmit(@Valid UserVO vo, BindingResult result, CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
        
    	if(result.hasErrors() || !vo.getPassword().equals(vo.getRe_password())) {
    		mv.setViewName("redirect:/login/openLoginSignup.do");
    		return mv;
    	}
    	
        loginService.insertUser(commandMap.getMap());
        
        return mv;
    }
}