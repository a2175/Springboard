package first.comment.service;
 
import java.util.List;
import java.util.Map;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import first.comment.dao.CommentDAO;
 
@Service
public class CommentServiceImpl implements CommentService{
    Logger log = Logger.getLogger(this.getClass());

    private CommentDAO commentDAO;
    
    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO) {
    	this.commentDAO = commentDAO;
    }
    
	@Override
	public List<Map<String, Object>> selectCommentList(Map<String, Object> map) {
		return commentDAO.selectCommentList(map);
	}

	@Override
	public void insertComment(Map<String, Object> map) {
		commentDAO.insertComment(map);
	}

	@Override
	public void deleteComment(Map<String, Object> map) {
		commentDAO.deleteComment(map);
	}

	@Override
	public Map<String, Object> checkThumbsup(Map<String, Object> map) {
		return commentDAO.checkThumbsup(map);
	}

	@Override
	public void insertThumbsup(Map<String, Object> map) {
		commentDAO.insertThumbsup(map);	
	}
	
	@Override
	public Map<String, Object> checkThumbsdown(Map<String, Object> map) {
		return commentDAO.checkThumbsdown(map);
	}

	@Override
	public void insertThumbsdown(Map<String, Object> map) {
		commentDAO.insertThumbsdown(map);	
	}
}