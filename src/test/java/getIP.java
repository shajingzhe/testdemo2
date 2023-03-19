import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

public class getIP {
    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost());
    }
}
