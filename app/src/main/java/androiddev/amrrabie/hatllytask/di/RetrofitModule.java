package androiddev.amrrabie.hatllytask.di;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androiddev.amrrabie.hatllytask.network.MoviesDbApiService;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class RetrofitModule {

    public static final String Api_url="https://api.themoviedb.org/3/";

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(){
        return new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public static MoviesDbApiService provideMoviesDbApiService(OkHttpClient okHttpClient){




        return new Retrofit.Builder()
                .baseUrl(Api_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(MoviesDbApiService.class);
    }
}
