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
            font-weight: bold;
        }

        .imageTD {
            /*background: #dcddc0 url('/style/images/cell-grey3.jpg') !important;
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #999999;*/
            font-weight: normal !important;
        }

    </style>
    <link href="${ctx}/js/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${ctx}/js/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="${ctx}/js/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
    <script type="text/javascript">
        $(function () {
            loadData();

            $('.form_date').datetimepicker({
                autoclose: 1,
                todayHighlight: 1,
                startView: 4,
                minView: 4,
                format: "yyyy"
            });
        });

        function sortNumber(a, b) {
            return b - a;
        }

        function loadData() {
            $("#inTable tr:gt(0)").remove();
            var tr = $('<tr/>');
            var td = $('<td/>');
            td.html('合计');
            tr.append(td);
            for(var i=0;i<13;i++){
                var td1 = $('<td/>');
                td1.html('0');
                tr.append(td1);
            }
            $("#inTable tr:eq(0)").after(tr);
            $("#outTable tr:gt(0)").remove();
            var tr = $('<tr/>');
            var td = $('<td/>');
            td.html('合计');
            tr.append(td);
            for(var i=0;i<13;i++){
                var td1 = $('<td/>');
                td1.html('0');
                tr.append(td1);
            }
            $("#outTable tr:eq(0)").after(tr);
            var yearVal = $("#yearVal").val();
            $.ajax({
                type: "get",
                url: "${ctx}/console/report/inAndOutList",
                dataType: "json",
                data: {
                    year: yearVal
                },
                success: function (result) {
//                    console.log(result);
                    if (result.success) {
                        var inList = result.obj.inList;
                        var outList = result.obj.outList;
//                        console.log(outList);
                        var sumInArr = new Array();
                        var sumOutArr = new Array();
                        for (var i = 0; i < 13; i++) {
                            sumInArr[i] = 0;
                            sumOutArr[i] = 0;
                        }
                        var tdInArr = $("#inTable tr:last").find('td');
                        var tdOutArr = $("#outTable tr:last").find('td');
                        $.each(inList, function (i, v) {
                            var moneyArr = v.money;
                            var tr = $('<tr style="cursor: pointer"/>');
                            var td = $('<td/>');
                            td.html(v.purposeName);
                            tr.append(td);
                            var sum = parseFloat(0);
                            var sortMoneyArr = new Array();
                            sortMoneyArr = sortMoneyArr.concat(moneyArr);
                            sortMoneyArr.sort(sortNumber);
                            $.each(moneyArr, function (m, n) {
                                sumInArr[m] = sumInArr[m] + parseFloat(n);
                                sum = sum + parseFloat(n);
                                td = $('<td/>');
                                if (parseFloat(n) == parseFloat(sortMoneyArr[0])) {
                                    td.css("color", "red");
                                } else if (parseFloat(n) == parseFloat(sortMoneyArr[1])) {
                                    td.css("color", "blue");
                                } else if (parseFloat(n) == parseFloat(sortMoneyArr[2])) {
                                    td.css("color", "green");
                                }
                                td.html(n);
                                tr.append(td);
                            });
                            td = $('<td/>');
                            td.html(sum);
                            tr.append(td);
                            $("#inTable tr:eq(" + i + ")").after(tr);
                            tr.on("click", function () {
                                var trP = $(this);
                                var hasChildren = $(this).attr("hasChildren");
                                if (hasChildren == undefined) {
                                    $(this).attr("hasChildren", "1");
                                    hasChildren = "1";
                                } else {
                                    if (hasChildren == "1") {
                                        hasChildren = "-1";
                                        $(this).attr("hasChildren", "-1");
                                    } else {
                                        hasChildren = "1";
                                        $(this).attr("hasChildren", "1");
                                    }
                                }
                                if (hasChildren == "1") {
                                    $.ajax({
                                        type: "get",
                                        url: "${ctx}/console/report/childrenTableList",
                                        dataType: "json",
                                        data: {
                                            parentPurposeNo: v.purposeNo
                                        },
                                        success: function (result2) {
                                            var objs = result2.obj;
                                            $.each(objs, function (childI, childV) {
                                                var moneyArrC = childV.money;
                                                var trC = $('<tr type="' + v.purposeNo + '"/>');
                                                var tdC = $('<td class="imageTD" style="padding-left: 50px;"/>');
                                                tdC.html(childV.purposeName);
                                                trC.append(tdC);
                                                var sumC = parseFloat(0);
                                                var sortMoneyArrC = new Array();
                                                sortMoneyArrC = sortMoneyArrC.concat(moneyArrC);
                                                sortMoneyArrC.sort(sortNumber);
                                                $.each(moneyArrC, function (mC, nC) {
                                                    tdC = $('<td class="imageTD"/>');
                                                    if (parseFloat(nC) == parseFloat(sortMoneyArrC[0])) {
                                                        tdC.css("color", "red");
                                                    } else if (parseFloat(nC) == parseFloat(sortMoneyArrC[1])) {
                                                        tdC.css("color", "blue");
                                                    } else if (parseFloat(nC) == parseFloat(sortMoneyArrC[2])) {
                                                        tdC.css("color", "green");
                                                    }
                                                    tdC.html(parseFloat(nC).toFixed(2));
                                                    trC.append(tdC);
                                                    sumC = sumC + parseFloat(nC);
                                                });
                                                tdC = $('<td class="imageTD"/>');
                                                tdC.html(parseFloat(sumC).toFixed(2));
                                                trC.append(tdC);
                                                trP.after(trC);
                                            });
                                        }
                                    });
                                } else {
                                    $("[type='" + v.purposeNo + "']").remove();
                                }
                            });
                            sumInArr[12] = sumInArr[12] + parseFloat(sum);
                        });
                        $.each(outList, function (i, v) {
                            var moneyArr = v.money;
                            var tr = $('<tr style="cursor:pointer;"/>');
                            var td = $('<td/>');
                            td.html(v.purposeName);
                            tr.append(td);
                            var sum = parseFloat(0);
                            var sortMoneyArr = new Array();
                            sortMoneyArr = sortMoneyArr.concat(moneyArr);
                            sortMoneyArr.sort(sortNumber);
                            $.each(moneyArr, function (m, n) {
                                sumOutArr[m] = sumOutArr[m] + parseFloat(n);
                                sum = sum + parseFloat(n);
                                td = $('<td/>');
                                if (parseFloat(n) == parseFloat(sortMoneyArr[0])) {
                                    td.css("color", "red");
                                } else if (parseFloat(n) == parseFloat(sortMoneyArr[1])) {
                                    td.css("color", "blue");
                                } else if (parseFloat(n) == parseFloat(sortMoneyArr[2])) {
                                    td.css("color", "green");
                                }
                                td.html(n);
                                tr.append(td);
                            });
                            td = $('<td/>');
                            td.html(parseFloat(sum).toFixed(2));
                            tr.append(td);
                            $("#outTable tr:eq(" + i + ")").after(tr);
                            tr.on("click", function () {
                                var trP = $(this);
                                var hasChildren = $(this).attr("hasChildren");
                                if (hasChildren == undefined) {
                                    $(this).attr("hasChildren", "1");
                                    hasChildren = "1";
                                } else {
                                    if (hasChildren == "1") {
                                        hasChildren = "-1";
                                        $(this).attr("hasChildren", "-1");
                                    } else {
                                        hasChildren = "1";
                                        $(this).attr("hasChildren", "1");
                                    }
                                }
                                if (hasChildren == "1") {
                                    $.ajax({
                                        type: "get",
                                        url: "${ctx}/console/report/childrenTableList",
                                        dataType: "json",
                                        data: {
                                            parentPurposeNo: v.purposeNo
                                        },
                                        success: function (result2) {
                                            console.log(result2);
                                            var objs = result2.obj;
                                            $.each(objs, function (childI, childV) {
                                                var moneyArrC = childV.money;
                                                var trC = $('<tr type="' + v.purposeNo + '"/>');
                                                var tdC = $('<td class="imageTD" style="padding-left: 50px;"/>');
                                                tdC.html(childV.purposeName);
                                                trC.append(tdC);
                                                var sumC = parseFloat(0);
                                                var sortMoneyArrC = new Array();
                                                sortMoneyArrC = sortMoneyArrC.concat(moneyArrC);
                                                sortMoneyArrC.sort(sortNumber);
                                                $.each(moneyArrC, function (mC, nC) {
                                                    tdC = $('<td class="imageTD"/>');
                                                    if (parseFloat(nC) == parseFloat(sortMoneyArrC[0])) {
                                                        tdC.css("color", "red");
                                                    } else if (parseFloat(nC) == parseFloat(sortMoneyArrC[1])) {
                                                        tdC.css("color", "blue");
                                                    } else if (parseFloat(nC) == parseFloat(sortMoneyArrC[2])) {
                                                        tdC.css("color", "green");
                                                    }
                                                    tdC.html(parseFloat(nC).toFixed(2));
                                                    trC.append(tdC);
                                                    sumC = sumC + parseFloat(nC);
                                                });
                                                tdC = $('<td class="imageTD"/>');
                                                tdC.html(parseFloat(sumC).toFixed(2));
                                                trC.append(tdC);
                                                var trr = trP;
                                                for (var tt = 0; tt < childI; tt++) {
                                                    trr = trr.next();
                                                }
                                                trr.after(trC);
                                            });
                                        }
                                    });
                                } else {
                                    $("[type='" + v.purposeNo + "']").remove();
                                }
                            });
                            sumOutArr[12] = sumOutArr[12] + parseFloat(sum);
                        });
                        var sortSumInArr = new Array();
                        var sortSumOutArr = new Array();
                        sortSumInArr = sortSumInArr.concat(sumInArr);
                        sortSumOutArr = sortSumOutArr.concat(sumOutArr);
                        sortSumInArr[12] = 0;
                        sortSumOutArr[12] = 0;
                        sortSumInArr.sort(sortNumber);
                        sortSumOutArr.sort(sortNumber);
                        for (var i = 0; i < 13; i++) {
                            var sumIn = parseFloat(sumInArr[i]).toFixed(2);
                            var sumOut = parseFloat(sumOutArr[i]).toFixed(2);
                            if (i < 12) {
                                if (parseFloat(sumInArr[i]).toFixed(2) == parseFloat(sortSumInArr[0]).toFixed(2)) {
                                    tdInArr.eq(i + 1).css("color", "red");
                                } else if (parseFloat(sumInArr[i]).toFixed(2) == parseFloat(sortSumInArr[1]).toFixed(2)) {
                                    tdInArr.eq(i + 1).css("color", "blue");
                                } else if (parseFloat(sumInArr[i]).toFixed(2) == parseFloat(sortSumInArr[2]).toFixed(2)) {
                                    tdInArr.eq(i + 1).css("color", "green");
                                }
                                if (parseFloat(sumOutArr[i]).toFixed(2) == parseFloat(sortSumOutArr[0]).toFixed(2)) {
                                    tdOutArr.eq(i + 1).css("color", "red");
                                } else if (parseFloat(sumOutArr[i]).toFixed(2) == parseFloat(sortSumOutArr[1]).toFixed(2)) {
                                    tdOutArr.eq(i + 1).css("color", "blue");
                                } else if (parseFloat(sumOutArr[i]).toFixed(2) == parseFloat(sortSumOutArr[2]).toFixed(2)) {
                                    tdOutArr.eq(i + 1).css("color", "green");
                                }
                            }
                            tdInArr.eq(i + 1).html(sumIn);
                            tdOutArr.eq(i + 1).html(sumOut);
                        }

                        var tds = $("#inTable tr td:nth-child(13)");
                    }
                }
            });
        }

    </script>
</head>
<body>
<div class="container">
    <form action="" class="form-horizontal"  role="form">
        <fieldset>
            <div class="form-group">
                <label for="dtp_input2" class="col-md-2 control-label">请选择年份</label>
                <div class="input-group date form_date col-md-5" data-date="" data-date-format="dd MM yyyy" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input class="form-control" type="text" value="${nowYear}" readonly id="yearVal">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-search" onclick="loadData()"></span></span>
                </div>
                <input type="hidden" id="dtp_input2" value="" /><br/>
            </div>
        </fieldset>
    </form>
</div>
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
