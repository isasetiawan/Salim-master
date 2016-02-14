package com.example.kucing.salim;

import android.app.Activity;
import android.content.res.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by node06 on 12/02/2016.
 */
public class JadwalSolatParser {


//    mendapatkan jadwalshoaltadapeter dari inputan tanggal
    public static jadwalSholatAdapter getAdapter(String jason,String i,Resources res,Activity act) throws JSONException {
        ArrayList<ItemJadwalSholat> harian = new ArrayList<>();
        ItemJadwalSholat temp = new ItemJadwalSholat();
        JSONObject satubulanj = new JSONObject(jason);
        JSONObject harianj;

        harianj = satubulanj.getJSONObject(i);
        //mendapatkan setiap item solat
        //subuh
        temp.setSolatna("Subuh");
        temp.setWaktuna(harianj.getString("fajr"));
        temp.setImage(R.drawable.night);
        harian.add(temp);temp = new ItemJadwalSholat();
        //dzuhur
        temp.setSolatna("Dzuhr");
        temp.setWaktuna(harianj.getString("zuhr"));
        temp.setImage(R.drawable.day);
        harian.add(temp);temp = new ItemJadwalSholat();
        //asr
        temp.setSolatna("Ashar");
        temp.setWaktuna(harianj.getString("asr"));
        temp.setImage(R.drawable.day);
        harian.add(temp);temp = new ItemJadwalSholat();
        //maghrib
        temp.setSolatna("Maghrib");
        temp.setWaktuna(harianj.getString("maghrib"));
        temp.setImage(R.drawable.night);
        harian.add(temp);temp = new ItemJadwalSholat();
        //isha
        temp.setSolatna("Isya");
        temp.setWaktuna(harianj.getString("isha"));
        temp.setImage(R.drawable.night);
        harian.add(temp);
        return new jadwalSholatAdapter(act,harian,res);
    }

}