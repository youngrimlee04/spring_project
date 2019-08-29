package come.spring.youngrim.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import come.spring.youngrim.dao.MemberDao;

@Service
public class HomeService {
	@Autowired
	MemberDao memberDao;
	
	public List<Map<String, Object>> getMemberList() {
		return memberDao.selectMember();
	}
}
