public class TryCatchTest {

    public static void main(String[] args) {
        try{
            throw new RuntimeException("抛出异常");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("继续执行");
    }
}
