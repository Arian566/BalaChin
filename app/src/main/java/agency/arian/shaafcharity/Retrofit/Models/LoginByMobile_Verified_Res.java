package agency.arian.shaafcharity.Retrofit.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginByMobile_Verified_Res {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("pass")
    @Expose
    private String pass;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}

