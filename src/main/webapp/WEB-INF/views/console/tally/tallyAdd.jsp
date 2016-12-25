<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../inc.jsp"></jsp:include>
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>新增账单</title>
    <style type="text/css">
        .tdTitle2 {
            text-align: left;
            font-size: 14px;
            color: #000000;
            width: 15%;
        }

        .tdTitle3 {
            text-align: center;
            font-size: 14px;
            color: #000000;
            width: 15%;
        }
    </style>
    <script type="text/javascript">
        var purposeInList;
        var purposeInMap = {};
        var purposeOutList;
        var purposeOutMap = {};
        function save() {
            var f = $('#addForm');
            f.attr("action", "${ctx}/console/tally/add");
            f.submit();
        }
        $(function () {
            $('#addForm').form({
                onSubmit: function () {
                    progressLoad();
                    var isValid = $(this).form('validate');
                    if (!isValid) {
                        progressClose();
                    }
                    return isValid;
                },
                success: function (result) {
                    progressClose();
                    result = $.parseJSON(result);
                    if (result.success) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        self.parent.closeTab("新增账单", "${param.title}");
                    } else {
                        parent.$.messager.alert('提示', result.msg, 'error');
                    }
                }
            });

            var typeClick = $("[name='purposeType']:checked").val();
            if (typeClick == "-1") {
                $("[type='out']").hide();
                $("[type='in']").show();
            } else {
                $("[type='out']").show();
                $("[type='in']").hide();
            }

            $("[name='purposeType']").click(function () {
                var click = $("[name='purposeType']:checked").val();
                if (click == "-1") {
                    $("[type='out']").hide();
                    $("[type='in']").show();
                } else {
                    $("[type='out']").show();
                    $("[type='in']").hide();
                }
            });

            $.ajax({
                type: "get",
                url: "${ctx}/console/tally/purposeInList",
                dataType: "json",
                success: function (result) {
                    purposeInList = result.obj;
                    console.log(purposeInList);
                    var data = [];
                    $.each(purposeInList, function (i, v) {
                        data.push(
                            {
                                "text": v.name,
                                "value": v.id
                            });
                        purposeInMap[v.id] = v;
                    });
                    $("#parentPurposeIn").combobox("loadData", data);
                    console.log(purposeInMap);
                }
            });

            $.ajax({
                type: "get",
                url: "${ctx}/console/tally/purposeOutList",
                dataType: "json",
                success: function (result) {
                    purposeOutList = result.obj;
                    console.log(purposeOutList);
                    var data = [];
                    $.each(purposeOutList, function (i, v) {
                        data.push(
                            {
                                "text": v.name,
                                "value": v.id
                            });
                        purposeOutMap[v.id] = v;
                    });
                    $("#parentPurposeOut").combobox("loadData", data);
                    console.log(purposeOutMap);
                }
            });

            $("#parentPurposeIn").combobox({
                onChange: function () {
                    var changeVal = $("#parentPurposeIn").combobox('getValue');
                    var obj = purposeInMap[changeVal];
                    console.log(obj);
                    var data = [];
                    var subList = obj.sub;
                    $.each(subList, function (i, v) {
                        data.push(
                            {
                                "text": v.name,
                                "id": v.id
                            });
                    });
                    $("#subPurposeIn").combobox("loadData", data);
                }
            });

            $("#parentPurposeOut").combobox({
                onChange: function () {
                    var changeVal = $("#parentPurposeOut").combobox('getValue');
                    var obj = purposeOutMap[changeVal];
                    console.log(obj);
                    var data = [];
                    var subList = obj.sub;
                    $.each(subList, function (i, v) {
                        data.push(
                            {
                                "text": v.name,
                                "id": v.id
                            });
                    });
                    $("#subPurposeOut").combobox("loadData", data);
                }
            });

        });
    </script>
</head>
<body data-options="fit:true,border:false">
<div data-options="fit:true,border:false" class="divContent1">
    <div data-options="region:'center',border:false" class="divContent2">
        <form id="addForm" method="post">
            <table class="grid">
                <tr>
                    <td class="tdTitle">金额：</td>
                    <td class="tdContent2">
                        <input name="money" type="text" class="easyui-numberbox"
                               data-options="required:true"/>
                    </td>
                    <td class="tdTitle">类型：</td>
                    <td class="tdContent2">
                        <input name="purposeType" type="radio" value="-1" checked/>支出
                        <input name="purposeType" type="radio" value="1"/>收入
                    </td>
                </tr>
                <tr>
                    <td class="tdTitle">日期：</td>
                    <td>
                        <input value="${today}" name="payDate"
                               class="easyui-datebox" data-options="required:true,editable:false" placeholder="点击选择日期"
                               type="text">
                    </td>
                    <td class="tdTitle">账户：</td>
                    <td class="tdContent2">
                        <select name="" class="easyui-combobox"
                                data-options="width:150,height:20,editable:false,panelHeight:'auto'">
                            <c:forEach items="${accountList}" var="account">
                                <option value="${account.value}">${account.title}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr type="in">
                    <td class="tdTitle">用途(父级)：</td>
                    <td class="tdTitle2">
                        <select name="parentPurposeIn" class="easyui-combobox" id="parentPurposeIn"
                                data-options="width:150,height:20,editable:false,panelHeight:'auto'">
                        </select>
                    </td>
                    <td class="tdTitle">用途(子集)：</td>
                    <td class="tdTitle2">
                        <select name="subPurposeIn" class="easyui-combobox" id="subPurposeIn"
                                data-options="width:150,height:20,editable:false,panelHeight:'auto'">
                        </select>
                    </td>
                </tr>
                <tr type="out">
                    <td class="tdTitle">用途(父级)：</td>
                    <td class="tdTitle2">
                        <select name="parentPurposeOut" class="easyui-combobox" id="parentPurposeOut"
                                data-options="width:150,height:20,editable:false,panelHeight:'auto'">
                        </select>
                    </td>
                    <td class="tdTitle">用途(子集)：</td>
                    <td class="tdTitle2">
                        <select name="subPurposeOut" class="easyui-combobox" id="subPurposeOut"
                                data-options="width:150,height:20,editable:false,panelHeight:'auto'">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdTitle">消费者：</td>
                    <td class="tdTitle2" colspan="3">
                        <select name="" class="easyui-combobox"
                                data-options="width:400,height:20,editable:false,multiple:true,panelHeight:'auto'">
                            <c:forEach items="${payUserList}" var="payUser">
                                <option value="${payUser.value}">${payUser.title}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="tdTitle">备注：</td>
                    <td class="tdContent2" colspan="5">
                        <textarea name="content" rows="5" cols="100" maxlength="100" style="width: 500px;"></textarea>
                    </td>
                </tr>
            </table>
        </form>
        <div class="divBtn">
            <a onclick="save()" href="javascript:void(0);" class="easyui-linkbutton"
               data-options="iconCls:'icon-save',plain:true">保存</a>
        </div>
    </div>
</div>
</body>
</html>