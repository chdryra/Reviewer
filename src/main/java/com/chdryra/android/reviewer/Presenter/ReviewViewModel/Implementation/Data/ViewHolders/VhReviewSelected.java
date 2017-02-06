/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ProfileImage;
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
public class VhReviewSelected extends ViewHolderBasic implements ReviewSelector
        .ReviewSelectorCallback, VhNode, ReviewReference.ReviewReferenceObserver {
    private static final int LAYOUT = R.layout.grid_cell_review_abstract;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating_number;
    private static final int IMAGE = R.id.review_image;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int STAMP = R.id.review_stamp;

    private final AuthorsRepository mAuthorsRepo;
    private final ReviewSelector mSelector;
    private final Bitmap mImagePlaceholder;
    private final CoverBinder mCoverBinder;
    private final TagsBinder mTagsBinder;
    private final CommentsBinder mCommentsBinder;
    private final LocationsBinder mLocationsBinder;
    private final NameBinder mNameBinder;

    private TextView mSubject;
    private TextView mRating;
    private ImageView mImage;
    private TextView mHeadline;
    private TextView mTags;
    private TextView mFooter;
    private ReviewId mNodeId;
    private ReviewReference mReview;
    private NamedAuthor mAuthor;
    private String mLocation;

    private int mNumReturned = 0;
    private boolean mCancelCover = false;
    private AsyncTask<GvNode, Void, GvNode> mBindingTask;

    private ProfileImageBinder mProfileImageBinder;
    private DataReference<ProfileImage> mProfileImage;

    public VhReviewSelected(AuthorsRepository authorsRepo, ReviewSelector selector, Bitmap
            imagePlaceholder) {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, TAGS, STAMP});
        mAuthorsRepo = authorsRepo;
        mSelector = selector;
        mImagePlaceholder = imagePlaceholder;

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
        mReview.unregisterObserver(this);
        mAuthorsRepo.getReference(mReview.getAuthorId()).unbindFromValue(mNameBinder);
        unbindFromProfileimage();
        mSelector.unregister(mNodeId);
        mReview = null;
        mNodeId = null;
    }

    @Override
    public void updateView(ViewHolderData data) {
        setViewsIfNecessary();
        setNode(((GvNode) data));
    }

    @Override
    public void onReviewSelected(@Nullable ReviewReference review) {
        if (review != null) {
            bindToReview(review);
        } else {
            unbindFromNode();
        }
    }

    private void setViewsIfNecessary() {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (TextView) getView(RATING);
        if (mImage == null) mImage = (ImageView) getView(IMAGE);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mTags == null) mTags = (TextView) getView(TAGS);
        if (mFooter == null) mFooter = (TextView) getView(STAMP);
    }

    private void setNode(GvNode node) {
        if (isBoundTo(node.getNode())) return;
        if(mNumReturned != 0) mCancelCover = true;
        initialiseData(node);
        unbindFromNode();
        if (mBindingTask != null) mBindingTask.cancel(true);
        mBindingTask = new SelectAndBindTask().execute(node);
    }

    private void selectAndBind(GvNode gvNode) {
        mBindingTask = null;
        ReviewNode node = gvNode.getNode();

        mNodeId = node.getReviewId();

        gvNode.setViewHolder(this);
        mSelector.select(node, this);
    }

    private void initialiseData(GvNode node) {
        mSubject.setText(node.getSubject().getSubject());
        mRating.setText(RatingFormatter.upToTwoSignificantDigits(node.getRating().getRating()));
        setCover(null);
        mHeadline.setText("");
        mTags.setText("");
        mFooter.setText("");
        mAuthor = null;
        mLocation = null;
    }

    private void newFooter() {
        if (mReview != null) {
            ReviewStamp stamp = ReviewStamp.newStamp(mReview.getAuthorId(), mReview
                    .getPublishDate());
            String date = stamp.toReadableDate();
            String name = mAuthor != null ? mAuthor.getName() : "";
            String text = name + " " + date + (validateString(mLocation) ? " @" + mLocation : "");
            mFooter.setText(text);
        }
    }

    private String getTagString(IdableList<? extends DataTag> tags, int maxTags) {
        String ignoreTag = TextUtils.toTag(mSubject.getText().toString());
        return DataFormatter.formatTags(tags, maxTags, ignoreTag);
    }

    private boolean validateString(@Nullable String string) {
        return string != null && string.length() > 0;
    }

    private void setCover(@Nullable Bitmap bitmap) {
        bitmap = bitmap != null ? bitmap : mImagePlaceholder;
        mImage.setImageBitmap(bitmap);
    }

    private void setAuthor(@Nullable NamedAuthor author) {
        mAuthor = author;
        newFooter();
        returned();
    }

    private void setHeadline(IdableList<DataComment> value) {
        String headline = DataFormatter.getHeadlineQuote(value);
        if (headline.length() == 0) headline = Strings.Formatted.NO_COMMENT;
        mHeadline.setText(headline);
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
        mLocation = DataFormatter.formatLocationsShort(value);
        newFooter();
    }

    private void bindToReview(ReviewReference review) {
        mReview = review;

        onSubjectChanged(mReview.getSubject());
        onRatingChanged(mReview.getRating());
        mReview.registerObserver(this);

        mNumReturned = 0;
        mCancelCover = false;
        mReview.getComments().bindToValue(mCommentsBinder);
        mReview.getLocations().bindToValue(mLocationsBinder);
        mReview.getTags().bindToValue(mTagsBinder);
        mAuthorsRepo.getReference(mReview.getAuthorId()).bindToValue(mNameBinder);
    }

    @Override
    public void onSubjectChanged(DataSubject newSubject) {
        mSubject.setText(newSubject.getSubject());
    }

    @Override
    public void onRatingChanged(DataRating newRating) {
        mRating.setText(RatingFormatter.upToTwoSignificantDigits(newRating.getRating()));
    }

    private void returned() {
        if (!mCancelCover && ++mNumReturned == 4 && mReview != null) {
            mReview.getCover().bindToValue(mCoverBinder);
        }
    }

    private void bindToProfileImage() {
        if(mProfileImageBinder == null) mProfileImageBinder = new ProfileImageBinder();
        if(!mCancelCover && mReview != null) {
            mProfileImage = mAuthorsRepo.getProfile(mAuthor.getAuthorId()).getProfileImage();
            mProfileImage.bindToValue(mProfileImageBinder);
        }
    }

    private void unbindFromProfileimage() {
        if(mProfileImage != null) mProfileImage.unbindFromValue(mProfileImageBinder);
    }

    //hacky way of avoiding downloads spooling up if gridview is being scrolled fast.
    private class SelectAndBindTask extends AsyncTask<GvNode, Void, GvNode> {
        @Override
        protected GvNode doInBackground(GvNode... gvNodes) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return gvNodes[0];
        }

        @Override
        protected void onPostExecute(GvNode gvNode) {
            selectAndBind(gvNode);
        }
    }

    private class ProfileImageBinder implements ReferenceBinder<ProfileImage> {
        @Override
        public void onInvalidated(DataReference<ProfileImage> reference) {
            if(!mCoverBinder.hasImage()) setCover(null);
        }

        @Override
        public void onReferenceValue(ProfileImage value) {
            if(!mCoverBinder.hasImage()) setCover(value.getBitmap());
        }
    }

    private class CoverBinder implements ReferenceBinder<DataImage> {
        private boolean mHasImage = false;

        private boolean hasImage() {
            return mHasImage;
        }

        @Override
        public void onInvalidated(DataReference<DataImage> reference) {
            bindToProfile();
        }

        @Override
        public void onReferenceValue(DataImage value) {
            Bitmap bitmap = value.getBitmap();
            if(bitmap == null) {
                bindToProfile();
            } else {
                unbindFromProfile();
                setCover(bitmap);
            }
        }

        private void unbindFromProfile() {
            mHasImage = true;
            unbindFromProfileimage();
        }

        private void bindToProfile() {
            mHasImage = false;
            setCover(null);
            bindToProfileImage();
        }
    }

    private class NameBinder implements ReferenceBinder<NamedAuthor> {
        @Override
        public void onInvalidated(DataReference<NamedAuthor> reference) {
            returned();
            setAuthor(null);
        }

        @Override
        public void onReferenceValue(NamedAuthor value) {
            returned();
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
            returned();
            onList(value);
        }

        @Override
        public void onInvalidated(DataReference<IdableList<T>> reference) {
            returned();
            onList(new IdableDataList<T>(mNodeId));
        }
    }
}