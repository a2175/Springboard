package first.login.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import first.common.vo.CommonVO;

public class UserVO extends CommonVO {
	
	@Pattern(regexp="^[a-zA-Z0-9]{4,10}$")
    private String id;
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_+|<>?:{}])(?=\\S+$).{8,16}$")
    private String password;
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_+|<>?:{}])(?=\\S+$).{8,16}$")
    private String re_password;
    @Pattern(regexp="^[a-zA-Z0-9가-힣]{4,12}$")
	private String nickname;
	@Email
    private String email;
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRe_password() {
		return re_password;
	}
	
	public void setRe_password(String re_password) {
		this.re_password = re_password;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
