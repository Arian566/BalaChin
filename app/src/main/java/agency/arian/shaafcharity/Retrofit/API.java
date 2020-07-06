package agency.arian.shaafcharity.Retrofit;

import agency.arian.shaafcharity.Retrofit.Models.GetUserDATA;
import agency.arian.shaafcharity.Retrofit.Models.LoginByMobile_Verified_Res;
import agency.arian.shaafcharity.Retrofit.Models.NewsModel;
import agency.arian.shaafcharity.Retrofit.Models.SetDATARes;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface API {


    @POST("LAUD")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<GetUserDATA> getUserData(@Field("user_login") String user_login,
                                  @Field("user_pass") String user_pass);

    @POST("SUD")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<SetDATARes> setUserDATA(@Field("user_login") String user_login,
                                 @Field("user_pass") String user_pass,
                                 @Field("interval_time") Integer interval_time,
                                 @Field("interval_money") Integer interval_money);

   /* @POST("LBM")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<LoginByMobile_Verified_Res> LOGIN_BY_MOBILE_VERIFIED_RES(@Field("type") String type,
                                                                  @Field("mobile") String mobile,
                                                                  @Field("code") String code);*/

    @POST("LBM")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<LoginByMobile_Verified_Res> LOGIN_BY_MOBILE_VERIFIED_RES(@Field("type") String type,
                                                                  @Field("mobile") String mobile,
                                                                  @Field("code") String code,
                                                                  @Field("cheshmak_id") String cheshmak_id);

    @POST("LBM")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<Void> LOGIN_BY_MOBILE_Get_Code(@Field("type") String type,
                                        @Field("mobile") String mobile
    );


    @POST("sms")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<Void> setSMSisActiveOrNot(@Field("user_login") String user_login,
                                   @Field("user_pass") String user_pass,
                                   @Field("isSmsAactived") Integer isSmsAactived);

    @POST("news")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<NewsModel> getnews(@Field("posts_per_page") int posts_per_page,
                            @Field("offset") int offset);
}
