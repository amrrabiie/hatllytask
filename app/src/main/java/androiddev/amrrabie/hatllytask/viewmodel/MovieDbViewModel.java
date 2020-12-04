package androiddev.amrrabie.hatllytask.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import androiddev.amrrabie.hatllytask.model.genre.GenresItem;
import androiddev.amrrabie.hatllytask.model.genre.GenresResponse;
import androiddev.amrrabie.hatllytask.model.trading.ResultsItem;
import androiddev.amrrabie.hatllytask.model.trading.TradingResponse;
import androiddev.amrrabie.hatllytask.repostry.MovieDbRepostry;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDbViewModel extends ViewModel {
    private MovieDbRepostry repostry;
    MutableLiveData<List<ResultsItem>> tradinglist=new MutableLiveData<>();
    MutableLiveData<List<GenresItem>> generslist=new MutableLiveData<>();
    MutableLiveData<Integer> pagescount=new MutableLiveData<>();

    @ViewModelInject
    public MovieDbViewModel(MovieDbRepostry repostry) {
        this.repostry = repostry;
    }

    public MutableLiveData<List<ResultsItem>> getTradinglist() {
        return tradinglist;
    }

    public MutableLiveData<List<GenresItem>> getGenerslist() {
        return generslist;
    }

    public MutableLiveData<Integer> getPagescount() {
        return pagescount;
    }

    public void getTradingMovies(String media_type, String time_window , String apikey){
        repostry.getTradingMovies(media_type,time_window,apikey)
                .subscribeOn(Schedulers.io())
                .map(new Function<TradingResponse, List<ResultsItem>>() {
                    @Override
                    public List<ResultsItem> apply(TradingResponse tradingResponse) throws Throwable {
                       /* Integer x=tradingResponse.getTotalPages();
                        pagescount.setValue(x);*/
                        List<ResultsItem> list=tradingResponse.getResults();
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->tradinglist.setValue(result),error-> Log.e("error",error.getMessage()));
    }

    public void getGenersList(String apikey){
        repostry.getGenersList(apikey)
                .subscribeOn(Schedulers.io())
                .map(new Function<GenresResponse, List<GenresItem>>() {
                    @Override
                    public List<GenresItem> apply(GenresResponse genresResponse) throws Throwable {
                        List<GenresItem> list=genresResponse.getGenres();
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->generslist.setValue(result),error->Log.e("error",error.getMessage()));
    }
}
