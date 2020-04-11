package first.comment.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import first.comment.vo.CommentVO;
import first.comment.service.CommentService;
 
@Controller
public class CommentController {
    Logger log = Logger.getLogger(this.getClass());
    
    private CommentService commentService;
    
    @Autowired
    public CommentController(CommentService commentService) {
    	this.commentService = commentService;
    }
    
    @RequestMapping(value="/comment/selectCommentList.do")
    public ModelAndView selectCommentList(int board_idx) {
        ModelAndView mv = new ModelAndView("jsonView");

        List<CommentVO> list = commentService.selectCommentList(board_idx);
        mv.addObject("list", list);
          
        return mv;
    }
    
    @RequestMapping(value="/comment/insertComment.do")
    public ModelAndView insertComment(CommentVO vo) {
    	ModelAndView mv = new ModelAndView("jsonView");
        commentService.insertComment(vo);
        return mv;
    }
    
    @RequestMapping(value="/comment/deleteComment.do")
    public ModelAndView deleteComment(int idx) {
		ModelAndView mv = new ModelAndView("jsonView");
		commentService.deleteComment(idx);
		return mv;
    }
    
    @RequestMapping(value="/comment/thumbsUp.do")
    public ModelAndView thumbsUp(CommentVO vo) {
        ModelAndView mv = new ModelAndView("jsonView");
        
        CommentVO commntVO = commentService.checkThumbsup(vo);
        
        if(commntVO == null) {
        	commentService.insertThumbsup(vo);
        	mv.addObject("checkThumbsup", 1);
        }
        else {
        	mv.addObject("checkThumbsup", 0);
        }
        
        return mv;
    }
    
    @RequestMapping(value="/comment/thumbsDown.do")
    public ModelAndView thumbsDown(CommentVO vo) {
        ModelAndView mv = new ModelAndView("jsonView");
        
        CommentVO commntVO = commentService.checkThumbsdown(vo);
        
        if(commntVO == null) {
        	commentService.insertThumbsdown(vo);
        	mv.addObject("checkThumbsdown", 1);
        }
        else {
        	mv.addObject("checkThumbsdown", 0);
        }
        
        return mv;
    }
}