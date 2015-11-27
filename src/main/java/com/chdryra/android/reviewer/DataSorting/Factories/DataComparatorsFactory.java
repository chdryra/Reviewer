package com.chdryra.android.reviewer.DataSorting.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataDate;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataFact;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataImage;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewSummary;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataTag;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataSorting.Implementation.AuthorAlphabetical;
import com.chdryra.android.reviewer.DataSorting.Implementation.CommentHeadlineThenAlphabetical;
import com.chdryra.android.reviewer.DataSorting.Interfaces.ComparatorCollection;
import com.chdryra.android.reviewer.DataSorting.Implementation.ComparatorCollectionImpl;
import com.chdryra.android.reviewer.DataSorting.Implementation.CriterionSubjectThenRating;
import com.chdryra.android.reviewer.DataSorting.Implementation.DateMostRecentFirst;
import com.chdryra.android.reviewer.DataSorting.Implementation.FactLabelThenValue;
import com.chdryra.android.reviewer.DataSorting.Implementation.ImageCoversThenMostRecent;
import com.chdryra.android.reviewer.DataSorting.Implementation.LocationNameAlphabetical;
import com.chdryra.android.reviewer.DataSorting.Implementation.ReviewMostRecentPublished;
import com.chdryra.android.reviewer.DataSorting.Implementation.SocialMostFollowersThenAlphabetical;
import com.chdryra.android.reviewer.DataSorting.Implementation.SubjectAlphabetical;
import com.chdryra.android.reviewer.DataSorting.Implementation.TagAlphabetical;
import com.chdryra.android.reviewer.DataSorting.Implementation.UrlLabelComparator;

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
