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
		width: 100%;
	}
	table td{
		text-align: center;
	}
</style>
</head>
<body>
<h3 align="center">标准信息列表</h3>
	<table id="table">
		<tr height="30px">
			<td colspan="7" style="text-align: right">
				<input type="search" id="stdNum" name="stdNum" placeholder="模糊匹配标准号或中文名称" size="22">
				<input type="button" id="search" value="提交检索">
				<input type="button" id="add" value="新增标准">
				<input type="button" id="del" value="删除标准">
				<input type="button" id="exportExcel" value="导出信息列表"/>
			</td>
		</tr>
		<tr height="30px">
			<td width="5%" align="center" id="selectAll">
				<input name="all" type="checkbox">
			</td>
			<th width="20%">标准号</th>
			<th width="30%">中文名称</th>
			<th width="15%">版本</th>
			<th width="10%">发布日期</th>
			<th width="10%">实施日期</th>
			<th width="10%">操作</th>
		</tr>	
  		<tr id="show">
  			<td colspan="7">
  				<div id="">
  					<img src="${pageContext.request.contextPath }/images/load.gif" width="50" height="50"/>
  					正在加载数据,请稍后...
  				</div>
  			</td>
  		</tr>		
	</table>
	<div id="page" style="text-align: right;line-height: 30px;vertical-align: middle;">
		<a id="firstPage" href="javascript:goToFirstPage();">首页</a>| 
		<a id="prePage" href="javascript:goToPrePage();">上一页</a>| 
		<a id="nextPage" href="javascript:goToNextPage();">下一页</a>| 
		<a id="endPage" href="javascript:goToEndPage();">末页</a>共<span id="totalCount"></span>条数据,第<span id="pageNo"></span>页/共<span id="maxPageNo"></span>页
	</div>
	
	<script type="text/javascript">
	    var pageNo = 1;
	    var stdNum = "";
		$("#search").click(function(){
			stdNum = $.trim($("#stdNum").val());
			getStandardList(pageNo,stdNum);
		});
			
		//首页
		function goToFirstPage(){
			pageNo = 1;
			getStandardList(pageNo,stdNum);
		}
		
		//上一页
		function goToPrePage(){
			pageNo -= 1;
			pageNo = pageNo > 0 ? pageNo : 1;
			getStandardList(pageNo,stdNum);
		}
		
		//下一页
		function goToNextPage(){
			var maxPageNo=$("#maxPageNo").text();
			maxPageNo=parseInt(maxPageNo);
			if(pageNo+1<=maxPageNo){
				pageNo += 1;
			}
			getStandardList(pageNo,stdNum);
		}
		
		//尾页
		function goToEndPage(){
			var maxPageNo=$("#maxPageNo").text();
			maxPageNo=parseInt(maxPageNo);
			pageNo = maxPageNo;
			getStandardList(pageNo,stdNum);
		}
		
		function getStandardList(pageNo,stdNum){
			$.ajax({
				//请求类型
				type:"POST",
				//预期服务器返回的数据类型
				dataType:"json",
				//请求参数
				data:{
			    	pageNo:pageNo,
					stdNum:stdNum	
				},
				//请求URL
				url:"${pageContext.request.contextPath }/standard/select",
				beforeSend:function(){				 
					// 正在加载数据
					$("#load").show();   
				},
				success:getJsonData,
				error:function(data,textStatus,jqXHR){
					alert("请求失败！！！");
				}
			});
		}
		
		function getJsonData(data,textStatus,jqXHR){
			$("#load").hide();
			clearData();
			$("#pageNo").text(data.page);
			$("#maxPageNo").text(data.maxPageNo);
			$("#totalCount").text(data.totalCount);
			if(data.maxPageNo==1){
				$("#firstPage").removeAttr("href").removeAttr("onclick");
				$("#prePage").removeAttr("href").removeAttr("onclick");
				$("#nextPage").removeAttr("href").removeAttr("onclick");
				$("#endPage").removeAttr("href").removeAttr("onclick");
			}
			$.each(data.standardList,function(index,value){
				if(value['releaseDate']==undefined){
					value['releaseDate'] = '无相关信息';
				}
				if(value['implDate']==undefined){
					value['implDate'] = '无相关信息';
				}
				var $newTr=$("<tr><td><input type='checkbox' name='ck' value='"+value['id']+"'/></td><td>"
						+value['stdNum']+"</td><td>"
						+value['zhname']+"</td><td>"
						+value['version']+"</td><td>"
						+value['releaseDate']+"</td><td>"
						+value['implDate']+"</td><td>"
						+"<a href='javascript:void(0)' name='download' title='"+value['packagePath']+"'>下载</a>&nbsp;<a href='javascript:void(0)' name='update' title='"+value['id']+"'>修改</a></td>"+
						+"</tr>");
				$("#table").append($newTr);
				$("tr:odd").css("background-color", "gray"); 
			});
			$("a[name=download]").click(function(event){
				var fileName = event.target["title"];
				window.location.href = "${pageContext.request.contextPath}/standard/download?fileName=" + fileName;
			});
			//方式1
			/* $("a[name=update]").click(function(event){
				var id = event.target["title"];
				window.location.href = "${pageContext.request.contextPath}/standard/updateInit/" + id;
			}); */
		}
		
		//方式2
		$("a[name=update]").live("click",function(){
			var id = event.target["title"];
			window.location.href = "${pageContext.request.contextPath}/standard/updateInit/" + id;
		});
		
		$("#del").click(function(){
			if(confirm("您确定要删除吗?")){
    			var isCheck = false;
	    		var ck = $("[name=ck]");
	    		var params = "";
	    		for(var i=0;i<ck.length;i++){
	    			if(ck[i].checked){
	    				params += ck[i].value + ",";
	    				isCheck = true;
	    			}
	    		}
	    		if(isCheck==false){
	    			alert("请选择删除项!");
	    		}else{
	    			params = params.substring(0,params.length-1);
		    		var data = {"params":params};
		    		delAjax(data);
	    		}
    		}
		});
		
		function delAjax(data){
			$.ajax({
				url:"${pageContext.request.contextPath}/standard/delete",
				type:"GET",
				data:data,
				dataType:"json",
				success:successFun,
				error:sendErrorMsg
			});
		}
		
		function successFun(data,textStatus,jqXHR){
			if(data.result==true){
				alert("删除成功！")
				getStandardList(pageNo,stdNum);
			}else{
				alert("删除失败！")
			}
		}
		
		function sendErrorMsg(data,textStatus,jqXHR){
			alert("请求失败")
		}
		
		$("[name=all]").click(function() {
        	if($("[name=all]").attr("checked")){
				$("[name=ck]").attr("checked","checked");
			}else{
				$("[name=ck]").removeAttr("checked");
			}
    	});
		
		
		//清除原有数据
		function clearData(){
			// 清除原来增加的tr,把大于第一行的数据都给清除
			$("#table tr:gt(1)").remove();		
		};
		
		$("#add").click(function(){
			window.location.href="${pageContext.request.contextPath}/add.jsp";
		}); 
		
		$("#exportExcel").click(function(){
			window.location.href="${pageContext.request.contextPath}/standard/exportExcel";
		});
	</script>
</body>
</html>