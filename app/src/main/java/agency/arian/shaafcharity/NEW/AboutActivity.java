package agency.arian.shaafcharity.NEW;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import agency.arian.shaafcharity.R;

public class AboutActivity extends AppCompatActivity {

    Button btn_back_tamas_ba_ma;
    //Double back
    boolean doubleBackToExitPressedOnce = false;
    LinearLayout ly_call_tamas_ba_ma, ly_web_tamas_ba_ma, ly_instagram_tamas_ba_ma, ly_telegram_tamas_ba_ma, ly_aparat_tamas_ba_ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        btn_back_tamas_ba_ma = findViewById(R.id.btn_back_tamas_ba_ma);

        ly_call_tamas_ba_ma = findViewById(R.id.ly_call_tamas_ba_ma);
        ly_web_tamas_ba_ma = findViewById(R.id.ly_web_tamas_ba_ma);
        ly_instagram_tamas_ba_ma = findViewById(R.id.ly_instagram_tamas_ba_ma);
        ly_telegram_tamas_ba_ma = findViewById(R.id.ly_telegram_tamas_ba_ma);
        ly_aparat_tamas_ba_ma = findViewById(R.id.ly_aparat_tamas_ba_ma);


        btn_back_tamas_ba_ma.setOnClickListener(new View.OnClickListener() {
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
                intent.setData(Uri.parse("tel:02188504229"));
                startActivity(intent);
            }
        });

        ly_web_tamas_ba_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.shaaf-charity.ir"));
                startActivity(browserIntent);
            }
        });

        ly_instagram_tamas_ba_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://instagram.com/shaafcharity");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://instagram.com/shaafcharity")));
                }
            }
        });
        ly_telegram_tamas_ba_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/shahidfatahian"));
                startActivity(telegram);
            }
        });

        ly_aparat_tamas_ba_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.aparat.com/shaafcharity"));
                startActivity(browserIntent);
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
