package com.deeshop.util;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import rx.AsyncEmitter;
import rx.Observable;
import static android.databinding.Observable.OnPropertyChangedCallback;
/**
 * Created by zhiPeng.S on 2017/3/23.
 */

public class RxUtils {

    private RxUtils() {
    }

    public static <T> Observable<T> toObservable(@NonNull final ObservableField<T> observableField) {
        return Observable.fromEmitter(asyncEmitter -> {

            final OnPropertyChangedCallback callback = new OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(android.databinding.Observable dataBindingObservable, int propertyId) {
                    if (dataBindingObservable == observableField) {
                        asyncEmitter.onNext(observableField.get());
                    }
                }
            };

            observableField.addOnPropertyChangedCallback(callback);

            asyncEmitter.setCancellation(() -> observableField.removeOnPropertyChangedCallback(callback));

        }, AsyncEmitter.BackpressureMode.LATEST);
    }
}
