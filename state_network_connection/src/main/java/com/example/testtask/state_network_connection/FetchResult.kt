package com.example.testtask.state_network_connection

/**
 * This is a sealed class that represents the different types of results that can occur when fetching data from a data source.
 *
 * @property SuccessDataUpload a subclass that represents a successful data upload
 * @property ErrorLoadingData a subclass that represents an error while loading data
 *
 * @constructor Creates a new instance of the FetchResult
 *
 */

sealed class FetchResult<T> {

    /**
     * A subclass that represents a successful data upload.
     *
     * @property data the data that was successfully uploaded
     *
     * @constructor Creates a new instance of the SuccessDataUpload
     *
     */

    class SuccessDataUpload<T>(val data: T) : FetchResult<T>()

    /**
     * A subclass that represents an error while loading data.
     *
     * @property message an optional message describing the error
     *
     * @constructor Creates a new instance of the ErrorLoadingData
     *
     */

    class ErrorLoadingData<T>(val message: String?) : FetchResult<T>()
}   