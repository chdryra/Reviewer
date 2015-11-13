package com.chdryra.android.reviewer.Database;

import android.database.sqlite.SQLiteDatabase;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.DataConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.MdConverterFacts;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.MdConverterLocations;
import com.chdryra.android.reviewer.Interfaces.Data.DataComment;
import com.chdryra.android.reviewer.Interfaces.Data.DataDate;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Interfaces.Data.DataSubject;
import com.chdryra.android.reviewer.Interfaces.Data.IdableList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdAuthor;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCommentList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdCriterionList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdDate;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdFactList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdIdableCollection;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdImageList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdLocationList;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdRating;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdSubject;
import com.chdryra.android.reviewer.Models.UserModel.UserId;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUserDb implements Review {
    private String mReviewId;
    private RowReview mRow;
    private ReviewerDb mDatabase;
    private ReviewNode mNode;

    //Constructors
    public ReviewUserDb(RowReview row,
                        ReviewerDb database,
                        FactoryReviewNodeComponent reviewFactory) {
        mDatabase = database;
        init(row, reviewFactory);
    }

    private void init(RowReview row, FactoryReviewNodeComponent reviewFactory) {
        mReviewId = row.getReviewId();
        mRow = row;
        mNode = reviewFactory.createReviewNodeComponent(this, false).makeTree();
    }

    private <T extends DbTableRow> T getRowWhere(DbTable<T> table, String
            col, String val) {
        SQLiteDatabase db = mDatabase.getHelper().getReadableDatabase();

        db.beginTransaction();
        T row = mDatabase.getRowWhere(db, table, col, val);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        return row;
    }

    private <T extends ReviewDataRow> ArrayList<T> loadFromDataTable(DbTable<T> table) {
        SQLiteDatabase db = mDatabase.getHelper().getReadableDatabase();

        db.beginTransaction();
        ArrayList<T> data = mDatabase.loadFromDataTable(db, table, mReviewId.toString());
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();

        return data;
    }

    //Overridden
    @Override
    public DataSubject getSubject() {
        return new Subject();
    }

    @Override
    public MdRating getRating() {
        return mRating;
    }

    @Override
    public MdAuthor getAuthor() {
        RowAuthor row = getRowWhere(mDatabase.getAuthorsTable(), RowAuthor.COLUMN_USER_ID,
                mUserId.toString());
        return new MdAuthor(mReviewId, row.getName(), row.getUserId());
    }

    @Override
    public MdDate getPublishDate() {
        return mPublishDate;
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return mNode;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return mRatingIsAverage;
    }

    @Override
    public MdCriterionList getCriteria() {
        SQLiteDatabase db = mDatabase.getHelper().getReadableDatabase();
        MdIdableCollection<Review> criteria = mDatabase.loadReviewsFromDbWhere(db, RowReview
                .COLUMN_PARENT_ID, mReviewId.toString());
        return new MdCriterionList(mReviewId, criteria);
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        ArrayList<RowComment> comments = loadFromDataTable(mDatabase.getCommentsTable());
        return new IdableRowList<>(mReviewId)
    }

    @Override
    public MdFactList getFacts() {
        return loadFromDataTable(mDatabase.getFactsTable(), MdFactList.class);
    }

    @Override
    public MdImageList getImages() {
        return loadFromDataTable(mDatabase.getImagesTable(), MdImageList.class);
    }

    @Override
    public MdLocationList getLocations() {
        return loadFromDataTable(mDatabase.getLocationsTable(), MdLocationList.class);
    }

    @Override
    public MdReviewId getMdReviewId() {
        return mReviewId;
    }

    private class Subject implements DataSubject {
        @Override
        public String getSubject() {
            return mSubject;
        }

        @Override
        public String getReviewId() {
            return mReviewId;
        }
    }
}
