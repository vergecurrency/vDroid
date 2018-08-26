package vergecurrency.vergewallet.models.sec;

import android.content.Context;
import android.content.SharedPreferences;

public class PinCodeCheck {

    private static long INIT_TIME           = 0;
    private static PinCodeCheck ref         = null;
    private static SharedPreferences values = null;

    //gets the preferences
    private PinCodeCheck(Context context){
        values = context.getSharedPreferences("com.vergecurrency.vergewallet", 0);
    }

    //gets a running instance of the check
    public static synchronized PinCodeCheck getInstance(Context context) {
        if (ref == null){
            ref = new PinCodeCheck(context);
        }
        return ref;
    }

    //Keeps track of the time
    public void init(){
        PinCodeCheck.INIT_TIME = System.currentTimeMillis();
    }

    //forces to 0 to always pwn the threshold
    public void forceLock(){
        PinCodeCheck.INIT_TIME = 0;
    }

    //Check if the threshold has been pwned
    public boolean isLocked(){
        long currentTime    = System.currentTimeMillis();
        long threshold      = 30000;// check here, might change in between calls
        if (currentTime - PinCodeCheck.INIT_TIME > threshold){
            return true;
        }
        return false;
    }
}
