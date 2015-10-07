package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataAggregation.Aggregater;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;

/**
 * Created by: Rizwan Choudrey
 * On: 06/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerNodeData implements GridDataViewer<GvData> {
    private GridDataViewer<GvData> mViewer;

    //Constructors
    public ViewerNodeData(Context context, ReviewNode node, ReviewsRepository repository) {
        IdableList<ReviewNode> children = node.getChildren();
        if (children.size() > 1) {
            mViewer = new ViewerTreeData(context, node, repository);
        } else {
            ReviewNode toExpand = children.size() == 0 ? node : children.getItem(0);
            ReviewNode expanded = toExpand.expand();
            if (expanded.equals(toExpand)) {
                mViewer = new ViewerReviewData(context, expanded, repository);
            } else {
                mViewer = new ViewerNodeData(context, expanded, repository);
            }
        }
    }

    //Overridden
    @Override
    public GvDataList<GvData> getGridData() {
        return mViewer.getGridData();
    }

    private static class ViewerReviewData implements GridDataViewer<GvData> {
        private Context mContext;
        private ReviewNode mNode;
        private ReviewsRepository mRepository;
        private GvList mCache;

        private ViewerReviewData(Context context, ReviewNode node, ReviewsRepository repository) {
            mContext = context;
            mNode = node;
            mRepository = repository;
        }

        //protected methods
        protected Context getContext() {
            return mContext;
        }

        protected ReviewNode getReviewNode() {
            return mNode;
        }

        protected ReviewsRepository getRepository() {
            return mRepository;
        }

        protected GvList makeGridData() {
            Review review = mNode.getReview();
            GvReviewId id = GvReviewId.getId(review.getId().toString());

            GvList data = new GvList(id);
            data.add(MdGvConverter.getTags(review.getId().toString(), mRepository.getTagsManager
                    ()));
            data.add(MdGvConverter.convert(review.getCriteria()));
            data.add(MdGvConverter.convert(review.getImages()));
            data.add(MdGvConverter.convert(review.getComments()));
            data.add(MdGvConverter.convert(review.getLocations()));
            data.add(MdGvConverter.convert(review.getFacts()));

            return data;
        }

        @Override
        public GvList getGridData() {
            GvList data = makeGridData();
            mCache = data;
            return data;
        }



        @Override
        public boolean isExpandable(GvData datum) {
            if (!datum.hasElements() || mCache == null) return false;

            GvDataCollection data = (GvDataCollection) datum;
            for (GvData list : mCache) {
                ((GvDataCollection) list).sort();
            }
            data.sort();

            return mCache.contains(datum);
        }



        @Override
        public ReviewViewAdapter expandGridCell(GvData datum) {
            if (isExpandable(datum)) {
                return FactoryReviewViewAdapter.newDataToDataAdapter(mContext, mNode,
                        (GvDataCollection<? extends GvData>) datum, mRepository);
            } else {
                return null;
            }
        }



        @Override
        public ReviewViewAdapter expandGridData() {
            return null;
        }


    }

    /**
     * Created by: Rizwan Choudrey
     * On: 05/10/2015
     * Email: rizwan.choudrey@gmail.com
     */
    private static class ViewerTreeData extends ViewerReviewData {
        private ViewerTreeData(Context context, ReviewNode node, ReviewsRepository repository) {
            super(context, node, repository);
        }

        //Overridden
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

        @Override
        public ReviewViewAdapter expandGridCell(GvData datum) {
            ReviewViewAdapter adapter = null;
            if (isExpandable(datum)) {
                if (datum.getGvDataType() == GvReviewOverviewList.GvReviewOverview.TYPE) {
                    adapter = FactoryReviewViewAdapter.newReviewsListAdapter(getContext(),
                            getReviewNode(), getRepository());
                } else {
                    String subject = datum.getStringSummary();
                    adapter = FactoryReviewViewAdapter.newAggregateToReviewsAdapter(
                            getContext(), (GvCanonicalCollection) datum, getRepository(), subject);
                }
            }

            return adapter;
        }
    }

    @Override
    public boolean isExpandable(GvData datum) {
        return mViewer.isExpandable(datum);
    }


    @Override
    public ReviewViewAdapter expandGridCell(GvData datum) {
        return mViewer.expandGridCell(datum);
    }

    @Override
    public ReviewViewAdapter expandGridData() {
        return mViewer.expandGridData();
    }


}
