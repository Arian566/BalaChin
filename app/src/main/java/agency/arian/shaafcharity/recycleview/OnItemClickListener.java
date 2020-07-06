package agency.arian.shaafcharity.recycleview;


import android.view.View;

import agency.arian.shaafcharity.Retrofit.Models.Post;

public interface OnItemClickListener {
//    void onItemClick(View v, String postUrl);
    void onItemClickDialog(View v, Post post);
}