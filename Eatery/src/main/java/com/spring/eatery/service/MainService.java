package com.spring.eatery.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.eatery.dao.ShopDao;
import com.spring.eatery.dao.ShopImgDao;

@Service
public class MainService {
	@Autowired
	ShopDao shopDao;
	@Autowired
	ShopImgDao shopImgDao;
	
	public Map<String, Object> addShop(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<>();
		// shop ���̺� ���� ����
		shopDao.save(map);
		
		// ��� �Էµ� ���� ��ȣ ��ȸ
		int maxShopId = shopDao.findMaxId();
		
		map.put("shopId", maxShopId);
		// �� ���忡 ���� �̹��� �� ÷�������� �������� �� �����Ƿ� Controller�� fileList�� �ݺ������� �ϳ��� �����鼭 shoping�� insert ���
		List<String> fileNames = (List<String>) map.get("fileNames");
		for(String fileName : fileNames) {
			map.put("fileNm", fileName);
			shopImgDao.save(map);
		}
		resultMap.put("code", 200);
		resultMap.put("msg", "Your shop enrollment has been finished.");
		map.remove("fileNm");
		resultMap.put("data", map);
		return resultMap;
	}
	
	public List<Map<String, Object>> getShopList(Map<String, Object> map) {
		String lat = (String)map.get("lat");
		String lng = (String)map.get("lng");
		String distance = (String)map.get("distance");
		if(lat !=null && lng !=null && distance !=null) {
			return shopDao.findAll(map);
		}
		return shopDao.findAll();
	}

	public Map<String, Object> getShopInfo(String id) {
		Map<String, Object> shopInfo = shopDao.findById(id);
		List<Map<String, Object>> shopImgs = shopImgDao.findByShopId(id);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("shopInfo", shopInfo);
		resultMap.put("shopImgs", shopImgs);
		return resultMap;
	}
}
