package of.samiron.demo.relayfunctiondemo.service;

import lombok.AllArgsConstructor;
import of.samiron.demo.relayfunctiondemo.dto.UserDTO;
import of.samiron.demo.relayfunctiondemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

	private final UserRepository userRepository;

	public UserDTO createUser(UserDTO userDTO) {

		return null;
	}
}
