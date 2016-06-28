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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api
        .DataAggregatorsApi;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Model.Factories.FactoryNodeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.TreeTraverser;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorDataGetter;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregatorOld {
    private final DataAggregatorsApi mAggregators;
    private final DataAggregatorParams mParams;
    private final ConverterGv mConverter;
    private FactoryVisitorReviewNode mVisitorFactory;
    private FactoryNodeTraverser mTraverserFactory;

    public enum CriterionAggregation {SUBJECT, SUBJECT_RATING}

    public GvDataAggregatorOld(DataAggregatorsApi aggregators,
                               DataAggregatorParams params,
                               ConverterGv converter,
                               FactoryVisitorReviewNode visitorFactory,
                               FactoryNodeTraverser traverserFactory) {
        mAggregators = aggregators;
        mParams = params;
        mConverter = converter;
        mVisitorFactory = visitorFactory;
        mTraverserFactory = traverserFactory;
    }

    public GvCanonicalCollection<GvAuthor> aggregateAuthors(ReviewNode root) {
        GvAuthorList data = mConverter.toGvAuthorList(root.getChildren(), root.getReviewId());
        DataAggregator<DataAuthorReview> aggregator = mAggregators.newAuthorsAggregator(mParams.getSimilarBoolean());
        AggregatedList<DataAuthorReview> aggregated = aggregator.aggregate(data);
        return newCollection(mConverter.getConverterAuthors(), aggregated, GvAuthor.TYPE);
    }

    public GvCanonicalCollection<GvSubject> aggregateSubjects(ReviewNode root) {
        GvSubjectList data = mConverter.toGvSubjectList(root.getChildren(), root.getReviewId());
        DataAggregator<DataSubject> aggregator = mAggregators.newSubjectsAggregator(mParams.getSimilarPercentage());
        AggregatedList<DataSubject> aggregated = aggregator.aggregate(data);
        return newCollection(mConverter.getConverterSubjects(), aggregated, GvSubject.TYPE);
    }

    public GvCanonicalCollection<GvTag> aggregateTags(ReviewNode root, TagsManager tagsManager) {
        GvTagList data = collectTags(root, tagsManager);
        DataAggregator<DataTag> aggregator = mAggregators.newTagsAggregator(mParams.getSimilarPercentage());
        AggregatedList<DataTag> aggregated = aggregator.aggregate(data);
        return newCollection(mConverter.getConverterTags(), aggregated, GvTag.TYPE);
    }

    public GvCanonicalCollection<GvDate> aggregateDates(ReviewNode root) {
        GvDateList data = mConverter.toGvDateList(root.getChildren(), root.getReviewId());
        DataAggregator<DataDateReview> aggregator = mAggregators.newDatesAggregator(mParams.getSimilarDate());
        AggregatedList<DataDateReview> aggregated = aggregator.aggregate(data);
        return newCollection(mConverter.getConverterDates(), aggregated, GvDate.TYPE);
    }

    public GvCanonicalCollection<GvCriterion> aggregateCriteria(ReviewNode root) {
        return aggregateCriteria(mConverter.toGvCriterionList(root.getData()), CriterionAggregation.SUBJECT);
    }

    public GvCanonicalCollection<GvCriterion> aggregateCriteria(GvDataList<GvCriterion> data, CriterionAggregation aggregation) {
        DataAggregator<DataCriterion> aggregator;
        if(aggregation == CriterionAggregation.SUBJECT) {
            aggregator = mAggregators.newCriteriaAggregatorSameSubject(mParams.getSimilarPercentage());
        } else {
            aggregator = mAggregators.newCriteriaAggregatorSameSubjectRating(mParams.getSimilarBoolean());
        }
        AggregatedList<DataCriterion> aggregated = aggregator.aggregate(data);
        return newCollection(mConverter.getConverterCriteria(), aggregated, GvCriterion.TYPE);
    }

    public GvCanonicalCollection<GvImage> aggregateImages(ReviewNode root) {
        DataAggregator<DataImage> aggregator = mAggregators.newImagesAggregator(mParams.getSimilarBoolean());
        AggregatedList<DataImage> aggregated = aggregator.aggregate(root.getData());
        return newCollection(mConverter.getConverterImages(), aggregated, GvImage.TYPE);
    }

    public GvCanonicalCollection<GvComment> aggregateComments(ReviewNode root) {
        return aggregateComments(root.getData());
    }

    public GvCanonicalCollection<GvComment> aggregateComments(IdableList<? extends DataComment> data) {
        DataAggregator<DataComment> aggregator = mAggregators.newCommentsAggregator(mParams.getSimilarPercentage());
        AggregatedList<DataComment> aggregated = aggregator.aggregate(data);
        return newCollection(mConverter.getConverterComments(), aggregated, GvComment.TYPE);
    }

    public GvCanonicalCollection<GvLocation> aggregateLocations(ReviewNode root) {
        DataAggregator<DataLocation> aggregator = mAggregators.newLocationsAggregator(mParams.getSimilarLocation());
        AggregatedList<DataLocation> aggregated = aggregator.aggregate(root.getData());
        return newCollection(mConverter.getConverterLocations(), aggregated, GvLocation.TYPE);
    }

    public GvCanonicalCollection<GvFact> aggregateFacts(ReviewNode root) {
        DataAggregator<DataFact> aggregator = mAggregators.newFactsAggregator(mParams.getSimilarPercentage());
        AggregatedList<DataFact> aggregated = aggregator.aggregate(root.getData());
        return newCollection(mConverter.getConverterFacts(), aggregated, GvFact.TYPE);
    }
    
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

    private GvTagList collectTags(ReviewNode root, TagsManager tagsManager) {
        VisitorDataGetter<DataTag> visitor = mVisitorFactory.newTagsCollector(tagsManager);
        TreeTraverser traverser = mTraverserFactory.newTreeTraverser(root);
        traverser.addVisitor(visitor);
        traverser.traverse();
        return mConverter.toGvTagList(visitor.getData(), root.getReviewId());
    }
}


