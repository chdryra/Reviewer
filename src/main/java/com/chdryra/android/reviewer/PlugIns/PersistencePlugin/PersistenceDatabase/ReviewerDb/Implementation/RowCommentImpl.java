package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
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
        mCommentId = values.getValue(COMMENT_ID.getName(), COMMENT_ID.getType());
        mReviewId = values.getValue(REVIEW_ID.getName(), REVIEW_ID.getType());
        mComment = values.getValue(COMMENT.getName(), COMMENT.getType());
        mIsHeadline = values.getValue(IS_HEADLINE.getName(), IS_HEADLINE.getType());
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
        return validator.validate(this) && validator.validateString(mCommentId)
                && validator.validateString(mReviewId);
    }

    @Override
    protected int size() {
        return 4;
    }

    @Override
    protected RowEntry<?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(COMMENT_ID, mCommentId);
        } else if(position == 1) {
            return new RowEntryImpl<>(REVIEW_ID, mReviewId);
        } else if(position == 2) {
            return new RowEntryImpl<>(COMMENT, mComment);
        } else if(position == 3){
            return new RowEntryImpl<>(IS_HEADLINE, mIsHeadline);
        } else {
            throw noElement();
        }
    }
}
