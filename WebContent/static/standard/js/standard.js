$(function(){
    //在初始化table之前,要将table销毁,否则会保留上次加载的内容
    $('#customerlist').bootstrapTable('destroy');
    $('#customerlist').bootstrapTable({
        columns: [{
            field: 'stdnum',
            title: '标准号',
            align:'center',
            //sortable: true
        }, {
            field: 'zhname',
            title: '中文名称',
            align:'center',
            //sortable: true
        }, {
            field: 'version',
            title: '版本',
            align:'center',
            //sortable: true
        }, {
            field: 'keys',
            title: '关键字',
            align:'center',
            //sortable: true
        },{
            field: 'impldate',
            title: '实施日期',
            align:'center',
            //sortable: true
        },{
            field: 'releasedate',
            title: '发布日期',
            align:'center',
            //sortable: true
        },{
            title: '操作',
            width: '280px',
            events: 'operateEvents',
            formatter:AddFunctionAlty,
        }
        ],
        pagination: true, //分页,设置为true会在表格底部显示分页条
        sidePagination: 'server',//设置在哪里进行分页,可选值为 'client' 或者 'server'.设置 'server'时,必须设置服务器数据地址(url)或者重写ajax方法
        pageNumber:1,//如果设置了分页,首页页码
        pageSize:10,//如果设置了分页,页面数据条数
        pageList: [5 , 10, 50, 100],//如果设置了分页,设置可供选择的页面数据条数.设置为All 则显示所有记录
        showColumns:false,//是否显示内容列下拉框
        showToggle:true,//是否显示切换试图(table/card)按钮,true为切换
        showRefresh: true,//是否显示刷新按钮,true为显示
        queryParamsType:'limit',
        queryParams: queryParams,
        url: '/standard/list'
    });
    //请求服务器的参数
    function queryParams(params) {
        return {
            pageSize: params.limit,//容量
            rowoffset:params.offset,//(当前页-1)*容量
            //pageSize: params.pageSize,  //容量
            //pageNumber: params.pageNumber, //页码
            stdNum:$("#stdNum").val()
            //sort: params.sort,      //排序列名
            //sortOrder: params.order
        }
    }

    //发布日期控件
    $("#releaseDateAdd").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,

    }).on("click",function(){
        $("#releaseDateAdd").datetimepicker("setEndDate",$("#implDateAdd").val())
    });

    //实施日期控件
    $("#implDateAdd").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
        //startDate:new Date() 日期控件的初始值
    }).on("click",function(){
        $("#implDateAdd").datetimepicker("setStartDate",$("#releaseDateAdd").val())
    });

    //发布日期控件
    $("#releaseDateUpdate").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,

    }).on("click",function(){
        $("#releaseDateUpdate").datetimepicker("setEndDate",$("#implDateUpdate").val())
    });

    //实施日期控件
    $("#implDateUpdate").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
        //startDate:new Date() 日期控件的初始值
    }).on("click",function(){
        $("#implDateUpdate").datetimepicker("setStartDate",$("#releaseDateUpdate").val())
    });

    //hidden.bs.modal当模态框完全对用户隐藏时触发
    $("#addModal").on("hidden.bs.modal", function() {
        $("#stdNumAdd").val("");
        $("#zhnameAdd").val("");
        $("#versionAdd").val("");
        $("#keysAdd").val("");
        $("#releaseDateAdd").val("");
        $("#implDateAdd").val("");
    });

    $("#updateModal").on("hidden.bs.modal", function() {
        $("#stdNumUpdate").val("");
        $("#zhnameUpdate").val("");
        $("#versionUpdate").val("");
        $("#keysUpdate").val("");
        $("#releaseDateUpdate").val("");
        $("#implDateUpdate").val("");
    });

     //保存标准
    $("#saveStandard").click(function(){
        if ($("#stdNumAdd").val() == "") {
            alert("标准号不能为空!");
            return;
        }
        if ($("#zhnameAdd").val() == "") {
            alert("中文名称不能为空!");
            return;
        }
        if ($("#versionAdd").val() == "") {
            alert("版本不能为空!");
            return;
        }
        if ($("#keysAdd").val() == "") {
            alert("关键词不能为空!");
            return;
        }
        if ($("#releaseDateAdd").val() == "") {
            alert("发布日期不能为空!");
            return;
        }

        if ($("#implDateAdd").val() == "") {
            alert("发布日期不能为空!");
            return;
        }

        $.post("/standard/validate",
            {
                stdNum:$("#stdNumAdd").val(),
            },
            function(data, textStatus, jqxhr) {
                if (data.code==102) {
                    alert(data.msg);
                } else {
                    $.post("/standard/addStandard",
                        {
                            stdNum:$("#stdNumAdd").val(),
                            zhname:$("#zhnameAdd").val(),
                            version:$("#versionAdd").val(),
                            keys:$("#keysAdd").val(),
                            releaseDate:$("#releaseDateAdd").val(),
                            implDate:$("#implDateAdd").val(),
                        },
                        function(data, textStatus, jqxhr) {
                            if (data.code==0) {
                                alert(data.msg);
                                //隐藏模态框
                                $('#addModal').modal('hide');
                                //刷新table
                                $('#customerlist').bootstrapTable('refresh');
                            }else if(data.code==1){
                                alert(data.msg);
                            }
                        },"json");
                }
            },"json");
    });

    //修改标准
    $("#updateStandard").click(function(){
        if ($("#zhnameUpdate").val() == "") {
            alert("中文名称不能为空!");
            return;
        }
        if ($("#versionUpdate").val() == "") {
            alert("版本不能为空!");
            return;
        }
        if ($("#keysUpdate").val() == "") {
            alert("关键词不能为空!");
            return;
        }
        if ($("#releaseDateUpdate").val() == "") {
            alert("发布日期不能为空!");
            return;
        }

        if ($("#implDateUpdate").val() == "") {
            alert("发布日期不能为空!");
            return;
        }
        $.post("/standard/updateStandard",
            {
                hidId: $("#hidId").val(),
                zhname: $("#zhnameUpdate").val(),
                version: $("#versionUpdate").val(),
                keys: $("#keysUpdate").val(),
                releaseDate: $("#releaseDateUpdate").val(),
                implDate: $("#implDateUpdate").val(),
            },
            function (data, textStatus, jqxhr) {
                if (data.code == 0) {
                    alert(data.msg);
                    //隐藏模态框
                    $('#updateModal').modal('hide');
                    //刷新table
                    $('#customerlist').bootstrapTable('refresh');
                }else if(data.code==1){
                    alert(data.msg);
                }
            }, "json");
    });

    //查询方法
    $("#searchBtn").click(function(){
        $('#customerlist').bootstrapTable('refresh');
    });

});

//回车调用查询
function listStandard() {
    $('#customerlist').bootstrapTable('refresh');
}

//value: 代表当前单元格中的值。row：代表当前行。index: 代表当前行的下标。
function AddFunctionAlty(value,row,index){
    return[
        '<input id="update" class="btn btn-success btn-sm" type="button" value="修改" data-toggle="modal" data-target="#updateModal" />&nbsp&nbsp&nbsp',
        '<input id="delete" class="btn btn-danger btn-sm" type="button" value="删除" />&nbsp&nbsp&nbsp',
    ].join("")
}
window.operateEvents={
    "click #update":function(e,value,row,index){
        $.post("/standard/selectByPrimaryKey",
            {
                id:row.id,
            },
            function(data, textStatus, jqxhr) {
                //给弹出的模态框中标签赋值
                $("#hidId").val(data.data.id);
                $("#stdNumUpdate").val(data.data.stdnum);
                $("#zhnameUpdate").val(data.data.zhname);
                $("#versionUpdate").val(data.data.version);
                $("#keysUpdate").val(data.data.keys);
                $("#releaseDateUpdate").val(data.data.releasedate);
                $("#implDateUpdate").val(data.data.impldate);
            });
    }
    , "click #delete": function (e, value, row, index) {
        var r = confirm("确认删除" + row.zhname + "这条信息吗?");
        if (r) {
            $.post("/standard/deleteStandard",
                {
                    id: row.id,
                },
                function (data, textStatus, jqxhr) {
                    if (data.code == 0) {
                        alert(data.msg);
                        //刷新table
                        $('#customerlist').bootstrapTable('refresh');
                    } else {
                        alert(data.msg);
                    }
                }, "json");
        }
    }
}

