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
    <jsp:include page="../../wechat.jsp"></jsp:include>
    <title>月度统计</title>
    <script type="text/javascript" src="${ctx}/js/echart/echarts.js"></script>
    <script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            ajaxJS(
                '${ctx}/console/report/MonthBar',
                {},
                'true',
                'get',
                function (result) {
                    console.log(result);
                    console.log(result.obj.seriesDatas);
                    // 指定图表的配置项和数据
                    var option = option = {
                        title: {
                            text: '消费比例',
                            subtext: '用途',
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
                                    normal:{
                                        label:{
                                            show: true,
                                            formatter: '{b} : {c} ({d}%)'
                                        },
                                        labelLine :{show:true}
                                    }
                                }
                            }
                        ]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            );
        });
    </script>
</head>
<body>
<div style="width: 95%;height:100%;margin: 0 auto;margin-top: 10px;margin-bottom: 10px">
    <form id="searchProductForm">
        <table style="width: 90%; height: 100%; text-align: center;">
            <tr>
                <th width="10%" align="right">类型：</th>
                <td width="20%" align="left">
                    <input name="purposeType" type="radio" value="-1"/>支出
                    <input name="purposeType" type="radio" value="1"/>收入
                </td>
                <th width="10%" align="right">类型：</th>
                <td width="20%" align="left">
                    <input name="tallyType" type="radio" value="purpose"/>用途
                    <input name="tallyType" type="radio" value="account"/>账户
                    <input name="tallyType" type="radio" value="payuser"/>使用者
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
<div id="main" style="width: 95%;height:500px;margin: 0 auto;"></div>
</body>
</html>
