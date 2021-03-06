package com.parth.pixabay.imagesearch.data;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by parth on 11/16/16.
 */
public interface PixabayImageApi {

    @GET("/api")
    Observable<PixabayServerResponse> getImages(@Query("key") String key, @Query("q") String query,
                                                @Query("page") int pageNo, @Query("per_page") int pageSize);

}
