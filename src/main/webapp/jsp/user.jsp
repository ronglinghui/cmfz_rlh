<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <script type="text/javascript">


        $(function () {
            //goeasy服务器传输页名的方式
            var goEasy = new GoEasy({
                host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: "BS-172bf1a3efa1482d9364f61e2923bb62", //替换为您的应用appkey
            });
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '最近七天'
                },
                tooltip: {},
                legend: {
                    data: ['注册量']
                },
                xAxis: {
                    data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
                },
                yAxis: {},
                series: [{
                    name: '注册量',
                    type: 'bar',
                }]
            };


            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            $.post("${pageContext.request.contextPath}/user/selectBySeven", function (date) {
                myChart.setOption({
                    xAxis: {
                        data: date[2]
                    },
                    series: [{
                        data: date[1]
                    }]
                });
            }, "json")


            //goeasy传输值
            goEasy.subscribe({
                channel: "echarts", //替换为您自己的channel
                onMessage: function (message) {
                    /*alert("Channel:" + message.channel + " content:" + message.content);*/
                    console.log(message.content);
                    var parse = JSON.parse(message.content);
                    console.log("------" + parse);
                    myChart.setOption({
                        xAxis: {
                            data: parse[2]
                        },
                        series: [{
                            data: parse[1]
                        }]
                    });
                }
            });


            //全部
            // 基于准备好的dom，初始化echarts实例
            var myChart1 = echarts.init(document.getElementById('main1'));
            var option1 = {
                title: {
                    text: '用户地理分布图',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['用户']
                },
                visualMap: {
                    min: 0,
                    max: 2500,
                    left: 'left',
                    top: 'bottom',
                    text: ['低'],           // 文本，默认为数值文本
                    calculable: true
                },
                toolbox: {
                    show: true,
                    orient: 'vertical',
                    left: 'right',
                    top: 'center',
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: '用户',
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart1.setOption(option1);
            $.post("${pageContext.request.contextPath}/user/selectUser", function (date) {
                myChart1.setOption({
                    series: [{
                        data: date
                    }]
                });
            }, "json")

            //1-12月份
            // 基于准备好的dom，初始化echarts实例
            var myChart2 = echarts.init(document.getElementById('main2'));

            // 指定图表的配置项和数据
            var option2 = {
                title: {
                    text: '1-12月'
                },
                tooltip: {},
                legend: {
                    data: ['注册量']
                },
                xAxis: {
                    data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
                },
                yAxis: {},
                series: [{
                    name: '注册量',
                    type: 'line',
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart2.setOption(option2);
            $.post("${pageContext.request.contextPath}/user/selectMonth", function (date) {
                myChart2.setOption({
                    xAxis: {
                        data: date[1]
                    },
                    series: [{
                        data: date[0]
                    }]
                });
            })
        })
    </script>
    <style>
        #main {
            float: left;
        }

        #main1 {
            float: left;
        }

        #main2 {
            float: left;
        }
    </style>
</head>
<body>
<div>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 500px;height:200px;"></div>
    <div id="main1" style="width: 300px;height:200px;"></div>
    <div id="main2" style="width: 800px;height:200px;"></div>
</div>
</body>
</html>