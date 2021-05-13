package com.liorkerenn.user.myapplication.injection;


import com.liorkerenn.user.myapplication.helpers.Const;
import com.liorkerenn.user.myapplication.model.Country;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * A Server Api Singleton/Injector Class
 * Loads data from api.
 * */
@Singleton
public class DataApi {
  private static Retrofit retrofit = null;
  public Map<String, String> countries = new HashMap<>();


  @Inject
  protected DataApi () {
    //Create rest service

    for (String iso : Locale.getISOCountries()) {
      Locale l = new Locale("en", iso);
      countries.put(l.getDisplayCountry(Locale.ENGLISH), iso);
    }

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    httpClient.addInterceptor(chain -> {
      Request original = chain.request();

      Request request = original.newBuilder()
              .method(original.method(), original.body())
              .build();

      return chain.proceed(request);
    });

    OkHttpClient client = httpClient.build();
    retrofit = new Retrofit.Builder()
            .baseUrl(Const.dataSetUrl.COUNTRIES_API_END_POINT)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
  }

  public DataSetService getAPIService() {
    return retrofit.create(DataSetService.class);
  }

  /**
   * Services
   * */
  public interface DataSetService {
    @GET("all")
    Observable<List<Country>> getAllCountries();

    @GET("alpha")
    Observable<List<Country>> getCountriesByISO(@Query("codes") String countries);
  }

}
