package in.ymcaapp.sbsdevs.collegeattendanceapp;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by abhey singh on 30-10-2015.
 */
public class Util_Class {

    public static String getMacAddress(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

    public static void LongToast(Context context,String Message){
        Toast toast = Toast.makeText(context,Message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void ShortToast(Context context,String Message){
        Toast toast = Toast.makeText(context,Message,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }


    public static JSONObject getJSONFromUrl(String completeUrl){
        InputStream is = null;

        JSONObject jsonObject=null;
        String jsonString="";
        try {
            URL url = new URL(completeUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            is = new BufferedInputStream(urlConnection.getInputStream());
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            if(s.hasNext()){
                jsonString= s.next();
            }
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            //Log.d("error", "error in getjsonfromurl MalformedUrlexception");
        }
        catch(SocketTimeoutException r){
            // Log.e("Socket","error in timeout");
            r.printStackTrace();
        }catch (IOException e){
            //  Log.d("error", "error in getjsonfromurl Ioexception");
        }
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            // Log.d("error", "Json exception");
        }
        return jsonObject;
    }
    public static Boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo  = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static Typeface setNewTextStyle(Context context){
        return Typeface.createFromAsset(context.getAssets(),"Xipital -BRK-.ttf");
    }

    public static int getRandomImage(Context context){
        Random random = new Random();
        int randomInt = random.nextInt(4)+1;
        String ImageName = "nonet"+randomInt;
        return context.getResources().getIdentifier(ImageName, "drawable", context.getPackageName());
    }

    public static String getJsonOfList(List<StudentObject> FinalAttendanceList){
        Gson gson = new Gson();
        return gson.toJson(FinalAttendanceList);
    }

    public static void SnackBarShow(View v,String message){
        Snackbar snackbar = Snackbar.make(v,""+message,Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    public static ArrayList<String> getBatchList(){
        ArrayList<String> BatchList = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        int Year = calendar.get(Calendar.YEAR);
        BatchList.add(""+(Year-3));
        BatchList.add(""+(Year-2));
        BatchList.add(""+(Year-1));
        BatchList.add(""+(Year));
        BatchList.add(""+(Year+1));
        BatchList.add(""+(Year+2));
        BatchList.add(""+(Year+3));
        return BatchList;
    }
}
