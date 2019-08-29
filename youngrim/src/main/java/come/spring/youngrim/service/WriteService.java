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

	// 원글 작성
	public void insert(Map<String, Object> map) {
		map.put("ord", "1");
		map.put("level", "1");
		writeDao.save(map);
		
		// 마지막 등록된 게시글 번호를 조회한 후 GRP 데이터로 입력
		// 내 ID가 11번이면 내 GRP도 11번
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

	public int update(Map<String, Object> map) { // 글 수정
		return writeDao.update(map);
	}

	public int delete(int id) { // 글 삭제
		return writeDao.delete(id);
	}

	// 답글 작성
	public void replyinsert(Map<String, Object> map) {
		int ord = Integer.parseInt((String) map.get("ord"));
		map.put("ord", ord + 1);
		int level = Integer.parseInt((String) map.get("level"));
		map.put("level", level + 1);
		writeDao.increaseOrd(map);

		// 기존 답변 글들의 ord를 +1 하는 Dao 호출
		int result = writeDao.replyinsert(map);
	}

	public List<Map<String, Object>> select() {
		return writeDao.select();
	}

	public Map<String, Object> selectById(int id) {
		writeDao.update(id); // 조회수 증가
		return writeDao.findById(id); // http://localhost:8080/board/show/1로 상세 보기

	}

}