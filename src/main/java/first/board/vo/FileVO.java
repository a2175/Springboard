package first.board.vo;

public class FileVO {
	private int idx;
	private int board_idx;
	private String original_file_name;
	private String stored_file_name;
	private int file_size;
	private String crea_id;
	private String del_gb;
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
	
	public String getOriginal_file_name() {
		return original_file_name;
	}
	
	public void setOriginal_file_name(String original_file_name) {
		this.original_file_name = original_file_name;
	}
	
	public String getStored_file_name() {
		return stored_file_name;
	}
	
	public void setStored_file_name(String stored_file_name) {
		this.stored_file_name = stored_file_name;
	}
	
	public int getFile_size() {
		return file_size;
	}
	
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	
	public String getCrea_id() {
		return crea_id;
	}
	
	public void setCrea_id(String crea_id) {
		this.crea_id = crea_id;
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
	
	public void setCrea_dtm(String crea_dtm) {
		this.crea_dtm = crea_dtm;
	}
}
