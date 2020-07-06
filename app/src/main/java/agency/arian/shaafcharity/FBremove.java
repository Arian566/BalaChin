package agency.arian.shaafcharity;


import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import me.cheshmak.android.sdk.core.push.CheshmakFirebaseMessagingService;

public class FBremove extends CheshmakFirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (isCheshmakMessage(remoteMessage)) {
            super.onMessageReceived(remoteMessage);
        } else {
            Log.e("Cheshmak", "onMessageReceived: NO Cheshmaki");
        }

    }

}
