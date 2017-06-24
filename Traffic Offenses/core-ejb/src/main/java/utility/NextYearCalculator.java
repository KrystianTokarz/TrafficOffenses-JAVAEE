package utility;

import java.util.Calendar;
import java.util.Date;

public class NextYearCalculator {

    private NextYearCalculator(){
    }

    public static Date calculateNextYearDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
//        cal.add(Calendar.YEAR, 1);
        return  calendar.getTime();
    }
}
