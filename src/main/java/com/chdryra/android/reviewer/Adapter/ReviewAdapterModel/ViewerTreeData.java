package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.Screens.ReviewListScreen;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeData extends ViewerNodeData {

    public ViewerTreeData(Context context, ReviewNode node, TagsManager tagsManager) {
        super(context, node, tagsManager);
    }

    @Override
    protected GvList makeGridData() {
        ReviewNode node = getReviewNode();
        TagsManager tagsManager = getTagsManager();

        GvReviewId id = GvReviewId.getId(node.getId().toString());
        GvList data = new GvList(id);

        TagCollector tagCollector = new TagCollector(node, tagsManager);
        ViewerChildList wrapper = new ViewerChildList(getContext(), node, tagsManager);

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

    @Override
    protected ReviewViewAdapter expandReviews() {
        return ReviewListScreen.newScreen(getContext(), getReviewNode(), getTagsManager()).getAdapter();
    }

    @Override
    protected ReviewViewAdapter expandData(GvDataCollection data) {
        String subject = data.getStringSummary();
        return FactoryReviewViewAdapter.newExpandToReviewsAdapterForAggregate(getContext(),
                (GvCanonicalCollection) data, subject);
    }
}
