package first.common.dao;
 
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import first.board.vo.FileVO;
import first.common.mapper.CommonMapper;

@Repository
public class CommonDAOImpl implements CommonDAO {
	
	private CommonMapper commonMapper;
	
    @Autowired
    public CommonDAOImpl(CommonMapper commonMapper) {
    	this.commonMapper = commonMapper;
    }
    
    @Override
	public FileVO selectFileInfo(Map<String, Object> map) {
	    return commonMapper.selectFileInfo(map);
	}

}