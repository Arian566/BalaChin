package agency.arian.shaafcharity.NEW;

import agency.arian.shaafcharity.R;
import agency.arian.shaafcharity.Retrofit.API;
import agency.arian.shaafcharity.Retrofit.Models.NewsModel;
import agency.arian.shaafcharity.Retrofit.Models.Post;
import agency.arian.shaafcharity.Retrofit.RetrofitClientInstance;
import agency.arian.shaafcharity.recycleview.NewsAdapter;
import agency.arian.shaafcharity.recycleview.OnItemClickListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

public class ChekhabarActivity extends AppCompatActivity {
    private static final String TAG = "Chekhabar Debug";
    //server
    API service;
    //        int[] newsRange = {1, 222, 3};
    ArrayList<Integer> newsRange = new ArrayList<Integer>();
    NewsModel newsModel = new NewsModel();
    Button btn_back_chekhabar, btn_tamas_chekhabar;

    LinearLayoutManager linearLayoutManager;
    RecyclerView rcv_akhbar;
    NewsAdapter newsAdapter;
    ProgressBar progressBar;
    private int offset = 0;
    private int postPerPage = 5;
    //
    Boolean isLoading = true;
    private int pastVisibleItems, visibleItemCount, totalItemCount, previous_total = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getBaseContext(), MainActivityNEW.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chekhabar);
        rcv_akhbar = findViewById(R.id.rcv_akhbar);
        btn_back_chekhabar = findViewById(R.id.btn_back_chekhabar);
        btn_tamas_chekhabar = findViewById(R.id.btn_tamas_chekhabar);
        progressBar = findViewById(R.id.pb_chekhabr);
        linearLayoutManager = new LinearLayoutManager(this);
        rcv_akhbar.setLayoutManager(linearLayoutManager);
        newsRange.add(0);

        service = RetrofitClientInstance.getRetrofitInstance().create(API.class);
        new getNewsAsyncTasks().execute(newsRange);

        btn_tamas_chekhabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_back_chekhabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivityNEW.class);
                startActivity(intent);
                finish();
            }
        });

        rcv_akhbar.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                Log.d(TAG, "onScrolled: dy : " + dy);
                Log.d(TAG, "onScrolled: totalItemCount : " + totalItemCount);
                Log.d(TAG, "onScrolled: previous_total : " + previous_total);
                Log.d(TAG, "onScrolled: pastVisibleItems : " + pastVisibleItems);
                if (dy > 0) {
                    if (isLoading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            isLoading = false;
                            progressBar.setVisibility(VISIBLE);
                            Log.e(TAG, "onScrolled: page_number : " + offset);
                            newsRange.set(0, offset);
                            new LoadMoreNews().execute(newsRange);
                        }
                    }
                }
            }
        });
    }

    public class getNewsAsyncTasks extends AsyncTask<ArrayList<Integer>, Void, Void> {

        @Override
        protected Void doInBackground(ArrayList<Integer>... range) {
            int newsRangFrom = range[0].get(0);
            Call<NewsModel> call = service.getnews(postPerPage, newsRangFrom);
            call.enqueue(new Callback<NewsModel>() {

                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    newsModel = response.body();
                    Log.d(TAG, "onResponse: " + newsModel.getPosts().get(0).getContent());
                    newsAdapter = new NewsAdapter((ArrayList<Post>) newsModel.getPosts(), ChekhabarActivity.this, new OnItemClickListener() {
//                        @Override
//                        public void onItemClick(View v, String postUrl) {
////                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(postUrl));
////                            startActivity(browserIntent);
//                        }

                        @Override
                        public void onItemClickDialog(View v, Post post) {
                            launchDismissDlg(ChekhabarActivity.this, post);
                            Log.e(TAG, "onItemClickDialog: " );
                        }
                    });
                    rcv_akhbar.setAdapter(newsAdapter);
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }.start();
            offset = 5;
        }
    }

    public class LoadMoreNews extends AsyncTask<ArrayList<Integer>, Void, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            offset = offset + 3;
            new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }.start();
        }

        @Override
        protected Void doInBackground(ArrayList<Integer>... offsetArrayList) {
            int offset = offsetArrayList[0].get(0);
            Call<NewsModel> call = service.getnews(3, offset);
            call.enqueue(new Callback<NewsModel>() {

                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    newsModel = response.body();
                    if (newsModel.getCode().equals("200")) {
                        Log.d(TAG, "onResponse: " + newsModel.getStatus());
//                        newsAdapter = new NewsAdapter((ArrayList<Post>) newsModel.getPosts(), ChekhabarActivity.this);
//                        rcv_akhbar.setAdapter(newsAdapter);
                        newsAdapter.LoadNews((ArrayList<Post>) newsModel.getPosts());
                        isLoading = true;
                    } else {
                        Toast.makeText(ChekhabarActivity.this, "دیگه خبر خاصی نیست...", Toast.LENGTH_SHORT).show();
                        isLoading = false;
                    }
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(VISIBLE);
        }
    }

    private void launchDismissDlg(Context context, Post post) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.setCanceledOnTouchOutside(true);

        Button btn_dialog = (Button) dialog.findViewById(R.id.btn_dialog);
        ImageView img_news = dialog.findViewById(R.id.img_news);
        TextView tv_news_title = dialog.findViewById(R.id.tv_news_title);
        TextView tv_news_content = dialog.findViewById(R.id.tv_news_content);


        Picasso.get().load(Uri.parse(post.getGetThePostThumbnailUrl())).into(img_news);
        tv_news_title.setText(post.getTitle());
        tv_news_content.setText(post.getContent());

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
}