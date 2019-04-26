<%@page contentType="text/html; utf-8" isELIgnored="false" pageEncoding="UTF-8" language="java" %>
<script type="text/javascript" src="../js/echarts.min.js"></script>
<script type="text/javascript" src="../js/china.js"></script>
<script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="statistics_china" style="width: 100%;height: 100%;margin-top: 30px;margin-left: 30px">

</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('statistics_china'));

//使用goeasy接收消息
    var goEasy = new GoEasy({
        appkey: "BC-375d0793129e4b7c900d1c6282061a17"
    });
    goEasy.subscribe({
        channel: "nsl",
        onMessage: function (message) {
            //console.log(message.content)
            var t = message.content
            //alert(t)
            var content = JSON.parse(t);
            console.log(content.famale)
            console.log(content.male)

                myChart.setOption({
                series: [{
                    // 根据名字对应到相应的系列
                    name: '男',
                    data: content.male
                }]
            });
            myChart.setOption({
                series: [{
                    // 根据名字对应到相应的系列
                    name: '女',
                    data: content.famale
                }]
            });

        }
    })





    function randomData() {
        return Math.round(Math.random() * 1000);
    }

    option = {
        title: {
            text: '持名法州APP用户分布图',
            subtext: '2019年4月22日 最新数据',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        // 说明
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['男', '女']
        },
        visualMap: {
            min: 0,
            max: 2500,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'],           // 文本，默认为数值文本
            calculable: true
        },
        // 工具箱
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                dataView: {readOnly: false},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '男',
                type: 'map',
                mapType: 'china',
                roam: false,
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: []
            },
            {
                name: '女',
                type: 'map',
                mapType: 'china',
                label: {
                    normal: {
                        show: true
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: []
            }
        ]
    };
    myChart.setOption(option);

    $(function () {
        $.post("${pageContext.request.contextPath}/user/selectByProvince1", function (data) {
            console.log(data.list);
            myChart.setOption({
                series: [{
                    // 根据名字对应到相应的系列
                    name: '男',
                    data: data.list
                }]
            });
        }, "json");

        $.post("${pageContext.request.contextPath}/user/selectByProvince0", function (data) {
            console.log(data.list);
            myChart.setOption({
                series: [{
                    // 根据名字对应到相应的系列
                    name: '女',
                    data: data.list
                }]
            });
        }, "json");
    });
</script>