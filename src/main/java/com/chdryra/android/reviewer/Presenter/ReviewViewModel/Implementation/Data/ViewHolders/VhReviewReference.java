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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocationList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReference;
import com.chdryra.android.reviewer.R;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 07/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class VhReviewReference extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.grid_cell_review_overview;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating;
    private static final int IMAGE = R.id.review_image;
    private static final int HEADLINE = R.id.review_headline;
    private static final int TAGS = R.id.review_tags;
    private static final int PUBLISH = R.id.review_publish_data;

    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;

    private TextView mSubject;
    private RatingBar mRating;
    private ImageView mImage;
    private TextView mHeadline;
    private TextView mTags;
    private TextView mPublishDate;

    private ReviewReference mReference;
    private CoverBinder mCoverBinder;
    private TagsBinder mTagsBinder;
    private CommentsBinder mCommentsBinder;
    private LocationsBinder mLocationsBinder;

    public VhReviewReference(GvConverterComments converterComments,
                             GvConverterLocations converterLocations) {
        super(LAYOUT, new int[]{LAYOUT, SUBJECT, RATING, IMAGE, HEADLINE, TAGS, PUBLISH});

        mConverterComments = converterComments;
        mConverterLocations = converterLocations;

        mCoverBinder = new CoverBinder();
        mCommentsBinder = new CommentsBinder();
        mTagsBinder = new TagsBinder();
        mLocationsBinder = new LocationsBinder();
    }

    public boolean isBoundTo(ReviewReference reference) {
        return mReference != null && mReference.getReviewId().equals(reference.getReviewId());
    }

    public void unbindFromReference() {
        if (mReference == null) return;
        mReference.unbind(mCoverBinder);
        mReference.unbind(mCommentsBinder);
        mReference.unbind(mLocationsBinder);
        mReference.unbind(mTagsBinder);
    }

    @Override
    public void updateView(ViewHolderData data) {
        setViewsIfNecessary();
        setReference(((GvReference) data));
    }

    private void setViewsIfNecessary() {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (RatingBar) getView(RATING);
        if (mImage == null) mImage = (ImageView) getView(IMAGE);
        if (mHeadline == null) mHeadline = (TextView) getView(HEADLINE);
        if (mTags == null) mTags = (TextView) getView(TAGS);
        if (mPublishDate == null) mPublishDate = (TextView) getView(PUBLISH);
    }

    private void setReference(GvReference gvReference) {
        ReviewReference reference = gvReference.getReference();
        if (isBoundTo(reference)) return;

        unbindFromReference();

        mReference = reference;

        initialiseData();
        bindToReference();
        gvReference.setViewHolder(this);
    }

    private void initialiseData() {
        mSubject.setText(mReference.getSubject().getSubject());
        mRating.setRating(mReference.getRating().getRating());
        mImage.setImageBitmap(null);
        mHeadline.setText(null);
        mTags.setText(null);
        newFooter(null);
    }

    private void newFooter(@Nullable String location) {
        ReviewStamp stamp = ReviewStamp.newStamp(mReference.getAuthor(), mReference
                .getPublishDate());
        String text = stamp.toReadable() + (validateString(location) ? " @" + location : "");
        mPublishDate.setText(text);
    }

    private void bindToReference() {
        if (mReference == null) return;
        //dereference();
        mReference.bind(mCoverBinder);
        mReference.bind(mCommentsBinder);
        mReference.bind(mLocationsBinder);
        mReference.bind(mTagsBinder);
    }

//    private void dereference() {
//        mReference.dereference(new ReviewReference.DereferenceCallback() {
//            @Override
//            public void onDereferenced(@Nullable Review review, CallbackMessage message) {
//                if (!message.isError() && review != null) {
//                    mImage.setImageBitmap(review.getCover().getBitmap());
//                    setLocationString(review.getLocations());
//                    newFooter();
//                    setHeadline(review.getComments());
//                }
//            }
//        });
//    }

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

    private void setHeadline(IdableList<? extends DataComment> value) {
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
        newFooter(getLocationString(value));
    }


    private class CoverBinder implements ReferenceBinders.CoverBinder {
        @Override
        public void onValue(DataImage value) {
            setCover(value);
        }
    }

    private class TagsBinder implements ReferenceBinders.TagsBinder {
        @Override
        public void onValue(IdableList<? extends DataTag> value) {
            setTags(value);
        }
    }

    private class LocationsBinder implements ReferenceBinders.LocationsBinder {
        @Override
        public void onValue(IdableList<? extends DataLocation> value) {
            setLocation(value);
        }
    }

    private class CommentsBinder implements ReferenceBinders.CommentsBinder {
        @Override
        public void onValue(IdableList<? extends DataComment> value) {
            setHeadline(value);
        }
    }
}