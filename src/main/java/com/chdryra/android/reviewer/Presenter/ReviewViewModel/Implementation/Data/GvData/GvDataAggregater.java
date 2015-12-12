package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories.FactoryDataAggregator;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.AggregatedCollection;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregater {
    private FactoryDataAggregator mAggregatorFactory;
    private ConverterGv mConverter;

    public enum CriterionAggregation { SUBJECT, SUBJECT_RATING}
    
    public GvDataAggregater(FactoryDataAggregator aggregatorFactory,
                            ConverterGv converter) {
        mAggregatorFactory = aggregatorFactory;
        mConverter = converter;
    }

    public GvCanonicalCollection<GvAuthor> aggregateAuthors(IdableList<? extends DataAuthorReview> data) {
        DataAggregator<DataAuthorReview> aggregator = mAggregatorFactory.newAuthorsAggregator();
        AggregatedCollection<DataAuthorReview> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterAuthors(), aggregated, GvAuthor.TYPE);
    }

    public GvCanonicalCollection<GvSubject> aggregateSubjects(IdableList<? extends DataSubject> data) {
        DataAggregator<DataSubject> aggregator = mAggregatorFactory.newSubjectsAggregator();
        AggregatedCollection<DataSubject> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterSubjects(), aggregated, GvSubject.TYPE);
    }

    public GvCanonicalCollection<GvTag> aggregateTags(IdableList<? extends DataTag> data) {
        DataAggregator<DataTag> aggregator = mAggregatorFactory.newTagsAggregator();
        AggregatedCollection<DataTag> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterTags(), aggregated, GvTag.TYPE);
    }

    public GvCanonicalCollection<GvDate> aggregateDates(IdableList<? extends DataDateReview> data) {
        DataAggregator<DataDateReview> aggregator = mAggregatorFactory.newDatesAggregator();
        AggregatedCollection<DataDateReview> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterDates(), aggregated, GvDate.TYPE);
    }

    public GvCanonicalCollection<GvCriterion> aggregateCriteria(IdableList<? extends 
            DataCriterion> data, CriterionAggregation aggregation) {
        DataAggregator<DataCriterion> aggregator;
        if(aggregation == CriterionAggregation.SUBJECT) {
            aggregator = mAggregatorFactory.newCriteriaAggregatorSubject();
        } else {
            aggregator = mAggregatorFactory.newCriteriaAggregatorSubjectRating();
        }
        AggregatedCollection<DataCriterion> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterCriteria(), aggregated, GvCriterion.TYPE);
    }

    public GvCanonicalCollection<GvImage> aggregateImages(IdableList<? extends DataImage> data) {
        DataAggregator<DataImage> aggregator = mAggregatorFactory.newImagesAggregator();
        AggregatedCollection<DataImage> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterImages(), aggregated, GvImage.TYPE);
    }

    public GvCanonicalCollection<GvComment> aggregateComments(IdableList<? extends DataComment> data) {
        DataAggregator<DataComment> aggregator = mAggregatorFactory.newCommentsAggregator();
        AggregatedCollection<DataComment> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterComments(), aggregated, GvComment.TYPE);
    }

    public GvCanonicalCollection<GvLocation> aggregateLocations(IdableList<? extends DataLocation> data) {
        DataAggregator<DataLocation> aggregator = mAggregatorFactory.newLocationsAggregator();
        AggregatedCollection<DataLocation> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterLocations(), aggregated, GvLocation.TYPE);
    }

    public GvCanonicalCollection<GvFact> aggregateFacts(IdableList<? extends DataFact> data) {
        DataAggregator<DataFact> aggregator = mAggregatorFactory.newFactsAggregator();
        AggregatedCollection<DataFact> aggregated = aggregator.aggregate(data);
        return aggregate(mConverter.getConverterFacts(), aggregated, GvFact.TYPE);
    }
    
    public <T1 extends HasReviewId, T2 extends GvData> GvCanonicalCollection<T2>
    aggregate(DataConverter<? super T1, T2, ? extends GvDataList<T2>> converter,
              AggregatedCollection<T1> aggregated, GvDataType<T2> type) {
        GvReviewId id = new GvReviewId(aggregated.getReviewId());
        GvCanonicalCollection<T2> canonicals = new GvCanonicalCollection<>(id, type);
        for(AggregatedList<T1> aggregatedList : aggregated) {
            T2 canonical = converter.convert(aggregatedList.getCanonical());
            GvDataList<T2> similar = converter.convert(aggregatedList.getAggregated());
            canonicals.addCanonnical(new GvCanonical<>(canonical, similar));
        }

        return canonicals;
    }
}


