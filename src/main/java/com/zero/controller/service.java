package com.zero.controller;

import com.zero.Utils.Utils;
import cn.hutool.core.convert.Convert;
import com.zero.entity.XFileInfo;
import com.zero.entity.SysParameter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class service {

    /**
     * 解析ET-List3参数文档
     */
    @Test
    public void parseSysParameterText() {
        String originalText;
        try {
            originalText = Utils.fileRead("C:\\Users\\admin\\Desktop\\新建文本文档.txt");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        String[] attributeTextArray = new String[10];
        String forPermission;
        String displayName;
        String parameterName;
        String[] forPermissionArray = new String[1];
        String[] displayNameArray = new String[1];
        String[] parameterNameArrayString = new String[1];
        String[] parameterNameArrayBool = new String[1];
        String[] parameterNameArrayInt = new String[1];
        List<SysParameter> sysParameterList = new ArrayList<>();

        attributeTextArray = StringUtils.substringsBetween(originalText, "[Exatell.Function", "set; }");
        for (String attributeText : attributeTextArray) {
            SysParameter sysParameter = new SysParameter();
            forPermissionArray = StringUtils.substringsBetween(attributeText, "ForPermission = ", "),");
            forPermission = (forPermissionArray != null && forPermissionArray.length > 0 && Convert.toBool(forPermissionArray[0])) ? "Y" : "";
            sysParameter.setForPermission(forPermission);
            displayNameArray = StringUtils.substringsBetween(attributeText, "DisplayName(\"", "\")");
            displayName = displayNameArray != null && displayNameArray.length > 0 ? displayNameArray[0] : "";
            sysParameter.setDisplayName(displayName);
            parameterNameArrayString = StringUtils.substringsBetween(attributeText, "public string ", " { get;");
            parameterNameArrayBool = StringUtils.substringsBetween(attributeText, "public bool ", " { get;");
            parameterNameArrayInt = StringUtils.substringsBetween(attributeText, "public int ", " { get;");

            if (parameterNameArrayString != null && parameterNameArrayString.length > 0) {
                parameterName = parameterNameArrayString.length > 0 ? parameterNameArrayString[0] : "";
            } else if (parameterNameArrayBool != null && parameterNameArrayBool.length > 0) {
                parameterName = parameterNameArrayBool.length > 0 ? parameterNameArrayBool[0] : "";
            } else if (parameterNameArrayInt != null && parameterNameArrayInt.length > 0) {
                parameterName = parameterNameArrayInt.length > 0 ? parameterNameArrayInt[0] : "";
            } else {
                parameterName = "";
            }
            sysParameter.setParameterName(parameterName);
            sysParameterList.add(sysParameter);
        }
        LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
        aliasMap.put("forPermission", "可授权");
        aliasMap.put("parameterName", "参数");
        aliasMap.put("displayName", "说明");
        List<Object> objectList = new ArrayList<>();
        objectList.addAll(sysParameterList);

        try {
            byte[] fileBytes = fileBytes = Utils.creatXls(objectList, aliasMap);
            String fileName = "ET-List3参数文档.xls";
            XFileInfo xfileInfo = new XFileInfo("系统工具生成文件");
            MultipartFile file = new MockMultipartFile(fileName, fileBytes);
            xfileInfo.setFileName(fileName);
            xfileInfo.setFile(file);
            Utils.uploadFile(xfileInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.out.println("生成成功");
        return;

    }
}
