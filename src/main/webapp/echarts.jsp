<%--
  Created by IntelliJ IDEA.
  User: 18157
  Date: 2023/8/3
  Time: 9:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <title>ECharts</title>
    <!-- 引入刚刚下载的 ECharts 文件 -->
    <script src="<%=request.getContextPath()%>/static/echarts.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath()%>/static/jquery-2.1.4.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<!-- 为 ECharts 准备一个定义了宽高的 DOM -->
<div style="display: flex; margin-top: 30px">
    <div id="main" style="width: 600px;height:400px;" ></div>
    <div id="main2" style="width: 600px;height:400px;"></div>
</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var myChart2 = echarts.init(document.getElementById('main2'));

    $.post(
        '<%=request.getContextPath()%>/echarts?method=sale',
        function(jsonObj) {
            console.log(jsonObj);
            //[{name: 'java1807', value: 4},{name: 'java1812', value: 5}]
            var xArray = new Array();
            var yArray = new Array();
            $(jsonObj).each(function() {
                xArray.push(this.goods);
                yArray.push(this.value);
            });
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '销售流水统计'
                },
                tooltip: {},
                xAxis: {
                    data: xArray
                },
                yAxis: {},
                series: [
                    {
                        name: '商品',
                        type: 'bar',
                        data: yArray
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        },
        'json'
    );

    //供应流水统计
    $.post(
        '<%=request.getContextPath()%>/echarts?method=supply',
        function(jsonObj) {
            console.log(jsonObj);
            //[{name: 'java1807', value: 4},{name: 'java1812', value: 5}]
            var xArray = new Array();
            var yArray = new Array();
            $(jsonObj).each(function() {
                xArray.push(this.goods);
                yArray.push(this.value);
            });
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '供应流水统计'
                },
                tooltip: {},
                legend: {
                    data: ['供应量']
                },
                xAxis: {
                    data: xArray,
                    axisLabel: { interval: 0, rotate: 30 }
                },
                yAxis: {},
                series: [
                    {
                        name: '商品',
                        type: 'bar',
                        data: yArray
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart2.setOption(option);
        },
        'json'
    );

</script>
</body>
</html>