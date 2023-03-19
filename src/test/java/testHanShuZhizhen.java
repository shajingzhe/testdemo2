public class testHanShuZhizhen {
    public static void main(String[] args) {
        Student student=new Student();
        String a = "Runoob";
        new testHanShuZhizhen().add(a);
        System.out.println(a);
        new testHanShuZhizhen().setStudent(student);
        System.out.println(student.toString());

    }

    private void add(String m) {
        m=m.replace("u","o");
        System.out.println("数值："+m);
    }

    private void setStudent(Student m) {
        m.setAge("123");
        m.setName("7777");
    }

    public class student{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        private String name;
        private String age;

        @Override
        public String toString() {
            return "student{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }
}
