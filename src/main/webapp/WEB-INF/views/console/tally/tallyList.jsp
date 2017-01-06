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
    <title>账单列表</title>
    <script type="text/javascript">
        var dataGrid;
        $(function () {
            dataGrid = $('#dataGrid')
                .datagrid(
                    {
                        url: '${ctx}/console/tally/dataGrid',
                        fit: true,
                        striped: true,
                        rownumbers: true,
                        pagination: true,
                        singleSelect: true,
                        idField: 'id',
                        sortName: 'payDate',
                        sortOrder: 'desc',
                        pageSize: 50,
                        pageList: [10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
                        columns: [
                            [
                                {
                                    width: '100',
                                    title: 'ID',
                                    field: 'id',
                                    align: "center",
                                    hidden: true
                                },
                                {
                                    width: '150 ',
                                    title: '账单编号',
                                    field: 'tallyNo',
                                    align: "center",
                                    hidden: true
                                },
                                {
                                    width: '200 ',
                                    title: '说明',
                                    field: 'remark',
                                    align: "center"
                                },
                                {
                                    width: '80',
                                    title: '金额',
                                    field: 'money',
                                    align: "center"
                                },
                                {
                                    width: '80',
                                    title: '账户',
                                    field: 'accountName',
                                    align: "center"
                                },
                                {
                                    width: '150',
                                    title: '使用者',
                                    field: 'payUserNames',
                                    align: "center"
                                },
                                {
                                    width: '150',
                                    title: '用途',
                                    field: 'purposeName',
                                    align: "center"
                                },
                                {
                                    width: '50',
                                    title: '类型',
                                    field: 'purposeType',
                                    align: "center",
                                    formatter: function (value, row, index) {
                                        if (value == "-1") {
                                            return "<span style='color: green;'>支出</span>";
                                        } else {
                                            return "<span style='color: orange;'>收入</span>";
                                        }
                                    }
                                },
                                {
                                    width: '50',
                                    title: '状态',
                                    field: 'visible',
                                    align: "center",
                                    formatter: function (value, row, index) {
                                        if (value) {
                                            return "<span style='color: green;'>启用</span>";
                                        } else {
                                            return "<span style='color: red;'>禁用</span>";
                                        }
                                    }
                                },
                                {
                                    width: '150',
                                    title: '账单时间',
                                    field: 'payDate',
                                    align: "center",
                                    sortable: true,
                                    formatter: function (value, row, index) {
                                        var unixTimestamp = new Date(value);
                                        return unixTimestamp.toLocaleString("yyyy-MM-dd hh:mm:ss");
                                    }
                                },
                                {
                                    field: 'action',
                                    title: '操作',
                                    width: 200,
                                    align: "center",
                                    formatter: function (value, row, index) {
                                        var str = '';
                                        str += $.formatString(
                                            '<a href="javascript:void(0)" onclick="updateFun(\'{0}\');" >修改</a>',
                                            row.id);
                                        if (row.visible) {
                                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                            str += $
                                                .formatString(
                                                    '<a href="javascript:void(0)" onclick="changeFun(\'{0}\',-1);" >禁用</a>', row.id);
                                        } else {
                                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                            str += $
                                                .formatString(
                                                    '<a href="javascript:void(0)" onclick="changeFun(\'{0}\',1);" >启用</a>', row.id);
                                        }
                                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                        str += $
                                            .formatString(
                                                '<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                                        return str;
                                    }
                                }
                            ]],
                        toolbar: '#toolbar'
                    });
        });

        function deleteFun(id) {
            parent.$.messager.confirm('询问', '您是否要删除当前数据？', function (b) {
                if (b) {
                    progressLoad();
                    $.post('${ctx}/console/tally/delete', {
                        id: id
                    }, function (result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            dataGrid.datagrid('reload');
                        }
                        progressClose();
                    }, 'JSON');
                }
            });
        }

        function changeFun(id, status) {
            parent.$.messager.confirm('询问', '您是否要变更当前状态？', function (b) {
                if (b) {
                    progressLoad();
                    $.post('${ctx}/console/tally/changeStatus', {
                        id: id,
                        status: status
                    }, function (result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            dataGrid.datagrid('reload');
                        }
                        progressClose();
                    }, 'JSON');
                }
            });
        }

        function addFun() {
            self.parent.addTab({
                url: '${ctx}/console/tally/addPage?title=${param.title}',
                title: "新增账单",
                iconCls: "icon-add"
            });
        }
        function updateFun(id) {
            self.parent.addTab({
                url: '${ctx}/console/tally/editPage?id=' + id + '&title=${param.title}',
                title: "修改账单",
                iconCls: "icon-edit"
            });
        }
        function searchFun() {
           /* var createDateStart = $('#createDateStart').val();
            var createDateEnd = $("#createDateEnd").val();
            var paramMap = {};
            if(!isEmpty(createDateStart)){
                createDateStart = createDateStart + " 00:00:00";
                paramMap['createDateStart'] = createDateStart;
            }
            if(!isEmpty(createDateEnd)){
                createDateEnd = createDateEnd + " 23:59:59";
                paramMap['createDateEnd'] = createDateEnd;
            }*/
            var visible = $("#visible").combobox('getValue');
            if (visible != null || visible != "" || visible != undefined) {
                $("[name='status']").val('true');
            }
            dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
        }
        function cleanFun() {
            $('#searchForm input').val('');
            dataGrid.datagrid('load', {});
        }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'north',border:false" class="divContent0">
    <form id="searchForm">
        <table class="tabContent0">
            <tr>
                <th width="10%" align="right">账单编号：</th>
                <td width="20%" align="left">
                    <input name="tallyNo" placeholder="请输入账单编号"/>
                </td>
                <th width="10%" align="right">说明：</th>
                <td width="20%" align="left">
                    <input name="remark" placeholder="请输入说明"/>
                </td>
                <th width="10%" align="right">状态：</th>
                <td width="20%" align="left">
                    <select id="visible" name="visible" class="easyui-combobox"
                            data-options="width:150,height:20,editable:false,panelHeight:'auto'">
                        <option value="">请选择状态...</option>
                        <option value="false">禁用</option>
                        <option value="true">启用</option>
                    </select>
                </td>

            </tr>
            <tr>
                <th width="10%" align="right">类型：</th>
                <td width="20%" align="left">
                    <select name="purposeType" class="easyui-combobox"
                            data-options="width:150,height:20,editable:false,panelHeight:'auto'">
                        <option value="">请选择类型...</option>
                        <option value="-1">支出</option>
                        <option value="1">收入</option>
                    </select>
                </td>
                <th width="10%" align="right">用途(父级)：</th>
                <td width="20%" align="left">
                </td>
                <th width="10%" align="right">用途(子级)：</th>
                <td width="20%" align="left">
                </td>
            </tr>
            <tr>
                <th width="10%" align="right">账户：</th>
                <td width="20%" align="left">
                    <select name="accountId" class="easyui-combobox"
                            data-options="width:150,height:20,editable:false,panelHeight:'auto'">
                        <option value="">请选择账户...</option>
                        <c:forEach items="${accountList}" var="account">
                            <option value="${account.value}">${account.title}</option>
                        </c:forEach>
                    </select>
                </td>
                <th width="10%" align="right">使用者：</th>
                <td width="20%" align="left">
                    <select name="payUserId" class="easyui-combobox"
                            data-options="width:150,height:20,editable:false,panelHeight:'auto'">
                        <option value="">请选择使用者...</option>
                        <c:forEach items="${payUserList}" var="payUser">
                            <option value="${payUser.value}">${payUser.title}</option>
                        </c:forEach>
                    </select>
                </td>

                <th width="10%" align="right">金额：</th>
                <td width="20%" align="left">
                    <input name="moneyFrom" type="text" placeholder="请输入金额从"
                           class="easyui-numberbox" data-options="required:false,min:0,precision:0"
                           style="width: 80px"/>
                    至
                    <input name="moneyTo" type="text" placeholder="请输入金额到"
                           class="easyui-numberbox" data-options="required:false,min:0,precision:0"
                           style="width: 80px"/>
                </td>
            </tr>
            <tr>
                <th width="10%" align="right">账单日期：</th>
                <td width="60%" align="left" colspan="4">
                    <input name="createDateStart"
                           class="easyui-datebox"
                           placeholder="点击选择日期"
                           editable="false"
                           type="text"
                    />
                    至
                    <input name="createDateEnd"
                           class="easyui-datebox"
                           placeholder="点击选择日期"
                           editable="false"
                           type="text"
                    />
                </td>
                <td width="20%" align="left">
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-search',plain:true" onclick="searchFun('searchProductForm');">
                        查询 </a>
                    <a href="javascript:void(0);" class="easyui-linkbutton"
                       data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun('searchProductForm');">
                        清空 </a>
                </td>
            </tr>
        </table>
        <div>
            <input id="page" name="page" type="hidden"/>
            <input id="pageSize" name="pageSize" type="hidden"/>
            <input id="sort" name="sort" type="hidden"/>
            <input id="order" name="order" type="hidden"/>
            <input name="status" type="hidden"/>
        </div>
    </form>
</div>
<div data-options="region:'center',border:true">
    <table id="dataGrid" data-options="fit:true,border:false"></table>
</div>
<div id="toolbar" style="display: none;">
    <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
       data-options="plain:true,iconCls:'icon-add'">新建</a>
</div>
</body>
</html>