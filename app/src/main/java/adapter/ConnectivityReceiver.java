package adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListner connectivityReceiverListner;
    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        boolean isConnected= activeNetwork!=null && activeNetwork.isConnectedOrConnecting();

        if(connectivityReceiverListner != null){
            connectivityReceiverListner.onNetworkConnectionChanged(isConnected);
        }
    }

    //Create Method To Check Manually  (On Button click)
    public static boolean isConnected(){
        ConnectivityManager cm= (ConnectivityManager) MyApp
                                    .getInstance()
                                    .getApplicationContext()
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork=cm.getActiveNetworkInfo();

        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }

    //Create Interface
    public interface ConnectivityReceiverListner{
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
