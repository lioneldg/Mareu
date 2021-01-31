package com.example.mareu.di;

import com.example.mareu.service.InterfaceMeetingApiService;
import com.example.mareu.service.MeetingApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static final InterfaceMeetingApiService service = new MeetingApiService();    //variable de classe instanciée à la première utilisation de DI

    public static InterfaceMeetingApiService getMeetingApiService(){
        return service;
    }

}
