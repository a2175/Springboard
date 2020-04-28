package first.common.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonVO {
	private int idx;
	private String crea_id;
	private String del_gb;
	private String crea_dtm;
	
	public int getIdx() {
		return idx;
	}
	
	public void setIdx(int idx) {
		this.idx = idx;
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
	
	public void setCrea_dtm(Timestamp crea_dtm) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		this.crea_dtm = simpleDateFormat.format(new Date(crea_dtm.getTime()));
	}
}

