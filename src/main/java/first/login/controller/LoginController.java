package first.login.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import first.common.common.CommandMap;
import first.login.service.LoginService;

@Controller
public class LoginController {
	Logger log = Logger.getLogger(this.getClass());
    
    @Resource(name="loginService")
    private LoginService loginService;
    
    @RequestMapping(value="/login/openLoginPage.do")
    public ModelAndView openLoginPage(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/login/loginPage");
        
        return mv;
    }
    
    @RequestMapping(value="/login/doLogin.do")
    public ModelAndView doLogin(CommandMap commandMap, HttpServletRequest request) throws Exception{
    	ModelAndView mv = new ModelAndView("/sample/boardList");
    		
    	Map<String,Object> userInfo = loginService.selectLoginCheck(commandMap.getMap());
        if(userInfo == null) {
        	mv.addObject("dologin", false);
        }      
        else {
        	HttpSession session = request.getSession();
        	session.setAttribute("ID", userInfo.get("ID"));
        	session.setAttribute("NICKNAME", userInfo.get("NICKNAME"));
        }
        
        mv.addObject("PAGE_INDEX", commandMap.get("PAGE_INDEX"));
        mv.addObject("KEYWORD", commandMap.get("KEYWORD"));
        
        return mv;
    }
    
    @RequestMapping(value="/login/doLogout.do")
    public ModelAndView doLogout(CommandMap commandMap, HttpServletRequest request) throws Exception{
    	ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
    		
        HttpSession session = request.getSession();
        session.invalidate();
        
        return mv;
    }
    
    @RequestMapping(value="/login/doIdDuplicationCheck.do")
    public ModelAndView doIdDuplicationCheck(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        
        boolean isDuplication = loginService.selectIdCheck(commandMap.getMap());
        mv.addObject("isDuplication", isDuplication);
         
        return mv;
    }
    
    @RequestMapping(value="/login/doNicknameDuplicationCheck.do")
    public ModelAndView doNicknameDuplicationCheck(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        
        boolean isDuplication = loginService.selectNicknameCheck(commandMap.getMap());
        mv.addObject("isDuplication", isDuplication);
         
        return mv;
    }
    
    @RequestMapping(value="/login/openLoginSignup.do")
    public ModelAndView openLoginSignup(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/login/loginSignup");
        	
        return mv;
    }
    
    @RequestMapping(value="/login/doSubmit.do")
    public ModelAndView doSubmit(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
        
        loginService.insertUser(commandMap.getMap());
        
        return mv;
    }
}