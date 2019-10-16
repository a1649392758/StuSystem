package cn.bdqn.ssm.common;


/**
 * @description 字符串工具类
 * @author 李洋
 * @address 北大青鸟沈阳三好中心
 * @created 2017年4月4日 上午7:23:36
 * @version 1.0.0
 */
public class StringUtils {


	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	

	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}

	public static String formatLike(String str){
		if(isNotEmpty(str)){
			return "%"+str+"%";
		}else{
			return null;
		}
	}
}
