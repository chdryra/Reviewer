/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.reviewer.GVImageList.GVImage;

/**
 * Dialog for editing images: change/delete image and add/edit/delete comment.
 */
public class DialogImageEditFragment extends DialogEditReviewDataFragment<GVImage> {
    private ClearableEditText mImageCaption;

    public DialogImageEditFragment() {
        super(GVReviewDataList.GVType.IMAGES);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_image_view, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.dialog_image_image_view);
        mImageCaption = (ClearableEditText) v.findViewById(R.id.dialog_image_caption_edit_text);


        imageView.setImageBitmap(getDatum().getBitmap());
        String caption = getDatum().getCaption();
        mImageCaption.setHint(getResources().getString(R.string.edit_text_image_caption_hint));
        mImageCaption.setText(caption);
        //For some reason setSelection(0) doesn't work unless I force set the span of the selection
        if (caption != null && caption.length() > 0) {
            mImageCaption.setSelection(0, caption.length());
            mImageCaption.setSelection(0);
        }

        setDialogTitle(null);
        hideKeyboardOnLaunch();
        setDeleteWhatTitle(getResources().getString(R.string.dialog_delete_image_title));

        return v;
    }

    @Override
    protected GVImage createGVData() {
        getDatum().setCaption(mImageCaption.getText().toString().trim());
        return getDatum();
    }
}
