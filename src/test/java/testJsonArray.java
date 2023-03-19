import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import java.io.UnsupportedEncodingException;

public class testJsonArray {
    public static void main(String[] args) throws UnsupportedEncodingException {
        int[] a={1,2};
        String[] b={"123","asdf"};
        String jsonArray=JSONUtil.parse(b).toString();
        System.out.println(jsonArray);
        String c ="[\"123\",\"asdf\"]";
        JSONArray array=JSONUtil.parseArray(c);
        for(Object d:array){
            System.out.println(d);
        }
    }
}
