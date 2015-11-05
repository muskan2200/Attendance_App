package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by abhey singh on 04-11-2015.
 */
public class StudentSharedPref {

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public StudentSharedPref(Context AppContext) {
        this.context = AppContext;
        sharedPreferences = context.getSharedPreferences(Constants.StudentSharedPrefName,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (!sharedPreferences.contains(Constants.IsStudentLogin)){
            editor.putBoolean(Constants.IsStudentLogin,false);
            editor.commit();
        }
    }

    public void setIsLoggedIn(){
        editor.putBoolean(Constants.IsStudentLogin,true);
        editor.commit();
    }

    public boolean getIsLoggedIn(){
        return sharedPreferences.getBoolean(Constants.IsStudentLogin,false);
    }

    public void setStudentRoll(String Roll){
        editor.putString(Constants.LoggedInStudentRoll,Roll);
        editor.commit();
    }
    public String getStudentRoll(){
        return sharedPreferences.getString(Constants.LoggedInStudentRoll,"");
    }

    public void logOutUser(){
        editor.clear();
        editor.commit();
    }

}
