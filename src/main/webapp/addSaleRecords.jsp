<%--
  Created by IntelliJ IDEA.
  User: 18157
  Date: 2023/8/12
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="formId" method="post">
    <span style="margin-left: 10px">商品名：</span>

    <select id="goodsId" name="sale">
        <option>---请选择商品名---</option>
    </select>
    <HR style="margin: 20px">
    <br>
    <span style="margin-left: 10px">顾客：</span>
    <input type="text" name="customer" id="customer"><br>
    <span style="margin-left: 10px">数量：</span>
    <input type="text" name="sold" id="sold"><br>
    <input class="layui-btn" type="button" onclick="submitForm()" value="提交">
</form>

<script>
    var goodsId
    var sold, customer


    $('#goodsId').change(function () {
        goodsId = $('#goodsId').val()
    })

    function submitForm() {
        sold = $('#sold').val()
        customer = $('#customer').val()
        $.post(
            '<%=request.getContextPath()%>/saleRecords?method=add',
            {
                goods: goodsId,
                sold:sold,
                customer:customer
            },
            function (jsonObj) {
                console.log(jsonObj);
                if (jsonObj.code === 200) {
                    mylayer.okUrl(jsonObj.msg);
                    var index = parent.layer.getFrameIndex(window.name)
                    parent.layer.close(index);
                    parent.layui.table.reload('tableId')

                } else {
                    mylayer.errorMsg(jsonObj.msg);
                }
            },
            'json'
        );
    }

    $(function () {
        $.post(
            '<%=request.getContextPath()%>/saleRecords?method=selectGoods',
            function (jsonObj) {
                // console.log(jsonObj)
                $(jsonObj).each(function () {
                    $('#goodsId').append('<option value="' + this.id + '">' + this.name + '</option>');
                });
            },
            'json'
        );
    })
</script>

</body>
</html>
