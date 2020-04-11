package first.comment.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentVO {
	private int idx;
	private int board_idx;
	private String contents;
	private String crea_id;
	private String nickname;
	private String del_gb;
	private int thumbsup_cnt;
	private int thumbsdown_cnt;
	private String crea_dtm;
	
	public int getIdx() {
		return idx;
	}
	
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
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
	
	public String getCrea_id() {
		return crea_id;
	}
	
	public void setCrea_id(String crea_id) {
		this.crea_id = crea_id;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getDel_gb() {
		return del_gb;
	}
	
	public void setDel_gb(String del_gb) {
		this.del_gb = del_gb;
	}
	
	public String getCrea_dtm() {
		return crea_dtm;
	}
	
	public void setCrea_dtm(Timestamp crea_dtm) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		this.crea_dtm = simpleDateFormat.format(new Date(crea_dtm.getTime()));
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

