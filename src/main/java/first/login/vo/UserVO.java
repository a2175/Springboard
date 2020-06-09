package first.login.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class UserVO implements UserDetails {
    
	@Pattern(regexp="^[a-zA-Z0-9]{4,10}$")
    private String id;
    @Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_+|<>?:{}])(?=\\S+$).{8,16}$")
    private String password;
    private String re_password;
    @Pattern(regexp="^[a-zA-Z0-9가-힣]{4,12}$")
	private String nickname;
	@Email
    private String email;
	private String crea_dtm;
	
	private List<String> authotitys;
    private boolean enabled;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        for(String authotity : authotitys) {
        	auth.add(new SimpleGrantedAuthority(authotity));
        }
        return auth;
    }
 
    @Override
    public String getPassword() {
        return this.password;
    }
	@Override
    public String getUsername() {
        return this.id;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	public String getCrea_dtm() {
		return crea_dtm;
	}
	
	public void setCrea_dtm(Timestamp crea_dtm) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		this.crea_dtm = simpleDateFormat.format(new Date(crea_dtm.getTime()));
	}
    
    public List<String> getAuthotitys() {
		return authotitys;
	}

	public void setAuthotitys(List<String> authotitys) {
		this.authotitys = authotitys;
	}
	
}