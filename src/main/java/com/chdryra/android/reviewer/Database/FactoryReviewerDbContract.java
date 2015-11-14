package com.chdryra.android.reviewer.Database;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbContract {

    public ReviewerDbContract newContract() {
        TableAuthors authors = new TableAuthors();
        TableTags tags = new TableTags();
        TableReviews reviews = new TableReviews(authors);
        TableComments comments = new TableComments(reviews);
        TableFacts factes = new TableFacts(reviews);
        TableImages images = new TableImages(reviews);
        TableLocations locations = new TableLocations(reviews);
        String id = RowReview.COLUMN_REVIEW_ID;

        return new ReviewerDbContract(id, authors, tags, reviews, comments, factes, images,
                locations);
    }

}
