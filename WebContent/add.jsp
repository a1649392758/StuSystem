<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加标准信息</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
	table{
		border-collapse: collapse;
		border: 1px solid black; 
		width: 800px;
	}
	span{
		color:red;
	}
</style>
</head>
<body>
<form method="post" enctype="multipart/form-data"
		action="${pageContext.request.contextPath }/standard/add">
		<table>
			<tr>
				<td width="40%" align="right"><span>*</span>标准号：</td>
				<td width="60%">
				<input name="stdNum" required="required"/> 
				</td>
			</tr>
			<tr>
				<td align="right"><span>*</span>中文名称：</td>
				<td><input name="zhname" required="required"/>
				</td>
			</tr>
			<tr>
				<td align="right"><span>*</span>版本：</td>
				<td><input name="version" required="required"/> 
				</td>
			</tr>
			<tr>
				<td align="right"><span>*</span>关键字/词：</td>
				<td><input name="keys" required="required"/> 
				</td>
			</tr>
			<tr>
				<td align="right">发布日期(yyyy-MM-dd):</td>
				<td><input name="releaseDate" onClick="WdatePicker()"/> 
				</td>
			</tr>
			<tr>
				<td align="right">实施日期(yyyy-MM-dd):</td>
				<td><input name="implDate" onClick="WdatePicker()"/>
				</td>
			</tr>
			<tr>
				<td align="right"><span>*</span>附件:</td>
				<td><input type="file" name="imgs" required="required">
				</td>
			</tr>
			<tr>
				<td align="right">&nbsp;</td>
				<td><input id="save" type="submit" value="保存"/>
					<input type="button" value="取消" onclick="window.history.back();"  />
				</td>
			</tr>
		</table>
	</form>
	
	<script type="text/javascript">
		$("input[name=stdNum]").blur(function(){
			var stdNum = $.trim($(this).val());
			$.ajax({
				url:"${pageContext.request.contextPath}/standard/check",
				type : "POST",
				data : {stdNum:stdNum},
				dataType : "json",
				success : getJsonData,
				error:sendErrorMsg
			});
		});
		
		function getJsonData(data,textStatus,jqXHR){
			if (data.result==false) {
				alert("标准号重复!");
				$("#save").attr("disabled", true);
			}else{
				$("#save").removeAttr("disabled");
			}
		}
		
		function sendErrorMsg(data,textStatus,jqXHR) {
			alert("请求失败!");
		}
	</script>
</body>
</html>