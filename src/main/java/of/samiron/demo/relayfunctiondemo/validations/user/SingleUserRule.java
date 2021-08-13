package of.samiron.demo.relayfunctiondemo.validations.user;

import of.samiron.demo.relayfunctiondemo.model.User;
import of.samiron.demo.relayfunctiondemo.function.RelayOne;

import java.util.Objects;

/**
 * These rules concerns only one user object.
 * It does not need any other object for the business logic.
 */
public interface SingleUserRule extends RelayOne<User> {

	default SingleUserRule next(SingleUserRule nextFn) {
		Objects.requireNonNull(nextFn);
		return (t) -> {
			this.apply(t);
			nextFn.apply(t);
		};
	}
}
