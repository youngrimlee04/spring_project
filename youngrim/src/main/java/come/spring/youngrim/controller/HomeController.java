package come.spring.youngrim.controller;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import come.spring.youngrim.service.HomeService;
import come.spring.youngrim.service.WriteService;

@Controller
public class HomeController {

	@Autowired
	JdbcTemplate jt;

	@Autowired
	HomeService homeService;

	@Autowired
	WriteService writeService;

	// (1) /write는 WriteService, WriteDao와 함께 파일을 DB에 넣기
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	@ResponseBody
	public String writePost(@RequestParam Map<String, Object> map, MultipartHttpServletRequest mReq) {
		// 다운 받은 파일 DB에 넣기 위해 MultipartFile 사용
		MultipartFile mFile1 = mReq.getFile("file1");
		MultipartFile mFile2 = mReq.getFile("file2");

		String file1 = mFile1.getOriginalFilename();
		String file2 = mFile2.getOriginalFilename();

		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.substring(0, path.indexOf("WEB-INF/"));

		// 위의 path 경로 밑에 resources 폴더 생성
		File dir = new File(path + "/resources");
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}

		// (3) 파일명 xxx를 xxx_2323(현재 시각 초).jpg로 바꾸기
		if (!file1.equals("")) { // 파일1이 공백이 아닐 경우에만 작동하게 제어
			long unixTime = System.currentTimeMillis();
			String fileName = file1.substring(0, file1.indexOf("."));
			String fileExt = file1.substring(file1.indexOf("."));
			file1 = fileName + "_" + unixTime + fileExt;

			try {
				mFile1.transferTo(new File(path + "/resources/" + file1));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		if (!file2.equals("")) { // 파일1이 공백이 아닐 경우에만 작동하게 제어
			long unixTime = System.currentTimeMillis();
			String fileName = file2.substring(0, file2.indexOf("."));
			String fileExt = file2.substring(file2.indexOf("."));
			file2 = fileName + "_" + unixTime + fileExt;

			try {
				mFile2.transferTo(new File(path + "/resources/" + file2));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		map.put("file1", file1);
		map.put("file2", file2);

		writeService.insert(map);
		return "write";
	}

	// (2) uploadPOST와 uploadGET은
	// http:localhost:8080/youngrim/upload에서 내가 첨부한 파일이 dev에 들어오게 함
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadPost(MultipartHttpServletRequest mReq) {
		MultipartFile mFile = mReq.getFile("file");
		String Name = mFile.getOriginalFilename();
		long size = mFile.getSize();

		String path = this.getClass().getClassLoader().getResource("").getPath();
		// System.out.println(path);
		path = path.substring(0, path.indexOf("WEB-INF"));
		// System.out.println(path);

		try {
			mFile.transferTo(new File(path + "/resources/" + Name));
		} catch (Exception e) {
			// TODO: handle exception
		}

		// List는 화면에서 파일을 여러 개 선택케 함
		String n = "";
		long size2 = 0;
		List<MultipartFile> files = mReq.getFiles("file2");
		for (int i = 0; i < files.size(); i++) {
			MultipartFile f = files.get(i);
			n = f.getOriginalFilename();
			size2 = f.getSize();
			try {
				f.transferTo(new File(path + "/resources/" + n));
			} catch (Exception e) {
				// TODO: handle exception
			}

		}
		return "first file name and size : " + Name + "//" + size + "<br>" + "second file name and size : " + n + "//"
				+ size2;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload() {
		// c:/dev/.../Sample/.../Webinf/clases../HomeController
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.substring(0, path.indexOf("WEB-INF"));

		return "upload";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		List<Map<String, Object>> list = homeService.getMemberList();
		model.addAttribute("list", list);

		return "home";
	}

	// (4) http://localhost:8080/youngrim/download 접속시 내가 지정한 파일을 사용자가 다운
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download() {
		return "download";
	}

	// (5) 페이징 처리한 화면 show에서 뿌려주기 (http://localhost:8080/youngrim/show)
	@RequestMapping(value = "/show")
	public String show(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "search", defaultValue = "") String search) {
		if (page < 1) {
			page = 1;
		}

		int endRow = page * 10;
		int startRow = endRow - 9;

		model.addAttribute("list", writeService.findAll(startRow, search));
		int totalCount = writeService.getTotalCount();

		model.addAttribute("totalCount", totalCount);

		int totalPage = totalCount / 10 + 1;
		if (totalCount % 10 == 0) {
			totalPage -= 1;
		}
		model.addAttribute("totalPage", totalPage);

		// 이렇게 찾은 페이지를 jsp로 넘김

		int startPage = page / 10 * 10 + 1;
		int endPage = startPage + 9;
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "show"; // 호출 결과를 show.jsp로 지정함
	}

	@RequestMapping(value = "/show/{id}")
	public String showNum(@PathVariable("id") int id, Model model) { // (6) 게시물 상세 조회
		Map<String, Object> map = writeService.findById(id);
		model.addAttribute("map", map);
		return "view";
	}

	// (7) 게시글 수정
	// 수정 화면과 실제 데이터를 처리할 페이지 따로 필요하므로 GET과 POST 나눔
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editNum(@PathVariable("id") int id, Model model) {
		Map<String, Object> map = writeService.findById(id);
		model.addAttribute("map",map);
		return "edit";
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	// @ResponseBody
	public String editNumPost(@PathVariable("id") int id, Model model,
	@RequestParam Map<String, Object> map) { //  edit 한 정보가 get에서 POST로 다 넘어왔는데 이걸 map으로 받아줌
		map.put("id",id); // 근데 그 값의 기준값을 id값으로 잡음
		int result = writeService.update(map);
		return "redirect:/show"; // 수정완료 후 원래 list 게시판으로 넘어옴

	}
	
	@RequestMapping(value="delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") int id) { // 글 삭제
		int result = writeService.delete(id);
		return "" + result;
	}
	
	// 답글 달기
	@RequestMapping(value="list")

	public String list(Model model) {
	model.addAttribute("list", writeService.select());
	return "list";
	}
	@RequestMapping(value="reply", method=RequestMethod.GET)
	public String reply(Model model) {
		return "reply";
	}

	@RequestMapping(value="reply", method=RequestMethod.POST)
	@ResponseBody
	public String replyPost(@RequestParam Map<String, Object> map) {
		writeService.replyinsert(map);
		return "Success";
	}

}
