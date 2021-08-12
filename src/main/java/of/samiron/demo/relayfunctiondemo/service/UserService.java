package of.samiron.demo.relayfunctiondemo.service;

import lombok.AllArgsConstructor;
import of.samiron.demo.relayfunctiondemo.model.User;
import of.samiron.demo.relayfunctiondemo.repository.UserRepository;
import of.samiron.demo.relayfunctiondemo.validations.user.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

	private final UserRepository userRepository;

	public User createUser(User user) {

		UserValidator
				.VALIDATE_CREATE_USER
				.apply(user);

		return userRepository.save(user);

	}

	public User updateUser(User user) {
		Integer id = user.getId();
		Optional<User> oldUser = userRepository.findById(id);
		if(oldUser.isEmpty()) {
			throw new EntityNotFoundException("User not found");
		}

		UserValidator
				.VALIDATE_UPDATE_USER
				.apply(user, oldUser.get());

		return userRepository.save(user);
	}
}
