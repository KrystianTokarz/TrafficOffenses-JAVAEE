package utility;

import com.sun.media.jfxmedia.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormatter {

    private DateFormatter() {
    }

    public static Date formatDate(String date, String pattern){
        DateFormat df = new SimpleDateFormat(pattern);
        Date formatDate = null;
        try {
            formatDate =  df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatDate;
    }

    public static String formatDate(Date date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

}
