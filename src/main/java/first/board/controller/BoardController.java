package first.board.controller;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping(value="/board/openBoardList.do")
    public ModelAndView openBoardList() {
        ModelAndView mv = new ModelAndView("/board/boardList");
        
        return mv;
    }
        
    // 페이징 게시글 목록 가져오기 (json)
    @RequestMapping(value="/board/selectBoardList.do")
    public ModelAndView selectBoardList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");

        mv.addAllObjects(boardService.selectBoardList(commandMap.getMap()));
        
        return mv;
    }
    
    // 전자정부 페이징 페이지로 이동
    @RequestMapping(value="/board/openBoardEGList.do")
    public ModelAndView openBoardEGList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/board/boardEGList");
          
        mv.addAllObjects(boardService.selectBoardEGList(commandMap.getMap()));
         
        return mv;
    }
    
    // 검색한 페이징 게시글 목록 가져오기 (json)
    @RequestMapping(value="/board/selectBoardSearchList.do")
    public ModelAndView selectBoardSearchList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
        
        mv.addAllObjects(boardService.selectBoardSearchList(commandMap.getMap()));
         
        return mv;
    }
    
    // 글쓰기 페이지로 이동
    @RequestMapping(value="/board/openBoardWrite.do")
    public ModelAndView openBoardWrite() {
        ModelAndView mv = new ModelAndView("/board/boardWrite");
        
        return mv;
    }
    
    // 게시글 삽입
    @RequestMapping(value="/board/insertBoard.do")
    public ModelAndView insertBoard(@Valid BoardVO vo, BindingResult result, CommandMap commandMap, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
        
    	if(result.hasErrors()) {
    		mv.setViewName("redirect:/board/openBoardWrite.do");
    		return mv;
    	}
        
        boardService.insertBoard(commandMap.getMap(), request);
        
        return mv;
    }
    
    // 게시글 페이지로 이동
    @RequestMapping(value="/board/openBoardDetail.do")
    public ModelAndView openBoardDetail(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/board/boardDetail");
        
        mv.addAllObjects(boardService.selectBoardDetail(commandMap.getMap()));
        boardService.updateHitCnt(commandMap.getMap());
        
        return mv;
    }
    
    // 게시글 수정 페이지로 이동
    @RequestMapping(value="/board/openBoardUpdate.do")
    public ModelAndView openBoardUpdate(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/board/boardUpdate");
         
        mv.addAllObjects(boardService.selectBoardDetail(commandMap.getMap()));
         
        return mv;
    }
    
    // 게시글 수정
    @RequestMapping(value="/board/updateBoard.do")
    public ModelAndView updateBoard(@Valid BoardVO vo, BindingResult result, CommandMap commandMap, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardDetail.do");
         
        if(!result.hasErrors()) boardService.updateBoard(commandMap.getMap(), request);

        mv.addObject("idx", commandMap.get("idx"));
        return mv;
    }
    
    // 게시글 삭제
    @RequestMapping(value="/board/deleteBoard.do")
    public ModelAndView deleteBoard(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
         
        boardService.deleteBoard(commandMap.getMap());
         
        return mv;
    }
    
    @RequestMapping(value="/board/testMapArgumentResolver.do")
    public ModelAndView testMapArgumentResolver(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("");
         
        if(commandMap.isEmpty() == false){
            Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
            Entry<String,Object> entry = null;
            while(iterator.hasNext()){
                entry = iterator.next();
                log.debug("key : "+entry.getKey()+", value : "+entry.getValue());
            }
        }
        return mv;
    }
}