package first.board.vo;

import first.common.vo.CommonVO;

public class FileVO extends CommonVO {
	private int board_idx;
	private String original_file_name;
	private String stored_file_name;
	private int file_size;
	
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
}
