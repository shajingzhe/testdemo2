public class stackTest {
    public static void main(String[] args) {
        method1();
    }

    private static void method1() {
        method2();
    }

    private static int method2() {
        int a = 1 + 2;
        int b = 2 + 9;
        return a+b;
    }
}
