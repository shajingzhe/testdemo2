public class testFormat {
    public static void main(String[] args){
        String b ="[{\"pageNum\":1, \"x\": 10, \"y\": 100, \"text\": \"%s\"},{\"pageNum\":-1, \"x\": 10, \"y\": 100, \"text\": \"%\"},{\"pageNum\":-1, \"x\": 30 ,\"y\": 100, \"text\": \"%s\"},{\"pageNum\":-1, \"x\": 50, \"y\": 100, \"text\": \"%s\"},{\"pageNum\":-1, \"x\": 70, \"y\": 100, \"text\": \"%s\"}]";
        String a=String.format(b, "1", "2","3","4","5");
        System.out.println(a);
    }
}
