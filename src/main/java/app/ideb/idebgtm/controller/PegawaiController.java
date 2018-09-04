package app.ideb.idebgtm.controller;

import app.ideb.idebgtm.config.AppUtil;
import app.ideb.idebgtm.config.StageManager;
import app.ideb.idebgtm.config.animation.Animate;
import app.ideb.idebgtm.config.bean.ApiResponse;
import app.ideb.idebgtm.config.bean.Pegawai;
import app.ideb.idebgtm.controller.loader.LoaderController;
import app.ideb.idebgtm.controller.report.Report_ViewerController;
import app.ideb.idebgtm.service.PegawaiService;
import app.ideb.idebgtm.view.FxmlView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;

@Controller
public class PegawaiController implements Initializable {
    @Lazy
    @Autowired
    private StageManager stageManager;
    @Autowired
    Animate animate;
    @Autowired
    AppUtil appUtil;
    @Autowired
    PegawaiService pegawaiService;
    private String id = null;
    private String imagebase64 = null;
    private List<String> listStatusKarywan = new ArrayList<>();
    private List<String> listJenkel = new ArrayList<>();
    private ObservableList<ApiResponse> apiResponses = FXCollections.observableArrayList();
    private ObservableList<Pegawai> listKaryawans = FXCollections.observableArrayList();
    private ObservableList<String> agamas = FXCollections.observableArrayList();
    private ObservableList<String> pendidikans = FXCollections.observableArrayList();
    private ObservableList<String> golongans = FXCollections.observableArrayList();
    private ObservableList<String> status_kawin = FXCollections.observableArrayList();
    private ObservableList<String> jenis_karyawans = FXCollections.observableArrayList();
    private ObservableList<String> status_karyawans = FXCollections.observableArrayList();
    private ObservableList<String> jabatan = FXCollections.observableArrayList();
    private ObservableList<String> jenkel = FXCollections.observableArrayList();
    private String imageDefaultToString = "iVBORw0KGgoAAAANSUhEUgAAAgAAAAIAAgMAAACJFjxpAAAADFBMVEXFxcX////p6enW1tbAmiBwAAAFiElEQVR4AezAgQAAAACAoP2pF6kAAAAAAAAAAAAAAIDbu2MkvY0jiuMWWQoUmI50BB+BgRTpCAz4G6C8CJDrC3AEXGKPoMTlYA/gAJfwETawI8cuBs5Nk2KtvfiLW+gLfK9m+r3X82G653+JP/zjF8afP1S//y+An4/i51//AsB4aH+/QPD6EQAY/zwZwN8BAP50bh786KP4+VT+3fs4/noigEc+jnHeJrzxX+NWMDDh4g8+EXcnLcC9T8U5S/CdT8bcUeBEIrwBOiI8ki7Ba5+NrePgWUy89/nYyxQ8Iw3f+pWY4h1gb3eAW7sDTPEOsLc7wK1TIeDuDB+I/OA1QOUHv/dFsZQkhKkh4QlEfOULYz2nGj2/Nn1LmwR/86VxlCoAW6kCsHRGANx1RgCMo5Qh2EsZgrXNQZZShp5Liv7Il8eIc5C91EHY2hxk6bwYmNscZIReDBwtCdhbErC1JGBpScBcOgFMLQsZMQs5Whayd+UQsLYsZGlZyNyykKllISNmIUfAwifw8NXvTojAjGFrdYi11SGWVoeYWx1i6lmQCiEjFkKOVgjZ+xxIhZCtFULWHkCqxCw9gNQKmP9vNHzipdEPrRcxtVbAeDkAvve0iM2QozVD9hfjhp4YP/UrkJYDbD2AtBxgfSkAvvHEeNcDSAsilgtAWxIy91J8AXgZAJ5e33+4tuACcAG4AFwALgBXRXQB6AFcB5MXAuA6nl9/0Vx/011/1V5/1/dfTPJvRtdnu/zL6beeFO/7r+fXBYbrEkt/j+i6ytXfpuvvE/ZXOnsA/a3a/l5xf7O6v1t+Xe/vOyz6HpO8yyboM8o7rfJes77bru83THk48p7TvOs27zvOO6/73vO++z7l4cgnMPQzKPopHC0N9noSSz6LJp/Gk88jyicy5TOp6qlc+VyyfDJbPpuuns6XzyfMJzTmMyrrKZ35nNJ8Ums+q7af1tvPK+4nNodEnPKp3fnc8npyez67/qVP7+/fL8hfcMjfsOhf8cjfMclfcnn9+BkOnLECP8Q58OYeyJ40eoyF6Ee/En/JHlP6mIlRVXprF4BxtAvArV0AxtEuALd2ARhHuwDc2gVgHPX/hFv9fMBddjIGeKg/WCxlCsI46u+Ga5mCcJd+sIG9UkGAW32ZbApFAHhod4Bb3eo04h3god0BbiUHYApVCNjbHeBW+QDAXT4a7qg7r7e214057vg0QhkEHkoSwq0kIdydXw4/Q3H8hjYJ3vL0WConBJhCHQaOToeBrU0BljYFmEoVgHGUKgAPnREAt84IgLuqFgAYSUEOAHszDwuAtSkHAZhLGYIpdCLgKGUIHtocZG1zkLmUIRhxDnJU1RDA1uYga5uDzKUOwhTnIEfnxcDe5iBrcyQAYGlzkKkUYhhxDrKXQgxbSwLWUohhbknA1JKAEZOAvSUBW0sC1pYEzC0JmFoSMMJyCDhaFrK3JGDtyiFgaVnI3LKQqWUhI2YhR8tC9paFrC0LWVoWMrcsZGpZyIhZyNGykL2rSIGtlQHWVgZYWhlgbmWAqZUBRiwDHK0MsLcywNbKAGsOoNUhllaHmFsdYmp1iBHrEEerQ+w5gFYI2VodYm11iKXVIeYcQCuETK0QMmIh5MgBtELI3gohWyuErDmAVolZWiFkzgG0SszUKjGjfj6gVmKOVonZcwCtFbB9HQC+ozWDbz1bvGu9iKW1AuYcQOtFTLEX1GbIaFegN0OOHEBrhuw5gNYM2XIArRuz5gDacoB3bTnAEktxXQ4wfw0AvveM8b4tiJjSJOwLIsbXsAKeNeKCiOO3D+AVbUl0AfjGs8ZPbUnIdgFoa1LWC0BblfMuB9AeC1j6gqQE0J9LmC8AOYD2ZMb7i4bt2ZTpWoHfPoB7Tj2fXzT8N1X41vkq/QHOAAAAAElFTkSuQmCC";
    private String tgl_masuk = null;
    private String tgl_lahir = null;
    private boolean update = false;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private Pegawai pegawai;
    private String res=null;
    private ObjectMapper mapper=new ObjectMapper();
    private String Datasource=null;


    @FXML
    private StackPane pegawai_page;

    @FXML
    private ScrollPane detail_karyawan;
    @FXML
    private StackPane root_pegawai;

    @FXML
    private JFXButton btn_open_form;

    @FXML
    private AnchorPane form_pegawai;

    @FXML
    private ImageView image_view;
    @FXML
    private ImageView photo_view;

    @FXML
    private JFXButton btn_choose_file;

    @FXML
    private JFXTextField txt_nik;

    @FXML
    private JFXTextField txt_nama;

    @FXML
    private JFXTextField txt_alamat;

    @FXML
    private JFXTextField txt_tempat_lahir;

    @FXML
    private JFXDatePicker txt_tgl_lahir;

    @FXML
    private JFXComboBox<String> cmb_jenkel;

    @FXML
    private JFXComboBox<String> cmb_agama;

    @FXML
    private JFXComboBox<String> cmb_Status_pernikahan;

    @FXML
    private JFXComboBox<String> cmb_pendidikan;
    @FXML
    private JFXComboBox<String> cmb_jenis_karyawan;

    @FXML
    private JFXComboBox<String> cmb_status_karyawan;

    @FXML
    private JFXComboBox<String> cmb_jabatan;

    @FXML
    private JFXComboBox<String> cmb_golongan;

    @FXML
    private JFXTextField txt_notelp;

    @FXML
    private JFXTextField txt_email;

    @FXML
    private Label usia;
    @FXML
    private Label lbl_nik;

    @FXML
    private Label lbl_nama;

    @FXML
    private Label lbl_jenkel;

    @FXML
    private Label lbl_alamat;

    @FXML
    private Label lbl_tmpat_tgl_lahir;

    @FXML
    private Label lbl_sts_perkawinan;

    @FXML
    private Label lbl_jabatan;

    @FXML
    private Label lbl_jenis_karyawan;

    @FXML
    private Label lbl_status_karyawan;

    @FXML
    private Label lbl_tgl_masuk;

    @FXML
    private Label lbl_masa_kerja;

    @FXML
    private Label lbl_pend_gol;
    @FXML
    private JFXButton btn_refresh_pegawai;

    @FXML
    private JFXDatePicker txt_tgl_masuk;

    @FXML
    private Label lbl_masa_kerja1;
    @FXML
    private JFXTreeTableView<Pegawai> tbl_Karyawan;
    @FXML
    private TextField txt_cari_pegawai;
    @FXML
    private JFXButton btn_simpan;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listKaryawans.addListener((ListChangeListener<? super Pegawai>) c -> {
            if(c.getList().isEmpty()){
                txt_cari_pegawai.setVisible(false);
                btn_refresh_pegawai.setVisible(false);
            }else {
                txt_cari_pegawai.setVisible(true);
                btn_refresh_pegawai.setVisible(true);
                animate.setFadeinTransition(txt_cari_pegawai,0.5);
                animate.setFadeinTransition(btn_refresh_pegawai,0.5);
            }
        });
        txt_tgl_lahir.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                //convert String to LocalDate
                LocalDate tgllahir = LocalDate.parse(newValue.format(formatter), formatter);
                LocalDate today = LocalDate.now();
                Period p = Period.between(today, tgllahir);
                long tahun = ChronoUnit.YEARS.between(tgllahir, today);
                long hari = ChronoUnit.DAYS.between(tgllahir, today);
                if (tahun < 17) {
                    appUtil.showAlert(root_pegawai, "Error Tanggal Lahir", "Usia Anda di bawah 17 Tahun", null);
                    newValue.equals(null);
                    tgl_lahir = null;
                    txt_tgl_lahir.setValue(null);
                } else {
                    tgl_lahir = String.valueOf(newValue);
                    txt_nik.setText(appUtil.createNik(txt_tgl_lahir, System.getProperty("kodekantor")));
                }
                usia.setText(String.valueOf(tahun));
            }

        });
        txt_tgl_masuk.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tgl_masuk = newValue.toString();
                lbl_masa_kerja1.setText(appUtil.getMasaKerja(root_pegawai, newValue));
            }

        });
        apiResponses.addListener((ListChangeListener<? super ApiResponse>) c -> {
            if (c.getList().size() > 0) {
                appUtil.showAlert(root_pegawai, c.getList().get(0).getStatus(), c.getList().get(0).getMessage(), null);
                if(!c.getList().get(0).getStatus().toLowerCase().equals("error")){
                    close_detail_pegawai(null);
                    refresh_list_karyawan(null);
                }


            }
        });
        btn_simpan.setOnAction(event -> {
            simpan();
        });
        Platform.runLater(() -> {
            initTable();
            initParameter();
        });
    }

    @FXML
    void open_form(ActionEvent event) {
        AnchorPane.setRightAnchor(form_pegawai, 0.0);
        AnchorPane.setLeftAnchor(form_pegawai, 0.0);
        form_pegawai.setVisible(true);
        btn_open_form.setVisible(false);
        animate.setFadeinTransition(form_pegawai, 0.5);
    }
    @FXML
    void refresh_list_karyawan(ActionEvent event) {
        txt_cari_pegawai.setText("");
        initdataPegawai();
    }

    @FXML
    void close_form(ActionEvent event) {
        clearForm();
        animate.setFadeoutTransition(form_pegawai, 0.5);
        form_pegawai.setVisible(false);
        btn_open_form.setVisible(true);
    }

    @FXML
    void choose_image(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        List<String> ex = new ArrayList<String>();
        ex.add("*.Jpeg");
        ex.add("*.png");
        ex.add("*.Jpg");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png", "*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpg", "*.jpg"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagebase64 = appUtil.EncodeImageToBase64(selectedFile.getAbsolutePath());
            Image img = new Image(new ByteArrayInputStream(appUtil.DecodeBase64toImage2(imagebase64)));
            image_view.setImage(img);
        } else {
            //image_file.setText(null);
        }
    }

    @FXML
    void close_detail_pegawai(ActionEvent event) {
        detail_karyawan.setVisible(false);
        detail_karyawan.toBack();
        root_pegawai.requestFocus();
    }

    private void initdataPegawai() {
        listKaryawans.clear();
        listKaryawans.addAll(pegawaiService.getAll());
    }
    private void initParameter() {
        agamas.addListener((ListChangeListener<? super String>) p -> {
            cmb_agama.getItems().addAll(p.getList());
        });
        pendidikans.addListener((ListChangeListener<? super String>) p -> {
            cmb_pendidikan.getItems().addAll(p.getList());
        });
        golongans.addListener((ListChangeListener<? super String>) p -> {
            cmb_golongan.getItems().addAll(p.getList());
        });
        status_kawin.addListener((ListChangeListener<? super String>) p -> {
            cmb_Status_pernikahan.getItems().addAll(p.getList());
        });
        jenis_karyawans.addListener((ListChangeListener<? super String>) p -> {
            cmb_jenis_karyawan.getItems().addAll(p.getList());
        });
        status_karyawans.addListener((ListChangeListener<? super String>) p -> {
            cmb_status_karyawan.getItems().addAll(p.getList());
        });
        jabatan.addListener((ListChangeListener<? super String>) p -> {
            cmb_jabatan.getItems().addAll(p.getList());
        });
        jenkel.addListener((ListChangeListener<? super String>) p -> {
            cmb_jenkel.getItems().addAll(p.getList());
        });

        Platform.runLater(() -> {
            listStatusKarywan.add("Aktif");
            listStatusKarywan.add("Tidak Aktif");
            listJenkel.add("Laki-laki");
            listJenkel.add("Perempuan");
            agamas.addAll(pegawaiService.getAgama());
            pendidikans.addAll(pegawaiService.getPendidikan());
            golongans.addAll(pegawaiService.getGolongan());
            status_kawin.addAll(pegawaiService.getStatusKawin());
            jenis_karyawans.addAll(pegawaiService.getJenisKaryawan());
            status_karyawans.addAll(listStatusKarywan);
            jabatan.addAll(pegawaiService.getJabatan());
            jenkel.addAll(listJenkel);
        });
    }

    private void initTable() {
        JFXTreeTableColumn<Pegawai, String> nikcol = new JFXTreeTableColumn<>("Nik");
        nikcol.setPrefWidth(150);
        nikcol.setCellValueFactory(param -> param.getValue().getValue().nikProperty());

        JFXTreeTableColumn<Pegawai, String> namacol = new JFXTreeTableColumn<>("Nama");
        namacol.setPrefWidth(150);
        namacol.setCellValueFactory(param -> param.getValue().getValue().namaProperty());

        JFXTreeTableColumn<Pegawai, String> tpt_lahir_col = new JFXTreeTableColumn<>("Tempat Lahir");
        tpt_lahir_col.setPrefWidth(150);
        tpt_lahir_col.setCellValueFactory(param -> param.getValue().getValue().tempat_lahirProperty());

        JFXTreeTableColumn<Pegawai, String> tgl_lahir_col = new JFXTreeTableColumn<>("Tgl Lahir");
        tgl_lahir_col.setPrefWidth(150);
        tgl_lahir_col.setCellValueFactory(param -> param.getValue().getValue().tgl_lahirProperty());

        JFXTreeTableColumn<Pegawai, String> email_col = new JFXTreeTableColumn<>("Email");
        email_col.setPrefWidth(150);
        email_col.setCellValueFactory(param -> param.getValue().getValue().emailProperty());

        JFXTreeTableColumn<Pegawai, String> tgl_masuk_col = new JFXTreeTableColumn<>("Tgl Masuk");
        tgl_masuk_col.setPrefWidth(150);
        tgl_masuk_col.setCellValueFactory(param -> param.getValue().getValue().tgl_masukProperty());

        JFXTreeTableColumn<Pegawai, String> masa_kerja_col = new JFXTreeTableColumn<>("Masa Kerja");
        masa_kerja_col.setPrefWidth(150);
        masa_kerja_col.setCellValueFactory(param -> param.getValue().getValue().masa_kerjaProperty());

        JFXTreeTableColumn<Pegawai, String> createat_col = new JFXTreeTableColumn<>("Create At");
        createat_col.setPrefWidth(150);
        createat_col.setCellValueFactory(param -> param.getValue().getValue().createdAtProperty());

        JFXTreeTableColumn<Pegawai, String> updateat_col = new JFXTreeTableColumn<>("Update At");
        updateat_col.setPrefWidth(150);
        updateat_col.setCellValueFactory(param -> param.getValue().getValue().updatedAtProperty());

        MenuItem editItem = new MenuItem("Edit");
        MenuItem detailItem = new MenuItem("Details");
        MenuItem hapusItem = new MenuItem("Hapus");
        MenuItem printItem = new MenuItem("Print Data");
        ContextMenu rootContextMenu = new ContextMenu();
        rootContextMenu.getItems().addAll(detailItem, editItem, hapusItem,printItem);
        rootContextMenu.getItems().forEach(menuItem -> {
            menuItem.setOnAction(event -> {
                if (menuItem.getText().toLowerCase().equals("details")) {
                    initDetailPegawai(tbl_Karyawan.getSelectionModel().getSelectedItem().getValue());
                } else if (menuItem.getText().toLowerCase().equals("edit")) {
                    open_form(null);
                    initEditData(tbl_Karyawan.getSelectionModel().getSelectedItem().getValue());
                } else if (menuItem.getText().toLowerCase().equals("hapus")) {
                    deletedata(tbl_Karyawan.getSelectionModel().getSelectedItem().getValue());
                }else if (menuItem.getText().toLowerCase().equals("print data")) {
                    //initDetailPegawai(tbl_Karyawan.getSelectionModel().getSelectedItem().getValue());
                    //open form choose print
                    openPrintLayout();
                    Datasource=DataSoruceJsonString(tbl_Karyawan.getSelectionModel().getSelectedItem().getValue());
                }
            });
        });

        rootContextMenu.getItems().forEach(menuItem -> {
            FontAwesomeIcon icon = new FontAwesomeIcon();
            icon.getStyleClass().add("icon");
            icon.setSize("1.2em");
            if (menuItem.getText().toLowerCase().equals("details")) {
                icon.setGlyphName("EYE");
                menuItem.setGraphic(icon);
            } else if (menuItem.getText().toLowerCase().equals("edit")) {
                icon.setGlyphName("PENCIL");
                menuItem.setGraphic(icon);
            } else if (menuItem.getText().toLowerCase().equals("hapus")) {
                icon.setGlyphName("TRASH");
                menuItem.setGraphic(icon);
            } else if (menuItem.getText().toLowerCase().equals("print data")) {
                icon.setGlyphName("PRINT");
                menuItem.setGraphic(icon);
            }
        });

        final TreeItem<Pegawai> root = new RecursiveTreeItem<Pegawai>(listKaryawans, RecursiveTreeObject::getChildren);

        tbl_Karyawan.getColumns().setAll(nikcol, namacol, tpt_lahir_col, tgl_lahir_col, email_col, tgl_masuk_col, createat_col, updateat_col);
        tbl_Karyawan.setContextMenu(rootContextMenu);
        tbl_Karyawan.setRoot(root);
        tbl_Karyawan.setShowRoot(false);
        txt_cari_pegawai.textProperty().addListener((observable, oldValue, newValue) -> {
            tbl_Karyawan.setPredicate(new Predicate<TreeItem<Pegawai>>() {
                @Override
                public boolean test(TreeItem<Pegawai> karyawanTreeItem) {
                    // Boolean flag = false;
                    if (karyawanTreeItem.getValue().nikProperty().getValue().contains(newValue)) {

                        return true;
                    } else if (karyawanTreeItem.getValue().namaProperty().getValue().contains(newValue)) {

                        return true;
                    } else if (karyawanTreeItem.getValue().emailProperty().getValue().contains(newValue)) {

                        return true;
                    }
                    return false;
                }
            });
        });


        listKaryawans.clear();
        listKaryawans.addAll(pegawaiService.getAll());

    }

    private void initDetailPegawai(Pegawai karyawan) {
        lbl_nik.setText(karyawan.nikProperty().getValue());
        lbl_nama.setText(karyawan.namaProperty().getValue());
        lbl_jenkel.setText(karyawan.jenkelProperty().getValue());
        lbl_alamat.setText(karyawan.alamatProperty().getValue());
        lbl_tmpat_tgl_lahir.setText(karyawan.tempat_lahirProperty().getValue() + "/" + karyawan.tgl_lahirProperty().getValue());
        lbl_jabatan.setText(karyawan.jabatanProperty().getValue());
        lbl_jenis_karyawan.setText(karyawan.jenis_karyawanProperty().getValue());
        lbl_pend_gol.setText(karyawan.pendidikanProperty().getValue() + "/" + karyawan.golonganProperty().getValue());
        lbl_status_karyawan.setText(karyawan.statusProperty().getValue());
        lbl_sts_perkawinan.setText(karyawan.statusProperty().getValue());
        lbl_tgl_masuk.setText(karyawan.tgl_masukProperty().getValue());

        // lbl_masa_kerja.setText(formUtility.getMasaKerja(root_pegawai, LocalDate.parse(karyawan.tgl_masukProperty().getValue())));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (!karyawan.tgl_masukProperty().getValue().equals("")) {
            //default, ISO_LOCAL_DATE
            LocalDate localDate = LocalDate.parse(karyawan.tgl_masukProperty().getValue(), formatter);
            karyawan.masa_kerjaProperty().setValue(appUtil.getMasaKerja(root_pegawai, localDate));
        }
        lbl_masa_kerja.setText(karyawan.masa_kerjaProperty().getValue());
        Image img = new Image(new ByteArrayInputStream(appUtil.DecodeBase64toImage2(karyawan.photoProperty().getValue())));
        photo_view.setImage(img);
        Platform.runLater(() -> {
            detail_karyawan.setVisible(true);
            detail_karyawan.toFront();
            animate.setFadeinTransition(detail_karyawan, 0.5);
            detail_karyawan.requestFocus();
        });
    }

    public void initEditData(Pegawai karyawan) {
        setUpdate(true);
        id = karyawan._idProperty().getValue();
        txt_nik.setText(karyawan.nikProperty().getValue());
        txt_nama.setText(karyawan.namaProperty().getValue());
        txt_tempat_lahir.setText(karyawan.tempat_lahirProperty().getValue());
        txt_tgl_lahir.setValue(appUtil.LOCAL_DATE(karyawan.tgl_lahirProperty().getValue()));
        txt_alamat.setText(karyawan.alamatProperty().getValue());
        txt_email.setText(karyawan.emailProperty().getValue());
        txt_notelp.setText(karyawan.notelpProperty().getValue());
        cmb_agama.setValue(karyawan.agamaProperty().getValue());
        cmb_jenkel.setValue(karyawan.jenkelProperty().getValue());
        cmb_jenis_karyawan.setValue(karyawan.jenis_karyawanProperty().getValue());
        cmb_status_karyawan.setValue(karyawan.status_karyawanProperty().getValue());
        cmb_Status_pernikahan.setValue(karyawan.statusProperty().getValue());
        //lbl_masa_kerja.setText(karyawan.masa_kerjaProperty().getValue());
        cmb_golongan.setValue(karyawan.golonganProperty().getValue());
        cmb_pendidikan.setValue(karyawan.pendidikanProperty().getValue());
        cmb_jabatan.setValue(karyawan.jabatanProperty().getValue());
        txt_tgl_masuk.setValue(appUtil.LOCAL_DATE(karyawan.tgl_masukProperty().getValue()));
        imagebase64 = karyawan.photoProperty().getValue();
        Image img = new Image(new ByteArrayInputStream(appUtil.DecodeBase64toImage2(imagebase64)));
        image_view.setImage(img);
    }

    public void deletedata(Pegawai karyawan) {
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        FontAwesomeIcon icon_ya = new FontAwesomeIcon();
        FontAwesomeIcon icon_tidak = new FontAwesomeIcon();
        JFXButton btn_tidak = new JFXButton("Batal");
        JFXButton btn_ya = new JFXButton("Hapus");
        icon_tidak.setGlyphName("CLOSE");
        icon_ya.setGlyphName("CHECK");
        icon_tidak.setSize("1.5em");
        icon_ya.setSize("1.5em");
        icon_tidak.setFill(Color.WHITE);
        icon_ya.setFill(Color.WHITE);
        btn_ya.setGraphic(icon_ya);
        btn_tidak.setGraphic(icon_tidak);

        btn_ya.setStyle("-fx-text-fill: white;-fx-background-color: green");
        btn_tidak.setStyle("-fx-text-fill: white;-fx-background-color: orangered");
        btn_tidak.setButtonType(JFXButton.ButtonType.RAISED);
        btn_ya.setButtonType(JFXButton.ButtonType.RAISED);

        btn_ya.setOnAction(event -> {
            String res = pegawaiService.delete(karyawan._idProperty().getValue());
            try {
                jsonArray = (JSONArray) new JSONParser().parse(res);
                jsonObject = (JSONObject) jsonArray.get(0);
                appUtil.closeAlert();
                Platform.runLater(() -> {
                    apiResponses.addAll(new ApiResponse(
                            Boolean.parseBoolean(jsonObject.get("success").toString()),
                            jsonObject.get("message").toString(),
                            "success"));
                });
            } catch (ParseException e) {
                e.printStackTrace();
            }

        });
        btn_tidak.setOnAction(event -> {
            appUtil.closeAlert();
        });
        hBox.getChildren().addAll(btn_ya, btn_tidak);
        appUtil.showAlert(root_pegawai, "Hapus", "Data Karyawan \ndengan nik :" + karyawan.nikProperty().getValue() + "\ningin di hapus?", hBox);


    }

    public void simpan1() {
        apiResponses.clear();
        if (!isUpdate()) {
            pegawai = new Pegawai(
                    null,
                    txt_nik.getText(),
                    txt_nama.getText(),
                    txt_tempat_lahir.getText(),
                    tgl_lahir,
                    cmb_jenkel.getValue(),
                    txt_email.getText(),
                    txt_alamat.getText(),
                    cmb_agama.getValue(),
                    cmb_Status_pernikahan.getValue(),
                    cmb_status_karyawan.getValue(),
                    cmb_jenis_karyawan.getValue(),
                    cmb_golongan.getValue(),
                    cmb_pendidikan.getValue(),
                    cmb_jabatan.getValue(),
                    tgl_masuk,
                    lbl_masa_kerja.getText(),
                    txt_notelp.getText(),
                    System.getProperty("kodekantor"),
                    imagebase64, null, null);
            Task<Object> task = new Task<Object>() {
                @Override
                protected Object call() throws Exception {
                    res = pegawaiService.save(pegawai);
                    try {
                        jsonArray = (JSONArray) new JSONParser().parse(res);
                        jsonObject = (JSONObject) jsonArray.get(0);
                        apiResponses.addAll(new ApiResponse(
                                Boolean.parseBoolean(jsonObject.get("success").toString()),
                                jsonObject.get("message").toString(),
                                jsonObject.get("status").toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            task.setOnSucceeded(event -> {
            });
            task.run();
        }else{
            pegawai = new Pegawai(
                    null,
                    txt_nik.getText(),
                    txt_nama.getText(),
                    txt_tempat_lahir.getText(),
                    tgl_lahir,
                    cmb_jenkel.getValue(),
                    txt_email.getText(),
                    txt_alamat.getText(),
                    cmb_agama.getValue(),
                    cmb_Status_pernikahan.getValue(),
                    cmb_status_karyawan.getValue(),
                    cmb_jenis_karyawan.getValue(),
                    cmb_golongan.getValue(),
                    cmb_pendidikan.getValue(),
                    cmb_jabatan.getValue(),
                    tgl_masuk,
                    lbl_masa_kerja.getText(),
                    txt_notelp.getText(),
                    System.getProperty("kodekantor"),
                    imagebase64, null, null);

            res = pegawaiService.update(id,pegawai);
            try {
                jsonArray = (JSONArray) new JSONParser().parse(res);
                jsonObject = (JSONObject) jsonArray.get(0);
                apiResponses.addAll(new ApiResponse(
                        Boolean.parseBoolean(jsonObject.get("success").toString()),
                        jsonObject.get("message").toString(),
                        jsonObject.get("status").toString()));

            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        if(apiResponses.get(0).isSuccess()){
            clearForm();
        }

    }

    private void simpan(){
        Platform.runLater(() -> {
            LoaderController.getInstance().pProperty().setValue(appUtil.getStringFromResourceBundle("pegawai.simpan"));
            stageManager.openNode(HomeController.getInstance().content, FxmlView.LOADER);
        });
    }

    private void clearForm(){
        id=null;
        txt_nik.setText("");
        txt_nama.setText("");
        txt_tempat_lahir.setText("");
        txt_tgl_lahir.setValue(null);
        txt_alamat.setText("");
        txt_email.setText("");
        txt_notelp.setText("");
        cmb_agama.setValue("");
        cmb_jenkel.setValue("");
        cmb_jenis_karyawan.setValue("");
        cmb_status_karyawan.setValue("");
        cmb_Status_pernikahan.setValue("");
        lbl_masa_kerja1.setText("");
        cmb_golongan.setValue("");
        cmb_pendidikan.setValue("");
        cmb_jabatan.setValue("");
        txt_tgl_masuk.setValue(null);
        imagebase64 = imageDefaultToString;
        Image img = new Image(new ByteArrayInputStream(appUtil.DecodeBase64toImage2(imagebase64)));
        image_view.setImage(img);
        usia.setText("");
        setUpdate(false);
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    private void openPrintLayout(){

        VBox panePrint=new VBox();
        AnchorPane layout=new AnchorPane();
        FontAwesomeIcon closelayout=new FontAwesomeIcon();
        closelayout.setSize("1.5em");
        closelayout.setGlyphName("CLOSE");
        closelayout.setCursor(Cursor.HAND);
        animate.setFadeinTransition(layout,0.2);
        closelayout.setOnMouseClicked(event -> {
            animate.setFadeoutTransition(layout,0.2);
        });
        layout.setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        layout.setPrefSize(250,250);
        layout.setMaxSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE);
        panePrint.getStyleClass().add("side");
        panePrint.setSpacing(10);
        panePrint.setAlignment(Pos.TOP_CENTER);
        panePrint.setPadding(new Insets(15,0,15,0));



        String jsonString = "[{\"icon\":\"PRINT\",\"title\":\"Print Card Id\"},{\"icon\":\"PRINT\",\"title\":\"Print Slip Gaji\"}]";
        try {
            JSONArray jsonArray= (JSONArray) new JSONParser().parse(jsonString);
            jsonArray.forEach(data -> {
                HBox hBox=new HBox();
                hBox.getStyleClass().add("menu_pane");
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(0,15,0,15));
                hBox.setSpacing(15.0);
                hBox.setPrefHeight(35);
                hBox.setMinHeight(Region.USE_PREF_SIZE);
                hBox.setCursor(Cursor.HAND);

                FontAwesomeIcon fontAwesomeIcon=new FontAwesomeIcon();
                fontAwesomeIcon.setSize("1.5em");
                fontAwesomeIcon.getStyleClass().add("menu_icon");
                Label label=new Label();
                label.getStyleClass().add("menu_title");
                hBox.setId(((JSONObject) data).get("title").toString());
                fontAwesomeIcon.setGlyphName(((JSONObject) data).get("icon").toString());
                label.setText(((JSONObject) data).get("title").toString());
                hBox.getChildren().addAll(fontAwesomeIcon,label);
                hBox.setOnMouseClicked(event -> {
                    if(hBox.getId().toLowerCase().equals("print card id")){
                    }
                    try {
                        Report_ViewerController.getInstance().setParameters(create_title_parameters());
                        Report_ViewerController.getInstance().setDs(createDatasource(Datasource));
                    } catch (JRException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(() -> {
                        LoaderController.getInstance().pProperty().setValue("REPORT_VIEWER");
                        stageManager.openLoader(HomeController.getInstance().content,true);

                       // stageManager.openNode(HomeController.getInstance().content,null,FxmlView.LOADER,false);
                       // stageManager.openNode(HomeController.getInstance().Home_root,null,FxmlView.REPORT_VIEWER,false);
                    });

                });

                panePrint.getChildren().addAll(hBox);
            });
            layout.getChildren().addAll(panePrint,closelayout);
            AnchorPane.setRightAnchor(closelayout,-1.0);
            AnchorPane.setTopAnchor(closelayout,-1.0);
            AnchorPane.setLeftAnchor(panePrint,0.0);
            AnchorPane.setRightAnchor(panePrint,0.0);
            AnchorPane.setTopAnchor(panePrint,0.0);
            AnchorPane.setBottomAnchor(panePrint,0.0);
            root_pegawai.getChildren().add(layout);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //ReportSource
    private String DataSoruceJsonString(Pegawai karyawan)  {
        String data=null;
        JSONObject jsonObject=new JSONObject();
        try {
            data=mapper.writeValueAsString(karyawan);
            System.out.println(data);
            if(!data.startsWith("[")){
                jsonObject=new JSONObject();
                jsonObject= (JSONObject) new JSONParser().parse(data);
                jsonObject.remove("children");
                jsonObject.remove("groupedColumn");
                jsonObject.remove("groupedValue");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }

    private JsonDataSource createDatasource(String ds) throws JRException {
        ByteArrayInputStream jsonDataStream = new ByteArrayInputStream(ds.getBytes());
        JsonDataSource jds = new JsonDataSource(jsonDataStream);
        return jds;
    }

    private Map create_title_parameters(){
        Map parameters = new HashMap();
        parameters.put("nik","nik");
        parameters.put("nama","nama");
        parameters.put("photo","photo");
        parameters.put("jabatan","jabatan");
        return parameters;
    }
}
