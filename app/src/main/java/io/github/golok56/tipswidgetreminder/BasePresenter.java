package io.github.golok56.tipswidgetreminder;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter {
    private CompositeDisposable mCompositeDisposables;

    protected BasePresenter() {
        mCompositeDisposables = new CompositeDisposable();
    }

    public void clear() {
        mCompositeDisposables.clear();
    }

    public void dispose() {
        mCompositeDisposables.dispose();
    }

    protected void add(Disposable disposable) {
        mCompositeDisposables.add(disposable);
    }
}
