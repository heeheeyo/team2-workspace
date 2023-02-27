package com.kh.myPage.model.dao;

import static com.kh.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.member.model.dao.MemberDao;
import com.kh.myPage.model.vo.Follow;

public class MyPageDao {

	private Properties prop = new Properties();

	public MyPageDao() {
		String filePath = MemberDao.class.getResource("/db/sql/myPage-mapper.xml").getPath();

		try {
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Follow> selectFollowerList(Connection conn, int memNo) {
		ArrayList<Follow> list = new ArrayList<Follow>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = prop.getProperty("selectFollowerList");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				list.add(new Follow(rset.getInt("mem_no"), rset.getString("login_id"), rset.getString("followers")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;

	}

	public ArrayList<Follow> selectFollowingList(Connection conn, int memNo) {
		ArrayList<Follow> list = new ArrayList<Follow>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String sql = prop.getProperty("selectFollowingList");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				list.add(new Follow(rset.getInt("mem_no"), rset.getString("login_id"), rset.getString("following")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;

		
	}

	public int deleteFollower(Connection conn, int memNo, String followerId) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteFollower");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setString(2, followerId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}