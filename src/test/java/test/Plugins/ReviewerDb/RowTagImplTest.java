package test.Plugins.ReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTag;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.RowTagImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces.RowTag;
import com.chdryra.android.testutils.RandomString;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowTagImplTest {
    private static final int NUM = 10;
    @Rule
    public ExpectedException mExpectedException = ExpectedException.none();

    @Test
    public void constructionWithRowValues() {
        RowTag reference = getRowToIterateOver();

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
    public void getRowIdReturnsTagString() {
        String tagString = RandomString.nextWord();
        ItemTag tag = new Tag(tagString, RandomString.nextWord());
        RowTagImpl row = new RowTagImpl(tag);
        assertThat(row.getRowId(), is(tagString));
    }

    @Test
    public void getRowIdColumnNameReturnsRowTag_TagColumnName() {
        ItemTag tag = new Tag(RandomString.nextWord(), RandomString.nextWord());
        RowTagImpl row = new RowTagImpl(tag);
        assertThat(row.getRowIdColumnName(), is(RowTag.TAG.getName()));
    }

    @Test
    public void iteratorIsSize2() {
        RowTagImpl row = getRowToIterateOver();
        Iterator<RowEntry<?>> it = row.iterator();
        int i = 0;
        while (it.hasNext()) {
            it.next();
            ++i;
        }
        assertThat(i, is(2));
    }

    @Test
    public void iteratorThrowsNoElementExceptionAfterTooManyNexts() {
        mExpectedException.expect(NoSuchElementException.class);
        RowTagImpl row = getRowToIterateOver();
        Iterator<RowEntry<?>> it = row.iterator();
        while (it.hasNext()) it.next();
        it.next();
    }

    @Test
    public void iteratorThrowsUnsupportedOperationExceptionOnRemove() {
        mExpectedException.expect(UnsupportedOperationException.class);
        RowTagImpl row = getRowToIterateOver();
        Iterator<RowEntry<?>> it = row.iterator();
        it.remove();
    }

    @Test
    public void iteratorReturnsTagThenIds() {
        RowTagImpl row = getRowToIterateOver();

        ArrayList<RowEntry<?>> entries = new ArrayList<>();
        for (RowEntry<?> entry : row) {
            entries.add(entry);
        }

        assertThat(entries.size(), is(2));

        RowEntry<?> entry1 = entries.get(0);
        assertThat(entry1.getColumnName(), is(RowTag.TAG.getName()));
        assertThat(entry1.getEntryType().equals(RowTag.TAG.getType()), is(true));
        assertThat((String) entry1.getValue(), is(row.getTag()));

        RowEntry<?> entry2 = entries.get(1);
        assertThat(entry2.getColumnName(), is(RowTag.REVIEWS.getName()));
        assertThat(entry2.getEntryType().equals(RowTag.REVIEWS.getType()), is(true));
        String reviewIds = (String) entry2.getValue();
        ArrayList<String> ids = new ArrayList<>(Arrays.asList(reviewIds.split(",")));
        assertThat(ids, is(row.getReviewIds()));
    }

    @NonNull
    private RowTagImpl getRowToIterateOver() {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < NUM; i++) {
            ids.add(RandomString.nextWord());
        }
        String tagString = RandomString.nextWord();
        ItemTag tag = new Tag(tagString, ids);

        return new RowTagImpl(tag);
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
