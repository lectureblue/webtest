package spring.model.member;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;



public class MemberTest {

	public static void main(String[] args) {
		
		Resource resource = new ClassPathResource("daoTest.xml");
		
		BeanFactory factory = new XmlBeanFactory(resource);
		
		MemberDAO dao = (MemberDAO)factory.getBean("member");
		
		//list(dao);
		//read(dao);
		//total(dao);
		//create(dao);
		//update(dao);
		//delete(dao);
		//updateFile(dao);
		//getFname(dao);
		//getGrade(dao);
		//getIdFind(dao);
		//getPwFind(dao);
		//duplicateId(dao);
		//duplicateEmail(dao);
		loginCheck(dao);
		
	}

	private static void loginCheck(MemberDAO dao) {
		Map map = new HashMap();
		map.put("passwd", "12345");
		map.put("id", "user4");
		
		if(dao.loginCheck(map)) {
			p("회원입니다.");
		}else {
			p("회원이 아니거나 아이디및 패스워드 오류입니다.");
		}
		
	}

	private static void duplicateEmail(MemberDAO dao) {
		if(dao.duplicateEmail("user41@mail.com")) {
			p("이메일 중복");
		}else {
			p("이메일 중복아님");
		}
		
	}

	private static void duplicateId(MemberDAO dao) {
		if(dao.duplicateId("user41")) {
			p("아이디 중복");
		}else {
			p("아이디 중복아님");
		}
		
	}

	private static void getPwFind(MemberDAO dao) {
		Map map = new HashMap();
		map.put("mname", "김길동");
		map.put("id", "user4");
		p("pw:"+dao.getPwFind(map));
		
	}

	private static void getIdFind(MemberDAO dao) {
		Map map = new HashMap();
		map.put("mname", "김길동");
		map.put("email", "user4@mail.com");
		p("id:"+dao.getIdFind(map));
	}

	private static void getGrade(MemberDAO dao) {
		p(dao.getGrade("user4"));
		
	}

	
	
	private static void getFname(MemberDAO dao) {
		p(dao.getFname("user4"));
		
	}

	private static void updateFile(MemberDAO dao) {
		Map map = new HashMap();
		map.put("fname", "koala.jpg");
		map.put("id", "user4");
		if(dao.updateFile(map)) {
			p("성공");
		}else {
			p("실패");
		}
		
	}



	private static void delete(MemberDAO dao) {
		if(dao.delete("user1")){
			p("성공");
		}else {
			p("실패");
		}
		
	}
	

	private static void update(MemberDAO dao) {
		MemberDTO dto = new MemberDTO();
		dto.setId("user1");
		dto.setTel("010-0000-0000");
		dto.setEmail("user1@mail.com");
		dto.setJob("A02");
		dto.setPasswd("1234");
		dto.setFname("member.jpg");
		//dto.setZipcode("12345");
		//dto.setAddress("서울시 종로구 관철동");
		//dto.setAddress2("젊음의 거리");
		if(dao.update(dto)) {
			p("성공");
		}else {
			p("실패");
		}
		
	}

	private static void create(MemberDAO dao) {

		MemberDTO dto = new MemberDTO();
		dto.setMname("김영수");
		dto.setTel("010-2222-3333");
		dto.setId("user1");
		dto.setEmail("user1@mail.com");
		dto.setJob("A01");
		dto.setPasswd("1234");
		dto.setFname("member.jpg");
		//dto.setZipcode("12345");
		//dto.setAddress("서울시 종로구 관철동");
		//dto.setAddress2("젊음의 거리");
		if(dao.create(dto )) {
			p("성공");
		}else {
			p("실패");
		}
		
	}

	private static void total(MemberDAO dao) {
		Map map = new HashMap();
		map.put("col", "mname");
		map.put("word", "");
		
		int total = dao.total(map);
		
		p("전체레코드수:"+total);
		
	}

	private static void read(MemberDAO dao) {
		MemberDTO dto = dao.read("user1");
		p(dto);
	}

	private static void list(MemberDAO dao) {
		Map map = new HashMap();
		map.put("col", "mname");
		map.put("word", "김영수");
		map.put("sno", 1);
		map.put("eno", 10);
		List<MemberDTO> list = dao.list(map );
		
		Iterator<MemberDTO> iter = list.iterator();
		
		while(iter.hasNext()) {
			MemberDTO dto = iter.next();
			p(dto);
			p("------------------");
		}
		
	}

	private static void p(String string) {
		System.out.println(string);
		
	}

	private static void p(MemberDTO dto) {
		p("아이디:"+dto.getId());
		p("이름:"+dto.getMname());
		p("이메일:"+dto.getEmail());
		p("직업코드:"+dto.getJob());
		p("전화번호:"+dto.getTel());
		p("등록날짜:"+dto.getMdate());
		p("우편번호:"+dto.getZipcode());
		p("주소:"+dto.getAddress1());
		p("상세주소:"+dto.getAddress2());
		p("사진명:"+dto.getFname());
		
	}

}
