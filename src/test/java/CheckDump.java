import com.google.common.collect.Maps;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.util.RamUsageEstimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @Author shajingzhe
 * @Date 2023/8/28 下午4:52
 */
public class CheckDump {
	public static void main(String[] args) {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("handleEndDate", "办结日期");
		map.put("caseDifficulty", "案件难易程度");
		map.put("mediationResult", "调解结果");
		map.put("involvedMoney", "涉案金额");
		map.put("fulfillTimeLimit", "履行时限");
		map.put("agreementAmount", "协议金额");
		map.put("factsAndMatters", "纠纷主要实事、争议事项");
		map.put("agreementContent", "协议内容");

		List m = new ArrayList<>();
		m.add("handleEndDate");
		m.add("caseDifficulty");
		m.add("mediationResult");
		m.add("involvedMoney");
		m.add("fulfillTimeLimit");
		m.add("agreementAmount");
		m.add("factsAndMatters");
		m.add("agreementContent");


		HashSet<String> set = new HashSet<>();
		set.add("handleEndDate");
		set.add("caseDifficulty");
		set.add("mediationResult");
		set.add("involvedMoney");
		set.add("fulfillTimeLimit");
		set.add("agreementAmount");
		set.add("factsAndMatters");
		set.add("agreementContent");

		System.out.println("map size 100, value is " + RamUsageEstimator.sizeOf(map));
		System.out.println("map size 100, value is " + RamUsageEstimator.humanSizeOf(map));
		System.out.println("map size 100, value is " + RamUsageEstimator.humanSizeOf(m));
		System.out.println("map size 100, value is " + RamUsageEstimator.humanSizeOf(set));

	}
}
