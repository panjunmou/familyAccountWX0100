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
    <title>用户列表</title>
    <script type="text/javascript">
        var dataGrid;
        $(function () {
            dataGrid = $('#dataGrid')
                .datagrid(
                    {
                        url: '${ctx}/console/user/dataGrid',
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
                                    width: '100',
                                    title: '姓名',
                                    field: 'id',
                                    align: "center",
                                    hidden: true
                                },
                                {
                                    width: '150',
                                    title: '创建时间',
                                    field: 'createDate',
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
                                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                        str += $
                                            .formatString(
                                                '<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>',
                                                row.id);
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
                    $.post('${ctx}/console/user/delete', {
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

        function addFun(id) {
            self.parent.addTab({
                url: '${ctx}/console/user/addPage?id=' + id + "&title=${param.title}",
                title: "新增任务",
                iconCls: "icon-add"
            });
        }
        function updateFun(id) {
            self.parent.addTab({
                url: '${ctx}/console/user/editPage?id=' + id + "&title=${param.title}",
                title: "修改任务",
                iconCls: "icon-edit"
            });
        }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'north',border:false" class="divContent0">
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