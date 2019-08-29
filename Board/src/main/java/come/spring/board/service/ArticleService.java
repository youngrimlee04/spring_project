package come.spring.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import come.spring.board.dao.ArticleDao;
import come.spring.board.dao.CommentDao;

@Service
public class ArticleService {
	@Autowired
	ArticleDao articleDao;
	
	@Autowired
	CommentDao commentDao;
	
	public void insertComment(Map<String, Object> map) {
		commentDao.insert(map);
	}
	
	public void insert(Map<String, Object> map) {
		// Client가 입력한 데이터를 받아오면 무조건 String이므로
		// Integer.parseInt() 로 감싸서 String을 숫자로 변환
		// reply.jsp에서 Controller로 갔던 값들이 여기 온 것임
		// 답글들 중 최신 답글이 위로 올라오게 하기 (기존 답변 글 순서 변경)
		int ord = Integer.parseInt((String) map.get("ord"));
		map.put("ord", ord+1);
		int level = Integer.parseInt((String) map.get("level"));
		map.put("level", level+1);
		
		articleDao.increaseOrd(map);
		// 기존 답변 글들의 ord를 +1 하는 Dao 호출
				
		int result = articleDao.insert(map);
		// dao에서 insert int였음
	}
	public List<Map<String, Object>> select() {
		return articleDao.select();
	}
	public Map<String, Object> selectById(int id) {
		// select할 때 조회수 증가
		articleDao.update(id); //조회수 증가
		return articleDao.selectById(id); // http://localhost:8080/board/show/1로 상세 보기 
	}
}
