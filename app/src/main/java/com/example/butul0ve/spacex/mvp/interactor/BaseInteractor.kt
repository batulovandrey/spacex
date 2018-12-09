package com.example.butul0ve.spacex.mvp.interactor

import com.example.butul0ve.spacex.network.api.NetworkHelper

abstract class BaseInteractor(override val networkHelper: NetworkHelper) : MvpInteractor