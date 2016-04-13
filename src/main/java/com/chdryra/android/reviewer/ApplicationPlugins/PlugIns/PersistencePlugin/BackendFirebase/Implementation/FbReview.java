/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbReview {
    private String reviewId;
    private String subject;
    private Rating rating;
    private Author author;
    private long publishDate;
    private List<Criterion> criteria;
    private List<Comment> comments;
    private List<Fact> facts;
    private List<ImageData> images;
    private List<Location> locations;
    private List<String> tags;

    private boolean ratingAverageOfCriteria;

    public FbReview() {
    }

    public FbReview(Review review, TagsManager tagsManager) {
        reviewId = review.getReviewId().toString();
        subject = review.getSubject().getSubject();
        rating = new Rating(review.getRating());
        author = new Author(review.getAuthor());
        publishDate = review.getPublishDate().getTime();
        ratingAverageOfCriteria = review.isRatingAverageOfCriteria();

        criteria = new ArrayList<>();
        for(DataCriterion criterion : review.getCriteria()) {
            criteria.add(new Criterion(criterion));
        }

        comments = new ArrayList<>();
        for(DataComment comment : review.getComments()) {
            comments.add(new Comment(comment));
        }

        facts = new ArrayList<>();
        for(DataFact fact : review.getFacts()) {
            facts.add(new Fact(fact));
        }

        images = new ArrayList<>();
        for(DataImage image : review.getImages()) {
            images.add(new ImageData(image));
        }

        locations = new ArrayList<>();
        for(DataLocation location : review.getLocations()) {
            locations.add(new Location(location));
        }

        tags = new ArrayList<>();
        ItemTagCollection itemtags = tagsManager.getTags(reviewId);
        for(ItemTag tag : itemtags) {
            tags.add(tag.getTag());
        }
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getSubject() {
        return subject;
    }

    public Rating getRating() {
        return rating;
    }

    public Author getAuthor() {
        return author;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public boolean isRatingAverageOfCriteria() {
        return ratingAverageOfCriteria;
    }

    public List<String> getTags() {
        return tags;
    }
    
    public List<Criterion> getCriteria() {
        return criteria != null ? criteria : new ArrayList<Criterion>();
    }

    public List<Comment> getComments() {
        return comments != null ? comments : new ArrayList<Comment>();
    }

    public List<Fact> getFacts() {
        return facts != null ? facts : new ArrayList<Fact>();
    }

    public List<ImageData> getImages() {
        return images != null ? images : new ArrayList<ImageData>();
    }

    public List<Location> getLocations() {
        return locations != null ? locations : new ArrayList<Location>();
    }
}
