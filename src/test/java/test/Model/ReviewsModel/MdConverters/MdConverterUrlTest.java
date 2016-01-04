package test.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdUrl;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.MdConverterUrl;
import com.chdryra.android.testutils.RandomString;

import java.net.MalformedURLException;
import java.net.URL;

import test.TestUtils.RandomReviewId;

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

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
        //Don't test urls are equal as extremely slow. Value check good enough.
        assertThat(mdDatum.getReviewId().toString(), is(datum.getReviewId().toString()));
        assertThat(mdDatum.getLabel(), is(datum.getLabel()));
        assertThat(mdDatum.getValue(), is(datum.getValue()));
        assertThat(mdDatum.isUrl(), is(datum.isUrl()));
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
