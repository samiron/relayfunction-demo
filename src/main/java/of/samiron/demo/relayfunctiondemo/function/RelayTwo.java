package of.samiron.demo.relayfunctiondemo.function;

import java.util.Objects;

@FunctionalInterface
public interface RelayTwo<T, U> {
	void apply(T t, U u);

	default RelayTwo<T, U> next(RelayTwo<T, U> nextFn) {
		Objects.requireNonNull(nextFn);
		return (t, u) -> {
			this.apply(t, u);
			nextFn.apply(t, u);
		};
	}

	default RelayTwo<T, U> next(RelayOne<T> nextFn) {
		Objects.requireNonNull(nextFn);
		return (t, u) -> {
			this.apply(t, u);
			nextFn.apply(t);
		};
	}

}
