package spring.model.bbs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BbsService {
	
	@Autowired
	private BbsDAO dao;
	
	public boolean reply(BbsDTO dto) {
		boolean flag = false;
	   try {
			Map map = new HashMap();
			map.put("grpno", dto.getGrpno());
			map.put("ansnum", dto.getAnsnum());
			
			dao.upAnsnum(map);
			
			flag = dao.replyCreate(dto);		
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			 
			
		} finally {
			
		}			
		return flag;
	}
}
