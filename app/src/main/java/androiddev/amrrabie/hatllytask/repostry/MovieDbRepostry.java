package androiddev.amrrabie.hatllytask.repostry;

import javax.inject.Inject;

import androiddev.amrrabie.hatllytask.model.genre.GenresResponse;
import androiddev.amrrabie.hatllytask.model.trading.TradingResponse;
import androiddev.amrrabie.hatllytask.network.MoviesDbApiService;
import io.reactivex.rxjava3.core.Single;

public class MovieDbRepostry {
    private MoviesDbApiService moviesDbApiService;

    @Inject
    public MovieDbRepostry(MoviesDbApiService moviesDbApiService) {
        this.moviesDbApiService = moviesDbApiService;
    }

    public Single<TradingResponse> getTradingMovies(String media_type,String time_window , String apikey){
        return moviesDbApiService.getTradingMovies(media_type,time_window,apikey);
    }

    public Single<GenresResponse> getGenersList(String apikey){
        return moviesDbApiService.getGenersList(apikey);
    }
}
