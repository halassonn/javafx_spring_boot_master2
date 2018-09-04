package app.ideb.idebgtm.service;

import app.ideb.idebgtm.config.repository.LoginRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginRepository {
    @Autowired
    private ApiService apiService;
    private JSONArray jsonArray=null;
    private JSONObject jsonObject=null;

    @Override
    public String login(String endpoint, Object payload) throws ParseException {
        jsonArray = new JSONArray();
        String res=apiService.POST(endpoint,payload);
        System.out.println(payload);
        System.out.println(res);
        jsonArray = (JSONArray) new JSONParser().parse(res);
        jsonObject = (JSONObject) new JSONParser().parse(jsonArray.get(0).toString());
        return jsonObject.toString();
    }

    public static class UserLogin{
        private String email;
        private String password;

        public UserLogin(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}