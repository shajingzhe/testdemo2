import cn.hutool.core.io.file.FileNameUtil;

import java.io.File;
import java.io.IOException;

public class getPackageFromPathName {
    public static void main(String[] args) throws IOException {
        System.out.println("文件有如下：");
        //表示一个文件路径
        File file = new File("C:\\Users\\admin\\Desktop\\ls\\test\\DM8安装手册1.pdf");
        System.out.println(FileNameUtil.getName(file));
    }

}
