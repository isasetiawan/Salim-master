package com.example.kucing.salim;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by node06 on 09/02/2016.
 */
public class AlertRecivier extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String[] AlarmKey = {"subuh_alarm","dzuhr_alarm","ashar_alarm","maghrib_alarm","isya_alarm"};
        SharedPreferences notif = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences sape = context.getSharedPreferences("Jaso", Context.MODE_PRIVATE);
        String expired_jason = new SimpleDateFormat("M/yyyy").format(new Date());
        String today_date = new SimpleDateFormat("d").format(new Date());
        try {
            String[] today_prayer_time = JadwalSolatParser.getArrayJaso(sape.getString(expired_jason,"Empty"),today_date);
            for (int i=0;i<5;i++){
                if (notif.getBoolean(AlarmKey[i],true))
                    setAlarmEachPrayerTime(today_prayer_time[i],context,i);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void setAlarmEachPrayerTime(String prayerTime,Context context, int n) throws ParseException {
        String [] solats = {"Subuh","Dzuhr","Ashar","Maghrib","Isya'"};
        //parsing the input time
        Date wanted = new SimpleDateFormat("HH:mm").parse(prayerTime);
        Calendar prayTime = Calendar.getInstance();
        prayTime.setTime(wanted);
        //parsing now date
        Date now = new SimpleDateFormat("HH:mm").parse(new SimpleDateFormat("HH:mm").format(new Date()));
        Calendar nowTime = Calendar.getInstance();
        nowTime.setTime(now);

        Calendar calnow = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        //setting calendar to input time
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, prayTime.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, prayTime.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        //setting now calendar to compare
        calnow.setTimeInMillis(System.currentTimeMillis());
        calnow.set(Calendar.HOUR_OF_DAY, nowTime.get(Calendar.HOUR_OF_DAY));
        calnow.set(Calendar.MINUTE, nowTime.get(Calendar.MINUTE));
        calnow.set(Calendar.SECOND,0);
        calnow.set(Calendar.MILLISECOND, 0);

        //setting alarm
        if (cal.compareTo(calnow)!=-1){
            AlarmManager alma = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent FiveTime = new Intent(context, FiveTime.class);
            for (int i=0;i<5 ;i++ ){
                if (i==n) FiveTime.putExtra("idSolat",solats[i]);
            }
            PendingIntent pentent = PendingIntent.getBroadcast(context, n, FiveTime, PendingIntent.FLAG_UPDATE_CURRENT);
            alma.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pentent);
            Log.d("warong", "setAlarmEachPrayerTime: alarm at "+prayerTime+" setted");
        }
    }
}


