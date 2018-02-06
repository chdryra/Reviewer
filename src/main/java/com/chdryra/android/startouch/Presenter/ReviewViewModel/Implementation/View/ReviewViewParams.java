/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.startouch.Application.Implementation.Strings;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewViewParams {
    private Alpha mAlpha = Alpha.FOG;
    private final Subject mSubjectParams = new Subject();
    private final RatingBar mRatingBarParams = new RatingBar();
    private final BannerButton mBannerButton = new BannerButton();
    private final GridView mGridViewParams = new GridView();
    private final ContextView mContextView = new ContextView();

    private boolean mCoverManager = true;
    private ViewType mViewType;

    public enum ViewType {EDIT, VIEW}

    public enum Alpha {
        TRANSPARENT(0),
        GLASS(50),
        FOG(200),
        OPAQUE(255);

        private final int mAlpha;

        Alpha(int alpha) {
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
        EIGHTH(8),
        WRAPPED(-1);

        private final int mDivider;
        CellDimension(int divider) {
            mDivider = divider;
        }

        public int getDivider() {
            return mDivider;
        }
    }


    public ReviewViewParams() {
        mViewType = ViewType.VIEW;
    }

    public ReviewViewParams(ViewType viewType) {
        mViewType = viewType;
    }

    public ViewType getViewType() {
        return mViewType;
    }

    public Subject getSubjectParams() {
        return mSubjectParams;
    }

    public RatingBar getRatingBarParams() {
        return mRatingBarParams;
    }

    public BannerButton getBannerButtonParams() {
        return mBannerButton;
    }

    public GridView getGridViewParams() {
        return mGridViewParams;
    }

    public ContextView getContextViewParams() {
        return mContextView;
    }

    public boolean manageCover() {
        return mCoverManager;
    }

    public ReviewViewParams setEditable() {
        mRatingBarParams.setEditable(true);
        mSubjectParams.setEditable(true);
        return this;
    }

    public ReviewViewParams setCoverManager(boolean coverManager) {
        mCoverManager = coverManager;
        return this;
    }

    public ReviewViewParams setAlpha(Alpha alpha) {
        mAlpha = alpha;
        return this;
    }

    public class Subject {
        private boolean mIsEditable;
        private boolean mUpdateOnRefresh;
        private String mHint;

        private Subject() {
            mIsEditable = false;
            mUpdateOnRefresh = true;
            mHint = Strings.EditTexts.Hints.SUBJECT;
        }

        public Subject setEditable(boolean editable) {
            mIsEditable = editable;
            return this;
        }

        public Subject setUpdateOnRefresh(boolean updateOnRefresh) {
            mUpdateOnRefresh = updateOnRefresh;
            return this;
        }

        public Subject setHint(String hint) {
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

        public int getAlpha() {
            return mAlpha.getAlpha();
        }
    }

    public class RatingBar {
        private boolean mIsEditable;
        private boolean mIsVisible;

        private RatingBar() {
            mIsEditable = false;
            mIsVisible = true;
        }

        public RatingBar setEditable(boolean editable) {
            mIsEditable = editable;
            return this;
        }

        public RatingBar setVisible(boolean visible) {
            mIsVisible = visible;
            return this;
        }

        public boolean isEditable() {
            return mIsEditable;
        }

        public boolean isVisible() {
            return mIsVisible;
        }

        public int getAlpha() {
            return mAlpha.getAlpha();
        }
    }

    public class BannerButton {
        private BannerButton() {

        }

        public int getAlpha() {
            return mAlpha.getAlpha();
        }
    }

    public class GridView {
        private CellDimension mCellWidth = CellDimension.HALF;
        private CellDimension mCellHeight = CellDimension.QUARTER;

        private GridView() {
        }

        public CellDimension getCellWidth() {
            return mCellWidth;
        }

        public GridView setCellWidth(CellDimension cellWidth) {
            mCellWidth = cellWidth;
            return this;
        }

        public CellDimension getCellHeight() {
            return mCellHeight;
        }

        public GridView setCellHeight(CellDimension cellHeight) {
            mCellHeight = cellHeight;
            return this;
        }

        public int getAlpha() {
            return mAlpha.getAlpha();
        }
    }

    public class ContextView {
        private ContextView() {

        }

        public int getAlpha() {
            return mAlpha.getAlpha();
        }
    }
}
