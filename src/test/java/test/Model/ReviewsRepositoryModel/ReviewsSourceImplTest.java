/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Model.ReviewsRepositoryModel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.Collections.SortableListImpl;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdConverter;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters.ConverterMd;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsSourceImpl;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackReviewsSource;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;
import com.chdryra.android.testutils.RandomString;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Random;

import test.TestUtils.RandomReview;
import test.TestUtils.RandomReviewId;
import test.TestUtils.StaticReviewsRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by: Rizwan Choudrey
 * On: 22/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewsSourceImplTest {
    private static final int NUM = 5;
    private static final DataAuthor AUTHOR = new DatumAuthor("Author", AuthorId.generateId());
    private static final Random RAND = new Random();

    @Mock
    private TagsManager mMockManager;
    private IdableCollection<Review> mReviews;
    private ReviewsRepository mRepo;
    private ReviewsSource mSource;

    @Before
    public void setUp() {
        mReviews = new IdableDataCollection<>();
        for (int i = 0; i < NUM; ++i) {
            mReviews.add(RandomReview.nextReview());
        }
        mRepo = new StaticReviewsRepository(mReviews, mMockManager);
        mSource = new ReviewsSourceImpl(mRepo, getReviewFactory());
    }

    @Test
    public void asMetaReview_ReviewId_ReturnsNullIfNoReviewFound() {
        mSource.asMetaReview(RandomReviewId.nextReviewId(), new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertThat(review , is(nullValue()));
            }
        });
    }

    @Test
    public void asMetaReview_ReviewId_ReturnsMetaReviewWithOneChildOnly() {
        Review review = getRandomReview();
        mSource.asMetaReview(review.getReviewId(), new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertNumChildren(review, 1);
            }
        });
    }

    @Test
    public void asMetaReview_ReviewId_ReturnsMetaReviewWithCorrectChildNode() {
        final Review expectedReview = getRandomReview();
        mSource.asMetaReview(expectedReview.getReviewId(), new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertNotNull(review);
                assertCorrectReview(review.getChildren().getItem(0), expectedReview);
            }
        });
    }

    @Test
    public void asMetaReview_Data_ReturnsNullIfNoReviewFound() {
        VerboseDatum datum = new VerboseDatum(RandomReviewId.nextReviewId());
        mSource.asMetaReview(datum, "", new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertThat(review, is(nullValue()));
            }
        });
    }

    @Test
    public void asMetaReview_Data_ReturnsMetaReviewWithOneChildOnlyForDatum() {
        Review review = getRandomReview();
        VerboseDatum datum = new VerboseDatum(review.getReviewId());
        mSource.asMetaReview(datum, "", new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertNumChildren(review, 1);
            }
        });
    }

    @Test
    public void asMetaReview_Data_ReturnsMetaReviewWithCorrectChildNodeForDatum() {
        final Review expectedReview = getRandomReview();
        VerboseDatum datum = new VerboseDatum(expectedReview.getReviewId());
        mSource.asMetaReview(datum, "", new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertNotNull(review);
                assertCorrectReview(review.getChildren().getItem(0), expectedReview);
            }
        });

    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithOneChildOnlyForDataCollectionWithReviewIdInSource
            () {
        ReviewId id = getRandomReview().getReviewId();
        VerboseCollection collection = new VerboseCollection(id, RandomString.nextWord());
        for (int i = 0; i < NUM; ++i) {
            collection.add(new VerboseDatum(RandomReviewId.nextReviewId()));
        }

        mSource.asMetaReview(collection, "", new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertNumChildren(review, 1);
            }
        });
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithCorrectChildNodeForDataCollectionWithReviewIdInSource() {
        final Review expectedReview = getRandomReview();
        ReviewId id = expectedReview.getReviewId();
        VerboseCollection collection = new VerboseCollection(id, RandomString.nextWord());
        for (int i = 0; i < NUM; ++i) {
            collection.add(new VerboseDatum(RandomReviewId.nextReviewId()));
        }

        mSource.asMetaReview(collection, RandomString.nextWord(), new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertNotNull(review);
                assertCorrectReview(review.getChildren().getItem(0), expectedReview);
            }
        });
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithCorrectNumberChildrenForDataCollectionWithReviewIdNotInSource
            () {
        VerboseCollection collection = getItemsDataCollection();
        assertThat(collection.size(), is(mReviews.size() + 1));
        mSource.asMetaReview(collection, "", new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertNumChildren(review, mReviews.size());
            }
        });
    }

    @Test
    public void
    asMetaReview_Data_ReturnsMetaReviewWithCorrectReviewForDataCollectionWithReviewIdNotInSource
            () {
        VerboseCollection collection = getItemsDataCollection();
        final String subject = RandomString.nextWord();

        mSource.asMetaReview(collection, subject, new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertNotNull(review);
                assertNodeHasCorrectData(subject, review);
            }
        });
    }

    @Test
    public void
    getMetaReviewReturnsNullIfDataDoesNotHaveReviewIdsInSource() {
        final VerboseCollection collection = new VerboseCollection(RandomReviewId.nextReviewId(), "");
        for(int i = 0; i < NUM; ++i) {
            collection.add(new VerboseDatum(RandomReviewId.nextReviewId()));
        }

        mSource.getMetaReview(collection, "", new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertThat(review, is(nullValue()));
            }
        });
    }

    @Test
    public void
    getMetaReviewReturnsMetaReviewWithCorrectNumberChildren() {
        VerboseCollection collection = getItemsDataCollection();
        assertThat(collection.size(), is(mReviews.size() + 1));
        mSource.getMetaReview(collection, "", new CallbackReviewsSource() {
                    @Override
                    public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                        assertNumChildren(review, mReviews.size());
                    }
                });
    }

    @Test
    public void
    getMetaReviewReturnsMetaReviewWithCorrectReview() {
        VerboseCollection collection = getItemsDataCollection();
        final String subject = RandomString.nextWord();

        mSource.getMetaReview(collection, subject, new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                assertNotNull(review);
                assertNodeHasCorrectData(subject, review);
            }
        });
    }

    @Test
    public void getFlattenedMetaReview() {
        fail(); //TODO write test when flattening strategy more concrete
    }


    @Test
    public void getReviewReturnsNullIfReviewNotFound() {
        mSource.getReview(RandomReviewId.nextReviewId(), new CallbackRepository() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
                assertThat(review, is(nullValue()));
            }

            @Override
            public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {

            }
        });
    }

    @Test
    public void getReviewReturnsCorrectReviewIfFound() {
        Review review = getRandomReview();
        mSource.getReview(review.getReviewId(), new CallbackRepository() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
                assertThat(review, is(review));
            }

            @Override
            public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {

            }
        });
    }

    @Test
    public void getReviewsReturnsReviewsInRepository() {
        mSource.getReviews(new CallbackRepository() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {

            }

            @Override
            public void onFetchedFromRepo(final Collection<Review> fromSource, CallbackMessage result) {
                mRepo.getReviews(new CallbackRepository() {
                    @Override
                    public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
                    }

                    @Override
                    public void onFetchedFromRepo(Collection<Review> fromRepo, CallbackMessage result) {
                        assertThat(fromSource, is(fromRepo));
                    }
                });
            }
        });
    }

    @Test
    public void getTagsManagerReturnsManager() {
        assertThat(mSource.getTagsManager(), is(mMockManager));
    }

    @Test
    public void registerUnregisterObserverDelegatesToRepository() {
        ReviewsRepository repo = mock(ReviewsRepository.class);
        ReviewsSource source = new ReviewsSourceImpl(repo, getReviewFactory());
        ReviewsRepositoryObserver observer = mock(ReviewsRepositoryObserver.class);
        source.registerObserver(observer);
        verify(repo).registerObserver(observer);
        source.unregisterObserver(observer);
        verify(repo).unregisterObserver(observer);
    }

    @NonNull
    private VerboseCollection getItemsDataCollection() {
        ReviewId id = RandomReviewId.nextReviewId();
        VerboseCollection collection = new VerboseCollection(id, RandomString.nextWord());
        for (Review review : mReviews) {
            collection.add(new VerboseDatum(review.getReviewId()));
        }
        collection.add(new VerboseDatum(getRandomReview().getReviewId()));
        return collection;
    }

    @NonNull
    private FactoryReviews getReviewFactory() {
        ConverterMd converter = new FactoryMdConverter().newMdConverter();
        FactoryReviews factoryReviews = new FactoryReviews(new FactoryReviewNode(), converter, new DataValidator());
        factoryReviews.setAuthorsStamp(new AuthorsStamp(AUTHOR));
        return factoryReviews;
    }

    private Review getRandomReview() {
        int index = RAND.nextInt(mReviews.size());
        return mReviews.getItem(index);
    }

    private void assertNodeHasCorrectData(String subject, ReviewNode node) {
        IdableList<ReviewNode> children = node.getChildren();
        float averageRating = 0;
        int numChildren = children.size();
        for (int i = 0; i < numChildren; ++i) {
            ReviewNode child = children.getItem(i);
            assertCorrectReview(child, mReviews.getItem(i));
            averageRating += child.getRating().getRating() / numChildren;
        }

        assertThat(node.getSubject().getSubject(), is(subject));
        assertThat((double)node.getRating().getRating(), closeTo((double)averageRating, 0.001));
        assertThat(node.getRating().getRatingWeight(), is(numChildren));
    }

    private void assertNumChildren(@Nullable ReviewNode node, int num) {
        assertNotNull(node);
        assertThat(node.getParent(), is(nullValue()));
        assertThat(node.getChildren().size(), is(num));
        for (int i = 0; i < num; ++i) {
            ReviewNode child = node.getChildren().getItem(i);
            assertThat(child.getChildren().size(), is(0));
        }
    }

    private void assertCorrectReview(@Nullable ReviewNode node, Review expectedReview) {
        assertNotNull(node);
        assertThat(node.getReview(), is(expectedReview));
        assertThat(node.getSubject(), is(expectedReview.getSubject()));
        assertThat(node.getRating(), is(expectedReview.getRating()));
    }

    private class VerboseDatum implements VerboseDataReview {
        private ReviewId mId;
        private String mSummary;

        public VerboseDatum(ReviewId id) {
            this(id, RandomString.nextWord());
        }

        public VerboseDatum(ReviewId id, String summary) {
            mId = id;
            mSummary = summary;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public String getStringSummary() {
            return mSummary;
        }

        @Override
        public boolean hasElements() {
            return false;
        }

        @Override
        public boolean isVerboseCollection() {
            return false;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return true;
        }
    }

    private class VerboseCollection extends SortableListImpl<VerboseDatum>
            implements VerboseIdableCollection<VerboseDatum>, VerboseDataReview {
        private ReviewId mId;
        private String mSummary;

        public VerboseCollection(ReviewId id, String summary) {
            mId = id;
            mSummary = summary;
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public String getStringSummary() {
            return mSummary;
        }

        @Override
        public boolean hasElements() {
            return size() > 0;
        }

        @Override
        public boolean isVerboseCollection() {
            return true;
        }

        @Override
        public boolean hasData(DataValidator dataValidator) {
            return hasElements();
        }
    }
}
