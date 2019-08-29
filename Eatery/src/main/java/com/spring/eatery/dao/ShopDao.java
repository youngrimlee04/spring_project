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
	// save�� findall�� mybatis ���, �������� JDBCTEMPLATE
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

	public List<Map<String, Object>> findAll(Map<String, Object> map) { // �ݰ� ? m �̳��� �ִ� ���� ã��
		return ss.selectList("shop.findAll",map);
		/*
		 * StringBuffer sql = new StringBuffer();
		 * 
		 * sql.append("SELECT S.*, SI.FILE_NM");
		 * sql.append(" , (6371*acos(cos(radians(?))");
		 * sql.append("	*cos(radians(LAT))*cos(radians(LNG)"); // �׳� join �ϸ� ���� �� �ִ� ��츸
		 * ���� ��ϵ�, outer join �ؼ� ���� ��� ����� ��(�ذ� �̹���)
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
		// shoplist.jsp���� FILE_NM �Ӽ��� ������ error.png�� �ذ� ��
		sql.append("SELECT S.*, SI.FILE_NM");
		sql.append(" FROM SHOP S");
		sql.append(" LEFT OUTER JOIN SHOP_IMG SI"); // �׳� join �ϸ� ���� �� �ִ� ��츸 ���� ��ϵ�, outer join �ؼ� ���� ���  ����� ��(�ذ� �̹���)
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
