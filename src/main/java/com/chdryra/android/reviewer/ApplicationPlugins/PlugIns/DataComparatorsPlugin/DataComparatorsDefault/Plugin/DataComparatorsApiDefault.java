/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Plugin;

import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewFundamentals;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewSummary;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault
        .Implementation.AuthorAlphabetical;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault
        .Implementation.CommentHeadlineThenAlphabetical;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault
        .Implementation.ComparatorCollectionImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.CriterionSubjectThenRating;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.DateMostRecentFirst;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.FactLabelThenValue;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.ImageCoversThenMostRecent;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.LocationNameAlphabetical;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.ReviewMostRecentPublished;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.SocialAlphabetical;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault
        .Implementation.SubjectAlphabetical;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault
        .Implementation.TagAlphabetical;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.UrlLabelComparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataComparatorsApiDefault implements DataComparatorsApi {

    @Override
    public ComparatorCollection<NamedAuthor> getAuthorComparators() {
        return new ComparatorCollectionImpl<>(new AuthorAlphabetical());
    }

    @Override
    public ComparatorCollection<DataComment> getCommentComparators() {
        return new ComparatorCollectionImpl<>(new CommentHeadlineThenAlphabetical());
    }

    @Override
    public ComparatorCollection<DataCriterion> getCriterionComparators() {
        return new ComparatorCollectionImpl<>(new CriterionSubjectThenRating());
    }

    @Override
    public ComparatorCollection<DateTime> getDateComparators() {
        return new ComparatorCollectionImpl<>(new DateMostRecentFirst());
    }

    @Override
    public ComparatorCollection<DataFact> getFactComparators() {
        return new ComparatorCollectionImpl<>(new FactLabelThenValue());
    }

    @Override
    public ComparatorCollection<DataImage> getImageComparators() {
        return new ComparatorCollectionImpl<>(new ImageCoversThenMostRecent(getDateComparators().getDefault()));
    }

    @Override
    public ComparatorCollection<DataLocation> getLocationComparators() {
        return new ComparatorCollectionImpl<>(new LocationNameAlphabetical());
    }

    @Override
    public ComparatorCollection<DataReviewSummary> getReviewComparators() {
        ReviewMostRecentPublished<DataReviewSummary> mostRecent =
                new ReviewMostRecentPublished<>(getDateComparators().getDefault());
        return new ComparatorCollectionImpl<>(mostRecent);
    }

    @Override
    public ComparatorCollection<ReviewFundamentals> getReviewInfoComparators() {
        ReviewMostRecentPublished<ReviewFundamentals> mostRecent =
                new ReviewMostRecentPublished<>(getDateComparators().getDefault());
        return new ComparatorCollectionImpl<>(mostRecent);
    }

    @Override
    public ComparatorCollection<DataSocialPlatform> getSocialPlatformComparators() {
        return new ComparatorCollectionImpl<>(new SocialAlphabetical());
    }

    @Override
    public ComparatorCollection<DataSubject> getSubjectComparators() {
        return new ComparatorCollectionImpl<>(new SubjectAlphabetical());
    }

    @Override
    public ComparatorCollection<DataTag> getTagComparators() {
        return new ComparatorCollectionImpl<>(new TagAlphabetical());
    }

    @Override
    public ComparatorCollection<DataUrl> getUrlComparators() {
        return new ComparatorCollectionImpl<>(new UrlLabelComparator());
    }
}
