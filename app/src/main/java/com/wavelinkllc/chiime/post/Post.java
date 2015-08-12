/**
 * Created by kelvin on 8/10/15.
 */
package com.wavelinkllc.chiime.post;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.wavelinkllc.chiime.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class Post {

    public final View view;
    public Object tag = null;

    protected CircleImageView profilePicture = null;
    protected TextView nameLabel = null;
    protected TextView usernameLabel = null;
    protected TextView timeLabel = null;
    protected TextView textLabel = null;
    protected ImageView likeImage = null;
    protected ImageView commentImage = null;
    protected ImageView shareImage = null;
    protected Button likeButton = null;
    protected Button commentButton = null;
    protected Button shareButton = null;

    public Post(View convertView)
    {
        this.view = convertView;
        this.profilePicture = (CircleImageView)this.view.findViewById(R.id.profile_picture);
        this.nameLabel = (TextView)this.view.findViewById(R.id.name_label);
        this.usernameLabel = (TextView)this.view.findViewById(R.id.username_label);
        this.timeLabel = (TextView)this.view.findViewById(R.id.time_label);
        this.textLabel = (TextView)this.view.findViewById(R.id.text_label);
        this.likeImage = (ImageView)this.view.findViewById(R.id.like_image);
        this.commentImage = (ImageView)this.view.findViewById(R.id.comment_image);
        this.shareImage = (ImageView)this.view.findViewById(R.id.share_image);
        this.likeButton = (Button)this.view.findViewById(R.id.like_button);
        this.commentButton = (Button)this.view.findViewById(R.id.comment_button);
        this.shareButton = (Button)this.view.findViewById(R.id.share_button);

        convertView.setTag(this);
    }

    public void render(PostItem postItem)
    {
        Ion.with(profilePicture)
                .placeholder(R.drawable.profile)
                .load("http://www.chiime.co" + postItem.picture);
        nameLabel.setText(postItem.name);
        usernameLabel.setText("@" + postItem.username);
        timeLabel.setText(postItem.time.toUpperCase());
        if (null != postItem.text && postItem.text.length() > 0) {
            textLabel.setText(postItem.text);
        } else {
            textLabel.setVisibility(View.GONE);
        }
        likeButton.setText("LIKE (" + postItem.likes + ")");
        commentButton.setText("COMMENT (" + postItem.comments + ")");
    }

}
