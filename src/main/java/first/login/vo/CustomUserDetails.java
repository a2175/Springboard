package first.login.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {
    
    private String USERNAME;
    private String PASSWORD;
    private List<String> AUTHORITY;
    private boolean ENABLED;
    private String NAME;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        for(int i=0; i<AUTHORITY.size(); i++)
        	auth.add(new SimpleGrantedAuthority(AUTHORITY.get(i)));
        return auth;
    }
 
    @Override
    public String getPassword() {
        return PASSWORD;
    }
 
    @Override
    public String getUsername() {
        return USERNAME;
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
        return ENABLED;
    }
    
    public String getName() {
        return NAME;
    }
 
    public void setName(String name) {
        this.NAME = name;
    }
    
    public List<String> getAuthority() {
        return AUTHORITY;
    }
 
    public void setAuthority(List<String> Authority) {
        this.AUTHORITY = Authority;
    }
 
}
