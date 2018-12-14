package com.github.butul0ve.spacexchecker.mvp.interactor

import com.github.butul0ve.spacexchecker.network.api.NetworkHelper

abstract class BaseInteractor(override val networkHelper: NetworkHelper) : MvpInteractor