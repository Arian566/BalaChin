package agency.arian.shaafcharity.recycleview;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import agency.arian.shaafcharity.R;
import agency.arian.shaafcharity.Retrofit.Models.Post;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.PostViewHolder> {

    // List to store all the contact details
    private ArrayList<Post> postsList;
    private Context context;
    private final OnItemClickListener listener;

    public NewsAdapter(ArrayList<Post> posts, Context context, OnItemClickListener listener) {
        this.postsList = posts;
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.akhbar_row_sample, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, final int position) {
        final Post post = postsList.get(position);
//        Log.e("onScrolled", "onBindViewHolder  position: " + position);
//        holder.img_khabar.
        if (post.getGetThePostThumbnailUrl().equals("false")) {
            holder.img_khabar.setImageDrawable(null);
            holder.img_khabar.setImageResource(R.drawable.ic_web);

//            holder.img_khabar.setBackgroundResource(R.drawable.button_green_new);


        } else {
            Picasso.get().load(Uri.parse(post.getGetThePostThumbnailUrl())).into(holder.img_khabar);
        }

        holder.tv_onvan_khabar.setText(post.getTitle());
        holder.tv_matne_khabar.setText(post.getContent());
        holder.tv_nevisande_khabar.setText(post.getAuthor());
        holder.tv_tarikh_khabar.setText(post.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                listener.onItemClickd(view, post.getPostUrl());
                listener.onItemClickDialog(view, post);
            }
        });
    }


    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView img_khabar;
        TextView tv_onvan_khabar, tv_matne_khabar, tv_tarikh_khabar, tv_nevisande_khabar;

        public PostViewHolder(@NonNull View itemView) {

            super(itemView);
            img_khabar = itemView.findViewById(R.id.img_khabar);
            tv_onvan_khabar = itemView.findViewById(R.id.tv_onvan_khabar);
            tv_matne_khabar = itemView.findViewById(R.id.tv_matne_khabar);
            tv_tarikh_khabar = itemView.findViewById(R.id.tv_tarikh_khabar);
            tv_nevisande_khabar = itemView.findViewById(R.id.tv_nevisande_khabar);
        }
    }


    public void LoadNews(ArrayList<Post> posts) {
        postsList.addAll(posts);
        notifyDataSetChanged();
    }

}
