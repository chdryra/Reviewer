/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 19 March, 2015
 */

package com.chdryra.android.reviewer.View.Screens;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.GridDataViewer;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapterBasic;
import com.chdryra.android.reviewer.ApplicationSingletons.Administrator;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.ActivitiesFragments.ActivityFeed;
import com.chdryra.android.reviewer.View.ActivitiesFragments.FragmentReviewView;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;

/**
 * Created by: Rizwan Choudrey
 * On: 19/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ShareScreen {
    //Static methods
    public static ReviewView newScreen(Context context) {
        Administrator admin = Administrator.getInstance(context);
        ReviewBuilderAdapter builder = admin.getReviewBuilder();

        ReviewViewAdapter adapter = new ShareScreenAdapter(context, builder);

        ReviewViewActions actions = new ReviewViewActions();
        String title = context.getResources().getString(R.string.button_social);
        actions.setAction(ReviewViewAction.BannerButtonAction.newDisplayButton(title));
        actions.setAction(new ShareScreenGridItem());
        actions.setAction(new ReviewViewAction.MenuAction(title));

        ReviewViewParams params = new ReviewViewParams();
        params.setGridAlpha(ReviewViewParams.GridViewAlpha.TRANSPARENT);

        ReviewViewPerspective perspective =
                new ReviewViewPerspective(adapter, params, actions, new ShareScreenModifier());

        return new ReviewView(perspective);
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
                    Administrator.getInstance(activity).publishReviewBuilder();
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
        private ReviewBuilderAdapter mBuilder;
        private Context mContext;

        private ShareScreenAdapter(Context context, ReviewBuilderAdapter builder) {
            mBuilder = builder;
            mContext = context;
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
                return Administrator.getInstance(mContext).getSocialPlatformList();
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
