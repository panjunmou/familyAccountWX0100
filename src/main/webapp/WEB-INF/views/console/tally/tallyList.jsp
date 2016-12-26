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
                        sortName: 'createDate',
                        sortOrder: 'desc',
                        pageSize: 10,
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
                                    align: "center"
                                },
                                {
                                    width: '200 ',
                                    title: '说明',
                                    field: 'remark',
                                    align: "center"
                                },
                                {
                                    width: '100',
                                    title: '金额',
                                    field: 'money',
                                    align: "center"
                                },
                                {
                                    width: '100',
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
                                    width: '100',
                                    title: '类型',
                                    field: 'purposeType',
                                    align: "center",
                                    formatter: function (value, row, index) {
                                        if(value == "-1"){
                                            return "<span style='color: green;'>支出</span>";
                                        }else{
                                            return "<span style='color: orange;'>收入</span>";
                                        }
                                    }
                                },
                                {
                                    width: '100',
                                    title: '状态',
                                    field: 'visible',
                                    align: "center",
                                    formatter: function (value, row, index) {
                                        if(value){
                                            return "<span style='color: green;'>启用</span>";
                                        }else{
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
                                        return unixTimestamp.toLocaleString("yyyy-MM-dd");
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
                                        if(row.visible){
                                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                            str += $
                                                .formatString(
                                                    '<a href="javascript:void(0)" onclick="changeFun(\'{0}\',-1);" >禁用</a>', row.id);
                                        }else{
                                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                            str += $
                                                .formatString(
                                                    '<a href="javascript:void(0)" onclick="changeFun(\'{0}\',1);" >启用</a>', row.id);
                                        }
                                        return str;
                                    }
                                }
                            ]],
                        toolbar: '#toolbar'
                    });
        });

        function changeFun(id,status) {
            parent.$.messager.confirm('询问', '您是否要变更当前状态？', function (b) {
                if (b) {
                    progressLoad();
                    $.post('${ctx}/console/tally/changeStatus', {
                        id: id,
                        status:status
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
            console.log('${param.title}');
            self.parent.addTab({
                url: '${ctx}/console/tally/addPage?title=${param.title}',
                title: "新增账单",
                iconCls: "icon-add"
            });
        }
        function updateFun(id) {
        }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',border:true">
    <table id="dataGrid" data-options="fit:true,border:false"></table>
</div>
<div id="toolbar" style="display: none;">
    <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
       data-options="plain:true,iconCls:'icon-add'">新建</a>
</div>
</body>
</html>