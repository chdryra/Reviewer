/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;


import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterGv {
    private TagsManager mTagsManager;

    public ConverterGv(TagsManager tagsManager) {
        mTagsManager = tagsManager;
    }

    public GvConverterSizes newConverterSizes(GvDataType<?> type) {
        return new GvConverterSizes(type);
    }

    public GvConverterImages newConverterImages() {
        return new GvConverterImages(newConverterDates());
    }

    public GvConverterReviews newConverterReviews() {
        return new GvConverterReviews(mTagsManager, newConverterImages(), newConverterComments(), newConverterLocations());
    }

    public GvConverterReviewNode newConverterNodes(AuthorsRepository repository) {
        return new GvConverterReviewNode(repository, newConverterComments(), newConverterLocations());
    }

    public GvConverterComments newConverterComments() {
        return new GvConverterComments();
    }

    public GvConverterFacts newConverterFacts() {
        return new GvConverterFacts(newConverterUrls());
    }

    public GvConverterLocations newConverterLocations() {
        return new GvConverterLocations();
    }

    public GvConverterUrls newConverterUrls() {
        return new GvConverterUrls();
    }

    public GvConverterCriteria newConverterCriteria() {
        return new GvConverterCriteria();
    }

    public GvConverterCriteria.SubjectOnly newConverterCriteriaSubjects() {
        return new GvConverterCriteria.SubjectOnly();
    }

    public GvConverterSubjects newConverterSubjects() {
        return new GvConverterSubjects();
    }

    public GvConverterAuthors newConverterAuthors() {
        return  new GvConverterAuthors();
    }

    public GvConverterAuthorIds newConverterAuthorsIds() {
        return new GvConverterAuthorIds();
    }

    public GvConverterDateReviews newConverterDateReviews() {
        return new GvConverterDateReviews();
    }

    private GvConverterDates newConverterDates() {
        return new GvConverterDates();
    }

    public GvConverterItemTags newConverterItemTags() {
        return new GvConverterItemTags();
    }

    public GvConverterDataTags newConverterTags() {
        return new GvConverterDataTags();
    }
}
