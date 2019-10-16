package cn.bdqn.ssm.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @description 标准信息类
 * @author 李洋
 * @address 北大青鸟沈阳三好中心
 * @created 2017年7月2日 上午10:44:24
 * @version 1.0.0
 */
public class Standard {

	private Integer id;
	private String stdNum;
	private String zhname;
	private String version;
	private String keys;
	@JSONField(format="yyyy-MM-dd")
	private Date releaseDate;
	@JSONField(format="yyyy-MM-dd")
	private Date implDate;
	private String packagePath;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStdNum() {
		return stdNum;
	}
	public void setStdNum(String stdNum) {
		this.stdNum = stdNum;
	}
	public String getZhname() {
		return zhname;
	}
	public void setZhname(String zhname) {
		this.zhname = zhname;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Date getImplDate() {
		return implDate;
	}
	public void setImplDate(Date implDate) {
		this.implDate = implDate;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
}
