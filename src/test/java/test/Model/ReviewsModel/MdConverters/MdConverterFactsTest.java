package test.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdFact;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdUrl;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.MdConverterFacts;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters.MdConverterUrl;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import test.TestUtils.DataEquivalence;
import test.TestUtils.RandomReviewId;

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 04/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class MdConverterFactsTest extends MdConverterBasicTest<DataFact, MdFact> {
    public MdConverterFactsTest() {
        super(new MdConverterFacts(new MdConverterUrl()));
    }

    @Test
    public void ifDataFactIsUrlReturnMdUrl() {
        MdConverterFacts converter = (MdConverterFacts) getConverter();
        DataUrl url = new FactUrl();
        MdFact fact = converter.convert(url);
        assertThat(fact instanceof MdUrl, is(true));
        assertThat(fact.isUrl(), is(true));
        assertThat(fact.getReviewId().toString(), is(url.getReviewId().toString()));
        assertThat(fact.getLabel(), is(url.getLabel()));
        assertThat(fact.getValue(), is(url.getUrl().toExternalForm()));
    }

// This doesn't work as URLUtil.guessUrl(.) is not mocked yet by Android.....
//    @Test
//    public void ifDataFactIsUrlButDoesNotParseThenReturnMdFact() {
//        MdConverterFacts converter = (MdConverterFacts) getConverter();
//        DataFact url = new Fact(true);
//        MdFact fact = converter.convert(url);
//        assertThat(fact instanceof MdUrl, is(false));
//        assertThat(fact.isUrl(), is(false));
//        assertThat(fact.getReviewId().toString(), is(url.getReviewId().toString()));
//        assertThat(fact.getLabel(), is(url.getLabel()));
//        assertThat(fact.getValue(), is(url.getValue()));
//    }

    @Override
    protected DataFact newDatum() {
        return new Fact();
    }

    @Override
    protected void checkDatumEquivalence(DataFact datum, MdFact mdDatum) {
        DataEquivalence.checkEquivalence(datum, mdDatum);
    }

    private static class FactUrl extends Fact implements DataUrl {
        private URL mUrl;

        public FactUrl() {
            try {
                mUrl = new URL("http://www." + RandomString.nextWord() + ".co.uk");
            } catch (MalformedURLException e) {
                fail();
            }
        }

        @Override
        public URL getUrl() {
            return mUrl;
        }

        @Override
        public String getLabel() {
            return mUrl.toExternalForm();
        }

        @Override
        public boolean isUrl() {
            return true;
        }
    }

    private static class Fact implements DataFact {
        private String mLabel;
        private String mValue;
        private ReviewId mId;
        private boolean mIsUrl = false;

        private Fact() {
            mId = RandomReviewId.nextReviewId();
            mLabel = RandomString.nextWord();
            mValue = RandomString.nextWord();
        }

        private Fact(boolean fakeUrl) {
            this();
            mIsUrl = fakeUrl;
        }

        @Override
        public String getLabel() {
            return mLabel;
        }

        @Override
        public String getValue() {
            return mValue;
        }

        @Override
        public boolean isUrl() {
            return mIsUrl;
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
