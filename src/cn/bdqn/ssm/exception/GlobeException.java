package cn.bdqn.ssm.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description 全局异常处理类
 * @author 李洋
 * @address 北大青鸟沈阳三好中心
 * @created 2018年4月23日 上午7:08:52
 * @version 1.0.0
 */
@ControllerAdvice
public class GlobeException {

	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Map<String, Object> exceptionHandler(HttpServletRequest request,Exception e){
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("success", false);
		modelMap.put("errMsg", e.getMessage());
		return modelMap;
	}
}
