/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.Implementation;



import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ProfileImage;
import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 26/05/2016
 * Email: rizwan.choudrey@gmail.com
 *
 * This doesn't work very well as binding is lost if cover deleted due to null path.
 * Maybe should dereference instead.
 */
public class CoverNodeBannerUi extends ViewUi<ImageView, ReviewItemReference<DataImage>>
        implements ViewUiBinder.BindableViewUi<DataImage>{
    private final ViewUiBinder<DataImage> mBinder;
    private final DataReference<ProfileImage> mProfileImage;
    private final Bitmap mPlaceholder;
    private ProfileImageSubscriber mProfileImageBinder;
    private boolean mHasImage = false;

    public CoverNodeBannerUi(ImageView view,
                             final ReviewNode node,
                             DataReference<ProfileImage> profileImage,
                             Bitmap placeholder,
                             CellDimensionsCalculator.Dimensions dims) {
        super(view, new ReferenceValueGetter<ReviewItemReference<DataImage>>() {
            @Override
            public ReviewItemReference<DataImage> getValue() {
                if(node.isLeaf() && node.getReference() != null) {
                    //More efficient this way.
                    return node.getReference().getCover();
                } else {
                    return node.getCover();
                }
            }
        });

        mProfileImage = profileImage;
        mPlaceholder = placeholder;
        view.getLayoutParams().width = dims.getCellWidth();
        view.getLayoutParams().height = dims.getCellHeight();
        mBinder = new ViewUiBinder<>(this);
    }

    @Override
    public void update(DataImage value) {
        Bitmap bitmap = value.getBitmap();
        mHasImage = bitmap != null;
        if(mHasImage) {
            setCover(bitmap);
        } else {
            useProfileImage();
        }
    }

    private void useProfileImage() {
        if(mProfileImageBinder == null) {
            mProfileImageBinder = new ProfileImageSubscriber();
            mProfileImage.subscribe(mProfileImageBinder);
        } else {
            setCover(mProfileImageBinder.getProfileImage());
        }
    }

    @Override
    public void onInvalidated() {
        update(new DatumImage());
    }

    @Override
    public void update() {
        mBinder.bind();
    }

    private void setCover(@Nullable Bitmap image) {
        getView().setImageBitmap(image == null ? mPlaceholder : image);
    }

    private class ProfileImageSubscriber implements DataReference.ValueSubscriber<ProfileImage> {
        private Bitmap mProfileImage;

        @Override
        public void onInvalidated(DataReference<ProfileImage> reference) {
            mProfileImage = null;
            if(!mHasImage) setCover(null);
        }

        @Override
        public void onReferenceValue(ProfileImage value) {
            mProfileImage = value.getBitmap();
            if(!mHasImage) setCover(mProfileImage);
        }

        private Bitmap getProfileImage() {
            return mProfileImage;
        }
    }
}
