package first.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import first.common.common.CommandMap;

@Controller
public class CommonController {
	Logger log = Logger.getLogger(this.getClass());

	@RequestMapping(value="/common/openErrorPage.do")
    public ModelAndView openErrorPage(CommandMap commandMap, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/common/commonError");
        
        mv.addObject("MSG", request.getAttribute("MSG"));
        mv.addObject("ADDRESS", request.getAttribute("ADDRESS"));
        
        return mv;
    }
}