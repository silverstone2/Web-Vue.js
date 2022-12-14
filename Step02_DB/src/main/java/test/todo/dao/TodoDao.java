package test.todo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import test.member.dto.MemberDto;
import test.todo.dto.TodoDto;
import test.util.DbcpBean;

public class TodoDao {
	
	private static TodoDao dao;
	
	private TodoDao() {}
	
	public static TodoDao getInstance() {
		if(dao == null) {
			dao = new TodoDao();
		}
		return dao;
	}
	
	public boolean insert(TodoDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int updateRowCount = 0;
		try {
			conn = new DbcpBean().getConn();

			String sql = "INSERT INTO todo" 
						+ " (num, content, regdate)" 
						+ " VALUES(todo_seq.NEXTVAL, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getContent());
			updateRowCount = pstmt.executeUpdate();
			System.out.println("회원 정보 추가 성공");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		if (updateRowCount > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean delete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int updateRowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = "DELETE FROM todo" 
						+ " WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			updateRowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		if (updateRowCount > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean update(TodoDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int updateRowCount = 0;
		try {
			conn = new DbcpBean().getConn();
			String sql = "UPDATE todo"
					+ " SET content=?"
					+ " WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getContent());
			pstmt.setInt(2, dto.getNum());
			updateRowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close(); 
			} catch (Exception e) {
			}
		}
		if (updateRowCount > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public TodoDto getData(int num) {
		TodoDto dto=null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			String sql = "SELECT content, TO_CHAR(regdate,'YYYY.MM.DD HH24:MI') regdate"
						+ " FROM todo"
		               + " WHERE num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto=new TodoDto();
				dto.setNum(num);
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
			}
			} catch (Exception e) {
		         e.printStackTrace();
			} finally {
				try {
		            if (rs != null)
		               rs.close();
		            if (pstmt != null)
		               pstmt.close();
		            if (conn != null)
		               conn.close();
		         } catch (Exception e) {
		         }
		      }
		      return dto;
	}
	
	public List<TodoDto> getList() {
		List<TodoDto> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DbcpBean().getConn();
			
			String sql = "SELECT num, content, regdate"
					+ " FROM todo"
					+ " ORDER BY num ASC";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				TodoDto dto = new TodoDto();
				dto.setNum(rs.getInt("num"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (Exception e) {}
		}
		
		return list;
	}
}
