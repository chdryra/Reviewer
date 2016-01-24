/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdUrl;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.MdConverterUrl;
import com.chdryra.android.testutils.RandomString;

import java.net.MalformedURLException;
import java.net.URL;

import test.TestUtils.DataEquivalence;
import test.TestUtils.RandomReviewId;

import static junit.framework.Assert.fail;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterUrlTest extends MdConverterBasicTest<DataUrl, MdUrl> {
    public MdConverterUrlTest() {
        super(new MdConverterUrl());
    }

    @Override
    protected DataUrl newDatum() {
        return new Url();
    }

    @Override
    protected void checkDatumEquivalence(DataUrl datum, MdUrl mdDatum) {
        DataEquivalence.checkEquivalence(datum, mdDatum);
    }

    private static class Url implements DataUrl {
        private URL mUrl;
        private String mLabel;
        private ReviewId mId;

        private Url() {
            try {
                mUrl = new URL("http://www." + RandomString.nextWord() + ".co.uk");
            } catch (MalformedURLException e) {
                fail();
            }
            mId = RandomReviewId.nextReviewId();
            mLabel = RandomString.nextWord();
        }

        @Override
        public URL getUrl() {
            return mUrl;
        }

        @Override
        public String getLabel() {
            return mLabel;
        }

        @Override
        public String getValue() {
            return mUrl.toExternalForm();
        }

        @Override
        public boolean isUrl() {
            return true;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return true;
        }
    }
}
