package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.Screens.ReviewListScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeData extends ViewerReviewData {
    public ViewerTreeData(Context context, ReviewNode node, ReviewsRepository repository) {
        super(context, node, repository);
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvData datum) {
        ReviewViewAdapter adapter = null;
        if (isExpandable(datum)) {
            if (datum.getGvDataType() == GvReviewOverviewList.GvReviewOverview.TYPE) {
                adapter = ReviewListScreen.newScreen(getContext(), getReviewNode(),
                        getRepository()).getAdapter();;
            } else {
                String subject = datum.getStringSummary();
                adapter = FactoryReviewViewAdapter.newAggregateToReviewsAdapter(
                        getContext(), (GvCanonicalCollection) datum, getRepository(), subject);
            }
        }

        return adapter;
    }

    @Override
    protected GvList makeGridData() {
        ReviewNode node = getReviewNode();
        ReviewsRepository repository = getRepository();

        TagCollector tagCollector = new TagCollector(node, repository.getTagsManager());
        ViewerChildList wrapper = new ViewerChildList(getContext(), node, repository);

        GvList data = new GvList(GvReviewId.getId(node.getId().toString()));
        data.add(wrapper.getGridData());
        data.add(Aggregater.aggregate(MdGvConverter.convertChildAuthors(node)));
        data.add(Aggregater.aggregate(MdGvConverter.convertChildSubjects(node)));
        data.add(Aggregater.aggregate(MdGvConverter.convertChildPublishDates(node)));

        data.add(Aggregater.aggregate(tagCollector.collectTags()));
        data.add(Aggregater.aggregate(MdGvConverter.convert(node.getCriteria())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(node.getImages())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(node.getComments())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(node.getLocations())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(node.getFacts())));

        return data;
    }
}
