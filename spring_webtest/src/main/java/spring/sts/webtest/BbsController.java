package spring.sts.webtest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.model.bbs.BbsDAO;
import spring.model.bbs.BbsDTO;
import spring.model.bbs.BbsService;
import spring.utility.webtest.Utility;

@Controller
public class BbsController {
	
	@Autowired
	private BbsDAO dao;
	
	@Autowired
	private BbsService mgr;
	
	@RequestMapping(value="/bbs/delete",method=RequestMethod.POST)
	public String delete(int bbsno, String passwd, String oldfile, 
			HttpServletRequest request,Model model) {
		
		String upDir = request.getRealPath("/bbs/storage");
		Map map = new HashMap();
		map.put("bbsno",bbsno);
		map.put("passwd", passwd);

		boolean pflag = dao.passCheck(map);

		if(pflag){
			if(dao.delete(bbsno)) {
				Utility.deleteFile(upDir, oldfile);
				model.addAttribute("nowPage", request.getParameter("nowPage"));
				model.addAttribute("col", request.getParameter("col"));
				model.addAttribute("word", request.getParameter("word"));
				return "redirect:/bbs/list";
			}else {
				return "/error/error";
			}
			
		}else {
			
			return "/error/passwdError";
		}		
	}
	@RequestMapping(value="/bbs/delete",method=RequestMethod.GET)
	public String delete(int bbsno, HttpServletRequest request) {
		
		boolean flag = dao.checkRefnum(bbsno);
		
		request.setAttribute("flag", flag);
		
		return "/bbs/delete";
		
	}
	
	@RequestMapping(value="/bbs/reply", method=RequestMethod.POST)
	public String reply(BbsDTO dto,HttpServletRequest request) {
		String upDir = request.getRealPath("/bbs/storage");
		int filesize = (int)dto.getFilenameMF().getSize();
		String filename = "";
		if(filesize>0)filename = Utility.saveFileSpring(dto.getFilenameMF(), upDir);
		
		dto.setFilename(filename);
		dto.setFilesize(filesize);
		
		boolean flag = mgr.reply(dto);
		
		
		if(flag) {
			
			return "redirect:/bbs/list";
		}else {
			if(!filename.equals("")) 
				Utility.deleteFile(upDir, filename);
			
			return "/error/error";
		}
		
	}
	@RequestMapping(value="/bbs/reply", method=RequestMethod.GET)
	public String reply(int bbsno, Model model) {
		BbsDTO dto = dao.replyRead(bbsno);
		
		model.addAttribute("dto", dto);
		
		return "/bbs/reply";
	}
	
	@RequestMapping(value="/bbs/update", method=RequestMethod.POST)
	public String update(BbsDTO dto, String oldfile, Model model,
			HttpServletRequest request) {
		
		String upDir = request.getRealPath("/bbs/storage");
		
		Map map = new HashMap();
		map.put("bbsno", dto.getBbsno());
		map.put("passwd",dto.getPasswd());
		
		int filesize = (int)dto.getFilenameMF().getSize();
		String filename = "";
		boolean pflag = dao.passCheck(map);
		if(pflag){
			if(filesize>0){
				if(oldfile!=null) Utility.deleteFile(upDir, oldfile);
				filename = Utility.saveFileSpring(dto.getFilenameMF(), upDir);
			}
			
			dto.setFilename(filename);
			dto.setFilesize(filesize);
			
			if(dao.update(dto)) {
				model.addAttribute("nowPage", request.getParameter("nowPage"));
				model.addAttribute("col", request.getParameter("col"));
				model.addAttribute("word", request.getParameter("word"));
				return "redirect:/bbs/list";
			}else {
				return "/error/error";
			}
		}else {
			return "/error/passwdError";
		}

	}
	@RequestMapping(value="/bbs/update", method=RequestMethod.GET)
	public String update(int bbsno, Model model) {
		
		BbsDTO dto = dao.read(bbsno);
		
		model.addAttribute("dto", dto);
		
		return "/bbs/update";
	}
	
	@RequestMapping("/bbs/read")
	public String read(int bbsno,Model model) {
		
		dao.upViewcnt(bbsno);
		BbsDTO dto = dao.read(bbsno);
		
		String content = dto.getContent();
		content = content.replaceAll("\r\n", "<br>");
		
		dto.setContent(content);
		
		model.addAttribute("dto", dto);
		
		return "/bbs/read";
	}
	
	@RequestMapping(value="/bbs/create", method=RequestMethod.POST)
	public String create(BbsDTO dto, HttpServletRequest request) {
		
		String upDir = request.getRealPath("/bbs/storage");
		
		int filesize = (int) dto.getFilenameMF().getSize();
		String filename = "";
		
		if(filesize>0)
			filename = Utility.saveFileSpring(dto.getFilenameMF(),upDir);
		
		dto.setFilesize(filesize);
		dto.setFilename(filename);
	
		boolean flag = dao.create(dto);
		
		if(flag) {
			return "redirect:/bbs/list";
		}else {
			if(!filename.equals(""))
		         Utility.deleteFile(upDir, filename);
			
			return  "/error/error";
		}
		
	}
	@RequestMapping(value="/bbs/create", method=RequestMethod.GET)
	public String create() {
		
		return "/bbs/create";
	}
	
	
	@RequestMapping("/bbs/list")
	public String list(HttpServletRequest request, Model model) {
		//검색관련 처리
		   String col = Utility.checkNull(request.getParameter("col"));
		   String word = Utility.checkNull(request.getParameter("word"));

		   if(col.equals("total")) word= "";
		   
		   //paging관련
		   int nowPage = 1;
		   int recordPerPage = 5;
		   
		   if(request.getParameter("nowPage")!=null){
		   	   nowPage = Integer.parseInt(request.getParameter("nowPage"));
		   }
		   
		   //DB에서 가져올 레코드의 순번
		   int sno = ((nowPage-1)*recordPerPage) + 1;
		   int eno = nowPage * recordPerPage;
		   
		   Map map = new HashMap();
		   map.put("col", col);
		   map.put("word", word);
		   map.put("sno", sno);
		   map.put("eno", eno);
		   		   
		   List<BbsDTO> list = dao.list(map);
		   //전체레코드 갯수(col,word)
		   int totalRecord = dao.total(map);
		   
		   String paging= Utility.paging3(totalRecord, nowPage, recordPerPage, col, word);
		   
		   
		   model.addAttribute("list", list);
		   model.addAttribute("paging", paging);
		   model.addAttribute("nowPage", nowPage);
		   model.addAttribute("col", col);
		   model.addAttribute("word", word);
		
		   return "/bbs/list";
	}

}
