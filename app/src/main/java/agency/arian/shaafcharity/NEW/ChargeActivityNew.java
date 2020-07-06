package agency.arian.shaafcharity.NEW;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import agency.arian.shaafcharity.R;
import agency.arian.shaafcharity.Radio.PresetRadioGroup;
import agency.arian.shaafcharity.Radio.PresetValueButton;

import static java.lang.Integer.parseInt;

public class ChargeActivityNew extends AppCompatActivity {

    PresetRadioGroup moneyRG;
    EditText edt_mablagh_delkhah_zamandar_charge;
    int moneyChargeAmount;
    int beforeTextChangedValue, afterTextChangedValue;
    int userID;
    Button btn_pay_zamandar_charge,btn_back_charge;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView tv_wallet_zamandar_charge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_new);


      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

        btn_pay_zamandar_charge = findViewById(R.id.btn_pay_zamandar_charge);
        moneyRG = findViewById(R.id.rbg_money_charge);
        edt_mablagh_delkhah_zamandar_charge = findViewById(R.id.edt_mablagh_delkhah_zamandar_charge);
        btn_back_charge = findViewById(R.id.btn_back_charge);
        tv_wallet_zamandar_charge = findViewById(R.id.tv_wallet_zamandar_charge);

        pref = getApplicationContext().getSharedPreferences("Shaaf", 0); // 0 - for private mode

        tv_wallet_zamandar_charge.setText(getDecimalFormat( pref.getInt("Balance", 0)+""));

        userID  = pref.getInt("userID", 0);


        moneyRG.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
                switch (radioButton.getId()) {
                    case R.id.rb_money_1_charge:
                        moneyChargeAmount = 1000;
                        break;
                    case R.id.rb_money_2_charge:
                        moneyChargeAmount = 2000;
                        break;
                    case R.id.rb_money_5_charge:
                        moneyChargeAmount = 5000;
                        break;
                    case R.id.rb_money_10_charge:
                        moneyChargeAmount = 10000;
                        break;
                }
//                Toast.makeText(ChargeActivityNew.this, "money checkedId :" + checkedId, Toast.LENGTH_SHORT).show();
                edt_mablagh_delkhah_zamandar_charge.setText(getDecimalFormat(moneyChargeAmount + ""));
//
//                Log.e("ttt", "moneyInterval_zamandar: " + moneyChargeAmount);
            }
        });

        edt_mablagh_delkhah_zamandar_charge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextChangedValue = getEditAndCheckInput(edt_mablagh_delkhah_zamandar_charge.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                afterTextChangedValue = getEditAndCheckInput(edt_mablagh_delkhah_zamandar_charge.getText().toString());
                if (beforeTextChangedValue != afterTextChangedValue) {
                    moneyChargeAmount = getEditAndCheckInput(edt_mablagh_delkhah_zamandar_charge.getText().toString());
//                    Log.e("ttttttt", "afterTextChanged: " + moneyChargeAmount);
//                    Log.e("ttttttt", "afterTextChanged: " + getDecimalFormat(moneyChargeAmount + ""));
                    edt_mablagh_delkhah_zamandar_charge.setText(getDecimalFormat(moneyChargeAmount + ""));
                    edt_mablagh_delkhah_zamandar_charge.setSelection(edt_mablagh_delkhah_zamandar_charge.getText().length());
                } else {
//                    Log.e("ttttttt", "afterTextChanged: tekrariiii");
                }
            }
        });

        btn_pay_zamandar_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyChargeAmount = getEditAndCheckInput(edt_mablagh_delkhah_zamandar_charge.getText().toString());
                if (afterTextChangedValue > 0) {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://app-builder.ir/wp-json/shaaf/v1/cart?charge=" + moneyChargeAmount));
                      Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://donate.shaaf-charity.ir/wp-json/shaaf/v1/cart?charge=" + moneyChargeAmount + "&ID="+ userID));

                    startActivity(browserIntent);
                    finish();
                }else {
                    Toast.makeText(ChargeActivityNew.this, "لطفا مقدار معتبری وارد کنید", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_back_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivityNEW.class);
                startActivity(intent);
                finish();
            }
        });

    }


    int getEditAndCheckInput(String ss) {
        ss = ss.replace(",", "");
        ss = ss.replace(" ", "");
        int len = ss.length();
        if (len < 0 || ss.equals("")) {
            ss = "0";
        }
        int sss = parseInt(ss);
        return sss;
    }

    private String getDecimalFormat(String value) {
        value = value.replace(" ", "");
        int len = value.length();
        if (len < 0) {
            value = "0";
        }
        int firstortwo = len % 3;
        int part = len / 3;
//        String[] newVal = new String[len];
        String newV = value.substring(0, firstortwo);
        for (int i = part; i > 0; i--) {
            if (firstortwo > 0 || i < part) {
                newV += ",";
            }
            newV += value.substring(len - (i * 3), len - ((i - 1) * 3));
        }
//        Log.e("tttt", "getDecimalFormat:" + len);
//        Log.e("tttt", "getDecimalFormat:" + firstortwo);
//        Log.e("tttt", "getDecimalFormat:" + newV);

        return newV;
    }
}
