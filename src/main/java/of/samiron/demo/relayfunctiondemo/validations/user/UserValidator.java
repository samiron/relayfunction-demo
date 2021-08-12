package of.samiron.demo.relayfunctiondemo.validations.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import of.samiron.demo.relayfunctiondemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class UserValidator {


	private UserRepository userRepository;


	public IndividualUserRule VALIDATE_CREATE_USER =
			nameMustExist()
					.next(emailAddressIsValid())
					.next(nameMustHaveAlphaNums())
					.next(activationDateMustExist())
					.next(expirationDateShouldNotExist());

	public ContextualUserRule VALIDATE_UPDATE_USER =
			emailCannotBeChanged()
					.next(activationDateCannotBeChanged())
					.next(expirationDateCannotBeBeforeActivationDate())
					.next(nameMustExist());

	ContextualUserRule expirationDateCannotBeBeforeActivationDate() {
		return (newUser, oldUser) -> {
			if(newUser.getExpirationDate() != null && newUser.getExpirationDate().isBefore(oldUser.getActivationDate())) {
				throw new UserValidationException("Expiration date can not be before activation date");
			}
		};
	}

	ContextualUserRule activationDateCannotBeChanged() {
		return (newUser, oldUser) -> {
			if(!newUser.getActivationDate().equals(oldUser.getActivationDate())) {
				throw new UserValidationException("Activation date must not be changed");
			}
		};
	}

	ContextualUserRule emailCannotBeChanged() {
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

	IndividualUserRule nameMustExist() {
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
