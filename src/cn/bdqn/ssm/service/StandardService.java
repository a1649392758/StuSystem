package cn.bdqn.ssm.service;

import java.util.List;

import cn.bdqn.ssm.common.Page;
import cn.bdqn.ssm.entity.Standard;

/**
 * @description 标准信息业务层
 * @author 李洋
 * @address 北大青鸟沈阳三好中心
 * @created 2017年7月2日 下午1:23:22
 * @version 1.0.0
 */
public interface StandardService {

	public Page<Standard> standardAllSelect(int currPageNO,String stuNum);
	public boolean standardInsert(Standard standard);
	public boolean standardCheck(String stuNum);
	public boolean standardDelete(List<Integer> ids);
	public Standard standardByIdSelect(int id);
	public boolean standardUpdate(Standard standard);
	public List<Standard> standardExcelSelect();
}
