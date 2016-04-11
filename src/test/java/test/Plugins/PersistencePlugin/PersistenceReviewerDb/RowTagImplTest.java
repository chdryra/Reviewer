/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.PersistenceReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.RowTagImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.testutils.RandomString;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowTagImplTest extends RowTableBasicTest<RowTag, RowTagImpl>{
    private static final int NUM = 10;

    public RowTagImplTest() {
        super(RowTag.TAG.getName(), 2);
    }

    @Test
    public void constructionWithItemTagAndGetters() {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < NUM; i++) {
            ids.add(RandomString.nextWord());
        }
        String tagString = RandomString.nextWord();
        ItemTag tag = new Tag(tagString, ids);

        RowTagImpl row = new RowTagImpl(tag);
        assertThat(row.getTag(), is(tagString));
        assertThat(row.getReviewIds(), is(ids));
    }

    @Test
    public void constructionWithRowValuesAndGetters() {
        RowTag reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowTag.TAG, reference.getTag());
        values.put(RowTag.REVIEWS, StringUtils.join(reference.getReviewIds().toArray(), ","));

        RowTagImpl row = new RowTagImpl(values);
        assertThat(row.hasData(new DataValidator()), is(true));

        assertThat(row.getTag(), is(reference.getTag()));
        assertThat(row.getReviewIds(), is(reference.getReviewIds()));
    }

    @Test
    public void constructionWithItemTagWithInvalidTagMakesRowTagInvalid() {
        ItemTag tag = new Tag("", RandomString.nextWord());
        RowTagImpl row = new RowTagImpl(tag);
        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithItemTagWithInvalidTaggedIdsMakesRowTagInvalid() {
        ItemTag tag = new Tag(RandomString.nextWord(), "");
        RowTagImpl row = new RowTagImpl(tag);
        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithValidItemTagMakesRowTagValid() {
        ItemTag tag = new Tag(RandomString.nextWord(), RandomString.nextWord());
        RowTagImpl row = new RowTagImpl(tag);
        assertThat(row.hasData(new DataValidator()), is(true));
    }

    @Test
    public void constructionWithTagAndGetters() {
        String tagString = RandomString.nextWord();
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < NUM; i++) {
            ids.add(RandomString.nextWord());
        }
        ItemTag tag = new Tag(tagString, ids);

        RowTagImpl row = new RowTagImpl(tag);

        assertThat(row.getTag(), is(tagString));
        assertThat(row.getReviewIds(), is(ids));
    }

    @Test
    public void iteratorReturnsDataInOrder() {
        RowTagImpl row = newRow();

        ArrayList<RowEntry<RowTag, ?>> entries = getRowEntries(row);

        assertThat(entries.size(), is(2));

        checkEntry(entries.get(0), RowTag.TAG, row.getTag());
        checkEntry(entries.get(1), RowTag.REVIEWS, StringUtils.join(row.getReviewIds().toArray(),
                ","));
    }

    @NonNull
    @Override
    protected RowTagImpl newRow() {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < NUM; i++) {
            ids.add(RandomString.nextWord());
        }
        String tagString = RandomString.nextWord();
        ItemTag tag = new Tag(tagString, ids);

        return new RowTagImpl(tag);
    }

    @Override
    protected String getRowId(RowTagImpl row) {
        return row.getTag();
    }

    private class Tag implements ItemTag {
        String mTag;
        ArrayList<String> mIds;

        public Tag(String tag, ArrayList<String> ids) {
            mTag = tag;
            mIds = ids;
        }

        public Tag(String tag, String id) {
            mTag = tag;
            mIds = new ArrayList<>();
            mIds.add(id);
        }

        @Override
        public ArrayList<String> getItemIds() {
            return mIds;
        }

        @Override
        public String getTag() {
            return mTag;
        }

        @Override
        public boolean tagsItem(String itemId) {
            return mIds.contains(itemId);
        }

        @Override
        public boolean isTag(String tag) {
            return mTag.equals(tag);
        }

        @Override
        public int compareTo(@NotNull ItemTag another) {
            return mTag.compareToIgnoreCase(another.getTag());
        }
    }
}
