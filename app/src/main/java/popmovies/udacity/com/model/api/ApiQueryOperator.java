/*
 * Copyright (C) 2016 The Android Open Source Project
 */

package popmovies.udacity.com.model.api;

import java.io.IOException;
import java.net.UnknownHostException;

import popmovies.udacity.com.presenter.interfaces.view.IView;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.OnErrorThrowable;

/**
 * Operator that handles API communication returning response but as well
 * handling error states and showing them to the view
 */
public class ApiQueryOperator<T> implements Observable.Operator<T, Response<T>> {

    /**
     * View that invoked a call and should show the error states if any
     */
    private final IView mView;

    public ApiQueryOperator(IView view) {
        mView = view;
    }

    /**
     * Maps result calls to the subscriber but as well handles the error states if any
     * occurred during communication with API
     * @param subscriber {@link Subscriber} that listens for result
     * @return {@link Subscriber} that can be used for communication with API
     */
    @Override
    public Subscriber<Response<T>> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<Response<T>>(subscriber) {
            @Override public void onNext(Response<T> response) {
                if (response.isSuccessful()) {
                    if (subscriber.isUnsubscribed()) return;

                    subscriber.onNext(response.body());
                    return;
                }

                try {
                    Throwable throwable = OnErrorThrowable
                            .addValueAsLastCause(new Exception(), response.errorBody().string());
                    onError(throwable);
                } catch (IOException e) {
                    onError(e);
                }
            }

            @Override public void onCompleted() {
                if (subscriber.isUnsubscribed()) return;
                subscriber.onCompleted();
            }

            @Override public void onError(Throwable e) {
                if (subscriber.isUnsubscribed() || mView == null) return;

                mView.hideProgressBar();
                if (e instanceof UnknownHostException) {
                    mView.showNoInternetConnection();
                } else {
                    mView.showServerErrorMessage();
                }

                subscriber.onError(e);
            }
        };
    }
}
