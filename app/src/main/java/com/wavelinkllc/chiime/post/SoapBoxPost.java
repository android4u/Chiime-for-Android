/**
 * Created by kelvin on 8/15/15.
 */
package com.wavelinkllc.chiime.post;

import android.view.View;
import android.widget.TextView;

import com.wavelinkllc.chiime.R;
import com.wavelinkllc.chiime.SquareTextView;

public class SoapBoxPost extends Post {

    private SquareTextView soapBoxLabel;

    public SoapBoxPost(View convertView)
    {
        super(convertView);
        soapBoxLabel = (SquareTextView)view.findViewById(R.id.soap_box_label);
    }

    @Override
    public void render(PostItem postItem)
    {
        super.render(postItem);
        soapBoxLabel.setText(postItem.description);
    }

}
