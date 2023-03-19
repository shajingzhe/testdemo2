import cn.hutool.core.convert.Convert;

public class convert2Int {
    public static void main(String[] args) {
        String fileName = "发生的";
        int a = Convert.toInt(fileName, 0);
        System.out.println(a);

        String fileType = ".jpg";
        if (fileType != null && fileType.startsWith(".")) {
            fileType = fileType.substring(1);
            System.out.println(fileType);
        }
    }
}
