package first.board.dao;
 
import java.util.List;
import java.util.Map;
 
import org.springframework.stereotype.Repository;

import first.board.vo.BoardVO;
import first.board.vo.FileVO;
import first.common.dao.AbstractDAO;

@SuppressWarnings("unchecked")
@Repository
public class BoardDAO extends AbstractDAO{

	public List<BoardVO> selectBoardList(Map<String, Object> map) {
	    return (List<BoardVO>)selectPagingList("board.selectBoardList", map);
	}
	
	public List<BoardVO> selectBoardSearchList(Map<String, Object> map) {
		return (List<BoardVO>)selectPagingList("board.selectBoardSearchList", map);
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
     
    public BoardVO selectBoardDetail(Map<String, Object> map) {
        return (BoardVO)selectOne("board.selectBoardDetail", map);
    }

	public void updateBoard(Map<String, Object> map) {
		update("board.updateBoard", map);
	}

	public void deleteBoard(Map<String, Object> map) {
		update("board.deleteBoard", map);		
	}

	public List<FileVO> selectFileList(Map<String, Object> map) {
	    return (List<FileVO>)selectList("board.selectFileList", map);
	}
	
	public List<BoardVO> selectBoardNextAndPrev(Map<String, Object> map) {
	    return (List<BoardVO>)selectList("board.selectBoardNextAndPrev", map);
	}
	
	public List<BoardVO> selectBoardSearchNextAndPrev(Map<String, Object> map) {
		return (List<BoardVO>)selectList("board.selectBoardSearchNextAndPrev", map);
	}

	public void deleteFileList(Map<String, Object> map) {
	    update("board.deleteFileList", map);
	}
	 
	public void updateFile(Map<String, Object> map) {
	    update("board.updateFile", map);
	}

	public int totalCount() { 
		return (int)selectOne("board.totalCount");
	}
	
	public int searchCount(Map<String, Object> map) {
		return (int)selectOne("board.searchCount", map);
	}
}