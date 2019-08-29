package come.spring.youngrim.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
	@Autowired
	JdbcTemplate jt;
	
	public List<Map<String, Object>> selectMember() {
	String sql = "select * from `table`";
	
	List<Map<String, Object>> list = jt.queryForList(sql); // sql ���� �ϱ� ���� �ʿ� 
	return list; // MemberDao�� �θ� HomeService�� list�� ������ 
	}

}