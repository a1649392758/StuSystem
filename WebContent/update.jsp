<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改标准信息</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
	table{
		border-collapse: collapse;
		border: 1px solid black; 
	}
	table td{
		text-align: center;
	}
	span{
		color:red;
	}
	div{
		margin: 5px auto;
		width: 500px;
	}
</style>
</head>
<body>
	<div>
		<form method="post" enctype="multipart/form-data"
			action="${pageContext.request.contextPath }/standard/update">
			<table>
				<tr>
					<td width="40%" align="right">
					<input type="hidden" name="id" value="${standard.id }"/>
					<span>*</span>标准号：</td>
					<td width="60%">
					<input name="stdNum" value="${standard.stdNum }" readonly="readonly"/> 
					</td>
				</tr>
				<tr>
					<td align="right"><span>*</span>中文名称：</td>
					<td><input name="zhname" value="${standard.zhname }"/>
					</td>
				</tr>
				<tr>
					<td align="right"><span>*</span>版本：</td>
					<td><input name="version" value="${standard.version }"/> 
					</td>
				</tr>
				<tr>
					<td align="right"><span>*</span>关键字/词：</td>
					<td><input name="keys" value="${standard.keys }"/>
					</td>
				</tr>
				<tr>
					<td align="right">发布日期(yyyy-MM-dd):</td>
					<td><input name="releaseDate" value="<fmt:formatDate value='${standard.releaseDate }' type='date'/>" onClick="WdatePicker()"/> 
					</td>
				</tr>
				<tr>
					<td align="right">实施日期(yyyy-MM-dd):</td>
					<td><input name="implDate" value="<fmt:formatDate value='${standard.implDate }' type='date'/>" onClick="WdatePicker()"/>
					</td>
				</tr>
				<tr>
					<td align="right"><span>*</span>附件:</td>
					<td><input type="file" name="imgs" required="required">
					</td>
				</tr>
				<tr>
					<td align="right">&nbsp;</td>
					<td><input type="submit" name="button" id="button" value="保存" />
						<input type="button" onclick="window.history.back();" value="取消" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
