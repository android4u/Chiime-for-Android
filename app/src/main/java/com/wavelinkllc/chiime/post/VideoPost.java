/**
 * Created by kelvin on 8/15/15.
 */
package com.wavelinkllc.chiime.post;

import android.content.Intent;
import android.view.View;

import com.koushikdutta.ion.Ion;
import com.wavelinkllc.chiime.HomeActivity;
import com.wavelinkllc.chiime.R;
import com.wavelinkllc.chiime.SquareImageView;
import com.wavelinkllc.chiime.VideoActivity;

public class VideoPost extends Post {

    public final static String VIDEO_PATH = "com.wavelinkllc.chiime.VIDEO_PATH";

    private SquareImageView image;

    public VideoPost(View convertView)
    {
        super(convertView);
        image = (SquareImageView)view.findViewById(R.id.image);
    }

    @Override
    public void render(final PostItem postItem)
    {
        super.render(postItem);
        Ion.with(image)
                .placeholder(R.drawable.load)
                .load("http://www.chiime.co" + postItem.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), VideoActivity.class);
                intent.putExtra(VIDEO_PATH, postItem.video);
                view.getContext().startActivity(intent);
            }
        });
    }

}
