/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.DatabaseAndroidSqLite;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDbPlugin.AndroidSqLiteDb.Implementation.SqLiteTypeDefinitions;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDbPlugin.AndroidSqLiteDb.Implementation.TablesSql;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;

/**
 * Created by: Rizwan Choudrey
 * On: 31/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TablesSqlTest {
    private TablesSql mSql;
    private ReviewerDbContract mContract;

    @Before
    public void setUp() {
        mSql = new TablesSql(new SqLiteTypeDefinitions());
        mContract = new FactoryReviewerDbContract().newContract();
    }

    @Test
    public void createAuthorsTableSql() {
        String expected = "CREATE TABLE Authors(\n" +
                "user_id TEXT NOT NULL,\n" +
                "name TEXT NOT NULL,\n" +
                "PRIMARY KEY(user_id)\n" +
                ")";
        assertThat(mSql.createTableSql(mContract.getAuthorsTable()), is(expected));
    }

    @Test
    public void createTagsTableSql() {
        String expected = "CREATE TABLE Tags(\n" +
                "tag TEXT NOT NULL,\n" +
                "reviews TEXT NOT NULL,\n" +
                "PRIMARY KEY(tag)\n" +
                ")";
        assertThat(mSql.createTableSql(mContract.getTagsTable()), is(expected));
    }

    @Test
    public void createReviewsTableSql() {
        String expected = "CREATE TABLE Reviews(\n" +
                "review_id TEXT NOT NULL,\n" +
                "parent_id TEXT,\n" +
                "user_id TEXT NOT NULL,\n" +
                "publish_date INTEGER NOT NULL,\n" +
                "subject TEXT NOT NULL,\n" +
                "rating REAL NOT NULL,\n" +
                "rating_weight INTEGER NOT NULL,\n" +
                "rating_is_average INTEGER NOT NULL,\n" +
                "PRIMARY KEY(review_id),\n" +
                "FOREIGN KEY(parent_id) REFERENCES Reviews(review_id),\n" +
                "FOREIGN KEY(user_id) REFERENCES Authors(user_id)\n" +
                ")";
        assertThat(mSql.createTableSql(mContract.getReviewsTable()), is(expected));
    }

    @Test
    public void createCommentsTableSql() {
        String expected = "CREATE TABLE Comments(\n" +
                "comment_id TEXT NOT NULL,\n" +
                "review_id TEXT NOT NULL,\n" +
                "comment TEXT NOT NULL,\n" +
                "is_headline INTEGER NOT NULL,\n" +
                "PRIMARY KEY(comment_id),\n" +
                "FOREIGN KEY(review_id) REFERENCES Reviews(review_id)\n" +
                ")";
        assertThat(mSql.createTableSql(mContract.getCommentsTable()), is(expected));
    }

    @Test
    public void createFactsTableSql() {
        String expected = "CREATE TABLE Facts(\n" +
                "fact_id TEXT NOT NULL,\n" +
                "review_id TEXT NOT NULL,\n" +
                "label TEXT NOT NULL,\n" +
                "value TEXT NOT NULL,\n" +
                "is_url INTEGER NOT NULL,\n" +
                "PRIMARY KEY(fact_id),\n" +
                "FOREIGN KEY(review_id) REFERENCES Reviews(review_id)\n" +
                ")";
        assertThat(mSql.createTableSql(mContract.getFactsTable()), is(expected));
    }

    @Test
    public void createLocationsTableSql() {
        String expected = "CREATE TABLE Locations(\n" +
                "location_id TEXT NOT NULL,\n" +
                "review_id TEXT NOT NULL,\n" +
                "latitude REAL NOT NULL,\n" +
                "longitude REAL NOT NULL,\n" +
                "name TEXT NOT NULL,\n" +
                "PRIMARY KEY(location_id),\n" +
                "FOREIGN KEY(review_id) REFERENCES Reviews(review_id)\n" +
                ")";
        assertThat(mSql.createTableSql(mContract.getLocationsTable()), is(expected));
    }

    @Test
    public void createImagesTableSql() {
        String expected = "CREATE TABLE Images(\n" +
                "image_id TEXT NOT NULL,\n" +
                "review_id TEXT NOT NULL,\n" +
                "bitmap BLOB NOT NULL,\n" +
                "is_cover INTEGER NOT NULL,\n" +
                "caption TEXT,\n" +
                "image_date INTEGER,\n" +
                "PRIMARY KEY(image_id),\n" +
                "FOREIGN KEY(review_id) REFERENCES Reviews(review_id)\n" +
                ")";
        assertThat(mSql.createTableSql(mContract.getImagesTable()), is(expected));
    }

    @Test
    public void dropAllTablesSql() {
        String expected = "SET foreign_key_checks = 0;\n" +
                "DROP TABLE IF EXISTS Authors,Reviews,Comments,Facts,Locations,Images,Tags;\n" +
                "SET foreign_key_checks = 1;";
        assertThat(mSql.dropAllTablesSql(mContract.getTableNames()), is(expected));
    }

    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();

    @Test
    public void getFromTableWhereQueryNonNullColumnAndColumnNotInTableThrowsIllegalArgumentExcpetion() {
        DbTable<RowAuthor> table = mContract.getAuthorsTable();
        String column = RowReview.SUBJECT.getName();

        mExpectedException.expect(IllegalArgumentException.class);
        mExpectedException.expectMessage("Column " + column + " not found in table " + table.getName());

        mSql.getFromTableWhereQuery(table, column, RandomString.nextWord());
    }

    @Test
    public void getFromTableWhereQueryNonNullColumnNullValueButColumnNotNullableThrowsIllegalArgumentExcpetion() {
        DbTable<RowAuthor> table = mContract.getAuthorsTable();
        String column = RowAuthor.AUTHOR_NAME.getName();

        mExpectedException.expect(IllegalArgumentException.class);
        mExpectedException.expectMessage("Column " + column + " not nullable");

        mSql.getFromTableWhereQuery(table, column, null);
    }

    @Test
    public void getFromTableWhereQueryNonNullColumnNonNullValue() {
        String expected = "SELECT * FROM Authors WHERE name = ?";
        String name = "Riz";

        TablesSql.Query query = mSql.getFromTableWhereQuery(mContract.getAuthorsTable(),
                RowAuthor.AUTHOR_NAME.getName(), name);

        assertThat(query.getQuery(), is(expected));
        assertThat(query.getArgs().length, is(1));
        assertThat(query.getArgs()[0], is(name));
    }

    @Test
    public void getFromTableWhereQueryNullColumnNullValueSelectsAll() {
        String expected = "SELECT * FROM Authors";

        TablesSql.Query query = mSql.getFromTableWhereQuery(mContract.getAuthorsTable(), null, null);

        assertThat(query.getQuery(), is(expected));
        assertThat(query.getArgs(), is(nullValue()));
    }

    @Test
    public void getFromTableWhereQueryNonNullColumnWithNullValue() {
        String expected = "SELECT * FROM Images WHERE image_date IS NULL";

        TablesSql.Query query = mSql.getFromTableWhereQuery(mContract.getImagesTable(),
                RowImage.IMAGE_DATE.getName(), null);

        assertThat(query.getQuery(), is(expected));
        assertThat(query.getArgs(), is(nullValue()));
    }

    @Test
    public void bindColumnWithValue() {
        String column = RowAuthor.AUTHOR_NAME.getName();
        String value = "Riz";
        TablesSql.Query query = mSql.bindColumnWithValue(column, value);
        assertThat(query.getQuery(), is(column + " = ?"));
        assertThat(query.getArgs().length, is(1));
        assertThat(query.getArgs()[0], is(value));
    }
}
