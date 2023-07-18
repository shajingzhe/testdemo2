import java.net.InetAddress;

/**
 * @Author shajingzhe
 * @Date 2023/7/18 下午3:06
 */
public class NetChat {
	public static void checkHosts(String subnet){
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
	}
	public static void main ( String[] args ) {
		checkHosts("192.168.0");
	}
}
