/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories;

import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterAuthors;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterComments;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterCriteria;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterCriteriaSubjects;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterDataTags;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterDateReviews;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterDates;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterFacts;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterImages;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterItemTags;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterLocations;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterReviewsAsync;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterSubjects;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.GvConverterUrls;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvConverter {
    private ReviewsRepository mRepo;

    public FactoryGvConverter(ReviewsRepository repo) {
        mRepo = repo;
    }

    public ConverterGv newGvConverter() {
        GvConverterComments converterComments = new GvConverterComments();
        GvConverterUrls converterUrl = new GvConverterUrls();
        GvConverterFacts converterFacts = new GvConverterFacts(converterUrl);
        GvConverterImages converterImages = new GvConverterImages(new GvConverterDates());
        GvConverterLocations converterLocations = new GvConverterLocations();
        GvConverterCriteria converterCriteria = new GvConverterCriteria();
        GvConverterCriteriaSubjects converterCriteriaSubjects = new GvConverterCriteriaSubjects();
        GvConverterSubjects converterSubjects = new GvConverterSubjects();
        GvConverterAuthors converterAuthors = new GvConverterAuthors();
        GvConverterDateReviews converterDates = new GvConverterDateReviews();
        GvConverterReviewsAsync converterReview = new GvConverterReviewsAsync(mRepo, converterImages,
                converterComments, converterLocations, converterAuthors);
        GvConverterDataTags converterTags = new GvConverterDataTags();
        GvConverterItemTags converterItemTags = new GvConverterItemTags();

        return new ConverterGv(converterComments, converterFacts, converterImages,
                converterLocations, converterUrl, converterCriteria, converterCriteriaSubjects,
                converterReview, converterSubjects, converterAuthors, converterDates, converterTags,
                converterItemTags);
    }
}
