/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.ViewHolderAdapter;

/**
 * UI Fragment: comments. Each grid cell shows a comment headline or comment sentence depending
 * whether the "Show sentences" ActionBar icon is pressed.
 * <p>
 * Also an ActionBar icon for switching between comment headlines and comment sentences.
 * </p>
 */
public class FragmentReviewComments extends FragmentReviewGridAddEdit<GvCommentList.GvComment> {
    private GvCommentList mComments;
    private boolean mCommentsAreSplit = false;

    public FragmentReviewComments() {
        super(GvCommentList.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComments = (GvCommentList) getGridData();
    }

    @Override
    protected void packGridCellData(GvCommentList.GvComment comment, Bundle args) {
        super.packGridCellData(comment.getUnSplitComment(), args);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_review_comment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_split_comment) {
            splitOrUnsplitComments(item);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    void updateGridDataUi() {
        if (mCommentsAreSplit) {
            ((ViewHolderAdapter) getGridView().getAdapter()).setData(mComments.getSplitComments
                    ());
        } else {
            ((ViewHolderAdapter) getGridView().getAdapter()).setData(mComments);
        }
    }

    private void splitOrUnsplitComments(MenuItem item) {
        mCommentsAreSplit = !mCommentsAreSplit;
        item.setIcon(mCommentsAreSplit ? R.drawable.ic_action_return_from_full_screen : R
                .drawable.ic_action_full_screen);
        if (mCommentsAreSplit) {
            Toast.makeText(getActivity(), R.string.toast_split_comment, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.toast_unsplit_comment,
                    Toast.LENGTH_SHORT).show();
        }
        updateGridDataUi();
    }
}