package first.board.service;
 
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import first.board.dao.BoardDAO;
import first.board.vo.BoardVO;
import first.board.vo.FileVO;
import first.common.util.CustomFileUtils;

@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
@Service
public class BoardServiceImpl implements BoardService {
    Logger log = Logger.getLogger(this.getClass());
    
    private BoardDAO boardDAO;
    private CustomFileUtils customFileUtils;
    
    @Autowired
    public BoardServiceImpl(BoardDAO boardDAO, CustomFileUtils customFileUtils) {
    	this.boardDAO = boardDAO;
    	this.customFileUtils = customFileUtils;
	}
     
    @Override
    public Map<String,Object> selectBoardList(Map<String, Object> map) {
    	Map<String, Object> resultMap = new HashMap<String,Object>();
    	
        String strPageIndex = (String)map.get("pageIdx"); 
        String strPageRow = (String)map.get("pageRow");

        int nPageIndex = 0;
        int nPageRow = 15;
                 
        nPageIndex = StringUtils.isEmpty(strPageIndex) ? nPageIndex : Integer.parseInt(strPageIndex) - 1;
        nPageRow = StringUtils.isEmpty(strPageRow) ? nPageRow : Integer.parseInt(strPageRow);
        
        map.put("start", (nPageIndex * nPageRow));
        map.put("end", nPageRow);
    	
    	resultMap.put("list", boardDAO.selectBoardList(map));
    	resultMap.put("total", boardDAO.selectBoardTotalCount());
    	
        return resultMap;
    }
    
    @Override
	public Map<String,Object> selectBoardSearchList(Map<String, Object> map) {
    	Map<String, Object> resultMap = new HashMap<String,Object>();
    	
        String strPageIndex = (String)map.get("pageIdx"); 
        String strPageRow = (String)map.get("pageRow");

        int nPageIndex = 0;
        int nPageRow = 15;
                 
        nPageIndex = StringUtils.isEmpty(strPageIndex) ? nPageIndex : Integer.parseInt(strPageIndex) - 1;
        nPageRow = StringUtils.isEmpty(strPageRow) ? nPageRow : Integer.parseInt(strPageRow);
        
        map.put("start", (nPageIndex * nPageRow));
        map.put("end", nPageRow);
    	
    	resultMap.put("list", boardDAO.selectBoardSearchList(map));
    	resultMap.put("total", boardDAO.selectBoardSearchTotalCount(map));
    	
        return resultMap;
	}
    
    @Override
	public Map<String, Object> selectBoardEGList(Map<String, Object> map) {
		PaginationInfo paginationInfo = null;
        
        if(map.containsKey("currentPageNo") == false || StringUtils.isEmpty(map.get("currentPageNo")) == true)
            map.put("currentPageNo", "1");
         
        paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(Integer.parseInt(map.get("currentPageNo").toString()));
        if(map.containsKey("pageRow") == false || StringUtils.isEmpty(map.get("pageRow")) == true){
            paginationInfo.setRecordCountPerPage(15);
        }
        else{
            paginationInfo.setRecordCountPerPage(Integer.parseInt(map.get("pageRow").toString()));
        }
        paginationInfo.setPageSize(10);
        
        int start = paginationInfo.getFirstRecordIndex();
        int end = paginationInfo.getRecordCountPerPage();
        map.put("start",start);
        map.put("end",end);
    	
        Map<String,Object> returnMap = new HashMap<String,Object>();
        
        List<BoardVO> list = boardDAO.selectBoardList(map);
        int totalCount = boardDAO.selectBoardTotalCount();
         
        if(list.size() == 0){            
            if(paginationInfo != null){
                paginationInfo.setTotalRecordCount(0);
                returnMap.put("paginationInfo", paginationInfo);
            }
        }
        else{
            if(paginationInfo != null){
                paginationInfo.setTotalRecordCount(totalCount);
                returnMap.put("paginationInfo", paginationInfo);
            }
        }
        
        returnMap.put("list", list);
        return returnMap;
    }
    
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    @Override
    public void insertBoard(Map<String, Object> map, MultipartHttpServletRequest multipartRequest) {
        boardDAO.insertBoard(map);
        
        List<Map<String,Object>> fileList = customFileUtils.parseInsertFileInfo(map, multipartRequest);
        
        for(Map<String,Object> file : fileList) {
        	boardDAO.insertFile(file);
        }
    }
 
    @Override
    public Map<String, Object> selectBoardDetail(Map<String, Object> map) {   		
        Map<String, Object> resultMap = new HashMap<String,Object>();

        resultMap.put("detail", boardDAO.selectBoardDetail(map));
        
    	BoardVO nextBoard;
    	BoardVO prevBoard;
        if(map.get("keyword") == null) {
        	nextBoard = boardDAO.selectNextBoard(map);
        	prevBoard = boardDAO.selectPrevBoard(map);
        }
        else {
        	nextBoard = boardDAO.selectNextSearchBoard(map);
        	prevBoard = boardDAO.selectPrevSearchBoard(map);
        }
        
    	resultMap.put("nextBoard", nextBoard);
    	resultMap.put("prevBoard", prevBoard);
         
        return resultMap;
    }
    
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    @Override
    public void updateBoard(Map<String, Object> map, MultipartHttpServletRequest multipartRequest) {
        boardDAO.updateBoard(map);
        boardDAO.deleteFileList(map);
        
        List<Map<String,Object>> fileList = customFileUtils.parseUpdateFileInfo(map, multipartRequest);

    	for(Map<String,Object> file : fileList) {
            if(file.get("IS_NEW").equals("Y")) {
                boardDAO.insertFile(file);
            }
            else {
                boardDAO.updateFile(file);
            }
        }
    }
    
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
    @Override
    public void deleteBoard(Map<String, Object> map) {
    	boardDAO.deleteFileList(map);
        boardDAO.deleteBoard(map);
    }
    
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public void updateHitCnt(Map<String, Object> map) {
		boardDAO.updateHitCnt(map);
	}
	
	@Override
	public void downloadFile(Map<String, Object> map, HttpServletResponse response) {
		FileVO fileInfo = boardDAO.selectFileInfo(map);
		String storedFileName = fileInfo.getStored_file_name();
		String originalFileName = fileInfo.getOriginal_file_name();
		
		try {
			byte fileByte[] = customFileUtils.readFileToByteArray(storedFileName);
			response.setContentType("application/octet-stream");
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}