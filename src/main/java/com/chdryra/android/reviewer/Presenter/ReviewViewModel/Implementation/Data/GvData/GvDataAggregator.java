/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.AggregatedData;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsApi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregator {
    private final DataAggregatorsApi mAggregators;
    private final DataAggregatorParams mParams;
    private final ConverterGv mConverter;

    public enum CriterionAggregation {SUBJECT, SUBJECT_RATING}

    public GvDataAggregator(DataAggregatorsApi aggregators,
                            DataAggregatorParams params,
                            ConverterGv converter) {
        mAggregators = aggregators;
        mParams = params;
        mConverter = converter;
    }
//
//    public GvCanonicalCollection<GvAuthor> aggregateAuthors(ReviewNode root) {
//        GvAuthorList data = mConverter.toGvAuthorList(root.getChildren(), root.getReviewId());
//        DataAggregator<DataAuthor> aggregator = mAggregators.newAuthorsAggregator(mParams.getSimilarBoolean());
//        AggregatedList<DataAuthor> aggregated = aggregator.aggregate(data);
//        return newCollection(mConverter.getConverterAuthors(), aggregated, GvAuthor.TYPE);
//    }
//
//    public GvCanonicalCollection<GvSubject> aggregateSubjects(ReviewNode root) {
//        GvSubjectList data = mConverter.toGvSubjectList(root.getChildren(), root.getReviewId());
//        DataAggregator<DataSubject> aggregator = mAggregators.newSubjectsAggregator(mParams.getSimilarPercentage());
//        AggregatedList<DataSubject> aggregated = aggregator.aggregate(data);
//        return newCollection(mConverter.getConverterSubjects(), aggregated, GvSubject.TYPE);
//    }

//    public GvCanonicalCollection<GvTag> aggregateTags(ReviewNode root, TagsManager tagsManager) {
//        GvTagList data = collectTags(root, tagsManager);
//        DataAggregator<DataTag> aggregator = mAggregators.newTagsAggregator(mParams.getSimilarPercentage());
//        AggregatedList<DataTag> aggregated = aggregator.aggregate(data);
//        return newCollection(mConverter.getConverterTags(), aggregated, GvTag.TYPE);
//    }
//
//    public GvCanonicalCollection<GvDate> aggregateDates(ReviewNode root) {
//        GvDateList data = mConverter.toGvDateList(root.getChildren(), root.getReviewId());
//        DataAggregator<DataDate> aggregator = mAggregators.newDatesAggregator(mParams.getSimilarDate());
//        AggregatedList<DataDate> aggregated = aggregator.aggregate(data);
//        return newCollection(mConverter.getConverterDates(), aggregated, GvDate.TYPE);
//    }
//
//    public GvCanonicalCollection<GvCriterion> aggregateCriteria(ReviewNode root) {
//        return aggregateCriteria(mConverter.toGvCriterionList(root.getData()), CriterionAggregation.SUBJECT);
//    }

    public GvCanonicalCollection<GvCriterion> aggregateCriteria(GvDataList<GvCriterion> data, CriterionAggregation aggregation) {
        DataAggregator<DataCriterion> aggregator;
        if(aggregation == CriterionAggregation.SUBJECT) {
            aggregator = mAggregators.newCriteriaAggregatorSameSubject(mParams.getSimilarPercentage());
        } else {
            aggregator = mAggregators.newCriteriaAggregatorSameSubjectRating(mParams.getSimilarBoolean());
        }
        AggregatedList<DataCriterion> aggregated = aggregator.aggregate(data);
        return newCollection(mConverter.newConverterCriteria(), aggregated, GvCriterion.TYPE);
    }
//
//    public GvCanonicalCollection<GvImage> aggregateImages(ReviewNode root) {
//        DataAggregator<DataImage> aggregator = mAggregators.newImagesAggregator(mParams.getSimilarBoolean());
//        AggregatedList<DataImage> aggregated = aggregator.aggregate(root.getData());
//        return newCollection(mConverter.getConverterImages(), aggregated, GvImage.TYPE);
//    }
//
//    public GvCanonicalCollection<GvComment> aggregateComments(ReviewNode root) {
//        return aggregateComments(root.getData());
//    }

    public GvCanonicalCollection<GvComment> aggregateComments(IdableList<? extends DataComment> data) {
        DataAggregator<DataComment> aggregator = mAggregators.newCommentsAggregator(mParams.getSimilarPercentage());
        AggregatedList<DataComment> aggregated = aggregator.aggregate(data);
        return newCollection(mConverter.newConverterComments(), aggregated, GvComment.TYPE);
    }
//
//    public GvCanonicalCollection<GvLocation> aggregateLocations(ReviewNode root) {
//        DataAggregator<DataLocation> aggregator = mAggregators.newLocationsAggregator(mParams.getSimilarLocation());
//        AggregatedList<DataLocation> aggregated = aggregator.aggregate(root.getData());
//        return newCollection(mConverter.getConverterLocations(), aggregated, GvLocation.TYPE);
//    }
//
//    public GvCanonicalCollection<GvFact> aggregateFacts(ReviewNode root) {
//        DataAggregator<DataFact> aggregator = mAggregators.newFactsAggregator(mParams.getSimilarPercentage());
//        AggregatedList<DataFact> aggregated = aggregator.aggregate(root.getData());
//        return newCollection(mConverter.getConverterFacts(), aggregated, GvFact.TYPE);
//    }
    
    public <T1 extends HasReviewId, T2 extends GvData> GvCanonicalCollection<T2>
    newCollection(DataConverter<? super T1, T2, ? extends GvDataList<T2>> converter,
                  AggregatedList<T1> aggregated, GvDataType<T2> type) {
        GvReviewId id = new GvReviewId(aggregated.getReviewId());
        GvCanonicalCollection<T2> canonicals = new GvCanonicalCollection<>(id, type);
        for(AggregatedData<T1> aggregatedData : aggregated) {
            T2 canonical = converter.convert(aggregatedData.getCanonical());
            GvDataList<T2> similar = converter.convert(aggregatedData.getAggregatedItems());
            canonicals.addCanonnical(new GvCanonical<>(canonical, similar));
        }

        return canonicals;
    }
//
//    private GvTagList collectTags(ReviewNode root, TagsManager tagsManager) {
//        VisitorDataGetter<DataTag> visitor = mVisitorFactory.newItemCollector(tagsManager);
//        TreeTraverser traverser = mTraverserFactory.newTreeTraverser(root);
//        traverser.addVisitor(visitor);
//        traverser.traverse();
//        return mConverter.toGvTagList(visitor.getData(), root.getReviewId());
//    }
}


