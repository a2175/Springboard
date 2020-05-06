package first.board.vo;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import first.common.vo.CommonVO;

public class BoardVO extends CommonVO {
	
	private int parent_idx;
	@NotEmpty
	@Size(max = 100)
	private String title;
	@NotEmpty
	@Size(max = 255)
	private String contents;
	private int hit_cnt;

	private String nickname;
	private int comment_cnt;
	private List<FileVO> files;
	
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

	public List<FileVO> getFiles() {
		return files;
	}

	public void setFiles(List<FileVO> files) {
		this.files = files;
	}
}
