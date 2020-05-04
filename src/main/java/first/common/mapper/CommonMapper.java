package first.common.mapper;

import java.util.Map;

import first.board.vo.FileVO;

public interface CommonMapper {
	FileVO selectFileInfo(Map<String, Object> map);
}
