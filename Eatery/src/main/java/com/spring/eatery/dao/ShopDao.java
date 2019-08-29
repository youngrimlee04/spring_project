package com.spring.eatery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDao {
	@Autowired
	JdbcTemplate jt;
	
	public int save(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO SHOP (ID, NAME, ADDRESS, LAT, LNG, CRE_DATE)");
		sql.append("  VALUES (NULL, ?, ?, ?, ?, NOW())");
		return jt.update(
				sql.toString(), 
				map.get("name"), map.get("address"), map.get("lat"), map.get("lng"));
	}

	public List<Map<String, Object>> findAll() {
		StringBuffer sql = new StringBuffer();
		
		// TODO SHOP SHOP TABLE + SHOP_IMG TABLE's image together select(JOIN)
		// shoplist.jsp에서 FILE_NM 속성이 없으면 error.png로 해골 뜸
		sql.append("SELECT S.*, SI.FILE_NM");
		sql.append(" FROM SHOP S");
		sql.append(" JOIN SHOP_IMG SI");
		sql.append("   ON S.ID= SI.SHOP_ID");
		sql.append("   WHERE SI.ORD =1;");
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
