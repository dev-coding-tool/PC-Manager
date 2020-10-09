package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ItemVO;

public class ItemDAO {
	//아이템 이름과 가격 가져옴
	public ArrayList<ItemVO> getItemNamePrice(){
		ArrayList<ItemVO> list = new ArrayList<ItemVO>();
		String sql =  "select itemname, price from item";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemVO ivo = null;
		try {
			con = JdbcUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ivo = new ItemVO(rs.getString(1), rs.getInt(2));
				list.add(ivo);
			}
			
			//System.out.println(list);
			
			
		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs, pstmt, con);
		}
		return list;
	}

}
