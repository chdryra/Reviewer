package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.AggregatedData;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;
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
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.Api.FactoryDataAggregator;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregator {
    private final FactoryDataAggregator mAggregatorFactory;
    private final DataAggregatorParams mParams;
    private final ConverterGv mConverter;

    public enum CriterionAggregation { SUBJECT, SUBJECT_RATING}
    
    public GvDataAggregator(FactoryDataAggregator aggregatorFactory,
                            DataAggregatorParams params,
                            ConverterGv converter) {
        mAggregatorFactory = aggregatorFactory;
        mParams = params;
        mConverter = converter;
    }

    public GvCanonicalCollection<GvAuthor> aggregateAuthors(IdableList<? extends DataAuthorReview> data) {
        DataAggregator<DataAuthorReview> aggregator = mAggregatorFactory.newAuthorsAggregator(mParams.getSimilarBoolean());
        AggregatedList<DataAuthorReview> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterAuthors(), aggregated, GvAuthor.TYPE);
    }

    public GvCanonicalCollection<GvSubject> aggregateSubjects(IdableList<? extends DataSubject> data) {
        DataAggregator<DataSubject> aggregator = mAggregatorFactory.newSubjectsAggregator(mParams.getSimilarPercentage());
        AggregatedList<DataSubject> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterSubjects(), aggregated, GvSubject.TYPE);
    }

    public GvCanonicalCollection<GvTag> aggregateTags(IdableList<? extends DataTag> data) {
        DataAggregator<DataTag> aggregator = mAggregatorFactory.newTagsAggregator(mParams.getSimilarPercentage());
        AggregatedList<DataTag> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterTags(), aggregated, GvTag.TYPE);
    }

    public GvCanonicalCollection<GvDate> aggregateDates(IdableList<? extends DataDateReview> data) {
        DataAggregator<DataDateReview> aggregator = mAggregatorFactory.newDatesAggregator(mParams.getSimilarDate());
        AggregatedList<DataDateReview> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterDates(), aggregated, GvDate.TYPE);
    }

    public GvCanonicalCollection<GvCriterion> aggregateCriteria(IdableList<? extends 
            DataCriterion> data, CriterionAggregation aggregation) {
        DataAggregator<DataCriterion> aggregator;
        if(aggregation == CriterionAggregation.SUBJECT) {
            aggregator = mAggregatorFactory.newCriteriaAggregatorSameSubject(mParams.getSimilarPercentage());
        } else {
            aggregator = mAggregatorFactory.newCriteriaAggregatorSameSubjectRating(mParams.getSimilarBoolean());
        }
        AggregatedList<DataCriterion> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterCriteria(), aggregated, GvCriterion.TYPE);
    }

    public GvCanonicalCollection<GvImage> aggregateImages(IdableList<? extends DataImage> data) {
        DataAggregator<DataImage> aggregator = mAggregatorFactory.newImagesAggregator(mParams.getSimilarBoolean());
        AggregatedList<DataImage> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterImages(), aggregated, GvImage.TYPE);
    }

    public GvCanonicalCollection<GvComment> aggregateComments(IdableList<? extends DataComment> data) {
        DataAggregator<DataComment> aggregator = mAggregatorFactory.newCommentsAggregator(mParams.getSimilarPercentage());
        AggregatedList<DataComment> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterComments(), aggregated, GvComment.TYPE);
    }

    public GvCanonicalCollection<GvLocation> aggregateLocations(IdableList<? extends DataLocation> data) {
        DataAggregator<DataLocation> aggregator = mAggregatorFactory.newLocationsAggregator(mParams.getSimilarLocation());
        AggregatedList<DataLocation> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterLocations(), aggregated, GvLocation.TYPE);
    }

    public GvCanonicalCollection<GvFact> aggregateFacts(IdableList<? extends DataFact> data) {
        DataAggregator<DataFact> aggregator = mAggregatorFactory.newFactsAggregator(mParams.getSimilarPercentage());
        AggregatedList<DataFact> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterFacts(), aggregated, GvFact.TYPE);
    }
    
    public <T1 extends HasReviewId, T2 extends GvData> GvCanonicalCollection<T2>
    aggregate(DataConverter<? super T1, T2, ? extends GvDataList<T2>> converter,
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
}


