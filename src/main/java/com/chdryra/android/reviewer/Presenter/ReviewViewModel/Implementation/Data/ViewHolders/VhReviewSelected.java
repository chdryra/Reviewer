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
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocationList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;
import com.chdryra.android.reviewer.R;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhReviewSelected extends ViewHolderBasic implements ReviewSelector.ReviewSelectorCallback, VhNode {
    private static final int LAYOUT = R.layout.grid_cell_review_overview;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating;
    private static final int IMAGE = R.id.review_image;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int PUBLISH = R.id.review_publish_data;

    private AuthorsRepository mAuthorsRepo;
    private ReviewSelector mSelector;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;

    private TextView mSubject;
    private RatingBar mRating;
    private ImageView mImage;
    private TextView mHeadline;
    private TextView mTags;
    private TextView mPublishDate;

    private ReviewId mNodeId;
    private ReviewReference mReview;
    private CoverBinder mCoverBinder;
    private TagsBinder mTagsBinder;
    private CommentsBinder mCommentsBinder;
    private LocationsBinder mLocationsBinder;
    private NameBinder mNameBinder;

    private NamedAuthor mAuthor;
    private String mLocation;

    public VhReviewSelected(AuthorsRepository authorsRepo,
                            ReviewSelector selector,
                            GvConverterComments converterComments,
                            GvConverterLocations converterLocations) {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, TAGS, PUBLISH});
        mAuthorsRepo = authorsRepo;
        mSelector = selector;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;

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
        if (mRating == null) mRating = (RatingBar) getView(RATING);
        if (mImage == null) mImage = (ImageView) getView(IMAGE);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mTags == null) mTags = (TextView) getView(TAGS);
        if (mPublishDate == null) mPublishDate = (TextView) getView(PUBLISH);
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
        mRating.setRating(node.getRating().getRating());
        mImage.setImageBitmap(null);
        mHeadline.setText(null);
        mTags.setText(null);
        mAuthor = null;
        mLocation = null;
        newFooter();
    }

    @Override
    public void onReviewSelected(@Nullable ReviewReference review) {
        if(review != null) {
            bindToReview(review);
        } else {
            unbindFromNode();
        }
    }

    protected void bindToReview(ReviewReference review) {
        mReview = review;
        mReview.getCover().bindToValue(mCoverBinder);
        mReview.getComments().bindToValue(mCommentsBinder);
        mReview.getLocations().bindToValue(mLocationsBinder);
        mReview.getTags().bindToValue(mTagsBinder);
        mAuthorsRepo.getName(mReview.getAuthorId()).bindToValue(mNameBinder);
    }

    private void newFooter() {
        ReviewStamp stamp = ReviewStamp.newStamp(mReview.getAuthorId(), mReview
                .getPublishDate());
        String date = stamp.toReadableDate();
        String name = mAuthor != null ? " by " + mAuthor.getName() : "";
        String text = date + name + (validateString(mLocation) ? " @" + mLocation : "");
        mPublishDate.setText(text);
    }

    private String getLocationString(IdableList<? extends DataLocation> value) {
        GvLocationList locations = mConverterLocations.convert(value);
        ArrayList<String> locationNames = new ArrayList<>();
        for (GvLocation location : locations) {
            locationNames.add(location.getShortenedName());
        }
        String location = "";
        int locs = locationNames.size();
        if (locs > 0) {
            location = locationNames.get(0);
            if (locs > 1) {
                String loc = locs == 2 ? " loc" : " locs";
                location += " +" + String.valueOf(locationNames.size() - 1) + loc;
            }
        }

        return location.trim();
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

    private void setCover(DataImage cover) {
        mImage.setImageBitmap(cover.getBitmap());
    }

    private void setAuthor(@Nullable NamedAuthor author) {
        mAuthor = author;
        newFooter();
    }

    private void setHeadline(IdableList<DataComment> value) {
        GvCommentList comments = mConverterComments.convert(value);
        GvCommentList headlines = comments.getHeadlines();
        String headline = headlines.size() > 0 ? headlines.getItem(0).getHeadline() : null;
        if (validateString(headline)) {
            //TODO sort this out with resource strings with placeholders
            mHeadline.setText("\"" + headline + "\"");
        } else {
            mHeadline.setText("");
        }
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
        mLocation = getLocationString(value);
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