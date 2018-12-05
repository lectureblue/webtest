package spring.model.bbs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class BbsTest {

	public static void main(String[] args) {
		
		Resource resource = new ClassPathResource("daoTest.xml");
		
		BeanFactory factory = new XmlBeanFactory(resource);
		
		BbsDAO dao = (BbsDAO)factory.getBean("dao");
		
		//list(dao);
		//read(dao);
		//total(dao);
		//create(dao);
		//passwdCheck(dao);
		//update(dao);
		//readReply(dao);
		//reply(dao);
		//checkRefnum(dao);
		delete(dao);
	}

	private static void passwdCheck(BbsDAO dao) {
		Map map = new HashMap();
		map.put("bbsno", 3);
		map.put("passwd", 1234111);
		if(dao.passCheck(map)) {
			p("올바른 패스워드 입니다.");
		}else {
			p("잘못된 패스워드 입니다.");
		}
		
	}

	private static void delete(BbsDAO dao) {
		if(dao.delete(5)){
			p("성공");
		}else {
			p("실패");
		}
		
	}

	private static void checkRefnum(BbsDAO dao) {
		if(dao.checkRefnum(5)) {
			p("답변있는 글이므로 삭제할 수 없습니다.");
		}else {
			p("삭제 가능합니다.");
		}
		
	}

	private static void reply(BbsDAO dao) {
		BbsDTO dto = dao.replyRead(3);
		dto.setWname("이길동");
		dto.setContent("게시판 내용 답변");
		dto.setPasswd("1234");
		dto.setFilesize(100);
		dto.setFilename("test.txt");
		
		Map map = new HashMap();
		map.put("grpno", dto.getGrpno());
		map.put("ansnum", dto.getAnsnum());
		dao.upAnsnum(map);
		if(dao.replyCreate(dto)) {
			p("성공");
		}else {
			p("실패");
		}
		
	}

	private static void update(BbsDAO dao) {
		BbsDTO dto = dao.read(3);
		dto.setWname("홍길순");
		dto.setTitle("제목변경");
		dto.setContent("내용변경");
		dto.setFilename("test2.txt");
		dto.setFilesize(50);
		if(dao.update(dto)) {
			p("성공");
		}else {
			p("실패");
		}
		
	}

	private static void create(BbsDAO dao) {

		BbsDTO dto = new BbsDTO();
		dto.setWname("박길동");
		dto.setTitle("게시판 제목2");
		dto.setContent("게시판 내용2");
		dto.setPasswd("1234");
		//dto.setFilesize(100);
		//dto.setFilename("test.txt");
		if(dao.create(dto )) {
			p("성공");
		}else {
			p("실패");
		}
		
	}

	private static void total(BbsDAO dao) {
		Map map = new HashMap();
		map.put("col", "wname");
		map.put("word", "test");
		
		int total = dao.total(map);
		
		p("전체레코드수:"+total);
		
	}

	private static void read(BbsDAO dao) {
		BbsDTO dto = dao.read(6);
		p(dto);
	}

	private static void list(BbsDAO dao) {
		Map map = new HashMap();
		map.put("col", "name");
		map.put("word", "");
		map.put("sno", 1);
		map.put("eno", 10);
		List<BbsDTO> list = dao.list(map );
		
		Iterator<BbsDTO> iter = list.iterator();
		
		while(iter.hasNext()) {
			BbsDTO dto = iter.next();
			p(dto);
			p("------------------");
		}
		
	}

	private static void p(String string) {
		System.out.println(string);
		
	}

	private static void p(BbsDTO dto) {
		p("번호:"+dto.getBbsno());
		p("이름:"+dto.getWname());
		p("제목:"+dto.getTitle());
		p("내용:"+dto.getContent());
		p("날짜:"+dto.getWdate());
		p("조회수:"+dto.getViewcnt());
		p("파일이름:"+dto.getFilename());
		p("파일크기:"+dto.getFilesize());
		
	}

}
