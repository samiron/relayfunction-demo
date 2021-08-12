package of.samiron.demo.relayfunctiondemo.validations.user;

import java.time.LocalDate;

public class UserValidator {

	public static CreateUserRule VALIDATE_CREATE_USER =
			nameMustExist()
					.next(emailAddressIsValid())
					.next(nameMustHaveAlphaNums())
					.next(activationDateMustExist())
					.next(expirationDateShouldNotExist());

	public static UpdateUserRule VALIDATE_UPDATE_USER =
			emailCannotBeChanged()
					.next(activationDateCannotBeChanged())
					.next(expirationDateCannotBeBeforeActivationDate())
					.next(nameMustExist());

	private static UpdateUserRule expirationDateCannotBeBeforeActivationDate() {
		return (newUser, oldUser) -> {
			if(newUser.getExpirationDate() != null && newUser.getExpirationDate().isBefore(oldUser.getActivationDate())) {
				throw new UserValidationException("Expiration date can not be before activation date");
			}
		};
	}

	private static UpdateUserRule activationDateCannotBeChanged() {
		return (newUser, oldUser) -> {
			if(!newUser.getActivationDate().equals(oldUser.getActivationDate())) {
				throw new UserValidationException("Activation date must not be changed");
			}
		};
	}

	private static UpdateUserRule emailCannotBeChanged() {
		return (newUser, oldUser) -> {
			if(!newUser.getEmail().equals(oldUser.getEmail())) {
				throw new UserValidationException("Email address can not be changed");
			}
		};
	}


	static CreateUserRule emailAddressIsValid() {
		return (user) -> {
			if(user.getEmail() == null || !user.getEmail().matches("^(.+)@(.+)$")) {
				throw new UserValidationException("Invalid email address");
			}
		};
	}

	static CreateUserRule nameMustExist() {
		return (user) -> {
			if(user.getFullName() == null || user.getFullName().isBlank()) {
				throw new UserValidationException("User name must exists");
			}
		};
	}

	static CreateUserRule nameMustHaveAlphaNums() {
		return (user) -> {
			if(!user.getFullName().matches("^[a-zA-Z0-9]+\\s[a-zA-Z0-9]+$")) {
				throw new UserValidationException("Name should have alphanum characters separated by one space");
			}
		};
	}

	static CreateUserRule activationDateMustExist() {
		return (user) -> {
			if(user.getActivationDate() == null || user.getActivationDate().isBefore(LocalDate.now())) {
				throw new UserValidationException("Activation date must be today or in future");
			}
		};
	}

	static CreateUserRule expirationDateShouldNotExist() {
		return (user) -> {
			if(user.getExpirationDate() != null) {
				throw new UserValidationException("Should not have any expiration date when creating");
			}
		};
	}


}
