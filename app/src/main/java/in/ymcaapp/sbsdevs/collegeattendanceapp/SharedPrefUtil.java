package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by abhey singh on 30-10-2015.
 */
public class SharedPrefUtil {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPrefUtil(Context AppContext) {
        this.context = AppContext;
        sharedPreferences = context.getSharedPreferences(Constants.SharedPrefName,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (!sharedPreferences.contains(Constants.IsLoggedIn)){
            editor.putBoolean(Constants.IsLoggedIn,false);
            editor.commit();
        }
    }

    //Setters Methods

    public void setIsLoggedIn(){
        editor.putBoolean(Constants.IsLoggedIn,true);
        editor.commit();
    }

    public boolean getIsLoggedIn(){
        return sharedPreferences.getBoolean(Constants.IsLoggedIn,false);
    }

    public void setUserName(String Name){
        editor.putString(Constants.UserName,Name);
        editor.commit();
    }

    public void setUserEmail(String Email){
        editor.putString(Constants.UserEmail,Email);
        editor.commit();
    }
    public String getUserName(){
        return sharedPreferences.getString(Constants.UserName,"");
    }
    public String getUserEmail(){
        return sharedPreferences.getString(Constants.UserEmail,"");
    }

    public void setUserPassword(String Password){
        editor.putString(Constants.UserPassword,Password);
        editor.commit();
    }
    public String getUserPassword(){
        return sharedPreferences.getString(Constants.UserPassword,"");
    }



    public void logOutUser(){
        editor.clear();
        editor.commit();
    }
}