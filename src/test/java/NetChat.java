import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 *  同wifi下ip的获取
 * @Author shajingzhe
 * @Date 2023/7/18 下午3:06
 */
@Slf4j
public class NetChat {
	public static void checkHosts(String subnet){
		long start = System.currentTimeMillis();
		int timeout=1000;
		for (int i=1;i<255;i++){
			System.out.println("ip:"+i);
			String host=subnet + "." + i;
			try{
				if (InetAddress.getByName(host).isReachable(timeout)){
					System.out.println(host + " is reachable");
				}
			}catch(Exception e){
				System.out.println( "None at " + host );
			}
		}
		long end = System.currentTimeMillis();
		log.info(String.format("Total Time：%d ms", end - start));
	}
	public static void main ( String[] args ) {
		checkHosts("192.168.0");
	}
}
