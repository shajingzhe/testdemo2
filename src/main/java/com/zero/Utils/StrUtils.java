package com.zero.Utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

public class StrUtils {

    private static final String underLineMark = "_";

    private static final String kebabMark = "-";

    private static final String EMPTY_STRING = "";

    public static boolean equals(String a, String b){
        if(null == a){
            return null == b;
        }
        return a.equals(b);
    }

    public static int getMatchCount(String str, String subStr){
        if(!StringUtils.hasLength(str) || !StringUtils.hasLength(subStr)){
            throw new RuntimeException(
                    String.format(
                            "getMatchCount's str and subStr should not be null or empty:%s,%s.",
                            str, subStr));
        }
        return StringUtils.countOccurrencesOf(str, subStr);
    }

    public static String nonEmptyStr(String value){
        if(!StringUtils.hasLength(value)){
            return EMPTY_STRING;
        }

        return value;
    }

    /**
     * @param name 下划线
     * @return 小驼峰
     */
    public static String underlineTransferSmallHump(String name){
        return symbolTransferSmallCamel(name, underLineMark.toCharArray()[0]);
    }

    @SuppressWarnings("all")
    public static String symbolTransferSmallCamel(String name, Character symbol){
        if(!StringUtils.hasLength(name)) {
            return EMPTY_STRING;
        }

        if(null == symbol){
            throw new RuntimeException("symbol access empty");
        }

        if(nonEmptyContains(name, symbol.toString())){
            CharSequence cs = name;
            int i = 0, csLen = cs.length();
            StringBuilder sbd = new StringBuilder(csLen);
            boolean isUpper = false;

            for(; i < csLen; ++ i){
                char c;
                if(i == 0 && Character.isUpperCase(c = cs.charAt(i))){
                    sbd.append(Character.toLowerCase(c));
                    continue;
                }

                c = cs.charAt(i);
                if(c == symbol){

                    isUpper = true;

                }else if(isUpper){

                    if(sbd.length() == 0){
                        sbd.append(Character.toLowerCase(c));
                    }else{
                        sbd.append(Character.toUpperCase(c));
                    }
                    isUpper = false;
                }else {
                    sbd.append(c);
                }
            }

            return sbd.toString();
        }else{
            int strLen;
            return (strLen = name.length()) > 1
                    ? name.substring(0, 1).toLowerCase() + name.substring(1, strLen)
                    : name.toLowerCase();
        }
    }

    public static boolean nonEmptyContains(String str1, String str2){
        // if str2 "", str1 "XXX", must true, so here return false
        if(!StringUtils.hasLength(str1) || !StringUtils.hasLength(str2)) return false;

        return str1.contains(str2);
    }

    public static String simpleHumpTransferUnderline(String name){
        return symbolTransfer(name, underLineMark);
    }

    public static String simpleHumpTransferKebab(String name){
        return symbolTransfer(name, kebabMark);
    }

    @SuppressWarnings("all")
    private static String symbolTransfer(String name, CharSequence symbol) {
        if(!StringUtils.hasLength(name)) {
            return EMPTY_STRING;
        }

        ExcpUtils.throwExpIfFalse(StringUtils.hasLength(symbol),
                "symbol access empty");
        StringBuilder sbd = new StringBuilder();

        CharSequence cs = name;
        int i = 0, csLen;

        for(; i < (csLen = cs.length()); i++){
            char c = cs.charAt(i);
            if(Character.isUpperCase(c)){
                Character pre = i > 0 ? cs.charAt(i - 1) : null;

                if(null != pre){
                    sbd.append(symbol);
                }

                c = Character.toLowerCase(c);
            }
            sbd.append(c);
        }

        return sbd.toString();
    }

    /**
     * @param name 小驼峰命名Str
     * @return 下划线
     */
    @SuppressWarnings("all")
    public static String humpTransferUnderline(String name){
        // null or empty throw exp
        ExcpUtils.throwExpIfFalse(StringUtils.hasLength(name),
                "when hump transfer to underline, name should not be empty.");
        CharSequence cs = name;
        List<CharSequence> charSequenceList = Lists.newArrayList();

        int temI = 0, i = 0, csLen = 0;

        for (; i < (csLen = cs.length()); i++) {
            char c = cs.charAt(i);
            if(Character.isUpperCase(c)){
                CharSequence csq = cs.subSequence(temI, i);
                if(csq.length() > 0){
                    addCharSequence(charSequenceList, csq);
                    temI = i;
                }
            }
        }

        CharSequence lastSequence = cs.subSequence(temI, csLen);
        if(lastSequence.length() > 0){
            addCharSequence(charSequenceList, lastSequence);
        }

        // actual could not execute this
        if(CollectionUtils.isEmpty(charSequenceList)) return EMPTY_STRING;

        return String.join(underLineMark, charSequenceList);
    }

    private static void addCharSequence(List<CharSequence> charSequenceList,
            CharSequence charSequence) {
        if(null == charSequenceList){
            throw new RuntimeException("charSequenceList could not be null");
        }

        if(null == charSequence || charSequence.length() <= 0){
            throw new RuntimeException("charSequence need non empty");
        }
        char[] csqChars = charSequence.toString().toCharArray();
        char[] initialLowerCsqChar = new char[csqChars.length];
        initialLowerTransfer(initialLowerCsqChar, csqChars);
        charSequenceList.add(new String(initialLowerCsqChar));
    }

    private static void initialLowerTransfer(char[] targetChar, char[] originChar){
        ExcpUtils.throwExpIfFalse(ArrayUtils.isNotEmpty(targetChar),
                "targetChar is empty");
        ExcpUtils.throwExpIfFalse(ArrayUtils.isNotEmpty(originChar),
                "originChar is empty");
        int totalLength;
        ExcpUtils.throwExpIfFalse((totalLength = originChar.length) ==
                        targetChar.length,
                "targetChar'length not equals to originChar's length");

        char[] temp;int tempSize;
        System.arraycopy((temp = new char[]{Character.toLowerCase(originChar[0])}), 0 , targetChar, 0,  (tempSize = temp.length));

        if(totalLength > tempSize){
            System.arraycopy(originChar, tempSize, targetChar, tempSize, totalLength - tempSize);
        }
    }

    /**
     * 获取雪花ID
     * 分布式系统中，有一些需要使用全局唯一ID的场景，有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。Twitter的Snowflake 算法就是这种生成器。
     */
    public static String getSnowflakeId() {
        //注意 IdUtil.createSnowflake每次调用会创建一个新的Snowflake对象，不同的Snowflake对象创建的ID可能会有重复，因此请自行维护此对象为单例，或者使用IdUtil.getSnowflake使用全局单例对象。
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        long id = snowflake.nextId();
        return Long.toString(id);
    }

//	public static void main(String[] args) {
//		System.out.println(underlineTransferSmallHump("_As1_s_ANd1A_aa_"));
//		System.out.println(underlineTransferSmallHump("a"));
//		System.out.println(underlineTransferSmallHump("A"));
//		System.out.println(underlineTransferSmallHump("12abc"));
//		System.out.println(underlineTransferSmallHump("createTime"));
//		System.out.println(underlineTransferSmallHump("create_Time"));
//		System.out.println(underlineTransferSmallHump("Create_time"));
//		System.out.println(underlineTransferSmallHump("Ada1cAa2BDFa"));
//		System.out.println(underlineTransferSmallHump("___aHb__3AH_aJK"));
//	}
}
