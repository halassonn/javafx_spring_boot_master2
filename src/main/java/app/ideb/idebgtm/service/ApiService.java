package app.ideb.idebgtm.service;

import app.ideb.idebgtm.config.repository.ApiRepo;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

@Service
public class ApiService implements ApiRepo {

    private  static final String API_ENPOINT=getSettingAPI("HOST").concat(":").concat(getSettingAPI("PORT")).concat(getSettingAPI("ENDPOINT"));
    private HttpURLConnection conn;
    private String res = null;
    private static String token = "";
    private JSONObject jsonObject=null;
    /*public ApiService() {
        this.API_ENPOINT = getSettingAPI("HOST").concat(":").concat(getSettingAPI("PORT")).concat(getSettingAPI("ENDPOINT"));
    }*/

    @Override
    public String POST(String url, Object payload) {
        return access_Url("POST", url,payload);
    }

    @Override
    public String GET(String url) {
        return access_Url("GET", url,null);
    }

    @Override
    public String DELETE(String url, String id) {
        return access_Url("DELETE", url,null);
    }

    @Override
    public String PUT(String url,Object payload) {
        return access_Url("PUT", url,payload);
    }

    @Override
    public String PATCH(String url, Object payload) {
        return null;
    }

    private static String getSettingAPI(String key){
        return ResourceBundle.getBundle("ApiSetting").getString(key);
    }


    private String access_Url(String method, String endpoint,Object payload) {
        String t= (String) System.getProperties().get("token");
        if(t!=null){
            setToken(t);
        }
        try {
            System.out.println(API_ENPOINT.concat(endpoint));
            URL url = new URL(this.API_ENPOINT + endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Authorization", getToken());
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Language", "en-US");
            conn.setRequestProperty( "Accept", "*/*" );
            if(method.equals("POST")||method.equals("PUT")){
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(payload.toString());
                wr.flush();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            res = in.readLine();

        }catch (IOException e){
            try {

                if(conn.getErrorStream() != null){
                    BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

                    jsonObject = new JSONObject();
                    jsonObject.put("status","error");
                    jsonObject.put("message",err.readLine().toString());
                    res = jsonObject.toString();
                }else{
                    jsonObject = new JSONObject();
                    jsonObject.put("status","error");
                    jsonObject.put("message","Can't Connect to Server");
                    res = jsonObject.toString();
                }
            } catch (IOException e1) {
                jsonObject = new JSONObject();
                jsonObject.put("status","error");
                jsonObject.put("message",e.getLocalizedMessage());
                res = jsonObject.toString();
            }
        }
        return res;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ApiService.token = token;
    }
}
