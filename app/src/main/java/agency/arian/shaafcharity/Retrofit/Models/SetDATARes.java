package agency.arian.shaafcharity.Retrofit.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetDATARes {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("Balance")
    @Expose
    private Integer balance;
    @SerializedName("Interval_time")
    @Expose
    private Integer intervalTime;
    @SerializedName("Interval_money")
    @Expose
    private Integer intervalMoney;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("Log")
    @Expose
    private String log;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public Integer getIntervalMoney() {
        return intervalMoney;
    }

    public void setIntervalMoney(Integer intervalMoney) {
        this.intervalMoney = intervalMoney;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}