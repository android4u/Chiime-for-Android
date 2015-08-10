package com.wavelinkllc.chiime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    static class Post {
        String id;
        String userId;
        String username;
        String name;
        String picture;
        String text;
        String description;
        String time;
        String image;
        String video;
        String type;
        String sharedId;
        String likes;
        String comments;
        String points;
        String flags;
        String opinion1;
        String opinion2;
        String opinion3;
        String opinion4;
        String caption1;
        String caption2;
        String caption3;
        String caption4;
        String activityUserId;
        String activityText;
        String activityTime;
        String activityImage;
        String activityType;
        String isActivity;
        String originalTime;
        String isLiked;
        String commentlist;
        Number next;
    }

    ArrayAdapter<Post> postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postAdapter = new ArrayAdapter<Post>(this, 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = getLayoutInflater().inflate(R.layout.post, null);

                //if (position >= getCount() - 3);
                    //load();

                Post post = getItem(position);

                ImageView imageView = (ImageView)convertView.findViewById(R.id.picture);
                Ion.with(imageView)
                    .placeholder(R.drawable.user)
                    .load("http://www.chiime.co" + post.picture);

                TextView handle = (TextView)convertView.findViewById(R.id.handle);
                handle.setText(post.username);

                TextView text = (TextView)convertView.findViewById(R.id.tweet);
                text.setText(post.text);
                return convertView;
            }
        };

        setContentView(R.layout.activity_home);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(postAdapter);

        load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void load() {
        Ion.with(this)
            .load("http://www.chiime.co/services/feed/main.php")
            .setBodyParameter("userId", User.userId)
            .setBodyParameter("next", "0")
            .as(new TypeToken<List<Post>>() {
            })
            .setCallback(new FutureCallback<List<Post>>() {
                @Override
                public void onCompleted(Exception e, List<Post> result) {
                    // this is called back onto the ui thread, no Activity.runOnUiThread or Handler.post necessary.
                    if (e != null) {
                        Toast.makeText(HomeActivity.this, "Error loading tweets", Toast.LENGTH_LONG).show();
                        return;
                    }
                    // add the tweets
                    for (int i = 0; i < result.size(); i++) {
                        postAdapter.add(result.get(i));
                    }
                }
            });
    }
}
