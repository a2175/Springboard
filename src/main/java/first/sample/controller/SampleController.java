package first.sample.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import first.common.common.CommandMap;
import first.sample.service.SampleService;
 
@Controller
public class SampleController {
    Logger log = Logger.getLogger(this.getClass());
     
    @Resource(name="sampleService")
    private SampleService sampleService;
     
    @RequestMapping(value="/sample/openBoardList.do")
    public ModelAndView openBoardList(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/boardList");
        
        mv.addObject("PAGE_INDEX", commandMap.get("PAGE_INDEX"));
        mv.addObject("KEYWORD", commandMap.get("KEYWORD"));
        
        return mv;
    }
     
    @RequestMapping(value="/sample/selectBoardList.do")
    public ModelAndView selectBoardList(CommandMap commandMap) throws Exception{
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
    
    @RequestMapping(value="/sample/openBoardEGList.do")
    public ModelAndView openBoardEGList(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/boardEGList");
        
        Map<String,Object> resultMap = sampleService.selectBoardEGList(commandMap.getMap());
        
        mv.addObject("paginationInfo", (PaginationInfo)resultMap.get("paginationInfo"));
        mv.addObject("list", resultMap.get("result"));
         
        return mv;
    }
    
    @RequestMapping(value="/sample/selectBoardSearchList.do")
    public ModelAndView selectBoardSearchList(CommandMap commandMap) throws Exception{
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
    
    @RequestMapping(value="/sample/openBoardWrite.do")
    public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/boardWrite");
        
        return mv;
    }
    
    @RequestMapping(value="/sample/insertBoard.do")
    public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
        
        sampleService.insertBoard(commandMap.getMap(), request);
        
        return mv;
    }
    
    @RequestMapping(value="/sample/openBoardDetail.do")
    public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception{
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
    
    @RequestMapping(value="/sample/openBoardUpdate.do")
    public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("/sample/boardUpdate");
         
        Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap());
        mv.addObject("map", map.get("map"));
        mv.addObject("list", map.get("list"));
         
        return mv;
    }
     
    @RequestMapping(value="/sample/updateBoard.do")
    public ModelAndView updateBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
         
        sampleService.updateBoard(commandMap.getMap(), request);
         
        mv.addObject("IDX", commandMap.get("IDX"));
        return mv;
    }
    
    @RequestMapping(value="/sample/deleteBoard.do")
    public ModelAndView deleteBoard(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
         
        sampleService.deleteBoard(commandMap.getMap());
         
        return mv;
    }
    
    @RequestMapping(value="/sample/selectCommentList.do")
    public ModelAndView selectCommentList(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
         
        List<Map<String,Object>> list = sampleService.selectCommentList(commandMap.getMap());
        mv.addObject("list", list);
        
        if(list.size() == 0) {
            mv.addObject("TOTAL", 0);
        }
        
        return mv;
    }
    
    @RequestMapping(value="/sample/insertComment.do")
    public ModelAndView insertComment(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        
        sampleService.insertComment(commandMap.getMap());
        List<Map<String,Object>> list = sampleService.selectCommentList(commandMap.getMap());
        mv.addObject("list", list);
        
        return mv;
    }
    
    @RequestMapping(value="/sample/deleteComment.do")
    public ModelAndView deleteComment(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        
        sampleService.deleteComment(commandMap.getMap());
        List<Map<String,Object>> list = sampleService.selectCommentList(commandMap.getMap());
        mv.addObject("list", list);
        
        if(list.size() == 0) {
            mv.addObject("TOTAL", 0);
        }
        
        return mv;
    }
    
    @RequestMapping(value="/sample/thumbsUp.do")
    public ModelAndView thumbsUp(CommandMap commandMap) throws Exception{
        ModelAndView mv = new ModelAndView("jsonView");
        
        Map<String,Object> map = sampleService.checkThumbsup(commandMap.getMap());
        
        if(map == null) {
        	sampleService.insertThumbsup(commandMap.getMap());
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
        
        Map<String,Object> map = sampleService.checkThumbsdown(commandMap.getMap());
        
        if(map == null) {
        	sampleService.insertThumbsdown(commandMap.getMap());
        	mv.addObject("checkThumbsdown", 1);
        }
        else {
        	mv.addObject("checkThumbsdown", 0);
        }
        
        return mv;
    }
    
    @RequestMapping(value="/sample/testMapArgumentResolver.do")
    public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception{
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