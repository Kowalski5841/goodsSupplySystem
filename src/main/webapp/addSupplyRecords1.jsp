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
    <span style="margin-left: 10px">供应商：</span>

    <select id="supplyId" name="supply">
        <option>---请选择供应商---</option>
    </select>
    商户：
    <select id="shopId" name="shop">
        <option>---请选择商户---</option>
    </select>
    商品名：
    <select id="goodsId" name="goods">
        <option>---请选择商品名---</option>
    </select>
    <HR style="margin: 20px">
    <br>
    <span style="margin-left: 10px">数量：</span>
    <%--    <input type="text" name="cc" id="cc"/><br>--%>
    <input type="text" name="cc" id="cc">
    <input class="layui-btn" type="button" onclick="submitForm()" value="提交">
</form>

<script>
    var supplyId, shopId, goodsId
    var cc
    $('#supplyId').change(function () {
        supplyId = $('#supplyId').val()
    })

    $('#shopId').change(function () {
        shopId = $('#shopId').val()
    })

    $('#goodsId').change(function () {
        goodsId = $('#goodsId').val()
    })

    function submitForm() {
        cc = $('#cc').val()

        // $.ajax({
        //     url: '',
        //     method:'post',
        //     data:{
        //
        //     },
        //     // success:function (res){
        //     //
        //     // },
        //     // error:function (res){
        //     //
        //     // },
        //
        //
        //     success(){
        //
        //     },
        //     error(){
        //
        //     },
        //     dataType:'json'
        //
        // })
        $.post(
            '<%=request.getContextPath()%>/supplyRecords?method=add',
            {
                supply: supplyId,
                shop: shopId,
                goods: goodsId,
                cc: cc
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
            '<%=request.getContextPath()%>/supplyRecords?method=selectSupply',
            function (jsonObj) {
                // console.log(jsonObj)
                $(jsonObj).each(function () {
                    $('#supplyId').append('<option value="' + this.id + '">' + this.name + '</option>');
                });
            },
            'json'
        );
        $.post(
            '<%=request.getContextPath()%>/supplyRecords?method=selectShop',
            function (jsonObj) {
                // console.log(jsonObj)
                $(jsonObj).each(function () {
                    $('#shopId').append('<option value="' + this.id + '">' + this.name + '</option>');
                });
            },
            'json'
        );
        $('#supplyId').change(function (){
            $('#goodsId option:gt(0)').remove();
            var supplyId =$('#supplyId').val();
            $.post(
                '<%=request.getContextPath()%>/supplyRecords?method=selectGoods',
                {'supplyId':supplyId},
                function (jsonObj) {
                    // console.log(jsonObj)
                    $(jsonObj).each(function () {
                        $('#goodsId').append('<option value="' + this.id + '">' + this.name + '</option>');
                    });
                },
                'json'
            );
        })

    })
</script>

</body>
</html>
