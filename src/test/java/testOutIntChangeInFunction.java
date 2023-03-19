public class testOutIntChangeInFunction {
    public static void main(String[] args) {
        int i=0;
        add(i=1);
        System.out.println(i);
    }

    public static void add(int i){
        i=1;
    }
}
