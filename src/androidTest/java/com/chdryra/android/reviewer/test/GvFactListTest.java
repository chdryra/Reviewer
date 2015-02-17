/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvFactListTest extends TestCase {
    private static final int NUM = 50;
    private GvFactList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvFactList.TYPE, mList.getGvType());
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 50; ++i) {
            mList.add(GvDataMocker.newFact());
        }

        assertEquals(50, mList.size());

        Random rand = new Random();
        for (int i = 0; i < 50; ++i) {
            int item = rand.nextInt(9);
            String label = mList.getItem(item).getLabel();
            String value = RandomString.nextWord();
            mList.add(new GvFactList.GvFact(label, value));
        }

        mList.sort();
        GvFactList.GvFact prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvFactList.GvFact next = mList.getItem(i);
            String prevLabel = prev.getLabel();
            String prevValue = prev.getValue();
            String nextLabel = next.getLabel();
            String nextValue = next.getValue();
            if (nextLabel.equals(prevLabel)) {
                assertTrue(prevValue.compareTo(nextValue) <= 0);
            } else {
                assertTrue(prevLabel.compareTo(nextLabel) < 0);
            }

            prev = next;
        }
    }

    @SmallTest
    public void testGvFact() {
        GvFactList.GvFact fact1 = GvDataMocker.newFact();
        GvFactList.GvFact fact2 = GvDataMocker.newFact();

        String label1 = fact1.getLabel();
        String value1 = fact1.getValue();
        String label2 = fact2.getLabel();
        String value2 = fact2.getValue();

        GvFactList.GvFact gvFact = new GvFactList.GvFact(label1, value1);
        GvFactList.GvFact gvFactEquals = new GvFactList.GvFact(label1,
                value1);
        GvFactList.GvFact gvFactNotEquals1 = new GvFactList.GvFact
                (label1, value2);
        GvFactList.GvFact gvFactNotEquals2 = new GvFactList.GvFact
                (label2, value1);
        GvFactList.GvFact gvFactNotEquals3 = new GvFactList.GvFact
                (label2, value2);
        GvFactList.GvFact gvFactNull = new GvFactList.GvFact();
        GvFactList.GvFact gvFactEmpty1 = new GvFactList.GvFact(label1, "");
        GvFactList.GvFact gvFactEmpty2 = new GvFactList.GvFact("", value1);
        GvFactList.GvFact gvFactEmpty3 = new GvFactList.GvFact("", "");

        assertNotNull(gvFact.newViewHolder());
        assertTrue(gvFact.isValidForDisplay());

        assertEquals(label1, gvFact.getLabel());
        assertEquals(value1, gvFact.getValue());

        assertTrue(gvFact.equals(gvFactEquals));
        assertFalse(gvFact.equals(gvFactNotEquals1));
        assertFalse(gvFact.equals(gvFactNotEquals2));
        assertFalse(gvFact.equals(gvFactNotEquals3));

        assertFalse(gvFactNull.isValidForDisplay());
        assertFalse(gvFactEmpty1.isValidForDisplay());
        assertFalse(gvFactEmpty2.isValidForDisplay());
        assertFalse(gvFactEmpty3.isValidForDisplay());
    }

    @SmallTest
    public void testEquals() {
        mList.add(GvDataMocker.newFactList(NUM));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.CHILDREN, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.TAGS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.LOCATIONS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.COMMENTS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.FACTS, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.IMAGES, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvDataList.GvType.URLS, NUM)));

        GvFactList list = new GvFactList();
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        list.add(mList);
        assertFalse(mList.equals(list));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvFactList();
    }
}
