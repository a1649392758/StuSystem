package cn.bdqn.ssm.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 时间相关工具类
 * @author 李洋
 * @address 北大青鸟沈阳三好中心
 * @created 2017年7月2日 下午1:43:51
 * @version 1.0.0
 */
public class DateUtils {

	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtils.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static String getCurrentDateStr()throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}
}
