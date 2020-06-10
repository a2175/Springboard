package first.comment.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import first.comment.vo.CommentVO;
import first.common.common.CommandMap;
import first.comment.service.CommentService;
 
@Controller
public class CommentController {
    Logger log = Logger.getLogger(this.getClass());
    
    private CommentService commentService;
    
    @Autowired
    public CommentController(CommentService commentService) {
    	this.commentService = commentService;
    }
    
    // 해당 게시물의 댓글 목록 가져오기 (json)
    @RequestMapping(value="/comment/selectCommentList.do", method=RequestMethod.GET)
    public ModelAndView selectCommentList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");

        List<CommentVO> list = commentService.selectCommentList(commandMap.getMap());
        mv.addObject("list", list);
          
        return mv;
    }
    
    // 댓글 삽입
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/comment/insertComment.do", method=RequestMethod.POST)
    public ModelAndView insertComment(@Valid CommentVO vo, BindingResult result, CommandMap commandMap) {
    	ModelAndView mv = new ModelAndView("jsonView");
    	
    	if(!result.hasErrors()) commentService.insertComment(commandMap.getMap());
    	 
        return mv;
    }
    
    // 댓글 삭제
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/comment/deleteComment.do", method=RequestMethod.POST)
    public ModelAndView deleteComment(CommandMap commandMap) {
		ModelAndView mv = new ModelAndView("jsonView");
		commentService.deleteComment(commandMap.getMap());
		return mv;
    }
    
    // 댓글 추천
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/comment/thumbsUp.do", method=RequestMethod.POST)
    public ModelAndView thumbsUp(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
         
        mv.addObject("checkThumbsup", commentService.insertThumbsup(commandMap.getMap()));
        
        return mv;
    }
    
    // 댓글 비추천
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/comment/thumbsDown.do", method=RequestMethod.POST)
    public ModelAndView thumbsDown(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
      
        mv.addObject("checkThumbsdown", commentService.insertThumbsdown(commandMap.getMap()));
        
        return mv;
    }
}