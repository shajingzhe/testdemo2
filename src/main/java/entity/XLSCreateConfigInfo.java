package entity;


import java.util.LinkedHashMap;
import java.util.List;


public class XLSCreateConfigInfo {
    private List<Object> objectList;//数据  每行数据
    private LinkedHashMap<String, String> aliasMap;//名称标题（xls第一行显示内容） 第一个string:java 数据类中对应属性名 。第二个string:xls中显示的标题
    private String sheetName;//分表名

    public List<Object> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
    }

    public LinkedHashMap<String, String> getAliasMap() {
        return aliasMap;
    }

    public void setAliasMap(LinkedHashMap<String, String> aliasMap) {
        this.aliasMap = aliasMap;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }
}
