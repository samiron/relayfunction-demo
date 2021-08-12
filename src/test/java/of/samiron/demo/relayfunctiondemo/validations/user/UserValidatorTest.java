package of.samiron.demo.relayfunctiondemo.validations.user;

import of.samiron.demo.relayfunctiondemo.model.User;
import of.samiron.demo.relayfunctiondemo.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserValidator userValidator;

	@ParameterizedTest
	@CsvSource({"-", "invalid_email_address"})
	void emailAddressIsValid_fail(String invalidEmail) {
		User user = aValidUser();
		user.setEmail(invalidEmail);
		Throwable exc = assertThrows(
				UserValidationException.class,
				() -> userValidator.emailAddressIsValid().apply(user));
		assertThat(exc.getMessage(), is(equalTo("Invalid email address")));
	}

	@ParameterizedTest
	@CsvSource({"a@b.com", "royan@dwayne.ca"})
	void emailAddressIsValid_pass(String validEmail) {
		User user = aValidUser();
		user.setEmail(validEmail);
		userValidator.emailAddressIsValid().apply(user);
	}

	@ParameterizedTest
	@CsvSource({"'    '"})
	void nameMustExist_fail(String inValidName) {
		User user = aValidUser();
		user.setFullName(inValidName);
		Throwable exc = assertThrows(
				UserValidationException.class,
				() -> userValidator.nameMustExist().apply(user));
		assertThat(exc.getMessage(), is(equalTo("User name must exists")));
	}

	@ParameterizedTest
	@CsvSource({"Doyan", "Royan"})
	void nameMustExist_pass(String validName) {
		User user = aValidUser();
		user.setFullName(validName);
		userValidator.nameMustExist().apply(user);
	}

	@ParameterizedTest
	@CsvSource({"Doyan", "Royan"})
	void nameMustHaveAlphaNums_fail(String inValidName) {
		User user = aValidUser();
		user.setFullName(inValidName);
		Throwable exc = assertThrows(
				UserValidationException.class,
				() -> userValidator.nameMustHaveAlphaNums().apply(user));
		assertThat(exc.getMessage(), is(equalTo("Name should have alphanum characters separated by one space")));
	}

	@ParameterizedTest
	@CsvSource({"Doyan Royan"})
	void nameMustHaveAlphaNums_pass(String validName) {
		User user = aValidUser();
		user.setFullName(validName);
		userValidator.nameMustHaveAlphaNums().apply(user);
	}

	@ParameterizedTest
	@CsvSource({"-1", "-100"})
	void activationDateMustExist_fail(int daysOffset) {
		User user = aValidUser();
		LocalDate invalidActivationDate = LocalDate.now().plusDays(daysOffset);
		user.setActivationDate(invalidActivationDate);

		Throwable th = assertThrows(UserValidationException.class,
				() -> userValidator.activationDateMustExist().apply(user));
		assertThat(th.getMessage(), is(equalTo("Activation date must be today or in future")));
	}

	@ParameterizedTest
	@CsvSource({"0", "100"})
	void activationDateMustExist_pass(int daysOffset) {
		User user = aValidUser();
		LocalDate invalidActivationDate = LocalDate.now().plusDays(daysOffset);
		user.setActivationDate(invalidActivationDate);
		userValidator.activationDateMustExist().apply(user);
	}

	private User aValidUser() {
		return User.builder()
				.fullName("Rowan Dwayne")
				.email("rowan@dwayne.com")
				.activationDate(LocalDate.now())
				.build();
	}
}