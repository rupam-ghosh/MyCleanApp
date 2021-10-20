package com.colearn.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.colearn.data.ApiStatus
import com.colearn.data.CollectionApiResult
import com.colearn.data.Constants.COLLECTION_ID
import com.colearn.data.Constants.PAGE_SIZE
import com.colearn.data.Photo
import com.colearn.data.Service

class LandingPageViewModel : ViewModel() {
    val collectionApiLiveData: MutableLiveData<CollectionApiResult> = MutableLiveData()
    var lastSuccessfulPage: Int = 0
    var currentPageToBeFetched: Int = -1

    fun fetchLandingPagePhotos() {
        val pageToBeFetched: Int = lastSuccessfulPage + 1
        if (currentPageToBeFetched == pageToBeFetched) {
            // this logic will prevent api calls for the same page
            return
        }
        currentPageToBeFetched = pageToBeFetched
        collectionApiLiveData.postValue(CollectionApiResult(ApiStatus.LOADING, collectionApiLiveData.value?.data))
        Service.unsplashService.getPhotosForCollection(COLLECTION_ID, pageToBeFetched, PAGE_SIZE).enqueue(object : Callback<ArrayList<Photo>> {
            override fun onResponse(call: Call<ArrayList<Photo>>, response: Response<ArrayList<Photo>>) {
                val photoList = response.body()
                if (photoList == null || photoList.size == 0) {
                    // code will reach here when the user has reached the last page
                    collectionApiLiveData.postValue(CollectionApiResult(ApiStatus.SUCCESS, collectionApiLiveData.value?.data))
                    return
                }
//                val collectionPhotoList = photoList.map { CollectionPhoto(COLLECTION_ID, it) }
//                Service.db.collectionDao().insertAll(collectionPhotoList)
                if (collectionApiLiveData.value?.data == null) {
                    // this code will execute for first page response of the api
                    photoList.let { collectionApiLiveData.postValue(CollectionApiResult(ApiStatus.SUCCESS, photoList)) }
                } else {
                    // this code will execute for subsequent page responses of the api
                    val lastData = collectionApiLiveData.value?.data
                    photoList.let { lastData?.addAll(it) }
                    collectionApiLiveData.postValue(CollectionApiResult(ApiStatus.SUCCESS, lastData))
                }
                lastSuccessfulPage = pageToBeFetched
            }

            override fun onFailure(call: Call<ArrayList<Photo>>, t: Throwable) {
                collectionApiLiveData.postValue(CollectionApiResult(ApiStatus.FAILURE, collectionApiLiveData.value?.data))
                // setting currentPageToBeFetched to -1 makes sure that the failed page can be retried by the user
                currentPageToBeFetched = -1
            }
        })
    }
}