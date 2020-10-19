package com.example.sicredi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sicredi.model.CheckIn
import com.example.sicredi.model.Event
import com.example.sicredi.model.EventAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailsViewModel (application: Application) : AndroidViewModel(application) {

    var events: MutableLiveData<Event> = MutableLiveData()
    var eventsLoadError = MutableLiveData<Boolean>()
    var peopleLoadError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
    var context = getApplication<Application>().applicationContext

    private val eventAPIService: EventAPIService = EventAPIService(context)
    private val disposable = CompositeDisposable()

    fun fetchFromServer(eventId:String) {
        eventAPIService.eventAPIService()
        loading.value = true
        eventAPIService.getOneEvent(eventId)
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object :
                DisposableSingleObserver<Event>() {
                override fun onSuccess(event: Event) {
                    Log.d("OK", "OK")
                    eventRetrieved(event)
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

    private fun eventRetrieved(event: Event) {
        if(event.people.isEmpty()){
          peopleLoadError.postValue(true)
        }else{
            peopleLoadError.postValue(false)
        }

        events.postValue(event)
        eventsLoadError.postValue(false)
        loading.postValue(false)
    }

    fun checkIn(checkIn: CheckIn) {
        eventAPIService.eventAPIService()
        eventAPIService.checkIn(checkIn)
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object :
                DisposableSingleObserver<Event>() {
                override fun onSuccess(event: Event) {
                Log.d("Retrieved", "Retrieved")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })?.let {
                disposable.add(
                    it
                )
            }
    }
}