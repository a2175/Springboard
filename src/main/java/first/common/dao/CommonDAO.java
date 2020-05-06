package first.common.dao;

import java.util.Map;

import first.board.vo.FileVO;

public interface CommonDAO {
	
	FileVO selectFileInfo(Map<String, Object> map);
	
}
