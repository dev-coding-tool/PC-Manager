package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MemberVO;

public class MemberDAO {
	// ȸ�� �߰�
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

	// �ߺ�Ȯ�� ���̵� ã��
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
			while (rs.next()) {// �������� ���������� ���� while, ������ ������ ����
				resultString = rs.getString(1);
				// rs.getString(1)�� ����������� 1���� �����Ͱ�
			}
			if (resultString == null) {
				return resultString;
			}
		} catch (SQLException e) {
			CommonDialog.alertDisplay(1, "db�������", "�������", "�ٽýõ�");
		} finally {
			JdbcUtil.close(rs, pstmt, con);
		}

		return resultString;
	}

	// ���̵� ã��
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
			while (rs.next()) {// �������� ���������� ���� while ������ ����
				resultString = rs.getString(1);
				// rs.getString(1)�� ����������� 1���� �����Ͱ�
			}
			if (resultString == null) {
				return resultString;
			}
		} catch (SQLException e) {
			CommonDialog.alertDisplay(1, "�˻�����", "ID�� ã�� ���߽��ϴ�", "�ٽ� �õ����ּ���");
		} finally {
			JdbcUtil.close(rs, pstmt, con);
		}

		return resultString;
	}

	// �н����� ã��
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
			while (rs.next()) {// �������� ���������� ���� while ������ ����
				resultString = rs.getString(1);
				// rs.getString(1)�� ����������� 1���� �����Ͱ�
			}
			if (resultString == null) {
				return resultString;
			}
		} catch (SQLException e) {
			CommonDialog.alertDisplay(1, "�˻�����", "PW�� ã�� ���߽��ϴ�", "�ٽ� �õ����ּ���");
		} finally {
			JdbcUtil.close(rs, pstmt, con);
		}

		return resultString;
	}

	//�α����Ҷ� DB�� �����ϴ� ȸ������ Ȯ��
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
				dbpw = rs.getString("pw"); //"pw"�÷��� ���� dbpw�� ����
				if (dbpw.contentEquals(PW)) { 
					resultCount = 1; // ��������
				} else {
					resultCount = -1;// ��й�ȣ ����
				}
			} else
				resultCount = 0;// ���̵� ����
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		return resultCount;

	}

	// ȸ�� ��ü����Ʈ
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

	// ȸ�� ����
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
				CommonDialog.alertDisplay(3, "ȸ������", "���� �Ϸ�", "ȸ���� �����Ǿ����ϴ�.");
			} else {
				CommonDialog.alertDisplay(1, "ȸ������", "���� ����", "���� ����");
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, con);
		}
	}

	// ȸ�� �˻�
	public ArrayList<MemberVO> getMemberSearch(String name) throws Exception {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		String sql = "select id, name, gender, phone, birth, status from member where name like ?"; // �̸��� '%__%'�� �˻�
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

	// ȸ�� ��������(�̿ϼ�)
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
