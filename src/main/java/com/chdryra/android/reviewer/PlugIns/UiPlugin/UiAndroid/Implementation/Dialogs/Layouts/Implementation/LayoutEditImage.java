/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Implementation;

import android.widget.EditText;
import android.widget.ImageView;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Dialogs.Layouts.Interfaces.GvDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 18/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class LayoutEditImage extends AddEditLayoutBasic<GvImage> {
    public static final int LAYOUT = R.layout.dialog_image_edit;
    public static final int IMAGE = R.id.photo_image_view;
    public static final int CAPTION = R.id.caption_edit_text;

    private GvImage mCurrent;

    //Constructors
    public LayoutEditImage(GvDataEditor editor) {
        super(GvImage.class, new LayoutHolder(LAYOUT, IMAGE, CAPTION), CAPTION, editor);
    }

    //Overridden
    @Override
    public GvImage createGvDataFromInputs() {
        String caption = ((EditText) getView(CAPTION)).getText().toString().trim();
        mCurrent = new GvImage(mCurrent.getBitmap(), mCurrent.getDate(),
                mCurrent.getLatLng(), caption, mCurrent.isCover());

        return mCurrent;
    }

    @Override
    public void updateLayout(GvImage image) {
        ImageView imageView = (ImageView) getView(IMAGE);
        EditText imageCaption = (EditText) getView(CAPTION);

        imageView.setImageBitmap(image.getBitmap());
        String caption = image.getCaption();
        imageCaption.setText(caption);

        //For some reason setSelection(0) doesn't work unless I force set the span of the selection
        if (caption != null && caption.length() > 0) {
            imageCaption.setSelection(0, caption.length());
            imageCaption.setSelection(0);
        }

        mCurrent = image;
    }
}
