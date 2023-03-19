import org.apache.commons.lang3.StringUtils;

public class stringUtils_substringTEST {
    public static void main(String[] args) {
        String a="";
        String[] b=StringUtils.substringsBetween(a,"c","i");
        System.out.println(b.length>0?b[0]:"");
    }
}
