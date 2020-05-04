package first.common.dao;
 
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import first.board.vo.FileVO;
import first.common.mapper.CommonMapper;

@Repository
public class CommonDAO {
	
	private CommonMapper commonMapper;
	
    @Autowired
    public CommonDAO(CommonMapper commonMapper) {
    	this.commonMapper = commonMapper;
    }
	
	public FileVO selectFileInfo(Map<String, Object> map) {
	    return commonMapper.selectFileInfo(map);
	}

}