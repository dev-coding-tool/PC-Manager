package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AdminVO;

public class AdminDAO {
	//관리자  추가
	public static int getAdminAdd(AdminVO avo) throws Exception{
		
		String sql = "insert into admin values (admin_seq.nextval, ?, ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, avo.getId());
			pstmt.setString(2, avo.getName());
			pstmt.setString(3, avo.getGender());
			pstmt.setString(4, avo.getPhone());
			pstmt.setString(5, avo.getBirth());
			pstmt.setString(6, avo.getPw1());
			
			count = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
				JdbcUtil.close(pstmt, con);
		}
		return count;
	}
	//중복확인 아이디 찾기
	public static String getAdminIDCheck(String ID) {
		String sql = "select id from admin where id=?";
		String resultString = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();
			while(rs.next()) {//다음행이 있을떄까지 도는 while 없으면 멈춤
				resultString =rs.getString(1);
				//rs.getString(1)은 쿼리문결과의 1열의 데이터값
			}
			if(resultString == null) {
				return resultString;
			}
		}catch(SQLException e) {
			CommonDialog.alertDisplay(1, "db연결오류", "연결실패", "다시시도");
		}finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		
		return resultString;
	}
	
	//아이디 찾기
	public static String getFoundID(String Name, String Phone) {
		String sql = "select id from admin where name = ? and phone = ?";
		String resultString =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Name);
			pstmt.setString(2, Phone);
			rs = pstmt.executeQuery();
			while(rs.next()) {//다음행이 있을떄까지 도는 while 없으면 멈춤
				resultString =rs.getString(1);
				//rs.getString(1)은 쿼리문결과의 1열의 데이터값
			}
			if(resultString == null) {
				return resultString;
			}
		}catch(SQLException e) {
			CommonDialog.alertDisplay(1, "검색실패", "ID를 찾지 못했습니다", "다시 시도해주세요");
		}finally {
			JdbcUtil.close(rs, pstmt, con);
		}	
		return resultString;
	}
	
	//패스워드찾기
	public static String getFoundPW(String ID, String Name, String Phone) {
		String sql = "select id from admin where id = ? and name = ? and phone = ?";
		String resultString =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ID);
			pstmt.setString(2, Name);
			pstmt.setString(3, Phone);
			rs = pstmt.executeQuery();
			while(rs.next()) {//다음행이 있을떄까지 도는 while 없으면 멈춤
				resultString =rs.getString(1);
				//rs.getString(1)은 쿼리문결과의 1열의 데이터값
			}
			if(resultString == null) {
				return resultString;
			}
		}catch(SQLException e) {
			CommonDialog.alertDisplay(1, "검색실패", "PW를 찾지 못했습니다", "다시 시도해주세요");
		}finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		
		return resultString;
	}
	
	//--------------------로그인 관련-------------
	//DB에 존재하는 회원인지 확인
	public static int adminCheck(String ID, String PW) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		String dbpw = "";
		int resultCount = -1;
		try {
			con = JdbcUtil.getConnection();
			sql = "select pw from admin where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbpw = rs.getString("pw");
				if(dbpw.contentEquals(PW)) {
					resultCount=1; //인증성공
				}
				else {
					resultCount=-1;// 비밀번호 오류
				}
			}else
				resultCount = 0;// 아이디 오류
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		return resultCount;	
	}
	
}
