package tobyspring.helloboot;

public interface HelloRepository {
	Hello findMember(String name);

	void increaseCount(String name);

	default int countOf(String name) {
		Hello hello = findMember(name);
		return hello == null ? 0 : hello.getCount();
	}
}
