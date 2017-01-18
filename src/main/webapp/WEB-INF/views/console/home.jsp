<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../inc.jsp"></jsp:include>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>主页</title>
    <script type="text/javascript">
        String.prototype.startWith = function (str) {
            if (str == null || str == "" || this.length == 0
                || str.length > this.length)
                return false;
            if (this.substr(0, str.length) == str)
                return true;
            else
                return false;
            return true;
        }
    </script>
    <script type="text/javascript">
        var index_layout;
        var index_tabs;
        var layout_west_tree;
        var layout_west_tree_url = '';
        var sessionInfo_userId = '${loginUserInfo.id}';
        if (sessionInfo_userId) {//如果没有登录,直接跳转到登录页面
            <%--layout_west_tree_url = '${ctx}/sys/resource/tree';--%>
        } else {
            window.location.href = '${ctx}/index/index';
        }

        function loadTree() {
            layout_west_tree = $('#layout_west_tree').tree(
                {
                    url: layout_west_tree_url,
                    parentField: 'pid',
                    lines: false,
                    onClick: function (node) {
                        if (node.attributes && node.attributes.url) {
                            var uri = node.attributes.url;
                            var url = "";
                            /*临时处理，访问报表链接处理*/
                            if (uri.startWith("http://")) {
                                url = node.attributes.url + "?title="
                                    + node.text;
                            } else {
                                url = ' ${ctx}' + node.attributes.url + "?title=" + node.text;
                            }
                            addTab({
                                url: url,
                                title: node.text,
                                iconCls: node.iconCls
                            });
                        }
                    }
                });
        }

        $(function () {
            index_layout = $('#index_layout').layout({
                fit: true
            });
            index_tabs = $('#index_tabs').tabs(
                {
                    fit: true,
                    border: false,
                    tools: [
                        {
                            iconCls: 'icon-home',
                            handler: function () {
                                index_tabs.tabs('select', 0);
                            }
                        },
                        {
                            iconCls: 'icon-refresh',
                            handler: function () {
                                var index = index_tabs.tabs('getTabIndex',
                                    index_tabs.tabs('getSelected'));
                                index_tabs.tabs('getTab', index).panel(
                                    'open').panel('refresh');
                            }
                        },
                        {
                            iconCls: 'icon-del',
                            handler: function () {
                                var index = index_tabs.tabs('getTabIndex',
                                    index_tabs.tabs('getSelected'));
                                var tab = index_tabs.tabs('getTab', index);
                                if (tab.panel('options').closable) {
                                    index_tabs.tabs('close', index);
                                }
                            }
                        }]
                });

            /*$.ajax({
             type: "post",
             url: layout_west_tree_url,
             dataType: "JSON",
             success: function (data) {
             var menu_accordion = $('#menu_accordion');
             $.each(data, function (i, v) {
             var content = "";
             var children = v.children;
             if (children != null && children.length > 0) {
             $.each(children, function (l, m) {
             var url = m.attributes.url + "?title=" + m.text;
             content += ' <div class="accordion-item" onclick="goPage(\'' + url + '\',\'' + m.text + '\')">' + m.text + '</div>';
             });
             }
             menu_accordion.accordion('add', {
             title: v.text,
             //                            iconCls: v.iconCls,
             content: content,
             selected: false
             });
             });
             }
             });*/
        });

        function goPage(url, text) {
            addTab({
                url: '${ctx}' + url,
                title: text
            });
        }

        function closeTab(closeTabName, refreshTabName) {
            //var currTab =  self.parent.$('#tabs').tabs('getSelected'); //获得当前tab
            var currTab = $('#index_tabs').tabs('getTab', refreshTabName);
            var url = $(currTab.panel('options').content).attr('src');
            $('#index_tabs')
                .tabs(
                    'update',
                    {
                        tab: currTab,
                        options: {
                            content: '<iframe src="'
                            + url
                            + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
                        }
                    });
            $("#index_tabs").tabs('close', closeTabName);
            $("#index_tabs").tabs('select', refreshTabName);
        }

        function refershParentTab(closeTabName, refreshTabName) {
            //var currTab =  self.parent.$('#tabs').tabs('getSelected'); //获得当前tab
            var currTab = $('#index_tabs').tabs('getTab', refreshTabName);
            var url = $(currTab.panel('options').content).attr('src');
            $('#index_tabs').tabs('update',
                {
                    tab: currTab,
                    options: {
                        content: '<iframe src="'
                        + url
                        + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
                    }
                });
            //             $("#index_tabs").tabs('close', closeTabName);
            //             $("#index_tabs").tabs('select', refreshTabName);
        }

        function getCurrentTab() {
            var currTab = $('#index_tabs').tabs('getSelected');
            var title = currTab.panel('options').title;
            return title;
        }

        function onlyCloseTab(closeTabName) {
            $("#index_tabs").tabs('close', closeTabName);
        }

        function addTab(params) {
            var iframe = '<iframe src="'
                + params.url
                + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
            var t = $('#index_tabs');
            var opts = {
                title: params.title,
                closable: true,
                iconCls: params.iconCls,
                content: iframe,
                border: false,
                fit: true
            };
            if (t.tabs('exists', opts.title)) {
                //modify by qianwei
                //t.tabs('select', opts.title);
                t.tabs('close', opts.title);
                t.tabs('add', opts);
            } else {
                t.tabs('add', opts);
            }
        }

        function logout() {
            $.messager.confirm('提示', '确定要退出?', function (r) {
                if (r) {
                    progressLoad();
                    $.post('${ctx}/login/logout', function (result) {
                        if (result.success) {
                            progressClose();
                            window.location.href = '${ctx}/index/index';
                        }
                    }, 'json');
                }
            });
        }

        function editUserPwd() {
            parent.$.modalDialog({
                title: '修改密码',
                width: 300,
                height: 250,
                href: '${ctx}/sys/user/editPwdPage',
                buttons: [{
                    text: '修改',
                    handler: function () {
                        var f = parent.$.modalDialog.handler
                            .find('#editUserPwdForm');
                        f.submit();
                    }
                }]
            });
        }

        function downloadfile(name) {
            var form = $("<form>"); //定义一个form表单
            form.attr('style', 'display:none'); //在form表单中添加查询参数
            form.attr('target', '');
            form.attr('method', 'post');
            form.attr('action', '${ctx}/common/download/' + name);
            $('body').append(form); //将表单放置在web中
            form.submit();
        }
    </script>
</head>
<style type="text/css">
    .accordion-item {
        background-color: #d9e7f8;
        border: solid 1px #c5d8f2;
        padding: 5px;
        margin-top: 1px;
        padding-left: 20px;
        cursor: pointer;
    }
</style>
<body>
<div id="loading"
     style="position: fixed; top: -50%; left: -50%; width: 200%; height: 200%; background: #fff; z-index: 100; overflow: hidden;">
    <img src="${ctx}/style/images/ajax-loader.gif"
         style="position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: auto;"/>
</div>
<div id="index_layout">
    <div data-options="region:'north',border:false" style="overflow: hidden;">
        <div id="header">
				<span style="float: right; padding-right: 20px;">
					欢迎， <b>${loginUserInfo.name}</b>&nbsp;&nbsp;
					<%--<a href="javascript:void(0)" onclick="editUserPwd()" style="color: #fff">修改密码</a>--%>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="logout()" style="color: #fff">安全退出</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
            <span class="header"></span>
        </div>
    </div>
    <div data-options="region:'west',split:true" title="菜单" style="width: 160px; overflow: hidden; overflow-y: auto;">
        <%-- <div class="well well-small" style="padding: 5px 5px 5px 5px;">
             <ul id="layout_west_tree"></ul>
         </div>--%>
        <div class="easyui-accordion" id="menu_accordion" style="width:160px;">
            <div title="系统管理" data-options="" style="padding:0;">
                <%--<div class="accordion-item" onclick="goPage('${ctx}/console/user/manager','用户管理')">用户管理</div>--%>
                <div class="accordion-item" onclick="goPage('/console/account/manager?title=账户管理','账户管理')">账户管理</div>
                <div class="accordion-item" onclick="goPage('/console/purpose/manager?title=用途管理','用途管理')">用途管理</div>
                <div class="accordion-item" onclick="goPage('/console/payUser/manager?title=使用者管理','使用者管理')">使用者管理</div>
                <div class="accordion-item" onclick="goPage('/console/tally/manager?title=账单明细管理','账单明细管理')">账单明细管理</div>
            </div>
            <div title="报表管理" data-options="" style="padding:0;">
                <div class="accordion-item" onclick="goPage('/console/report/monthManager?title=月度统计','月度统计')">月度统计</div>
                <div class="accordion-item" onclick="goPage('/console/report/yearTableManager?title=年度统计','年度图表统计')">年度图表统计</div>
            </div>
        </div>
    </div>
    <div data-options="region:'center'" style="overflow: hidden;">
        <div id="index_tabs" style="overflow: hidden;">
            <div title="首页" data-options="border:false" style="overflow: hidden;">
                <div style="padding: 10px 0 10px 10px">
                    <h2>系统介绍</h2>
                    <div class="light-info">
                        <div class="light-tip icon-tip"></div>
                        <div>家庭记账管理系统。</div>
                        <ul>
                            <li style="margin: 5px;">
                                <a onclick="downloadfile('weixin')" href="javascript:void(0);">微信使用说明书</a>
                            </li>
                            <li style="margin: 5px;">
                                <a onclick="downloadfile('web')" href="javascript:void(0);">web使用说明书</a>
                            </li>
                            <li style="margin: 5px;">
                                <a onclick="downloadfile('console')" href="javascript:void(0);">后台使用说明书</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--[if lte IE 8]>
<div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a
        href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 9</a> 或以下浏览器：
    <a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a
            href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a
            href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/"
                                                                                   target="_blank">Opera</a></p></div>
<![endif]-->
<style>
    /*ie6提示*/
    #ie6-warning {
        width: 100%;
        position: absolute;
        top: 0;
        left: 0;
        background: #fae692;
        padding: 5px 0;
        font-size: 12px
    }

    #ie6-warning p {
        width: 960px;
        margin: 0 auto;
    }
</style>
<script type="text/javascript">
    $(function () {
        $(".l-btn-icon.icon-refresh").click(function () {
            window.location.reload();
        });
    });
</script>
</body>
</html>