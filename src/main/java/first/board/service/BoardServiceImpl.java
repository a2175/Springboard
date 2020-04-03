package first.board.service;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import first.board.dao.BoardDAO;
import first.common.util.FileUtils;
 
@Service
public class BoardServiceImpl implements BoardService {
    Logger log = Logger.getLogger(this.getClass());
    
    private FileUtils fileUtils; 
    private BoardDAO sampleDAO;
    
    @Autowired
    public BoardServiceImpl(FileUtils fileUtils, BoardDAO sampleDAO) {
    	this.fileUtils = fileUtils;
    	this.sampleDAO = sampleDAO;
    }
     
    @Override
    public List<Map<String, Object>> selectBoardList(Map<String, Object> map) {
        return sampleDAO.selectBoardList(map);
    }
    
    @Override
	public List<Map<String, Object>> selectBoardSearchList(Map<String, Object> map) {
    	return sampleDAO.selectBoardSearchList(map);
	}
    
    @Override
	public Map<String, Object> selectBoardEGList(Map<String, Object> map) {
    	return sampleDAO.selectBoardEGList(map);
    }
 
    @Override
    public void insertBoard(Map<String, Object> map, HttpServletRequest request) {
        sampleDAO.insertBoard(map);
        
        List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
        
        for(int i=0, size=list.size(); i<size; i++){
        	list.get(i).put("ID", map.get("ID"));
            sampleDAO.insertFile(list.get(i));
        }
    }
 
    @Override
    public Map<String, Object> selectBoardDetail(Map<String, Object> map) {
    	if(map.get("isUpdate") == null)
    		sampleDAO.updateHitCnt(map);
        Map<String, Object> resultMap = new HashMap<String,Object>();
        Map<String, Object> Map = sampleDAO.selectBoardDetail(map);
        resultMap.put("map", Map);
        
        List<Map<String,Object>> testlist;
        if(map.get("KEYWORD") == null) {
        	testlist = sampleDAO.selectBoardNextAndPrev(map);
        }
        else {
        	testlist = sampleDAO.selectBoardSearchNextAndPrev(map);
        }
        
        int mapidx = Integer.parseInt(map.get("IDX").toString());
        for(int i=0; i<testlist.size(); i++) {
        	int idx = Integer.parseInt(testlist.get(i).get("IDX").toString());
        	if(idx > mapidx) {
        		Map<String, Object> nextMap = testlist.get(i);
        		resultMap.put("nextmap", nextMap);
        	}
        	else {
        		Map<String, Object> prevMap = testlist.get(i);
        		resultMap.put("prevmap", prevMap);
        	}
        }
        
        List<Map<String,Object>> list = sampleDAO.selectFileList(map);
        resultMap.put("list", list);
         
        return resultMap;
    }
 
    @Override
    public void updateBoard(Map<String, Object> map, HttpServletRequest request) {
        sampleDAO.updateBoard(map);
         
        sampleDAO.deleteFileList(map);
        List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
        Map<String,Object> tempMap = null;
        for(int i=0, size=list.size(); i<size; i++){
            tempMap = list.get(i);
            if(tempMap.get("IS_NEW").equals("Y")){
            	tempMap.put("ID", map.get("ID"));
                sampleDAO.insertFile(tempMap);
            }
            else{
                sampleDAO.updateFile(tempMap);
            }
        }
    }
 
    @Override
    public void deleteBoard(Map<String, Object> map) {
    	sampleDAO.deleteFileList(map);
        sampleDAO.deleteBoard(map);
    }
    
    @Override
    public Map<String, Object> totalCount(Map<String, Object> map) {
    	return sampleDAO.totalCount(map);
    }

	@Override
	public Map<String, Object> searchCount(Map<String, Object> map) {
		return sampleDAO.searchCount(map);
	}
}