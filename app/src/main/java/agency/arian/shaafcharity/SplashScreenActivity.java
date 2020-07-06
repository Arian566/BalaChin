package agency.arian.shaafcharity;

import agency.arian.shaafcharity.NEW.LoginActivity_NEW;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    public static String PUSH_TITLE;
    public static String PUSH_MESSAGE;
    ImageView img_niro;
    Animation  slide_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        img_niro = findViewById(R.id.img_niro);

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        slide_up.setStartOffset(200);
        slide_up.setDuration(2000);
        img_niro.startAnimation(slide_up);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null) {
       /*         Toast.makeText(this, "Cheshmak push notification data" +
                        intent.getExtras().getString("me.cheshmak.data") + "\n" +
                        intent.getExtras().getString("title") + "\n" +
                        intent.getExtras().getString("message") + "\n", Toast.LENGTH_LONG).show();*/

                PUSH_TITLE = intent.getExtras().getString("title");
                PUSH_MESSAGE = intent.getExtras().getString("message");
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(SplashScreenActivity.this, LoginActivity_NEW.class);
//                myIntent.putExtra("key", value); //Optional parameters
                SplashScreenActivity.this.startActivity(myIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
