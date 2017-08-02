package com.zhiwei.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhiwei.dao.GradeDao;
import com.zhiwei.model.Grade;
import com.zhiwei.model.PageBean;
import com.zhiwei.util.DbUtil;
import com.zhiwei.util.JsonUtil;
import com.zhiwei.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class GradeListServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DbUtil dbUtil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		String gradeName=request.getParameter("gradeName");
		if(gradeName==null){
			gradeName="";
		}
		Grade grade=new Grade();
		grade.setGradeName(gradeName);

		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));

		Connection connection=null;
		try{
			connection=dbUtil.getCon( );
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(gradeDao.gradeList(connection,pageBean,grade));
			int total=gradeDao.gradeCount(connection,grade);
			
			result.put("rows", jsonArray);
			result.put("total", total);
			
			ResponseUtil.write(response, result);
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		
	}

	
	
}
