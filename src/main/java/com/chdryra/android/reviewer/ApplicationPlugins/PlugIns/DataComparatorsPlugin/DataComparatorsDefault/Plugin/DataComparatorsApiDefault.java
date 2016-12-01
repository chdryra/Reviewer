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

import com.chdryra.android.mygenerallibrary.TextUtils.TextUtils;
import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.ComparatorCollectionImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.DataGetter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.DataGetters;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.FactoryComparators;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.NamedComparator;
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
    private final FactoryComparators mFactory;

    public DataComparatorsApiDefault(FactoryComparators factory) {
        mFactory = factory;
    }

    @Override
    public ComparatorCollection<NamedAuthor> newAuthorComparators() {
        NamedComparator<NamedAuthor> aToZ
                = aToZ(upper(NamedAuthor.TYPE_NAME), new DataGetters.NamedAuthorName());
        ComparatorCollectionImpl<NamedAuthor> comparators = new ComparatorCollectionImpl<>(aToZ);
        comparators.add(aToZ.reverse(false));

        return comparators;
    }

    @Override
    public ComparatorCollection<DataComment> newCommentComparators() {
        NamedComparator<DataComment> isHeadline 
                = trueThenFalse("Headlines first", new DataGetters.CommentIsHeadline());
        NamedComparator<DataComment> aToZ 
                = aToZ(upper(DataComment.TYPE_NAME), new DataGetters.CommentString());
        
        NamedComparator.Builder<DataComment> builder 
                = newBuilder(isHeadline.getName(), isHeadline)
                .withReverseName("Headlines last")
                .addTieBreaker(aToZ);
        
        return new ComparatorCollectionImpl<>(builder.build());
    }
    
    @Override
    public ComparatorCollection<DataCriterion> newCriterionComparators() {
        String rating = DataCriterion.RATING;
        String subject = DataCriterion.SUBJECT;

        NamedComparator<DataCriterion> descRating
                = ascRating(rating, new DataGetters.CriterionRating()).reverse();
        NamedComparator<DataCriterion> aToZ 
                = aToZ(subject, new DataGetters.CriterionSubject());
        
        NamedComparator.Builder<DataCriterion> builder 
                = newBuilder(rating, descRating)
                .withReverseName(descRating.getReverseName())
                .addTieBreaker(aToZ);
        
        NamedComparator<DataCriterion> desc = builder.build();

        ComparatorCollectionImpl<DataCriterion> comparators = new ComparatorCollectionImpl<>(desc);
        comparators.add(desc.reverse(false));
        NamedComparator<DataCriterion> a2z = aToZ(subject, new DataGetters.CriterionSubject());
        comparators.add(a2z);
        comparators.add(a2z.reverse());

        return comparators;
    }

    @Override
    public ComparatorCollection<DateTime> newDateTimeComparators() {
        NamedComparator<DateTime> oldest = oldest();
        ComparatorCollectionImpl<DateTime> comparators
                = new ComparatorCollectionImpl<>(oldest.reverse());
        comparators.add(oldest);
        
        return comparators;
    }

    @Override
    public ComparatorCollection<DataFact> newFactComparators() {
        return getDefaultFactComparators(DataFact.class);
    }

    @Override
    public ComparatorCollection<DataImage> newImageComparators() {
        NamedComparator<DataImage> isCover 
                = trueThenFalse("Covers first", new DataGetters.ImageIsCover());
        NamedComparator.Builder<DataImage> builder 
                = newBuilder(isCover.getName(), isCover)
                .withReverseName("Covers last")
                .addTieBreaker(newest("Cover dates", new DataGetters.ImageDate()));
        
        return new ComparatorCollectionImpl<>(builder.build());
    }

    @Override
    public ComparatorCollection<DataLocation> newLocationComparators() {
        NamedComparator<DataLocation> locations 
                = aToZ(upper(DataLocation.TYPE_NAME), new DataGetters.LocationName());
        
        ComparatorCollectionImpl<DataLocation> comparator
                = new ComparatorCollectionImpl<>(locations);
        comparator.add(locations.reverse());
        
        return comparator;
    }

    @Override
    public ComparatorCollection<DataReviewInfo> newReviewComparators() {
        NamedComparator<DateTime> mostRecentNamed = oldest().reverse();
        Comparator<DataReviewInfo> mostRecent
                = newDataComparator(mostRecentNamed, new DataGetters.ReviewDate());

        NamedComparator<DataRating> highestRatedNamed = ascRating().reverse();
        Comparator<DataReviewInfo> highestRated
                = newDataComparator(highestRatedNamed, new DataGetters.ReviewRating());

        NamedComparator<DataSubject> subjectAtoZNamed = aToZSubject();
        Comparator<DataReviewInfo> subjectAtoZ
                = newDataComparator(subjectAtoZNamed, new DataGetters.ReviewSubject());

        NamedComparator.Builder<DataReviewInfo> builder;

        //Most recent first comes first, then oldest
        builder = newBuilder(mostRecentNamed.getName(), mostRecent)
                .withReverseName(mostRecentNamed.getReverseName())
                .addTieBreaker(highestRated)
                .addTieBreaker(subjectAtoZ);

        NamedComparator<DataReviewInfo> recentFirst = builder.build();
        ComparatorCollectionImpl<DataReviewInfo> comparators
                = new ComparatorCollectionImpl<>(recentFirst);
        comparators.add(recentFirst.reverse(false));

        //highest rating comes next, then lowest
        builder = newBuilder(highestRatedNamed.getName(), highestRated)
                .withReverseName(highestRatedNamed.getReverseName())
                .addTieBreaker(mostRecent)
                .addTieBreaker(subjectAtoZ);

        NamedComparator<DataReviewInfo> highest = builder.build();
        comparators.add(highest);
        comparators.add(highest.reverse());

        //Then subject AtoZ, followed by ZtoA
        builder = newBuilder(subjectAtoZNamed.getName(), subjectAtoZ)
                .withReverseName(subjectAtoZNamed.getReverseName())
                .addTieBreaker(mostRecent)
                .addTieBreaker(highestRated);

        NamedComparator<DataReviewInfo> alphabetical = builder.build();
        comparators.add(alphabetical);
        comparators.add(alphabetical.reverse());

        return comparators;
    }

    @Override
    public ComparatorCollection<DataSocialPlatform> newSocialPlatformComparators() {
        NamedComparator<DataSocialPlatform> platformNames 
                = aToZ(upper(DataSocialPlatform.TYPE_NAME), new DataGetters.PlatformName());
        return new ComparatorCollectionImpl<>(platformNames);
    }

    @Override
    public ComparatorCollection<DataSubject> newSubjectComparators() {
        ComparatorCollectionImpl<DataSubject> comparators
                = new ComparatorCollectionImpl<>(aToZSubject());
        comparators.add(aToZSubject().reverse());
        return comparators;
    }

    @Override
    public ComparatorCollection<DataTag> newTagComparators() {
        NamedComparator<DataTag> aToZ 
                = aToZ(upper(DataTag.TYPE_NAME), new DataGetters.TagString());
        ComparatorCollectionImpl<DataTag> comparators
                = new ComparatorCollectionImpl<>(aToZ);
        comparators.add(aToZ.reverse());
        
        return comparators;
    }

    @Override
    public ComparatorCollection<DataUrl> newUrlComparators() {
        return getDefaultFactComparators(DataUrl.class);
    }

    private String upper(String name) {
        return TextUtils.capitalize(name);
    }

    private <ObjectType, DataType> Comparator<ObjectType>
    newDataComparator(Comparator<DataType> comparator, DataGetter<ObjectType, ? extends DataType>
            getter) {
        return mFactory.newDataComparator(comparator, getter);
    }

    private NamedComparator<DataSubject> aToZSubject() {
        return aToZ(upper(DataSubject.TYPE_NAME), new DataGetters.SubjectString());
    }

    private NamedComparator<DataRating> ascRating() {
        return ascRating(upper(DataRating.TYPE_NAME), new DataGetters.RatingFloat());
    }

    private <T> Comparator<T> newest(String name, DataGetter<T, DateTime> getter) {
        return mFactory.newDateAscending(name, getter).reverse(false);
    }

    @NonNull
    private NamedComparator<DateTime> oldest() {
        return mFactory.newDateAscending();
    }

    private <T> NamedComparator<T> ascRating(String name, DataGetter<T, Float> getter) {
        return mFactory.newAscendingFloat(name, getter);
    }

    private <T> NamedComparator<T> trueThenFalse(String name, DataGetter<T, Boolean> getter) {
        return mFactory.newTrueThenFalse(name, getter);
    }

    private <T> NamedComparator<T> aToZ(String name, DataGetter<T, String> getter) {
        return mFactory.newAtoZ(name, getter);
    }

    @NonNull
    private <T extends DataFact> ComparatorCollection<T> getDefaultFactComparators(Class<T> clazz) {
        String label = DataFact.LABEL;
        String value = DataFact.VALUE;

        NamedComparator<T> labelAtoZ = mFactory.newAtoZ(label, new DataGetters.FactLabel<T>());
        NamedComparator.Builder<T> builder = newBuilder(labelAtoZ.getName(), labelAtoZ);
        builder.withReverseName(labelAtoZ.getReverseName())
                .addTieBreaker(mFactory.newAtoZ(value, new DataGetters.FactValue<T>()));

        NamedComparator<T> labelAz = builder.build();

        ComparatorCollectionImpl<T> comparators = new ComparatorCollectionImpl<>(labelAz);
        comparators.add(labelAz.reverse(false));

        return comparators;
    }

    @NonNull
    private <T> NamedComparator.Builder<T> newBuilder(String name, Comparator<T> comparator) {
        return mFactory.newBuilder(name, comparator);
    }
}
