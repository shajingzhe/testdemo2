var jqueryAjax = {
    query: function (requestUrl, data, dataType) {
        var result;
        $.ajax({
            url: requestUrl,
            dataType: dataType ? dataType : 'json',
            async: false,
            type: "post",
            data: data,
            success: function (data, textStatus, jqXHR) {
                result = data;
            }
        });
        return result;
    },
    postForm: function (url, formData, successFun, dataType) {
        var result;
        $.ajax({
            url: url,
            dataType: dataType ? dataType : 'json',
            async: false,
            type: "post",
            data: formData,
            contentType: false,// 当有文件要上传时，此项是必须的，否则后台无法识别文件流的起始位置(详见：#1)
            processData: false,// 是否序列化data属性，默认true(注意：false时type必须是post，详见：#2)
            success: function (responseData, textStatus, jqXHR) {
                if (successFun)
                    eval('successFun(responseData)');
                result = responseData;
            }
        });
        return result;
    },
    post: function (requestUrl, data, successFun, dataType) {
        var result;
        $.ajax({
            url: requestUrl,
            dataType: dataType ? dataType : 'json',
            async: false,
            type: "post",
            data: data,
            success: function (responseData, textStatus, jqXHR) {
                if (successFun)
                    eval('successFun(responseData)');
                result = responseData;
            }
        });
        return result;
    },
    postAsync: function (postUrl, data, successFun, dataType) {
        $.ajax({
            url: postUrl,
            dataType: dataType ? dataType : 'json',
            async: true,
            type: "post",
            data: data,
            success: function (responseData, textStatus, jqXHR) {
                if (successFun)
                    eval('successFun(responseData)');
            }
        });
    },
    getJson: function (getUrl) {
        var result;
        $.ajax({
            url: getUrl,
            dataType: 'json',
            async: false,
            type: "post",
            success: function (data, textStatus, jqXHR) {
                result = data;
            }
        });
        return result;
    }
};

let userOperateNoteName = {
    ZBSBTBSM: "招标申报填表说明",
    JHRWSTBSM: "计划任务书填表说明",
    JHRWSGZS: "计划任务书告知书",
    JHRWSCNS: "计划任务书承诺书",
    XMCGPSXZ: "项目成果评审须知",
    ZBPSXZ: "招标评审须知",
    ZBMTBX: "招标面谈比选",
    LJRWZJPSXZ: "领军人物专家评审须知",
    //todo s 待优化（前后端）
}

var StatusInfo = {
    _data: {},
    getStatusList: function (statusCategory) {
        var statusResponse = jqueryAjax.getJson('/status/query?category=' + statusCategory);
        if (statusResponse && statusResponse.data)
            this._data[statusCategory] = JSON.parse(statusResponse.data);
    },
    getStatusName: function (statusCategory, statusCode) {
        if (!(statusCategory in this._data)) {
            this.getStatusList(statusCategory);
        }
        var status_data = this._data[statusCategory];
        if (status_data) {
            var status = status_data.find(s => s.code == statusCode);
            if (status)
                return status.name;
        }
        return statusCode;
    }
}

function getNewUUID() {
    var response = jqueryAjax.getJson("/getNewID");
    return response.data;
}

//获取请求参数的对象封装
var Request = {
    Url: window.location.href,
    Params: function (key) {
        var url = this.Url;
        var paramPart = url.split('?').length > 1 ? url.split('?')[1] : '';
        var paramList = paramPart.split('&');
        var _params = {};
        for (var i = 0; i < paramList.length; i++) {
            var paramItem = paramList[i].split('=');
            if (paramItem[0] && paramItem[0].length > 0)
                _params[paramItem[0]] = paramItem[1];
        }
        if (key && key != "")
            return _params[key];
        else
            return _params;
    },
    HasParam: function (name) {
        var params = this.Params();
        return params && name in params;
    },
    ParamsCount: function () {
        var params = this.Params();
        var count = 0;
        for (var i in params) {
            if (params.hasOwnProperty(i)) {  // 建议加上判断,如果没有扩展对象属性可以不加
                count++;
            }
        }
        return count;
    }
}

//关闭当前layer窗口
function closeDialog(win) {
    var index = parent.layer.getFrameIndex(win); //先得到当前iframe层的索引
    parent.layer.close(index);
}

//弹出确认窗口
function confirmDialog(confirmMsg, okFun) {
    (parent.layer || layer).confirm(confirmMsg, {icon: 3, title: '询问'},
        function (index) {
            eval('okFun()');
            (parent.layer || layer).close(index);
        });
}

function showMsg(content) {
    (parent.window.layer || layer).msg(content);
}

//弹出窗口
function showDialog(url, options, endFun) {
    var _self = "_self" in options && options._self;
    if (!_self)
        _self = (parent.window.layer || layer);
    var area;
    if (options && options.area) {
        area = options.area;
        if (area[0] && area[0].indexOf('px') > -1 && area[0].replace('px', '') > top.document.body.clientWidth)
            area[0] = "100%";
        if (area[1] && area[1].indexOf('px') > -1 && area[1].replace('px', '') > top.document.body.clientHeight)
            area[1] = "100%";
    } else {
        area = ['50%', '50%'];
    }
    var title = (options && options.title) ? options.title : '';
    _self.open({
        type: 2,
        title: title,
        area: area ? area : ['360px', '280px'],
        offset: (options && options.offset) ? options.offset : 'auto',
        shadeClose: false,
        maxmin: title ? true : false, //开启最大化最小化按钮
        moveOut: (options && "moveOut" in options) ? options.moveOut : true,
        content: url,
        success: function (layero, index) {
            if (area && area[0] == "100%" && area[1] == "100%")
                _self.full(index);

            $(top.document.body).css("overflow-y", "hidden");
        },
        end: function (layero, index) {
            if (endFun)
                eval('endFun(index)');

            $(top.document.body).css("overflow-y", "auto")
        },
        resizing: function (layero) {
            var height = $(layero).height() - $(layero).find(".layui-layer-title").height() - 1;
            $(layero).find("iframe").height(height)
        }
    });
}

//消息弹框提示（layer封装）
function alterMessage(content, title, fun) {
    (parent.window.layer || layer).open({
        title: title ? title : '提示'
        , content: content.replace(/\n/g, "<br/>")
        , end: fun
    });
}

function showMessage(content, options) {

    var dialogContent = `<div class="modal" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="staticBackdropLabel">消息提示</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        ${content}
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal">确定</button>
      </div>
    </div>
  </div>
</div>`;

    $(dialogContent).modal("show");
}

function mobliePhoneValidate(mobilePhone) {
    var myreg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
    if (!myreg.test(mobilePhone)) {
        return false;
    } else {
        return true;
    }
};


var XFileReader = {
    checkAvailable: function () {
        //检测浏览器是否支持FileReader
        if (typeof (FileReader) === 'undefined') {
            alterMessage('抱歉，你的浏览器不支持附件上传功能，请使用现代浏览器操作！');
            return false;
        }
        return true;
    },
    addEventListener: function (fileInputID, readFileFun) {
        var fileInput = document.getElementById(fileInputID);
        if (fileInput == undefined) {
            console.log("监听对象不能为空");
            return;
        }
        if (readFileFun == undefined) {
            console.log("监听事件不能为空");
            return;
        }
        fileInput.addEventListener('change', readFileFun, false);
    }
}