package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.ChargeVO;

public class ChargeDAO {
	//회원가입시 charge테이블에 생성되는 데이터(enterno, id)
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
				System.out.println("ChargeDAO.getChargeAdd() 정상");
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
	
	//로그인시 charge테이블에 수정되는 데이터(starttime, seatno, startdate)
	public void updateCharge(String SeatNo, String ID) throws Exception {
		
		String sql = "update charge set starttime = to_char(sysdate, 'hh:mi:ss'), seatno = to_char(?), startdate = to_char(sysdate, 'yyyy-mm-dd') where id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int i=0;
		System.out.println("i 초기값 = " + i);
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
				System.out.println("쿼리문 돌아감");
			} else {
				System.out.println("쿼리문 안돌아감");
				//CommonDialog.alertDisplay(1, "오류", "오류", "정상이용이 불가합니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, con);
		}
	}
	
	//기존 충전된 시간 불러오기
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
	//회원가입 후 최초충전 
	public void getFirstCharge(ChargeVO cvo)throws Exception{
		String sql = "insert into charge values (charge_seq.nextval, to_char(sysdate, 'hh:mi:ss'), ?, ?,  to_char(sysdate, 'yyyy:mm:dd'), '', ?)"; 
		Connection con = null; 
		PreparedStatement pstmt = null;
		System.out.println(cvo.getId() + "유저아이디");
		System.out.println(cvo.getPrepaidmoney() + "선불요금");
		System.out.println(cvo.getAvailabletime() + "사용가능시간"); 
		try { 
			con =JdbcUtil.getConnection(); 
			pstmt = con.prepareStatement(sql); 
			pstmt.setInt(1, cvo.getPrepaidmoney()); 
			pstmt.setString(2, cvo.getId()); 
			pstmt.setInt(3,cvo.getAvailabletime());
	  
			int i = pstmt.executeUpdate(); 
			if(i==1) { 
				CommonDialog.alertDisplay(3, "요금 충전", cvo.getId() + "님 " + cvo.getPrepaidmoney() +"원 요금 충전 완료","시간이 충전되었습니다."); 
			}else { 
				CommonDialog.alertDisplay(1, "요금 충전", "오류", "요금정보 수정 실패"); 
			} 
		}catch(SQLException e) {
			System.out.println("FirstCharge 에러"); 
		}catch(Exception e) {
			System.out.println("FirstCharge 에러"); 
		}finally {
			JdbcUtil.close(pstmt, con);
		} 
	}
*/
	//로그인 아이디 받아서 선불요금 가져옴 (int값 리턴)
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
			//System.out.println("sql문 정상");
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
	
	//사용가능시간 가져오기
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
	
	//로그인한 유저의 좌석번호 가져오기
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
	
	//회원 삭제할때 Charge테이블 먼저지우기
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
	
	//회원 요금충전
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
				System.out.println("회원 요금충전 쿼리문 돌아감");
			} else {
				System.out.println("회원 요금충전 쿼리문 안돌아감");
				//CommonDialog.alertDisplay(1, "오류", "오류", "정상이용이 불가합니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(pstmt, con);
		}
	}
	
	//사용종료시 남은시간 저장
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
				System.out.println("회원 요금충전 쿼리문 돌아감");
			} else {
				System.out.println("회원 요금충전 쿼리문 안돌아감");
				//CommonDialog.alertDisplay(1, "오류", "오류", "정상이용이 불가합니다.");
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