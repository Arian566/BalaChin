package agency.arian.shaafcharity.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import agency.arian.shaafcharity.R;
import agency.arian.shaafcharity.Retrofit.Models.Post;

public class CustomDialog {
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
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

    }
}
