package androiddev.amrrabie.hatllytask.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androiddev.amrrabie.hatllytask.R;
import androiddev.amrrabie.hatllytask.adapters.GenersAdapter;
import androiddev.amrrabie.hatllytask.adapters.TradingAdapter;
import androiddev.amrrabie.hatllytask.databinding.ActivityMainBinding;
import androiddev.amrrabie.hatllytask.model.genre.GenresItem;
import androiddev.amrrabie.hatllytask.model.trading.ResultsItem;
import androiddev.amrrabie.hatllytask.utils.Network;
import androiddev.amrrabie.hatllytask.viewmodel.MovieDbViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements GetMovieClicked   {

    ActivityMainBinding binding;
    MovieDbViewModel viewModel;
    TradingAdapter adapter;
    String releasedatefilter="No filter";
    String genername="No filter";



    GenersAdapter genersAdapter;
    private int generid=50001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        viewModel=new ViewModelProvider(this).get(MovieDbViewModel.class);

        adapter=new TradingAdapter(this,this);

        fillGenersSpiner();

        bindTradingData();
    }

    private void fillGenersSpiner() {
        if(Network.isNetworkAvailable(MainActivity.this)){
            bindGenersData();
        }else{
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Warnibg !")
                    .setMessage("No internet coneection exists!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }

    private void bindGenersData() {
        String apikey="a1d41735f90f329d79ee2b4335f2e227";
        viewModel.getGenersList(apikey);

        viewModel.getGenerslist().observe(MainActivity.this, new Observer<List<GenresItem>>() {
            @Override
            public void onChanged(List<GenresItem> genresItems) {
                if(genresItems != null) {
                    genresItems.add(0,new GenresItem("No filter",50001));
                    genresItems.add(1,new GenresItem("Newest release date",50003));
                    genresItems.add(2,new GenresItem("Oldest release date",50002));
                    genersAdapter = new GenersAdapter(MainActivity.this, genresItems);
                    binding.typespinner.setAdapter(genersAdapter);

                    binding.typespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            generid=genresItems.get(position).getId();
                            genername=genresItems.get(position).getName();
                            //Toast.makeText(MainActivity.this, "Gener: " + genername + " / " + generid, Toast.LENGTH_SHORT).show();
                            bindData();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }
            }
        });
    }

    private void bindData() {
        if(generid == 50001){
            bindTradingData();
        }else if(generid == 50002){
            binding.pbar.setVisibility(View.VISIBLE);
            binding.tradingrecycler.setVisibility(View.GONE);
            String mediatype="movie";
            String timewindow="day";
            String apikey="a1d41735f90f329d79ee2b4335f2e227";
            viewModel.getTradingMovies(mediatype,timewindow,apikey);

            viewModel.getTradinglist().observe(MainActivity.this, new Observer<List<ResultsItem>>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onChanged(List<ResultsItem> resultsItems) {

                    resultsItems.sort(Comparator.comparing(o -> o.getReleaseDate()));

                    binding.tradingrecycler.setAdapter(adapter);
                    adapter.setList(resultsItems);
                    adapter.notifyDataSetChanged();
                    binding.pbar.setVisibility(View.GONE);
                    binding.tradingrecycler.setVisibility(View.VISIBLE);
                }
            });
        }else if(generid == 50003){
            binding.pbar.setVisibility(View.VISIBLE);
            binding.tradingrecycler.setVisibility(View.GONE);
            String mediatype="movie";
            String timewindow="day";
            String apikey="a1d41735f90f329d79ee2b4335f2e227";
            viewModel.getTradingMovies(mediatype,timewindow,apikey);

            viewModel.getTradinglist().observe(MainActivity.this, new Observer<List<ResultsItem>>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onChanged(List<ResultsItem> resultsItems) {

                    resultsItems.sort(Comparator.comparing(o -> o.getReleaseDate()));
                    Collections.reverse(resultsItems);

                    binding.tradingrecycler.setAdapter(adapter);
                    adapter.setList(resultsItems);
                    adapter.notifyDataSetChanged();
                    binding.pbar.setVisibility(View.GONE);
                    binding.tradingrecycler.setVisibility(View.VISIBLE);
                }
            });
        }else{
            binding.pbar.setVisibility(View.VISIBLE);
            binding.tradingrecycler.setVisibility(View.GONE);
            bindTradingData(generid);
        }
    }

    private void bindTradingData() {
        String mediatype = "movie";
        String timewindow = "day";
        String apikey = "a1d41735f90f329d79ee2b4335f2e227";
        viewModel.getTradingMovies(mediatype, timewindow, apikey);

        /*viewModel.getPagescount().observe(MainActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                pagescount=count;
            }
        });*/

        viewModel.getTradinglist().observe(MainActivity.this, new Observer<List<ResultsItem>>() {
            @Override
            public void onChanged(List<ResultsItem> resultsItems) {


                binding.tradingrecycler.setAdapter(adapter);
                adapter.setList(resultsItems);


                binding.pbar.setVisibility(View.GONE);
                binding.tradingrecycler.setVisibility(View.VISIBLE);


            }
        });
    }

    private void bindTradingData(int generid) {
        String mediatype="movie";
        String timewindow="day";
        String apikey="a1d41735f90f329d79ee2b4335f2e227";
        viewModel.getTradingMovies(mediatype,timewindow,apikey);



        viewModel.getTradinglist().observe(MainActivity.this, new Observer<List<ResultsItem>>() {
            @Override
            public void onChanged(List<ResultsItem> resultsItems) {

                List<ResultsItem> list=new ArrayList<>();

                for(int i=0;i<resultsItems.size();i++){
                    ResultsItem item=resultsItems.get(i);
                    List<Integer> genreIds = item.getGenreIds();
                    for(int x=0;x<genreIds.size();x++){
                        int id=genreIds.get(x);
                        if(id == generid){
                            list.add(item);
                        }
                    }
                }



                binding.tradingrecycler.setAdapter(adapter);
                adapter.setList(list);
                adapter.notifyDataSetChanged();
                binding.pbar.setVisibility(View.GONE);
                binding.tradingrecycler.setVisibility(View.VISIBLE);
            }
        });
    }





    @Override
    public void onClick(ResultsItem item) {
        Log.i("item",item.toString());

        Bundle bundle = new Bundle();
        bundle.putSerializable("trading",(Serializable) item );

        DetailsFragment sheet = new DetailsFragment();
        sheet.setArguments(bundle);
        sheet.show(getSupportFragmentManager(), "DemoBottomSheetFragment");
    }



}

