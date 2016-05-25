package example.codeclan.com.alforii;

/**
 * Created by user on 24/05/2016.
 */
public class AlarmClock {


    int hour;
    int min;
    String reason;

    public AlarmClock(int hour, int min, String reason){
        this.hour= hour;
        this.min= min;
        this.reason = reason;
    }

    public void delete(){
        //go into database and kill it
    }

    public void save(){
        //go into database and save it.
    }
}
