/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.Tagging.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.TreeDataGetter;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Grid data is a summary of how many of each type of {@link com.chdryra.android.reviewer.Model
 * .MdData}. {@link TagsManager.ReviewTag} is in the tree.
 * Includes number of reviews and subjects if a meta-review.
 */
public class ViewerTreeData implements GridDataViewer<GvData> {
    private ReviewNode mNode;
    private TreeDataGetter mGetter;

    public ViewerTreeData(ReviewNode node) {
        mNode = node;
        mGetter = new TreeDataGetter(mNode);
    }

    @Override
    public GvList getGridData() {
        return mNode.getChildren().size() > 0 ? getAggregateGridData() : getNodeGridData();
    }

    private GvList getAggregateGridData() {
        GvReviewId id = GvReviewId.getId(mNode.getId().toString());
        GvList data = new GvList(id);

        TagCollector tagCollector = new TagCollector(mNode);
        ViewerChildList wrapper = new ViewerChildList(mNode);

        data.add(wrapper.getGridData());
        data.add(Aggregater.aggregate(MdGvConverter.convertChildAuthors(mNode)));
        data.add(Aggregater.aggregate(MdGvConverter.convertChildSubjects(mNode)));
        data.add(Aggregater.aggregate(MdGvConverter.convertChildPublishDates(mNode)));

        data.add(Aggregater.aggregate(tagCollector.collectTags()));
        data.add(Aggregater.aggregate(collectCriteria()));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mGetter.getImages())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mGetter.getComments())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mGetter.getLocations())));
        data.add(Aggregater.aggregate(MdGvConverter.convert(mGetter.getFacts())));

        return data;
    }

    private GvList getNodeGridData() {
        GvReviewId id = GvReviewId.getId(mNode.getId().toString());
        GvList data = new GvList(id);
        TagCollector tagCollector = new TagCollector(mNode);

        data.add(tagCollector.collectTags());
        data.add(collectCriteria());
        data.add(MdGvConverter.convert(mGetter.getImages()));
        data.add(MdGvConverter.convert(mGetter.getComments()));
        data.add(MdGvConverter.convert(mGetter.getLocations()));
        data.add(MdGvConverter.convert(mGetter.getFacts()));

        return data;
    }

    @Override
    public boolean isExpandable(GvData datum) {
        if (!datum.hasElements()) return false;

        GvDataCollection data = (GvDataCollection) datum;
        GvList gridData = getGridData();
        for (GvData list : gridData) {
            ((GvDataCollection) list).sort();
        }
        data.sort();

        return gridData.contains(datum);
    }

    @Override
    public ReviewViewAdapter<? extends GvData> expandItem(GvData datum) {
        if (isExpandable(datum)) {
            ReviewViewAdapter<? extends GvData> parent =
                    FactoryReviewViewAdapter.newChildListAdapter(mNode);
            if (datum.getGvDataType() == GvReviewOverviewList.TYPE) {
                return parent;
            }

            //TODO make typesafe
            return FactoryReviewViewAdapter.newGvDataCollectionAdapter(parent,
                    (GvDataCollection) datum);
        }

        return null;
    }

    private GvChildReviewList collectCriteria() {
        GvChildReviewList criteria = new GvChildReviewList(GvReviewId.getId(mNode.getId()
                .toString()));
        criteria.addList(MdGvConverter.convertChildren(mNode.expand()));
        for (ReviewNode node : mNode.getChildren()) {
            criteria.addList(MdGvConverter.convertChildren(node.expand()));
        }

        return criteria;
    }
}
