/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDb {
    public static final String SUBJECT = "subject";
    public static final String RATING = "rating";
    public static final String AUTHOR = "author";
    public static final String PUBLISH_DATE = "publishDate";
    public static final String COVER = "cover";
    public static final String CRITERIA = "criteria";
    public static final String COMMENTS = "comments";
    public static final String FACTS = "facts";
    public static final String IMAGES = "images";
    public static final String LOCATIONS = "locations";
    public static final String TAGS = "tags";

    private String reviewId;
    private String subject;
    private Rating rating;
    private Author author;
    private long publishDate;
    private ImageData cover;
    private List<Criterion> criteria;
    private List<Comment> comments;
    private List<Fact> facts;
    private List<ImageData> images;
    private List<Location> locations;
    private List<String> tags;
    private boolean average;

    public ReviewDb() {
    }

    public int size(){
        return 2 + Rating.size() + Author.size() + 2 + 6 + 1;
    }

    public ReviewDb(Review review, List<String> reviewTags) {
        reviewId = review.getReviewId().toString();
        subject = review.getSubject().getSubject();
        rating = new Rating(review.getRating());
        author = new Author(review.getAuthor());
        publishDate = review.getPublishDate().getTime();
        average = review.isRatingAverageOfCriteria();

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
            ImageData data = new ImageData(image);
            images.add(data);
            if(cover == null && image.isCover()) cover = data;
        }

        locations = new ArrayList<>();
        for(DataLocation location : review.getLocations()) {
            locations.add(new Location(location));
        }

        tags = reviewTags;
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

    public ImageData getCover() {
        return cover;
    }

    public boolean isAverage() {
        return average;
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
