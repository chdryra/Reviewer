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
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.GridViewCellAdapter;
import com.chdryra.android.reviewer.GVCommentList.GVComment;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: comments. Each grid cell shows a comment headline or comment sentence depending
 * whether the "Show sentences" ActionBar icon is pressed.
 * <p/>
 * <p>
 * FragmentReviewGrid functionality:
 * <ul>
 * <li>Subject: disabled</li>
 * <li>RatingBar: disabled</li>
 * <li>Banner button: launches DialogCommentAddFragment</li>
 * <li>Grid cell click: launches DialogCommentEditFragment</li>
 * <li>Grid cell long click: same as click</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * Also an ActionBar icon for switching between comment headlines and comment sentences.
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewComments
 * @see com.chdryra.android.reviewer.DialogCommentAddFragment
 * @see com.chdryra.android.reviewer.DialogCommentEditFragment
 */
public class FragmentReviewComments extends FragmentReviewGridAddEdit<GVComment> {
    public static final String COMMENT = "com.chdryra.android.reviewer.comment";

    private GVCommentList mComments;
    private boolean mCommentsAreSplit = false;

    public FragmentReviewComments() {
        super(GVType.COMMENTS);
    }

    @Override
    protected void doDatumAdd(Intent data) {
        String comment = (String) data.getSerializableExtra(DialogCommentAddFragment
                .COMMENT);
        if (comment != null && comment.length() > 0 && !mComments.contains(comment)) {
            mComments.add(comment);
        }
    }

    @Override
    protected void doDatumDelete(Intent data) {
        mComments.remove((String) data.getSerializableExtra(DialogCommentEditFragment
                .COMMENT_OLD));
    }

    @Override
    protected void doDatumEdit(Intent data) {
        String oldComment = (String) data.getSerializableExtra(DialogCommentEditFragment
                .COMMENT_OLD);
        String newComment = (String) data.getSerializableExtra(DialogCommentEditFragment
                .COMMENT_NEW);
        if (!oldComment.equalsIgnoreCase(newComment) && mComments.contains(newComment)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.toast_has_comment),
                    Toast.LENGTH_SHORT).show();
        } else {
            mComments.remove(oldComment);
            mComments.add(newComment);
        }
    }

    @Override
    protected Bundle packGridCellData(GVComment comment, Bundle args) {
        args.putString(COMMENT, comment.getUnSplitComment().getComment());
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComments = (GVCommentList) getGridData();
    }

    @Override
    void updateGridDataUI() {
        if (mCommentsAreSplit) {
            ((GridViewCellAdapter) getGridView().getAdapter()).setData(mComments.getSplitComments
                    ());
        } else {
            ((GridViewCellAdapter) getGridView().getAdapter()).setData(mComments);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_review_comment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_item_split_comment) {
            splitOrUnsplitComments(item);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
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
        updateGridDataUI();
    }
}