package com.chdryra.android.reviewer.test.Adapter.ReviewAdapterModel;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomAuthor;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.RandomString;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataBuilderAdapterTest extends AndroidTestCase {
    private static final int NUM = 3;
    private static final ArrayList<GvDataType<? extends GvData>> TYPES = GvDataMocker.TYPES;

    private Author mAuthor;
    private TagsManager mTagsManager;
    private ReviewBuilder mBuilder;
    private ReviewBuilderAdapter mAdapter;

    @SmallTest
    public void testGetDataType() {
        for (GvDataType dataType : TYPES) {
            assertEquals(dataType, getBuilder(dataType).getDataType());
        }
    }


    @SmallTest
    public void testGetParentBuilder() {
        for (GvDataType dataType : TYPES) {
            assertEquals(mAdapter, getBuilder(dataType).getParentBuilder());
        }
    }

    @SmallTest
    public void testSetGetSubject() {
        assertEquals("", mAdapter.getSubject());
        for (GvDataType dataType : TYPES) {
            String subject = RandomString.nextWord();
            ReviewBuilderAdapter.DataBuilderAdapter adapter = getBuilder(dataType);
            assertEquals("", adapter.getSubject());
            mAdapter.setSubject(subject);
            assertEquals(subject, adapter.getSubject());
            mAdapter.setSubject("");
            assertEquals("", adapter.getSubject());
            adapter.setSubject(subject);
            assertEquals(subject, adapter.getSubject());
            assertEquals(subject, mAdapter.getSubject());
            mAdapter.setSubject("");
        }
    }

    @SmallTest
    public void testSetGetRating() {
        assertEquals(0f, mAdapter.getRating());
        for (GvDataType dataType : TYPES) {
            float rating = RandomRating.nextRating();
            ReviewBuilderAdapter.DataBuilderAdapter adapter = getBuilder(dataType);
            assertEquals(0f, adapter.getRating());
            mAdapter.setRating(rating);
            assertEquals(rating, adapter.getRating());
            mAdapter.setRating(0f);
            assertEquals(0f, adapter.getRating());
            adapter.setRating(rating);
            assertEquals(rating, adapter.getRating());
            assertEquals(rating, mAdapter.getRating());
            mAdapter.setRating(0f);
        }
    }

    @SmallTest
    public void testIsRatingAverage() {

        for (GvDataType dataType : TYPES) {
            ReviewBuilderAdapter.DataBuilderAdapter adapter = getBuilder(dataType);
            mAdapter.setRatingIsAverage(false);
            assertFalse(adapter.isRatingAverage());
            mAdapter.setRatingIsAverage(true);
            assertTrue(adapter.isRatingAverage());
            mAdapter.setRatingIsAverage(false);
            assertFalse(adapter.isRatingAverage());
            adapter.setRatingIsAverage(true);
            assertTrue(adapter.isRatingAverage());
        }
    }

    @SmallTest
    public void testGetAverageRating() {
        mAdapter.setRatingIsAverage(false);
        GvCriterionList criteria;
        do {
            criteria = GvDataMocker.newChildList(NUM, false);
        } while (criteria.getAverageRating() == mAdapter.getRating());
        setBuilderData(criteria);

        for (GvDataType dataType : TYPES) {
            ReviewBuilderAdapter.DataBuilderAdapter builder = mAdapter.getDataBuilder(dataType);
            assertEquals(criteria.getAverageRating(), builder.getAverageRating());
        }

        //Change criteria data
        ReviewBuilderAdapter.DataBuilderAdapter<GvCriterionList.GvCriterion> criteriaBuilder =
                mAdapter.getDataBuilder(GvCriterionList.GvCriterion.TYPE);
        GvCriterionList criteria2;
        do {
            criteria2 = GvDataMocker.newChildList(NUM, false);
        } while (criteria.getAverageRating() == criteria2.getAverageRating());
        for (int i = 0; i < criteria2.size(); ++i) {
            GvCriterionList.GvCriterion datum = criteria2.getItem(i);
            assertTrue(criteriaBuilder.add(datum));
        }

        for (GvDataType dataType : TYPES) {
            ReviewBuilderAdapter.DataBuilderAdapter builder = mAdapter.getDataBuilder(dataType);
            if(dataType == GvCriterionList.GvCriterion.TYPE) {
                assertEquals(criteria2.getAverageRating(), builder.getAverageRating());
            } else {
                assertEquals(criteria.getAverageRating(), builder.getAverageRating());
            }
        }

        criteriaBuilder.setData();
        for (GvDataType dataType : TYPES) {
            ReviewBuilderAdapter.DataBuilderAdapter builder = mAdapter.getDataBuilder(dataType);
            assertEquals(criteria2.getAverageRating(), builder.getAverageRating());
        }
    }

    @SmallTest
    public void testGetCovers() {
        GvImageList images = GvDataMocker.newImageList(NUM, false);
        GvImageList newImages = GvDataMocker.newImageList(NUM, false);
        setBuilderData(images);

        ReviewBuilderAdapter.DataBuilderAdapter<GvImageList.GvImage> imageBuilder =
                mAdapter.getDataBuilder(GvImageList.GvImage.TYPE);
        imageBuilder.deleteAll();
        for (GvImageList.GvImage image : newImages) {
            imageBuilder.add(image);
        }

        assertEquals(1, mAdapter.getCovers().size());
        assertEquals(images.getItem(0), mAdapter.getCovers().getItem(0));
        for (GvDataType dataType : TYPES) {
            ReviewBuilderAdapter.DataBuilderAdapter builder = mAdapter.getDataBuilder(dataType);
            assertEquals(1, builder.getCovers().size());
            if (dataType == GvImageList.GvImage.TYPE) {
                assertEquals(newImages.getItem(0), builder.getCovers().getItem(0));
            } else {
                assertEquals(images.getItem(0), builder.getCovers().getItem(0));
            }
        }
    }

    @SmallTest
    public void testGetSetData() {
        for (GvDataType dataType : TYPES) {
            assertEquals(0, getBuilderData(dataType).size());
        }

        for (GvDataType dataType : TYPES) {
            GvDataList data = GvDataMocker.getData(dataType, NUM);
            setBuilderData(data);
            assertEquals(data, getBuilderData(dataType));
        }
    }

    private GvDataList getBuilderData(GvDataType dataType) {
        return mAdapter.getDataBuilder(dataType).getGridData();
    }

    private <T extends GvData> void setBuilderData(GvDataList<T> data) {
        ReviewBuilderAdapter.DataBuilderAdapter<T> builder = mAdapter.getDataBuilder(data
                .getGvDataType());
        for (int i = 0; i < data.size(); ++i) {
            T datum = data.getItem(i);
            assertTrue(builder.add(datum));
        }
        builder.setData();
    }

    private <T extends GvData> ReviewBuilderAdapter.DataBuilderAdapter<T> getBuilder(GvDataType<T> dataType) {
        return mAdapter.getDataBuilder(dataType);
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mAuthor = RandomAuthor.nextAuthor();
        mTagsManager = new TagsManager();
        mBuilder = new ReviewBuilder(getContext(), mAuthor, mTagsManager);
        mAdapter = new ReviewBuilderAdapter(mBuilder);
    }
}

