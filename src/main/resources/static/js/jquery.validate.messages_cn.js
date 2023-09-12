/*
* Translated default messages for the jQuery validation plugin.
* Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
*/
$.extend($.validator.messages, {
    required: "必须填写",
    remote: "请修正此栏位",
    email: "请输入有效的电子邮件",
    url: "请输入有效的网址",
    date: "请输入有效的日期",
    dateISO: "请输入有效的日期 (YYYY-MM-DD)",
    number: "请输入正确的数字",
    digits: "只可输入数字",
    creditcard: "请输入有效的信用卡号码",
    equalTo: "你的输入不相同",
    extension: "请输入有效的后缀",
    maxlength: $.validator.format("最多 {0} 个字"),
    minlength: $.validator.format("最少 {0} 个字"),
    rangelength: $.validator.format("请输入长度为 {0} 至 {1} 之間的字串"),
    range: $.validator.format("请输入 {0} 至 {1} 之间的数值"),
    max: $.validator.format("请输入不大于 {0} 的数值"),
    min: $.validator.format("请输入不小于 {0} 的数值")
});

/*      验证日期时间格式：yyyy-MM-dd HH:mm:ss     */
jQuery.validator.addMethod("datetime", function (value, element) {
    return this.optional(element) || /^\d{4}\-([0-9]|0[0-9]|1[0-2])\-([0-2]{0,1}[0-9]|3[0-1])\s([0-1]{0,1}[0-9]|2[0-4])\:[0-5]{0,1}[0-9]$/i.test($.trim(value));
}, "日期时间不正确");

/*      验证日期格式：yyyy-MM-dd     */
jQuery.validator.addMethod("ETDate", function (value, element) {
    return this.optional(element) || /^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$/i.test($.trim(value));
}, "请输入合法的日期");

/*      验证日期年份*/
jQuery.validator.addMethod("date-year", function (value, element) {
    return this.optional(element) || /^[1-9][0-9]{3}$/i.test($.trim(value));
}, "格式错误");

/*      验证邮政编码格式*/
jQuery.validator.addMethod("postcode", function (value, element) {
    return this.optional(element) || /^[1-9][0-9]{5}$/i.test($.trim(value));
}, "请输入正确的邮政编码");

/*      验证手机号码格式：中国大陆移动电话号码     */
jQuery.validator.addMethod("mobilephone", function (value, element) {
    return this.optional(element) || /^1[3|4|5|6|7|8|9][0-9]\d{8}$/i.test($.trim(value));
}, "请输入正确的手机号码");

/*      验证固定电话号码格式：中国大陆固定电话号码     */
jQuery.validator.addMethod("telephone_zh", function (value, element) {
    return this.optional(element) || /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/i.test($.trim(value));
}, "请输入正确的固定电话号码");

jQuery.validator.addMethod("currency", function (value, element) {
    return this.optional(element) || /^([1-9]\d*\.\d*|0\.\d*[1-9]\d*|[1-9]\d*|0)$/i.test($.trim(value));
}, "请输入正确的金额");

jQuery.validator.addMethod("noBlankPassword", function (value, element) {
    return this.optional(element) || /^[^\s]*$/i.test($.trim(value));
}, "密码不能包含空格");

jQuery.validator.addMethod("noLettersNumber", function (value, element) {
    return this.optional(element) || /^(?=.*\d)(?=.*[a-zA-Z]).{2,}$/i.test($.trim(value));
}, "必须包含字母和数字");

// 身份证号码验证 
jQuery.validator.addMethod("idCardNo", function (value, element) {
    return this.optional(element) || idCardNoUtil.checkIdCardNo(value);
}, "请输入您正确的身份证号码");


var idCardNoUtil = {
    provinceAndCitys: { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江",
        31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东",
        45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏",
        65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外"
    },

    powers: ["7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"],

    parityBit: ["1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"],

    genders: { male: "男", female: "女" },

    checkAddressCode: function(addressCode) {
        var check = /^[1-9]\d{5}$/.test(addressCode);
        if (!check) return false;
        if (idCardNoUtil.provinceAndCitys[parseInt(addressCode.substring(0, 2))]) {
            return true;
        } else {
            return false;
        }
    },

    checkBirthDayCode: function(birDayCode) {
        var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/.test(birDayCode);
        if (!check) return false;
        var yyyy = parseInt(birDayCode.substring(0, 4), 10);
        var mm = parseInt(birDayCode.substring(4, 6), 10);
        var dd = parseInt(birDayCode.substring(6), 10);
        var xdata = new Date(yyyy, mm - 1, dd);
        if (xdata > new Date()) {
            return false; //生日不能大于当前日期
        } else if ((xdata.getFullYear() == yyyy) && (xdata.getMonth() == mm - 1) && (xdata.getDate() == dd)) {
            return true;
        } else {
            return false;
        }
    },

    getParityBit: function(idCardNo) {
        var id17 = idCardNo.substring(0, 17);
        var power = 0;
        for (var i = 0; i < 17; i++) {
            power += parseInt(id17.charAt(i), 10) * parseInt(idCardNoUtil.powers[i]);
        }
        var mod = power % 11;
        return idCardNoUtil.parityBit[mod];
    },

    checkParityBit: function(idCardNo) {
        var parityBit = idCardNo.charAt(17).toUpperCase();
        if (idCardNoUtil.getParityBit(idCardNo) == parityBit) {
            return true;
        } else {
            return false;
        }
    },

    checkIdCardNo: function(idCardNo) {
        //15位和18位身份证号码的基本校验
        var check = /^\d{15}|(\d{17}(\d|x|X))$/.test(idCardNo);
        if (!check) return false;
        //判断长度为15位或18位
        if (idCardNo.length == 15) {
            return idCardNoUtil.check15IdCardNo(idCardNo);
        } else if (idCardNo.length == 18) {
            return idCardNoUtil.check18IdCardNo(idCardNo);
        } else {
            return false;
        }
    },
    //校验15位的身份证号码
    check15IdCardNo: function(idCardNo) {
        //15位身份证号码的基本校验
        var check = /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/.test(idCardNo);
        if (!check) return false;
        //校验地址码
        var addressCode = idCardNo.substring(0, 6);
        check = idCardNoUtil.checkAddressCode(addressCode);
        if (!check) return false;
        var birDayCode = '19' + idCardNo.substring(6, 12);
        //校验日期码
        return idCardNoUtil.checkBirthDayCode(birDayCode);
    },
    //校验18位的身份证号码
    check18IdCardNo: function(idCardNo) {
        //18位身份证号码的基本格式校验
        var check = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}(\d|x|X)$/.test(idCardNo);
        if (!check) return false;
        //校验地址码
        var addressCode = idCardNo.substring(0, 6);
        check = idCardNoUtil.checkAddressCode(addressCode);
        if (!check) return false;
        //校验日期码
        var birDayCode = idCardNo.substring(6, 14);
        check = idCardNoUtil.checkBirthDayCode(birDayCode);
        if (!check) return false;
        //验证校检码
        return idCardNoUtil.checkParityBit(idCardNo);
    },
    formateDateCN: function(day) {
        var yyyy = day.substring(0, 4);
        var mm = day.substring(4, 6);
        var dd = day.substring(6);
        return yyyy + '-' + mm + '-' + dd;
    },
    //获取信息
    getIdCardInfo: function(idCardNo) {
        var idCardInfo = {
            gender: "", //性别
            birthday: "" // 出生日期(yyyy-mm-dd)
        };
        if (idCardNo.length == 15) {
            var aday = '19' + idCardNo.substring(6, 12);
            idCardInfo.birthday = idCardNoUtil.formateDateCN(aday);
            if (parseInt(idCardNo.charAt(14)) % 2 == 0) {
                idCardInfo.gender = idCardNoUtil.genders.female;
            } else {
                idCardInfo.gender = idCardNoUtil.genders.male;
            }
        } else if (idCardNo.length == 18) {
            var aday = idCardNo.substring(6, 14);
            idCardInfo.birthday = idCardNoUtil.formateDateCN(aday);
            if (parseInt(idCardNo.charAt(16)) % 2 == 0) {
                idCardInfo.gender = idCardNoUtil.genders.female;
            } else {
                idCardInfo.gender = idCardNoUtil.genders.male;
            }
        }
        return idCardInfo;
    },

    getId15: function(idCardNo) {
        if (idCardNo.length == 15) {
            return idCardNo;
        } else if (idCardNo.length == 18) {
            return idCardNo.substring(0, 6) + idCardNo.substring(8, 17);
        } else {
            return null;
        }
    },

    getId18: function(idCardNo) {
        if (idCardNo.length == 15) {
            var id17 = idCardNo.substring(0, 6) + '19' + idCardNo.substring(6);
            var parityBit = idCardNoUtil.getParityBit(id17);
            return id17 + parityBit;
        } else if (idCardNo.length == 18) {
            return idCardNo;
        } else {
            return null;
        }
    }
};