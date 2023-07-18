import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class getIP {
	private static String a = "123";
	String e;
	private static final Logger logger = LoggerFactory.getLogger(getIP.class);

	public static void main(String[] args) throws UnknownHostException {
		String m = "2411212467";
		a = "43333";
		System.out.println(InetAddress.getLocalHost());
		a = "asdf";
		m.equals("2345");
		final int a = 12;
		String d;
		int i = 45;
		add(i);
		System.out.println("i:" + i);
		String f = null;
		logger.info("asdf:{}", f);
	}

	static void add(int m) {
		++m;
	}

}
