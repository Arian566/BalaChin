package agency.arian.shaafcharity.Retrofit.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsModel {

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;
    @SerializedName("Log ")
    @Expose
    private String log;

    /**
     * No args constructor for use in serialization
     *
     */
    public NewsModel() {
    }

    /**
     *
     * @param code
     * @param log
     * @param posts
     * @param status
     */
    public NewsModel(String code, String status, List<Post> posts, String log) {
        super();
        this.code = code;
        this.status = status;
        this.posts = posts;
        this.log = log;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}