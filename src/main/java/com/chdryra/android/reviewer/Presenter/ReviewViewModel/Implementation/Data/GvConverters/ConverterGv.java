/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;


import android.content.res.Resources;

import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .VhNode;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders
        .ViewHolderFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConverterGv {
    private final Resources mResources;

    public ConverterGv(Resources resources) {
        mResources = resources;
    }

    public GvConverterSizes newConverterSizes(GvDataType<?> type) {
        return new GvConverterSizes(type);
    }

    public GvConverterImages newConverterImages() {
        return new GvConverterImages(newConverterDates());
    }

    public GvConverterReviewNode newConverterNodes(AuthorsRepository repository) {
        return newConverterNodes(new VhMostRecentFactory(repository, mResources));
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

    public GvConverterCriteria newConverterCriteria() {
        return new GvConverterCriteria();
    }

    public GvConverterCriteria.SubjectOnly newConverterCriteriaSubjects() {
        return new GvConverterCriteria.SubjectOnly();
    }

    public GvConverterSubjects newConverterSubjects() {
        return new GvConverterSubjects();
    }

    public GvConverterAuthorIds newConverterAuthorsIds(AuthorsRepository repo) {
        return new GvConverterAuthorIds(repo);
    }

    public GvConverterAuthors newConverterAuthors() {
        return new GvConverterAuthors();
    }

    public GvConverterDateReviews newConverterDateReviews() {
        return new GvConverterDateReviews();
    }

    public GvConverterDataTags newConverterTags() {
        return new GvConverterDataTags();
    }

    private GvConverterReviewNode newConverterNodes(ViewHolderFactory<VhNode> factory) {
        return new GvConverterReviewNode(factory);
    }

    private GvConverterUrls newConverterUrls() {
        return new GvConverterUrls();
    }

    private GvConverterDates newConverterDates() {
        return new GvConverterDates();
    }
}
