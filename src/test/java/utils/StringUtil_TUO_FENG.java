package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * @Author shajingzhe
 * @Date 2023/12/12 上午11:29
 */
public class StringUtil_TUO_FENG {

	public static String toUpperCaseUnderscore(String input, String delimiter) {
		if (input == null || input.isEmpty()) {
			return input;
		}

		String[] words = input.split(delimiter);
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < words.length; i++) {
			if (i > 0) {
				result.append("_");
			}
			result.append(words[i].toUpperCase());
		}

		return result.toString();
	}

	public static void main(String[] args) {
		String result="Underscore String:";
		LinkedHashSet<String> hashSet=new LinkedHashSet<>(Arrays.asList("JusticeBureau"));
		Iterator<String> iterator = hashSet.iterator();
		while(iterator.hasNext()){
			result=result+toUpperCaseUnderscore(iterator.next(), "(?=[A-Z])")+",";
		}

		System.out.println(result);
	}

}
