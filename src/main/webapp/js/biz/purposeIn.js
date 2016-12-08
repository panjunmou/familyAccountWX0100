
+function (e) {
    e.rawCitiesData = purposeInData;

}($), +function (e) {
    "use strict";
    var a;
    e.fn.purposeIn = function (n) {
        return n = e.extend({}, a, n), this.each(function () {
            var a = function (e) {
                for (var a = [], n = 0; n < e.length; n++) {
                    var m = e[n];
                    "请选择" !== m.name && a.push(m.name)
                }
                return a.length ? a : [""]
            }, m = function (e) {
                return e.sub ? a(e.sub) : [""]
            }, s = function (e) {
                for (var a = 0; a < t.length; a++)if (t[a].name === e)return m(t[a]);
                return [""]
            }, u = function (e, a) {
                for (var n = 0; n < t.length; n++)if (t[n].name === e)for (var s = 0; s < t[n].sub.length; s++)if (t[n].sub[s].name === a)return m(t[n].sub[s]);
                return [""]
            }, t = e.rawCitiesData, b = t.map(function (e) {
                return e.name
            }), p = m(t[0]), y = m(t[0].sub[0]), r = b[0], l = p[0], c = y[0], i = [{
                values: b,
                cssClass: "col-province"
            }, {values: p, cssClass: "col-city"}];
            n.showDistrict && i.push({values: y, cssClass: "col-district"});
            var o = {
                cssClass: "city-picker", rotateEffect: !1, onChange: function (e, a, m) {
                    var t, b = e.cols[0].value;
                    if (b !== r) {
                        var p = s(b);
                        t = p[0];
                        var y = u(b, t);
                        return e.cols[1].replaceValues(p), n.showDistrict && e.cols[2].replaceValues(y), r = b, l = t, void e.updateValue()
                    }
                    n.showDistrict && (t = e.cols[1].value, t !== l && (e.cols[2].replaceValues(u(b, t)), l = t, e.updateValue()))
                }, cols: i
            };
            if (this) {
                var v = e.extend(o, n), f = e(this).val();
                f && (v.value = f.split(" "), v.value[0] && (r = v.value[0], v.cols[1].values = s(v.value[0])), v.value[1] ? (l = v.value[1], n.showDistrict && (v.cols[2].values = u(v.value[0], v.value[1]))) : (c = v.value[2], n.showDistrict && (v.cols[2].values = u(v.value[0], v.cols[1].values[0])))), e(this).picker(v)
            }
        })
    }, a = e.fn.purposeIn.prototype.defaults = {showDistrict: !0}
}($);