package fr.appsolute.tp.ui.viewmodel

typealias OnSuccess<T> = (T)->Unit

typealias OnError = (Throwable) -> Unit

interface CallBack<in T> {
    fun onSuccess(value:T)
    fun onError(e: Throwable)
}