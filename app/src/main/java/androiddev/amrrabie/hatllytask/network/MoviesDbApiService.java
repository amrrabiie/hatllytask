package androiddev.amrrabie.hatllytask.network;

import androiddev.amrrabie.hatllytask.model.genre.GenresResponse;
import androiddev.amrrabie.hatllytask.model.trading.TradingResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesDbApiService {

    @GET("trending/{media_type}/{time_window}")
    public Single<TradingResponse> getTradingMovies(
            @Path("media_type") String media_type,
            @Path("time_window") String time_window,
            @Query("api_key") String api_key
    );

    @GET("genre/movie/list")
    public Single<GenresResponse> getGenersList(
            @Query("api_key") String api_key
    );
}
