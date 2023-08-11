<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="header.jsp"%>
</head>
<body>
<div class="demoTable">
    ID：
    <div class="layui-inline">
        <input class="layui-input" name="id" id="id" autocomplete="off">
    </div>

    供应商名：
    <div class="layui-inline">
        <input class="layui-input" name="name" id="nameId" autocomplete="off">
    </div>
    <button class="layui-btn" data-type="reload">搜索</button>
</div>

<table class="layui-hide" id="test" lay-filter="test"></table>

<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="deleteAll">批量删除</button>
        <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
    </div>
</script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="imageTemplet">
    <img src="/pic/{{d.image}}"/>
</script>
<script>
    layui.use('table', function(){
        var table = layui.table;
        var form = layui.form;
        table.render({
            elem: '#test'
            ,url:'<%=request.getContextPath()%>/supply?method=selectByPage'
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                ,layEvent: 'LAYTABLE_TIPS'
                ,icon: 'layui-icon-tips'
            }]
            ,title: '供应商表'
            ,cols: [[
                {type: 'checkbox', fixed: 'left'}
                ,{field:'id', title:'ID', fixed: 'left'}
                ,{field:'name', title:'供应商名'}
                ,{field:'address', title:'地址'}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
            ]]
            ,page: true
            ,id: 'tableId'
        });

        //头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'deleteAll':
                    var data = checkStatus.data;
                    var ids = new Array();
                    $(data).each(function() {
                        ids.push(this.id)
                    });
                    //[2,4]
                    layer.confirm('真的删除行么', function(index){
                        $.post(
                            '<%=request.getContextPath()%>/supply?method=deleteAll',
                            {'ids': ids},
                            function(result) {
                                if (result.code == 200) {
                                    mylayer.okMsg(result.msg);
                                    //删除之后重新刷新这个表格
                                    table.reload('tableId');
                                }
                            },
                            'json'
                        );
                    });
                    break;
                case 'add':
                    layer.open({
                        type: 2,
                        area: ['350px', '550px'],
                        content: '<%=request.getContextPath()%>/addSupply.jsp'
                    });
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选': '未全选');
                    break;

                //自定义头工具栏右侧图标 - 提示
                case 'LAYTABLE_TIPS':
                    layer.alert('这是工具栏右侧自定义的一个图标按钮');
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){
            //{id: 2, name: 'zhansgan', password: '23', age: 0, gender: null}
            var data = obj.data;
            console.log(obj)
            console.log('---------')
            console.log(obj.data)
            console.log(obj.data.id)
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    $.post(
                        '<%=request.getContextPath()%>/supply?method=delete',
                        {'id': data.id},
                        function(result) {
                            if (result.code == 200) {
                                mylayer.okMsg(result.msg);
                                //删除之后重新刷新这个表格
                                table.reload('tableId');
                            }
                        },
                        'json'
                    );
                });
            } else if(obj.event === 'edit'){
                layer.open({
                    type: 2,
                    area: ['550px', '350px'],
                    content: '<%=request.getContextPath()%>/supply?method=getSupplyUpdatePage&id=' + data.id
                });
            }
        });

        var $ = layui.$, active = {
            reload: function(){
                //执行重载
                table.reload('tableId', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,where: {
                        // /supply?method=selectByPage&page=1&limit=10&name=zhasang&gender=男
                        id: $('#id').val(),
                        name: $('#nameId').val(),

                    }
                });
            }
        };

        $('.demoTable .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });


    });
</script>
</body>
</html>