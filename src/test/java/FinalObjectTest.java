import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FinalObjectTest {
    public static void main(String[] args) {
        int num = 4;
        final String[] values = new String[num];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = new Date();

        for (int i = 0; i < num; i++) {
            values[i] = sdf.format(date);
            date=stepMonth(date, -1);
        }
        int a=1;

    }

    public static Date stepMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }
}
