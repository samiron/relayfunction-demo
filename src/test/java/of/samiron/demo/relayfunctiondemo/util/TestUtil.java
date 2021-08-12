package of.samiron.demo.relayfunctiondemo.util;

import org.mockito.stubbing.Answer;

import java.util.function.Consumer;

public class TestUtil {

	/**
	 * This method returns back the User object that passed in the userRepository.save() call.
	 * replicating the original repos
	 * @return
	 */
	public static <T> Answer<T> echoRepositorySave(Consumer<T> modifier){
		return (invocationOnMock -> {
			T arg = invocationOnMock.getArgument(0);
			if(modifier != null) {
				modifier.accept(arg);
			}
			return arg;
		});
	}
}
