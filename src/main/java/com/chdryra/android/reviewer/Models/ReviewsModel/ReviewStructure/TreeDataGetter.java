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
        criteria.add((MdCriterionList) mRoot.getReview().getCriteria());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            criteria.add(getter.getCriteria());
        }

        return criteria;
    }

    public MdCommentList getComments() {
        MdCommentList comments = new MdCommentList(mId);
        if(mRoot == null) return comments;
        comments.add((MdCommentList)  mRoot.getReview().getComments());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            comments.add(getter.getComments());
        }

        return comments;
    }

    public MdImageList getImages() {
        MdImageList images = new MdImageList(mId);
        if(mRoot == null) return images;
        images.add((MdImageList)  mRoot.getReview().getImages());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            images.add(getter.getImages());
        }

        return images;
    }

    public MdFactList getFacts() {
        MdFactList facts = new MdFactList(mId);
        if(mRoot == null) return facts;
        facts.add((MdFactList)  mRoot.getReview().getFacts());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            facts.add(getter.getFacts());
        }

        return facts;
    }

    public MdLocationList getLocations() {
        MdLocationList locations = new MdLocationList(mId);
        if(mRoot == null) return locations;
        locations.add((MdLocationList)  mRoot.getReview().getLocations());
        for (ReviewNode node : mRoot.getChildren()) {
            TreeDataGetter getter = new TreeDataGetter(node);
            locations.add(getter.getLocations());
        }

        return locations;
    }
}
