package first.common.service;

import java.util.Map;

import first.board.vo.FileVO;

public interface CommonService {

	FileVO selectFileInfo(Map<String, Object> map);
	
}