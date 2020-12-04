package androiddev.amrrabie.hatllytask.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import androiddev.amrrabie.hatllytask.R;
import androiddev.amrrabie.hatllytask.model.genre.GenresItem;

public class GenersAdapter   extends BaseAdapter {
    Context context; List<GenresItem> genersList;
    public GenersAdapter(Context context, List<GenresItem> genersList) {
        this.context=context;
        this.genersList=genersList;
    }

    @Override
    public int getCount() {
        try{
            if(genersList.size() > 0) {
                return genersList.size();
            }else{
                return 0;
            }}
        catch (Exception e){
            Log.e("e",e.getMessage());
            return 0;
        }
    }

    @Override
    public GenresItem getItem(int i) {
        return genersList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CountriesViewHolder holder=null;
        if(view == null){
            view= LayoutInflater.from(context).inflate(R.layout.gener_item,viewGroup,false);
            holder=new CountriesViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (CountriesViewHolder) view.getTag();
        }



        holder.name.setText(genersList.get(i).getName());


        return view;
    }



    public class CountriesViewHolder{

        TextView name;
        public CountriesViewHolder(View v) {
            name=v.findViewById(R.id.genername);
        }
    }
}
