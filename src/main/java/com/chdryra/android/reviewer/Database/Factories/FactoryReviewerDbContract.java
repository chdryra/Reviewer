package com.chdryra.android.reviewer.Database.Factories;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.Implementation.ReviewerDbContractImpl;
import com.chdryra.android.reviewer.Database.Implementation.TableAuthors;
import com.chdryra.android.reviewer.Database.Implementation.TableComments;
import com.chdryra.android.reviewer.Database.Implementation.TableFacts;
import com.chdryra.android.reviewer.Database.Implementation.TableImages;
import com.chdryra.android.reviewer.Database.Implementation.TableLocations;
import com.chdryra.android.reviewer.Database.Implementation.TableReviews;
import com.chdryra.android.reviewer.Database.Implementation.TableTags;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbContract {
    private FactoryDbColumnDef mColumnFactory;
    private FactoryForeignKeyConstraint mConstraintFactory;

    public FactoryReviewerDbContract(FactoryDbColumnDef columnFactory,
                                     FactoryForeignKeyConstraint constraintFactory) {
        mColumnFactory = columnFactory;
        mConstraintFactory = constraintFactory;
    }

    public ReviewerDbContract newContract() {
        TableAuthors authors = new TableAuthors(mColumnFactory);
        TableTags tags = new TableTags(mColumnFactory);
        TableReviews reviews = new TableReviews(authors, mColumnFactory, mConstraintFactory);
        TableComments comments = new TableComments(reviews, mColumnFactory, mConstraintFactory);
        TableFacts factes = new TableFacts(reviews, mColumnFactory, mConstraintFactory);
        TableImages images = new TableImages(reviews, mColumnFactory, mConstraintFactory);
        TableLocations locations = new TableLocations(reviews, mColumnFactory, mConstraintFactory);
        String id = RowReview.COLUMN_REVIEW_ID;

        return new ReviewerDbContractImpl(id, authors, tags, reviews, comments, factes, images,
                locations);
    }

}
