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
    <title>添加商户</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
</head>
<body>
<form id="formId" class="layui-form layui-form-pane" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">商户名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" autocomplete="off" placeholder="请输入商户名" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">地址</label>
        <div class="layui-input-block">
            <input type="text" name="address" autocomplete="off" placeholder="请填写商户地址" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" onclick="submitForm()" class="layui-btn" lay-filter="demo1">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
<script>
    function submitForm() {
        $.post(
            '<%=request.getContextPath()%>/shop?method=add',
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
