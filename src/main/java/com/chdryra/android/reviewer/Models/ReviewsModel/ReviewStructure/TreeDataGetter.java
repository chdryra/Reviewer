package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCommentList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCriterionList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdFactList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdImageList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdLocationList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 15/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class TreeDataGetter {
    public MdReviewId mId;
    public ReviewNode mRoot;

    public TreeDataGetter() {
    }

    public TreeDataGetter(ReviewNode root) {
        mRoot = root;
        mId = new MdReviewId(root.getReviewId());
    }

    public void setRoot(ReviewNode root) {
        mRoot = root;
        mId = new MdReviewId(root.getReviewId());
    }

    //public methods
    public MdCriterionList getCriteria() {
        MdCriterionList criteria = new MdCriterionList(mId);
        if(mRoot == null) return criteria;
        criteria.addCollection((MdCriterionList) mRoot.getReview().getCriteria());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            criteria.addCollection(getter.getCriteria());
        }

        return criteria;
    }

    public MdCommentList getComments() {
        MdCommentList comments = new MdCommentList(mId);
        if(mRoot == null) return comments;
        comments.addCollection((MdCommentList) mRoot.getReview().getComments());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            comments.addCollection(getter.getComments());
        }

        return comments;
    }

    public MdImageList getImages() {
        MdImageList images = new MdImageList(mId);
        if(mRoot == null) return images;
        images.addCollection((MdImageList) mRoot.getReview().getImages());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            images.addCollection(getter.getImages());
        }

        return images;
    }

    public MdFactList getFacts() {
        MdFactList facts = new MdFactList(mId);
        if(mRoot == null) return facts;
        facts.addCollection((MdFactList) mRoot.getReview().getFacts());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            facts.addCollection(getter.getFacts());
        }

        return facts;
    }

    public MdLocationList getLocations() {
        MdLocationList locations = new MdLocationList(mId);
        if(mRoot == null) return locations;
        locations.addCollection((MdLocationList) mRoot.getReview().getLocations());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            locations.addCollection(getter.getLocations());
        }

        return locations;
    }
}
