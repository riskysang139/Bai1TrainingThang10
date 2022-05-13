package com.example.moviefilm.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.TypedValue;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cuonglv on 8/16/2021
 */
public class Converter {

    private static DecimalFormat digitalNumberDecimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);

    private static String a = "ĂẮẶẰÂẤẦẬăắằặâấầậÁÀẠáàạẲẴẳẵẨẪẩẫẢÃảã";
    private static String e = "éèẹÉÈẸÊẾỀỆêếềệẺẼẻẽỂỄểễ";
    private static String i = "íìịÍÌỊỈĨỉĩ";
    private static String o = "ÓÒỌóòọÔỐỒỘôốồộƠỚỜỢơớờợỎÕỏõỔỖổỗỞỖởỡ";
    private static String u = "ÚÙỤúùụƯỨỪỰưứừựỦŨủũỬỮửữ";
    private static String y = "ÝỲỴýỳỵỶỸỷỹ";
    private static String d = "Đđ";
    public static final DecimalFormat df = new DecimalFormat("0.0");
    public static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * convert 1000 to 1.000đ
     *
     * @param value
     * @return
     */
    public static String convertThousandMoneyToStringCommaDong(int value) {
        NumberFormat formatter = new DecimalFormat("#,###");
        StringBuilder builder = new StringBuilder();
        if (value == 0)
            return builder.append("0").append("đ").toString();
        if (value >= 1000) {
            builder.append(formatter.format(value).replace(",", ".")).append("đ");
        } else builder.append(value).append("đ");
        return builder.toString();
    }

    public static String convertDateToString(Date date, String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * convert 1000 to 1.000
     *
     * @param value
     * @return
     */
    public static String convertThousandMoneyToStringComma(int value) {
        NumberFormat formatter = new DecimalFormat("#,###");
        StringBuilder builder = new StringBuilder();
        if (value == 0)
            return builder.append("0").toString();
        if (value >= 1000) {
            builder.append(formatter.format(value).replace(",", "."));
        } else builder.append(value);
        return builder.toString();
    }

    /**
     * convert 1000 to 1.000đ
     *
     * @param value
     * @return
     */
    public static String convertThousandMoneyToStringCommaDong(Double value) {
        NumberFormat formatter = new DecimalFormat("#,###");
        StringBuilder builder = new StringBuilder();
        if (value == 0)
            return builder.append("0").append("đ").toString();
        if (value >= 1000) {
            builder.append(formatter.format(value).replace(",", ".")).append("đ");
        } else builder.append(value).append("đ");
        return builder.toString();
    }

    /**
     * convert 1234 to 1.2k
     *
     * @param value
     * @return
     */
    public static String convertThousandSaleToString(int value) {
        StringBuilder builder = new StringBuilder();
        if (value == 0) return builder.append("0").toString();
        if (value >= 1000 && value <= 1099) {
            builder.append(value / 1000);
            builder.append("k");
        } else if (value > 1099) {
            builder.append(value / 1000);
            builder.append(",");
            builder.append(value % 1000 / 100);
            builder.append("k");
        } else builder.append(value);
        return builder.toString();
    }

    public static int dpToPx(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static Double getOneDigitalNumberAfterComma(Double value) {
        digitalNumberDecimalFormat.applyPattern("#.#");
        digitalNumberDecimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return Double.valueOf(digitalNumberDecimalFormat.format(value));
    }

    public static String convertStringUTF8(String str) {
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            tmp.append(convertCharUTF8(str.charAt(i)));
        }
        return tmp.toString();
    }

    public static boolean hasUTF8(char ch) {
        String tmp = "" + ch;
        return a.contains(tmp) || e.contains(tmp) || i.contains(tmp)
                || o.contains(tmp) || u.contains(tmp) || y.contains(tmp) || d.contains(tmp);
    }

    public static char convertCharUTF8(char ch) {
        String tmp = "" + ch;
        if (a.contains(tmp)) return 'A';
        else if (e.contains(tmp)) return 'E';
        else if (i.contains(tmp)) return 'I';
        else if (o.contains(tmp)) return 'O';
        else if (u.contains(tmp)) return 'U';
        else if (y.contains(tmp)) return 'Y';
        else if (d.contains(tmp)) return 'D';
        else return Character.toUpperCase(ch);
    }

    public static String convertStringToDate(String date) {
        String displayDate = "";
        try {
            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd/MM/yyyy");
            date = spf.format(newDate);
            displayDate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (date == null || date.length() == 0)
                displayDate = "01/01/2099";
            else
                displayDate = date;
        }
        return displayDate;
    }

    public static String convertStringDate(String date) {
        String displayDate = "";
        try {
            SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = null;
            newDate = spf.parse(date);
            spf = new SimpleDateFormat("dd/MM");
            date = spf.format(newDate);
            displayDate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (date == null || date.length() == 0)
                displayDate = "01/01/2099";
            else
                displayDate = date;
        }
        return displayDate;
    }


    public static String convertDate(String date) {
        String year = "";
        try {
            year = date.substring(0, 4);
        } catch (Exception e) {
            e.printStackTrace();
            year = "1900";
        }
        return year;
    }

    @SuppressLint("DefaultLocale")
    public static String convertTime(long time) {
        String videoTime;
        int duration = (int) time;
        int hour = duration / 3600000;
        int mns = (duration / 60000) % 60000;
        int scs = duration % 60000 / 1000;
        if (hour > 0) {
            videoTime = String.format("%02d:%02d:%02d", hour, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;
    }

    public static String convertTimeStampToDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date;
        try {
            date = DateFormat.format("dd/MM/yyyy", cal).toString();
        } catch (Exception e) {
            e.printStackTrace();
            date = "01/01/2099";
        }
        return date;
    }

    public static Date cvSDate(String startDate) {
        Date date = null;
        try {
            date = format.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }

    public static Date cvSDateBefore(String startDate) {
        Date date = null;
        Date dateYesterday = null;
        try {
            date = format.parse(startDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            dateYesterday = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
            dateYesterday = new Date();
        }
        return dateYesterday;
    }

    public static Date cvSDateAfter(String startDate) {
        Date date = null;
        Date dateTomorrow = null;
        try {
            date = format.parse(startDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dateTomorrow = calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
            dateTomorrow = new Date();
        }
        return dateTomorrow;
    }
}
