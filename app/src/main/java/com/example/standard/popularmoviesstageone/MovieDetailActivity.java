package com.example.standard.popularmoviesstageone;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        setTitle(getString(R.string.title_details));

        ImageView poster = (ImageView) findViewById(R.id.poster_image);
        TextView titleTextView = (TextView) findViewById(R.id.title_tv);
        TextView overviewTextView = (TextView) findViewById(R.id.story_tv);
        TextView ratingTextView = (TextView) findViewById(R.id.rating_tv);
        TextView dateTextView = (TextView) findViewById(R.id.date_tv);

        /*
        * Receive Data from MovieActivity and set them into Views of detail layout
        */
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String title = extras.getString(IntentKeys.TITLE_KEY);
            titleTextView.setText(title);
            String posterImage = extras.getString(IntentKeys.IMAGE_KEY);
            Picasso.with(this).load(posterImage).into(poster);
            String overview = extras.getString(IntentKeys.STORY_KEY);
            overviewTextView.setText(overview);
            String rating = extras.getString(IntentKeys.RATING_KEY);
            ratingTextView.setText(rating);
            String date = extras.getString(IntentKeys.DATE_KEY);
            dateTextView.setText(date);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MovieActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return true;
    }
}
