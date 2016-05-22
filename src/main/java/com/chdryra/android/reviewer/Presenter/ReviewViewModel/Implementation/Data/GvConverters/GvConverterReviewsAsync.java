/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDate;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewAsync;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewAsyncList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvConverterReviewsAsync
        extends GvConverterBasic<Review, GvReviewAsync, GvReviewAsyncList>
        implements DataConverter<Review, GvReviewAsync, GvReviewAsyncList> {
    private GvConverterImages mConverterImages;
    private GvConverterComments mConverterComments;
    private GvConverterLocations mConverterLocations;
    private GvConverterAuthors mGvConverterAuthor;
    private ReviewsRepository mRepo;

    public GvConverterReviewsAsync(ReviewsRepository repo,
                                   GvConverterImages converterImages,
                                   GvConverterComments converterComments,
                                   GvConverterLocations converterLocations,
                                   GvConverterAuthors gvConverterAuthor) {
        super(GvReviewAsyncList.class);
        mRepo = repo;
        mConverterImages = converterImages;
        mConverterComments = converterComments;
        mConverterLocations = converterLocations;
        mGvConverterAuthor = gvConverterAuthor;
    }

    @Override
    public GvReviewAsync convert(Review review, ReviewId parentId) {
        GvReviewId id = newId(review.getReviewId());
        return new GvReviewAsync(id, review.getSubject().getSubject(),
                review.getRating().getRating(),
                new GvDate(id, review.getPublishDate().getTime()),
                mRepo, mConverterImages,
                mConverterComments, mConverterLocations, mGvConverterAuthor);
    }

    @Override
    public GvReviewAsync convert(Review review) {
        return convert(review, null);
    }

    @Override
    public GvReviewAsyncList convert(IdableList<? extends Review> data) {
        GvReviewAsyncList list = new GvReviewAsyncList(newId(data.getReviewId()));
        for(Review datum : data) {
            list.add(convert(datum, data.getReviewId()));
        }

        return list;
    }
}
