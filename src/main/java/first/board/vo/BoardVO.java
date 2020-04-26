package first.board.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardVO {
	private int idx;
	private int parent_idx;
	private String title;
	private String contents;
	private int hit_cnt;
	private String del_gb;
	private String crea_id;
	private String crea_dtm;
	private String nickname;
	private int comment_cnt;
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	public int getParent_idx() {
		return parent_idx;
	}
	
	public void setParent_idx(int parent_idx) {
		this.parent_idx = parent_idx;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public int getHit_cnt() {
		return hit_cnt;
	}
	
	public void setHit_cnt(int hit_cnt) {
		this.hit_cnt = hit_cnt;
	}
	
	public String getDel_gb() {
		return del_gb;
	}
	
	public void setDel_gb(String del_gb) {
		this.del_gb = del_gb;
	}

	public String getCrea_id() {
		return crea_id;
	}
	
	public void setCrea_id(String crea_id) {
		this.crea_id = crea_id;
	}
	
	public String getCrea_dtm() {
		return crea_dtm;
	}
	
	public void setCrea_dtm(Timestamp crea_dtm) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		this.crea_dtm = simpleDateFormat.format(new Date(crea_dtm.getTime()));
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public int getComment_cnt() {
		return comment_cnt;
	}

	public void setComment_cnt(int comment_cnt) {
		this.comment_cnt = comment_cnt;
	}
}
