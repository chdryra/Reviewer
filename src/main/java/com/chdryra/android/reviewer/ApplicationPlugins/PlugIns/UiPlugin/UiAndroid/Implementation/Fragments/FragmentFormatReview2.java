/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityFormatReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.AuthorNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CellDimensionsCalculator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CommentNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CoverNodeBannerUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.CriteriaNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.DateNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.FactsNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.HorizontalAdapterRef;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.HorizontalGridUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.LocationsNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuOptionsAppLevel;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.MenuUpAppLevel;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.RatingBarTouchable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.SubjectNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.TagsNodeUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.TextUi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.VhFactory;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.UiManagers.ViewUi;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuActionItem;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.OptionSelectListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiOptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Implementation.MaiUpAppLevel;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Implementation.OptionsCommand;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders.VhImage;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewParams;
import com.chdryra.android.reviewer.R;

/**
 * Created by: Rizwan Choudrey
 * On: 24/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class FragmentFormatReview2 extends Fragment implements ReviewNode.NodeObserver,
        OptionSelectListener {
    private static final String ID = TagKeyGenerator.getKey(FragmentFormatReview2.class,
            "ReviewId");
    private static final String PUBLISHED = TagKeyGenerator.getKey(FragmentFormatReview2.class,
            "published");

    private static final int LAYOUT = R.layout.fragment_review_formatted2;
    private static final int IMAGE = R.id.image_formatted;
    private static final int SUBJECT = R.id.subject_formatted;
    private static final int RATING = R.id.rating_formatted;
    private static final int HEADLINE = R.id.headline_formatted;
    private static final int AUTHOR = R.id.author_formatted;
    private static final int DATE = R.id.date_formatted;
    private static final int COMMENT = R.id.comment_formatted;
    private static final int TAGS = R.id.tags_formatted;
    private static final int CRITERIA = R.id.criteria_formatted;
    private static final int FACTS = R.id.facts_formatted;
    private static final int LOCATIONS = R.id.locations_formatted;
    private static final int IMAGES = R.id.images_formatted;
    private static final int TITLE = R.id.section_title;
    private static final int VALUE = R.id.section_value;

    private static final ReviewViewParams.CellDimension FULL
            = ReviewViewParams.CellDimension.FULL;
    private static final ReviewViewParams.CellDimension HALF
            = ReviewViewParams.CellDimension.HALF;

    private boolean mIsPublished = true;
    private ReviewNode mNode;
    private UiSuite mUi;
    private RepositorySuite mRepo;
    private MenuUi mMenu;

    private CoverNodeBannerUi mCover;
    private TextUi<TextView> mSubject;
    private RatingBarTouchable mRating;
    private TextUi<TextView> mAuthor;
    private TextUi<TextView> mDate;
    private CommentNodeUi mComment;
    private TagsNodeUi mTags;
    private CriteriaNodeUi mCriteria;
    private FactsNodeUi mFacts;
    private LocationsNodeUi mLocations;
    private ViewUi<RecyclerView, RefDataList<DataImage>> mImages;

    public static FragmentFormatReview2 newInstance(ReviewId nodeId, boolean isPublished) {
        //Can't use FactoryFragment as Support fragment rather than normal fragment
        Bundle args = new Bundle();
        args.putString(ID, nodeId.toString());
        args.putBoolean(PUBLISHED, isPublished);
        FragmentFormatReview2 fragment = new FragmentFormatReview2();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        mUi = app.getUi();
        mRepo = app.getRepository();

        Bundle args = getArguments();
        if (args == null) throwNoReview();
        mIsPublished = args.getBoolean(PUBLISHED);

        setNode(args);
        setMenu();

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(LAYOUT, container, false);

        setCover(v);
        setSubject(v);
        setRating(v);
        setAuthor(v);
        setDate(v);
        setComment(v);
        setTags(v);
        setCriteria(v);
        setFacts(v);
        setLocations(v);

        update();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, android.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mMenu.inflate(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return mMenu.onItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        update();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        update();
    }

    @Override
    public void onNodeChanged() {
        update();
    }

    @Override
    public void onTreeChanged() {
        update();
    }

    @Override
    public boolean onOptionSelected(int requestCode, String option) {
        return mMenu.onOptionSelected(requestCode, option);
    }

    private FactoryCommands getCommandsFactory() {
        return mUi.getCommandsFactory();
    }

    private void setNode(Bundle args) {
        String reviewId = args.getString(ID);
        if (reviewId == null) throwNoReview();
        try {
            ActivityFormatReview activity = (ActivityFormatReview) getActivity();
            mNode = activity.getNode(new DatumReviewId(reviewId));
            mNode.registerObserver(this);
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    private void throwNoReview() {
        throw new RuntimeException("No review found");
    }

    private void setMenu() {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getActivity());
        UiSuite ui = app.getUi();
        MenuActionItem<GvData> upAction = new MaiUpAppLevel<>(app);

        MenuAction<?> action;
        if (mIsPublished) {
            OptionsCommand command
                    = getCommandsFactory().newReviewOptionsSelector(new NodeAuthorId(mNode));
            MaiOptionsCommand<GvData> mai = new MaiOptionsCommand<>(command);
            action = new MenuOptionsAppLevel(Strings.Screens.FORMATTED, upAction, mai, ui);
        } else {
            action = new MenuUpAppLevel(Strings.Screens.PREVIEW, upAction, ui);
        }

        mMenu = new MenuUi(action);
    }

    private void update() {
        mCover.update();
        mSubject.update();
        mRating.update();
        mAuthor.update();
        mDate.update();
        mComment.update();
        mTags.update();
        mCriteria.update();
        mFacts.update();
        mLocations.update();
    }

    @Nullable
    private Command launchView(GvDataType<?> type) {
        return mIsPublished ? newLaunchViewCommand(type) : null;
    }

    private void setCover(View v) {
        CellDimensionsCalculator calculator = new CellDimensionsCalculator(getActivity());
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(FULL, HALF);

        Bitmap placeholder = BitmapFactory.decodeResource(getResources(),
                R.drawable.image_placeholder);
        ImageView image = (ImageView) v.findViewById(IMAGE);
        image.getLayoutParams().width = dims.getCellWidth();
        image.getLayoutParams().height = dims.getCellHeight();
        mCover = new CoverNodeBannerUi(image, mNode, placeholder);
    }

    private void setTags(final View v) {
        TextView tags = (TextView) v.findViewById(TAGS);
        mTags = new TagsNodeUi(tags, mNode);
        if(mIsPublished) mTags.setOnClickCommand(launchView(GvTag.TYPE));
    }

    private void setComment(View v) {
        TextView comment = (TextView) setTitleAndGetValueView(v, Strings.FORMATTED.COMMENT,
                COMMENT);
        mComment = new CommentNodeUi((TextView) v.findViewById(HEADLINE),
                comment, mNode, launchView(GvComment.TYPE));
    }

    private void setCriteria(View v) {
        LinearLayout section = (LinearLayout) setTitleAndGetSectionView(v, Strings.FORMATTED.CRITERIA, CRITERIA);
        mCriteria = new CriteriaNodeUi(section, VALUE, inflater(), mNode);
        if(mIsPublished) mCriteria.setOnClickCommand(launchView(GvCriterion.TYPE));
    }

    private LayoutInflater inflater() {
        return LayoutInflater.from(getActivity());
    }

    private void setFacts(View v) {
        LinearLayout section = (LinearLayout) setTitleAndGetSectionView(v, Strings.FORMATTED.FACTS, FACTS);
        mFacts = new FactsNodeUi(section, VALUE, inflater(), mNode);
        if(mIsPublished) mFacts.setOnClickCommand(launchView(GvFact.TYPE));
    }

    private void setLocations(View v) {
        LinearLayout section = (LinearLayout) setTitleAndGetSectionView(v, Strings.FORMATTED.LOCATIONS, LOCATIONS);
        mLocations = new LocationsNodeUi(section, VALUE, inflater(), mNode);
        if(mIsPublished) mLocations.setOnClickCommand(launchView(GvLocation.TYPE));
    }

    private void setAuthor(View v) {
        DataAuthorId authorId = mNode.getAuthorId();
        AuthorReference reference = mRepo.getAuthorsRepository().getReference(authorId);
        TextView authorView = (TextView) setTitleAndGetValueView(v, Strings.FORMATTED.AUTHOR, AUTHOR);
        mAuthor = new AuthorNodeUi(authorView, reference);
        if(mIsPublished) mAuthor.setOnClickCommand(launchAuthor());
    }

    private void setDate(View v) {
        TextView date = (TextView) setTitleAndGetValueView(v, Strings.FORMATTED.DATE, DATE);
        mDate = new DateNodeUi(date, mNode);
        if(mIsPublished) mDate.setOnClickCommand(launchAuthor());
    }

    private void setRating(View v) {
        Command onTouch = mIsPublished ? launchSummary() : null;
        mRating = new RatingBarTouchable((RatingBar) v.findViewById(RATING), mNode, onTouch);
    }

    private void setSubject(View v) {
        mSubject = new SubjectNodeUi((TextView) v.findViewById(SUBJECT), mNode, launchAuthor());
    }

    private void setImages(View v) {
        CellDimensionsCalculator calculator = new CellDimensionsCalculator(getActivity());
        CellDimensionsCalculator.Dimensions dims = calculator.calcDimensions(HALF, HALF);
        mImages = newGridUi((RecyclerView)v.findViewById
                        (IMAGES), VhImage.class, 1, dims,
                mUi.getGvConverter().newConverterImages(),
                new ViewUi.ValueGetter<RefDataList<DataImage>>() {
                    @Override
                    public RefDataList<DataImage> getValue() {
                        return mNode.getImages();
                    }
                });
    }

    @NonNull
    private <T1 extends HasReviewId, T2 extends GvData, Vh extends ViewHolder>
    ViewUi<RecyclerView, RefDataList<T1>>
    newGridUi(RecyclerView view, Class<Vh> vhClass,
              int span, CellDimensionsCalculator.Dimensions dims,
              DataConverter<T1, T2, ? extends GvDataList<T2>> converter,
              ViewUi.ValueGetter<RefDataList<T1>> reference) {
        IdableDataList<T1> empty = new IdableDataList<>(reference.getValue().getReviewId());
        GvDataType<T2> dataType = converter.convert(empty).getGvDataType();

        Command onClick = mIsPublished ? newLaunchViewCommand(dataType) : null;
        HorizontalAdapterRef<T1, T2, Vh> adapter
                = new HorizontalAdapterRef<>(reference, converter, new VhFactory<>(vhClass), dims);
        return new HorizontalGridUi<>(getActivity(), view, adapter, span, onClick);
    }

    private View setTitleAndGetValueView(View v, String sectionTitle, int sectionView) {
        return setTitleAndGetSectionView(v, sectionTitle, sectionView).findViewById(VALUE);
    }

    @NonNull
    private View setTitleAndGetSectionView(View v, String sectionTitle, int sectionView) {
        View view = v.findViewById(sectionView);
        TextView titleView = (TextView) view.findViewById(TITLE);
        titleView.setText(sectionTitle);
        return view;
    }

    private Command newLaunchViewCommand(GvDataType<?> dataType) {
        return getCommandsFactory().newLaunchViewCommand(mUi.newDataView(mNode, dataType));
    }

    private Command launchAuthor() {
        return mUi.getCommandsFactory().newLaunchAuthorCommand(mNode.getAuthorId());
    }

    private Command launchSummary() {
        return mUi.getCommandsFactory().newLaunchSummaryCommand(mNode.getReviewId());
    }

    private static class NodeAuthorId extends DatumAuthorId {
        private ReviewNode mNode;

        public NodeAuthorId(ReviewNode node) {
            super(node.getReviewId(), node.getAuthorId().toString());
            mNode = node;
        }

        @Override
        public ReviewId getReviewId() {
            return mNode.getReviewId();
        }

        @Override
        public String toString() {
            return mNode.getAuthorId().toString();
        }
    }
}
