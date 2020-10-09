package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MemberVO;

public class MemberDAO {
	// 회원 추가
	public static int getMemberAdd(MemberVO avo) throws Exception {

		String sql = "insert into member values (?, ?, ?, ?, ?, ?, ?)";
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
			pstmt.setString(7, avo.getStatus());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, con);
		}
		return count;
	}

	// 중복확인 아이디 찾기
	public static String getMemberIDCheck(String ID) {
		String sql = "select id from member where id=?";
		String resultString = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();
			while (rs.next()) {// 다음행이 있을떄까지 도는 while, 다음행 없으면 멈춤
				resultString = rs.getString(1);
				// rs.getString(1)은 쿼리문결과의 1열의 데이터값
			}
			if (resultString == null) {
				return resultString;
			}
		} catch (SQLException e) {
			CommonDialog.alertDisplay(1, "db연결오류", "연결실패", "다시시도");
		} finally {
			JdbcUtil.close(rs, pstmt, con);
		}

		return resultString;
	}

	// 아이디 찾기
	public static String getFoundID(String Name, String Phone) {
		String sql = "select id from member where name = ? and phone = ?";
		String resultString = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Name);
			pstmt.setString(2, Phone);
			rs = pstmt.executeQuery();
			while (rs.next()) {// 다음행이 있을떄까지 도는 while 없으면 멈춤
				resultString = rs.getString(1);
				// rs.getString(1)은 쿼리문결과의 1열의 데이터값
			}
			if (resultString == null) {
				return resultString;
			}
		} catch (SQLException e) {
			CommonDialog.alertDisplay(1, "검색실패", "ID를 찾지 못했습니다", "다시 시도해주세요");
		} finally {
			JdbcUtil.close(rs, pstmt, con);
		}

		return resultString;
	}

	// 패스워드 찾기
	public static String getFoundPW(String ID, String Name, String Phone) {
		String sql = "select id from member where id = ? and name = ? and phone = ?";
		String resultString = null;
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
			while (rs.next()) {// 다음행이 있을떄까지 도는 while 없으면 멈춤
				resultString = rs.getString(1);
				// rs.getString(1)은 쿼리문결과의 1열의 데이터값
			}
			if (resultString == null) {
				return resultString;
			}
		} catch (SQLException e) {
			CommonDialog.alertDisplay(1, "검색실패", "PW를 찾지 못했습니다", "다시 시도해주세요");
		} finally {
			JdbcUtil.close(rs, pstmt, con);
		}

		return resultString;
	}

	//로그인할때 DB에 존재하는 회원인지 확인
	public static int memberCheck(String ID, String PW) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		String dbpw = "";
		int resultCount = -1;
		try {
			con = JdbcUtil.getConnection();
			sql = "select pw from member where id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dbpw = rs.getString("pw"); //"pw"컬럼의 값을 dbpw에 넣음
				if (dbpw.contentEquals(PW)) { 
					resultCount = 1; // 인증성공
				} else {
					resultCount = -1;// 비밀번호 오류
				}
			} else
				resultCount = 0;// 아이디 오류
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		return resultCount;

	}

	// 회원 전체리스트
	public ArrayList<MemberVO> getMemberTotal() {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		String sql = "select id, name, gender, phone, birth, status from member";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO mvo = null;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mvo = new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				list.add(mvo);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		return list;
	}

	// 회원 삭제
	public void getMemberDelete(String ID) throws Exception {
		String sql = "delete from member where id = ? ";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ID);
			int i = pstmt.executeUpdate();
			if (i == 1) {
				CommonDialog.alertDisplay(3, "회원삭제", "삭제 완료", "회원이 삭제되었습니다.");
			} else {
				CommonDialog.alertDisplay(1, "회원삭제", "삭제 실패", "삭제 실패");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, con);
		}
	}

	// 회원 검색
	public ArrayList<MemberVO> getMemberSearch(String name) throws Exception {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		String sql = "select id, name, gender, phone, birth, status from member where name like ?"; // 이름은 '%__%'로 검색
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO mvo = null;
		String searchname = "%" + name + "%";
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchname);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mvo = new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6));
				list.add(mvo);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		return list;
	}

	// 회원 정보수정(미완성)
/*	public static MemberVO updateMember(MemberVO memberVO) throws Exception{
	 
	 ClientLoginController clc = new ClientLoginController(); String currentid =
	 clc.getTxid();
	  
	 String sql = "update member set pw = ?, phone = ? where id = ?"; 
	 Connection con = null; 
	 PreparedStatement pstmt = null; 
	 int i = 0; 
	 try { 
		 con =JdbcUtil.getConnection(); 
		 pstmt = con.prepareStatement(sql);
		 pstmt.setString(1, memberVO.getPw1()); 
		 pstmt.setString(2, memberVO.getPhone()); 
		 pstmt.setString(3, currentid); 
	}
	catch(SQLException sqle) { 
		sqle.printStackTrace(); 
	}catch(Exception e) { 
		e.printStackTrace();
	 }finally { 
		 JdbcUtil.close(pstmt, con); 
		 } 
	 return memberVO; 
	 }
*/
}
