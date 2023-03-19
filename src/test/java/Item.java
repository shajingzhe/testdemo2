import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Item implements Delayed {


    /**
     * 触发时间
     */
    private  long time;

    String name;

    public Item(String name,long time,TimeUnit unit) {
        this.name = name;
        this.time = System.currentTimeMillis()+(time > 0? unit.toMillis(time):0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time-System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        Item item = (Item) o;
        long diff = this.time - item.time;
        if(diff <= 0)
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Item{" +
                "time=" + time +
                ", name='" + name + '\'' +
                '}';
    }
}