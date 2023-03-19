import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListFilt {
    public static void main(String[] args) throws UnsupportedEncodingException {
        List<Integer> list=new ArrayList<>();
        list.add(2);
        list.add(4);
        list.add(20);
        list.add(2130);
        list.add(38);
//过滤包含2的数据
        list=list.stream().filter(v ->!v.toString().contains("2")).collect(Collectors.toList());
        for (Integer integer : list) {
            System.out.println(integer);
        }

    }
}
