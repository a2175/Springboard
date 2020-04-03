package first.common.dao;
 
import java.util.Map;

import org.springframework.stereotype.Repository;

@SuppressWarnings("unchecked")
@Repository
public class CommonDAO extends AbstractDAO{
	
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception{
	    return (Map<String, Object>)selectOne("common.selectFileInfo", map);
	}

}