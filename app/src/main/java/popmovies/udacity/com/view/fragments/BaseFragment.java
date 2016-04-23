package popmovies.udacity.com.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import popmovies.udacity.com.R;
import popmovies.udacity.com.presenter.interfaces.presenter.IPresenter;
import popmovies.udacity.com.presenter.interfaces.view.IView;

/**
 * Base fragment that covers common logic for all screens
 */
public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView {

    /**
     * Presenter that is controlling flow of the screen
     */
    @Inject protected T mPresenter;

    /**
     * Progress bar that is shown on the start of screen
     */
    @Bind(R.id.progress_bar) protected ProgressBar mProgressBar;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mPresenter.setView(this);
        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int layoutId = getLayout();
        View rootView = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, rootView);
        onPrepareLayoutFinished();
        mPresenter.onScreenCreated();
        return rootView;
    }

    @NonNull
    protected abstract Integer getLayout();

    /**
     * Invoked after layout has been inflated but before presenter invoked to start logic
     */
    protected void onPrepareLayoutFinished() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onScreenResumed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onScreenDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showServerErrorMessage() {
        Toast.makeText(getContext(),
                R.string.api_experiencing_problems_message,
                Toast.LENGTH_LONG)
                .show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showNoInternetConnection() {
        Toast.makeText(getContext(),
                R.string.no_internet_connection_message,
                Toast.LENGTH_LONG)
                .show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        setContentVisibility(View.VISIBLE);
    }

    /**
     * Sets the visibility of screen's primary content
     * @param visibility Visibility to set
     */
    protected abstract void setContentVisibility(int visibility);

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        setContentVisibility(View.GONE);
    }

    /**
     * Refreshes content of screen
     */
    public void refresh() {
        mPresenter.onScreenCreated();
    }
}
