package com.spring.eatery.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDao {
	@Autowired
	JdbcTemplate jt;
	@Autowired
	SqlSessionTemplate ss;
	// save와 findall만 mybatis 사용, 나머지는 JDBCTEMPLATE
	public int save(Map<String, Object> map) {
		return ss.insert("shop.save", map);
		/*
		 * StringBuffer sql = new StringBuffer();
		 * sql.append("INSERT INTO SHOP (ID, NAME, ADDRESS, LAT, LNG, CRE_DATE)");
		 * sql.append("  VALUES (NULL, ?, ?, ?, ?, NOW())"); return jt.update(
		 * sql.toString(), map.get("name"), map.get("address"), map.get("lat"),
		 * map.get("lng"));
		 */
	}

	public List<Map<String, Object>> findAll(Map<String, Object> map) { // 반경 ? m 이내에 있는 매장 찾기
		return ss.selectList("shop.findAll",map);
		/*
		 * StringBuffer sql = new StringBuffer();
		 * 
		 * sql.append("SELECT S.*, SI.FILE_NM");
		 * sql.append(" , (6371*acos(cos(radians(?))");
		 * sql.append("	*cos(radians(LAT))*cos(radians(LNG)"); // 그냥 join 하면 사진 꼭 있는 경우만
		 * 매장 등록됨, outer join 해서 사진 없어도 등록케 함(해골 이미지)
		 * sql.append("  -radians(?))+sin(radians(?))");
		 * sql.append("	*sin(radians(LAT)))) AS distance"); sql.append("  FROM SHOP S");
		 * sql.append("  LEFT OUTER JOIN SHOP_IMG SI");
		 * sql.append("    ON S.ID= SI.SHOP_ID"); sql.append("   AND SI.ORD = 1");
		 * sql.append(" HAVING distance <= ?"); return jt.queryForList( sql.toString(),
		 * map.get("lat"), map.get("lng"),map.get("lat"),map.get("distance"));
		 */
		
	}
	
	public List<Map<String, Object>> findAll() {
		StringBuffer sql = new StringBuffer();
		// SHOP TABLE + SHOP_IMG TABLE's image together select(JOIN)
		// shoplist.jsp에서 FILE_NM 속성이 없으면 error.png로 해골 뜸
		sql.append("SELECT S.*, SI.FILE_NM");
		sql.append(" FROM SHOP S");
		sql.append(" LEFT OUTER JOIN SHOP_IMG SI"); // 그냥 join 하면 사진 꼭 있는 경우만 매장 등록됨, outer join 해서 사진 없어도  등록케 함(해골 이미지)
		sql.append("   ON S.ID= SI.SHOP_ID");
		sql.append("  AND SI.ORD = 1");
		return jt.queryForList(sql.toString());
	}
	
	public Map<String, Object> findById(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID, NAME, ADDRESS, LAT, LNG, CRE_DATE");
		sql.append("  FROM SHOP");
		sql.append(" WHERE ID = ?");
		return jt.queryForMap(sql.toString(), id);
	}

	public int findMaxId() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(ID)");
		sql.append("  FROM SHOP");
		return jt.queryForObject(sql.toString(), Integer.class);
	}
}
