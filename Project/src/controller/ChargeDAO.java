package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.ChargeVO;

public class ChargeDAO {
	//ȸ�����Խ� charge���̺� �����Ǵ� ������(enterno, id)
	public static int getChargeAdd(ChargeVO cvo) throws Exception {
							//enterno, startime, prepaidmoney, id, startdate, seatno, availabletime
		String sql = "insert into charge values (charge_seq.nextval, to_char(sysdate,'hh:mm:ss'), 1000, ?, to_char(sysdate, 'yyyy-mm-dd'), '', 3600)";
				
		Connection con = null;
		PreparedStatement pstmt = null;
		int count1 = 0;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cvo.getId());
			
			count1 = pstmt.executeUpdate();
			if(count1 ==1) {
				System.out.println("ChargeDAO.getChargeAdd() ����");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, con);
		}
		return count1;
	}
	
	//�α��ν� charge���̺� �����Ǵ� ������(starttime, seatno, startdate)
	public void updateCharge(String SeatNo, String ID) throws Exception {
		
		String sql = "update charge set starttime = to_char(sysdate, 'hh:mi:ss'), seatno = to_char(?), startdate = to_char(sysdate, 'yyyy-mm-dd') where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int i=0;
		System.out.println("i �ʱⰪ = " + i);
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			System.out.println("ID = " + ID);
			System.out.println("SeatNo = " + SeatNo);
			
			pstmt.setString(1, SeatNo);
			pstmt.setString(2, ID);

			i = pstmt.executeUpdate();
			System.out.println("i = " + i);
			if (i == 1) {
				System.out.println("������ ���ư�");
			} else {
				System.out.println("������ �ȵ��ư�");
				//CommonDialog.alertDisplay(1, "����", "����", "�����̿��� �Ұ��մϴ�.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, con);
		}
	}
	
	//���� ������ �ð� �ҷ�����
	public int remainTime(String ID) {
		String sql = "select availabletime from charge where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int remaintime=0;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				remaintime = rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		return remaintime;
			
	}
	
/*
	//ȸ������ �� �������� 
	public void getFirstCharge(ChargeVO cvo)throws Exception{
		String sql = "insert into charge values (charge_seq.nextval, to_char(sysdate, 'hh:mi:ss'), ?, ?,  to_char(sysdate, 'yyyy:mm:dd'), '', ?)"; 
		Connection con = null; 
		PreparedStatement pstmt = null;
		System.out.println(cvo.getId() + "�������̵�");
		System.out.println(cvo.getPrepaidmoney() + "���ҿ��");
		System.out.println(cvo.getAvailabletime() + "��밡�ɽð�"); 
		try { 
			con =JdbcUtil.getConnection(); 
			pstmt = con.prepareStatement(sql); 
			pstmt.setInt(1, cvo.getPrepaidmoney()); 
			pstmt.setString(2, cvo.getId()); 
			pstmt.setInt(3,cvo.getAvailabletime());
	  
			int i = pstmt.executeUpdate(); 
			if(i==1) { 
				CommonDialog.alertDisplay(3, "��� ����", cvo.getId() + "�� " + cvo.getPrepaidmoney() +"�� ��� ���� �Ϸ�","�ð��� �����Ǿ����ϴ�."); 
			}else { 
				CommonDialog.alertDisplay(1, "��� ����", "����", "������� ���� ����"); 
			} 
		}catch(SQLException e) {
			System.out.println("FirstCharge ����"); 
		}catch(Exception e) {
			System.out.println("FirstCharge ����"); 
		}finally {
			JdbcUtil.close(pstmt, con);
		} 
	}
*/
	//�α��� ���̵� �޾Ƽ� ���ҿ�� ������ (int�� ����)
	public int selectNameTime(String ID) {
		Vector<Object> vector = new Vector<Object>();
		String sql = "select c.prepaidmoney from charge c where c.id = ? "; 
		Connection con = null;
		PreparedStatement pstmt = null;
		int prepaidmoney = 0;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ID);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				prepaidmoney = rs.getInt(1);
			}
			//System.out.println("sql�� ����");
			//System.out.println(prepaidmoney);
		}catch(SQLException sqle) {
			System.out.print("selectNameTime = " + sqle);
		}catch(Exception e) {
			System.out.print("selectNameTime = " + e);
		}finally {
			JdbcUtil.close(pstmt, con);
		}
		return prepaidmoney;
	}
	
	//��밡�ɽð� ��������
	public int selectTime(String ID) {
		String sql = "select availabletime from charge where id = ? "; 
		Connection con = null;
		PreparedStatement pstmt = null;
		int availabletime = 0;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ID);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				availabletime = rs.getInt(1);
			}
		}catch(SQLException sqle) {
			System.out.print("selectNameTime = " + sqle);
		}catch(Exception e) {
			System.out.print("selectNameTime = " + e);
		}finally {
			JdbcUtil.close(pstmt, con);
		}
		return availabletime;
	}
	
	//�α����� ������ �¼���ȣ ��������
	public String getLoginUserSeatNo(String ID) {
		String sql = "select seatno from charge where id = ? "; //and startdate=to_char(sysdate, 'yyyy-mm-dd')";
		Connection con = null;
		PreparedStatement pstmt = null;
		String seatno = null;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ID);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				seatno = rs.getString(1);
			}
		}catch(SQLException sqle) {
			System.out.print("getLoginUserSeatNo = " + sqle);
		}catch(Exception e) {
			System.out.print("getLoginUserSeatNo = " + e);
		}finally {
			JdbcUtil.close(pstmt, con);
		}
		return seatno;
	}
	
	//ȸ�� �����Ҷ� Charge���̺� ���������
	public void getChargeDelete(String ID) throws Exception {
		String sql = "delete from charge where id = ? ";
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
	
	//ȸ�� �������
	public void memberCharge(int PrepaidMoney, int AvailableTime, String ID) throws Exception {
		String sql = "update charge set prepaidmoney = prepaidmoney + ?, availabletime = availabletime + ? where id = ?";
		//String sql = "update charge set prepaidmoney = ?, availabletime=? where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int i=0;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, PrepaidMoney);
			pstmt.setInt(2, AvailableTime);
			pstmt.setString(3, ID);

			i = pstmt.executeUpdate();
			
			if (i == 1) {
				System.out.println("ȸ�� ������� ������ ���ư�");
			} else {
				System.out.println("ȸ�� ������� ������ �ȵ��ư�");
				//CommonDialog.alertDisplay(1, "����", "����", "�����̿��� �Ұ��մϴ�.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, con);
		}
	}
	
	//�������� �����ð� ����
	public void saveTime(int AvailableTime, String ID) throws Exception {
		
		String sql = "update charge set availabletime = ? where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int i=0;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, AvailableTime);
			pstmt.setString(2, ID);

			i = pstmt.executeUpdate();
			
			if (i == 1) {
				System.out.println("ȸ�� ������� ������ ���ư�");
			} else {
				System.out.println("ȸ�� ������� ������ �ȵ��ư�");
				//CommonDialog.alertDisplay(1, "����", "����", "�����̿��� �Ұ��մϴ�.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, con);
		}
	}
	
}