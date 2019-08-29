package come.spring.youngrim.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository // component-scan의 스캔 대상
public class WriteDao {
	@Autowired
	JdbcTemplate jt;
	
	@Autowired
	SqlSessionTemplate ss;
	
	public Map<String, Object> findById(int id) { // 게시물 상세 조회
		Map<String, Object> map = ss.selectOne("write.selectById",id);
		return map;
		}

	public int findMaxId() { // 게시물 마지막 번호 조회
		int maxId = ss.selectOne("write.findMaxId");
		return maxId;
	}
	
	public void save(Map<String, Object> map) { // insert 기능, hit는 0 넣어주고 ID는 db에서 autoincrement로 수정, write_date는 값 넣지 말기
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `TABLE` (ID, TITLE, CONTENT, HIT, WRITER, WRITE_DATE, GRP, ORD, LEVEL, FILE1, FILE2)");
		sql.append(" VALUES (NULL,?,?,0,?,NOW(),?,?,?,?,?)");
		jt.update(sql.toString(), map.get("title"), map.get("content"), map.get("writer"), map.get("grp"),
				map.get("ord"), map.get("level"), map.get("file1"), map.get("file2"));

	}
	
	public List<Map<String, Object>> findAll(int startRow, String search){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startRow", startRow-1);
		map.put("search", search);
		List<Map<String, Object>> list = ss.selectList("write.selectAll", map);
		return list;
	}
	
	public int getTotalCount() {
		return ss.selectOne("write.totalCount");
		}
	
	public int update(Map<String, Object> map) { //글 수정
		int result = ss.update("write.update", map);
		return result;
		}
	
	public int delete(int id) { //글 삭제
		int result = ss.delete("write.delete", id);
		return result;
		}
	
	// 답글
	public void increaseOrd(Map<String, Object> map) {
		ss.update("write.increaseOrd", map);
		}

	public int update(int id) {
			return ss.update("write.updateHit", id);
		}

	public List<Map<String,Object>> select() {
		return ss.selectList("write.select");
		}
	
	public int replyinsert(Map<String, Object> map) {
		return ss.insert("write.replyinsert", map);
		}
	
	public Map<String,Object> selectById(int id) {
		return ss.selectOne("write.findById", id);
		}
	
	public int updateByGrp(Map<String, Object> map) {
		return ss.update("write.updateByGrp", map);
	}
	
	
}
