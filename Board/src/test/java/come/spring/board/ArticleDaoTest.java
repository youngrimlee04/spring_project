package come.spring.board;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import come.spring.board.dao.ArticleDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})

public class ArticleDaoTest {
	@Autowired
	ArticleDao dao;
	
	@Test
	public void select() {
		List<Map<String, Object>> list = dao.select();
		System.out.println(list);
		}
	}
	

