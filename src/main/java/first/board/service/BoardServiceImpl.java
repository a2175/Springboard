package first.board.service;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import first.board.dao.BoardDAO;
import first.board.vo.BoardVO;
import first.common.util.FileUtils;
 
@Service
public class BoardServiceImpl implements BoardService {
    Logger log = Logger.getLogger(this.getClass());
    
    private FileUtils fileUtils; 
    private BoardDAO boardDAO;
    
    @Autowired
    public BoardServiceImpl(FileUtils fileUtils, BoardDAO boardDAO) {
    	this.fileUtils = fileUtils;
    	this.boardDAO = boardDAO;
    }
     
    @Override
    public Map<String,Object> selectBoardList(Map<String, Object> map) {
    	Map<String, Object> resultMap = new HashMap<String,Object>();
    	resultMap.put("list", boardDAO.selectBoardList(map));
    	resultMap.put("total", boardDAO.totalCount());
    	
        return resultMap;
    }
    
    @Override
	public Map<String,Object> selectBoardSearchList(Map<String, Object> map) {
    	Map<String, Object> resultMap = new HashMap<String,Object>();
    	resultMap.put("list", boardDAO.selectBoardSearchList(map));
    	resultMap.put("total", boardDAO.searchCount(map));
    	
        return resultMap;
	}
    
    @Override
	public Map<String, Object> selectBoardEGList(Map<String, Object> map) {
    	return boardDAO.selectBoardEGList(map);
    }
 
    @Override
    public void insertBoard(Map<String, Object> map, HttpServletRequest request) {
        boardDAO.insertBoard(map);
        
        List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
        
        for(int i=0, size=list.size(); i<size; i++){
        	list.get(i).put("ID", map.get("ID"));
            boardDAO.insertFile(list.get(i));
        }
    }
 
    @Override
    public Map<String, Object> selectBoardDetail(Map<String, Object> map) {   		
        Map<String, Object> resultMap = new HashMap<String,Object>();

        resultMap.put("detail", boardDAO.selectBoardDetail(map));
        
        List<BoardVO> nextAndPrev;
        if(map.get("KEYWORD") == null) {
        	nextAndPrev = boardDAO.selectBoardNextAndPrev(map);
        }
        else {
        	nextAndPrev = boardDAO.selectBoardSearchNextAndPrev(map);
        }
        
        int mapidx = Integer.parseInt((String)map.get("idx"));
        for(int i=0; i<nextAndPrev.size(); i++) {
        	int idx = nextAndPrev.get(i).getIdx();
        	if(idx > mapidx) {
        		BoardVO nextMap = nextAndPrev.get(i);
        		resultMap.put("nextmap", nextMap);
        	}
        	else {
        		BoardVO prevMap = nextAndPrev.get(i);
        		resultMap.put("prevmap", prevMap);
        	}
        }
        
        resultMap.put("fileList", boardDAO.selectFileList(map));
         
        return resultMap;
    }
 
    @Override
    public void updateBoard(Map<String, Object> map, HttpServletRequest request) {
        boardDAO.updateBoard(map);
         
        boardDAO.deleteFileList(map);
        List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
        Map<String,Object> tempMap = null;
        for(int i=0, size=list.size(); i<size; i++){
            tempMap = list.get(i);
            if(tempMap.get("IS_NEW").equals("Y")){
            	tempMap.put("ID", map.get("ID"));
                boardDAO.insertFile(tempMap);
            }
            else{
                boardDAO.updateFile(tempMap);
            }
        }
    }
 
    @Override
    public void deleteBoard(Map<String, Object> map) {
    	boardDAO.deleteFileList(map);
        boardDAO.deleteBoard(map);
    }

	@Override
	public void updateHitCnt(Map<String, Object> map) {
		boardDAO.updateHitCnt(map);
	}
}