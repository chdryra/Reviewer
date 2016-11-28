/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin
        .DataComparatorsDefault.Plugin;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.ComparatorCollectionImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.DataGetter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.DataGetters;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.FactoryComparitors;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataReviewInfo;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataComparatorsApiDefault implements DataComparatorsApi {
    private final FactoryComparitors mFactory;

    public DataComparatorsApiDefault(FactoryComparitors factory) {
        mFactory = factory;
    }

    @Override
    public ComparatorCollection<NamedAuthor> getAuthorComparators() {
        Comparator<NamedAuthor> aToZ = aToZ(new DataGetters.NamedAuthorName());
        ComparatorCollectionImpl<NamedAuthor> comparators = new ComparatorCollectionImpl<>(aToZ);
        comparators.add(zToA(new DataGetters.NamedAuthorName()));
        return comparators;
    }

    @Override
    public ComparatorCollection<DataComment> getCommentComparators() {
        Comparator<DataComment> isHeadline = trueFalse(new DataGetters.CommentIsHeadline());
        Comparator<DataComment> atoZ = aToZ(new DataGetters.CommentString());
        Comparator<DataComment> headlineThenAtoZ = tieBreaker(isHeadline, atoZ);
        return new ComparatorCollectionImpl<>(headlineThenAtoZ);
    }

    @Override
    public ComparatorCollection<DataCriterion> getCriterionComparators() {
        Comparator<DataCriterion> desc = descending(new DataGetters.CriterionRating());
        Comparator<DataCriterion> ratingDesc = tieBreaker(desc, aToZ(new DataGetters
                .CriterionSubject()));
        ComparatorCollectionImpl<DataCriterion> comparators = new ComparatorCollectionImpl<>
                (ratingDesc);

        Comparator<DataCriterion> asc = ascending(new DataGetters.CriterionRating());
        comparators.add(tieBreaker(asc, aToZ(new DataGetters.CriterionSubject())));
        comparators.add(aToZ(new DataGetters.CriterionSubject()));
        comparators.add(zToA(new DataGetters.CriterionSubject()));

        return comparators;
    }

    @Override
    public ComparatorCollection<DateTime> getDateTimeComparators() {
        ComparatorCollectionImpl<DateTime> comparators
                = new ComparatorCollectionImpl<>(mostRecent());
        comparators.add(oldest());
        return comparators;
    }

    @Override
    public ComparatorCollection<DataFact> getFactComparators() {
        return getDefaultFactComparators(DataFact.class);
    }

    @Override
    public ComparatorCollection<DataImage> getImageComparators() {
        Comparator<DataImage> isCover = trueFalse(new DataGetters.ImageIsCover());
        Comparator<DataImage> descDate = mostRecent(new DataGetters.ImageDate());
        return new ComparatorCollectionImpl<>(tieBreaker(isCover, descDate));
    }

    @Override
    public ComparatorCollection<DataLocation> getLocationComparators() {
        ComparatorCollectionImpl<DataLocation> comparator
                = new ComparatorCollectionImpl<>(aToZ(new DataGetters.LocationName()));
        comparator.add(zToA(new DataGetters.LocationName()));
        return comparator;
    }

    @Override
    public ComparatorCollection<DataReviewInfo> getReviewInfoComparators() {
        Comparator<DataReviewInfo> subjectAtoZ
                = newDataComparator(aToZSubject(), new DataGetters.ReviewSubject());
        Comparator<DataReviewInfo> subjectZtoA
                = newDataComparator(zToASubject(), new DataGetters.ReviewSubject());
        Comparator<DataReviewInfo> descRating
                = newDataComparator(descRating(), new DataGetters.ReviewRating());
        Comparator<DataReviewInfo> ascRating
                = newDataComparator(ascRating(), new DataGetters.ReviewRating());
        Comparator<DataReviewInfo> recent
                = newDataComparator(mostRecent(), new DataGetters.ReviewDate());
        Comparator<DataReviewInfo> oldest
                = newDataComparator(oldest(), new DataGetters.ReviewDate());

        ComparatorCollectionImpl<DataReviewInfo> comparators = new
                ComparatorCollectionImpl<>(recent);

        comparators.add(oldest);
        comparators.add(descRating);
        comparators.add(ascRating);
        comparators.add(subjectAtoZ);
        comparators.add(subjectZtoA);

        return comparators;
    }

    @Override
    public ComparatorCollection<DataSocialPlatform> getSocialPlatformComparators() {
        return new ComparatorCollectionImpl<>(aToZ(new DataGetters.PlatformName()));
    }

    @Override
    public ComparatorCollection<DataSubject> getSubjectComparators() {
        ComparatorCollectionImpl<DataSubject> comparators
                = new ComparatorCollectionImpl<>(aToZSubject());
        comparators.add(zToASubject());
        return comparators;
    }

    @Override
    public ComparatorCollection<DataTag> getTagComparators() {
        ComparatorCollectionImpl<DataTag> comparators
                = new ComparatorCollectionImpl<>(aToZ(new DataGetters.TagString()));
        comparators.add(zToA(new DataGetters.TagString()));
        return comparators;
    }

    @Override
    public ComparatorCollection<DataUrl> getUrlComparators() {
        return getDefaultFactComparators(DataUrl.class);
    }

    private <ObjectType, DataType> Comparator<ObjectType>
    newDataComparator(Comparator<DataType> comparator, DataGetter<ObjectType, ? extends DataType> getter) {
        return mFactory.newDataComparator(comparator, getter);
    }

    private Comparator<DataSubject> zToASubject() {
        return zToA(new DataGetters.SubjectString());
    }

    private Comparator<DataSubject> aToZSubject() {
        return aToZ(new DataGetters.SubjectString());
    }

    private Comparator<DataRating> descRating() {
        return descending(new DataGetters.RatingFloat());
    }

    private Comparator<DataRating> ascRating() {
        return ascending(new DataGetters.RatingFloat());
    }

    private <T> Comparator<T> mostRecent(DataGetter<T, DateTime> getter) {
        return mFactory.newMostRecentFirst(getter);
    }

    @NonNull
    private Comparator<DateTime> oldest() {
        return mFactory.newOldestFirst();
    }

    private Comparator<DateTime> mostRecent() {
        return mFactory.newMostRecentFirst();
    }

    private <T> Comparator<T> descending(DataGetter<T, Float> getter) {
        return mFactory.newDescendingFloat(getter);
    }

    private <T> Comparator<T> ascending(DataGetter<T, Float> getter) {
        return mFactory.newAscendingFloat(getter);
    }

    @NonNull
    private <T> Comparator<T> tieBreaker(Comparator<T> primary,
                                         Comparator<T> tieBreaker) {
        return mFactory.newTieBreaker(primary, tieBreaker);
    }

    private <T> Comparator<T> trueFalse(DataGetter<T, Boolean> getter) {
        return mFactory.newTrueThenFalse(getter);
    }

    private <T> Comparator<T> zToA(DataGetter<T, String> getter) {
        return mFactory.newZtoA(getter);
    }

    private <T> Comparator<T> aToZ(DataGetter<T, String> getter) {
        return mFactory.newAtoZ(getter);
    }

    @NonNull
    private <T extends DataFact> ComparatorCollection<T> getDefaultFactComparators(Class<T> clazz) {
        Comparator<T> labelAtoZ = mFactory.newAtoZ(new DataGetters.FactLabel<T>());
        Comparator<T> labelZtoA = mFactory.newZtoA(new DataGetters.FactLabel<T>());
        Comparator<T> labelAz = mFactory.newTieBreaker(labelAtoZ, mFactory.newAtoZ(new
                DataGetters.FactValue<T>()));
        ComparatorCollectionImpl<T> comparators = new ComparatorCollectionImpl<T>(labelAz);
        comparators.add(mFactory.newTieBreaker(labelZtoA, mFactory.newAtoZ(new DataGetters
                .FactValue<T>())));
        return comparators;
    }
}
