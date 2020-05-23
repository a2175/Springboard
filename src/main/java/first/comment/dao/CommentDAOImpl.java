package first.comment.dao;
 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import first.comment.mapper.CommentMapper;
import first.comment.vo.CommentVO;

@Repository
public class CommentDAOImpl implements CommentDAO {
 	
	private CommentMapper commentMapper;
	
    @Autowired
    public CommentDAOImpl(CommentMapper commentMapper) {
    	this.commentMapper = commentMapper;
    }
	
    @Override
	public List<CommentVO> selectCommentList(Map<String, Object> map) {
		return commentMapper.selectCommentList(map);
	}

    @Override
	public void insertComment(Map<String, Object> map) {
		commentMapper.insertComment(map);
	}

    @Override
	public void deleteComment(Map<String, Object> map) {
		commentMapper.deleteComment(map);
	}
	
    @Override
	public CommentVO selectThumbsUpByIdAndIdx(Map<String, Object> map) {
		return commentMapper.selectThumbsUpByIdAndIdx(map);
	}
	
    @Override
	public void insertThumbsup(Map<String, Object> map) {
		commentMapper.insertThumbsup(map);
	}
	
    @Override
	public CommentVO selectThumbsDownByIdAndIdx(Map<String, Object> map) {
		return commentMapper.selectThumbsDownByIdAndIdx(map);
	}
	
    @Override
	public void insertThumbsdown(Map<String, Object> map) {
		commentMapper.insertThumbsdown(map);
	}
	
}