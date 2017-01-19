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
    <jsp:include page="../../inc.jsp"></jsp:include>
    <title>年度统计</title>
    <!-- CSS goes in the document HEAD or added to your external stylesheet -->
    <style type="text/css">
        table.imagetable {
            font-family: verdana, arial, sans-serif;
            font-size: 11px;
            color: #333333;
            border-width: 1px;
            border-color: #999999;
            border-collapse: collapse;
        }

        table.imagetable th {
            background: #b5cfd2 url('/style/images/cell-blue.jpg');
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #999999;
        }

        table.imagetable td {
            background: #dcddc0 url('/style/images/cell-grey.jpg');
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #999999;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            loadData();
        })

        function loadData() {
            $.ajax({
                type: "get",
                url: "${ctx}/console/report/inAndOutList",
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    if (result.success) {
                        var inList = result.obj.inList;
                        var outList = result.obj.outList;
                        console.log(outList);
                        var sumInArr = new Array();
                        var sumOutArr = new Array();
                        for (var i = 0; i < 13; i++) {
                            sumInArr[i] = 0;
                            sumOutArr[i] = 0;
                        }
                        $.each(inList, function (i, v) {
                            var moneyArr = v.money;
                            var tr = $('<tr/>');
                            var td = $('<td/>');
                            td.html(v.purposeName);
                            tr.append(td);
                            var sum = parseFloat(0);
                            $.each(moneyArr, function (m, n) {
                                sumInArr[m] = sumInArr[m] + parseFloat(n);
                                sum = sum + parseFloat(n);
                                td = $('<td/>');
                                td.html(n);
                                tr.append(td);
                            });
                            td = $('<td/>');
                            td.html(sum);
                            tr.append(td);
                            $("#inTable tr:eq(" + i + ")").after(tr);
                            tr.on("click", function () {
                                $.ajax({
                                    type: "get",
                                    url: "${ctx}/console/report/childrenTableList",
                                    dataType: "json",
                                    data:{
                                        parentPurposeNo:v.purposeNo
                                    },
                                    success: function (result) {
                                        console.log(resutl);
                                    }
                                });
                            });
                            sumInArr[12] = sumInArr[12] + parseFloat(sum);
                        });
                        $.each(outList, function (i, v) {
                            var moneyArr = v.money;
                            var tr = $('<tr/>');
                            var td = $('<td/>');
                            td.html(v.purposeName);
                            tr.append(td);
                            var sum = parseFloat(0);
                            $.each(moneyArr, function (m, n) {
                                sumOutArr[m] = sumOutArr[m] + parseFloat(n);
                                sum = sum + parseFloat(n);
                                td = $('<td/>');
                                td.html(n);
                                tr.append(td);
                            });
                            td = $('<td/>');
                            td.html(parseFloat(sum).toFixed(2));
                            tr.append(td);
                            $("#outTable tr:eq(" + i + ")").after(tr);
                            sumOutArr[12] = sumOutArr[12] + parseFloat(sum);
                        });
                        var tdInArr = $("#inTable tr:last").find('td');
                        var tdOutArr = $("#outTable tr:last").find('td');
                        var colsTdInArr = new Array();
                        var colsTdOutArr = new Array();
                        for (var i = 0; i < 6; i++) {
                            colsTdInArr[i] = 0;
                            colsTdOutArr[i] = 0;
                        }
                        for (var i = 0; i < 13; i++) {
                            var sumIn = parseFloat(sumInArr[i]).toFixed(2);
                            var sumOut = parseFloat(sumOutArr[i]).toFixed(2);
//                            console.log(sumIn + "|" + colsTdInArr[0]);
                            if (i < 12) {
                                if (parseFloat(sumIn) >= parseFloat(colsTdInArr[0])) {
//                                    console.log(1);
                                    colsTdInArr[1] = colsTdInArr[0];
                                    colsTdInArr[4] = colsTdInArr[3];
                                    colsTdInArr[0] = sumIn;
                                    colsTdInArr[3] = i + 2;
                                } else if (parseFloat(sumIn) >= parseFloat(colsTdInArr[1])) {
//                                    console.log(2);
                                    colsTdInArr[2] = colsTdInArr[1];
                                    colsTdInArr[5] = colsTdInArr[4];
                                    colsTdInArr[1] = sumIn;
                                    colsTdInArr[4] = i + 2;
                                } else if (parseFloat(sumIn) >= parseFloat(colsTdInArr[2])) {
//                                    console.log(3);
                                    colsTdInArr[2] = sumIn;
                                    colsTdInArr[5] = i + 2;
                                }
//                                console.log(colsTdInArr);
                                if (parseFloat(sumOut) >= parseFloat(colsTdOutArr[0])) {
                                    colsTdOutArr[1] = colsTdOutArr[0];
                                    colsTdOutArr[4] = colsTdOutArr[3];
                                    colsTdOutArr[0] = sumOut;
                                    colsTdOutArr[3] = i + 2;
                                } else if (parseFloat(sumOut) >= parseFloat(colsTdOutArr[1])) {
                                    colsTdOutArr[2] = colsTdOutArr[1];
                                    colsTdOutArr[5] = colsTdOutArr[4];
                                    colsTdOutArr[1] = sumOut;
                                    colsTdOutArr[4] = i + 2;
                                } else if (parseFloat(sumOut) >= parseFloat(colsTdOutArr[2])) {
                                    colsTdOutArr[2] = sumOut;
                                    colsTdOutArr[5] = i + 2;
                                }
                            }
                            tdInArr.eq(i + 1).html(sumIn);
                            tdOutArr.eq(i + 1).html(sumOut);
                        }
//                        console.log(colsTdInArr);
                        $("#inTable tr td:nth-child(" + colsTdInArr[3] + ")").css('color', 'red');
                        $("#inTable tr td:nth-child(" + colsTdInArr[4] + ")").css('color', 'blue');
                        $("#inTable tr td:nth-child(" + colsTdInArr[5] + ")").css('color', 'green');

                        $("#outTable tr td:nth-child(" + colsTdOutArr[3] + ")").css('color', 'red');
                        $("#outTable tr td:nth-child(" + colsTdOutArr[4] + ")").css('color', 'blue');
                        $("#outTable tr td:nth-child(" + colsTdOutArr[5] + ")").css('color', 'green');
                    }
                }
            });
        }

    </script>
</head>
<body>
<div style="margin: 0px auto;width: 95%;text-align: center;">
    <!-- Table goes in the document BODY -->
    <table class="imagetable" id="inTable" style="margin: auto;width: 100%">
        <tr>
            <th>收入</th>
            <th>一月</th>
            <th>二月</th>
            <th>三月</th>
            <th>四月</th>
            <th>五月</th>
            <th>六月</th>
            <th>七月</th>
            <th>八月</th>
            <th>九月</th>
            <th>十月</th>
            <th>十一月</th>
            <th>十二月</th>
            <th>全部</th>
        </tr>
        <tr>
            <td>合计</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
        </tr>
    </table>
    <br/>
    <hr/>
    <br/>
    <table class="imagetable" id="outTable" style="margin: auto;width: 100%;">
        <tr>
            <th>支出</th>
            <th>一月</th>
            <th>二月</th>
            <th>三月</th>
            <th>四月</th>
            <th>五月</th>
            <th>六月</th>
            <th>七月</th>
            <th>八月</th>
            <th>九月</th>
            <th>十月</th>
            <th>十一月</th>
            <th>十二月</th>
            <th>全部</th>
        </tr>
        <tr>
            <td>合计</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
            <td>0</td>
        </tr>
    </table>
</div>
</body>
</html>
