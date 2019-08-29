package come.spring.board.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import come.spring.board.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	ArticleService articleService;
	
	// http://localhost:8080/board/addComment?id=1&content=aaaa
	@RequestMapping(value="addComment")
	public String addComment(@RequestParam Map<String, Object> map) {
		articleService.insertComment(map);
		return "list";
	}
	
	@RequestMapping(value="list")
	public String list(Model model) {
		model.addAttribute("list", articleService.select());
		return "list";
	}
	// �Խù� ��ȸ
	@RequestMapping(value="show/{id}")
	public String show(Model model, @PathVariable("id") int id) {
		model.addAttribute("board", articleService.selectById(id));
		return "board";
	}
	@RequestMapping(value="reply", method=RequestMethod.GET)
	public String reply(Model model) {
		
		return "reply";
	}
	
	//reply.jsp�� form�� ������ name�� �Էµ� ������ key�� value������ map�� ����
	//�� map�� service, dao�� �Ѱܾ� �� mybatis(Article.xml) dao service �� ������ controller�� ����
	@RequestMapping(value="reply", method=RequestMethod.POST)
	@ResponseBody
	public String replyPost(@RequestParam Map<String, Object> map) {
		articleService.insert(map);
		return "Success";
	}
}
