/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 27 January, 2015
 */

package com.chdryra.android.reviewer;

import android.app.Fragment;
import android.content.Intent;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;

/**
 * Created by: Rizwan Choudrey
 * On: 27/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonAddImage extends BannerButtonAdd {
    private static final String TAG = "BannerButtonAddImageListener";
    private ImageChooser mImageChooser;
    private Fragment mListener;

    public BannerButtonAddImage(ControllerReviewEditable controller) {
        super(controller, GvDataList.GvType.IMAGES);
        mListener = new AddImageListener() {
        };
        registerActionListener(mListener, TAG);
    }

    @Override
    public void onSetViewReview() {
        super.onSetViewReview();
        mImageChooser = Administrator.getImageChooser(getActivity());
    }

    @Override
    public void onClick(View v) {
        if (getViewReview() == null) return;
        mListener.startActivityForResult(mImageChooser.getChooserIntents(), getRequestCode());
    }

    private void setCover() {
        GvImageList images = (GvImageList) getData();
        GvImageList.GvImage cover = images.getItem(0);
        cover.setIsCover(true);
        getViewReview().updateUi();
    }

    private abstract class AddImageListener extends Fragment implements ImageChooser
            .ImageChooserListener {

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            boolean isOk = ActivityResultCode.OK.equals(resultCode);
            if (requestCode == getRequestCode() && isOk && mImageChooser.chosenImageExists
                    (ActivityResultCode.get(resultCode), data)) {
                mImageChooser.getChosenImage(this);
            }
        }

        @Override
        public void onImageChosen(GvImageList.GvImage image) {
            boolean success = addData(image);
            if (success && getData().size() == 1) {
                setCover();
            }
        }
    }
}