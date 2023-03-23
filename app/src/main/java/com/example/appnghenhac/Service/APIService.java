package com.example.appnghenhac.Service;

//CLASS LUU LAI VI TRI DUONG DAN CO DINH CUA CAC DU LIEU DUOC TRA VE//
public class APIService {
    private static String base_url = "https://musicdatabase2906.000webhostapp.com/Server/";
    

    public static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);

    }

}
