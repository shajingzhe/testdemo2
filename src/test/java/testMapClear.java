import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class testMapClear {
    public static void main(String[] args) throws UnsupportedEncodingException {

        LinkedHashMap<String, String> aliasMap = new LinkedHashMap<>();
        aliasMap.clear();
        aliasMap.put("objectType", "评审材料");
        aliasMap.put("appSno", "申报编号");
        aliasMap.put("expertBizNo", "评审编号");
        aliasMap.put("subjectName", "课题名称");
        aliasMap.put("applicantsName", "申请人");
        aliasMap.put("workUnit", "申请人所在单位");
        aliasMap.put("applicantsMobile", "申请人移动电话");
        List<String> stringList=new ArrayList<>();
        stringList.add("123");
        stringList.add("324");
        stringList.add("234");
        stringList.add("234");
        EditConfigInfo editConfigInfo=new EditConfigInfo();
        editConfigInfo.setAliasMap(aliasMap);
        aliasMap.clear();
        aliasMap=new LinkedHashMap<>();
        String a="123";
        editConfigInfo.setSheetName(a);
        a=null;
        editConfigInfo.setStringList(stringList);
        //stringList=new ArrayList<>();
        stringList.clear();
        System.out.println("123");
    }

}

class EditConfigInfo {
    private List<Object> objectList;//数据
    private LinkedHashMap<String, String> aliasMap;//名称
    private String sheetName;//分表名
    private  List<String> stringList;


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

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }
}
