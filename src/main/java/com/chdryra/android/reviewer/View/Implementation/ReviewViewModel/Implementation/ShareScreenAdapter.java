package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.ReviewViewAdapterBasic;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces.GridDataViewer;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataListImpl;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImageList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSocialPlatform;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ShareScreenAdapter extends ReviewViewAdapterBasic<GvSocialPlatform> {
    private static final GvDataType<GvSocialPlatform> TYPE = GvSocialPlatform.TYPE;
    private GvSocialPlatformList mSocialPlatforms;
    private ReviewViewAdapter<?> mReviewViewAdapter;

    public ShareScreenAdapter(GvSocialPlatformList socialPlatforms, ReviewViewAdapter<?> reviewViewAdapter) {
        mSocialPlatforms = socialPlatforms;
        mReviewViewAdapter = reviewViewAdapter;
        setViewer(new ShareScreenViewer());
    }

    //Overridden

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
    public GvImageList getCovers() {
        return mReviewViewAdapter.getCovers();
    }

    private class ShareScreenViewer implements GridDataViewer<GvSocialPlatform> {

        //Overridden
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
