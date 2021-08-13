package of.samiron.demo.relayfunctiondemo.validations.user;

import of.samiron.demo.relayfunctiondemo.model.User;
import of.samiron.demo.relayfunctiondemo.function.RelayTwo;

import java.util.Objects;

/**
 * These rules need two users. Usually one with the new data, an another is the persisted one.
 * This rule can also be connected to a SingleUserRule relaying only the first argument.
 */
public interface TwoUsersRule extends RelayTwo<User, User> {

	default TwoUsersRule next(TwoUsersRule nextFn) {
		Objects.requireNonNull(nextFn);
		return (t, u) -> {
			this.apply(t, u);
			nextFn.apply(t, u);
		};
	}

	default TwoUsersRule next(SingleUserRule nextFn) {
		Objects.requireNonNull(nextFn);
		return (t, u) -> {
			nextFn.apply(t);
			this.apply(t, u);
		};
	}

}
