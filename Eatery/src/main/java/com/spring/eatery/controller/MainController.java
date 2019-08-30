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
		// ���� ������Ʈ�� ��� �˾Ƴ���
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.substring(0, path.indexOf("WEB-INF"));
		
		File dir = new File(path + "/resources/save_img"); // resources�� save_img ���� ����� ���� ���� ���ε�
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		// ���ε�� ���� ���� ����
		List<MultipartFile> list = mRequest.getFiles("img"); // img��� �̸��� ���� �� ��������
		List<String> fileNames = new ArrayList<>(); // mybatis, dao�� String ������ list�� �Ѱܼ� #img�� DB�� ������� �뵵, Multipart�� ���ڿ�����, int���� �˾Ƴ��� �����
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
		} // public Map<String, Object> registerPost(@RequestParam Map<String, Object> map�� register.jsp�� form�� file(MultipartRequest) �� ��� ������ only ��������
		map.put("fileNames", fileNames); // ����� fileList�� map�� �������, ���� �浵�� RequestParam���� ���� �� map���� �� �� �ּ� ����
		
		return mainService.addShop(map);
	}

	@RequestMapping(value = "shop", method = RequestMethod.GET)
	public String shoplist(Model model, @RequestParam Map<String, Object> map) {
		model.addAttribute("list", mainService.getShopList(map)); // ��ϵ� ���� ��ȸ �ؼ� list�� ���·� shoplist�� ������
		return "shoplist";
	}
	
	@RequestMapping(value = "shop2", method = RequestMethod.GET)
	public String shoplist2(Model model, @RequestParam Map<String, Object> map) {
		// model.addAttribute("list", mainService.getShopList(map)); �� ������ �ֱ�
		return "shoplist2";
	}
	
	@RequestMapping(value = "data", method = RequestMethod.GET) // AJAX�� ��û�ϰ� ����� �޴� �޼���, ���� Map ��
	@ResponseBody
	public Map<String, Object> data(Model model, 
			@RequestParam Map<String, Object> map) {
		model.addAttribute("list", mainService.getShopList(map)); 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", mainService.getShopList(map));
		return resultMap;
	}
	
	@RequestMapping(value = "shop/{id}", method = RequestMethod.GET)
	public String shoplist(Model model, @PathVariable("id") String id) { // ���� Ŭ�� �� �󼼺���
		model.addAttribute("info", mainService.getShopInfo(id));
		return "shop";
	}
}


