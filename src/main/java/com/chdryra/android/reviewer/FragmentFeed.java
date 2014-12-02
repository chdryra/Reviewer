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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;

/**
 * UI Fragment: published reviews.
 * <p/>
 * <p>
 * The feed is a list of grid cells each showing an overview of each review. A bit basic at
 * the moment. Most of the FragmentReviewGrid functionality is disabled.
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityFeed
 */
public class FragmentFeed extends FragmentReviewGrid<GVReviewOverviewList> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGridViewData(Administrator.get(getActivity()).getPublishedReviewsData());
        setGridCellDimension(CellDimension.FULL, CellDimension.FULL);
        setController(null);
        setDisplayHomeAsUp(false);
        setTransparentGridCellBackground();
    }

    @Override
    protected void onDoneSelected() {
    }

    @Override
    protected void initSubjectUI() {
        super.initSubjectUI();
        getSubjectView().setHint(R.string.search_hint);
    }

    @Override
    protected void initRatingBarUI() {
        getTotalRatingBar().setVisibility(View.GONE);
    }

    @Override
    protected void initBannerButtonUI() {
        getBannerButton().setVisibility(View.GONE);
    }

    @Override
    protected void onGridItemLongClick(AdapterView<?> parent, View v, int position, long id) {

    }

    @Override
    protected void updateGridDataUI() {
        setGridViewData(Administrator.get(getActivity()).getPublishedReviewsData());
        ((ViewHolderAdapter) getGridView().getAdapter()).setData(getGridData());
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_feed, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_item_new_review) {
            requestNewReviewIntent();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void requestNewReviewIntent() {
        Intent i = new Intent(getActivity(), ActivityReviewBuild.class);
        startActivity(i);
    }
}
