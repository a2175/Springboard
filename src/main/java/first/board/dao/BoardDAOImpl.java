package first.board.dao;
 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import first.board.mapper.BoardMapper;
import first.board.vo.BoardVO;
import first.board.vo.FileVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	private BoardMapper boardMapper;
	
    @Autowired
    public BoardDAOImpl(BoardMapper boardMapper) {
    	this.boardMapper = boardMapper;
    }
	
    @Override
	public List<BoardVO> selectBoardList(Map<String, Object> map) {     
	    return boardMapper.selectBoardList(map);
	}
	
    @Override
	public List<BoardVO> selectBoardSearchList(Map<String, Object> map) {      
	    return boardMapper.selectBoardSearchList(map);
	}
	
    @Override
    public BoardVO selectBoardDetail(Map<String, Object> map) {
        return boardMapper.selectBoardDetail(map);
    }
    
    @Override
    public BoardVO selectNextBoard(Map<String, Object> map) {
        return boardMapper.selectNextBoard(map);
    }
    
    @Override
    public BoardVO selectPrevBoard(Map<String, Object> map) {
        return boardMapper.selectPrevBoard(map);
    }
    
    @Override
    public BoardVO selectNextSearchBoard(Map<String, Object> map) {
        return boardMapper.selectNextSearchBoard(map);
    }
    
    @Override
    public BoardVO selectPrevSearchBoard(Map<String, Object> map) {
        return boardMapper.selectPrevSearchBoard(map);
    }
    
    @Override
	public int selectBoardTotalCount() { 
		return boardMapper.selectBoardTotalCount();
	}
	
    @Override
	public int selectBoardSearchTotalCount(Map<String, Object> map) {
		return boardMapper.selectBoardSearchTotalCount(map);
	}
	
    @Override
    public void insertBoard(Map<String, Object> map) {
    	boardMapper.insertBoard(map);
    }
    
    @Override
	public void updateBoard(Map<String, Object> map) {
		boardMapper.updateBoard(map);
	}
	
    @Override
    public void updateHitCnt(Map<String, Object> map) {
    	boardMapper.updateHitCnt(map);
    }
    
    @Override
	public void deleteBoard(Map<String, Object> map) {
		boardMapper.deleteBoard(map);	
	}
    
    @Override
	public List<FileVO> selectFileList(Map<String, Object> map) {
	    return boardMapper.selectFileList(map);
	}
	
    @Override
    public FileVO selectFileInfo(Map<String, Object> map) {
		return boardMapper.selectFileInfo(map);
	}
    
    @Override
    public void insertFile(Map<String, Object> map) {
    	boardMapper.insertFile(map);
    }
    
    @Override
	public void updateFile(Map<String, Object> map) {
		boardMapper.updateFile(map);
	}
		
    @Override
	public void deleteFileList(Map<String, Object> map) {
		boardMapper.deleteFileList(map);;
	}
    
}