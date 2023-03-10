package tobyspring.helloboot;

public interface HelloRepository {
	Member findMember(String name);

	void increaseCount(String name);

	default int countOf(String name) {
		Member member = findMember(name);
		return member == null ? 0 : member.getCount();
	}
}
