package first.comment.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import first.comment.service.CommentService;
import first.common.common.CommandMap;
 
@Controller
public class CommentController {
    Logger log = Logger.getLogger(this.getClass());
     
    @Resource(name="commentService")
    private CommentService commentService;
    
    @RequestMapping(value="/sample/selectCommentList.do")
    public ModelAndView selectCommentList(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
         
        List<Map<String,Object>> list = commentService.selectCommentList(commandMap.getMap());
        mv.addObject("list", list);
        
        if(list.size() == 0) {
            mv.addObject("TOTAL", 0);
        }
        
        return mv;
    }
    
    @RequestMapping(value="/sample/insertComment.do")
    public ModelAndView insertComment(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        
        commentService.insertComment(commandMap.getMap());
        List<Map<String,Object>> list = commentService.selectCommentList(commandMap.getMap());
        mv.addObject("list", list);
        
        return mv;
    }
    
    @RequestMapping(value="/sample/deleteComment.do")
    public ModelAndView deleteComment(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        
        commentService.deleteComment(commandMap.getMap());
        List<Map<String,Object>> list = commentService.selectCommentList(commandMap.getMap());
        mv.addObject("list", list);
        
        if(list.size() == 0) {
            mv.addObject("TOTAL", 0);
        }
        
        return mv;
    }
    
    @RequestMapping(value="/sample/thumbsUp.do")
    public ModelAndView thumbsUp(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        
        Map<String,Object> map = commentService.checkThumbsup(commandMap.getMap());
        
        if(map == null) {
        	commentService.insertThumbsup(commandMap.getMap());
        	mv.addObject("checkThumbsup", 1);
        }
        else {
        	mv.addObject("checkThumbsup", 0);
        }
        
        return mv;
    }
    
    @RequestMapping(value="/sample/thumbsDown.do")
    public ModelAndView thumbsDown(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        
        Map<String,Object> map = commentService.checkThumbsdown(commandMap.getMap());
        
        if(map == null) {
        	commentService.insertThumbsdown(commandMap.getMap());
        	mv.addObject("checkThumbsdown", 1);
        }
        else {
        	mv.addObject("checkThumbsdown", 0);
        }
        
        return mv;
    }
}