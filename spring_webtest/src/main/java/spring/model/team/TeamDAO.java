package spring.model.team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.db.webtest.DBClose;
import spring.db.webtest.DBOpen;

@Repository
public class TeamDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void setMybatis(SqlSessionTemplate mybatis) {
		this.mybatis = mybatis;
	}

	public boolean checkRefnum(int no) {
		boolean flag = false;
		int cnt = mybatis.selectOne("team.refnumCheck", no);
		if(cnt>0)flag = true;
		return flag;
	}
	
	public boolean replyCreate(TeamDTO dto) {
		boolean flag = false;
		
		int cnt = mybatis.insert("team.replyCreate", dto);
		if(cnt>0)flag = true;
		
		return flag;
	}
	
	public void upAnsnum(Map map) {
		
		mybatis.update("team.upAnsnum",map);
		
	}
	public TeamDTO replyRead(int no) {
		
		return mybatis.selectOne("team.replyRead", no);
	}
	
	public boolean delete(int no) {
		boolean flag = false;
		int cnt = mybatis.delete("team.delete", no);
		if(cnt>0)flag = true;
		return flag;
	}
	
	public boolean update(TeamDTO dto) {
		boolean flag = false;
		int cnt = mybatis.update("team.update",dto);
		if(cnt>0)flag = true;
		return flag;
	}
	
	public TeamDTO read(int no) {
		
		return mybatis.selectOne("team.read", no);
	}
	
	public boolean create(TeamDTO dto) {
		boolean flag = false;
		int cnt = mybatis.insert("team.create",dto);
		if(cnt>0) flag = true;		
		return flag;
	}
	
	public int total(Map map) {
		int total = 0;
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) from team  ");
	    if(word.trim().length()>0)
	    	sql.append(" where "+col+" like '%'||?||'%' ");
	    
	    
	    try {
			pstmt = con.prepareStatement(sql.toString());
			if(word.trim().length()>0)
				pstmt.setString(1, word);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			total = rs.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}
	      
		return total;
	}
	
	
	public List<TeamDTO> list(Map map){
		List<TeamDTO> list = new ArrayList<TeamDTO>();
		Connection con = DBOpen.open();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String col = (String)map.get("col");
		String word = (String)map.get("word");
		int sno = (Integer)map.get("sno");
		int eno = (Integer)map.get("eno");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select no, name, gender, skills, phone,  ");
		sql.append(" 	grpno, indent, ansnum,  r ");
		sql.append(" from( ");
		sql.append(" 	select no, name, gender, skills, phone,  ");
		sql.append(" 	grpno, indent, ansnum, rownum as r ");
		sql.append(" 	from( ");
		sql.append(" 		select no, name, gender, skills, phone,  ");
		sql.append(" 		grpno, indent, ansnum                 ");
		sql.append(" 		from TEAM  ");
		if(word.trim().length()>0)
			sql.append(" 	where "+col+" like '%'||?||'%' ");
		sql.append(" 		order by grpno desc, ansnum          ");
		sql.append(" 	) ");
		sql.append(" )where r >= ? and r <= ? ");

		int i=0;
		try {
			pstmt = con.prepareStatement(sql.toString());
			if(word.trim().length()>0)
				pstmt.setString(++i, word);
			
			pstmt.setInt(++i, sno);
			pstmt.setInt(++i, eno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TeamDTO dto = new TeamDTO();
				dto.setNo(rs.getInt("no"));
				dto.setName(rs.getString("name"));
				dto.setGender(rs.getString("gender"));
				dto.setSkills(rs.getString("skills"));
				dto.setPhone(rs.getString("phone"));
				dto.setGrpno(rs.getInt("grpno"));
				dto.setIndent(rs.getInt("indent"));
				dto.setAnsnum(rs.getInt("ansnum"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBClose.close(rs, pstmt, con);
		}

		return list;
	}
	
	

}
