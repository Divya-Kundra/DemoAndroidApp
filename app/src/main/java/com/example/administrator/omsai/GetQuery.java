package com.example.administrator.omsai;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetQuery {



    public static StringBuilder inputStreamToString (InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        // Read response until the end
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return full string
        return total;
    }

/*
    public static String Httppost(String username, String password ,String type, String url, JSONObject jo) throws ClientProtocolException, HttpResponseException
    {
        Http httpClient = new DefaultHttpClient();
        ResponseHandler<String> resonseHandler = new BasicResponseHandler();
        HttpPost postMethod = new HttpPost(url+"/ws/rest/v1/"+type);
        postMethod.setEntity(new StringEntity(jo.toString()));
        postMethod.setHeader( "Content-Type", "application/json");
        //String authorizationString = "Basic " + Base64.encodeToString(("admin" + ":" + "Admin123").getBytes(), Base64.DEFAULT); //this line is diffe
        //authorizationString.replace("\n", "");
        //postMethod.setHeader("Authorization", authorizationString);
        postMethod.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(username,password),"UTF-8", false));
        String response = httpClient.execute(postMethod,resonseHandler);
        return response;

    }

    public static String Httpget (String username, String password, String url,String query) throws ClientProtocolException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url+"/ws/rest/v1/"+query);
        httpGet.addHeader(BasicScheme.authenticate(new UsernamePasswordCredentials(username, password),"UTF-8", false));
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity responseEntity = httpResponse.getEntity();
        String str = inputStreamToString(responseEntity.getContent()).toString();
        httpClient.getConnectionManager().shutdown();
        return str;


    }
    public static String JsonParse(String str) throws JSONException
    {
        JSONObject jo = new JSONObject(str);
        JSONArray ja = jo.getJSONArray("results");
        JSONObject j1= (JSONObject)ja.get(0);
        String bla = (String)j1.get("uuid");
        return bla;

    }

    public static String uuidJsonParse(String str) throws JSONException
    {

        JSONObject obj = new JSONObject(str);
        String uuid = obj.getString("uuid");
        return uuid;


    }

    public static String create_patient(String username, String password, String url, String givenName,String familyName, String Id, String gender,int num) throws JSONException, ClientProtocolException {
        JSONObject nm = new JSONObject();
        nm.put("givenName",givenName);
        nm.put("familyName",familyName);

        JSONArray ja = new JSONArray();
        ja.put(nm);

        JSONObject json = new JSONObject();
        json.put("gender",gender);
        json.put("names",ja);
        json.put("age", num);
        //json.put("preferredAddress", address);

        String res;
        res=Httppost(username, password , "person",url,json);

        String uuid = uuidJsonParse(res);
        String idType=JsonParse(Httpget(username,password,url,"patientidentifiertype"));
        String loc=JsonParse(Httpget(username,password,url,"location"));

        JSONObject fijo = new JSONObject();
        JSONObject iden = new JSONObject();
        iden.put("identifier", Id);
        iden.put("identifierType", idType);
        iden.put("location", loc);
        iden.put("preferred", true);

        JSONArray finarr = new JSONArray();
        finarr.put(iden);
        fijo.put("identifiers", finarr);
        fijo.put("person", uuid);

        String finstr;
        finstr=Httppost(username,password,"patient",url, fijo);
        return finstr;


*/


    }









