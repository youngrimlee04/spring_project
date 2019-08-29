package come.spring.youngrim.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import come.spring.youngrim.dao.WriteDao;

@Service
public class WriteService {
	@Autowired
	WriteDao writeDao;

	public Map<String, Object> findById(int id) {
		Map<String, Object> map = writeDao.findById(id);
		return map;
	}

	// ���� �ۼ�
	public void insert(Map<String, Object> map) {
		map.put("ord", "1");
		map.put("level", "1");
		writeDao.save(map);
		
		// ������ ��ϵ� �Խñ� ��ȣ�� ��ȸ�� �� GRP �����ͷ� �Է�
		// �� ID�� 11���̸� �� GRP�� 11��
		int maxId = writeDao.findMaxId();
		map.put("grp", maxId + "");
		map.put("maxId", maxId + "");
		
		writeDao.updateByGrp(map);
	}

	public List<Map<String, Object>> findAll(int startRow, String search) {
		return writeDao.findAll(startRow, search);
	}

	public int getTotalCount() {
		return writeDao.getTotalCount();
	}

	public int update(Map<String, Object> map) { // �� ����
		return writeDao.update(map);
	}

	public int delete(int id) { // �� ����
		return writeDao.delete(id);
	}

	// ��� �ۼ�
	public void replyinsert(Map<String, Object> map) {
		int ord = Integer.parseInt((String) map.get("ord"));
		map.put("ord", ord + 1);
		int level = Integer.parseInt((String) map.get("level"));
		map.put("level", level + 1);
		writeDao.increaseOrd(map);

		// ���� �亯 �۵��� ord�� +1 �ϴ� Dao ȣ��
		int result = writeDao.replyinsert(map);
	}

	public List<Map<String, Object>> select() {
		return writeDao.select();
	}

	public Map<String, Object> selectById(int id) {
		writeDao.update(id); // ��ȸ�� ����
		return writeDao.findById(id); // http://localhost:8080/board/show/1�� �� ����

	}

}