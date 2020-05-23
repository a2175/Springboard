package first.comment.service;
 
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import first.comment.dao.CommentDAO;
import first.comment.vo.CommentVO;
 
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
@Service
public class CommentServiceImpl implements CommentService{
    Logger log = Logger.getLogger(this.getClass());

    private CommentDAO commentDAO;
    
    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO) {
    	this.commentDAO = commentDAO;
    }
    
    @Override
	public List<CommentVO> selectCommentList(Map<String, Object> map) {
		return commentDAO.selectCommentList(map);
	}

    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public void insertComment(Map<String, Object> map) {
		commentDAO.insertComment(map);
	}

    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public void deleteComment(Map<String, Object> map) {
		commentDAO.deleteComment(map);
	}

    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public int insertThumbsup(Map<String, Object> map) {
        CommentVO commntVO = commentDAO.selectThumbsUpByIdAndIdx(map);
        
        if(commntVO == null) {
        	commentDAO.insertThumbsup(map);
        	return 1;
        }
        else {
        	return 0;
        }
	}
	
    @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	@Override
	public int insertThumbsdown(Map<String, Object> map) {
        CommentVO commntVO = commentDAO.selectThumbsDownByIdAndIdx(map);
        
        if(commntVO == null) {
        	commentDAO.insertThumbsdown(map);
        	return 1;
        }
        else {
        	return 0;
        }
	}
}