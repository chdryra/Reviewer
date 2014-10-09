/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.chdryra.android.reviewer.GVSocialPlatformList.GVSocialPlatform;


public class FragmentReviewShare extends FragmentReviewGrid<GVSocialPlatformList.GVSocialPlatform> {
    private GVSocialPlatformList mSocialList;
    private Drawable             mDefault;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSocialSharingList();

        setGridViewData(mSocialList);
        setGridCellDimension(CellDimension.HALF, CellDimension.QUARTER);
        setDismissOnDone(false);
        setBannerButtonText(getResources().getString(R.string.button_social));
        setIsEditable(false);
        setTransparentGridCellBackground();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        getBannerButton().setClickable(false);
        View divider = inflater.inflate(R.layout.horizontal_divider, container, false);
        Button publishButton = (Button) inflater.inflate(R.layout.review_banner_button,
                container, false);
        publishButton.setText(getResources().getString(R.string.button_publish));
        publishButton.getLayoutParams().height = LayoutParams.MATCH_PARENT;
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Administrator.get(getActivity()).publishReviewInProgress();
                Intent intent = new Intent(getActivity(), ActivityFeed.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        getGridView().getLayoutParams().height = LayoutParams.WRAP_CONTENT;
        getLayout().addView(publishButton);
        getLayout().addView(divider);

        return v;
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        GVSocialPlatform platform = (GVSocialPlatform) parent.getItemAtPosition(position);
        if (mDefault == null && !platform.isChosen()) {
            mDefault = v.getBackground();
        }

        platform.press();

        if (platform.isChosen()) {
            v.setActivated(true);
        } else {
            v.setActivated(false);
        }
    }

    private void initSocialSharingList() {
        mSocialList = Administrator.get(getActivity()).getSocialPlatformList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }
}
