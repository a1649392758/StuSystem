package cn.bdqn.ssm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bdqn.ssm.common.Page;
import cn.bdqn.ssm.dao.StandardMapper;
import cn.bdqn.ssm.entity.Standard;
import cn.bdqn.ssm.service.StandardService;

/**
 * @description 标准信息业务层实现
 * @author 李洋
 * @address 北大青鸟沈阳三好中心
 * @created 2017年7月2日 下午1:24:12
 * @version 1.0.0
 */
@Service
public class StandardServiceImpl implements StandardService{

	@Resource
	private StandardMapper mapper;
	@Resource
	private Page<Standard> page;
	
	@Override
	public Page<Standard> standardAllSelect(int currPageNO, String stdNum){
		int allRecordNO = 0;
		List<Standard> standardList = new ArrayList<>();
		page.setCurrPageNO(currPageNO);
		System.out.println("page.getPerPageSize()-->" + page.getPerPageSize());
		allRecordNO = mapper.totalCounts(stdNum);
		page.setAllRecordNO(allRecordNO);
		int allPageNO = 0;
		if(page.getAllRecordNO() % Integer.parseInt(page.getPerPageSize()) == 0){
			allPageNO = page.getAllRecordNO() / Integer.parseInt(page.getPerPageSize());
		}else{
			allPageNO = page.getAllRecordNO() / Integer.parseInt(page.getPerPageSize()) + 1;
		}
		page.setAllPageNO(allPageNO);
		int start = (page.getCurrPageNO()-1) * Integer.parseInt(page.getPerPageSize());
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("start", start);
		map.put("size", Integer.parseInt(page.getPerPageSize()));
		map.put("stdNum", stdNum);
		standardList = mapper.standardAllSelect(map);
		page.setList(standardList);
		return page;
	}

	@Override
	public boolean standardInsert(Standard standard){
		try {
			int updateRows = mapper.standardInsert(standard);
			if(updateRows>0){
				return true;
			}else {
				throw new RuntimeException("添加标准失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException("添加标准失败：" + e.toString());
		}
	}

	@Override
	public boolean standardCheck(String stdNum){
		boolean isExist = true;
		if(mapper.standardCheck(stdNum)>0){
			isExist = false;
		}
		return isExist;
	}

	@Override
	public boolean standardDelete(List<Integer> ids){
		try {
			int updateRows = mapper.standardDelete(ids);
			if(updateRows>0){
				return true;
			}else {
				throw new RuntimeException("删除标准失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException("删除标准失败：" + e.toString());
		}
	}

	@Override
	public Standard standardByIdSelect(int id){
		return mapper.standardByIdSelect(id);
	}

	@Override
	public boolean standardUpdate(Standard standard){
		try {
			int updateRows = mapper.standardUpdate(standard);
			if(updateRows>0){
				return true;
			}else {
				throw new RuntimeException("修改标准失败！");
			}
		} catch (Exception e) {
			throw new RuntimeException("修改标准失败：" + e.toString());
		}
	}

	@Override
	public List<Standard> standardExcelSelect(){
		return mapper.standardExcelSelect();
	}
}
