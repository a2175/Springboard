package first.board.dao;
 
import java.util.List;
import java.util.Map;
 
import org.springframework.stereotype.Repository;
 
import first.common.dao.AbstractDAO;

@SuppressWarnings("unchecked")
@Repository
public class BoardDAO extends AbstractDAO{

	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) {
	    return (List<Map<String, Object>>)selectPagingList("board.selectBoardList", map);
	}
	
	public List<Map<String, Object>> selectBoardSearchList(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectPagingList("board.selectBoardSearchList", map);
	}
	
	public Map<String, Object> selectBoardEGList(Map<String, Object> map) {
	    return (Map<String, Object>)selectEGPagingList("board.selectBoardList", map);
	}
	
    public void insertBoard(Map<String, Object> map) {
        insert("board.insertBoard", map);
    }
    
    public void insertFile(Map<String, Object> map) {
        insert("board.insertFile", map);
    }

    public void updateHitCnt(Map<String, Object> map) {
        update("board.updateHitCnt", map);
    }
     
    public Map<String, Object> selectBoardDetail(Map<String, Object> map) {
        return (Map<String, Object>) selectOne("board.selectBoardDetail", map);
    }

	public void updateBoard(Map<String, Object> map) {
		update("board.updateBoard", map);
	}

	public void deleteBoard(Map<String, Object> map) {
		update("board.deleteBoard", map);		
	}

	public List<Map<String, Object>> selectFileList(Map<String, Object> map) {
	    return (List<Map<String, Object>>)selectList("board.selectFileList", map);
	}
	
	public List<Map<String, Object>> selectBoardNextAndPrev(Map<String, Object> map) {
	    return (List<Map<String, Object>>)selectList("board.selectBoardNextAndPrev", map);
	}
	
	public List<Map<String, Object>> selectBoardSearchNextAndPrev(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectList("board.selectBoardSearchNextAndPrev", map);
	}

	public void deleteFileList(Map<String, Object> map) {
	    update("board.deleteFileList", map);
	}
	 
	public void updateFile(Map<String, Object> map) {
	    update("board.updateFile", map);
	}

	public Map<String, Object> totalCount(Map<String, Object> map) { 
		return (Map<String, Object>) selectOne("board.totalCount");
	}
	
	public Map<String, Object> searchCount(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("board.searchCount", map);
	}
}