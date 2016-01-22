package com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewSummary;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.Api.FactoryDataComparators;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataComparatorsDefault implements FactoryDataComparators {

    @Override
    public ComparatorCollection<DataAuthor> getAuthorComparators() {
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
    public ComparatorCollection<DataDate> getDateComparators() {
        return new ComparatorCollectionImpl<>(new DateMostRecentFirst());
    }

    @Override
    public ComparatorCollection<DataFact> getFactCompartors() {
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
        return new ComparatorCollectionImpl<>(new ReviewMostRecentPublished(getDateComparators()
                .getDefault()));
    }

    @Override
    public ComparatorCollection<DataSocialPlatform> getSocialPlatformComparators() {
        return new ComparatorCollectionImpl<>(new SocialMostFollowersThenAlphabetical());
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
