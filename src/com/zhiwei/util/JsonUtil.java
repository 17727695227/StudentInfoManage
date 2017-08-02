package com.zhiwei.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Date;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	public static JSONArray formatRsToJsonArray(ResultSet rs)throws Exception{
		
		ResultSetMetaData mData=rs.getMetaData();
		int number=mData.getColumnCount();
		JSONArray array=new JSONArray();
		while(rs.next())
		{
			JSONObject mapofColValues=new JSONObject();
			for(int i=1;i<=number;i++)
			{
				
			 Object o=rs.getObject(i);
			 if(o instanceof Date)
			 {
				 mapofColValues.put(mData.getColumnName(i), DateUtil.formatDate((Date)o, "yyyy-MM-dd"));
			 }else{
				  mapofColValues.put(mData.getColumnName(i),rs.getObject(i) );
			 }
			}
			array.add(mapofColValues);
		}
		return array;
	}
}
