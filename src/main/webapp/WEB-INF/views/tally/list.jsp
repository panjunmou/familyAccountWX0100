<%--
  Created by IntelliJ IDEA.
  User: PanJM
  Date: 2016/11/19
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/config.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../wechat.jsp"></jsp:include>
    <title>首页</title>
    <link href="${ ctx }/style/css/list.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        $(function () {
            initSilde();

            searchList('all','');

            //下拉刷新
            $(document.body).pullToRefresh();
            $(document.body).on("pull-to-refresh", function () {
                $(".weui-pull-to-refresh-layer").css("padding-top", "100px");
                $("#content").css("top", "0px");
                $("[name='page']").val(1);
                $(document.body).infinite();
                searchList('all', '');
            });

//            $(document.body).infinite();
            //底部加载
            var loading = false;  //状态标记
            $(document.body).infinite().on("infinite", function () {
                console.log(loading);
                if (loading) return;
                loading = true;

                $("[name='page']").val(parseInt($("[name='page']").val()) + 1);

                var url = '${ctx}/tally/hardWareSuitList';

                ajaxJS(url, $("form").serialize(), false, "get", function (result) {
                    if (result.success) {//成功
                        loading = false;
                        addTallyList(result);
                    } else {
                        showError(result.msg)
                    }
                });
            });
        });

        function showActions(id) {
            $.actions({
                actions: [
                    {
                        text: "查看",
                        className: "color-primary",
                        onClick: function () {

                        }
                    },
                    {
                        text: "编辑",
                        className: "color-warning",
                        onClick: function () {
                        }
                    },
                    {
                        text: "删除",
                        className: 'color-danger',
                        onClick: function () {
                        }
                    }
                ]
            });
        }

        function searchList(type, value) {
            if (type == "type") {
                $("form [name='type']").val(value);
            } else if (type == 'account') {
                $("form [name='account']").val(value);
            } else if (type == 'payUser') {
                $("form [name='payUser']").val(value);
            } else if (type == 'all') {
                $("form [name='type']").val('');
                $("form [name='account']").val('');
                $("form [name='payUser']").val('');
            }
            var url = '${ctx}/tally/hardWareSuitList';
            ajaxJS(url, $("form").serialize(), false, "get", function (result) {
                if (result.success) {//成功
                    $("#content").empty();
                    addTallyList(result);
                } else {
                    showError(result.msg)
                }
            });
        }

        function addTallyList(result) {
            var list = result.obj.rows;
            $.each(list, function (i, v) {
                var obj =
                        '<div class="order" onclick="showActions()"> ' +
                        '<div class="order-title"> ' +
                        '<div class="pull-left order-title-id-unpaid">用途:'+v.purposeName+'</div> ' +
                        '<div class="pull-right order-title-state-unpaid">'+v.payDate+'</div> ' +
                        '<div class="clear"></div> </div> ' +
                        '<div class="order-content"> ' +
                        '<div class="order-info">账户:'+v.accountName+'</div> ' +
                        '<div class="order-info">消费者:'+v.payUserNames+'</div> ' +
                        '<div class="order-info">金额:'+v.money+'</div> ' +
                        '</div> ' +
                        '</div>';
                $("#content").append(obj);

                $(".showcase-image img").click(function (e) {
                    window.location.href = $(this).attr("link");
                });
            });
            $("#content").find("div[class='weui-infinite-scroll']").remove();
            var pageNum = $("[name='page']").val();
            var total = parseInt(result.obj.total);
            var pageSize = parseInt($("[name='pageSize']").val());
            var totalPage = getTotalPage(total, pageSize);
            if (parseInt(pageNum) < totalPage) {
                $("#content").append(
                        '<div class="weui-infinite-scroll"><div class="infinite-preloader"></div> 正在加载...</div>'
                );
            } else {
                $(document.body).destroyInfinite();
            }
            $(document.body).pullToRefreshDone();
        }
    </script>
</head>
<body>
<div class="weui_search_bar" id="search_bar">
    <form class="weui_search_outer">
        <div class="weui_search_inner">
            <i class="weui_icon_search"></i>
            <input type="search" class="weui_search_input" id="search_input" placeholder="搜索" required
                   onkeyup="searchList('wareSuitName', $(this).val())"/>
            <a href="javascript:void(0);" class="weui_icon_clear" id="search_clear"></a>
        </div>
        <label for="search_input" class="weui_search_text" id="search_text"> <i class="weui_icon_search"></i>
            <span>搜索</span>
        </label>
    </form>
    <a href="javascript:void(0);" class="weui_search_cancel" id="search_cancel">取消</a>
</div>
<div class="assets outer" id="outer">
    <div class="typebox clearfix">
        <li class="slide" style="width: 20%">
            <b>全部</b>
            <span></span>
        </li>
        <li class="slide" style="width: 20%">
            <b>类型</b>
            <span></span>
        </li>
        <li class="slide" style="width: 20%">
            <b>账户</b>
            <span></span>
        </li>
        <li class="slide" style="width: 20%">
            <b>消费者</b>
            <span></span>
        </li>
    </div>
    <div class="conditions">
        <ul>
            <li class="activecolor">
                <a class="active" href="javascript:void(0);" onclick="searchList('all', '')">全部</a>
            </li>
        </ul>
        <ul>
            <li>
                <a href="javascript:void(0);" onclick="searchList('type', '-1')">支出</a>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="searchList('type', '1')">收入</a>
            </li>
        </ul>
        <ul>

        </ul>
        <ul>

        </ul>
    </div>
</div>
<form>
    <input type="hidden" name="account" value=""/>
    <input type="hidden" name="payUser" value=""/>
    <input type="hidden" name="page" value="1"/>
    <input type="hidden" name="pageSize" value="5"/>
</form>
<div class="weui-pull-to-refresh-layer">
    <div class="pull-to-refresh-arrow"></div> <!-- 上下拉动的时候显示的箭头 -->
    <div class="pull-to-refresh-preloader"></div> <!-- 正在刷新的菊花 -->
    <div class="down">下拉刷新</div><!-- 下拉过程显示的文案 -->
    <div class="up">释放刷新</div><!-- 下拉超过50px显示的文案 -->
    <div class="refresh">正在刷新...</div><!-- 正在刷新时显示的文案 -->
</div>
<div id="content">
    <%--<div class="order" onclick="showActions()">
        <div class="order-title">
            <div class="pull-left order-title-id-unpaid">用途:基本生活-餐饮饮食</div>
            <div class="pull-right order-title-state-unpaid">2016-11-26</div>
            <div class="clear"></div>
        </div>
        <div class="order-content">
            <div class="order-info">账户:潘骏谋</div>
            <div class="order-info">消费者:潘骏谋,徐碧莹</div>
            <div class="order-info">金额：82800.00</div>
        </div>
    </div>--%>
</div>
<!-- 底部导航 -->
<jsp:include page="../common/footer.jsp"></jsp:include>
<!-- 底部导航 -->
</body>
</html>
