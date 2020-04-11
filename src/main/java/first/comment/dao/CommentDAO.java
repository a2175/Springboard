package first.comment.dao;
 
import java.util.List;
 
import org.springframework.stereotype.Repository;

import first.comment.vo.CommentVO;
import first.common.dao.AbstractDAO;

@SuppressWarnings("unchecked")
@Repository
public class CommentDAO extends AbstractDAO{
 	
	public List<CommentVO> selectCommentList(int board_idx) {
		return (List<CommentVO>) selectList("comment.selectCommentList", board_idx);
	}

	public void insertComment(CommentVO vo) {
		insert("comment.insertComment", vo);
	}

	public void deleteComment(int idx) {
		update("comment.deleteComment", idx);
	}
	
	public CommentVO checkThumbsup(CommentVO vo) {
		return (CommentVO) selectOne("comment.checkThumbsup", vo);
	}
	
	public void insertThumbsup(CommentVO vo) {
		insert("comment.insertThumbsup", vo);
	}
	
	public CommentVO checkThumbsdown(CommentVO vo) {
		return (CommentVO) selectOne("comment.checkThumbsdown", vo);
	}
	
	public void insertThumbsdown(CommentVO vo) {
		insert("comment.insertThumbsdown", vo);
	}
	
}