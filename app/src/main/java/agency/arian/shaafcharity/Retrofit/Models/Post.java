package agency.arian.shaafcharity.Retrofit.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("get_the_post_thumbnail_url")
    @Expose
    private String getThePostThumbnailUrl;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("post_url")
    @Expose
    private String postUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public Post() {
    }

    /**
     *
     * @param date
     * @param postUrl
     * @param getThePostThumbnailUrl
     * @param author
     * @param title
     * @param content
     */
    public Post(String title, String getThePostThumbnailUrl, String content, String author, String date, String postUrl) {
        super();
        this.title = title;
        this.getThePostThumbnailUrl = getThePostThumbnailUrl;
        this.content = content;
        this.author = author;
        this.date = date;
        this.postUrl = postUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGetThePostThumbnailUrl() {
        return getThePostThumbnailUrl;
    }

    public void setGetThePostThumbnailUrl(String getThePostThumbnailUrl) {
        this.getThePostThumbnailUrl = getThePostThumbnailUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }



}
