package first.sample.dao;
 
import java.util.List;
import java.util.Map;
 
import org.springframework.stereotype.Repository;
 
import first.common.dao.AbstractDAO;
 
@Repository("sampleDAO")
public class SampleDAO extends AbstractDAO{
 
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception{
	    return (List<Map<String, Object>>)selectPagingList("sample.selectBoardList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardSearchList(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>)selectPagingList("sample.selectBoardSearchList", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardEGList(Map<String, Object> map) throws Exception{
	    return (Map<String, Object>)selectEGPagingList("sample.selectBoardList", map);
	}
	
    public void insertBoard(Map<String, Object> map) throws Exception{
        insert("sample.insertBoard", map);
    }
    
    public void insertFile(Map<String, Object> map) throws Exception{
        insert("sample.insertFile", map);
    }

    public void updateHitCnt(Map<String, Object> map) throws Exception{
        update("sample.updateHitCnt", map);
    }
     
    @SuppressWarnings("unchecked")
    public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception{
        return (Map<String, Object>) selectOne("sample.selectBoardDetail", map);
    }

	public void updateBoard(Map<String, Object> map) throws Exception {
		update("sample.updateBoard", map);
	}

	public void deleteBoard(Map<String, Object> map) throws Exception {
		update("sample.deleteBoard", map);		
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectFileList(Map<String, Object> map) throws Exception{
	    return (List<Map<String, Object>>)selectList("sample.selectFileList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardNextAndPrev(Map<String, Object> map) throws Exception{
	    return (List<Map<String, Object>>)selectList("sample.selectBoardNextAndPrev", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardSearchNextAndPrev(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>)selectList("sample.selectBoardSearchNextAndPrev", map);
	}

	public void deleteFileList(Map<String, Object> map) throws Exception{
	    update("sample.deleteFileList", map);
	}
	 
	public void updateFile(Map<String, Object> map) throws Exception{
	    update("sample.updateFile", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> totalCount(Map<String, Object> map) throws Exception{ 
		return (Map<String, Object>) selectOne("sample.totalCount");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchCount(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectOne("sample.searchCount", map);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectCommentList(Map<String, Object> map) throws Exception {
		return (List<Map<String, Object>>)selectList("sample.selectCommentList", map);
	}

	public void insertComment(Map<String, Object> map) {
		insert("sample.insertComment", map);
	}

	public void deleteComment(Map<String, Object> map) {
		update("sample.deleteComment", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkThumbsup(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("sample.checkThumbsup", map);
	}
	
	public void insertThumbsup(Map<String, Object> map) {
		insert("sample.insertThumbsup", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> checkThumbsdown(Map<String, Object> map) {
		return (Map<String, Object>) selectOne("sample.checkThumbsdown", map);
	}
	
	public void insertThumbsdown(Map<String, Object> map) {
		insert("sample.insertThumbsdown", map);
	}
}