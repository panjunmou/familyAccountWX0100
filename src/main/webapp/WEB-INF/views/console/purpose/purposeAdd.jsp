<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
    $(function() {

        $('#addForm').form({
            url : '${ctx}/console/purpose/save',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_treeGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为organization.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                }
            }
        });

    });
</script>
<style type="text/css">
    .textbox-invalid {
        border: solid 1px #D3D3D3;
        background-color: white;
    }

    .validatebox-invalid {
        border: solid 1px #D3D3D3;
        background-color: white;
    }

    .numberbox {
        border: solid 1px #D3D3D3;
        background-color: white;
    }

    .require-star {
        color: red;
    }
</style>
<div style="padding: 3px;">
    <form id="addForm" method="post">
        <table class="grid">
            <tr>
                <td>
                    <span class="require-star">*</span>
                    编号
                </td>
                <td colspan="3">
                    ${purposeNo}
                    <input type="hidden" value="${purposeNo}" name="purposeNo"/>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="require-star">*</span>
                    类型
                </td>
                <td>
                    <select name="purposeType" class="easyui-combobox" id="purposeType"
                            data-options="width:150,height:20,editable:false,panelHeight:'auto'">
                        <option value="-1">支出</option>
                        <option value="1">收入</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <span class="require-star">*</span>
                    用途名称
                </td>
                <td>
                    <input name="name" style="width: 300px;" maxlength="80" />
                </td>
            </tr>
            <tr>
                <td>
                    <span class="require-star">*</span>
                    排序
                </td>
                <td>
                    <input name="seq" style="width: 300px;"/>
                </td>
            </tr>
        </table>
        <input type="hidden" value="${pId}" name="pId"/>
    </form>
</div>