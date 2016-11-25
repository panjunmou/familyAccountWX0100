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
    <script type="text/javascript">
        var cityData =[
            {
                name: "北京",
                sub: [{
                    name: "北京市",
                    sub: [{name: "请选择"}, {name: "东城区"}, {name: "西城区"}, {name: "崇文区"}, {name: "宣武区"}, {name: "朝阳区"}, {name: "海淀区"}, {name: "丰台区"}, {name: "石景山区"}, {name: "房山区"}, {name: "通州区"}, {name: "顺义区"}, {name: "昌平区"}, {name: "大兴区"}, {name: "怀柔区"}, {name: "平谷区"}, {name: "门头沟区"}, {name: "密云县"}, {name: "延庆县"}, {name: "其他"}]
                }],
                type: 0
            },
            {
                name: "广东",
                sub: [
                    {name: "请选择", sub: []}, {
                        name: "广州",
                        sub: [{name: "请选择"}, {name: "越秀区"}, {name: "荔湾区"}, {name: "海珠区"}, {name: "天河区"}, {name: "白云区"}, {name: "黄埔区"}, {name: "番禺区"}, {name: "花都区"}, {name: "南沙区"}, {name: "萝岗区"}, {name: "增城市"}, {name: "从化市"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "深圳",
                        sub: [{name: "请选择"}, {name: "福田区"}, {name: "罗湖区"}, {name: "南山区"}, {name: "宝安区"}, {name: "龙岗区"}, {name: "盐田区"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "珠海",
                        sub: [{name: "请选择"}, {name: "香洲区"}, {name: "斗门区"}, {name: "金湾区"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "汕头",
                        sub: [{name: "请选择"}, {name: "金平区"}, {name: "濠江区"}, {name: "龙湖区"}, {name: "潮阳区"}, {name: "潮南区"}, {name: "澄海区"}, {name: "南澳县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "韶关",
                        sub: [{name: "请选择"}, {name: "浈江区"}, {name: "武江区"}, {name: "曲江区"}, {name: "乐昌市"}, {name: "南雄市"}, {name: "始兴县"}, {name: "仁化县"}, {name: "翁源县"}, {name: "新丰县"}, {name: "乳源瑶族自治县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "佛山",
                        sub: [{name: "请选择"}, {name: "禅城区"}, {name: "南海区"}, {name: "顺德区"}, {name: "三水区"}, {name: "高明区"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "江门",
                        sub: [{name: "请选择"}, {name: "蓬江区"}, {name: "江海区"}, {name: "新会区"}, {name: "恩平市"}, {name: "台山市"}, {name: "开平市"}, {name: "鹤山市"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "湛江",
                        sub: [{name: "请选择"}, {name: "赤坎区"}, {name: "霞山区"}, {name: "坡头区"}, {name: "麻章区"}, {name: "吴川市"}, {name: "廉江市"}, {name: "雷州市"}, {name: "遂溪县"}, {name: "徐闻县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "茂名",
                        sub: [{name: "请选择"}, {name: "茂南区"}, {name: "茂港区"}, {name: "化州市"}, {name: "信宜市"}, {name: "高州市"}, {name: "电白县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "肇庆",
                        sub: [{name: "请选择"}, {name: "端州区"}, {name: "鼎湖区"}, {name: "高要市"}, {name: "四会市"}, {name: "广宁县"}, {name: "怀集县"}, {name: "封开县"}, {name: "德庆县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "惠州",
                        sub: [{name: "请选择"}, {name: "惠城区"}, {name: "惠阳区"}, {name: "博罗县"}, {name: "惠东县"}, {name: "龙门县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "梅州",
                        sub: [{name: "请选择"}, {name: "梅江区"}, {name: "兴宁市"}, {name: "梅县"}, {name: "大埔县"}, {name: "丰顺县"}, {name: "五华县"}, {name: "平远县"}, {name: "蕉岭县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "汕尾",
                        sub: [{name: "请选择"}, {name: "城区"}, {name: "陆丰市"}, {name: "海丰县"}, {name: "陆河县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "河源",
                        sub: [{name: "请选择"}, {name: "源城区"}, {name: "紫金县"}, {name: "龙川县"}, {name: "连平县"}, {name: "和平县"}, {name: "东源县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "阳江",
                        sub: [{name: "请选择"}, {name: "江城区"}, {name: "阳春市"}, {name: "阳西县"}, {name: "阳东县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "清远",
                        sub: [{name: "请选择"}, {name: "清城区"}, {name: "英德市"}, {name: "连州市"}, {name: "佛冈县"}, {name: "阳山县"}, {name: "清新县"}, {name: "连山壮族瑶族自治县"}, {name: "连南瑶族自治县"}, {name: "其他"}],
                        type: 0
                    }, {name: "东莞", sub: [], type: 0}, {name: "中山", sub: [], type: 0}, {
                        name: "潮州",
                        sub: [{name: "请选择"}, {name: "湘桥区"}, {name: "潮安县"}, {name: "饶平县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "揭阳",
                        sub: [{name: "请选择"}, {name: "榕城区"}, {name: "普宁市"}, {name: "揭东县"}, {name: "揭西县"}, {name: "惠来县"}, {name: "其他"}],
                        type: 0
                    }, {
                        name: "云浮",
                        sub: [{name: "请选择"}, {name: "云城区"}, {name: "罗定市"}, {name: "云安县"}, {name: "新兴县"}, {name: "郁南县"}, {name: "其他"}],
                        type: 0
                    }, {name: "其他"}],
                type: 1
            },
            {
                name: "上海",
                sub: [
                    {
                        name: "上海市",
                        sub: [
                            {name: "请选择"}, {name: "黄浦区"}, {name: "卢湾区"}, {name: "徐汇区"}, {name: "长宁区"}, {name: "静安区"}, {name: "普陀区"}, {name: "闸北区"}, {name: "虹口区"}, {name: "杨浦区"}, {name: "宝山区"}, {name: "闵行区"}, {name: "嘉定区"}, {name: "松江区"}, {name: "金山区"}, {name: "青浦区"}, {name: "南汇区"}, {name: "奉贤区"}, {name: "浦东新区"}, {name: "崇明县"}, {name: "其他"}
                        ]
                    }
                ],
                type: 0
            }
        ];

        $(function () {

            $("#date").calendar();

            $("#account").select({
                title: "选择账户",
                items: [
                    {
                        title: "潘春光",
                        value: 1
                    },
                    {
                        title: "刘晓丽",
                        value: 2
                    },
                    {
                        title: "潘骏谋",
                        value: 3
                    },
                    {
                        title: "徐碧莹",
                        value: 4
                    }
                ],
                onOpen: function () {
                    $("#footerBar").hide();
                },
                onClose: function () {
                    $("#footerBar").show();
                }
            });

            $("#usePerson").select({
                title: "选择消费者",
                multi: true,
                items: [
                    {
                        title: "潘春光",
                        value: 1
                    },
                    {
                        title: "刘晓丽",
                        value: 2
                    },
                    {
                        title: "潘骏谋",
                        value: 3
                    },
                    {
                        title: "徐碧莹",
                        value: 4
                    }
                ],
                onOpen: function () {
                    $("#footerBar").hide();
                },
                onClose: function () {
                    $("#footerBar").show();
                }
            });

            $("#usefull").cityPicker({
                title: "请选择收货地址"
            });

        });
    </script>
    <script type="text/javascript" src="${ctx}/js/jquery/city-picker.min.js"></script>
</head>
<body>
<div class="weui_tab">
    <div class="weui_navbar">
        <a class="weui_navbar_item weui_bar_item_on">
            支出
        </a>
        <a class="weui_navbar_item">
            收入
        </a>
    </div>
    <div class="weui_tab_bd">
        <div class="weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">金额</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" type="number" placeholder="请输入金额">
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">日期</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" id="date" type="text" data-toggle='date'/>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">账户</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" id="account" type="text" value=""/>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">用途</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" id="usefull" type="text" value=""/>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">消费者</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" id="usePerson" type="text" value=""/>
                </div>
            </div>
        </div>
        <div class="weui_cells_title">备注</div>
        <div class="weui_cells weui_cells_form">
            <div class="weui_cell">
                <div class="weui_cell_bd weui_cell_primary">
                    <textarea class="weui_textarea" placeholder="请输入评论" rows="3"></textarea>
                    <div class="weui_textarea_counter"><span>0</span>/200</div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 底部导航 -->
<div id="footerBar">
    <jsp:include page="../common/footer.jsp"></jsp:include>
</div>
<!-- 底部导航 -->
</body>
</html>