package com.chdryra.android.reviewer.Database.Factories;

import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.StorageTypeDefinitions;
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
    private StorageTypeDefinitions mStorageTypeFactory;

    public FactoryReviewerDbContract(FactoryDbColumnDef columnFactory,
                                     FactoryForeignKeyConstraint constraintFactory,
                                     StorageTypeDefinitions storageTypeFactory) {
        mColumnFactory = columnFactory;
        mConstraintFactory = constraintFactory;
        mStorageTypeFactory = storageTypeFactory;
    }

    public ReviewerDbContract newContract() {
        TableAuthors authors = new TableAuthors(mColumnFactory, mStorageTypeFactory);
        TableTags tags = new TableTags(mColumnFactory, mStorageTypeFactory);
        TableReviews reviews = new TableReviews(authors, mColumnFactory, mConstraintFactory, mStorageTypeFactory);
        TableComments comments = new TableComments(reviews, mColumnFactory, mConstraintFactory, mStorageTypeFactory);
        TableFacts factes = new TableFacts(reviews, mColumnFactory, mConstraintFactory, mStorageTypeFactory);
        TableImages images = new TableImages(reviews, mColumnFactory, mConstraintFactory, mStorageTypeFactory);
        TableLocations locations = new TableLocations(reviews, mColumnFactory, mConstraintFactory, mStorageTypeFactory);
        String id = RowReview.COLUMN_REVIEW_ID;

        return new ReviewerDbContractImpl(id, authors, tags, reviews, comments, factes, images,
                locations);
    }

}
