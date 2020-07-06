package agency.arian.shaafcharity.NEW;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import agency.arian.shaafcharity.R;
import agency.arian.shaafcharity.Retrofit.API;
import agency.arian.shaafcharity.Retrofit.Models.GetUserDATA;
import agency.arian.shaafcharity.Retrofit.Models.LoginByMobile_Verified_Res;
import agency.arian.shaafcharity.Retrofit.RetrofitClientInstance;
import me.cheshmak.android.sdk.core.Cheshmak;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity_NEW extends AppCompatActivity {

    ImageView logo;
    ImageView header1, header2, headerImg;
    Button btn_login;
    EditText edt_mobile, edt_code;
    TextView tv_countDown, tv_wrongMobile;
    GetUserDATA getUserDATA;
    ProgressBar progressBar;
    LoginByMobile_Verified_Res loginByMobile_verified_res;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean isRequestingCode = true;
    CountDownTimer countDownTimer;
    API service;
    private static int LOGIN_TIME_OUT = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        logo = findViewById(R.id.img_shaaf_logo_login);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_code = findViewById(R.id.edt_verify_code);
        tv_countDown = findViewById(R.id.tv_countDown);
        btn_login = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.pb_login_new);
        headerImg = findViewById(R.id.img_child);
        header1 = findViewById(R.id.img_header1);
        header2 = findViewById(R.id.img_header2);
        tv_wrongMobile = findViewById(R.id.tv_wrongMobile);

        service = RetrofitClientInstance.getRetrofitInstance().create(API.class);

        ObjectAnimator anim = ObjectAnimator.ofFloat(logo, "alpha", 0.5f, 1f).setDuration(2000);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.start();

        ObjectAnimator animheader1 = ObjectAnimator.ofFloat(header1, "translationX", 100f);
        animheader1.setDuration(2000);
        animheader1.setRepeatCount(ObjectAnimator.INFINITE);
        animheader1.setRepeatMode(ObjectAnimator.REVERSE);
        animheader1.start();

        ObjectAnimator animheader2 = ObjectAnimator.ofFloat(header2, "translationX", -100f);
        animheader2.setDuration(2000);
        animheader2.setRepeatCount(ObjectAnimator.INFINITE);
        animheader2.setRepeatMode(ObjectAnimator.REVERSE);
        animheader2.start();

        pref = getApplicationContext().getSharedPreferences("Shaaf", 0); // 0 - for private mode
        editor = pref.edit();

        userIsAlreadyLogedin();

        countDownTimer = new CountDownTimer(LOGIN_TIME_OUT, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_countDown.setText("درخواست مجدد در : " + millisUntilFinished / 1000 + " ثانیه");
                //here you can have your logic to set text to edittext
                if (millisUntilFinished <= (LOGIN_TIME_OUT / 2) && (tv_wrongMobile.getVisibility() != View.VISIBLE)) {
                    tv_wrongMobile.setVisibility(View.VISIBLE);
                }
            }

            public void onFinish() {
                btn_login.setEnabled(true);
                btn_login.setText("درخواست کد");
                tv_countDown.setText("درخواست مجدد کد");
                isRequestingCode = true;
            }

        };

        edt_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (edt_mobile.getText().length() >= 11) {
                    btn_login.setEnabled(true);
                }
            }
        });

        edt_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.e("ttt", "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e("ttt", "onTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable s) {
//                Log.e("ttt", "afterTextChanged: ");
//                Log.e("ttt", "edt_code.getText().length(): " + edt_code.getText().length());
                if (edt_code.getText().length() >= 4) {
                    btn_login.setEnabled(true);
                    btn_login.setText("ورود");
                    isRequestingCode = false;
                } else {
                    btn_login.setEnabled(false);
                    btn_login.setText("انتظار");
                    isRequestingCode = true;
                }

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                //Get Code

                if (isRequestingCode) {

                    GetTemporaryCodeForLogin();
                }
                //Using temp pass to get permanent pass
                else {
//                    Toast.makeText(LoginActivity_NEW.this, "ورود", Toast.LENGTH_SHORT).show();
                    // login using temp code and get new user_name & password
                    LoginAndGetNewPassword(edt_mobile.getText().toString(), edt_code.getText().toString());

                }
            }
        });

        tv_wrongMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_mobile.setEnabled(true);
                edt_code.setEnabled(false);
                btn_login.setText("درخواست کد");
                edt_mobile.setText("");
                tv_countDown.setText("درخواست مجدد کد");
                tv_wrongMobile.setVisibility(View.INVISIBLE);
//                Toast.makeText(LoginActivity_NEW.this, "WRONG", Toast.LENGTH_SHORT).show();
                isRequestingCode = true;
                countDownTimer.cancel();
                edt_code.setVisibility(View.INVISIBLE);
                tv_countDown.setVisibility(View.INVISIBLE);
            }
        });

    }

    void userIsAlreadyLogedin() {
        int userID = pref.getInt("userID", 0);
        if (userID > 0) {
            Intent intent = new Intent(getBaseContext(), MainActivityNEW.class);
//            intent.putExtra("userID", userID);
//            intent.putExtra("Interval_money", Interval_money);
//            intent.putExtra("Interval_time", Interval_time);
//            intent.putExtra("Balance", Balance);
            startActivity(intent);
            finish();
        }
    }

    void GetTemporaryCodeForLogin() {
        Call<Void> call = service.LOGIN_BY_MOBILE_Get_Code("init", edt_mobile.getText().toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(LoginActivity_NEW.this, "درخواست کد ارسال شد", Toast.LENGTH_SHORT).show();
                edt_mobile.setEnabled(false);
                tv_countDown.setVisibility(View.VISIBLE);
                edt_code.setEnabled(true);
                edt_code.setText("");
                btn_login.setEnabled(false);
                btn_login.setText("انتظار");
                countDownTimer.start();
                edt_code.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(LoginActivity_NEW.this, "خطا... لطفا لحظاتی دیگر تلاش کنید", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    void LoginAndGetNewPassword(String mobile, String tempCode) {
//        Toast.makeText(LoginActivity_NEW.this, "ورود", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        Call<LoginByMobile_Verified_Res> call = service.LOGIN_BY_MOBILE_VERIFIED_RES("verified", mobile, tempCode, Cheshmak.getCheshmakID(this));
        call.enqueue(new Callback<LoginByMobile_Verified_Res>() {
            @Override
            public void onResponse(Call<LoginByMobile_Verified_Res> call, Response<LoginByMobile_Verified_Res> response) {
//                Log.e("tttt", "onResponse: ");
                progressBar.setVisibility(View.INVISIBLE);
                loginByMobile_verified_res = new LoginByMobile_Verified_Res();
                loginByMobile_verified_res = response.body();
//                Toast.makeText(LoginActivity_NEW.this, "Status = " + (getUserDATA != null ? getUserDATA.getStatus() : "Failed"), Toast.LENGTH_SHORT).show();
                //isSuccessful() =>Code = 200-300
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity_NEW.this, "خوش آمدید", Toast.LENGTH_SHORT).show();
                    editor.putString("Mobile", String.valueOf(edt_mobile.getText()));
                    editor.putString("Password", loginByMobile_verified_res.getPass());
                    editor.apply(); // commit changes
                    LoginAndGetUserData();

                }// Handle unauthorized
                else if (response.code() == 404) {
                    Toast.makeText(LoginActivity_NEW.this, "کد معتبر نیست", Toast.LENGTH_SHORT).show();
                    edt_code.setText("");
                    btn_login.setEnabled(false);

                } else {
                    // Handle other responses
                }
            }

            @Override
            public void onFailure(Call<LoginByMobile_Verified_Res> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
//                Log.e("tttt", "onFailure: " + t);
                Toast.makeText(LoginActivity_NEW.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void LoginAndGetUserData() {
        String user_name = pref.getString("Mobile", "");
        String password = pref.getString("Password", "");

        Call<GetUserDATA> call = service.getUserData(user_name, password);
        call.enqueue(new Callback<GetUserDATA>() {
            @Override
            public void onResponse(Call<GetUserDATA> call, Response<GetUserDATA> response) {
                progressBar.setVisibility(View.INVISIBLE);

                getUserDATA = new GetUserDATA();
                getUserDATA = response.body();

//                Log.e("tttt", "onResponse: code " + response.code());
//                Log.e("tttt", "onResponse: Status " + getUserDATA.getStatus());
//                Log.e("tttt", "onResponse: Log " + getUserDATA.getLog());
//                Log.e("tttt", "onResponse: ID " + getUserDATA.getID());
//                Log.e("tttt", "onResponse: IntervalTime " + getUserDATA.getIntervalTime());
//                Log.e("tttt", "onResponse: IntervalMoney " + getUserDATA.getIntervalMoney());

//                Toast.makeText(LoginActivity_NEW.this, "Status = " + (getUserDATA != null ? getUserDATA.getStatus() : "FFailed"), Toast.LENGTH_SHORT).show();
                //isSuccessful() =>Code = 200-300
                if (response.isSuccessful()) {

                    editor.putInt("userID", getUserDATA.getID());
                    editor.putInt("Interval_money", getUserDATA.getIntervalMoney());
                    editor.putInt("Interval_time", getUserDATA.getIntervalTime());
                    editor.putInt("Balance", getUserDATA.getBalance());
                    editor.apply(); // commit changes
                }// Handle unauthorized
                else if (response.code() == 404) {
                    Toast.makeText(LoginActivity_NEW.this, "Unauthorized", Toast.LENGTH_SHORT).show();


                } else {
                    // Handle other responses
                }


                userIsAlreadyLogedin();
            }

            @Override
            public void onFailure(Call<GetUserDATA> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
//                Log.e("tttt", "onFailure: " + t);
                Toast.makeText(LoginActivity_NEW.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
