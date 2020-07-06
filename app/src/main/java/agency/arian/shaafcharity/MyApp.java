package agency.arian.shaafcharity;

import android.app.Application;
import android.util.Log;

import me.cheshmak.android.sdk.core.Cheshmak;
import me.cheshmak.android.sdk.core.network.CheshmakCallback;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Cheshmak.with(this);
//        Cheshmak.initTracker("jhC9j1TyNY1CBBECPElVjQ==");
//        Log.e("Cheshmak", "start: ");
        Cheshmak.initTracker("jhC9j1TyNY1CBBECPElVjQ==", new CheshmakCallback() {
            @Override
            public void onCheshmakIdReceived(String s) {
                Log.e("Cheshmak", "onCheshmakIdReceived: "+s);
            }
        });

    }



}




