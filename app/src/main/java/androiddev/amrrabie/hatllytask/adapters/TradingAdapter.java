package androiddev.amrrabie.hatllytask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import androiddev.amrrabie.hatllytask.R;
import androiddev.amrrabie.hatllytask.model.trading.ResultsItem;
import androiddev.amrrabie.hatllytask.ui.GetMovieClicked;

public class TradingAdapter extends RecyclerView.Adapter<TradingAdapter.TradingViewHolder> implements View.OnClickListener {

    Context mContext;
    List<ResultsItem> tradinglist;
    GetMovieClicked getMovieClicked;

    public TradingAdapter(Context mContext,GetMovieClicked getMovieClicked) {
        this.mContext = mContext;
        this.getMovieClicked=getMovieClicked;
    }

    public void setList(List<ResultsItem> tradinglist){
        this.tradinglist=tradinglist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TradingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        v.setOnClickListener(this);
        return new TradingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TradingViewHolder holder, int position) {
        holder.title.setText(tradinglist.get(position).getTitle());
        holder.rating.setText(tradinglist.get(position).getVoteAverage()+"");

        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500/" + tradinglist.get(position).getPosterPath())
                .into(holder.poster);

        holder.cview.setTag(position);
    }

    @Override
    public int getItemCount() {
        return tradinglist.size();
    }

    @Override
    public void onClick(View v) {
        int pos=(int)v.getTag();

        getMovieClicked.onClick(tradinglist.get(pos));

    }

    public class TradingViewHolder extends RecyclerView.ViewHolder {
        CardView cview;
        TextView title,rating;
        ImageView poster;
        public TradingViewHolder(@NonNull View v) {
            super(v);
            cview=v.findViewById(R.id.cview);
            poster=v.findViewById(R.id.poster);
            title=v.findViewById(R.id.title);
            rating=v.findViewById(R.id.rating);
        }
    }
}
