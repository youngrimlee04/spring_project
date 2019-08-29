package come.spring.board.dao;

import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDao {
	@Autowired
	SqlSessionTemplate ss;
	
	public int insert(Map<String, Object> map) {
		return ss.insert("comment.insert", map);
	}
		
	}

