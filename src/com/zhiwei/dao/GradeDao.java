package com.zhiwei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.zhiwei.model.Grade;
import com.zhiwei.model.PageBean;
import com.zhiwei.util.StringUtil;

public class GradeDao {

	public ResultSet gradeList(Connection connection,PageBean page,Grade grade)
		throws Exception{
		StringBuffer stringBuffer=new StringBuffer("select * from t_grade");
		if(grade!=null && StringUtil.isNotEmpty(grade.getGradeName())){
			stringBuffer.append(" and gradeName like '%"+grade.getGradeName()+"%'");
		}		
		if(page!=null){
		    stringBuffer.append(" limit "+page.getStart()+","+page.getRows());		    
		}
		PreparedStatement pstmt=connection.prepareStatement(stringBuffer.toString()
				.replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}
	
	
	public int gradeCount(Connection connection,Grade grade)throws Exception{
		StringBuffer stringBuffer=new StringBuffer("select count(*) as total from t_grade");
		if(StringUtil.isNotEmpty(grade.getGradeName())){
			stringBuffer.append(" and gradeName like '%"+grade.getGradeName()+"%'");			
		}
				
		PreparedStatement pstmt=connection.prepareStatement(stringBuffer.toString().replaceFirst("and", "where"));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}		
	}
	
	/**
	 * delete from tableName where field in (1,3,4)
	 * @param connection
	 * @param delIds
	 * @return
	 * @throws Exception
	 */
	public int gradeDelete(Connection connection,String delIds)
	throws Exception{
		
		String sql="delete from t_grade where id in("+delIds+")";
		PreparedStatement pstmt=connection.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	public int gradeAdd(Connection con,Grade grade)throws Exception{
		String sql="insert into t_grade values(null,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		return pstmt.executeUpdate();
	}
	
	public int gradeModify(Connection con,Grade grade)throws Exception{
		String sql="update t_grade set gradeName=?,gradeDesc=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		pstmt.setInt(3, grade.getId());
		return pstmt.executeUpdate();
	}
	
	
}
