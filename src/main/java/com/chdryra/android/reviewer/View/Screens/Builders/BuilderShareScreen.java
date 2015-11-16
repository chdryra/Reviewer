/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens.Builders;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Interfaces.GridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.Interfaces.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Implementation.ReviewViewAdapterBasic;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityFeed;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.Screens.Interfaces.ReviewView;
import com.chdryra.android.reviewer.View.Screens.ReviewViewAction;
import com.chdryra.android.reviewer.View.Screens.ReviewViewActions;
import com.chdryra.android.reviewer.View.Screens.ReviewViewImpl;
import com.chdryra.android.reviewer.View.Screens.ReviewViewParams;
import com.chdryra.android.reviewer.View.Screens.ReviewViewPerspective;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuilderShareScreen {

    public ReviewView createView(String title, SocialPlatformList socialPlatforms,
                                 ReviewBuilderAdapter builder) {
        GvSocialPlatformList platforms = new GvSocialPlatformList(socialPlatforms);
        ReviewViewAdapter adapter = new ShareScreenAdapter(platforms, builder);

        ReviewViewActions actions = new ReviewViewActions();
        actions.setAction(ReviewViewAction.BannerButtonAction.newDisplayButton(title));
        actions.setAction(new ShareScreenGridItem());
        actions.setAction(new ReviewViewAction.MenuAction(title));

        ReviewViewParams params = new ReviewViewParams();
        params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

        ReviewViewPerspective perspective =
                new ReviewViewPerspective(adapter, params, actions, new ShareScreenModifier());

        return new ReviewViewImpl(perspective);
    }

    private static class ShareScreenGridItem extends ReviewViewAction.GridItemAction {
        //Overridden
        @Override
        public void onGridItemClick(GvData item, int position, View v) {
            GvSocialPlatformList.GvSocialPlatform platform =
                    (GvSocialPlatformList.GvSocialPlatform) item;

            platform.press();
            v.setActivated(platform.isChosen());
        }
    }

    private static class ShareScreenModifier implements ReviewViewPerspective.ReviewViewModifier {
        //Overridden
        @Override
        public View modify(final FragmentReviewView parent, View v, LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

            final Activity activity = parent.getActivity();
            parent.setBannerNotClickable();
            View divider = inflater.inflate(R.layout.horizontal_divider, container, false);
            Button publishButton = (Button) inflater.inflate(R.layout.review_banner_button,
                    container, false);
            publishButton.setText(activity.getResources().getString(R.string.button_publish));
            publishButton.getLayoutParams().height = ActionBar.LayoutParams.MATCH_PARENT;
            publishButton.setOnClickListener(new View.OnClickListener() {
                //Overridden
                @Override
                public void onClick(View v) {
                    ApplicationInstance.getInstance(activity).publishReviewBuilder();
                    Intent intent = new Intent(activity, ActivityFeed.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    parent.startActivity(intent);
                }
            });

            parent.addView(publishButton);
            parent.addView(divider);

            return v;
        }
    }

    private static class ShareScreenAdapter extends ReviewViewAdapterBasic<GvSocialPlatformList
            .GvSocialPlatform> {
        private GvSocialPlatformList mSocialPlatforms;
        private ReviewBuilderAdapter mBuilder;

        private ShareScreenAdapter(GvSocialPlatformList socialPlatforms, ReviewBuilderAdapter builder) {
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
}
