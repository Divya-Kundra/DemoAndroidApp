// IRemote.aidl
package com.example.administrator.omsai;

// Declare any non-default types here with import statements

interface IRemote {

 String accAuthen(String username, String password ) ;
 int createAcc(String uname, String pword, String age, String height, String id);
 String postDetails(String username, String password,String id, String age, String height);
 String getDetails(String id, String authStatus, String username, String password);
 int updateDetails(String username, String password,String id, String age, String height);
}
