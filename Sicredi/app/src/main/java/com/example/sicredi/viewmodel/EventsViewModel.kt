package com.example.sicredi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sicredi.model.Event
import com.example.sicredi.model.EventAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class EventsViewModel(application: Application) : AndroidViewModel(application) {

    var events: MutableLiveData<List<Event>> = MutableLiveData()
    var eventsLoadError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
    var context = getApplication<Application>().applicationContext

    private val eventAPIService: EventAPIService = EventAPIService(context)
    private val disposable = CompositeDisposable()

    fun fetchFromServer() {
        eventAPIService.eventAPIService()
        loading.value = true
        eventAPIService.getEvents()
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object :
                DisposableSingleObserver<List<Event>>() {
                override fun onSuccess(events: List<Event>) {
                    eventsRetrieved(events)
                }

                override fun onError(e: Throwable) {
                    eventsLoadError.value = true
                    loading.value = false
                    e.printStackTrace()
                }
            })?.let {
                disposable.add(
                    it
            )
            }
    }

    private fun eventsRetrieved(eventsList: List<Event>) {
        if(eventsList.isNotEmpty()){
            events.postValue(eventsList)
            eventsLoadError.postValue(false)

        }else{
            eventsLoadError.postValue(true)
        }
        loading.postValue(false)
    }

}