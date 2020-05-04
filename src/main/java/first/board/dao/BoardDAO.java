package first.board.dao;
 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import first.board.mapper.BoardMapper;
import first.board.vo.BoardVO;
import first.board.vo.FileVO;

@Repository
public class BoardDAO {
	
	private BoardMapper boardMapper;
	
    @Autowired
    public BoardDAO(BoardMapper boardMapper) {
    	this.boardMapper = boardMapper;
    }
	
	public List<BoardVO> selectBoardList(Map<String, Object> map) {     
	    return boardMapper.selectBoardList(map);
	}
	
	public List<BoardVO> selectBoardSearchList(Map<String, Object> map) {      
	    return boardMapper.selectBoardSearchList(map);
	}
	
    public BoardVO selectBoardDetail(Map<String, Object> map) {
        return boardMapper.selectBoardDetail(map);
    }
    
    public BoardVO selectNextBoard(Map<String, Object> map) {
        return boardMapper.selectNextBoard(map);
    }
    
    public BoardVO selectPrevBoard(Map<String, Object> map) {
        return boardMapper.selectPrevBoard(map);
    }
    
    public BoardVO selectNextSearchBoard(Map<String, Object> map) {
        return boardMapper.selectNextSearchBoard(map);
    }
    
    public BoardVO selectPrevSearchBoard(Map<String, Object> map) {
        return boardMapper.selectPrevSearchBoard(map);
    }
    
	public int selectBoardTotalCount() { 
		return boardMapper.selectBoardTotalCount();
	}
	
	public int selectBoardSearchTotalCount(Map<String, Object> map) {
		return boardMapper.selectBoardSearchTotalCount(map);
	}
	
    public void insertBoard(Map<String, Object> map) {
    	boardMapper.insertBoard(map);
    }
    
	public void updateBoard(Map<String, Object> map) {
		boardMapper.updateBoard(map);
	}
	
    public void updateHitCnt(Map<String, Object> map) {
    	boardMapper.updateHitCnt(map);
    }
    
	public void deleteBoard(Map<String, Object> map) {
		boardMapper.deleteBoard(map);	
	}
    
	public List<FileVO> selectFileList(Map<String, Object> map) {
	    return boardMapper.selectFileList(map);
	}
	
    public void insertFile(Map<String, Object> map) {
    	boardMapper.insertFile(map);
    }
    
	public void updateFile(Map<String, Object> map) {
		boardMapper.updateFile(map);
	}
		
	public void deleteFileList(Map<String, Object> map) {
		boardMapper.deleteFileList(map);;
	}

}