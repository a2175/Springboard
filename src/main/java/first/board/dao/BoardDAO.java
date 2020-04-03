package first.board.dao;
 
import java.util.List;
import java.util.Map;
 
import org.springframework.stereotype.Repository;
 
import first.common.dao.AbstractDAO;

@SuppressWarnings("unchecked")
@Repository
public class BoardDAO extends AbstractDAO{

	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) {
	    return (List<Map<String, Object>>)selectPagingList("sample.selectBoardList", map);
	}
	
	public List<Map<String, Object>> selectBoardSearchList(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectPagingList("sample.selectBoardSearchList", map);
	}
	
	public Map<String, Object> selectBoardEGList(Map<String, Object> map) {
	    return (Map<String, Object>)selectEGPagingList("sample.selectBoardList", map);
	}
	
    public void insertBoard(Map<String, Object> map) {
        insert("sample.insertBoard", map);
    }
    
    public void insertFile(Map<String, Object> map) {
        insert("sample.insertFile", map);
    }

    public void updateHitCnt(Map<String, Object> map) {
        update("sample.updateHitCnt", map);
    }
     
    public Map<String, Object> selectBoardDetail(Map<String, Object> map) {
        return (Map<String, Object>) selectOne("sample.selectBoardDetail", map);
    }

	public void updateBoard(Map<String, Object> map) {
		update("sample.updateBoard", map);
	}

	public void deleteBoard(Map<String, Object> map) {
		update("sample.deleteBoard", map);		
	}

	public List<Map<String, Object>> selectFileList(Map<String, Object> map) {
	    return (List<Map<String, Object>>)selectList("sample.selectFileList", map);
	}
	
	public List<Map<String, Object>> selectBoardNextAndPrev(Map<String, Object> map) {
	    return (List<Map<String, Object>>)selectList("sample.selectBoardNextAndPrev", map);
	}
	
	public List<Map<String, Object>> selectBoardSearchNextAndPrev(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectList("sample.selectBoardSearchNextAndPrev", map);
	}

	public void deleteFileList(Map<String, Object> map) {
	    update("sample.deleteFileList", map);
	}
	 
	public void updateFile(Map<String, Object> map) {
	    update("sample.updateFile", map);
	}

	public Map<String, Object> totalCount(Map<String, Object> map) { 
		return (Map<String, Object>) selectOne("sample.totalCount");
	}
	
	public Map<String, Object> searchCount(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("sample.searchCount", map);
	}
}