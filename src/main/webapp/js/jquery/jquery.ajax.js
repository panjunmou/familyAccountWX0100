/*******************************************************************************
 * jQuery Ajax封装通用类
 ******************************************************************************/
$(function() {
	/**
	 * ajax封装 url 发送请求的地址 data 发送到服务器的数据，数组存储，如：{"date": new Date().getTime(),
	 * "state": 1} async 默认值: true。默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。
	 * 注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。 type 请求方式("POST" 或 "GET")， 默认为 "GET"
	 * successfn 成功回调函数 errorfn 失败回调函数
	 */
	ajaxJS = function(url, data, async, type, successfn) {
		showLoading();
		async = (async == null || async == "" || typeof (async) == "undefined") ? "true"
				: async;
		type = (type == null || type == "" || typeof (type) == "undefined") ? "post"
				: type;
		data = (data == null || data == "" || typeof (data) == "undefined") ? {
			"date" : new Date().getTime()
		} : data;
		$.ajax({
			type : type,
			async : async,
			data : data,
			url : url,
			dataType : "json",
			beforeSend : function(request) {
				request.setRequestHeader("X-Token", "asdasdasdasdasdasdadsa");
			},
			success : function(d) {
				successfn(d);
				hideLoading();
			},
			error : function(e) {
				showFail();
				hideLoading();
			}
		});
	};

});

function initSilde() {
	/**
	 * 下拉导航条件筛选
	 */
	// 筛选条件
	$(".typebox .slide").click(function() {
		if ($(this).hasClass("active")) {
			var _t = $(this).index();
			$(this).removeClass("active");
			$(".transparentmask").hide();
			$(".typebox").removeClass("active");
			$(".conditions ul").stop().eq(_t).hide();
		} else {
			$(".transparentmask").show();
			$(".typebox").addClass("active");
			$(".typebox li").removeClass("active");
			$(this).addClass("active");
			var _t = $(this).index();
			$(".conditions ul").stop().hide();
			$(".conditions ul").stop().eq(_t).slideDown(100);
		}
	});
	// 关闭条件
	$(".conditions ul li").click(function() {
		var _index = $(this).parent().index();
		if (_index == 0) {
			// 移除所有筛选条件样式
			$(".conditions ul li").removeClass("activecolor");
		} else {
			// 取消筛选条件“全部”的勾选样式
			$(".conditions ul").eq(0).find("li").removeClass("activecolor");

			// 清空当前点击的ul中所有li的样式
			$(this).parent().find("li").removeClass("activecolor");
		}
		// 设置当前点击的li的样式
		$(this).addClass("activecolor");

		// 关闭ul列表
		$(".conditions ul").hide();
		$(".typebox li").removeClass("active");
		$(".typebox").removeClass("active");

		// 关闭遮盖层
		$(".transparentmask").hide();

		// 2016-10-08 pangzhiwei
		// $(".typebox").removeClass("active");
		// $(this).addClass("activecolor");
		// $(".transparentmask").hide();
		// $(".conditions ul").hide();
		// var _con = $(this).html();
		// var _s = $(this).parent().index();
		// $(".typebox li b").eq(_s).html(_con);
		// $(".typebox li").removeClass("active");
		// 2016-10-08 pangzhiwei

	});
	// 遮盖曾
	$(".transparentmask").click(function() {
		$(".typebox").removeClass("active");
		$(this).hide();
		$(".conditions ul").hide();
		$(".typebox li").removeClass("active");
	});
}
/** 显示加载 * */
function showLoading() {
	$.showLoading();
}
/** 隐藏加载 * */
function hideLoading() {
	$.hideLoading();
}

/** 操作成功 * */
function showSucc(msg) {
	// $.toast(msg);
	$.alert(msg, "提示");
}

/** 接口操作出错 * */
function showError(msg) {
	// $.toast(msg, "cancel");
	$.alert(msg, "提示");
}

/** 接口调用失败 * */
function showFail(msg) {
	// $.toast("操作失败，请检查网络", "cancel");
	$.alert(msg, "错误");
}

/** 判断是微信浏览器 * */
function isWeChat() {
	var ua = window.navigator.userAgent.toLowerCase();
	if (ua.match(/MicroMessenger/i) == 'micromessenger') {
		return true;
	} else {
		return false;
	}
}
// 手机号码校验
function mobileCheck(value) {
	var rex = /^1[3-8]+\d{9}$/;
	// var rex=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	// 区号：前面一个0，后面跟2-3位数字 ： 0\d{2,3}
	// 电话号码：7-8位数字： \d{7,8
	// 分机号：一般都是3位数字： \d{3,}
	// 这样连接起来就是验证电话的正则表达式了：/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/
	var rex2 = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	if (rex.test(value) || rex2.test(value)) {
		// alert('t'+value);
		return true;
	} else {
		// alert('false '+value);
		return false;
	}
}

/**
 * 金额格式化，保留两位小数
 * 
 * @param val
 * @returns {Number}
 */
function fmtDecimal(val) {
	var fmtVal = 0.00;
	try {
		fmtVal = parseFloat(val).toFixed(2);
	} catch (e) {
		fmtVal = 0.00;
	}
	return fmtVal;
}