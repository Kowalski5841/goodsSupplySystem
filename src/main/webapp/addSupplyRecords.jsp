<%--
  Created by IntelliJ IDEA.
  User: 18157
  Date: 2023/7/27
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp"%>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title>录入供应记录</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
</head>
<body>

<form id="formId" class="layui-form layui-form-pane" action="">
    <div class="layui-btn-container">
        <button class="layui-btn demo1" id="goodSelector">
            下拉菜单
            <i class="layui-icon layui-icon-down layui-font-12"></i>
        </button>
        <button class="layui-btn layui-btn-primary demo1">
            下拉菜单
            <i class="layui-icon layui-icon-down layui-font-12"></i>
        </button>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" onclick="submitForm()" class="layui-btn" lay-filter="demo1">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script>
    layui.use(['dropdown', 'util', 'layer', 'table'], function() {
        var dropdown = layui.dropdown
            , util = layui.util
            , layer = layui.layer
            , table = layui.table
            , $ = layui.jquery;

        //初演示
        dropdown.render({
            elem: '.demo1'
            , data: [
                $.post(
                    '<%=request.getContextPath()%>/supplyRecords?method=selectGoods',
                    function (jsonObj) {
                        console.log(jsonObj)
                        $(jsonObj).each(function (){
                            //<option value="001">山东</option>
                            $('#goodSelector').append('<option value="'+this.id+'">'+this.name+'</option>');
                        });
                    },
                    'json'
                )
            ]
            , click: function (obj) {
                layer.tips('点击了：' + obj.title, this.elem, {tips: [1, '#5FB878']})
            }
        });
    })
</script>
<script>
    function submitForm() {
        $.post(
            '<%=request.getContextPath()%>/goods?method=add',
            $('#formId').serialize(),
            function(result) {
                console.log(result);
                if (result.code == 200) {
                    mylayer.okMsg(result.msg)
                    setInterval(function (){
                        //关闭弹出框
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        //刷新父页面
                        window.parent.location.reload();
                    },500)

                } else {
                    mylayer.errorMsg(result.msg);
                }
            },
            'json'
        );
    }
</script>
</body>
</html>
