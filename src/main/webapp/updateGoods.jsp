<%@ page import="com.kowalski.pojo.Goods" %><%--
  Created by IntelliJ IDEA.
  User: 18157
  Date: 2023/7/27
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改课程信息</title>
    <link href="static/bootstrap-3.4.1-dist/css/bootstrap.css">
</head>
<body>
    <%
        Goods goods = (Goods) request.getAttribute("goods");
    %>
    <form action="<%=request.getContextPath()%>/goods?method=updateGoods" method="post">
        <input type="hidden" name="id" value="<%=goods.getId()%>" > <br/>
        商品名：<input type="text" name="name" value="<%=goods.getName()%>"> <br/>
        规格：<input type="text" name="specs" value="<%=goods.getSpecs()%>"> <br/>
        单价：<input type="text" name="price" value="<%=goods.getPrice()%>"> <br/>
        库存：<input type="text" name="store" value="<%=goods.getStore()%>"> <br/>
        <input type="submit" value="修改">
    </form>
</body>
</html>
