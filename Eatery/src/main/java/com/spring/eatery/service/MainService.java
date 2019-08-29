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
		// shop 테이블에 정보 저장
		shopDao.save(map);
		
		// 방금 입력된 매장 번호 조회
		int maxShopId = shopDao.findMaxId();
		
		map.put("shopId", maxShopId);
		// 한 매장에 여러 이미지 즉 첨부파일이 여러개일 수 있으므로 Controller의 fileList를 반복문으로 하나씩 꺼내면서 shoping에 insert 계속
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
