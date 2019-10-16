package cn.bdqn.ssm.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;




/**
 * @description 分页工具类
 * @author 李洋
 * @address 北大青鸟沈阳三好中心
 * @created 2017年4月4日 上午7:23:36
 * @version 1.0.0
 */
@Component
public class Page<T> {

	private Integer currPageNO;
	
	@Value("${perPageSize}")
	private String perPageSize;
	
	private Integer allRecordNO;
	
	private Integer allPageNO;
	
	private List<T> list = new ArrayList<>();
	
	//public Page(){}

	public Integer getCurrPageNO() {
		return currPageNO;
	}

	public void setCurrPageNO(Integer currPageNO) {
		this.currPageNO = currPageNO;
	}

	public String getPerPageSize() {
		return perPageSize;
	}

	public void setPerPageSize(String perPageSize) {
		this.perPageSize = perPageSize;
	}

	public Integer getAllRecordNO() {
		return allRecordNO;
	}

	public void setAllRecordNO(Integer allRecordNO) {
		this.allRecordNO = allRecordNO;
	}

	public Integer getAllPageNO() {
		return allPageNO;
	}

	public void setAllPageNO(Integer allPageNO) {
		this.allPageNO = allPageNO;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
