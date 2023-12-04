package org.springframework.samples.maderas.auth;



import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.maderas.auth.payload.request.SignupRequest;
import org.springframework.samples.maderas.user.Authorities;
import org.springframework.samples.maderas.user.AuthoritiesService;
import org.springframework.samples.maderas.user.User;
import org.springframework.samples.maderas.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private final PasswordEncoder encoder;
	private final AuthoritiesService authoritiesService;
	private final UserService userService;


	@Autowired
	public AuthService(PasswordEncoder encoder, AuthoritiesService authoritiesService, UserService userService) {
		this.encoder = encoder;
		this.authoritiesService = authoritiesService;
		this.userService = userService;
	}

	@Transactional
	public void createUser(@Valid SignupRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		String strRoles = request.getAuthority();
		Authorities role;
		if(strRoles == "gestoradministrativo"){
			role = authoritiesService.findByAuthority("GESTORADMINISTRATIVO");
			user.setAuthority(role);
			userService.saveUser(user);
		}else{
			role = authoritiesService.findByAuthority("USER");
			user.setAuthority(role);
			userService.saveUser(user);
		}
	}

}
