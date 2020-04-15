package adapter;

import android.app.Application;

import androidx.core.net.ConnectivityManagerCompat;

public class MyApp extends Application {
    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public static synchronized MyApp getInstance(){
        return mInstance;
    }

    public void setConnrctivityListner(ConnectivityReceiver.ConnectivityReceiverListner listner){
        ConnectivityReceiver.connectivityReceiverListner=listner;
    }
}
