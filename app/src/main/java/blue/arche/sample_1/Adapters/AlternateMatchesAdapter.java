package blue.arche.sample_1.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import blue.arche.sample_1.Model.AlternateMatch;
import blue.arche.sample_1.R;
import blue.arche.sample_1.StoreItem;

/**
 * Created by pujanpaudel on 11/25/16.
 */

public class AlternateMatchesAdapter extends RecyclerView.Adapter<AlternateMatchesAdapter.AlternateMatchesViewHolder>{

    private List<StoreItem> alternateMatchList;

    private Context ctx;


    public AlternateMatchesAdapter(List<StoreItem>list){
        this.alternateMatchList=list;
    }

    @Override
    public AlternateMatchesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.alternate_matches_row, parent, false);

        return new AlternateMatchesViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(AlternateMatchesViewHolder holder, int position) {

        StoreItem match=alternateMatchList.get(position);
        holder.productPrice.setText(String.valueOf(match.price));
        holder.productRating.setRating(position+1);
        holder.productTitle.setText(match.name);
        Picasso.with(ctx).load(match.productImage).fit().centerInside().into(holder.previewImage);
      //  holder.previewImage.setImageBitmap(match.productImage);
    }

    @Override
    public int getItemCount() {
        return alternateMatchList.size();
    }

    public static class AlternateMatchesViewHolder extends RecyclerView.ViewHolder{

        protected ImageView previewImage;
        protected TextView productTitle;
        protected RatingBar productRating;
        protected TextView productPrice;
        public AlternateMatchesViewHolder(View v){
            super(v);

            previewImage=(ImageView)v.findViewById(R.id.product_preview);
            productTitle=(TextView)v.findViewById(R.id.product_name);
            productRating=(RatingBar)v.findViewById(R.id.product_rating);
            productPrice=(TextView)v.findViewById(R.id.product_price);


        }

    }
}
