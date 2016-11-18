/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils.DataFormatter;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.RatingFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhReviewSelected extends ViewHolderBasic implements ReviewSelector.ReviewSelectorCallback, VhNode {
    private static final int LAYOUT = R.layout.grid_cell_review_abstract;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating_number;
    private static final int IMAGE = R.id.review_image;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int STAMP = R.id.review_stamp;

    private final AuthorsRepository mAuthorsRepo;
    private final ReviewSelector mSelector;

    private TextView mSubject;
    private TextView mRating;
    private ImageView mImage;
    private TextView mHeadline;
    private TextView mTags;
    private TextView mPublishDate;

    private ReviewId mNodeId;
    private ReviewReference mReview;
    private final CoverBinder mCoverBinder;
    private final TagsBinder mTagsBinder;
    private final CommentsBinder mCommentsBinder;
    private final LocationsBinder mLocationsBinder;
    private final NameBinder mNameBinder;

    private NamedAuthor mAuthor;
    private String mLocation;

    public VhReviewSelected(AuthorsRepository authorsRepo, ReviewSelector selector) {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, TAGS, STAMP});
        mAuthorsRepo = authorsRepo;
        mSelector = selector;

        mCoverBinder = new CoverBinder();
        mCommentsBinder = new CommentsBinder();
        mTagsBinder = new TagsBinder();
        mLocationsBinder = new LocationsBinder();
        mNameBinder = new NameBinder();
    }

    @Override
    public boolean isBoundTo(ReviewNode node) {
        return mNodeId != null && mNodeId.equals(node.getReviewId());
    }

    @Override
    public void unbindFromNode() {
        if (mReview == null) return;
        mReview.getCover().unbindFromValue(mCoverBinder);
        mReview.getComments().unbindFromValue(mCommentsBinder);
        mReview.getLocations().unbindFromValue(mLocationsBinder);
        mReview.getTags().unbindFromValue(mTagsBinder);
        mAuthorsRepo.getName(mReview.getAuthorId()).unbindFromValue(mNameBinder);
        mSelector.unregister(mNodeId);
        mReview = null;
    }

    @Override
    public void updateView(ViewHolderData data) {
        setViewsIfNecessary();
        setNode(((GvNode) data));
    }


    private void setViewsIfNecessary() {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (TextView) getView(RATING);
        if (mImage == null) mImage = (ImageView) getView(IMAGE);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mTags == null) mTags = (TextView) getView(TAGS);
        if (mPublishDate == null) mPublishDate = (TextView) getView(STAMP);
    }

    private void setNode(GvNode gvNode) {
        ReviewNode node = gvNode.getNode();
        if (isBoundTo(node)) return;

        unbindFromNode();

        mNodeId = node.getReviewId();

        initialiseData(node);
        mSelector.select(node, this);
        gvNode.setViewHolder(this);
    }

    private void initialiseData(ReviewNode node) {
        mSubject.setText(node.getSubject().getSubject());
        mRating.setText(RatingFormatter.upToTwoSignificantDigits(node.getRating().getRating()));
        mImage.setImageBitmap(null);
        mHeadline.setText(null);
        mTags.setText(null);
        mPublishDate.setText(null);
        mAuthor = null;
        mLocation = null;
    }

    @Override
    public void onReviewSelected(@Nullable ReviewReference review) {
        if(review != null) {
            bindToReview(review);
        } else {
            unbindFromNode();
        }
    }

    private void bindToReview(ReviewReference review) {
        mReview = review;
        mReview.getCover().bindToValue(mCoverBinder);
        mReview.getComments().bindToValue(mCommentsBinder);
        mReview.getLocations().bindToValue(mLocationsBinder);
        mReview.getTags().bindToValue(mTagsBinder);
        mAuthorsRepo.getName(mReview.getAuthorId()).bindToValue(mNameBinder);
    }

    private void newFooter() {
        if(mReview != null) {
            ReviewStamp stamp = ReviewStamp.newStamp(mReview.getAuthorId(), mReview.getPublishDate());
            String date = stamp.toReadableDate();
            String name = mAuthor != null ? mAuthor.getName() : "";
            String text = name + " " + date + (validateString(mLocation) ? " @" + mLocation : "");
            mPublishDate.setText(text);
        }
    }

    private String getTagString(IdableList<? extends DataTag> tags, int maxTags) {
        return DataFormatter.formatTags(tags, maxTags, mSubject.getText().toString());
    }

    private boolean validateString(@Nullable String string) {
        return string != null && string.length() > 0;
    }

    private void setCover(DataImage cover) {
        mImage.setImageBitmap(cover.getBitmap());
    }

    private void setAuthor(@Nullable NamedAuthor author) {
        mAuthor = author;
        newFooter();
    }

    private void setHeadline(IdableList<DataComment> value) {
        mHeadline.setText(DataFormatter.getHeadlineQuote(value));
    }

    private void setTags(IdableList<? extends DataTag> tags) {
        int i = tags.size();
        String tagsString = getTagString(tags, i--);
        while (i > -1 && TextUtils.isTooLargeForTextView(mTags, tagsString)) {
            tagsString = getTagString(tags, i--);
        }

        mTags.setText(tagsString);
    }

    private void setLocation(IdableList<? extends DataLocation> value) {
        mLocation = DataFormatter.formatLocations(value);
        newFooter();
    }


    private class CoverBinder implements ReferenceBinder<DataImage> {
        @Override
        public void onInvalidated(DataReference<DataImage> reference) {
            setCover(new DatumImage(mNodeId));
        }

        @Override
        public void onReferenceValue(DataImage value) {
            setCover(value);
        }
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

    private class LocationsBinder extends ListBinder<DataLocation> {
        @Override
        protected void onList(IdableList<DataLocation> data) {
            setLocation(data);
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