package of.samiron.demo.relayfunctiondemo.function;

@FunctionalInterface
public interface RelayTwo<T, U> {
	void apply(T t, U u);
}
