package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.AdminVO;

public class AdminDAO {
	//������  �߰�
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
	//�ߺ�Ȯ�� ���̵� ã��
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
			while(rs.next()) {//�������� ���������� ���� while ������ ����
				resultString =rs.getString(1);
				//rs.getString(1)�� ����������� 1���� �����Ͱ�
			}
			if(resultString == null) {
				return resultString;
			}
		}catch(SQLException e) {
			CommonDialog.alertDisplay(1, "db�������", "�������", "�ٽýõ�");
		}finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		
		return resultString;
	}
	
	//���̵� ã��
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
			while(rs.next()) {//�������� ���������� ���� while ������ ����
				resultString =rs.getString(1);
				//rs.getString(1)�� ����������� 1���� �����Ͱ�
			}
			if(resultString == null) {
				return resultString;
			}
		}catch(SQLException e) {
			CommonDialog.alertDisplay(1, "�˻�����", "ID�� ã�� ���߽��ϴ�", "�ٽ� �õ����ּ���");
		}finally {
			JdbcUtil.close(rs, pstmt, con);
		}	
		return resultString;
	}
	
	//�н�����ã��
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
			while(rs.next()) {//�������� ���������� ���� while ������ ����
				resultString =rs.getString(1);
				//rs.getString(1)�� ����������� 1���� �����Ͱ�
			}
			if(resultString == null) {
				return resultString;
			}
		}catch(SQLException e) {
			CommonDialog.alertDisplay(1, "�˻�����", "PW�� ã�� ���߽��ϴ�", "�ٽ� �õ����ּ���");
		}finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		
		return resultString;
	}
	
	//--------------------�α��� ����-------------
	//DB�� �����ϴ� ȸ������ Ȯ��
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
					resultCount=1; //��������
				}
				else {
					resultCount=-1;// ��й�ȣ ����
				}
			}else
				resultCount = 0;// ���̵� ����
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		return resultCount;	
	}
	
}
