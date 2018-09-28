/**
 * Created by huyc on 17/1/20.
 */

/** 正则表达式获取URL参数 */
function getParameter(sProp) {
    var re = new RegExp(sProp + "=([^\&]*)", "i");
    var a = re.exec(document.location.search);
    if (a == null) {
        return null;
    }
    return a[1];
}

/** 验证电话 */
function validatePhone(str) {
    var pattern1 = /^1\d{10}$/;
    var pattern2 = /^(([0\+]\d{2,3}-)?(0\d{2,3})(-)?)?(\d{7,8})(-(\d{3,}))?$/;
    if (pattern1.test(str) || pattern2.test(str)) {
        return "t";
    }
    return "f";
}

/** 过滤特殊字符 */
function validateSpecialChar(str) {
    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？%]");
    var rs = "";
    for (var i = 0; i < str.length; i++) {
        rs = rs + str.substr(i, 1).replace(pattern, '');
    }
    return rs;
}

/** 特殊字符替换 */
function charsetFilter(str) {
    str = str.replace(/\+/g, "%2B");
    str = str.replace(/\&/g, "%26");
    return str;
}

/** 防止SQL注入 */
function validAntiSql(str) {
    re = /select|update|delete|exec|count|or|insert|'|"|=|;|>|<|%/i;
    if (re.test(str)) {
        return false;
    }
    return true;
}

/** 数组删除位置元素 */
Array.prototype.remove = function (dx) {
    if (isNaN(dx) || dx > this.length) {
        return false;
    }
    for (var i = 0, n = 0; i < this.length; i++) {
        if (this[i] != this[dx]) {
            this[n++] = this[i]
        }
    }
    this.length -= 1
};
/** 数组中是否包含某个元素 */
Array.prototype.in_array = function (e) {
    for (i = 0; i < this.length && this[i] != e; i++) ;
    return !(i == this.length);
};

/** 获取随机字符串 */
function getRandom(n) {
    return Math.floor(Math.random() * n + 1);
}

/**
 * 时间对象的格式化;
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 0 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    var week = {
        "0": "/u65e5",
        "1": "/u4e00",
        "2": "/u4e8c",
        "3": "/u4e09",
        "4": "/u56db",
        "5": "/u4e94",
        "6": "/u516d"
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(format)) {
        format = format.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return format;
};

//去掉所有空格
function removeAllSpace(str) {
    return str.replace(/\s+/g, "");
}

//去掉前后空格
function trim(str) {
    return str.replace(/(^\s+)|(\s+$)/g, "");
}

/*  常量  */
var CONSTANT = {
    DATA_TABLES: {
        DEFAULT_OPTION: { //DataTables初始化选项
            "language": {
                "sProcessing": "处理中...",
                "sLengthMenu": "每页 _MENU_ 项",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "当前显示第 _START_ 至 _END_ 条记录，共 _TOTAL_ 条",
                "sInfoEmpty": "当前显示第 0 至 0 条记录，共 0 条",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "查询数据为空!",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页",
                    "sJump": "跳转"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            "bFilter": true,
            "sPaginationType": "simple_numbers",
            "bProcessing": true, //隐藏加载提示,自行处理
            "bServerSide": true,
            "searching": false,//禁用表格内搜索
            "bSort": false,//是否支持排序功能
            "bAutoWidth": false  //自适应宽度
            //"destroy": true,//如果需要重新加载的时候请加上这个
        }
    }
};