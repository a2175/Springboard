package first.board.dao;

import java.util.List;
import java.util.Map;

import first.board.vo.BoardVO;
import first.board.vo.FileVO;

public interface BoardDAO {
	
	List<BoardVO> selectBoardList(Map<String, Object> map);
	
	List<BoardVO> selectBoardSearchList(Map<String, Object> map);
	
	BoardVO selectBoardDetail(Map<String, Object> map);
	
	BoardVO selectNextBoard(Map<String, Object> map);
	
	BoardVO selectPrevBoard(Map<String, Object> map);
	
	BoardVO selectNextSearchBoard(Map<String, Object> map);
	
	BoardVO selectPrevSearchBoard(Map<String, Object> map);
	
	int selectBoardTotalCount();
	
	int selectBoardSearchTotalCount(Map<String, Object> map);
	
	void insertBoard(Map<String, Object> map);
	
	void updateBoard(Map<String, Object> map);
	
	void updateHitCnt(Map<String, Object> map);
	
	void deleteBoard(Map<String, Object> map);
	
	List<FileVO> selectFileList(Map<String, Object> map);
	
	FileVO selectFileInfo(Map<String, Object> map);

	void insertFile(Map<String, Object> map);
	
	void updateFile(Map<String, Object> map);
	
	void deleteFileList(Map<String, Object> map);
		
}
