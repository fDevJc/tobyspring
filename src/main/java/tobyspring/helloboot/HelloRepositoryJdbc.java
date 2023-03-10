package tobyspring.helloboot;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HelloRepositoryJdbc implements HelloRepository {
	private final JdbcTemplate jdbcTemplate;

	public HelloRepositoryJdbc(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Hello findMember(String name) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM hello where name = '" + name + "'",
				(rs, rowNum) -> new Hello(rs.getString("name"), rs.getInt("count")));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void increaseCount(String name) {
		Hello hello = findMember(name);
		if (hello == null) {
			jdbcTemplate.update("INSERT INTO hello VALUES(?,?)", name, 1);
		} else {
			jdbcTemplate.update("UPDATE hello SET count = ? where name = ?", hello.getCount() + 1, name);
		}
	}
}
