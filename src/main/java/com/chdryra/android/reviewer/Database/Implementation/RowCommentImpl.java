package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValues;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowCommentImpl implements RowComment {
    private static final String SEPARATOR = ":";

    private String mCommentId;
    private String mReviewId;
    private String mComment;
    private boolean mIsHeadline;

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
        mReviewId = values.getString(COLUMN_REVIEW_ID);
        mCommentId = values.getString(COLUMN_COMMENT_ID);
        mComment = values.getString(COLUMN_COMMENT);
        mIsHeadline = values.getBoolean(COLUMN_IS_HEADLINE);
    }

    //Overridden

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
        return COLUMN_COMMENT_ID;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
