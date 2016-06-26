/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

import java.util.List;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 26/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewMetaSnapshot extends ReviewStatic {
    private static final Random RAND = new Random();
    private DataReviewInfo mMeta;
    private List<Review> mReviews;

    private interface Getter<T extends HasReviewId> {
        IdableList<? extends T> execute(Review review);
    }

    public ReviewMetaSnapshot(DataReviewInfo meta, List<Review> reviews) {
        mMeta = meta;
        mReviews = reviews;
    }

    @Override
    public DataSubject getSubject() {
        return mMeta.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mMeta.getRating();
    }

    @Override
    public DataAuthorReview getAuthor() {
        return mMeta.getAuthor();
    }

    @Override
    public DataDateReview getPublishDate() {
        return mMeta.getPublishDate();
    }

    @Override
    public DataImage getCover() {
        return mReviews.get(RAND.nextInt(mReviews.size())).getCover();
    }

    @Override
    public IdableList<? extends DataCriterion> getCriteria() {
        return getData(new Getter<DataCriterion>() {
            @Override
            public IdableList<? extends DataCriterion> execute(Review review) {
                return review.getCriteria();
            }
        });
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return getData(new Getter<DataComment>() {
            @Override
            public IdableList<? extends DataComment> execute(Review review) {
                return review.getComments();
            }
        });
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return getData(new Getter<DataFact>() {
            @Override
            public IdableList<? extends DataFact> execute(Review review) {
                return review.getFacts();
            }
        });
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return getData(new Getter<DataImage>() {
            @Override
            public IdableList<? extends DataImage> execute(Review review) {
                return review.getImages();
            }
        });
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return getData(new Getter<DataLocation>() {
            @Override
            public IdableList<? extends DataLocation> execute(Review review) {
                return review.getLocations();
            }
        });
    }

    @Override
    public ReviewId getReviewId() {
        return mMeta.getReviewId();
    }

    private <T extends HasReviewId> IdableList<T> getData(Getter<T> getter) {
        IdableList<T> data = new IdableDataList<>(getReviewId());
        for (Review review : mReviews) {
            data.addAll(getter.execute(review));
        }

        return data;
    }
}
