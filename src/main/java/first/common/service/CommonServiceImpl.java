package first.common.service;
 
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import first.board.vo.FileVO;
import first.common.dao.CommonDAO;
 
@Service
public class CommonServiceImpl implements CommonService{
    Logger log = Logger.getLogger(this.getClass());
     
    private CommonDAO commonDAO;
    
    @Autowired
    public CommonServiceImpl(CommonDAO commonDAO) {
    	this.commonDAO = commonDAO;
    }

    @Override
    public FileVO selectFileInfo(Map<String, Object> map) {
        return commonDAO.selectFileInfo(map);
    }

}