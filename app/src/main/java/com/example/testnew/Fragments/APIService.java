package com.example.testnew.Fragments;

import com.example.testnew.Notification.MyResponse;
import com.example.testnew.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAgGfrIIE:APA91bEPS2Ig6Trh8ur6PIqM2rFK62PFUzgxR6ZMnwo2FhikjPHw2WG_RTa9iIlQxXdUkmYXqGHF5tioz0d8nhCvJB-g4_WnUisak43Hb-Ajztpw9NEELe_8bbUBIQdPhmy-Cq8ype2e"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
