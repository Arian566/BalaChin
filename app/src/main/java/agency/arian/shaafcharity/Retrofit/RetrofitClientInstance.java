package agency.arian.shaafcharity.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
//    private static final String BASE_URL = "http://app-builder.ir/wp-json/shaaf/v1/";
    private static final String BASE_URL = "http://donate.shaaf-charity.ir/wp-json/shaaf/v1/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
