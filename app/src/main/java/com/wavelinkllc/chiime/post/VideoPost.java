/**
 * Created by kelvin on 8/15/15.
 */
package com.wavelinkllc.chiime.post;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.MediaController;

import com.wavelinkllc.chiime.R;
import com.wavelinkllc.chiime.SquareVideoView;

import java.net.URI;

public class VideoPost extends Post {

    private SquareVideoView video;

    public VideoPost(View convertView)
    {
        super(convertView);
        video = (SquareVideoView)view.findViewById(R.id.video);
    }

    @Override
    public void render(PostItem postItem)
    {
        super.render(postItem);
        video.stopPlayback();
        MediaController mediaController = new MediaController(this.view.getContext());
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);
        video.setVideoURI(Uri.parse("http://www.chiime.co" + postItem.video));
        video.requestFocus();
        video.start();
    }

}
