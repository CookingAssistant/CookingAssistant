package ideawolf.CookingAssistant;

import android.app.Application;
import android.content.Context;

public class CookingAssistant extends Application {
    private static Context applicationContext;

    public static Context getAppContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }
}