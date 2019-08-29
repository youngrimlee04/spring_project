package edu.spring.sample.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao2 {
	@Autowired
	SqlSessionTemplate ss;
	
	public void selectMember() { // Member.xml의 mapper에서 만든 namespace와 id 가져옴
	List<Map<String, Object>> map = 
			ss.selectList("member.selectAll", null);
	System.out.println(map);
	}
	
	public void selectById() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id","t"); //id가 t로 시작하는 데이터들 조회해옴
		
		ss.selectList("member.selectById", map);
	}
}