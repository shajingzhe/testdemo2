public class JVMTest {

    private static int count;

    public static void main(String[] args) {
        try{
            method1();
        }catch (Throwable e){
            e.printStackTrace();
            System.out.println(count);
        }
    }

    /**
     * -Xss256k
     */
    private static void method1(){
        count++;
        method1();
    }
}
