package mju.sw.micro.global.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mju.sw.micro.domain.user.domain.User;

public record MicroUserDetails(User user) implements UserDetails {

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getUserRoles()
			.stream()
			.map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name()))
			.collect(Collectors.toList());
	}

	//user field 추가 가능
	public String getEmail() {
		return user.getEmail();
	}

	public String getMajor() {
		return user.getMajor();
	}

	public String getNickName() {
		return user.getNickName();
	}

	public String getInterest() {
		return user.getInterest();
	}

	public String getIntroduction() {
		return user.getIntroduction();
	}

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
}
