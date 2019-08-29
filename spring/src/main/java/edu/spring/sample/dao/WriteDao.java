package edu.spring.sample.dao;

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

	public int delete(int id) {
		int result = ss.delete("write.delete", id);
		return result;
	}
	
	public int update(Map<String, Object> map) {
		int result = ss.update("write.update", map);
		return result;
	}
	
	public Map<String, Object> findById(int id) {
		Map<String, Object> map = ss.selectOne("write.select",id);
		return map;
	}
	
	public int getTotalCount() {
		return ss.selectOne("write.totalCount");
	}
	
	public List<Map<String, Object>> findAll(int startRow, String search) {
		// 마이바티스는 여러 개 자료 못넘김, int String 하나로 만들어서 넘기기
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startRow", startRow-1);
		map.put("search", search);
		List<Map<String, Object>> list = ss.selectList("write.selectAll", map);
		// -1 안하면 1~10이 아닌 2~11로 첫 페이지에 뽑힘 
		return list;
	}
	
public void save(Map<String, String> map) { //insert 기능
	StringBuffer sql = new StringBuffer();
	sql.append("INSERT INTO `WRITE` (ID, TITLE, FILE1, FILE2)");
	sql.append(" VALUES (NULL,?,?,?)");
	jt.update(sql.toString(), map.get("title"), map.get("file1"),map.get("file2"));
	}
}
