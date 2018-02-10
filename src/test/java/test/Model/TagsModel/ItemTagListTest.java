/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.TagsModel;

import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.TagsModel.Implementation.ItemTagImpl;
import com.chdryra.android.corelibrary.TagsModel.Implementation.ItemTagList;
import com.chdryra.android.corelibrary.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.testutils.RandomString;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemTagListTest {
    @Test
    public void testGetItemTag() {
        ItemTagList list = new ItemTagList();

        assertThat(list.size(), is(0));
        ArrayList<ItemTag> tags = addTags(list);
        assertThat(list.size(), is(3));

        assertThat(list.getItemTag(0), is(tags.get(0)));
        assertThat(list.getItemTag(1), is(tags.get(1)));
        assertThat(list.getItemTag(2), is(tags.get(2)));
    }

    @Test
    public void doesNotAddTagsAlreadyHeldInList() {
        ItemTagList list = new ItemTagList();
        ArrayList<ItemTag> itemTags = addTags(list);
        list.add(itemTags.get(2));
        list.add(itemTags.get(2));
        list.add(itemTags.get(1));

        assertThat(list.size(), is(3));
    }

    @Test
    public void toStringArrayMatchesTagStrings() {
        ItemTagList list = new ItemTagList();
        ArrayList<ItemTag> itemTags = addTags(list);

        ArrayList<String> tags = list.toStringArray();
        assertThat(tags.size(), is(3));
        assertThat(tags.get(0), is(itemTags.get(0).getTag()));
        assertThat(tags.get(1), is(itemTags.get(1).getTag()));
        assertThat(tags.get(2), is(itemTags.get(2).getTag()));
    }

    @Test
    public void getItemTagReturnsCorrectTag() {
        ItemTagList list = new ItemTagList();
        ArrayList<ItemTag> itemTags = addTags(list);
        assertThat(list.getItemTag(itemTags.get(1).getTag()), is(itemTags.get(1)));
        assertThat(list.getItemTag(itemTags.get(0).getTag()), is(itemTags.get(0)));
        assertThat(list.getItemTag(itemTags.get(2).getTag()), is(itemTags.get(2)));
    }

    @Test
    public void removeRemovesCorrectItemTag() {
        ItemTagList list = new ItemTagList();
        ArrayList<ItemTag> itemTags = addTags(list);

        assertThat(list.size(), is(3));
        assertThat(list.contains(itemTags.get(0)), is(true));
        assertThat(list.contains(itemTags.get(1)), is(true));
        assertThat(list.contains(itemTags.get(2)), is(true));

        list.remove(itemTags.get(1));
        assertThat(list.size(), is(2));
        assertThat(list.contains(itemTags.get(0)), is(true));
        assertThat(list.contains(itemTags.get(1)), is(false));
        assertThat(list.contains(itemTags.get(2)), is(true));
    }

    @Test
    public void testIteratorHasNextAndNext() {
        ItemTagList list = new ItemTagList();
        ArrayList<ItemTag> itemTags = addTags(list);
        Iterator<ItemTag> iterator = list.iterator();
        int i = 0;
        while(iterator.hasNext()) {
            assertThat(iterator.next(), is(itemTags.get(i++)));
        }
    }

    @Test
    public void iteratorRemoveRemovesLastReturnedByOnNext() {
        ItemTagList list = new ItemTagList();
        ArrayList<ItemTag> itemTags = addTags(list);
        Iterator<ItemTag> iterator = list.iterator();
        iterator.next();
        iterator.next();
        iterator.remove();
        assertThat(list.size(), is(2));
        assertThat(list.getItemTag(1), is(itemTags.get(2)));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void iteratorRemoveThrowsExceptionIfNoNextCalled() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Have to call next() before remove()");
        ItemTagList list = new ItemTagList();
        addTags(list);
        Iterator<ItemTag> iterator = list.iterator();
        iterator.remove();
    }

    @Test
    public void iteratorThrowsExceptionIfNoElements() {
        expectedException.expect(NoSuchElementException.class);
        expectedException.expectMessage("No more elements left");
        ItemTagList list = new ItemTagList();
        addTags(list);
        Iterator<ItemTag> iterator = list.iterator();
        while(iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        assertThat(iterator.hasNext(), is(false));
        iterator.next();
    }

    @NonNull
    private ArrayList<ItemTag> addTags(ItemTagList list) {
        ArrayList<ItemTag> tags = new ArrayList<>();
        tags.add(new ItemTagImpl(RandomString.nextWordLowerCase(), RandomString.nextWordLowerCase()));
        tags.add(new ItemTagImpl(RandomString.nextWordLowerCase(), RandomString.nextWordLowerCase()));
        tags.add(new ItemTagImpl(RandomString.nextWordLowerCase(), RandomString.nextWordLowerCase()));
        list.add(tags.get(0));
        list.add(tags.get(1));
        list.add(tags.get(2));
        return tags;
    }
}
