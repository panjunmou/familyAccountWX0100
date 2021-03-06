<%--
  Created by IntelliJ IDEA.
  User: PanJM_Levono
  Date: 2016/12/28
  Time: 8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%--<jsp:include page="../../wechat.jsp"></jsp:include>--%>
    <jsp:include page="../../inc.jsp"></jsp:include>
    <title>月度统计</title>
    <script type="text/javascript" src="${ctx}/js/echart/echarts.js"></script>
    <script type="text/javascript">
        var myChart;
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            myChart = echarts.init(document.getElementById('main'));

            loadChart();
        });

        function loadChart() {
            var purposeType = $("[name='purposeType']:checked").val();
            var tallyType = $("[name='tallyType']:checked").val();
            var dateStart = $("#dateStart").datebox('getValue');
            var dateEnd = $("#dateEnd").datebox('getValue');
            $.ajax({
                type: "get",
                url: '${ctx}/console/report/MonthBar',
                dataType: "json",
                data: {
                    tallyType: tallyType,
                    purposeType: purposeType,
                    dateStart: dateStart,
                    dateEnd: dateEnd
                },
                success: function (result) {
                    console.log(result);
                    console.log(result.obj.seriesDatas);
                    if (result.obj.seriesDatas == undefined) {
                        $("#main").html('<span style="font-size: 20px;font-weight: bold">暂无数据!!!</span>');
                    }
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '消费比例',
                            subtext: '共计支出:'+result.obj.totalOut+'元;共计收入:'+result.obj.totalIn+'元',
                            x: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                            data: result.obj.legendDatas
                        },
                        series: [
                            {
                                name: '用途',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: result.obj.seriesDatas,
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    },
                                    normal: {
                                        label: {
                                            show: true,
                                            formatter: '{b} : {c} ({d}%)'
                                        },
                                        labelLine: {show: true}
                                    }
                                }
                            }
                        ]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });
        }
    </script>
</head>
<body>
<div style="width: 95%;height:100%;margin: 0 auto;margin-top: 10px;margin-bottom: 10px">
    <form id="searchProductForm">
        <table style="width: 90%; height: 100%; text-align: center;">
            <tr>
                <th width="10%" align="right">类型：</th>
                <td width="20%" align="left">
                    <input name="purposeType" type="radio" value="-1" checked/>支出
                    <input name="purposeType" type="radio" value="1"/>收入
                </td>
                <th width="10%" align="right">类型：</th>
                <td width="20%" align="left">
                    <input name="tallyType" type="radio" value="purpose" checked/>用途
                    <input name="tallyType" type="radio" value="account"/>账户
                    <input name="tallyType" type="radio" value="payuser"/>使用者
                </td>
            </tr>
            <tr>
                <th width="10%" align="right">日期从：</th>
                <td width="20%" align="left">
                    <input id="dateStart" name="dateStart" placeholder="点击选择日期" class="easyui-datebox"
                           data-options="required:true,editable:false" value="${dateStart}"/>
                </td>
                <th width="10%" align="right">日期到：</th>
                <td width="20%" align="left">
                    <input id="dateEnd" name="dateEnd" placeholder="点击选择日期" class="easyui-datebox"
                           data-options="required:true,editable:false" value="${dateEnd}"/>
                </td>
            </tr>
            <tr>
                <th width="10%" align="right">&nbsp;</th>
                <td width="20%" align="left">
                    &nbsp;
                </td>
                <th width="10%" align="right">&nbsp;</th>
                <td width="20%" align="left">
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search',plain:true" onclick="loadChart();">
                        查询 </a>
                </td>
            </tr>
        </table>
        <div>
            <input id="page" name="page" type="hidden"/>
            <input id="pageSize" name="pageSize" type="hidden"/>
            <input id="sort" name="sort" type="hidden"/>
            <input id="order" name="order" type="hidden"/>
        </div>
    </form>
</div>
<div id="main" style="width: 95%;height:500px;margin: 0 auto;text-align: center"></div>
</body>
</html>
