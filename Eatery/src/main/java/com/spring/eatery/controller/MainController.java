package com.spring.eatery.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.spring.eatery.service.MainService;

@Controller
public class MainController {
	@Autowired
	MainService mainService;
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerPost(@RequestParam Map<String, Object> map,
			MultipartHttpServletRequest mRequest) {
		// 현재 프로젝트의 경로 알아내기
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.substring(0, path.indexOf("WEB-INF"));
		
		File dir = new File(path + "/resources/save_img"); // resources에 save_img 폴더 만들며 매장 사진 업로드
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		// 업로드된 파일 꺼내 오기
		List<MultipartFile> list = mRequest.getFiles("img"); // img라는 이름의 파일 다 꺼내오기
		List<String> fileNames = new ArrayList<>(); // mybatis, dao로 String 형태의 list로 넘겨서 #img로 DB에 집어넣을 용도, Multipart는 문자열인지, int인지 알아내기 어려움
		for (MultipartFile mFile : list) {
			String oFileName = mFile.getOriginalFilename();
			if(oFileName.equals("")) {
				break;
			}
			try {
				mFile.transferTo(new File(path + "/resources/save_img/" + oFileName)); 
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileNames.add("/resources/save_img/" + oFileName);
		} // public Map<String, Object> registerPost(@RequestParam Map<String, Object> map은 register.jsp의 form의 file(MultipartRequest) 뺀 모든 정보로 only 글자정보
		map.put("fileNames", fileNames); // 저장된 fileList를 map에 집어넣음, 위도 경도를 RequestParam으로 받은 그 map으로 윗 줄 주석 참조
		
		return mainService.addShop(map);
	}

	@RequestMapping(value = "shop", method = RequestMethod.GET)
	public String shoplist(Model model, @RequestParam Map<String, Object> map) {
		model.addAttribute("list", mainService.getShopList(map)); // 등록된 매장 조회 해서 list의 형태로 shoplist에 돌려줌
		return "shoplist";
	}
	
	@RequestMapping(value = "shop2", method = RequestMethod.GET)
	public String shoplist2(Model model, @RequestParam Map<String, Object> map) {
		// model.addAttribute("list", mainService.getShopList(map)); 빈 껍데기 주기
		return "shoplist2";
	}
	
	@RequestMapping(value = "data", method = RequestMethod.GET) // AJAX로 요청하고 결과를 받는 메서드, 보통 Map 씀
	@ResponseBody
	public Map<String, Object> data(Model model, 
			@RequestParam Map<String, Object> map) {
		model.addAttribute("list", mainService.getShopList(map)); 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", mainService.getShopList(map));
		return resultMap;
	}
	
	@RequestMapping(value = "shop/{id}", method = RequestMethod.GET)
	public String shoplist(Model model, @PathVariable("id") String id) { // 매장 클릭 시 상세보기
		model.addAttribute("info", mainService.getShopInfo(id));
		return "shop";
	}
}


