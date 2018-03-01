/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.corelibrary.ReferenceModel.Interfaces.DataReference;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataListParcelable;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatformList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.GridDataWrapperBasic;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ReviewViewAdapterBasic;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishScreenAdapter extends ReviewViewAdapterBasic<GvSocialPlatform> {
    private static final GvDataType<GvSocialPlatform> TYPE = GvSocialPlatform.TYPE;
    private final ReviewBuilderAdapter<?> mBuilder;

    public PublishScreenAdapter(GvSocialPlatformList socialPlatforms,
                                ReviewBuilderAdapter<?> builder) {
        super(new ShareScreenViewer(socialPlatforms));
        mBuilder = builder;
    }

    @Override
    public GvDataType<GvSocialPlatform> getGvDataType() {
        return TYPE;
    }

    public String getSubject() {
        return mBuilder.getSubject();
    }

    public float getRating() {
        return mBuilder.getRating();
    }

    @Override
    public DataReference<String> getSubjectReference() {
        return mBuilder.getSubjectReference();
    }

    @Override
    public DataReference<Float> getRatingReference() {
        return mBuilder.getRatingReference();
    }

    @Override
    public void getCover(CoverCallback callback) {
        mBuilder.getCover(callback);
    }

    private static class ShareScreenViewer extends GridDataWrapperBasic<GvSocialPlatform> {
        private GvDataListParcelable<GvSocialPlatform> mSocialPlatforms;

        public ShareScreenViewer(GvDataListParcelable<GvSocialPlatform> socialPlatforms) {
            mSocialPlatforms = socialPlatforms;
        }

        @Override
        public GvDataType<GvSocialPlatform> getGvDataType() {
            return TYPE;
        }

        @Override
        public GvDataListParcelable<GvSocialPlatform> getGridData() {
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
