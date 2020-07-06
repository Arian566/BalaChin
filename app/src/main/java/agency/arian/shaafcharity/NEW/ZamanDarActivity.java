package agency.arian.shaafcharity.NEW;


import agency.arian.shaafcharity.Retrofit.Models.YouHaveUserData;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import agency.arian.shaafcharity.R;
import agency.arian.shaafcharity.Radio.PresetRadioGroup;
import agency.arian.shaafcharity.Radio.PresetValueButton;
import agency.arian.shaafcharity.Retrofit.API;
import agency.arian.shaafcharity.Retrofit.Models.GetUserDATA;
import agency.arian.shaafcharity.Retrofit.Models.SetDATARes;
import agency.arian.shaafcharity.Retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static java.lang.Integer.parseInt;

public class ZamanDarActivity extends AppCompatActivity implements YouHaveUserData {

    PresetRadioGroup mSetDurationPresetRadioGroup, moneyRG;
    EditText edt_mablagh_delkhah_zamandar;
    TextView tv_wallet;
    int timeInterval_zamandar, moneyInterval_zamandar;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button btn_back_zamandar, btn_pay_zamandar;
    int beforeTextChangedValue, afterTextChangedValue;

    SetDATARes setDATARes;

    //server
    API service;

    private ProgressDialog dialog;

    Switch sw_sms;

    GetUserDATA getUserDATA = new GetUserDATA();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaman_dar);

        Intent intent = getIntent();
        String wallet = intent.getStringExtra("Wallet");
        String isSmsActivatedByUser = intent.getStringExtra("isSmsActivatedByUser");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/
        mSetDurationPresetRadioGroup = findViewById(R.id.rbg_time);
        moneyRG = findViewById(R.id.rbg_money);
        edt_mablagh_delkhah_zamandar = findViewById(R.id.edt_mablagh_delkhah_zamandar);
        tv_wallet = findViewById(R.id.tv_wallet_zamandar);
        btn_back_zamandar = findViewById(R.id.btn_back_zamandar);
        btn_pay_zamandar = findViewById(R.id.btn_pay_zamandar);
        sw_sms = findViewById(R.id.sw_sms);
        //server
        service = RetrofitClientInstance.getRetrofitInstance().create(API.class);

        pref = getApplicationContext().getSharedPreferences("Shaaf", 0); // 0 - for private mode

        dialog = new ProgressDialog(ZamanDarActivity.this);
        dialog.setMessage("در حال ثبت درخواست...");
        tv_wallet.setText(wallet + " تومان");
        if(isSmsActivatedByUser.equals("1")){
            sw_sms.setChecked(true);
        }else {
            sw_sms.setChecked(false);
        }
        timeInterval_zamandar = 1;
        edt_mablagh_delkhah_zamandar.setText(getDecimalFormat(1000 + ""));
        mSetDurationPresetRadioGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {

                switch (radioButton.getId()) {
                    case R.id.rb_time_1:
                        timeInterval_zamandar = 1;
                        break;
                    case R.id.rb_time_2:
                        timeInterval_zamandar = 2;
                        break;
                    case R.id.rb_time_7:
                        timeInterval_zamandar = 7;
                        break;
                    case R.id.rb_time_30:
                        timeInterval_zamandar = 30;
                        break;
                    default:
                        timeInterval_zamandar = 1;
                        break;

                }
//                Toast.makeText(ZamanDarActivity.this, "day checkedId :"+checkedId, Toast.LENGTH_SHORT).show();
//                Log.e("ttt", "checkedId: " + checkedId);
//                Log.e("ttt", "timeInterval_zamandar: " + timeInterval_zamandar);
            }
        });
        moneyRG.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
//                Toast.makeText(ZamanDarActivity.this, "money checkedId :"+checkedId, Toast.LENGTH_SHORT).show();
                edt_mablagh_delkhah_zamandar.setText(((PresetValueButton) radioButton).getValue());
                moneyInterval_zamandar = getEditAndCheckInput(edt_mablagh_delkhah_zamandar.getText().toString());
//                Log.e("ttt", "moneyInterval_zamandar: " + moneyInterval_zamandar);
            }
        });

        btn_back_zamandar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivityNEW.class);
                startActivity(intent);
                finish();
            }
        });

        btn_pay_zamandar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                sendSadaqeData(true);
            }
        });

        edt_mablagh_delkhah_zamandar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextChangedValue = getEditAndCheckInput(edt_mablagh_delkhah_zamandar.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                afterTextChangedValue = getEditAndCheckInput(edt_mablagh_delkhah_zamandar.getText().toString());
                if (beforeTextChangedValue != afterTextChangedValue) {
                    moneyInterval_zamandar = getEditAndCheckInput(edt_mablagh_delkhah_zamandar.getText().toString());
//                    Log.e("ttttttt", "afterTextChanged: " + moneyInterval_zamandar);
//                    Log.e("ttttttt", "afterTextChanged: " + getDecimalFormat(moneyInterval_zamandar + ""));
                    edt_mablagh_delkhah_zamandar.setText(getDecimalFormat(moneyInterval_zamandar + ""));
                    edt_mablagh_delkhah_zamandar.setSelection(edt_mablagh_delkhah_zamandar.getText().length());
                } else {
//                    Log.e("ttttttt", "afterTextChanged: tekrariiii");
                }
            }
        });


        sw_sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int isSMSChecked;
                if (isChecked) {
                    isSMSChecked = 1;
                    Log.e("TAG", "YouHaveUserData: " + getUserDATA.getStatus());

                } else {
                    isSMSChecked = 0;
                }
                sendSMSisActiveFunction(isSMSChecked);

            }
        });
    }


    private void sendSadaqeData(boolean isZamandar) {
        String user_name = pref.getString("Mobile", "");
        String password = pref.getString("Password", "");
        int sadaqeTime;
//        Log.e("tttttttttttttttttt", "sendSadaqeData: " + dayInterval.keyAt(sp_Interval_day.getSelectedItemPosition()));

        if (isZamandar) {
            sadaqeTime = timeInterval_zamandar;
        } else {
            sadaqeTime = 0;
        }


        Call<SetDATARes> call = service.setUserDATA(user_name, password, sadaqeTime, moneyInterval_zamandar);
        call.enqueue(new Callback<SetDATARes>() {
            @Override
            public void onResponse(Call<SetDATARes> call, Response<SetDATARes> response) {
//                progressBar.setVisibility(View.INVISIBLE);
//                Log.e("tttt", "onResponse: ");
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                setDATARes = response.body();
//                Toast.makeText(MainActivityNEW.this, "Status = " + (setDATARes != null ? setDATARes.getStatus() : "Failed"), Toast.LENGTH_SHORT).show();
                Log.e("tttt", "onResponse: code " + response.code());
                if (response.isSuccessful()) {
//                    Log.e("tttt", "onResponse: Status " + setDATARes.getStatus());
//                    Log.e("tttt", "onResponse: Log " + setDATARes.getLog());
//                    Log.e("tttt", "onResponse: ID " + setDATARes.getID());
//                    Log.e("tttt", "onResponse: IntervalTime " + setDATARes.getIntervalTime());
//                    Log.e("tttt", "onResponse: IntervalMoney " + setDATARes.getIntervalMoney());
//                    Log.e("tttt", "onResponse: Balance " + setDATARes.getBalance());
                    editor = pref.edit();
                    editor.putInt("userID", setDATARes.getID());
                    editor.putInt("Interval_money", setDATARes.getIntervalMoney());
                    editor.putInt("Interval_time", setDATARes.getIntervalTime());
                    editor.putInt("Balance", setDATARes.getBalance());
                    editor.apply(); // commit changes

                    Toast.makeText(ZamanDarActivity.this, "قبول حق", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), MainActivityNEW.class);
                    startActivity(intent);
                    finish();
                }// Handle unauthorized
                else if (response.code() == 404) {

                    Logout();

                } else {
                    // Handle other responses
                }

            }

            @Override
            public void onFailure(Call<SetDATARes> call, Throwable t) {
//                progressBar.setVisibility(View.INVISIBLE);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
//                Log.e("tttt", "onFailure: " + t);
                Toast.makeText(ZamanDarActivity.this, "مشکلی پیش آمده لطفا مججدا تلاش کنید", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendSMSisActiveFunction(final int isSMSChecked) {
        String user_name = pref.getString("Mobile", "");
        String password = pref.getString("Password", "");
        Call<Void> call = service.setSMSisActiveOrNot(user_name, password, isSMSChecked);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (isSMSChecked == 1) {
                    Toast.makeText(ZamanDarActivity.this, "ارسال پیامک فعال شد", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ZamanDarActivity.this, "ارسال پیامک غیر فعال شد", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ZamanDarActivity.this, "مشکلی در ارسال اطلاعات پیش آمده", Toast.LENGTH_SHORT).show();
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

    private void Logout() {
        editor.clear();
        editor.apply();
        Intent myIntent = new Intent(ZamanDarActivity.this, LoginActivity_NEW.class);
//                myIntent.putExtra("key", value); //Optional parameters
        ZamanDarActivity.this.startActivity(myIntent);
        finish();
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



    @Override
    public void info(GetUserDATA data) {
        this.getUserDATA = data;
    }
}
