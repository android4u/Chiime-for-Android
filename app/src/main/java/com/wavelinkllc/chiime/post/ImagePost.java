/**
 * Created by kelvin on 8/10/15.
 */
package com.wavelinkllc.chiime.post;

import android.view.View;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;
import com.wavelinkllc.chiime.R;
import com.wavelinkllc.chiime.SquareImageView;

public class ImagePost extends Post {

    private SquareImageView image;

    public ImagePost(View convertView)
    {
        super(convertView);
        image = (SquareImageView)view.findViewById(R.id.image);
    }

    public void render(PostItem postItem)
    {
        super.render(postItem);
        Ion.with(image)
                .placeholder(R.drawable.user)
                .load("http://www.chiime.co" + postItem.image);
    }

}
