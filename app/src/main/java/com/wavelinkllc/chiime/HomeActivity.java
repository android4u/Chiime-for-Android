package com.wavelinkllc.chiime;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.wavelinkllc.chiime.post.ImagePost;
import com.wavelinkllc.chiime.post.PostItem;
import com.wavelinkllc.chiime.post.TextPost;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public enum PostType {
        TEXT(0),
        IMAGE(1);

        private final int value;
        PostType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    ArrayAdapter<PostItem> postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postAdapter = new ArrayAdapter<PostItem>(this, 0) {

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int position) {
                String type = postAdapter.getItem(position).type;
                if (type.equals("text"))
                    return PostType.TEXT.getValue();
                if (type.equals("image"))
                    return PostType.IMAGE.getValue();
                return 0;
            }

            @Override

            public View getView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                int type = getItemViewType(position);

                if (type == PostType.TEXT.getValue()) {
                    TextPost textPost;
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.post_text, null);
                        textPost = new TextPost(convertView);
                    } else {
                        textPost = (TextPost) convertView.getTag();
                    }
                    textPost.render(getItem(position));
                    convertView = textPost.view;
                } else if (type == PostType.IMAGE.getValue()) {
                    ImagePost imagePost;
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.post_image, null);
                        imagePost = new ImagePost(convertView);
                    } else {
                        imagePost = (ImagePost) convertView.getTag();
                    }
                    imagePost.render(getItem(position));
                    convertView = imagePost.view;
                } else {
                    TextPost textPost;
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.post_text, null);
                        textPost = new TextPost(convertView);
                    } else {
                        textPost = (TextPost) convertView.getTag();
                    }
                    textPost.render(getItem(position));
                    convertView = textPost.view;
                }

                return convertView;

            }
        };

        setContentView(R.layout.activity_home);
        ListView listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(postAdapter);

        load();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.logo);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
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
            .as(new TypeToken<List<PostItem>>() {
            })
            .setCallback(new FutureCallback<List<PostItem>>() {
                @Override
                public void onCompleted(Exception e, List<PostItem> result) {
                    if (e != null) {
                        Toast.makeText(HomeActivity.this, "Could not connect. Please check your internet connection.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    for (int i = 0; i < result.size(); i++) {
                        postAdapter.add(result.get(i));
                    }
                }
            });
    }
}
