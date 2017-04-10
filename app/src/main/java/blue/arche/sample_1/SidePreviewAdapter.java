package blue.arche.sample_1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import blue.arche.sample_1.Adapters.AlternateMatchesAdapter;
import blue.arche.sample_1.Model.AlternateMatch;

/**
 * Created by pujanpaudel on 11/26/16.
 */

public class SidePreviewAdapter extends RecyclerView.Adapter<SidePreviewAdapter.SidePreviewHolder>{


    private List<StoreItem> sidePreviewLists;

    public SurfaceViewActivity parentActivity;


    private Context ctx;

    public  SidePreviewAdapter(List<StoreItem>list){
        this.sidePreviewLists=list;
        parentActivity=SurfaceViewActivity.reference;
    }

    @Override
    public SidePreviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ctx=parent.getContext();

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.side_preview_view, parent, false);

        return new SidePreviewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(SidePreviewHolder holder, final int position) {

     //   holder.previewImage.setImageBitmap(sidePreviewLists.get(position).productImage);

        Picasso.with(ctx).load(sidePreviewLists.get(position).productImage).fit().centerInside().into(holder.previewImage);
        holder.previewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentActivity.PreviewSelected(sidePreviewLists.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return sidePreviewLists.size();
    }

    public static class SidePreviewHolder extends RecyclerView.ViewHolder{

        protected ImageView previewImage;

        public SidePreviewHolder(View v){

            super(v);
            previewImage=(ImageView)v.findViewById(R.id.item_preview);


        }
    }
}
