package blue.arche.sample_1.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import blue.arche.sample_1.Model.RecentlyScanned;
import blue.arche.sample_1.ModelsData;
import blue.arche.sample_1.R;
import blue.arche.sample_1.StoreItem;

/**
 * Created by pujanpaudel on 11/25/16.
 */

public class RecentlyScannedAdapter extends RecyclerView.Adapter<RecentlyScannedAdapter.RecentlyScannedViewHolder>{



    private List<StoreItem> recentlyScannedList;

    public  RecentlyScannedAdapter(List<StoreItem>list){
        this.recentlyScannedList=list;
    }




    private Context ctx;
    @Override
    public RecentlyScannedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        ctx=parent.getContext();
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.recent_image_view, parent, false);

        ctx=parent.getContext();
        return new RecentlyScannedViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecentlyScannedViewHolder holder, int position) {


        StoreItem item=recentlyScannedList.get(position);
        Log.i("BRAND ASSOCIATED",item.brandAssociated);
       // holder.previewView.setImageBitmap(BitmapFactory.decodeResource(ctx.getResources(),R.drawable.brand_addidas));

      //  holder.previewView.setImageBitmap(item.productImage);
        Picasso.with(ctx).load(item.productImage).fit().centerInside().into(holder.previewView);
        holder.productTitle.setText(item.brandAssociated);
    }

    @Override
    public int getItemCount() {
        return recentlyScannedList.size();
    }

    public static class RecentlyScannedViewHolder extends RecyclerView.ViewHolder{

        protected CircularImageView previewView;
        protected TextView productTitle;

        public RecentlyScannedViewHolder(View v){
            super(v);
            previewView=(CircularImageView) v.findViewById(R.id.product_preview);
            productTitle=(TextView)v.findViewById(R.id.product_name);


        }
    }
}
