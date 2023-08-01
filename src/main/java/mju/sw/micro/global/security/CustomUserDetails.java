package mju.sw.micro.global.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mju.sw.micro.domain.user.domain.User;

public record CustomUserDetails(User user) implements UserDetails, MicroUserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getUserRoles()
			.stream()
			.map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()))
			.toList();
	}

	//user field 추가 가능
	@Override
	public String getEmail() {
		return user.getEmail();
	}

	@Override
	public String getMajor() {
		return user.getMajor();
	}

	@Override
	public String getNickName() {
		return user.getNickName();
	}

	@Override
	public String getInterest() {
		return user.getInterest();
	}

	@Override
	public String getIntroduction() {
		return user.getIntroduction();
	}

	@Override
	public String getPhoneNumber() {
		return user.getPhoneNumber();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getName();
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
		return true;
	}

	public Long getUserId() {return user.getId();}

}
