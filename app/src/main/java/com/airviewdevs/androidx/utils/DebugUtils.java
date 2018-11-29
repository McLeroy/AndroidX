package com.airviewdevs.androidx.utils;

import android.util.Log;


/**
 * Created by Developer: mcleroy on 3/3/2017.
 */
public class DebugUtils {
    public static void debug(Object caller,String message){
        String tag;
        if (caller instanceof Class){
            tag = ((Class) caller).getSimpleName();
        }else {
            tag = caller.getClass().getSimpleName();
        }
        Log.d(tag,message);
//        Crashlytics.log(Log.VERBOSE, tag, message);
    }
    public static void verbose(Class caller,String message){
        Log.d(caller.getClass().getSimpleName(),message);
    }

    public static void error(Object caller,String message,Throwable cause){
        String tag;
        if (caller instanceof Class){
            tag = ((Class) caller).getSimpleName();
        }else {
            tag = caller.getClass().getSimpleName();
        }
        Log.e(tag,message,cause);
    }
}
