package first.comment.service;

import java.util.List;

import first.comment.vo.CommentVO;

public interface CommentService {

	List<CommentVO> selectCommentList(int board_idx);

	void insertComment(CommentVO vo);

	void deleteComment(int idx);

	CommentVO checkThumbsup(CommentVO vo);

	void insertThumbsup(CommentVO vo);
	
	CommentVO checkThumbsdown(CommentVO vo);

	void insertThumbsdown(CommentVO vo);
	
}
