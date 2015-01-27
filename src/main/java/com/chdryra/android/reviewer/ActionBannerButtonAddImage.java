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
public class ActionBannerButtonAddImage extends ActionBannerButtonAdd {
    private ImageChooser mImageChooser;

    public ActionBannerButtonAddImage(ControllerReviewEditable controller) {
        super(controller, GvDataList.GvType.IMAGES);
    }

    @Override
    public void onSetReviewView() {
        super.onSetReviewView();
        mImageChooser = Administrator.getImageChooser(getActivity());
    }

    @Override
    protected Fragment getNewListener() {
        return new AddImageListener() {
        };
    }

    @Override
    public void onClick(View v) {
        if (getReviewView() == null) return;

        Intent options = mImageChooser.getChooserIntents();
        Fragment listener = getListener();
        listener.startActivityForResult(options, getRequestCode());
    }

    private void setCover() {
        GvImageList images = (GvImageList) getData();
        GvImageList.GvImage cover = images.getItem(0);
        cover.setIsCover(true);

        getReviewView().setCover(cover);
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
