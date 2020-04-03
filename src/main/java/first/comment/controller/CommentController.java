package first.comment.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import first.comment.service.CommentService;
import first.common.common.CommandMap;
 
@Controller
public class CommentController {
    Logger log = Logger.getLogger(this.getClass());
    
    private CommentService commentService;
    
    @Autowired
    public CommentController(CommentService commentService) {
    	this.commentService = commentService;
    }
    
    @RequestMapping(value="/comment/selectCommentList.do")
    public ModelAndView selectCommentList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
         
        List<Map<String,Object>> list = commentService.selectCommentList(commandMap.getMap());
        mv.addObject("list", list);
        
        if(list.size() == 0) {
            mv.addObject("TOTAL", 0);
        }
        
        return mv;
    }
    
    @RequestMapping(value="/comment/insertComment.do")
    public ModelAndView insertComment(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
        
        commentService.insertComment(commandMap.getMap());
        List<Map<String,Object>> list = commentService.selectCommentList(commandMap.getMap());
        mv.addObject("list", list);
        
        return mv;
    }
    
    @RequestMapping(value="/comment/deleteComment.do")
    public ModelAndView deleteComment(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
        
        commentService.deleteComment(commandMap.getMap());
        List<Map<String,Object>> list = commentService.selectCommentList(commandMap.getMap());
        mv.addObject("list", list);
        
        if(list.size() == 0) {
            mv.addObject("TOTAL", 0);
        }
        
        return mv;
    }
    
    @RequestMapping(value="/comment/thumbsUp.do")
    public ModelAndView thumbsUp(CommandMap commandMap) {
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
    
    @RequestMapping(value="/comment/thumbsDown.do")
    public ModelAndView thumbsDown(CommandMap commandMap) {
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