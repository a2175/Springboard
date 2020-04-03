package first.board.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface BoardService {

	List<Map<String, Object>> selectBoardList(Map<String, Object> map);

	List<Map<String, Object>> selectBoardSearchList(Map<String, Object> map);
	
	Map<String, Object> selectBoardEGList(Map<String, Object> map);
	
	void insertBoard(Map<String, Object> map, HttpServletRequest request);

	Map<String, Object> selectBoardDetail(Map<String, Object> map);

	void updateBoard(Map<String, Object> map, HttpServletRequest request);

	void deleteBoard(Map<String, Object> map);

	Map<String, Object> totalCount(Map<String, Object> map);

	Map<String, Object> searchCount(Map<String, Object> map);
	
}
