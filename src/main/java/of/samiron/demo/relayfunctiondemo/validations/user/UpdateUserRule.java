package of.samiron.demo.relayfunctiondemo.validations.user;

import of.samiron.demo.relayfunctiondemo.model.User;
import of.samiron.demo.relayfunctiondemo.function.RelayTwo;

import java.util.Objects;

public interface UpdateUserRule extends RelayTwo<User, User> {

	default UpdateUserRule next(UpdateUserRule nextFn) {
		Objects.requireNonNull(nextFn);
		return (t, u) -> {
			this.apply(t, u);
			nextFn.apply(t, u);
		};
	}

	default UpdateUserRule next(CreateUserRule nextFn) {
		Objects.requireNonNull(nextFn);
		return (t, u) -> {
			nextFn.apply(t);
			this.apply(t, u);
		};
	}

}
