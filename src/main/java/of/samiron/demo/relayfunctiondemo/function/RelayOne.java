package of.samiron.demo.relayfunctiondemo.function;

import java.util.Objects;

@FunctionalInterface
public interface RelayOne<T> {
	void apply(T t);

	default RelayOne<T> next(RelayOne<T> nextFn) {
		Objects.requireNonNull(nextFn);
		return (t) -> {
			this.apply(t);
			nextFn.apply(t);
		};
	}

	default <U> RelayTwo<T, U> next(RelayTwo<T, U> nextFn) {
		Objects.requireNonNull(nextFn);
		return (t, u) -> {
			this.apply(t);
			nextFn.apply(t, u);
		};
	}
}
