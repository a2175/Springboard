package first.board.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BoardService {

	Map<String,Object> selectBoardList(Map<String, Object> map);

	Map<String,Object> selectBoardSearchList(Map<String, Object> map);
	
	Map<String, Object> selectBoardEGList(Map<String, Object> map);
	
	void insertBoard(Map<String, Object> map, HttpServletRequest request);

	Map<String, Object> selectBoardDetail(Map<String, Object> map);

	void updateBoard(Map<String, Object> map, HttpServletRequest request);

	void deleteBoard(Map<String, Object> map);
	
	void updateHitCnt(Map<String, Object> map);
	
	void downloadFile(Map<String, Object> map, HttpServletResponse response);
}
