package edu.spring.sample.controller;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import edu.spring.sample.service.HomeService;
import edu.spring.sample.service.WriteService;

@Controller
public class HomeController {
	
	@Autowired
	HomeService homeService;
	
	@Autowired
	WriteService writeService;
	
	@RequestMapping(value="/delete/{id}")
	@ResponseBody // 삭제 후 1, 못하면 0찍기 위해
	public String delete(@PathVariable("id") int id) {
		int result = writeService.delete(id);
		return "" + result;
	}
	
   //수정 화면과 실제 데이터를 처리할 페이지 따로 필요하므로 GET과 POST 나눔 
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editNum(@PathVariable("id") int id, Model model) {
		Map<String, Object> map = writeService.findById(id);
		model.addAttribute("map",map);
		return "edit";
	}
	
     // 수정 화면과 실제 데이터를 처리할 페이지 따로 필요하므로 GET과 POST 나눔 
	// http://localhost/sample/edit/162(id)
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editNumPost(@PathVariable("id") int id, Model model,
			@RequestParam Map<String, Object> map) { // ?뒤에 어떤 값
		
	// request param으로 id 값을 넘겨 받는 게 없으므로 아래 코드 추가
		map.put("id",id);
		int result = writeService.update(map);
		return map; //JSON 응답
					// 스프링 외부조립기에 mvc:annotation-driven
					// message-converter
		
		// return "redirect:/show"; ﻿// 수정완료 후 원래 list 게시판으로 넘어옴
		// return "redirect:/show/" + id;
		// return String.valueOf(result);
		// return result + "";
	}
	
	
	@RequestMapping(value="/show/{id}")
	public String showNum(@PathVariable("id") int id, Model model) {
		Map<String, Object> map = writeService.findById(id);
		model.addAttribute("map",map);
		return "view";
	}
	
	@RequestMapping(value="/show")
	public String show(Model model, 
			@RequestParam(name="page", defaultValue="1") int page,//? 뒤 page 숫자 값을 알아서 int page에 대입
			@RequestParam(name="search", defaultValue="") String search) { // http://localhost/sample/show?search=d
		
		if(page<1) {
			page=1;
		}
		
		int endRow = page*10;
		int startRow =endRow -9; // 이걸 service dao mybatis로
		
		model.addAttribute("list",writeService.findAll(startRow,search)); //﻿호출 결과를 Model에 넣어주는 게 Spring 규칙
		
		int totalCount = writeService.getTotalCount();
		model.addAttribute("totalCount", totalCount);
		
		int totalPage = totalCount / 10 + 1;
		if(totalCount%10 ==0) {
			totalPage -=1;
		}
		model.addAttribute("totalPage", totalPage);
		
		// 이렇게 찾은 페이지를 jsp로 넘김
		int startPage = page / 10 * 10 + 1;
		int endPage = startPage + 9;
		if(endPage > totalPage) {
			endPage=totalPage;
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		
		return "show"; // 호출 결과를 show.jsp로 지정함
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "write";
	}
	
	@RequestMapping(value="/download", method=RequestMethod.GET)
	public String download() {
		// FileDownloadView의 ID를 return으로 써주면 Bean Name ViewResolver 동작
		return "download";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	@ResponseBody
	public String writePost(
			@RequestParam Map<String, String> map,
			MultipartHttpServletRequest mReq){ 
		 // sample이라는 프로젝트의 WEB-INF까지 찾아주는 경로 
		String path = this.getClass().getClassLoader().getResource("").getPath();
		// System.out.println(path);
		path = path.substring(0,path.indexOf("WEB-INF"));
		// System.out.println(path);
		
		// 위의 경로 하위에 resource 디렉토리가 없다면 생성 
		File dir = new File(path+"/resources");
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		
		// 파일을 DB에 넣으려면 아래 MultipartFile 등 필요 
		MultipartFile mFile1 = mReq.getFile("file1");
		MultipartFile mFile2 = mReq.getFile("file2");
		
		String file1 = mFile1.getOriginalFilename();
		String file2 = mFile2.getOriginalFilename();
		
		long unixTime =0;
		if(!file1.equals("")) {
		// 파일명 xxx를 xxx_232323(초).jpg로 바꾸기
		// 1970년 1월 1일 00시 ~ 현재까지 시간(초)
				unixTime = System.currentTimeMillis();
				String fileName = file1.substring(0, file1.indexOf("."));
				String fileExt = file1.substring(file1.indexOf("."));
				file1 = fileName + "_" + unixTime + fileExt;
		
		try {
			// 다운 받은 파일을 지정한 경로에 저장하는 코드
			// 1. NAS(url 통해 접근 불가)에 저장 : 보안을 위함 
			// 2. 프로젝트 내부(url 통해 접근 가능)에 저장 : 쉬운 접근성 
			mFile1.transferTo(new File(path+"/resources/"+file1));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		if(!file2.equals("")) { //파일2가 공백이 아닐 경우 작동
			unixTime = System.currentTimeMillis();
			String fileName = file2.substring(0, file2.indexOf("."));
			String fileExt = file2.substring(file2.indexOf("."));
			
			file2 = fileName + "_" + unixTime + fileExt;
			
			try {
				mFile2.transferTo(new File(path+"/resources/"+file2));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		map.put("file1",file1);
		map.put("file2",file2);
				
		writeService.insert(map);	
		return "write";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	@ResponseBody
	public String uploadPost(MultipartHttpServletRequest mReq) {
		MultipartFile mFile = mReq.getFile("file"); //jsp에서 지정한 file의 name 가져옴
		String oName = mFile.getOriginalFilename();
		long size = mFile.getSize();
			
		String path = this.getClass().getClassLoader().getResource("").getPath();
		// System.out.println(path);
		path = path.substring(0,path.indexOf("WEB-INF"));
		// System.out.println(path);
			
		// root의 dev에 파일 저장하고 경로와 파일명 명시 
		try {
			mFile.transferTo(new File(path+"/resources/"+oName));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MultipartFile mFile2 = mReq.getFile("file2"); //jsp에서 지정한 file의 name 가져옴
		String oName2 = mFile2.getOriginalFilename();
		long size2 = mFile2.getSize();
		
		// root의 dev에 파일 저장하고 경로와 파일명 명시 
		try {
			mFile2.transferTo(new File(path+"/resources/"+oName2));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<MultipartFile> files = mReq.getFiles("file3");
		for(int i=0; i<files.size(); i++) {
		MultipartFile f = files.get(i);
		String n = f.getOriginalFilename();
		long size3 = f.getSize();
		
		try {
			f.transferTo(new File(path+"/resources/"+n));
		} catch (IllegalStateException e) {
		e.printStackTrace();
		} catch (IOException e) {

		e.printStackTrace();
		}
		}
		return oName + "//" + size;
		}

	
		@RequestMapping(value="/upload", method=RequestMethod.GET)
		public String upload() {
			// c:/dev/.../Sample/.../Webinf/clases../HomeController 
			String path = this.getClass().getClassLoader().getResource("").getPath();
			System.out.println(path);
			path = path.substring(0,path.indexOf("WEB-INF"));
			System.out.println(path);
		return "upload";
		}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		List<Map<String, Object>> list = homeService.getMemberList();
		// MVC의 request.setAttribute와 동일 
		model.addAttribute("list", list);
		
		return "home";
	}
	
}
