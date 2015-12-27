package shelly.tripoto;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MainActivityCallback {


    private List<PlaceDetails> contactInfoList = new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private RelativeLayout mFabButton;
    private SeekBar seekBar;
    private String TAG = "MainActivity";
    private Location location;
    private double lat = 28.5266604;
    private double lon = 77.1443678;
    private int radius = 50;
    private ProgressBar spinner;
    private int activityoffset = 0;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFabButton = (RelativeLayout) findViewById(R.id.fabButton);
        seekBar = (SeekBar) findViewById(R.id.seekBar1);
        textView = (TextView) findViewById(R.id.textView1);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 28.5266604);
        lon = intent.getDoubleExtra("lon", 77.1443678);


        RecyclerView recyclerList = (RecyclerView) findViewById(R.id.cardList);
        recyclerList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerList.setLayoutManager(linearLayoutManager);

        Log.i(TAG, "ACTIVITY CREATED FETHING SERVER");
        fetchServerResponse(activityoffset, radius, lat, lon);
        recyclerAdapter = new RecyclerAdapter(contactInfoList, this);
        recyclerList.setAdapter(recyclerAdapter);


        //RECYCLER SCROLL LISTNER

        recyclerList.setOnScrollListener(new HidingScrollListener(linearLayoutManager) {

            @Override
            public void onLoadMore(int offset) {
                fetchServerResponse((offset), radius, lat, lon);
                activityoffset = offset;
            }

            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

        //SEEK BAR CHANGE LISTNER
        seekBar.setMax(1000);

        textView.setText("Radius: " + 50 + " " + "Km");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 50;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                spinner.setVisibility(View.VISIBLE);
                textView.setText("Radius: " + progress  +" " + "Km");

                radius = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                spinner.setVisibility(View.VISIBLE);
                textView.setText("Radius: " + progress +" "+ "Km");
                radius = progress;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText("Radius: " + progress +" "+ "Km");
                radius = progress;
                contactInfoList.clear();
                recyclerAdapter.notifyDataSetChanged();
                fetchServerResponse(activityoffset, radius, lat, lon);


            }

        });

    }

    private void hideViews() {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFabButton.animate().translationY(mFabButton.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public void fetchServerResponse(int offset, int radius, double latitude, double longitude) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("offset", offset);
            jsonObject.put("radius", radius);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            jsonObject.put("limit", 20);
            jsonObject.put("unit", "km");
            JSONObject tripoto = new JSONObject();
            tripoto.put("Tripoto", jsonObject);

            new Http(this).execute(tripoto.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateView(String response) {
        Gson gson = new GsonBuilder().create();
        PlaceDetailResponce placeDetailResponce = gson.fromJson(response, PlaceDetailResponce.class);
        if (placeDetailResponce != null) {
            contactInfoList.addAll(placeDetailResponce.getResult().getData());
        }
        recyclerAdapter.notifyDataSetChanged();
        spinner.setVisibility(View.GONE);
    }
}


