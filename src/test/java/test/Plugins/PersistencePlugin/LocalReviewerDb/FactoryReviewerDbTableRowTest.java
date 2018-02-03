/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.ByteArray;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Factories
        .FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowCriterion;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.mygenerallibrary.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.testutils.RandomLatLng;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import test.TestUtils.RandomAuthor;
import test.TestUtils.RandomBoolean;
import test.TestUtils.RandomDataDate;
import test.TestUtils.RandomRating;
import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewData;
import test.TestUtils.RandomReviewId;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by: Rizwan Choudrey
 * On: 25/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDbTableRowTest {
    private static final int INDEX = 314;
    private FactoryDbTableRow mFactory;
    private DataValidator mValidator;

    @Before
    public void setUp() {
        mFactory = new FactoryReviewerDbTableRow();
        mValidator = new DataValidator();
    }

    @Test
    public void emptyRowReview() {
        checkEmptyRowConstruction(RowReview.class);
    }

    @Test
    public void emptyRowAuthor() {
        checkEmptyRowConstruction(RowAuthor.class);
    }

    @Test
    public void emptyRowTag() {
        checkEmptyRowConstruction(RowTag.class);
    }

    @Test
    public void emptyRowComment() {
        checkEmptyRowConstruction(RowComment.class);
    }

    @Test
    public void emptyRowCriterion() {
        checkEmptyRowConstruction(RowCriterion.class);
    }
    
    @Test
    public void emptyRowFact() {
        checkEmptyRowConstruction(RowFact.class);
    }

    @Test
    public void emptyRowImage() {
        checkEmptyRowConstruction(RowImage.class);
    }

    @Test
    public void emptyRowLocation() {
        checkEmptyRowConstruction(RowLocation.class);
    }

    @Test
    public void newRowReviewWithReviewConstructor() {
        RowReview row = mFactory.newRow(RowReview.class, RandomReview.nextReview());

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowReviewWithRowValuesConstructor() {
        RowValuesForTest values = new RowValuesForTest();
        values.put(RowReview.REVIEW_ID, RandomReviewId.nextIdString());
        values.put(RowReview.AUTHOR_ID, RandomAuthor.nextAuthor().getAuthorId().toString());
        values.put(RowReview.PUBLISH_DATE, RandomDataDate.nextDateTime().getTime());
        values.put(RowReview.SUBJECT, RandomString.nextWord());
        values.put(RowReview.RATING, RandomRating.nextRating());
        values.put(RowReview.RATING_WEIGHT, RandomRating.nextWeight());
        values.put(RowReview.IS_AVERAGE, RandomBoolean.nextBoolean());


        RowReview row = mFactory.newRow(RowReview.class, values);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowAuthorWithAuthorConstructor() {
        RowAuthor row = mFactory.newRow(RowAuthor.class, RandomAuthor.nextAuthor());

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowAuthorWithRowValuesConstructor() {
        RowValuesForTest values = new RowValuesForTest();
        values.put(RowAuthor.AUTHOR_ID, RandomAuthor.nextAuthor().getAuthorId().toString());
        values.put(RowAuthor.AUTHOR_NAME, RandomString.nextWord());

        RowAuthor row = mFactory.newRow(RowAuthor.class, values);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowTagWithTagConstructor() {
        ItemTag tag = RandomReviewData.nextItemTag();
        RowTag row = mFactory.newRow(RowTag.class, tag);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowTagWithRowValuesConstructor() {
        RowValuesForTest values = new RowValuesForTest();
        values.put(RowTag.TAG, RandomString.nextWord());
        values.put(RowTag.REVIEWS, RandomString.nextWord());

        RowTag row = mFactory.newRow(RowTag.class, values);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowCriterionWithDataCriterionConstructor() {
        RowCriterion row = mFactory.newRow(RowCriterion.class, RandomReviewData.nextCriterion(), INDEX);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowCriterionWithRowValuesConstructor() {
        RowValuesForTest values = new RowValuesForTest();
        values.put(RowCriterion.CRITERION_ID, RandomString.nextWord());
        values.put(RowCriterion.SUBJECT, RandomString.nextWord());
        values.put(RowCriterion.REVIEW_ID, RandomReviewId.nextIdString());
        values.put(RowCriterion.RATING, RandomRating.nextRating());

        RowCriterion row = mFactory.newRow(RowCriterion.class, values);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }
    
    @Test
    public void newRowCommentWithDataCommentConstructor() {
        RowComment row = mFactory.newRow(RowComment.class, RandomReviewData.nextComment(), INDEX);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowCommentWithRowValuesConstructor() {
        RowValuesForTest values = new RowValuesForTest();
        values.put(RowComment.COMMENT_ID, RandomString.nextWord());
        values.put(RowComment.COMMENT, RandomString.nextSentence());
        values.put(RowComment.REVIEW_ID, RandomReviewId.nextIdString());
        values.put(RowComment.IS_HEADLINE, RandomBoolean.nextBoolean());

        RowComment row = mFactory.newRow(RowComment.class, values);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowFactWithDataFactConstructor() {
        RowFact row = mFactory.newRow(RowFact.class, RandomReviewData.nextFact(), INDEX);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowFactWithRowValuesConstructor() {
        RowValuesForTest values = new RowValuesForTest();
        values.put(RowFact.FACT_ID, RandomString.nextWord());
        values.put(RowFact.REVIEW_ID, RandomReviewId.nextIdString());
        values.put(RowFact.LABEL, RandomString.nextWord());
        values.put(RowFact.VALUE, RandomString.nextWord());
        values.put(RowFact.IS_URL, RandomBoolean.nextBoolean());

        RowFact row = mFactory.newRow(RowFact.class, values);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowLocationWithDataLocationConstructor() {
        RowLocation row = mFactory.newRow(RowLocation.class, RandomReviewData.nextLocation(), INDEX);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowLocationWithRowValuesConstructor() {
        RowValuesForTest values = new RowValuesForTest();
        values.put(RowLocation.LOCATION_ID, RandomString.nextWord());
        values.put(RowLocation.REVIEW_ID, RandomReviewId.nextIdString());
        values.put(RowLocation.LATITUDE, RandomLatLng.nextLatLng().latitude);
        values.put(RowLocation.LONGITUDE, RandomLatLng.nextLatLng().longitude);
        values.put(RowLocation.NAME, RandomString.nextWord());

        RowLocation row = mFactory.newRow(RowLocation.class, values);

        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowImageWithDataImageConstructor() {
        //TODO do properly when android Bitmap utils are mocked
        DataImage image = new DatumImage(RandomReviewId.nextReviewId(), null,
                RandomDataDate.nextDateTime(), RandomString.nextSentence(), RandomBoolean.nextBoolean());
        RowImage row = mFactory.newRow(RowImage.class, image, INDEX);

        assertThat(row, not(nullValue()));
        //assertThat(row.hasData(mValidator), is(true));
    }

    @Test
    public void newRowImageWithRowValuesConstructor() {
        RowValuesForTest values = new RowValuesForTest();
        values.put(RowImage.IMAGE_ID, RandomString.nextWord());
        values.put(RowImage.REVIEW_ID, RandomReviewId.nextIdString());
        byte[] data = new byte[40];
        new Random().nextBytes(data);
        values.put(RowImage.BITMAP, new ByteArray(data));
        values.put(RowImage.CAPTION, RandomString.nextSentence());
        values.put(RowImage.IMAGE_DATE, RandomDataDate.nextDateTime().getTime());
        values.put(RowImage.IS_COVER, RandomBoolean.nextBoolean());

        RowImage row = mFactory.newRow(RowImage.class, values);

        assertThat(row, not(nullValue()));
        //assertThat(row.hasData(mValidator), is(true));
    }

    private <T extends DbTableRow> void checkEmptyRowConstruction(Class<T> rowClass) {
        T row = mFactory.emptyRow(rowClass);
        assertThat(row, not(nullValue()));
        assertThat(row.hasData(mValidator), is(false));
    }
}
