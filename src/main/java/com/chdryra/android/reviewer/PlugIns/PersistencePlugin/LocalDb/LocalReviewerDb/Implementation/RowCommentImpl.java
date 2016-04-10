/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation;



import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.RowValues;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowCommentImpl extends RowTableBasic<RowComment> implements RowComment {
    private static final String SEPARATOR = ":";

    private String mCommentId;
    private String mReviewId;
    private String mComment;
    private boolean mIsHeadline;

    private boolean mValidIsHeadline = true;

    //Constructors
    public RowCommentImpl(DataComment comment, int index) {
        mReviewId = comment.getReviewId().toString();
        mCommentId = mReviewId + SEPARATOR + "c" + String.valueOf(index);
        mComment = comment.getComment();
        mIsHeadline = comment.isHeadline();
    }

    //Via reflection
    public RowCommentImpl() {
    }

    public RowCommentImpl(RowValues values) {
        mCommentId = values.getValue(COMMENT_ID.getName(), COMMENT_ID.getType());
        mReviewId = values.getValue(REVIEW_ID.getName(), REVIEW_ID.getType());
        mComment = values.getValue(COMMENT.getName(), COMMENT.getType());
        Boolean isHeadline = values.getValue(IS_HEADLINE.getName(), IS_HEADLINE.getType());
        if(isHeadline == null) mValidIsHeadline = false;
        mIsHeadline = mValidIsHeadline && isHeadline;
    }

    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId(mReviewId);
    }

    @Override
    public String getComment() {
        return mComment;
    }

    @Override
    public boolean isHeadline() {
        return mIsHeadline;
    }

    @Override
    public String getRowId() {
        return mCommentId;
    }

    @Override
    public String getRowIdColumnName() {
        return COMMENT_ID.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return mValidIsHeadline && validator.validate(this) && validator.validateString(mCommentId)
                && validator.validateString(mReviewId);
    }

    @Override
    protected int size() {
        return 4;
    }

    @Override
    protected RowEntry<RowComment, ?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(RowComment.class, COMMENT_ID, mCommentId);
        } else if(position == 1) {
            return new RowEntryImpl<>(RowComment.class, REVIEW_ID, mReviewId);
        } else if(position == 2) {
            return new RowEntryImpl<>(RowComment.class, COMMENT, mComment);
        } else if(position == 3){
            return new RowEntryImpl<>(RowComment.class, IS_HEADLINE, mIsHeadline);
        } else {
            throw noElement();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowCommentImpl)) return false;

        RowCommentImpl that = (RowCommentImpl) o;

        if (mIsHeadline != that.mIsHeadline) return false;
        if (mValidIsHeadline != that.mValidIsHeadline) return false;
        if (mCommentId != null ? !mCommentId.equals(that.mCommentId) : that.mCommentId != null)
            return false;
        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null)
            return false;
        return !(mComment != null ? !mComment.equals(that.mComment) : that.mComment != null);

    }

    @Override
    public int hashCode() {
        int result = mCommentId != null ? mCommentId.hashCode() : 0;
        result = 31 * result + (mReviewId != null ? mReviewId.hashCode() : 0);
        result = 31 * result + (mComment != null ? mComment.hashCode() : 0);
        result = 31 * result + (mIsHeadline ? 1 : 0);
        result = 31 * result + (mValidIsHeadline ? 1 : 0);
        return result;
    }
}
