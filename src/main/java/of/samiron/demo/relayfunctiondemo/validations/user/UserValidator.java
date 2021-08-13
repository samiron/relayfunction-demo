package of.samiron.demo.relayfunctiondemo.validations.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import of.samiron.demo.relayfunctiondemo.function.RelayTwo;
import of.samiron.demo.relayfunctiondemo.model.User;
import of.samiron.demo.relayfunctiondemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor()
@NoArgsConstructor
@Setter(onMethod = @__(@Autowired))
public class UserValidator {

	private UserRepository userRepository;

	public void validateCreateUser(User user) {
		nameIsNotEmpty()
				.next(emailAddressIsValid())
				.next(emailAddressMustBeUnique())
				.next(nameMustHaveAlphaNums())
				.next(activationDateMustExist())
				.next(expirationDateShouldNotExist())
		.apply(user);
	}

	public void validateUpdateUser(User newUser, User oldUser) {
		emailCannotBeChanged()
				.next(nameIsNotEmpty())
				.next(activationDateCannotBeChanged())
				.next(activationDateMustExist())
				.next(expirationDateCannotBeBeforeActivationDate())
		.apply(newUser, oldUser);
	}

	IndividualUserRule emailAddressMustBeUnique() {
		return (user -> {
			String email = user.getEmail();
			boolean exists = userRepository.countByEmail(email) != 0;
			if(exists) {
				throw new UserValidationException("Email address already exists");
			}
		});
	}

	RelayTwo<User, User> expirationDateCannotBeBeforeActivationDate() {
		return (newUser, oldUser) -> {
			if(newUser.getExpirationDate() != null && newUser.getExpirationDate().isBefore(oldUser.getActivationDate())) {
				throw new UserValidationException("Expiration date can not be before activation date");
			}
		};
	}

	RelayTwo<User, User> activationDateCannotBeChanged() {
		return (newUser, oldUser) -> {
			if(!newUser.getActivationDate().equals(oldUser.getActivationDate())) {
				throw new UserValidationException("Activation date must not be changed");
			}
		};
	}

	RelayTwo<User, User> emailCannotBeChanged() {
		return (newUser, oldUser) -> {
			if(!newUser.getEmail().equals(oldUser.getEmail())) {
				throw new UserValidationException("Email address can not be changed");
			}
		};
	}


	IndividualUserRule emailAddressIsValid() {
		return (user) -> {
			if(user.getEmail() == null || !user.getEmail().matches("^(.+)@(.+)$")) {
				throw new UserValidationException("Invalid email address");
			}
		};
	}

	IndividualUserRule nameIsNotEmpty() {
		return (user) -> {
			if(user.getFullName() == null || user.getFullName().isBlank()) {
				throw new UserValidationException("User name must exists");
			}
		};
	}

	IndividualUserRule nameMustHaveAlphaNums() {
		return (user) -> {
			if(!user.getFullName().matches("^[a-zA-Z0-9]+\\s[a-zA-Z0-9]+$")) {
				throw new UserValidationException("Name should have alphanum characters separated by one space");
			}
		};
	}

	IndividualUserRule activationDateMustExist() {
		return (user) -> {
			if(user.getActivationDate() == null || user.getActivationDate().isBefore(LocalDate.now())) {
				throw new UserValidationException("Activation date must be today or in future");
			}
		};
	}

	IndividualUserRule expirationDateShouldNotExist() {
		return (user) -> {
			if(user.getExpirationDate() != null) {
				throw new UserValidationException("Should not have any expiration date when creating");
			}
		};
	}


}
