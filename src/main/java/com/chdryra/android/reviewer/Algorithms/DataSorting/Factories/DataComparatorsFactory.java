package com.chdryra.android.reviewer.Algorithms.DataSorting.Factories;

import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation.AuthorAlphabetical;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation
        .CommentHeadlineThenAlphabetical;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation
        .ComparatorCollectionImpl;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation
        .CriterionSubjectThenRating;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation.DateMostRecentFirst;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation.FactLabelThenValue;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation
        .ImageCoversThenMostRecent;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation
        .LocationNameAlphabetical;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation
        .ReviewMostRecentPublished;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation
        .SocialMostFollowersThenAlphabetical;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation.SubjectAlphabetical;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation.TagAlphabetical;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Implementation.UrlLabelComparator;
import com.chdryra.android.reviewer.Algorithms.DataSorting.Interfaces.ComparatorCollection;
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

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataComparatorsFactory {

    public ComparatorCollection<DataAuthor> getAuthorComparators() {
        return new ComparatorCollectionImpl<>(new AuthorAlphabetical());
    }

    public ComparatorCollection<DataComment> getCommentComparators() {
        return new ComparatorCollectionImpl<>(new CommentHeadlineThenAlphabetical());
    }

    public ComparatorCollection<DataCriterion> getCriterionComparators() {
        return new ComparatorCollectionImpl<>(new CriterionSubjectThenRating());
    }

    public ComparatorCollection<DataDate> getDateComparators() {
        return new ComparatorCollectionImpl<>(new DateMostRecentFirst());
    }

    public ComparatorCollection<DataFact> getFactCompartors() {
        return new ComparatorCollectionImpl<>(new FactLabelThenValue());
    }

    public ComparatorCollection<DataImage> getImageComparators() {
        return new ComparatorCollectionImpl<>(new ImageCoversThenMostRecent(getDateComparators().getDefault()));
    }

    public ComparatorCollection<DataLocation> getLocationComparators() {
        return new ComparatorCollectionImpl<>(new LocationNameAlphabetical());
    }

    public ComparatorCollection<DataReviewSummary> getReviewComparators() {
        return new ComparatorCollectionImpl<>(new ReviewMostRecentPublished(getDateComparators().getDefault()));
    }

    public ComparatorCollection<DataSocialPlatform> getSocialPlatformComparators() {
        return new ComparatorCollectionImpl<>(new SocialMostFollowersThenAlphabetical());
    }

    public ComparatorCollection<DataSubject> getSubjectComparators() {
        return new ComparatorCollectionImpl<>(new SubjectAlphabetical());
    }

    public ComparatorCollection<DataTag> getTagComparators() {
        return new ComparatorCollectionImpl<>(new TagAlphabetical());
    }

    public ComparatorCollection<DataUrl> getUrlComparators() {
        return new ComparatorCollectionImpl<>(new UrlLabelComparator());
    }
}
