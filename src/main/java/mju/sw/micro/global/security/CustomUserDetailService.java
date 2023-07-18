package mju.sw.micro.global.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mju.sw.micro.domain.user.dao.UserRepository;
import mju.sw.micro.domain.user.domain.User;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("Invalid authentication!"));
		return new CustomUserDetails(user);
	}
}
