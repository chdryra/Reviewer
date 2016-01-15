package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Implementation.RowEntryImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowComment;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowCommentImpl extends RowTableBasic implements RowComment {
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
        mReviewId = values.getValue(COLUMN_REVIEW_ID, COLUMN_REVIEW_ID_TYPE);
        mCommentId = values.getValue(COLUMN_COMMENT_ID, COLUMN_REVIEW_ID_TYPE);
        mComment = values.getValue(COLUMN_COMMENT, COLUMN_COMMENT_TYPE);
        mIsHeadline = values.getValue(COLUMN_IS_HEADLINE, COLUMN_IS_HEADLINE_TYPE);
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
        return COLUMN_COMMENT_ID;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }

    @Override
    protected int size() {
        return 4;
    }

    @Override
    protected RowEntry<?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(COLUMN_COMMENT_ID, COLUMN_COMMENT_ID_TYPE, mCommentId);
        } else if(position == 1) {
            return new RowEntryImpl<>(COLUMN_REVIEW_ID, COLUMN_REVIEW_ID_TYPE, mReviewId);
        } else if(position == 2) {
            return new RowEntryImpl<>(COLUMN_COMMENT, COLUMN_COMMENT_TYPE, mComment);
        } else {
            return new RowEntryImpl<>(COLUMN_IS_HEADLINE, COLUMN_IS_HEADLINE_TYPE, mIsHeadline);
        }
    }
}
