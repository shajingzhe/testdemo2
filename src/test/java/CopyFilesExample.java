import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;

public class CopyFilesExample {

    public static void main(String[] args) throws InterruptedException,
            IOException {

        File source = new File("C:\\Users\\admin\\Desktop\\DM8安装手册.pdf");
        File dest = new File("C:\\Users\\admin\\Desktop\\ls\\test\\DM8安装手册1.pdf");

        // copy file using FileStreamslong start = System.nanoTime();
        long end;
        long start=System.nanoTime();
        copyFileUsingFileStreams(source, dest);
        System.out.println("Time taken by FileStreams Copy = "
                + (System.nanoTime() - start));

        // copy files using java.nio.FileChannelsource = new File("C:\\Users\\nikos7\\Desktop\\files\\sourcefile2.txt");
        dest = new File("C:\\Users\\admin\\Desktop\\ls\\test\\DM8安装手册2.pdf");
        start = System.nanoTime();
        copyFileUsingFileChannels(source, dest);
        end = System.nanoTime();
        System.out.println("Time taken by FileChannels Copy = " + (end - start));

        // copy file using Java 7 Files classsource = new File("C:\\Users\\nikos7\\Desktop\\files\\sourcefile3.txt");
        dest = new File("C:\\Users\\admin\\Desktop\\ls\\test\\DM8安装手册3.pdf");
        start = System.nanoTime();
        copyFileUsingJava7Files(source, dest);
        end = System.nanoTime();
        System.out.println("Time taken by Java7 Files Copy = " + (end - start));

        // copy files using apache commons iosource = new File("C:\\Users\\nikos7\\Desktop\\files\\sourcefile4.txt");
        dest = new File("C:\\Users\\admin\\Desktop\\ls\\test\\DM8安装手册4.pdf");
        start = System.nanoTime();
        copyFileUsingApacheCommonsIO(source, dest);
        end = System.nanoTime();
        System.out.println("Time taken by Apache Commons IO Copy = "
                + (end - start));

    }

    private static void copyFileUsingFileStreams(File source, File dest)
            throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    private static void copyFileUsingFileChannels(File source, File dest)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    private static void copyFileUsingJava7Files(File source, File dest)
            throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    private static void copyFileUsingApacheCommonsIO(File source, File dest)
            throws IOException {
        FileUtils.copyFile(source, dest);
    }

}