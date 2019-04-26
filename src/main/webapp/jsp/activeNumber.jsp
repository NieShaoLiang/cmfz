<%@page contentType="text/html; utf-8" isELIgnored="false" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
<script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>

<div id="main" style="width: 60%;height: 100%;margin-top: 30px;margin-left: 30px"></div>
<script>
    var myChart = echarts.init(document.getElementById('main'));

    var goEasy = new GoEasy({
        appkey: "BC-375d0793129e4b7c900d1c6282061a17"
    });
    goEasy.subscribe({
        channel: "nsl",
        onMessage: function (message) {
            var t = message.content

            var content = JSON.parse(t)
            console.log(content)
            var weeks = content.activeNumber.weeks;
            var number = content.activeNumber.number;
            console.log("=============================================================")
            console.log(weeks)
            console.log(number)
            console.log("=============================================================")
            myChart.setOption({
                xAxis: {
                    data: weeks
                },
                series: [{
                    // 根据名字对应到相应的系列
                    name: '活跃用户',
                    data: number
                }]
            });

        }
    });



    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '持名法州App活跃用户'
        },
        tooltip: {},
        legend: {
            data:['用户数量']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '数量',
            type: 'bar',
            data: []
        }]
    };

    myChart.setOption(option);


    // 异步加载统计信息
    $.post("${pageContext.request.contextPath }/user/selectActiveNumber",function(data){
        console.log(data);
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption({
            xAxis: {
                data: data.weeks
            },
            series: [{
                // 根据名字对应到相应的系列
                name: '活跃用户',
                data: data.number
            }]
        });
    },"json");
</script>