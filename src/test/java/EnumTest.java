import entity.SessionEvaluation;
import org.apache.commons.lang3.EnumUtils;

/**
 * @author shajingzhe
 * @date 2023/4/14 上午10:59
 */
public class EnumTest {
	public static void main(String[] args) {

		//String a =SessionEvaluation.NOTSATISFIED;
		boolean verysatisfied = EnumUtils.isValidEnum(SessionEvaluation.class, "12345");
		System.out.println(verysatisfied);

		System.out.println(SessionEvaluation.NOTSATISFIED.getText());
	}
}
