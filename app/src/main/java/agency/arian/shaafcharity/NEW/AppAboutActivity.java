package agency.arian.shaafcharity.NEW;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import agency.arian.shaafcharity.R;
import androidx.appcompat.app.AppCompatActivity;

public class AppAboutActivity extends AppCompatActivity {

    Button btn_back_tamas_ba_ma_app;
    //Double back
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout ly_call_tamas_ba_ma, ly_web_tamas_ba_ma, ly_instagram_tamas_ba_ma, ly_whatsup_tamas_ba_ma, ly_email_tamas_ba_ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_about);

        btn_back_tamas_ba_ma_app = findViewById(R.id.btn_back_tamas_ba_ma_app);

        ly_call_tamas_ba_ma = findViewById(R.id.ly_call_tamas_ba_ma_app);
        ly_web_tamas_ba_ma = findViewById(R.id.ly_web_tamas_ba_ma_app);
        ly_instagram_tamas_ba_ma = findViewById(R.id.ly_instagram_tamas_ba_ma_app);
        ly_whatsup_tamas_ba_ma = findViewById(R.id.ly_whatsup_tamas_ba_ma);
        ly_email_tamas_ba_ma = findViewById(R.id.ly_email_tamas_ba_ma);


        btn_back_tamas_ba_ma_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivityNEW.class);
                startActivity(intent);
                finish();
            }
        });

        ly_call_tamas_ba_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:02191008696"));
                startActivity(intent);
            }
        });

        ly_web_tamas_ba_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.arian.agency"));
                startActivity(browserIntent);
            }
        });

        ly_instagram_tamas_ba_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://instagram.com/arian.agency");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://instagram.com/arian.agency")));
                }
            }
        });
        ly_whatsup_tamas_ba_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsapp = new Intent(Intent.ACTION_VIEW , Uri.parse("https://api.whatsapp.com/send?phone="+"+989029434382"));
                startActivity(whatsapp);
            }
        });

        ly_email_tamas_ba_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Create the Intent */
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{("info@arian.agency")});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ارتباط با مشتریان ");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "با سلام:");

                /* Send it off to the Activity-Chooser */
                (AppAboutActivity.this).startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getBaseContext(), MainActivityNEW.class);
        startActivity(intent);
        finish();

    }
}
