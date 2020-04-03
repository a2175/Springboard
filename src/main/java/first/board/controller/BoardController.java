package first.board.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import first.board.service.BoardService;
import first.common.common.CommandMap;
 
@Controller
public class BoardController {
    Logger log = Logger.getLogger(this.getClass());
     
    private BoardService sampleService;
    
    @Autowired
    public BoardController(BoardService sampleService) {
    	this.sampleService = sampleService;
    }
    
    @RequestMapping(value="/board/openBoardList.do")
    public ModelAndView openBoardList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/sample/boardList");
        
        mv.addObject("PAGE_INDEX", commandMap.get("PAGE_INDEX"));
        mv.addObject("KEYWORD", commandMap.get("KEYWORD"));
        
        return mv;
    }
     
    @RequestMapping(value="/board/selectBoardList.do")
    public ModelAndView selectBoardList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
         
        List<Map<String,Object>> list = sampleService.selectBoardList(commandMap.getMap());
        Map<String,Object> totalCount = sampleService.totalCount(commandMap.getMap());
        mv.addObject("list", list);
        if(list.size() > 0){
            mv.addObject("TOTAL", totalCount.get("TOTAL_COUNT"));
        }
        else{
            mv.addObject("TOTAL", 0);
        }
         
        return mv;
    }
    
    @RequestMapping(value="/board/openBoardEGList.do")
    public ModelAndView openBoardEGList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/sample/boardEGList");
        
        Map<String,Object> resultMap = sampleService.selectBoardEGList(commandMap.getMap());
        
        mv.addObject("paginationInfo", (PaginationInfo)resultMap.get("paginationInfo"));
        mv.addObject("list", resultMap.get("result"));
         
        return mv;
    }
    
    @RequestMapping(value="/board/selectBoardSearchList.do")
    public ModelAndView selectBoardSearchList(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("jsonView");
         
        List<Map<String,Object>> list = sampleService.selectBoardSearchList(commandMap.getMap());
        Map<String,Object> searchCount = sampleService.searchCount(commandMap.getMap());
        mv.addObject("list", list);
        if(list.size() > 0){
            mv.addObject("TOTAL", searchCount.get("SEARCH_COUNT"));
        }
        else{
            mv.addObject("TOTAL", 0);
        }
         
        return mv;
    }
    
    @RequestMapping(value="/board/openBoardWrite.do")
    public ModelAndView openBoardWrite(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/sample/boardWrite");
        
        return mv;
    }
    
    @RequestMapping(value="/board/insertBoard.do")
    public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
        
        sampleService.insertBoard(commandMap.getMap(), request);
        
        return mv;
    }
    
    @RequestMapping(value="/board/openBoardDetail.do")
    public ModelAndView openBoardDetail(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/sample/boardDetail");
        
        Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
        mv.addObject("map", map.get("map"));
        mv.addObject("nextmap", map.get("nextmap"));
        mv.addObject("prevmap", map.get("prevmap"));
        mv.addObject("list", map.get("list"));
        mv.addObject("PAGE_INDEX", commandMap.get("PAGE_INDEX"));
        mv.addObject("KEYWORD", commandMap.get("KEYWORD"));
        
        return mv;
    }
    
    @RequestMapping(value="/board/openBoardUpdate.do")
    public ModelAndView openBoardUpdate(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("/sample/boardUpdate");
         
        Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
        mv.addObject("map", map.get("map"));
        mv.addObject("list", map.get("list"));
         
        return mv;
    }
     
    @RequestMapping(value="/board/updateBoard.do")
    public ModelAndView updateBoard(CommandMap commandMap, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardDetail.do");
         
        sampleService.updateBoard(commandMap.getMap(), request);
         
        mv.addObject("IDX", commandMap.get("IDX"));
        return mv;
    }
    
    @RequestMapping(value="/board/deleteBoard.do")
    public ModelAndView deleteBoard(CommandMap commandMap) {
        ModelAndView mv = new ModelAndView("redirect:/board/openBoardList.do");
         
        sampleService.deleteBoard(commandMap.getMap());
         
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