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
    <title>用途列表</title>
    <script type="text/javascript">
        var dataGrid;
        $(function () {
            var pId = '${pId}';
            dataGrid = $('#dataGrid')
                .datagrid(
                    {
                        url: '${ctx}/console/purpose/dataGrid?pId=' + pId,
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
                                    title: '用途编号',
                                    field: 'purposeNo',
                                    align: "center"
                                },
                                {
                                    width: '100',
                                    title: '用途名称',
                                    field: 'name',
                                    align: "center"
                                },
                                {
                                    width: '50',
                                    title: '排序',
                                    field: 'seq',
                                    align: "center"
                                },
                                {
                                    width: '100',
                                    title: '是否启用',
                                    field: 'visible',
                                    align: "center",
                                    formatter: function (value, row, index) {
                                        if(value) {
                                            return "<span style='color: green;'>是</span>";
                                        }else {
                                            return "<span style='color: red;'>否</span>";
                                        }
                                    }
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
                                        if(!row.hasParent){
                                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                                            str += $
                                                .formatString(
                                                    '<a href="javascript:void(0)" onclick="subFun(\'{0}\');" >子集</a>',
                                                    row.id);
                                        }
                                        return str;
                                    }
                                }
                            ]],
                        toolbar: '#toolbar'
                    });
        });

        function subFun(id) {
            self.parent.addTab({
                url : '${ctx}/console/purpose/manager?pId=' + id + "&title=${param.title}",
                title : "用途子集",
                iconCls : "icon-add"
            });
        }
        function changeFun(id,status) {
            parent.$.messager.confirm('询问', '您是否要变更当前状态？', function (b) {
                if (b) {
                    progressLoad();
                    $.post('${ctx}/console/purpose/changeStatus', {
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
            parent.$.modalDialog({
                title: '新建用途',
                width: 500,
                height: 300,
                href: '${ctx}/console/purpose/addPage?pId=${pId}',
                buttons: [{
                    text: '保存',
                    handler: function () {
                        parent.$.modalDialog.openner_treeGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#addForm');
                        f.submit();
                    }
                }, {
                    text: '取消',
                    handler: function () {
                        parent.$.modalDialog.handler.dialog('close');
                    }
                }]
            });
        }
        function updateFun(id) {
            parent.$.modalDialog({
                title: '新建用途',
                width: 500,
                height: 300,
                href: '${ctx}/console/purpose/editPage?id=' + id,
                buttons: [{
                    text: '修改',
                    handler: function () {
                        parent.$.modalDialog.openner_treeGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#editForm');
                        f.submit();
                    }
                }, {
                    text: '取消',
                    handler: function () {
                        parent.$.modalDialog.handler.dialog('close');
                    }
                }]
            });
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