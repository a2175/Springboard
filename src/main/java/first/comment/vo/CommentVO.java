package first.comment.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import first.common.vo.CommonVO;

public class CommentVO extends CommonVO {
	private int board_idx;
	@NotEmpty
	@Size(max = 255)
	private String contents;
	
	private String nickname;
	private int thumbsup_cnt;
	private int thumbsdown_cnt;
	
	public int getBoard_idx() {
		return board_idx;
	}
	
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public int getThumbsup_cnt() {
		return thumbsup_cnt;
	}

	public void setThumbsup_cnt(int thumbsup_cnt) {
		this.thumbsup_cnt = thumbsup_cnt;
	}

	public int getThumbsdown_cnt() {
		return thumbsdown_cnt;
	}

	public void setThumbsdown_cnt(int thumbsdown_cnt) {
		this.thumbsdown_cnt = thumbsdown_cnt;
	}
}

