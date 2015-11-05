package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Model.ReviewData.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewData.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.TreeMethods.VisitorReviewsGetter;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.GvDataAggregation.FactoryGvDataAggregate;
import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;

/**
 * Created by: Rizwan Choudrey
 * On: 05/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
class ViewerTreeData extends ViewerReviewData {
    private FactoryGvDataAggregate mAggregateFactory;

    ViewerTreeData(ReviewNode node,
                   MdGvConverter converter,
                   TagsManager tagsManager,
                   FactoryReviewViewAdapter adapterFactory,
                   FactoryGvDataAggregate aggregateFactory) {
        super(node, converter, tagsManager, adapterFactory);
        mAggregateFactory = aggregateFactory;
    }

    //Overridden
    @Override
    protected GvList makeGridData() {
        ReviewNode node = getReviewNode();
        FactoryReviewViewAdapter adapterFactory = getAdapterFactory();

        GridDataViewer wrapper = adapterFactory.newChildListViewer(node);
        MdGvConverter converter = adapterFactory.getConverter();
        GvList data = new GvList(GvReviewId.getId(node.getId().toString()));

        data.add(wrapper.getGridData());
        data.add(mAggregateFactory.getAggregate(convertChildAuthors()));
        data.add(mAggregateFactory.getAggregate(convertChildSubjects()));
        data.add(mAggregateFactory.getAggregate(convertChildPublishDates()));
        data.add(mAggregateFactory.getAggregate(collectTags(node)));
        data.add(mAggregateFactory.getAggregate(converter.convert(node.getCriteria()), false));
        data.add(mAggregateFactory.getAggregate(converter.convert(node.getImages())));
        data.add(mAggregateFactory.getAggregate(converter.convert(node.getComments())));
        data.add(mAggregateFactory.getAggregate(converter.convert(node.getLocations())));
        data.add(mAggregateFactory.getAggregate(converter.convert(node.getFacts())));

        return data;
    }

    @Override
    public ReviewViewAdapter expandGridCell(GvData datum) {
        FactoryReviewViewAdapter adapterFactory = getAdapterFactory();
        ReviewViewAdapter adapter = null;
        if (isExpandable(datum)) {
            if (datum.getGvDataType().equals(GvReviewOverviewList.GvReviewOverview.TYPE)) {
                adapter = adapterFactory.newReviewsListAdapter(getReviewNode());
            } else {
                String subject = datum.getStringSummary();
                GvCanonicalCollection data = (GvCanonicalCollection) datum;
                adapter = adapterFactory.newAggregateToReviewsAdapter(data, subject);
            }
        }

        return adapter;
    }

    private GvTagList collectTags(ReviewNode node) {
        MdDataList<ReviewId> ids = new MdDataList<>(node.getId());
        for (Review review : VisitorReviewsGetter.flatten(node)) {
            ids.add(review.getId());
        }

        GvTagList tags = new GvTagList(GvReviewId.getId(node.getId().toString()));
        for (ReviewId id : ids) {
            for (GvTagList.GvTag tag : getTags(id.toString())) {
                tags.add(tag);
            }
        }

        return tags;
    }

    private GvSubjectList convertChildSubjects() {
        ReviewNode node = getReviewNode();
        MdDataList<MdSubject> mdsubjects = new MdDataList<>(node.getId());
        for (ReviewNode child : node.getChildren()) {
            mdsubjects.add(child.getSubject());
        }

        GvReviewId id = GvReviewId.getId(node.getId().toString());
        GvSubjectList subjects = new GvSubjectList(id);
        for (MdSubject mdSubject : mdsubjects) {
            GvReviewId subjectId = GvReviewId.getId(mdSubject.getReviewId().toString());
            String subject = mdSubject.get();
            subjects.add(new GvSubjectList.GvSubject(subjectId, subject));
        }

        return subjects;
    }

    private GvAuthorList convertChildAuthors() {
        ReviewNode node = getReviewNode();
        GvReviewId id = GvReviewId.getId(node.getId().toString());
        GvAuthorList authors = new GvAuthorList(id);
        for (ReviewNode child : node.getChildren()) {
            GvReviewId childId = GvReviewId.getId(child.getId().toString());
            Author author = child.getAuthor();
            authors.add(new GvAuthorList.GvAuthor(childId, author.getName(),
                    author.getUserId().toString()));

        }

        return authors;
    }

    private GvDateList convertChildPublishDates() {
        ReviewNode node = getReviewNode();
        GvReviewId id = GvReviewId.getId(node.getId().toString());
        GvDateList list = new GvDateList(id);
        for (ReviewNode child : node.getChildren()) {
            GvReviewId childId = GvReviewId.getId(child.getId().toString());
            PublishDate date = child.getPublishDate();
            list.add(new GvDateList.GvDate(childId, date.getDate()));
        }

        return list;
    }
}
