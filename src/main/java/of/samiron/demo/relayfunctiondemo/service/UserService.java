package of.samiron.demo.relayfunctiondemo.service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import of.samiron.demo.relayfunctiondemo.model.User;
import of.samiron.demo.relayfunctiondemo.repository.UserRepository;
import of.samiron.demo.relayfunctiondemo.validations.user.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor()
@Setter(onMethod = @__(@Autowired))
public class UserService {

	private final UserRepository userRepository;

	private final UserValidator userValidator;

	public User createUser(User user) {

		userValidator.validateCreateUser(user);

		return userRepository.save(user);
	}

	public User updateUser(User user) {
		Integer id = user.getId();
		Optional<User> oldUser = userRepository.findById(id);
		if(oldUser.isEmpty()) {
			throw new EntityNotFoundException("User not found");
		}

		userValidator.validateUpdateUser(user, oldUser.get());

		return userRepository.save(user);
	}
}
