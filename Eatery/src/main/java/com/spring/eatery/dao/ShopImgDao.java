package com.spring.eatery.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShopImgDao {
	@Autowired
	JdbcTemplate jt;
	
	public int save(Map<String, Object> map) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO SHOP_IMG (ID, SHOP_ID, ORD, FILE_NM)");
		sql.append("  VALUES (NULL, ?, (SELECT IFNULL(MAX(S.ORD) + 1, 1)");
		sql.append("                      FROM SHOP_IMG S");
		sql.append("                     WHERE S.SHOP_ID = ?), ?)");
		return jt.update(
				sql.toString(), 
				map.get("shopId"), map.get("shopId"), map.get("fileNm"));
	}

	public List<Map<String, Object>> findByShopId(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID, SHOP_ID, ORD, FILE_NM");
		sql.append("  FROM SHOP_IMG");
		sql.append(" WHERE SHOP_ID = ?");
		return jt.queryForList(sql.toString(), id);
	}
}
