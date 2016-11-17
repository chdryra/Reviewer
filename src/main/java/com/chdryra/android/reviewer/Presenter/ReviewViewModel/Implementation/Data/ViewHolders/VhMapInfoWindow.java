/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RatingFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhMapInfoWindow extends ViewHolderBasic implements ReviewSelector.ReviewSelectorCallback {
    private static final int LAYOUT = R.layout.review_map_info_window;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating_number;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int STAMP = R.id.review_stamp;
    public static final int IMAGE = R.id.review_image;

    private final AuthorsRepository mAuthorsRepo;
    private final ReviewSelector mSelector;
    private final GvConverterComments mConverterComments;
    private final InfoUpdateListener mListener;

    private TextView mSubject;
    private TextView mRating;
    private TextView mHeadline;
    private TextView mTags;
    private TextView mPublishDate;

    private ReviewId mNodeId;
    private ReviewReference mReview;
    private final TagsBinder mTagsBinder;
    private final CommentsBinder mCommentsBinder;
    private final NameBinder mNameBinder;

    private NamedAuthor mAuthor;

    public interface InfoUpdateListener {
        void onInfoUpdated();
    }

    public VhMapInfoWindow(AuthorsRepository authorsRepo,
                           ReviewSelector selector,
                           GvConverterComments converterComments,
                           InfoUpdateListener listener) {
        super(LAYOUT, new int[]{LAYOUT, IMAGE, SUBJECT, RATING, HEADLINE, TAGS, STAMP});
        mAuthorsRepo = authorsRepo;
        mSelector = selector;
        mConverterComments = converterComments;
        mListener = listener;
        mCommentsBinder = new CommentsBinder();
        mTagsBinder = new TagsBinder();
        mNameBinder = new NameBinder();
    }

    protected ReviewReference getReview() {
        return mReview;
    }

    public void unbindFromReview() {
        if (mReview == null) return;
        mReview.getComments().unbindFromValue(mCommentsBinder);
        mReview.getTags().unbindFromValue(mTagsBinder);
        mAuthorsRepo.getName(mReview.getAuthorId()).unbindFromValue(mNameBinder);
        mSelector.unregister(mNodeId);
    }

    @Override
    public void updateView(ViewHolderData data) {
        setViewsIfNecessary();
        ReviewNode node = ((GvNode)data).getNode();
        mNodeId = node.getReviewId();
        initialiseData(node);
        mSelector.select(node, this);
    }

    private void setViewsIfNecessary() {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (TextView) getView(RATING);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mTags == null) mTags = (TextView) getView(TAGS);
        if (mPublishDate == null) mPublishDate = (TextView) getView(STAMP);
    }

    private void initialiseData(ReviewNode node) {
        mSubject.setText(node.getSubject().getSubject());
        mRating.setText(RatingFormatter.upToTwoSignificantDigits(node.getRating().getRating()));
        mHeadline.setText(null);
        mTags.setText(null);
        mPublishDate.setText(null);
        mAuthor = null;
    }

    @Override
    public void onReviewSelected(@Nullable ReviewReference review) {
        if(review != null) {
            mReview = review;
            mReview.getComments().bindToValue(mCommentsBinder);
            mReview.getTags().bindToValue(mTagsBinder);
            mAuthorsRepo.getName(mReview.getAuthorId()).bindToValue(mNameBinder);
        }
    }

    private void newFooter() {
        if(mReview != null) {
            ReviewStamp stamp = ReviewStamp.newStamp(mReview.getAuthorId(), mReview.getPublishDate());
            String date = stamp.toReadableDate();
            String name = mAuthor != null ? mAuthor.getName() : "";
            String text = name + " " + date;
            mPublishDate.setText(text);
            notifyListener();
        }
    }

    private void notifyListener() {
        mListener.onInfoUpdated();
    }

    private String getTagString(IdableList<? extends DataTag> tags, int maxTags) {
        String tagsString = "";
        int size = Math.min(tags.size(), Math.max(maxTags, tags.size()));
        int diff = tags.size() - size;
        int i = 0;
        while (i < size) {
            tagsString += "#" + tags.getItem(i).getTag() + " ";
            ++i;
        }

        if (diff > 0) tagsString += "+ " + String.valueOf(diff) + "#";

        return tagsString.trim();
    }

    private boolean validateString(@Nullable String string) {
        return string != null && string.length() > 0;
    }

    private void setAuthor(@Nullable NamedAuthor author) {
        mAuthor = author;
        newFooter();
    }

    private void setHeadline(IdableList<DataComment> value) {
        GvCommentList comments = mConverterComments.convert(value);
        GvCommentList headlines = comments.getHeadlines();
        String headline = headlines.size() > 0 ? headlines.getItem(0).getFirstSentence() : null;
        if (validateString(headline)) {
            String text = "\"" + headline + "\"";
            mHeadline.setText(text);
        } else {
            mHeadline.setText("");
        }
        notifyListener();
    }

    private void setTags(IdableList<? extends DataTag> tags) {
        int i = tags.size();
        String tagsString = getTagString(tags, i--);
        while (i > -1 && TextUtils.isTooLargeForTextView(mTags, tagsString)) {
            tagsString = getTagString(tags, i--);
        }

        mTags.setText(tagsString);
        notifyListener();
    }

    private class NameBinder implements ReferenceBinder<NamedAuthor> {
        @Override
        public void onInvalidated(DataReference<NamedAuthor> reference) {
            setAuthor(null);
        }

        @Override
        public void onReferenceValue(NamedAuthor value) {
            setAuthor(value);
        }
    }

    private class TagsBinder extends ListBinder<DataTag> {
        @Override
        protected void onList(IdableList<DataTag> data) {
            setTags(data);
        }
    }

    private class CommentsBinder extends ListBinder<DataComment> {
        @Override
        protected void onList(IdableList<DataComment> data) {
            setHeadline(data);
        }
    }

    private abstract class ListBinder<T extends HasReviewId> implements
            ReferenceBinder<IdableList<T>> {
        protected abstract void onList(IdableList<T> data);

        @Override
        public void onReferenceValue(IdableList<T> value) {
            onList(value);
        }

        @Override
        public void onInvalidated(DataReference<IdableList<T>> reference) {
            onList(new IdableDataList<T>(mNodeId));
        }
    }
}