package come.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleDao {
	@Autowired
	SqlSessionTemplate ss;
	
	public void increaseOrd(Map<String, Object> map) {
		ss.update("article.increaseOrd", map);
	}
	// insert/update/delete 등 DB 변경하는 작업 시 결과값은 정수로 나오므로 int 필요
	// 했던 작업수로 작업을 했으면 >0 안했으면 = 0
	public int insert(Map<String, Object> map) {
		return ss.insert("article.insert", map);
	}
	
	public int update(int id) {
		return ss.update("article.update", id);
	}
	
	public List<Map<String,Object>> select() {
		return ss.selectList("article.select");
	}
	public Map<String,Object> selectById(int id) {
		return ss.selectOne("article.selectById", id);
	}
}
