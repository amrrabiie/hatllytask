package androiddev.amrrabie.hatllytask.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment;
import com.bumptech.glide.Glide;

import androiddev.amrrabie.hatllytask.R;
import androiddev.amrrabie.hatllytask.model.trading.ResultsItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends SuperBottomSheetFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView title,rating,releasedate,overview;
    ImageView poster;
    private ResultsItem movie;

    public DetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (ResultsItem) getArguments().getSerializable("trading");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  movie = (ResultsItem) getArguments().getSerializable("trading");


        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_details, container, false);
        poster=v.findViewById(R.id.poster);
        title=v.findViewById(R.id.title);
        rating=v.findViewById(R.id.rating);
        releasedate=v.findViewById(R.id.releasedate);
        overview=v.findViewById(R.id.overview);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        fillMovieData();
    }

    private void fillMovieData() {
        title.setText(movie.getTitle());
        rating.setText(movie.getVoteAverage()+"");
        releasedate.setText(movie.getReleaseDate());
        overview.setText(movie.getOverview());

        Glide.with(getActivity())
                .load("https://image.tmdb.org/t/p/w500/" + movie.getPosterPath())
                .into(poster);
    }
}