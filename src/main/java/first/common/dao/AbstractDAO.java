package first.common.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


public class AbstractDAO {
    protected Log log = LogFactory.getLog(AbstractDAO.class);
     
    @Autowired
    private SqlSessionTemplate sqlSession;
     
    protected void printQueryId(String queryId) {
        if(log.isDebugEnabled()){
            log.debug("\t QueryId  \t:  " + queryId);
        }
    }
     
    public Object insert(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.insert(queryId, params);
    }
     
    public Object update(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.update(queryId, params);
    }
     
    public Object delete(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.delete(queryId, params);
    }
     
    public Object selectOne(String queryId){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId);
    }
     
    public Object selectOne(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId, params);
    }
     
    public Object selectList(String queryId){
        printQueryId(queryId);
        return sqlSession.selectList(queryId);
    }
     
    public Object selectList(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectList(queryId,params);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" }) //전자정부
    public Map selectEGPagingList(String queryId, Object params){ 
        printQueryId(queryId);
         
        Map<String,Object> map = (Map<String,Object>)params;
        PaginationInfo paginationInfo = null;
         
        if(map.containsKey("currentPageNo") == false || StringUtils.isEmpty(map.get("currentPageNo")) == true)
            map.put("currentPageNo","1");
         
        paginationInfo = new PaginationInfo();
        paginationInfo.setCurrentPageNo(Integer.parseInt(map.get("currentPageNo").toString()));
        if(map.containsKey("pageRow") == false || StringUtils.isEmpty(map.get("pageRow")) == true){
            paginationInfo.setRecordCountPerPage(15);
        }
        else{
            paginationInfo.setRecordCountPerPage(Integer.parseInt(map.get("pageRow").toString()));
        }
        paginationInfo.setPageSize(10);
        
        int start = paginationInfo.getFirstRecordIndex();
        int end = paginationInfo.getRecordCountPerPage();
        map.put("start",start);
        map.put("end",end);
         
        params = map;
         
        Map<String,Object> returnMap = new HashMap<String,Object>();
        List<Map<String,Object>> list = sqlSession.selectList(queryId, params);
        int totalCount = sqlSession.selectOne("board.totalCount");
         
        if(list.size() == 0){
            map = new HashMap<String,Object>();
            map.put("TOTAL_COUNT", 0); 
            list.add(map);
             
            if(paginationInfo != null){
                paginationInfo.setTotalRecordCount(0);
                returnMap.put("paginationInfo", paginationInfo);
            }
        }
        else{
            if(paginationInfo != null){
                paginationInfo.setTotalRecordCount(totalCount);
                returnMap.put("paginationInfo", paginationInfo);
            }
        }
        returnMap.put("list", list);
        return returnMap;
    }
    
    @SuppressWarnings("unchecked") //json
    public Object selectPagingList(String queryId, Object params){
        printQueryId(queryId);
        Map<String,Object> map = (Map<String,Object>)params;
        
        String strPageIndex = (String)map.get("pageIdx"); 
        String strPageRow = (String)map.get("pageRow");

        int nPageIndex = 0;
        int nPageRow = 15;
         
        if(StringUtils.isEmpty(strPageIndex) == false){
            nPageIndex = Integer.parseInt(strPageIndex)-1;
        }
        if(StringUtils.isEmpty(strPageRow) == false){
            nPageRow = Integer.parseInt(strPageRow);
        }
        map.put("start", (nPageIndex * nPageRow));
        map.put("end", nPageRow);
         
        return sqlSession.selectList(queryId, map);
    }
}