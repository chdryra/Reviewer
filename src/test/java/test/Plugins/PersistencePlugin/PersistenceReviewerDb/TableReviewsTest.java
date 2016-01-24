package test.Plugins.PersistencePlugin.PersistenceReviewerDb;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Factories.FactoryForeignKeyConstraint;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.ColumnInfo;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableAuthors;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableReviews;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;


/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableReviewsTest extends ReviewerDbTableTest<RowReview>{
    private static final String NAME = "Reviews";
    private static final ColumnInfo<?>[] ROWS = {RowReview.REVIEW_ID, RowReview.PARENT_ID,
            RowReview.USER_ID, RowReview.PUBLISH_DATE, RowReview.SUBJECT, RowReview.RATING,
            RowReview.RATING_WEIGHT, RowReview.IS_AVERAGE};
    private static final ColumnInfo<?>[] NULLABLE = {RowReview.PARENT_ID};

    private TableAuthors mAuthorsTable;
    private TableReviews mReviewsTable;

    public TableReviewsTest() {
        super(NAME, ROWS, NULLABLE);
    }

    @Override
    protected DbTable<RowReview> getTableToTest(FactoryDbColumnDef colFactory, FactoryForeignKeyConstraint constraintFactory) {
        mAuthorsTable = new TableAuthors(colFactory);
        mReviewsTable = new TableReviews(colFactory, mAuthorsTable, constraintFactory);

        return mReviewsTable;
    }

    @Override
    protected void setFkConstraints() {
        addConstraint(mReviewsTable, RowReview.PARENT_ID);
        addConstraint(mAuthorsTable, RowReview.USER_ID);
    }
}