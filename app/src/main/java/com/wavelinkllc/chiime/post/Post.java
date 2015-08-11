/**
 * Created by kelvin on 8/10/15.
 */
package com.wavelinkllc.chiime.post;

import android.view.View;
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
    protected TextView textLabel = null;

    public Post(View convertView)
    {
        this.view = convertView;
        this.profilePicture = (CircleImageView)this.view.findViewById(R.id.profile_picture);
        this.nameLabel = (TextView)this.view.findViewById(R.id.name_label);
        this.usernameLabel = (TextView)this.view.findViewById(R.id.username_label);
        this.textLabel = (TextView)this.view.findViewById(R.id.text_label);

        convertView.setTag(this);
    }

    public void render(PostItem postItem)
    {
        Ion.with(profilePicture)
                .placeholder(R.drawable.user)
                .load("http://www.chiime.co" + postItem.picture);
        nameLabel.setText(postItem.name);
        usernameLabel.setText(postItem.username);
        textLabel.setText(postItem.text);
    }

}
