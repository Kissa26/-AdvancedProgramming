package smartCity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import smartCity.model.User;
import smartCity.repository.UserRepository;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	private UserRepository userRepository;
	private PasswordEncoder bcryptEncoder;

	@Autowired
	public void setUserDao(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setBcryptEncoder(PasswordEncoder bcryptEncoder) {
		this.bcryptEncoder = bcryptEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getParola(),
				new ArrayList<>());
	}

	public User save(User user) throws Exception {
		user.setParola(bcryptEncoder.encode(user.getParola()));
		return userRepository.save(new User(user.getUsername(),user.getParola(), user.getEmail()));
	}
}