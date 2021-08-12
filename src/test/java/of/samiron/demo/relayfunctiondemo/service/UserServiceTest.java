package of.samiron.demo.relayfunctiondemo.service;

import of.samiron.demo.relayfunctiondemo.model.User;
import of.samiron.demo.relayfunctiondemo.repository.UserRepository;
import of.samiron.demo.relayfunctiondemo.validations.user.UserValidationException;
import of.samiron.demo.relayfunctiondemo.validations.user.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static of.samiron.demo.relayfunctiondemo.util.TestUtil.echoRepositorySave;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private static final LocalDate TODAY = LocalDate.now();
	private static final LocalDate TOMORROW = TODAY.plusDays(1);
	private static final LocalDate NEXT_MONTH = TODAY.plusMonths(1);
	private static final LocalDate YESTERDAY = TODAY.minusDays(1);
	private static final AtomicInteger userIdCounter = new AtomicInteger();

	@Mock
	UserRepository userRepository;

	@Spy
	UserValidator userValidator;

	@InjectMocks
	UserService userService;


	@Test
	void createUser_success() {
		User user = validUser(null, "Person 1");
		user.setExpirationDate(null);

		// Given
		mockUserRepositorySave();
		userValidator.setUserRepository(userRepository);

		// When
		User createdUser = userService.createUser(user);

		// Then
		Mockito.verify(userRepository).save(user);
		assertThat(createdUser.getId(), is(equalTo(userIdCounter.get())));
	}

	@Test
	void createUser_fail_expiration_exists() {
		User user = validUser(null, "Person 1");

		// When
		Throwable th = assertThrows(UserValidationException.class, () -> userService.createUser(user));

		//Then
		assertThat(th.getMessage(), is(equalTo("Should not have any expiration date when creating")));
		Mockito.verifyNoMoreInteractions(userRepository);
	}

	@Test
	void updateUser_success() {
		User oldUser = validUser(1, "Person 1");
		User newUser = validUser(1, "Person 1");
		newUser.setFullName("Person 1 name changed");

		// Given
		mockUserRepositorySave();
		Mockito.doReturn(Optional.of(oldUser)).when(userRepository).findById(1);

		// When
		User savedUser = userService.updateUser(newUser);

		// Then
		assertThat(savedUser.getId(), is(equalTo(1)));
		assertThat(savedUser.getFullName(), is(equalTo("Person 1 name changed")));
	}

	@Test
	void updateUser_fail_expiration_before_activation() {
		User oldUser = validUser(1, "Person 1");
		User newUser = validUser(1, "Updated Name");
		newUser.setExpirationDate(YESTERDAY);

		// Given
		Mockito.doReturn(Optional.of(oldUser)).when(userRepository).findById(1);

		// When
		Throwable th = assertThrows(
				UserValidationException.class,
				() -> userService.updateUser(newUser)
		);

		// Then
		assertThat(th.getMessage(), is(equalTo("Expiration date can not be before activation date")));
		Mockito.verify(userRepository).findById(1);
		Mockito.verifyNoMoreInteractions(userRepository);
	}

	@Test
	void createUser_fail_unique_email_address() {
		String email = "already@exists.com";
		User newUser = validUser(null, "Person two");
		newUser.setEmail(email);
		newUser.setExpirationDate(null);

		//Given
		Mockito.doReturn(1L).when(userRepository).countByEmail(email);
		userValidator.setUserRepository(userRepository);

		// When
		Throwable th = assertThrows(UserValidationException.class, () -> userService.createUser(newUser));

		// Then
		assertThat(th.getMessage(), is(equalTo("Email address already exists")));
		Mockito.verify(userRepository).countByEmail(email);
		Mockito.verify(userValidator).validateCreateUser(any(User.class));
	}

	private void mockUserRepositorySave() {
		userIdCounter.set(100);

		Answer<User> userSaveAnswer = echoRepositorySave((user) -> {
			if(user.getId() == null) {
				user.setId(userIdCounter.incrementAndGet());
			}
		});

		Mockito.doAnswer(userSaveAnswer)
				.when(userRepository)
				.save(any(User.class));
	}

	private User validUser(Integer id, String name) {
		return User.builder()
				.id(id)
				.fullName(name)
				.email("rowan@dwayne.com")
				.activationDate(TODAY)
				.expirationDate(NEXT_MONTH)
				.build();
	}
}