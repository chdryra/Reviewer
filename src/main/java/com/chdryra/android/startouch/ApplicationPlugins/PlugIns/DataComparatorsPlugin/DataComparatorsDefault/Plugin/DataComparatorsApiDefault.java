/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin
        .DataComparatorsDefault.Plugin;

import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.Comparators.DataGetter;
import com.chdryra.android.corelibrary.Comparators.NamedComparator;
import com.chdryra.android.corelibrary.TextUtils.TextUtils;
import com.chdryra.android.corelibrary.Comparators.ComparatorCollection;
import com.chdryra.android.corelibrary.Comparators.ComparatorCollectionImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api.DataComparatorsApi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.DataGetters;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.FactoryComparators;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSocialPlatform;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataUrl;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorName;

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
    public ComparatorCollection<AuthorName> newAuthorComparators() {
        NamedComparator<AuthorName> aToZ
                = aToZ(upper(AuthorName.TYPE_NAME), new DataGetters.NamedAuthorName());
        ComparatorCollection<AuthorName> comparators = new ComparatorCollectionImpl<>(aToZ);
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
                = newBuilder(isHeadline.getId(), isHeadline)
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

        ComparatorCollection<DataCriterion> comparators = new ComparatorCollectionImpl<>(desc);
        comparators.add(desc.reverse(false));
        NamedComparator<DataCriterion> a2z = aToZ(subject, new DataGetters.CriterionSubject());
        comparators.add(a2z);
        comparators.add(a2z.reverse());

        return comparators;
    }

    @Override
    public ComparatorCollection<DateTime> newDateTimeComparators() {
        NamedComparator<DateTime> oldest = oldest();
        ComparatorCollection<DateTime> comparators
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
                = newBuilder(isCover.getId(), isCover)
                .withReverseName("Covers last")
                .addTieBreaker(newest("Cover dates", new DataGetters.ImageDate()));

        return new ComparatorCollectionImpl<>(builder.build());
    }

    @Override
    public ComparatorCollection<DataLocation> newLocationComparators() {
        NamedComparator<DataLocation> locations 
                = aToZ(upper(DataLocation.TYPE_NAME), new DataGetters.LocationName());
        
        ComparatorCollection<DataLocation> comparator
                = new ComparatorCollectionImpl<>(locations);
        comparator.add(locations.reverse());
        
        return comparator;
    }

    @Override
    public ComparatorCollection<DataReview> newReviewComparators() {
        NamedComparator<DateTime> mostRecentNamed = oldest().reverse();
        Comparator<DataReview> mostRecent
                = newDataComparator(mostRecentNamed, new DataGetters.ReviewDate());

        NamedComparator<DataRating> highestRatedNamed = ascRating().reverse();
        Comparator<DataReview> highestRated
                = newDataComparator(highestRatedNamed, new DataGetters.ReviewRating());

        NamedComparator<DataSubject> subjectAtoZNamed = aToZSubject();
        Comparator<DataReview> subjectAtoZ
                = newDataComparator(subjectAtoZNamed, new DataGetters.ReviewSubject());

        NamedComparator.Builder<DataReview> builder;

        //Most recent first comes first, then oldest
        builder = newBuilder(mostRecentNamed.getId(), mostRecent)
                .withReverseName(mostRecentNamed.getReverseName())
                .addTieBreaker(highestRated)
                .addTieBreaker(subjectAtoZ);

        NamedComparator<DataReview> recentFirst = builder.build();
        ComparatorCollection<DataReview> comparators
                = new ComparatorCollectionImpl<>(recentFirst);
        comparators.add(recentFirst.reverse(false));

        //highest rating comes next, then lowest
        builder = newBuilder(highestRatedNamed.getId(), highestRated)
                .withReverseName(highestRatedNamed.getReverseName())
                .addTieBreaker(mostRecent)
                .addTieBreaker(subjectAtoZ);

        NamedComparator<DataReview> highest = builder.build();
        comparators.add(highest);
        comparators.add(highest.reverse());

        //Then subject AtoZ, followed by ZtoA
        builder = newBuilder(subjectAtoZNamed.getId(), subjectAtoZ)
                .withReverseName(subjectAtoZNamed.getReverseName())
                .addTieBreaker(mostRecent)
                .addTieBreaker(highestRated);

        NamedComparator<DataReview> alphabetical = builder.build();
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
        ComparatorCollection<DataSubject> comparators
                = new ComparatorCollectionImpl<>(aToZSubject());
        comparators.add(aToZSubject().reverse());
        return comparators;
    }

    @Override
    public ComparatorCollection<DataTag> newTagComparators() {
        NamedComparator<DataTag> aToZ 
                = aToZ(upper(DataTag.TYPE_NAME), new DataGetters.TagString());
        ComparatorCollection<DataTag> comparators
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
        //return aToZ(upper(DataSubject.TYPE_NAME), new DataGetters.SubjectString());
        return aToZ("", new DataGetters.SubjectString());
    }

    private NamedComparator<DataRating> ascRating() {
        //return ascRating(upper(DataRating.TYPE_NAME), new DataGetters.RatingFloat());
        return ascRating("", new DataGetters.RatingFloat());
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
        NamedComparator.Builder<T> builder = newBuilder(labelAtoZ.getId(), labelAtoZ);
        builder.withReverseName(labelAtoZ.getReverseName())
                .addTieBreaker(mFactory.newAtoZ(value, new DataGetters.FactValue<T>()));

        NamedComparator<T> labelAz = builder.build();

        ComparatorCollection<T> comparators = new ComparatorCollectionImpl<>(labelAz);
        comparators.add(labelAz.reverse(false));

        return comparators;
    }

    @NonNull
    private <T> NamedComparator.Builder<T> newBuilder(String name, Comparator<T> comparator) {
        return mFactory.newBuilder(name, comparator);
    }
}
