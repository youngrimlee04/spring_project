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
		// Client�� �Է��� �����͸� �޾ƿ��� ������ String�̹Ƿ�
		// Integer.parseInt() �� ���μ� String�� ���ڷ� ��ȯ
		// reply.jsp���� Controller�� ���� ������ ���� �� ����
		// ��۵� �� �ֽ� ����� ���� �ö���� �ϱ� (���� �亯 �� ���� ����)
		int ord = Integer.parseInt((String) map.get("ord"));
		map.put("ord", ord+1);
		int level = Integer.parseInt((String) map.get("level"));
		map.put("level", level+1);
		
		articleDao.increaseOrd(map);
		// ���� �亯 �۵��� ord�� +1 �ϴ� Dao ȣ��
				
		int result = articleDao.insert(map);
		// dao���� insert int����
	}
	public List<Map<String, Object>> select() {
		return articleDao.select();
	}
	public Map<String, Object> selectById(int id) {
		// select�� �� ��ȸ�� ����
		articleDao.update(id); //��ȸ�� ����
		return articleDao.selectById(id); // http://localhost:8080/board/show/1�� �� ���� 
	}
}
