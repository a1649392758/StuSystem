package cn.bdqn.ssm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.bdqn.ssm.entity.Standard;

/**
 * @description 标准信息数据库访问层
 * @author 李洋
 * @address 北大青鸟沈阳三好中心
 * @created 2017年7月2日 上午10:56:27
 * @version 1.0.0
 */
public interface StandardMapper {

	//按条件模糊分页查询
	public List<Standard> standardAllSelect(Map<String, Object> map);
	//查询总记录数
	public int totalCounts(@Param("stdNum") String stdNum);
	//添加标准
	public int standardInsert(Standard standard);
	//判断标准号是否存在
	public int standardCheck(@Param("stdNum") String stdNum);
	//删除标准（批量删除）
	public int standardDelete(List<Integer> ids);
	//按id查询
	public Standard standardByIdSelect(int id);
	//修改标准
	public int standardUpdate(Standard standard);
	//到处excel(全查询)
	public List<Standard> standardExcelSelect();
}
