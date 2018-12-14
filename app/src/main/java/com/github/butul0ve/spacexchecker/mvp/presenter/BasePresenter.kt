package com.github.butul0ve.spacexchecker.mvp.presenter

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.github.butul0ve.spacexchecker.mvp.interactor.MvpInteractor
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : MvpView>(protected open val interactor: MvpInteractor) :
        MvpPresenter<V>() {

    protected val disposable = CompositeDisposable()

    override fun destroyView(view: V) {
        super.destroyView(view)
        disposable.clear()
    }
}