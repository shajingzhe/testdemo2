var EditorForm = {
    _rootElement: null,
    Select2: [],
    Data: null,
    InitForm: function (formElement, data) {
        this._rootElement = formElement;
        this.Select2 = [];
        var $selects = $(formElement).find("select[data-form-type=select2]");
        if ($selects && $selects.length > 0) {
            for (let i = 0; i < $selects.length; i++) {
                var selector = $selects[i];
                let dataField = $(selector).attr('data-field');
                let selectValue = (data && dataField) ? data[dataField] : "";//todo s 存在疑问，当值为null的时候不搜索（不触发ajax请求），导致，如先点有值的，后点为null的，后一个初始化异常，显示值为前一个值；且初始化时，数值未找到，空格数值也不会覆盖前一个数值
                this.Select2.push({id: $(selector).attr('id'), selector: null, data: []});
                this.select2Init(selector, selectValue, formElement);
            }
        }

        var timePickers = $(formElement).find("input[data-form-type=datepicker]");
        if (timePickers && timePickers.length > 0) {
            for (var i = 0; i < timePickers.length; i++) {
                var $timePicker = $(timePickers[i]);
                var dataFormat = $timePicker.attr('data-format');
                var dataField = $timePicker.attr('data-field');
                if (data && dataField && dataField in data && data[dataField]) {
                    var inputValue = data[dataField];
                    inputValue = dataFormat ? moment(inputValue).format(dataFormat) : inputValue;
                    $timePicker.val(inputValue);
                }
                var dataRange = $timePicker.attr('data-range');
                var dataMinDate = $timePicker.attr('data-minDate');
                var dataMaxDate = $timePicker.attr('data-maxDate');
                var zIndex = $timePicker.attr('data-z-index');
                var pickerFormat = "yyyy-mm-dd hh:ii:ss";
                if (dataFormat && dataFormat.length > 0)
                    pickerFormat = pickerFormat.substr(0, dataFormat.length);
                if (pickerFormat === "yyyy-mm-dd" || pickerFormat === "yy-mm-dd") {
                    var options = {
                        changeMonth: true,
                        changeYear: true,
                        showOtherMonths: false,
                        dateFormat: "yy-mm-dd",
                    };
                    if (dataRange)
                        options.yearRange = dataRange;
                    if (dataMinDate)
                        options.minDate = dataMinDate;
                    if (dataMaxDate)
                        options.maxDate = dataMaxDate;
                    options.beforeShow = function (input, dp_inst) {
                        if (zIndex)
                            $(input).css("z-index", zIndex)
                    };
                    options.onClose = function (dateText, dp_inst) {
                        if (zIndex)
                            $(this).css("z-index", 0)
                    };
                    $timePicker.datepicker(options);
                } else {
                    $timePicker.datetimepicker({
                        changeMonth: true,
                        dateFormat: "yy-mm-dd",
                        beforeShow: function (input, dp_inst) {
                            if (zIndex)
                                $(input).css("z-index", zIndex)
                        },
                        onClose: function (dateText, dp_inst) {
                            if (zIndex)
                                $(this).css("z-index", 0)
                        }
                    });
                }
            }
        }
        this.FillInputValue(data);
        $(formElement).find('input.form-control').attr("autocomplete", "off");
    },
    select2Init: function (selector, select_value, parentElement) {
        var options = $(selector).attr('data-select2-options');
        if (options)
            options = JSON.parse(options);
        if (options)
            options.theme = "bootstrap-5";
        if (options && !options.language)
            options.language = "zh-CN";
        var dataSource = options && options.source;
        if (!dataSource) {
            $(selector).select2(options);
            return false;
        }
        if (!dataSource.text && dataSource.value)
            dataSource.text = dataSource.value;
        var queryParam = {
            fields: dataSource.fields,
            orderBy: dataSource.orderBy,
            group: dataSource.group
        };
        if (dataSource.length)
            queryParam.length = dataSource.length;
        var select2_Options = {
            language: options.language,
            ajax: {
                url: dataSource.url,
                dataType: 'json',
                data: function (params) {
                    var query = queryParam;
                    query.search = params.term;
                    if ($(selector).attr("limitValue"))
                        query.limitValue = $(selector).attr("limitValue");
                    return query;
                },
                processResults: function (response) {
                    if (response.msg)
                        console.log(response.msg);

                    var data = JSON.parse(response.data);
                    var items = [];
                    if (options.allowClear) {// todo s 待阅读
                        var defaultOption = $(selector).find("option:eq(0)");
                        if (defaultOption) {
                            var defaultValue = $(defaultOption).attr("value");
                            var defaultText = $(defaultOption).text() ? $(defaultOption).text() : $(defaultOption).attr("text");
                            //items.push({id: defaultValue, text: defaultText, data: null});
                        }
                    }
                    if (data == null) {
                        return {results: items};
                    }
                    for (var i = 0; i < data.length; i++) {
                        var itemData = data[i];
                        var label = dataSource.label;
                        var text = dataSource.text;
                        var item = {
                            id: itemData[dataSource.value],
                            data: itemData
                        };
                        item.text = label ? data_tramsform(label, itemData) : itemData[text];
                        items.push(item);
                    }
                    addOption(selector, data);
                    return {results: items};
                }
            },
            placeholder: options && options.placeholder ? options.placeholder : '',
            width: options && options.width ? options.width : '100%',
            tags: options && options.tags ? options.tags : false,
            allowClear: options && options.allowClear ? true : false,
            multiple: options && options.multiple ? true : false,
            theme: options.theme,
            templateSelection: function (data, container) {
                if (dataSource.label && dataSource.text && dataSource.text !== dataSource.label && data.data)
                    return data.data[dataSource.value];
                else
                    return data.text;
            }
        };
        if (options && "minimumResultsForSearch" in options)
            select2_Options.minimumResultsForSearch = options.minimumResultsForSearch;
        if (parentElement) {
            select2_Options.dropdownParent = $(parentElement);
        }
        var $select2 = $(selector).select2(select2_Options);
        if (dataSource.selected)
            $select2.on('select2:select', function (e) {
                var selected_data = e.params.data;
                for (var i = 0; i < dataSource.selected.length; i++) {
                    var selected = dataSource.selected[i];
                    var linkedValue = selected_data.data[selected.value];
                    if (linkedValue) {
                        setSelect2Editor(selected.target, linkedValue);
                    }
                }
            });
        if (select_value) {
            if (options && options.multiple && options.multiple_split) {
                var select_value_list = select_value.split(options.multiple_split);
                for (var i = 0; i < select_value_list.length; i++)
                    $select2.append(new Option(select_value_list[i], select_value_list[i], true, true)).trigger('change');
            } else {
                let option = new Option(select_value, select_value, true, true);
                if (dataSource.preselect) {
                    let param = {
                        query_initialValue: select_value,
                    };
                    var response = jqueryAjax.post(dataSource.url, param);
                    if (response && response.data) {
                        var preselected_item = JSON.parse(response.data);
                        var text = preselected_item[dataSource.text] ? preselected_item[dataSource.text] : dataSource.text;
                        option.text = data_tramsform(text, preselected_item);
                        $select2.append(option).trigger('change');
                        $select2.trigger({
                            type: 'select2:select',
                            params: {data: preselected_item}
                        });
                        if (this && this.Select2) {
                            var itemSelector = this.Select2.find(p => p.id == $(selector).attr('id'))
                            if (itemSelector) {
                                itemSelector.data.push(preselected_item);
                            } else {
                                this.Select2.push({id: $(selector).attr('id'), selector: null, data: preselected_item});
                            }
                        }
                    }
                } else {
                    $select2.append(option).trigger('change');
                }
            }
        }
    },
    FillInputValue: function (data) {
        if (!data)
            return false;
        this.Data = data;
        var inputs = [];
        $(this._rootElement).find('input[data-field]').each(function () {
            inputs.push($(this));
        });
        $(this._rootElement).find('textarea[data-field]').each(function () {
            inputs.push($(this));
        });
        for (var i = 0; i < inputs.length; i++) {
            var $input = $(inputs[i]);
            var dataType = $input.attr('data-type');
            var inputType = $input.attr('data-input-type');
            var dataField = $input.attr('data-field');
            if (!dataField || !(dataField in data))
                continue;
            var inputValue = data[dataField];
            if (!inputValue)
                inputValue = "";

            if ($input.attr("type") === 'checkbox' && inputValue) {
                $input.attr("checked", "checked");
            }

            var dataFormat = $input.attr('data-format');
            if (inputValue && dataFormat) {
                if ((inputType === 'datetimepicker' || dataType === 'datetime'))
                    inputValue = moment(inputValue).format(dataFormat);
            }
            $input.val(inputValue);
        }
    },
    ValidateForm: function (form) {
        if (!form && document.forms.length)
            form = document.forms[0];
        if (!form)
            return false;

        return $(form).validate({
            rules: {},
            messages: {},
            errorPlacement: function (error, element) {
                $(element).addClass("is-invalid");
                error.addClass("invalid-tooltip");
                error.insertBefore(element);
            }, success: function (error, element) {
                error.remove();
                $(element).removeClass("is-invalid");
            }
        }).form();
    }
}

let Utils = {
    /**
     * 校验只要是数字（包含正负整数，0以及正负浮点数）就返回true
     **/
    isNumber: function (val) {
        var regPos = /^[0-9]+.?[0-9]*/; //判断是否是数字。
        if (regPos.test(val)) {
            return true;
        } else {
            return false;
        }
    },
    /**
     * 保留2位小数
     * @param value
     * @returns {string|number}
     */
    returnFloat: function (value) {
        var value = Math.round(parseFloat(value) * 100) / 100;
        var xsd = value.toString().split(".");
        if (xsd.length == 1) {
            value = value.toString() + ".00";
            return value;
        }
        if (xsd.length > 1) {
            if (xsd[1].length < 2) {
                value = value.toString() + "0";
            }
            return value;
        }
    },

    /**
     * 初始化年份列表
     */
    initYearSelect: function (idName, clear, startYear) {
        if (clear) {
            $(idName).empty();
        }
        var apply_years = [];
        var lastYear = new Date().getFullYear();
        let year = startYear != null ? startYear : 2015
        for (var i = lastYear; i >= year; i--) {
            apply_years.push({id: i, text: i});
            $(idName).append('<option label="' + i + '" value="' + i + '"></option>');
        }
        $(idName).val(lastYear).trigger("change");
        $(idName).change(function () {
            queryData();
        });
    },
    checkIDCardNo: function (IDCardNo) {
        var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        return reg.test(IDCardNo);
    }
}

let PlanFormProcess = {
    //计划书批示
    PlanApproval: 4,
    // 工作室申请书审批
    StudioAppFormApproval: 6
}

function addOption(selector, data) {
    let id = $(selector).attr('id');
    let arr = EditorForm.Select2;
    for (let i = 0; i < arr.length; i++) {
        let orginalData = arr[i];
        if (orginalData.id == id) {
            data = data.concat(orginalData.data);
            data = delObj(data);
            var itemSelector = EditorForm.Select2.find(p => p.id == $(selector).attr('id'))
            if (itemSelector)
                itemSelector["data"] = data;
            break;
        }
    }
}

function delObj(obj) {// 去除相同项
    var uniques = [];
    var stringify = {};
    for (var i = 0; i < obj.length; i++) {
        var keys = Object.keys(obj[i]);
        keys.sort(function (a, b) {
            return (Number(a) - Number(b));
        });
        var str = '';
        for (var j = 0; j < keys.length; j++) {
            str += JSON.stringify(keys[j]);
            str += JSON.stringify(obj[i][keys[j]]);
        }
        if (!stringify.hasOwnProperty(str)) {
            uniques.push(obj[i]);
            stringify[str] = true;
        }
    }
    uniques = uniques;
    return uniques;
}

function setSelect2Editor(element, data, needDataExists) {
    if ($(element).find("option[value='" + data + "']").length) {
        $(element).val(data).trigger('change');
    } else if (!needDataExists) {
        // Create a DOM Option and pre-select by default
        var newOption = new Option(data, data, true, true);
        // Append it to the select
        $(element).append(newOption).trigger('change');
    }
}

function getSelect2Source(selector) {
    var paramsStr = $(selector).attr('data-select2-source');
    if (paramsStr)
        return JSON.parse(paramsStr);
    else
        return null;
}

function data_tramsform(value, data) {
    var temp_value = value;
    while (temp_value.match(/(\${\w+?})/)) {
        var _matched_items = temp_value.match(/(\${\w+?})/);
        if (_matched_items) {
            var _item_field = _matched_items[0].match(/\w+/)[0].toLowerCase();
            temp_value = temp_value.replace(_matched_items[0], data[_item_field]);
        }
    }
    if (temp_value !== value)
        value = temp_value;
    return value;
}

var XFile = {
    findFileInfo: function (ownerID, category, tag) {
        var fileList = this.queryFileList(ownerID, category, tag);
        return fileList && fileList.length > 0 ? fileList[0] : null;
    },
    queryFileList: function (ownerID, category, tag) {
        var response = jqueryAjax.getJson(`/xfile/query?ownerid=${ownerID}&category=${category}&tag=${tag}`);
        if (response && response.data) {
            return JSON.parse(response.data);
        } else {
            if (response.msg)
                alterMessage(response.msg);
            else
                layer.msg("加载失败");
        }
        return null;
    },
    showImageViewer: function (id, tag) {
        if (!id) {
            layer.msg("缺少参数，无法处理！");
            return false;
        }
        var image = new Image();

        image.src = "/xfile/download/image?id=" + id;
        if (tag)
            image.alt = tag;

        var viewer = new Viewer(image, {
            zIndex: new Date().getTime(),
            hidden: function () {
                viewer.destroy();
            },
        });

        viewer.show();
    },
    showImage: function (id, options, endFun) {
        var _self = window.top.layer;
        var url = `/xfile/view/image?id=${id}`;
        var title = (options && options.title) ? options.title : '';
        var area = (options && options.area) ? options.area : ['80%', '80%'];
        var offset = (options && options.offset) ? options.offset : 'auto';
        var moveOut = (options && "moveOut" in options) ? options.moveOut : true;
        _self.open({
            type: 2,
            title: title,
            area: area,
            offset: offset,
            shadeClose: false,
            maxmin: title ? true : false, //开启最大化最小化按钮
            moveOut: moveOut,
            content: url,
            success: function (layero, index) {
                if (area && area[0] == "100%" && area[1] == "100%")
                    _self.full(index);
            },
            end: function (layero, index) {
                if (endFun)
                    eval('endFun(index)');
            },
            resizing: function (layero) {
                var height = $(layero).height() - $(layero).find(".layui-layer-title").height() - 1;
                $(layero).find("iframe").height(height)
            }
        });
    }

}

var Uploader = {
    initUploader: function (uploadPicker, fileListView, uploaderAccept, parameters) {
        var uploader = WebUploader.create({
            // swf文件路径
            swf: BASE_URL + '/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: '/xfile/upload?ownerID=' + parameters.ownerid,
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: uploadPicker,
            accept: uploaderAccept,
            auto: true,
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false
        });
        if (parameters && parameters.catalog)
            uploader.options.formData.catalog = parameters.catalog;
        if (parameters && parameters.category)
            uploader.options.formData.category = parameters.catalog;
        if (parameters && parameters.tag)
            uploader.options.formData.tag = parameters.tag;

        // 当有文件被添加进队列的时候
        uploader.on('fileQueued', function (file) {
            if (uploaderAccept.mimeTypes === 'image/*') {
                uploader.makeThumb(file, function (error, ret) {
                    if (error) {
                        console.log("预览错误：" + error);
                    } else {
                        $(fileListView).append('<div id="' + file.id + '" class="item">' +
                            '<img src="' + ret + '" class="img-thumbnail" alt="">' +
                            '<p class="state">等待上传...</p>' +
                            '</div>');
                    }
                });

            } else {
                $(fileListView).append('<div id="' + file.id + '" class="item">' +
                    '<h4 class="info">' + file.name + '</h4>' +
                    '<p class="state">等待上传...</p>' +
                    '</div>');
            }
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress .progress-bar');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $(`<div class="progress progress-striped active">
                        <div class="progress-bar" role="progressbar" style="width: 0%"></div>
                        </div>`).appendTo($li).find('.progress-bar');
            }

            if ($li.find('p.state').length > 0)
                $li.find('p.state').text('上传中');

            $percent.css('width', percentage * 100 + '%');
        });
        uploader.on('uploadSuccess', function (file) {
            if ($('#' + file.id).find('p.state').length > 0)
                $('#' + file.id).find('p.state').text('已上传');
        });

        uploader.on('uploadError', function (file) {
            if ($('#' + file.id).find('p.state').length > 0)
                $('#' + file.id).find('p.state').text('上传出错');
        });

        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').fadeOut();
        });
    }
}

function BASE64() {

    // private property
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // public method for encoding
    this.encode = function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = _utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
                _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
                _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    }

    // public method for decoding
    this.decode = function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = _keyStr.indexOf(input.charAt(i++));
            enc2 = _keyStr.indexOf(input.charAt(i++));
            enc3 = _keyStr.indexOf(input.charAt(i++));
            enc4 = _keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = _utf8_decode(output);
        return output;
    }

    // private method for UTF-8 encoding
    _utf8_encode = function (string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }
        return utftext;
    }

    // private method for UTF-8 decoding
    _utf8_decode = function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while (i < utftext.length) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}