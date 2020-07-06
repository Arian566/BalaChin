package agency.arian.shaafcharity.NEW;

import agency.arian.shaafcharity.Retrofit.Models.Post;
import agency.arian.shaafcharity.Retrofit.Models.YouHaveUserData;
import agency.arian.shaafcharity.SplashScreenActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.UserData;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import agency.arian.shaafcharity.Dialogs.CustomDialog;
import agency.arian.shaafcharity.R;
import agency.arian.shaafcharity.Radio.PresetRadioGroup;
import agency.arian.shaafcharity.Retrofit.API;
import agency.arian.shaafcharity.Retrofit.Models.GetUserDATA;
import agency.arian.shaafcharity.Retrofit.Models.SetDATARes;
import agency.arian.shaafcharity.Retrofit.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Integer.parseInt;

public class MainActivityNEW extends AppCompatActivity {
    private static final int MONEY_INTERVAL_ADD_MINUS = 1000;
    /*************************
     Popup
     *************************/
    TextView tv_mojodi_feli_ani;
    RelativeLayout ly_pop_up;
    Button btn_ani_pop_up_pardakht;
    PresetRadioGroup mSetMoneyValuePresetRadioGroup, mSetPaymentType;
    Animation slide_down, slide_up;
    int rb_money_value_ani_popup;
    int beforeTextChangedValue, afterTextChangedValue;
    ImageView btn_minus_pop_ani, btn_add_pop_ani;
    EditText edt_mablaq_delkhah_pop_ani;
    boolean isAniFromWallet = true;

    /*************************
     Popup
     *************************/
    private ProgressDialog dialog;
    AnimationDrawable anim;
    TextView tv_wallet, tv_interval_money_text_new, tv_interval_day_new_value, tv_interval_money_value;
    ImageView logo, header1, header2;
    Spinner sp_Interval_day;
    Button btn_charge_new, btn_ani, btn_zamanDar;
    LinearLayout btn_call, btn_call_app, btn_logout;
    int walletValue, intervalValue_money, intervalValue_time;
    final SparseArray<String> dayInterval = new SparseArray<>();
    //    String[] day_interval_name = new String[]{"اول هر ماه", "اول هر هفته", "یک روز در میان", "هر روز"};
//    String[] day_interval_name = new String[]{"1day", "2day", "week", "month"};
    String[] day_interval_name = new String[]{"هر روز", "یک روز در میان", "اول هر هفته", "اول هر ماه"};
    //    int[] day_interval_value = new int[]{30, 7, 2, 1};
    int[] day_interval_value = new int[]{1, 2, 7, 30};

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    SetDATARes setDATARes;
    GetUserDATA getUserDATA;

    //server
    API service;

    //Double back
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_new);

        btn_charge_new = findViewById(R.id.btn_charge_new);
        setDATARes = new SetDATARes();
        getUserDATA = new GetUserDATA();

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

        btn_charge_new.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivityNEW.this, ChargeActivityNew.class);
//                myIntent.putExtra("userID", pref.getString("userID", "")); //Optional parameters
                MainActivityNEW.this.startActivity(myIntent);
//                Toast.makeText(MainActivityNEW.this, "بریم برای شارژ کیف", Toast.LENGTH_SHORT).show();

            }
        });
//server
        service = RetrofitClientInstance.getRetrofitInstance().create(API.class);

        tv_wallet = findViewById(R.id.tv_wallet_new);
        tv_interval_day_new_value = findViewById(R.id.tv_interval_day_new_value);
//        tv_interval_money_text_new = findViewById(R.id.tv_interval_money_text_new);
        tv_interval_money_value = findViewById(R.id.tv_interval_money_value);
        sp_Interval_day = findViewById(R.id.sp_Interval_day);
        btn_ani = findViewById(R.id.btn_Ani);
        btn_zamanDar = findViewById(R.id.btn_zamandar);
        btn_logout = findViewById(R.id.btn_logout);
        btn_call = findViewById(R.id.btn_call);
        btn_call_app = findViewById(R.id.btn_call_app);
        logo = findViewById(R.id.img_shaaf_logo_new);
        header1 = findViewById(R.id.img_header1);
        header2 = findViewById(R.id.img_header2);
        dialog = new ProgressDialog(MainActivityNEW.this);
        dialog.setMessage("در حال ثبت درخواست...");
        /*************************
         Popup
         *************************/
        tv_mojodi_feli_ani = findViewById(R.id.tv_mojodi_feli_ani);
        ly_pop_up = findViewById(R.id.ly_pop_up);
        btn_ani_pop_up_pardakht = findViewById(R.id.btn_ani_pop_up_pardakht);
        edt_mablaq_delkhah_pop_ani = findViewById(R.id.edt_mablaq_delkhah_pop_ani);
        mSetMoneyValuePresetRadioGroup = findViewById(R.id.rbg_money_popup);
        mSetPaymentType = findViewById(R.id.rbg_payment_type_popup);
        btn_minus_pop_ani = findViewById(R.id.btn_minus_pop_ani);
        btn_add_pop_ani = findViewById(R.id.btn_add_pop_ani);

        //Load animation
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);


        if (SplashScreenActivity.PUSH_TITLE != null) {
            CustomDialog alert = new CustomDialog();
//            alert.showDialog(this, SplashScreenActivity.PUSH_MESSAGE);
        }


//        Log.e("Cheshmak", "onCreate: " + Cheshmak.getCheshmakID(this));


        mSetPaymentType.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {

                switch (radioButton.getId()) {
                    case R.id.rb_type_from_wallet:
                        isAniFromWallet = true;
                        break;
                    case R.id.rb_type_online:
                        isAniFromWallet = false;
                        break;
                }

                CheckSelectedValueWithWallet();
            }
        });

        mSetMoneyValuePresetRadioGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {

                switch (radioButton.getId()) {
                    case R.id.rb_money_1_popup:
                        rb_money_value_ani_popup = 1000;
                        break;
                    case R.id.rb_money_2_popup:
                        rb_money_value_ani_popup = 2000;
                        break;
                    case R.id.rb_money_5_popup:
                        rb_money_value_ani_popup = 5000;
                        break;
                    case R.id.rb_money_10_popup:
                        rb_money_value_ani_popup = 10000;
                        break;
                }
                edt_mablaq_delkhah_pop_ani.setText(getDecimalFormat(rb_money_value_ani_popup + ""));
//                Toast.makeText(ZamanDarActivity.this, "day checkedId :"+checkedId, Toast.LENGTH_SHORT).show();
//                Log.e("ttt", "checkedId: " + checkedId);
//                Log.e("ttt", "rb_money_value: " + rb_money_value_ani_popup);
            }
        });

        btn_add_pop_ani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_money_value_ani_popup = getEditAndCheckInput(edt_mablaq_delkhah_pop_ani.getText().toString()) + MONEY_INTERVAL_ADD_MINUS;
//                rb_money_value_ani_popup += MONEY_INTERVAL_ADD_MINUS;
                CheckSelectedValueWithWallet();
                edt_mablaq_delkhah_pop_ani.setText(getDecimalFormat(rb_money_value_ani_popup + ""));
            }
        });
        btn_minus_pop_ani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_money_value_ani_popup = getEditAndCheckInput(edt_mablaq_delkhah_pop_ani.getText().toString()) - MONEY_INTERVAL_ADD_MINUS;
//                rb_money_value_ani_popup -= MONEY_INTERVAL_ADD_MINUS;
                if (rb_money_value_ani_popup <= 0) {
                    rb_money_value_ani_popup = 0;
                }
                edt_mablaq_delkhah_pop_ani.setText(getDecimalFormat(rb_money_value_ani_popup + ""));
            }
        });

        /*************************
         Popup
         *************************/

        //bloom anim
//        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wiggle);
//        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wiggle);
//        header1.startAnimation(animation);
//        animation1.setStartOffset(500);
//        header2.startAnimation(animation1);
        //bloom

        ObjectAnimator anim = ObjectAnimator.ofFloat(logo, "alpha", 0.5f, 1f).setDuration(2000);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.start();

        pref = getApplicationContext().getSharedPreferences("Shaaf", 0); // 0 - for private mode


        readDataFromPrefToVariables();


//        tv_wallet.setText(getDecimalFormat(walletValue + ""));


        // Mapping day_interval_name to day_interval_value.
        for (int i = 0; i < day_interval_name.length; i++) {
            dayInterval.put(day_interval_value[i], day_interval_name[i]);
        }

        edt_mablaq_delkhah_pop_ani.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextChangedValue = getEditAndCheckInput(edt_mablaq_delkhah_pop_ani.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                afterTextChangedValue = getEditAndCheckInput(edt_mablaq_delkhah_pop_ani.getText().toString());
                if (beforeTextChangedValue != afterTextChangedValue) {
                    intervalValue_money = getEditAndCheckInput(edt_mablaq_delkhah_pop_ani.getText().toString());
                    Log.e("ttttttt", "afterTextChanged: " + intervalValue_money);
                    Log.e("ttttttt", "afterTextChanged: " + getDecimalFormat(intervalValue_money + ""));
                    edt_mablaq_delkhah_pop_ani.setText(getDecimalFormat(intervalValue_money + ""));
                    edt_mablaq_delkhah_pop_ani.setSelection(edt_mablaq_delkhah_pop_ani.getText().length());
                    CheckSelectedValueWithWallet();
                } else {
                    Log.e("ttttttt", "afterTextChanged: tekrariiii");
                }

//                tv_interval_money_value.setText(getDecimalFormat(intervalValue_money + ""));
//                tv_wallet.setText(getDecimalFormat(walletValue - (interval_money) + ""));
            }
        });


        // Spinner click listener
        sp_Interval_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivityNEW.this, "onItemSelected: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        for (int i = 0; i < day_interval_name.length; i++) {
            categories.add(day_interval_name[i]);
//            Log.e("tttttttttttt", "day_interval_name: " + i + " -- " + day_interval_name[i]);
        }


//        tv_interval_day_new_value.setText(dayInterval.get(intervalValue_time));
//                valueAt(intervalValue_time));
        // attaching data adapter to spinner

//        Log.e("tttttt", "sp_Interval_day: " + dayInterval.indexOfKey(intervalValue_time));
//        Log.e("tttttt", "sp_Interval_day: " + intervalValue_time);
        btn_zamanDar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivityNEW.this, ZamanDarActivity.class);
                myIntent.putExtra("Wallet", walletValue + ""); //Optional parameters
                myIntent.putExtra("isSmsActivatedByUser", getUserDATA.getIsSmsActivatedByUser() + ""); //Optional parameters
                MainActivityNEW.this.startActivity(myIntent);
            }
        });

        btn_ani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendSadaqeData(false);
                rb_money_value_ani_popup = 1000;
//                Log.e("ttttt", "onClick: ani " + "rb_money_value_ani_popup = " + rb_money_value_ani_popup);
                edt_mablaq_delkhah_pop_ani.setText(getDecimalFormat(rb_money_value_ani_popup + ""));
                tv_mojodi_feli_ani.setText(getDecimalFormat(walletValue + ""));
                edt_mablaq_delkhah_pop_ani.setText(getDecimalFormat(1000 + ""));
                ly_pop_up.setVisibility(View.VISIBLE);
                btnVisibility(false);
            }
        });

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//              Intent intent = new Intent(getBaseContext(), AboutActivity.class);
                Intent intent = new Intent(getBaseContext(), ChekhabarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_call_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), AppAboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Logout();
                finish();
            }
        });

        LoginAndGetUserData();

        Pop_Up_Func();
    }

    private void CheckForPaymentType() {

        if (getEditAndCheckInput(edt_mablaq_delkhah_pop_ani.getText().toString()) > walletValue) {
            Log.d("TAG", "onTextChanged: Greater");
            mSetPaymentType.check(R.id.rb_type_online);
        } else {
            Log.d("TAG", "onTextChanged: less");
            mSetPaymentType.check(R.id.rb_type_from_wallet);
        }
    }

    private void readDataFromPrefToVariables() {
        walletValue = pref.getInt("Balance", 0);
        intervalValue_money = pref.getInt("Interval_money", 0);
        intervalValue_time = pref.getInt("Interval_time", 1);

        tv_interval_day_new_value.setText(dayInterval.get(intervalValue_time));
        tv_wallet.setText(getDecimalFormat(walletValue + ""));
        tv_interval_money_value.setText(getDecimalFormat(intervalValue_money + ""));
    }

    private void Logout() {
        try {
            editor = pref.edit();
            editor.clear();
            editor.apply();
        } catch (NullPointerException e) {
//            Log.e("tttt", "Logout: " + e);
        }

        Intent myIntent = new Intent(MainActivityNEW.this, LoginActivity_NEW.class);
//                myIntent.putExtra("key", value); //Optional parameters
        MainActivityNEW.this.startActivity(myIntent);
        finish();
    }

    private void sendSadaqeData(boolean isZamandar) {
        dialog.show();
        String user_name = pref.getString("Mobile", "");
        String password = pref.getString("Password", "");
        int sadaqeTime;
//        Log.e("tttttttttttttttttt", "sendSadaqeData: " + dayInterval.keyAt(sp_Interval_day.getSelectedItemPosition()));

        if (isZamandar) {
            sadaqeTime = dayInterval.keyAt(sp_Interval_day.getSelectedItemPosition());
        } else {
            sadaqeTime = 0;
            intervalValue_money = EdtMoneyValueReadyToInt();
        }


        Call<SetDATARes> call = service.setUserDATA(user_name, password, sadaqeTime, intervalValue_money);
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
//                Log.e("tttt", "onResponse: code " + response.code());
                if (response.isSuccessful()) {
                 /*   Log.e("tttt", "onResponse: Status " + setDATARes.getStatus());
                    Log.e("tttt", "onResponse: Log " + setDATARes.getLog());
                    Log.e("tttt", "onResponse: ID " + setDATARes.getID());
                    Log.e("tttt", "onResponse: IntervalTime " + setDATARes.getIntervalTime());
                    Log.e("tttt", "onResponse: IntervalMoney " + setDATARes.getIntervalMoney());
                    Log.e("tttt", "onResponse: Balance " + setDATARes.getBalance());*/
                    editor = pref.edit();
                    editor.putInt("userID", setDATARes.getID());
                    editor.putInt("Interval_money", setDATARes.getIntervalMoney());
                    editor.putInt("Interval_time", setDATARes.getIntervalTime());
                    editor.putInt("Balance", setDATARes.getBalance());
                    editor.apply(); // commit changes
//                    updateInfoOnUI();
                    readDataFromPrefToVariables();
                    Toast.makeText(MainActivityNEW.this, "قبول حق", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(MainActivityNEW.this, "خطا...لطفا مجددا تلاش کنید", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void updateInfoOnUI() {

        int walletValue = pref.getInt("Balance", 0);
        int moneyInterval = pref.getInt("Interval_money", 0);
        int timeInterval = pref.getInt("Interval_time", 0);
/*
        Log.e("ttt", "updateInfoOnUI: Balance = "+ walletValue);
        Log.e("ttt", "updateInfoOnUI: Interval_money = "+ moneyInterval);
        Log.e("ttt", "updateInfoOnUI: Interval_time = "+ timeInterval);
*/


        tv_interval_money_value.setText(getDecimalFormat(moneyInterval + ""));
        tv_wallet.setText(getDecimalFormat(walletValue + ""));
        int spinnerValueByIndex = dayInterval.indexOfKey(timeInterval);

        sp_Interval_day.setSelection(spinnerValueByIndex);
//    Log.e("tttttt", "updateInfoOnUI: "+ spinnerValueByIndex );

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent in = getIntent();
        Uri data = in.getData();
        String id = null;
        if (data != null) {
            id = data.getQueryParameter("id");
            Toast.makeText(this, "کیف پول شما شارژ شد", Toast.LENGTH_SHORT).show();
        }
//        Log.d("tttt", "onResume: data: " + data);
//        System.out.println("deeplinkingcallback  ID :- "+id);
//        System.out.println("deeplinkingcallback   :- "+data);

        // getDataFromServer();
    }


    @Override
    public void onBackPressed() {
        if (ly_pop_up.getVisibility() == View.VISIBLE) {
            ly_pop_up.setVisibility(View.GONE);
            btnVisibility(true);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "برای خروج دوباره برگشت را بزنید", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
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

    void LoginAndGetUserData() {
        String user_name = pref.getString("Mobile", "");
        String password = pref.getString("Password", "");

        Call<GetUserDATA> call = service.getUserData(user_name, password);
        call.enqueue(new Callback<GetUserDATA>() {
            @Override
            public void onResponse(Call<GetUserDATA> call, Response<GetUserDATA> response) {
                getUserDATA = new GetUserDATA();
                getUserDATA = response.body();

//                Log.e("tttt", "onResponse: code " + response.code());

//                Toast.makeText(LoginActivity_NEW.this, "Status = " + (getUserDATA != null ? getUserDATA.getStatus() : "FFailed"), Toast.LENGTH_SHORT).show();
                //isSuccessful() =>Code = 200-300
                if (response.isSuccessful()) {
//                    Log.e("tttt", "onResponse: Status " + getUserDATA.getStatus());
//                    Log.e("tttt", "onResponse: Log " + getUserDATA.getLog());
//                    Log.e("tttt", "onResponse: ID " + getUserDATA.getID());
//                    Log.e("tttt", "onResponse: IntervalTime " + getUserDATA.getIntervalTime());
//                    Log.e("tttt", "onResponse: IntervalMoney " + getUserDATA.getIntervalMoney());
                    editor = pref.edit();
                    editor.putInt("userID", getUserDATA.getID());
                    editor.putInt("Interval_money", getUserDATA.getIntervalMoney());
                    editor.putInt("Interval_time", getUserDATA.getIntervalTime());
                    editor.putInt("Balance", getUserDATA.getBalance());
                    editor.apply(); // commit changes

//                    updateInfoOnUI();
                    readDataFromPrefToVariables();


                }// Handle unauthorized
                else if (response.code() == 404) {
                    Logout();

                } else {
                    // Handle other responses
                }
            }

            @Override
            public void onFailure(Call<GetUserDATA> call, Throwable t) {
//                Log.e("tttt", "onFailure: " + t);
                Toast.makeText(MainActivityNEW.this, "خطا...لطفا مجددا تلاش کنید", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void Pop_Up_Func() {

        btn_ani_pop_up_pardakht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("tttt", "onClick:isSelected " + mSetPaymentType.isSelected());

                if (isAniFromWallet) {
                    sendSadaqeData(false);
                    ly_pop_up.setVisibility(View.GONE);
                    btnVisibility(true);
                } else {
                    dialog.show();
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://app-builder.ir/wp-json/shaaf/v1/cart?charge=" + EdtMoneyValueReadyToInt()));
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://donate.shaaf-charity.ir/wp-json/shaaf/v1/cart?charge=" + EdtMoneyValueReadyToInt()));
                    startActivity(browserIntent);
                    dialog.dismiss();
                }
            }
        });
    }

    void btnVisibility(boolean setVisible) {

        if (setVisible) {

            btn_ani.setVisibility(View.VISIBLE);
            btn_zamanDar.setVisibility(View.VISIBLE);
            // Start animation
            ly_pop_up.startAnimation(slide_down);
            readDataFromPrefToVariables();
        } else {
            edt_mablaq_delkhah_pop_ani.setSelection(edt_mablaq_delkhah_pop_ani.getText().length());
            btn_ani.setVisibility(View.INVISIBLE);
            btn_zamanDar.setVisibility(View.INVISIBLE);
            // Start animation
            ly_pop_up.startAnimation(slide_up);
            //  rb_money_value_ani_popup = intervalValue_money;

        }

    }

    int EdtMoneyValueReadyToInt() {
        String ss = (edt_mablaq_delkhah_pop_ani.getText().toString()).replace(" ", "").replace(",", "");

        int len = ss.length();
        if (len < 0 || ss.equals("")) {
            ss = "0";
        }
        int sss = parseInt(ss);
//                Log.e("ttttttttttttt", "sss: " + sss);
        int interval_money = (sss);
//                Log.e("interval_money", "walletTest: " + walletTest);
//                Log.e("interval_money", "interval_money: " + interval_money / 100);
//                Log.e("interval_money", "(i / 100): " + (i / 100));
//                Log.e("interval_money", "i: " + i);
        int countDay;
        if ((interval_money) > 0) {
            countDay = walletValue / (interval_money);
        } else {
            countDay = walletValue;
        }
        return (interval_money);
    }

    void CheckSelectedValueWithWallet() {
        if (rb_money_value_ani_popup > walletValue && isAniFromWallet) {
            mSetPaymentType.check(R.id.rb_type_online);
            isAniFromWallet = false;
            Toast.makeText(MainActivityNEW.this, "مبلغ مورد نظر بیش از موجودی کیف پول شماست" + "\n" + "لطفا آنلاین پرداخت کنید", Toast.LENGTH_SHORT).show();
        }
        CheckForPaymentType();
    }

    int getEditAndCheckInput(String ss) {
        ss = ss.replace(",", "");
        ss = ss.replace(" ", "");
        int len = ss.length();
        if (len < 0 || ss.equals("")) {
            ss = "0";
        }
//        int sss = parseInt(ss);
        return Integer.parseInt(ss);
    }

//    public interface YouHaveUserData {
//
//        void info(GetUserDATA data);
//    }
}

