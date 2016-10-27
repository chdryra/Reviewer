/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Application.Implementation.Strings;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewParams {
    private final GridViewParams mGridViewParams = new GridViewParams();
    private final SubjectParams mSubjectParams = new SubjectParams();
    private final RatingBarParams mRatingBarParams = new RatingBarParams();
    private boolean mCoverManager = true;

    public enum GridViewAlpha {
        TRANSPARENT(0),
        MEDIUM(200),
        OPAQUE(255);

        private final int mAlpha;

        GridViewAlpha(int alpha) {
            this.mAlpha = alpha;
        }

        public int getAlpha() {
            return mAlpha;
        }
    }

    public enum CellDimension {
        FULL(1),
        HALF(2),
        QUARTER(4),
        EIGHTH(8);

        private final int mDivider;
        CellDimension(int divider) {
            mDivider = divider;
        }

        public int getDivider() {
            return mDivider;
        }
    }

    public GridViewParams getGridViewParams() {
        return mGridViewParams;
    }

    public SubjectParams getSubjectParams() {
        return mSubjectParams;
    }

    public RatingBarParams getRatingBarParams() {
        return mRatingBarParams;
    }

    public boolean manageCover() {
        return mCoverManager;
    }

    public ReviewViewParams setCoverManager(boolean coverManager) {
        mCoverManager = coverManager;
        return this;
    }

    public static class SubjectParams {
        private boolean mIsEditable;
        private boolean mUpdateOnRefresh;
        private String mHint;

        public SubjectParams() {
            mIsEditable = false;
            mUpdateOnRefresh = true;
            mHint = Strings.EditTexts.Hints.SUBJECT;
        }

        public SubjectParams setEditable(boolean editable) {
            mIsEditable = editable;
            return this;
        }

        public SubjectParams setUpdateOnRefresh(boolean updateOnRefresh) {
            mUpdateOnRefresh = updateOnRefresh;
            return this;
        }

        public SubjectParams setHint(String hint) {
            mHint = hint;
            return this;
        }

        public boolean isEditable() {
            return mIsEditable;
        }

        public boolean isUpdateOnRefresh() {
            return mUpdateOnRefresh;
        }

        public String getHint() {
            return mHint;
        }
    }

    public static class RatingBarParams {
        private boolean mIsEditable;
        private boolean mIsVisible;

        public RatingBarParams() {
            mIsEditable = false;
            mIsVisible = true;
        }

        public RatingBarParams setEditable(boolean editable) {
            mIsEditable = editable;
            return this;
        }

        public RatingBarParams setVisible(boolean visible) {
            mIsVisible = visible;
            return this;
        }

        public boolean isEditable() {
            return mIsEditable;
        }

        public boolean isVisible() {
            return mIsVisible;
        }
    }

    public static class GridViewParams {
        private GridViewAlpha mGridAlpha = GridViewAlpha.TRANSPARENT;
        private CellDimension mCellWidth = CellDimension.HALF;
        private CellDimension mCellHeight = CellDimension.QUARTER;

        public int getGridAlpha() {
            return mGridAlpha.getAlpha();
        }

        public GridViewParams setGridAlpha(GridViewAlpha gridAlpha) {
            mGridAlpha = gridAlpha;
            return this;
        }

        public CellDimension getCellWidth() {
            return mCellWidth;
        }

        public GridViewParams setCellWidth(CellDimension cellWidth) {
            mCellWidth = cellWidth;
            return this;
        }

        public CellDimension getCellHeight() {
            return mCellHeight;
        }

        public GridViewParams setCellHeight(CellDimension cellHeight) {
            mCellHeight = cellHeight;
            return this;
        }
    }
}
