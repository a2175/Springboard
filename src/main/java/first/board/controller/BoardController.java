package first.board.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import first.board.service.BoardService;
import first.board.vo.BoardVO;
import first.common.common.CommandMap;
 
@Controller
public class BoardController {
    Logger log = Logger.getLogger(this.getClass());
     
    private BoardService boardService;
    
    @Autowired
    public BoardController(BoardService boardService) {
    	this.boardService = boardService;
    }
    
    // 게시판 페이지로 이동
    @RequestMapping(value="/board/openBoardList.do", method=RequestMethod.GET)
    public ModelAndView openBoardList() {
        ModelAndView mv = new ModelAndView("/board/boardList");
        
        return mv;
    }
        
    // 페이징 게시글 목록 가져오기 (json)
    @RequestMapping(value="/board/selectBoardList.do", method=RequestMethod.GET)
    public ModelAndView selectBoardList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addAllObjects(boardService.selectBoardList(commandMap.getMap()));
        
        return mv;
    }
    
    // 전자정부 페이징 페이지로 이동
    @RequestMapping(value="/board/openBoardEGList.do", method=RequestMethod.GET)
    public ModelAndView openBoardEGList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/board/boardEGList");
          
        mv.addAllObjects(boardService.selectBoardEGList(commandMap.getMap()));
        
        return mv;
    }
    
    // 검색한 페이징 게시글 목록 가져오기 (json)
    @RequestMapping(value="/board/selectBoardSearchList.do", method=RequestMethod.GET)
    public ModelAndView selectBoardSearchList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
        
        mv.addAllObjects(boardService.selectBoardSearchList(commandMap.getMap()));
         
        return mv;
    }
    
    // 글쓰기 페이지로 이동
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/board/openBoardWrite.do", method=RequestMethod.GET)
    public ModelAndView openBoardWrite() {
        ModelAndView mv = new ModelAndView("/board/boardWrite");
        
        return mv;
    }
    
    // 게시글 삽입
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/board/insertBoard.do", method=RequestMethod.POST)
    public ModelAndView insertBoard(@Valid BoardVO vo, BindingResult result, CommandMap commandMap, MultipartHttpServletRequest multipartRequest) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
        
    	if(result.hasErrors()) {
    		mv.setViewName("redirect:/board/openBoardWrite.do");
    		return mv;
    	}
        
        boardService.insertBoard(commandMap.getMap(), multipartRequest);
        vo.setIdx(Integer.valueOf(commandMap.get("idx").toString()));
        
        return mv;
    }
    
    // 게시글 페이지로 이동
    @RequestMapping(value="/board/openBoardDetail.do", method=RequestMethod.GET)
    public ModelAndView openBoardDetail(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/board/boardDetail");
        
        mv.addAllObjects(boardService.selectBoardDetail(commandMap.getMap()));
        boardService.updateHitCnt(commandMap.getMap());
        
        return mv;
    }
    
    // 게시글 수정 페이지로 이동
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/board/openBoardUpdate.do", method=RequestMethod.GET)
    public ModelAndView openBoardUpdate(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/board/boardUpdate");
         
        mv.addAllObjects(boardService.selectBoardDetail(commandMap.getMap()));
         
        return mv;
    }
    
    // 게시글 수정
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/board/updateBoard.do", method=RequestMethod.POST)
    public ModelAndView updateBoard(@Valid BoardVO vo, BindingResult result, CommandMap commandMap, MultipartHttpServletRequest multipartRequest) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardDetail.do");
         
        if(!result.hasErrors()) boardService.updateBoard(commandMap.getMap(), multipartRequest);

        mv.addObject("idx", commandMap.get("idx"));
        return mv;
    }
    
    // 게시글 삭제
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/board/deleteBoard.do", method=RequestMethod.POST)
    public ModelAndView deleteBoard(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
         
        boardService.deleteBoard(commandMap.getMap());
         
        return mv;
    }
    
    // 파일 다운로드
	@RequestMapping(value="/board/downloadFile.do", method=RequestMethod.GET)
	public void downloadFile(CommandMap commandMap, HttpServletResponse response) {
		boardService.downloadFile(commandMap.getMap(), response);
	}
}