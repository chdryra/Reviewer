/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogTwoButtonFragment.ActionType;
import com.chdryra.android.mygenerallibrary.FunctionPointer;
import com.chdryra.android.reviewer.GVImageList.GVImage;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: images. Each grid cell shows an image.
 * <p/>
 * <p>
 * FragmentReviewGrid functionality:
 * <ul>
 * <li>Subject: disabled</li>
 * <li>RatingBar: disabled</li>
 * <li>Banner button: calls <code>mHelperReviewImage.getImageChooserIntents(.)</code></li>
 * <li>Grid cell click: launches DialogImageEditFragment</li>
 * <li>Grid cell long click: launches DialogSetImageAsBackgroundFragment</li>
 * </ul>
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewImages
 * @see com.chdryra.android.reviewer.HelperReviewImage
 * @see com.chdryra.android.reviewer.DialogImageEditFragment
 * @see com.chdryra.android.reviewer.DialogSetImageAsBackgroundFragment
 */
public class FragmentReviewImages extends FragmentReviewGridAddEdit<GVImage> {
    private static final String POSITION = "com.chdryra.android.reviewer.image_position";

    private static final String IMAGE_BACKGROUND_TAG = "DataEditTag";
    private static final int    IMAGE_AS_BACKGROUND  = 20;

    private GVImageList       mImages;
    private HelperReviewImage mHelperReviewImage;

    public FragmentReviewImages() {
        mDataType = GVType.IMAGES;
        setGridCellDimension(CellDimension.HALF, CellDimension.HALF);
        setResultCode(Action.ADD, ActivityResultCode.OK);
    }

    @Override
    protected void doDatumAdd(Intent data) {
        mHelperReviewImage.addReviewImage(getActivity(), mImages,
                new FunctionPointer<Void>() {
                    @Override
                    public void execute(Void data) {
                        if (mImages.size() == 1) setCover(0);
                        updateUI();
                    }
                }
        );
    }

    @Override
    protected void doDatumDelete(Intent data) {
        boolean isCover = getInputHandler().unpack(InputHandlerReviewData.CurrentNewDatum
                .CURRENT, data).isCover();
        super.doDatumDelete(data);
        if (isCover) setCover(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getRequestCodeAdd()) {
            if (mHelperReviewImage.bitmapExistsOnActivityResult(getActivity(), resultCode, data)) {
                onAddRequested(resultCode, data);
            }
        } else if (requestCode == IMAGE_AS_BACKGROUND) {
            if (ActionType.YES.getResultCode().equals(resultCode)) {
                setCover(data.getIntExtra(POSITION, 0));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImages = (GVImageList) getGridData();
        mHelperReviewImage = HelperReviewImage.getInstance(getController());
    }

    @Override
    protected void onBannerButtonClick() {
        startActivityForResult(mHelperReviewImage.getImageChooserIntents(getActivity()),
                getRequestCodeAdd());
    }

    @Override
    protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        if (mImages.getItem(position).isCover()) return;

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        DialogShower.show(new DialogSetImageAsBackgroundFragment(), FragmentReviewImages.this,
                IMAGE_AS_BACKGROUND, IMAGE_BACKGROUND_TAG, args);
    }

    @Override
    void updateCover() {
        updateCover(mImages);
    }

    private void setCover(int position) {
        if (mImages.size() > 0 && position < mImages.size()) {
            for (GVImage image : mImages) {
                image.setIsCover(false);
            }

            mImages.getItem(position).setIsCover(true);
        }

        updateCover();
    }
}
