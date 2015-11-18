package com.chdryra.android.reviewer.View.ReviewViewModel.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation
        .ReviewViewAdapterBasic;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces
        .GridDataViewer;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 18/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ShareScreenAdapter extends ReviewViewAdapterBasic<GvSocialPlatformList
        .GvSocialPlatform> {
    private GvSocialPlatformList mSocialPlatforms;
    private ReviewBuilderAdapter mBuilder;

    public ShareScreenAdapter(GvSocialPlatformList socialPlatforms, ReviewBuilderAdapter builder) {
        mSocialPlatforms = socialPlatforms;
        mBuilder = builder;
        setViewer(new ShareScreenViewer());
    }

    //Overridden
    @Override
    public String getSubject() {
        return mBuilder.getSubject();
    }

    @Override
    public float getRating() {
        return mBuilder.getRating();
    }

    @Override
    public GvImageList getCovers() {
        return mBuilder.getCovers();
    }

    private class ShareScreenViewer implements GridDataViewer<GvSocialPlatformList
            .GvSocialPlatform> {

        //Overridden
        @Override
        public GvDataList<GvSocialPlatformList.GvSocialPlatform> getGridData() {
            return mSocialPlatforms;
        }

        @Override
        public boolean isExpandable(GvSocialPlatformList.GvSocialPlatform datum) {
            return false;
        }

        @Override
        public ReviewViewAdapter expandGridCell(GvSocialPlatformList.GvSocialPlatform datum) {
            return null;
        }

        @Override
        public ReviewViewAdapter expandGridData() {
            return null;
        }
    }
}
