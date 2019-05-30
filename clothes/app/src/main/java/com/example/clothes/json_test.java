package com.example.clothes;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class json_test {
    public static String loadJson (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL urlObject = new URL(url);
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json.toString();
    }


//    public static void main(String[] args) {
//        String url = "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/O-A0003-001?Authorization=CWB-6BB38BEE-559E-42AB-9AAD-698C12D12E22&downloadType=WEB&format=JSON";
////        String url = "http://www.kuaidi100.com/query?type=yunda&postid=1201386764793";
//        String json = loadJson(url);
//        System.out.println(json);
//    }

}
