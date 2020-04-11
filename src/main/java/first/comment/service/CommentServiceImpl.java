package first.comment.service;
 
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import first.comment.dao.CommentDAO;
import first.comment.vo.CommentVO;
 
@Service
public class CommentServiceImpl implements CommentService{
    Logger log = Logger.getLogger(this.getClass());

    private CommentDAO commentDAO;
    
    @Autowired
    public CommentServiceImpl(CommentDAO commentDAO) {
    	this.commentDAO = commentDAO;
    }
    
    @Override
	public List<CommentVO> selectCommentList(int board_idx) {
		return commentDAO.selectCommentList(board_idx);
	}

	@Override
	public void insertComment(CommentVO vo) {
		commentDAO.insertComment(vo);
	}

	@Override
	public void deleteComment(int idx) {
		commentDAO.deleteComment(idx);
	}

	@Override
	public CommentVO checkThumbsup(CommentVO vo) {
		return commentDAO.checkThumbsup(vo);
	}

	@Override
	public void insertThumbsup(CommentVO vo) {
		commentDAO.insertThumbsup(vo);	
	}
	
	@Override
	public CommentVO checkThumbsdown(CommentVO vo) {
		return commentDAO.checkThumbsdown(vo);
	}

	@Override
	public void insertThumbsdown(CommentVO vo) {
		commentDAO.insertThumbsdown(vo);	
	}
}