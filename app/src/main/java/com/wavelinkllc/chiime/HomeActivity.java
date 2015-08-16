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
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wavelinkllc.chiime.post.ImagePost;
import com.wavelinkllc.chiime.post.PostItem;
import com.wavelinkllc.chiime.post.SoapBoxPost;
import com.wavelinkllc.chiime.post.TextPost;
import com.wavelinkllc.chiime.post.VideoPost;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public enum PostType {
        TEXT(0),
        IMAGE(1),
        SOAPBOX(2),
        VIDEO(3);

        private final int value;
        PostType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    ArrayAdapter<PostItem> postAdapter;
    boolean isLoadingMorePosts = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postAdapter = new ArrayAdapter<PostItem>(this, 0) {

            @Override
            public int getViewTypeCount() {
                return 4;
            }

            @Override
            public int getItemViewType(int position) {
                String type = postAdapter.getItem(position).type;
                if (type.equals("text"))
                    return PostType.TEXT.getValue();
                if (type.equals("image") || type.equals("gif"))
                    return PostType.IMAGE.getValue();
                if (type.equals("soapbox"))
                    return PostType.SOAPBOX.getValue();
                if (type.equals("video"))
                    return PostType.VIDEO.getValue();
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
                } else if (type == PostType.SOAPBOX.getValue()) {
                    SoapBoxPost soapBoxPost;
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.post_soap_box, null);
                        soapBoxPost = new SoapBoxPost(convertView);
                    } else {
                        soapBoxPost = (SoapBoxPost) convertView.getTag();
                    }
                    soapBoxPost.render(getItem(position));
                    convertView = soapBoxPost.view;
                } else if (type == PostType.VIDEO.getValue()) {
                    VideoPost videoPost;
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.post_video, null);
                        videoPost = new VideoPost(convertView);
                    } else {
                        videoPost = (VideoPost) convertView.getTag();
                    }
                    videoPost.render(getItem(position));
                    convertView = videoPost.view;
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
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(totalItemCount > 0) {
                    int lastInScreen = firstVisibleItem + visibleItemCount + 5;
                    if (lastInScreen == totalItemCount && !isLoadingMorePosts) {
                        loadPosts();
                    }
                }
            }
        });

        loadPosts();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.logo);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);

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

    private void loadPosts() {
        isLoadingMorePosts = true;

        String next = "0";
        if(postAdapter.getCount() > 0) {
            next = postAdapter.getItem(postAdapter.getCount() - 1).next.toString();
        }

        Ion.with(this)
            .load("http://www.chiime.co/services/feed/videos.php")
            .setBodyParameter("userId", User.userId)
            .setBodyParameter("next", next)
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
                    isLoadingMorePosts = false;
                }
            });
    }

}
