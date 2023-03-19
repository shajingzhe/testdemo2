import java.io.UnsupportedEncodingException;

public class utf8 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String a="阿松大";
        String ret =new String(a.getBytes("utf-8"),"ISO-8859-1");
        System.out.println(ret);
        String test=new String(a.getBytes("ISO-8859-1"),"utf-8");
        System.out.println(test);

    }
}
