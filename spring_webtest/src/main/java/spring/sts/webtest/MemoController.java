package spring.sts.webtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.model.memo.MemoDAO;
import spring.model.memo.MemoDTO;
import spring.utility.webtest.Utility;

@Controller
public class MemoController {
	
	@Autowired
	private MemoDAO dao;
	
	
	@RequestMapping(value="/memo/create",method=RequestMethod.POST)
	public String create(MemoDTO dto) {
		
		if(dao.create(dto)) {
			return "redirect:/memo/list";
		}else {
			
			return "/error/error";
		}
		
	}
	@RequestMapping(value="/memo/create",method=RequestMethod.GET)
	public String create() {
		
		return "/memo/create";
	}
	
	
	@RequestMapping("/memo/list")
	public String list(HttpServletRequest request) {
		//검색관련
		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));
		
		if(col.equals("total"))word="";
		
		//paging관련
		int nowPage = 1;
		int recordPerPage = 5;
		
		if(request.getParameter("nowPage")!=null){
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}

		//DB에서 가져올 순번 생성
		int sno = ((nowPage-1) * recordPerPage) + 1;
		int eno = nowPage * recordPerPage;

		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);	
			
		List<MemoDTO> list = dao.list(map);
		
		int totalRecord = dao.total(map);
		
		String paging = Utility.paging3(totalRecord, nowPage, recordPerPage, col, word);
		
		request.setAttribute("list", list);
		request.setAttribute("paging", paging);
		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("nowPage", nowPage);
		
		return "/memo/list";
	}
	
	

}
