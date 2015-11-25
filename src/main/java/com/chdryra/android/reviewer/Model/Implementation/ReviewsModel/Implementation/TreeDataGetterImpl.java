package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Interfaces.TreeDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeDataGetterImpl implements TreeDataGetter {
    public MdReviewId mId;
    public ReviewNode mRoot;

    public TreeDataGetterImpl() {
    }

    private TreeDataGetterImpl(ReviewNode root) {
        mRoot = root;
        mId = new MdReviewId(root.getReviewId());
    }

    @Override
    public void setRoot(ReviewNode root) {
        mRoot = root;
        mId = new MdReviewId(root.getReviewId());
    }

    //public methods
    @Override
    public MdCriterionList getCriteria() {
        MdCriterionList criteria = new MdCriterionList(mId);
        if(mRoot == null) return criteria;
        criteria.addCollection((MdCriterionList) mRoot.getReview().getCriteria());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetterImpl(node);
            criteria.addCollection(getter.getCriteria());
        }

        return criteria;
    }

    @Override
    public MdCommentList getComments() {
        MdCommentList comments = new MdCommentList(mId);
        if(mRoot == null) return comments;
        comments.addCollection((MdCommentList) mRoot.getReview().getComments());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetterImpl(node);
            comments.addCollection(getter.getComments());
        }

        return comments;
    }

    @Override
    public MdImageList getImages() {
        MdImageList images = new MdImageList(mId);
        if(mRoot == null) return images;
        images.addCollection((MdImageList) mRoot.getReview().getImages());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetterImpl(node);
            images.addCollection(getter.getImages());
        }

        return images;
    }

    @Override
    public MdFactList getFacts() {
        MdFactList facts = new MdFactList(mId);
        if(mRoot == null) return facts;
        facts.addCollection((MdFactList) mRoot.getReview().getFacts());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetterImpl(node);
            facts.addCollection(getter.getFacts());
        }

        return facts;
    }

    @Override
    public MdLocationList getLocations() {
        MdLocationList locations = new MdLocationList(mId);
        if(mRoot == null) return locations;
        locations.addCollection((MdLocationList) mRoot.getReview().getLocations());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetterImpl(node);
            locations.addCollection(getter.getLocations());
        }

        return locations;
    }
}
