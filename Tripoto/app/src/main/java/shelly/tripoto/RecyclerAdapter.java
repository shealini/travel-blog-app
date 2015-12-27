package shelly.tripoto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shelly on 11/12/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ContactViewHolder> {


    private List<PlaceDetails> placeDetailses;
    private String TAG = "RecyclerAdapter";
    private Context context;

    public RecyclerAdapter(List<PlaceDetails> contactInfoList, Context context) {
        this.placeDetailses = contactInfoList;
        this.context = context;
    }

    @Override
    public RecyclerAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.listing_card, parent, false);
        return new RecyclerAdapter.ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ContactViewHolder holder, int position) {
        PlaceDetails placeDetails = placeDetailses.get(position);
        holder.title.setText(placeDetails.title);
        Log.i(TAG, "TITLE IS : " + placeDetails.title);
        holder.userName.setText(placeDetails.userName);
        if (placeDetails.getTripImageurl() != null && !placeDetails.getUserImageurl().trim().equals("")) {
            Picasso.with(context).load(placeDetails.getTripImageurl()).into(holder.tripImage);
        }
        if (placeDetails.getUserImageurl() != null && !placeDetails.getUserImageurl().trim().equals("")) {
            Picasso.with(context).load(placeDetails.getUserImageurl()).into(holder.userImage);
        }

    }

    @Override
    public int getItemCount() {
        return placeDetailses.size();
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView userName;
        protected ImageView tripImage;
        protected ImageView userImage;


        public ContactViewHolder(View v) {
            super(v);
            tripImage = (ImageView) v.findViewById(R.id.tripImage);
            userImage = (ImageView) v.findViewById(R.id.userImage);
            title = (TextView) v.findViewById(R.id.title);
            userName = (TextView) v.findViewById(R.id.userName);

        }
    }


}




