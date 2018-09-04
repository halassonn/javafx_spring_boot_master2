package app.ideb.idebgtm.service;

import app.ideb.idebgtm.config.AppUtil;
import app.ideb.idebgtm.config.bean.Pegawai;
import app.ideb.idebgtm.config.repository.PegawaiRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;

@Service
public class PegawaiService implements PegawaiRepository {

    @Autowired
    ApiService apiService;
    @Autowired
    AppUtil appUtil;
    private ObjectMapper mapper;
    private JSONArray jsonArray=null;
    private JSONObject jsonObject=null;
    private JSONParser jsonParser=new JSONParser();
    private static final String KARYAWAN_ENDPOINT = "/karyawan";
    private static final String PENDIDIKAN_ENDPOINT = "/pendidikan";
    private static final String GOLONGAN_ENDPOINT = "/golongan";
    private static final String AGAMA_ENDPOINT = "/agama";
    private static final String JENIS_KARYAWAN_ENDPOINT = "/jenis_karyawan";
    private static final String STATUS_PERNIKAHAN_KARYAWAN_ENDPOINT = "/status_perkawinan";
    private static final String JABATANT_ENDPOINT = "/jabatan";
    private Pegawai pegawai;
    private String res="";

    @Override
    public ObservableList<Pegawai> getAll() {
        ObservableList<Pegawai> pegawais = FXCollections.observableArrayList();
        try {
            jsonArray = new JSONArray();
            if(!System.getProperty("useras").equals("root")){
                res = apiService.GET(KARYAWAN_ENDPOINT+"/"+System.getProperty("kodekantor"));
            }else{
                res = apiService.GET(KARYAWAN_ENDPOINT);
            }
            jsonArray = (JSONArray) jsonParser.parse(res);

            for(int x=0;x<jsonArray.size();x++){
                jsonObject = new JSONObject((Map) jsonArray.get(x));
                //    utilityClass.convertdate( jsonObject.get("createdAt").toString());
                pegawai = new Pegawai(
                        jsonObject.get("_id").toString(),
                        jsonObject.get("nik").toString(),
                        jsonObject.get("nama").toString(),
                        jsonObject.get("tempat_lahir").toString(),
                        appUtil.formatedate(jsonObject.get("tgl_lahir").toString()),
                        jsonObject.get("jenkel").toString(),
                        jsonObject.get("email").toString(),
                        jsonObject.get("alamat").toString(),
                        jsonObject.get("agama").toString(),
                        jsonObject.get("status").toString(),
                        jsonObject.get("status_karyawan").toString(),
                        jsonObject.get("jenis_karyawan").toString(),
                        jsonObject.get("golongan").toString(),
                        jsonObject.get("pendidikan").toString(),
                        jsonObject.get("jabatan").toString(),
                        appUtil.formatedate(jsonObject.get("tgl_masuk").toString()),
                        jsonObject.get("masa_kerja").toString(),
                        jsonObject.get("notelp").toString(),
                        jsonObject.get("kode_kantor").toString(),
                        jsonObject.get("photo").toString(),
                        appUtil.formatedate2(jsonObject.get("createdAt").toString()),
                        appUtil.formatedate2(jsonObject.get("updatedAt").toString())
                );

                pegawais.addAll(pegawai);
                Comparator<Pegawai> comparator = Comparator.comparing(Pegawai::getUpdatedAt);
                pegawais.sort(comparator.reversed());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return pegawais;
    }

    @Override
    public ObservableList<Pegawai> findByNik(String nik) {
        ObservableList<Pegawai> pegawais = FXCollections.observableArrayList();
        try {
            jsonArray = new JSONArray();
            res = apiService.GET(KARYAWAN_ENDPOINT+"/"+System.getProperty("kodekantor")+"/"+nik);
            jsonArray = (JSONArray) jsonParser.parse(res);

            for(int x=0;x<jsonArray.size();x++){
                jsonObject = new JSONObject((Map) jsonArray.get(x));
                //    utilityClass.convertdate( jsonObject.get("createdAt").toString());
                pegawai = new Pegawai(
                        jsonObject.get("_id").toString(),
                        jsonObject.get("nik").toString(),
                        jsonObject.get("nama").toString(),
                        jsonObject.get("tempat_lahir").toString(),
                        appUtil.formatedate(jsonObject.get("tgl_lahir").toString()),
                        jsonObject.get("jenkel").toString(),
                        jsonObject.get("email").toString(),
                        jsonObject.get("alamat").toString(),
                        jsonObject.get("agama").toString(),
                        jsonObject.get("status").toString(),
                        jsonObject.get("status_karyawan").toString(),
                        jsonObject.get("jenis_karyawan").toString(),
                        jsonObject.get("golongan").toString(),
                        jsonObject.get("pendidikan").toString(),
                        jsonObject.get("jabatan").toString(),
                        appUtil.formatedate(jsonObject.get("tgl_masuk").toString()),
                        jsonObject.get("masa_kerja").toString(),
                        jsonObject.get("notelp").toString(),
                        jsonObject.get("kode_kantor").toString(),
                        jsonObject.get("photo").toString(),
                        appUtil.formatedate2(jsonObject.get("createdAt").toString()),
                        appUtil.formatedate2(jsonObject.get("updatedAt").toString())
                );
                pegawais.addAll(pegawai);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return pegawais;
    }

    @Override
    public ObservableList<String> getAgama() {
        ObservableList<String> agamas = FXCollections.observableArrayList();
        agamas = getParam("agama",AGAMA_ENDPOINT);
        return agamas;
    }

    @Override
    public ObservableList<String> getPendidikan() {
        ObservableList<String> pendidikans = FXCollections.observableArrayList();
        pendidikans = getParam("pendidikan",PENDIDIKAN_ENDPOINT);
        return pendidikans;
    }

    @Override
    public ObservableList<String> getGolongan() {
        ObservableList<String> golongans = FXCollections.observableArrayList();
        golongans = getParam("golongan",GOLONGAN_ENDPOINT);
        return golongans;
    }

    @Override
    public ObservableList<String> getStatusKawin() {
        ObservableList<String> status_kawins = FXCollections.observableArrayList();
        status_kawins = getParam("status",STATUS_PERNIKAHAN_KARYAWAN_ENDPOINT);
        return status_kawins;
    }

    @Override
    public ObservableList<String> getJenisKaryawan() {
        ObservableList<String> jenis_karyawans = FXCollections.observableArrayList();
        jenis_karyawans = getParam("jenis",JENIS_KARYAWAN_ENDPOINT);
        return jenis_karyawans;
    }

    @Override
    public ObservableList<String> getJabatan() {
        ObservableList<String> jabatans = FXCollections.observableArrayList();
        jabatans = getParam("jabatan",JABATANT_ENDPOINT);
        return jabatans;
    }

    @Override
    public String save(Pegawai karyawan) {
        jsonObject =new JSONObject();
        jsonObject=setDataForSaveOrUpdate(pegawai);
        return apiService.POST(KARYAWAN_ENDPOINT,jsonObject);
    }

    @Override
    public String delete(String id) {
        return apiService.DELETE(KARYAWAN_ENDPOINT+"/"+id,null);
    }

    @Override
    public String update(String id, Pegawai karyawan) {
        jsonObject = new JSONObject();
        jsonObject=setDataForSaveOrUpdate(pegawai);
        return apiService.PUT(KARYAWAN_ENDPOINT+"/"+id ,jsonObject);
    }

    private ObservableList<String> getParam(String param,String endpoint){
        ObservableList<String> datas = FXCollections.observableArrayList();
        try {
            jsonArray = new JSONArray();
            jsonArray = (JSONArray) jsonParser.parse(apiService.GET(endpoint));
            jsonObject = new JSONObject((Map) jsonArray.get(0));
            jsonArray = new JSONArray();
            jsonArray = (JSONArray) jsonParser.parse(jsonObject.get("data").toString());
            for(int x=0;x<jsonArray.size();x++){
                jsonObject = new JSONObject((Map) jsonArray.get(x));
                datas.add(jsonObject.get(param).toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datas.sort(String::compareToIgnoreCase);
        return datas;
    }

    private JSONObject setDataForSaveOrUpdate(Pegawai pegawai){
        mapper=new ObjectMapper();
        String payload=null;
        jsonObject =new JSONObject();
        try {
            payload=mapper.writeValueAsString(pegawai);
            jsonObject = (JSONObject) new JSONParser().parse(payload);
            jsonObject.remove("createdAt");
            jsonObject.remove("updatedAt");
            jsonObject.remove("groupedValue");
            jsonObject.remove("groupedColumn");
            jsonObject.remove("children");
            jsonObject.remove("_id");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }
}
