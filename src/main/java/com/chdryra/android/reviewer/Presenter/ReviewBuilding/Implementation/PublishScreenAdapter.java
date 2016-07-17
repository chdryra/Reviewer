/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataListImpl;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.GridDataWrapperBasic;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewAdapterBasic;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishScreenAdapter extends ReviewViewAdapterBasic<GvSocialPlatform> {
    private static final GvDataType<GvSocialPlatform> TYPE = GvSocialPlatform.TYPE;
    private GvSocialPlatformList mSocialPlatforms;
    private ReviewViewAdapter<?> mReviewViewAdapter;

    public PublishScreenAdapter(GvSocialPlatformList socialPlatforms,
                                ReviewViewAdapter<?> reviewViewAdapter) {
        mSocialPlatforms = socialPlatforms;
        mReviewViewAdapter = reviewViewAdapter;
        setWrapper(new ShareScreenViewer());
    }

    @Override
    public GvDataType<GvSocialPlatform> getGvDataType() {
        return TYPE;
    }

    @Override
    public String getSubject() {
        return mReviewViewAdapter.getSubject();
    }

    @Override
    public float getRating() {
        return mReviewViewAdapter.getRating();
    }

    @Override
    public void getCover(CoverCallback callback) {
        mReviewViewAdapter.getCover(callback);
    }

    private class ShareScreenViewer extends GridDataWrapperBasic<GvSocialPlatform> {

        @Override
        public GvDataType<GvSocialPlatform> getGvDataType() {
            return TYPE;
        }

        @Override
        public GvDataListImpl<GvSocialPlatform> getGridData() {
            return mSocialPlatforms;
        }

        @Override
        public boolean isExpandable(GvSocialPlatform datum) {
            return false;
        }

        @Override
        public ReviewViewAdapter expandGridCell(GvSocialPlatform datum) {
            return null;
        }

        @Override
        public ReviewViewAdapter expandGridData() {
            return null;
        }
    }
}
