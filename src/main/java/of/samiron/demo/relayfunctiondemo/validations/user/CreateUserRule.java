package of.samiron.demo.relayfunctiondemo.validations.user;

import of.samiron.demo.relayfunctiondemo.model.User;
import of.samiron.demo.relayfunctiondemo.function.RelayOne;

import java.util.Objects;

public interface CreateUserRule extends RelayOne<User> {

	default CreateUserRule next(CreateUserRule nextFn) {
		Objects.requireNonNull(nextFn);
		return (t) -> {
			this.apply(t);
			nextFn.apply(t);
		};
	}
}
