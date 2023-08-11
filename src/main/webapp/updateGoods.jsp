<%@ page import="com.kowalski.pojo.Goods" %><%--
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
    <title>修改商品</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/layui/css/layui.css"  media="all">
</head>
<body>
<%
    Goods goods = (Goods) request.getAttribute("goods");
%>
<form id="formId" class="layui-form layui-form-pane" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">ID</label>
        <div class="layui-input-block">
            <input type="text" name="id" autocomplete="off"
                    class="layui-input" readonly
                   style="color: #555555"
                   value="<%=goods.getId()%>"
            >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">商品名</label>
        <div class="layui-input-block">
            <input type="text" name="name" autocomplete="off"
                   placeholder="请输入商品名" class="layui-input"
                   value="<%=goods.getName()%>"
            >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">规格</label>
        <div class="layui-input-block">
            <input type="text" name="specs" autocomplete="off"
                   placeholder="请描述商品规格" class="layui-input"
                   value="<%=goods.getSpecs()%>"
            >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">单价</label>
        <div class="layui-input-block">
            <input type="text" name="price" autocomplete="off"
                   placeholder="请输入单价" class="layui-input"
                   value="<%=goods.getPrice()%>"
            >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">库存</label>
        <div class="layui-input-block">
            <input type="text" name="store" autocomplete="off"
                   placeholder="请输入库存" class="layui-input"
                   value="<%=goods.getStore()%>"
            >
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
            '<%=request.getContextPath()%>/goods?method=updateGoods',
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
