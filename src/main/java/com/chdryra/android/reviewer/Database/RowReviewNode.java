/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Controller.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReviewNode implements ReviewerDbRow.TableRow {
    public static final String NODE_ID    = ReviewerDbContract.TableReviewTrees
            .COLUMN_NAME_REVIEW_NODE_ID;
    public static final String REVIEW_ID  = ReviewerDbContract.TableReviewTrees
            .COLUMN_NAME_REVIEW_ID;
    public static final String PARENT_ID  = ReviewerDbContract.TableReviewTrees
            .COLUMN_NAME_PARENT_NODE_ID;
    public static final String IS_AVERAGE = ReviewerDbContract.TableReviewTrees
            .COLUMN_NAME_RATING_IS_AVERAGE;

    private String  mNodeId;
    private String  mReviewId;
    private String  mParentId;
    private boolean mIsAverage;

    public RowReviewNode() {
    }

    public RowReviewNode(ReviewNode node) {
        mNodeId = node.getId().toString();
        mReviewId = node.getReview().getId().toString();
        ReviewNode parent = node.getParent();
        if (parent != null) mParentId = parent.getId().toString();
        mIsAverage = node.isRatingAverageOfChildren();
    }

    public RowReviewNode(Cursor cursor) {
        mNodeId = cursor.getString(cursor.getColumnIndexOrThrow(NODE_ID));
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
        mParentId = cursor.getString(cursor.getColumnIndexOrThrow(PARENT_ID));
        mIsAverage = cursor.getInt(cursor.getColumnIndexOrThrow(IS_AVERAGE)) == 1;
    }

    @Override
    public String getRowId() {
        return mNodeId;
    }

    @Override
    public String getRowIdColumnName() {
        return NODE_ID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(NODE_ID, mNodeId);
        values.put(REVIEW_ID, mReviewId);
        values.put(PARENT_ID, mParentId);
        values.put(IS_AVERAGE, mIsAverage);

        return values;
    }

    @Override
    public boolean hasData() {
        return DataValidator.validateString(getRowId());
    }

    public String getNodeId() {
        return mNodeId;
    }

    public String getParentId() {
        return mParentId;
    }

    public String getReviewId() {
        return mReviewId;
    }
}
