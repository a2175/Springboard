package first.comment.service;
 
import java.util.List;
import java.util.Map;
 
import javax.annotation.Resource;
 
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import first.comment.dao.CommentDAO;
 
@Service("commentService")
public class CommentServiceImpl implements CommentService{
    Logger log = Logger.getLogger(this.getClass());
        
    @Resource(name="commentDAO")
    private CommentDAO commentDAO;
     
	@Override
	public List<Map<String, Object>> selectCommentList(Map<String, Object> map) throws Exception {
		return commentDAO.selectCommentList(map);
	}

	@Override
	public void insertComment(Map<String, Object> map) throws Exception {
		commentDAO.insertComment(map);
	}

	@Override
	public void deleteComment(Map<String, Object> map) throws Exception {
		commentDAO.deleteComment(map);
	}

	@Override
	public Map<String, Object> checkThumbsup(Map<String, Object> map) throws Exception {
		return commentDAO.checkThumbsup(map);
	}

	@Override
	public void insertThumbsup(Map<String, Object> map) throws Exception {
		commentDAO.insertThumbsup(map);	
	}
	
	@Override
	public Map<String, Object> checkThumbsdown(Map<String, Object> map) throws Exception {
		return commentDAO.checkThumbsdown(map);
	}

	@Override
	public void insertThumbsdown(Map<String, Object> map) throws Exception {
		commentDAO.insertThumbsdown(map);	
	}
}