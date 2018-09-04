package app.ideb.idebgtm.config;

import app.ideb.idebgtm.config.bean.ApiResponse;
import app.ideb.idebgtm.controller.HomeController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class AppUtil {
    JFXDialogLayout alertlayout;
    JFXDialog alert;
    private Properties config;
    private String home_folder="user.home";
    private String App_Folder=".Sysg_app";
    private String App_Report_Folder=".Sysg_app.Report";
    private Path configLocation = Paths.get(System.getProperty(home_folder), App_Folder, "SYSG_config.properties");
   // private Path configLocation = Paths.get(System.getProperty(home_folder), App_Report_Folder, "SYSG_config.properties");


    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    // GET DATA fROM Bundle Properties file by key
    public String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
    //GET THEMA INIT
    public String getStringFromResourceBundlethema(String key){
        //ResourceBundle.getBundle("thema").getString("app.thema").replace("da")
        return ResourceBundle.getBundle("application").getString(key);
    }

    //Write App Config To OS
    public void Copy_App_conf() throws IOException {
        if (! Files.exists(configLocation)) {
            // create directory if needed
            if (! Files.exists(configLocation.getParent())) {
                Files.createDirectory(configLocation.getParent());
            }

            // extract default config from jar and copy to config location:

            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/application.properties")));
                    BufferedWriter out = Files.newBufferedWriter(configLocation);) {

                in.lines().forEach(line -> {
                    try {
                        out.append(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception exc) {
                // handle exception, e.g. log and warn user config could not be created
            }
        }

    }
    //SET THEME FROM CONFIG.PROPERTIES
    public void settheme(Parent node, String theme) throws IOException, ConfigurationException {

        Platform.runLater(() -> {
            try {
                BufferedReader in = Files.newBufferedReader(configLocation);
                config.load(in);
                PropertiesConfiguration config = new PropertiesConfiguration(String.valueOf(configLocation));
                config.setProperty("theme_default", theme);
                config.save();
                getThema(HomeController.getInstance().Home_root.getParent());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }

        });
    }

    //GET CURRENT THEMA
    public void getThema(Parent node){
        config = new Properties();
        try (BufferedReader in = Files.newBufferedReader(configLocation)) {
            config.load(in);
        } catch (IOException exc) {
            // handle exception...
        }
        node.getScene().getStylesheets().clear();
        node.getScene().getStylesheets().add("/css/"+config.getProperty("theme_default")+".css");
    }

    //IMAGe TO Base64
    public String EncodeImageToBase64(String Imagepath) {
        String Base64String = "";
        File file = new File(Imagepath);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            fileInputStream.read(imageData);
            Base64String = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64String;

    }

    public byte[] DecodeBase64toImage2(String Base64String) {

        //FileOutputStream fileOutputStream=new FileOutputStream(pathfile);
        byte[] imageByteArray = Base64.getDecoder().decode(Base64String);
        // fileOutputStream.write(imageByteArray);
        return imageByteArray;
    }

    //DATE FORMAT

    public String formatedate(String tgl) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("Indonesia"));
        SimpleDateFormat DATE_FORMAT_PATTERN = new SimpleDateFormat("dd-MM-yyyy'0'0:0:0.0'0'");
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");

        Calendar calendar = new GregorianCalendar();
        Date date = null;
        format.setCalendar(calendar);
        try {
            date = f.parse(tgl);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(fmt.format(date));
    }

    public String formatedate2(String tgl) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("ID"));
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("Indonesia"));

        Date date = null;
        try {
            date = format.parse(tgl);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(format2.format(date));
    }

    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    //MASA KERJA
    public String getMasaKerja(StackPane root, LocalDate tanggalMasuk) {
        String masa_kerja = null;
        String tgl_masuk = null;
        LocalDate tglmasuk = LocalDate.parse(tanggalMasuk.format(formatter), formatter);
        LocalDate today = LocalDate.now();
        Period p = Period.between(today, tglmasuk);
        long hari = ChronoUnit.DAYS.between(tglmasuk, today);
        //long bulan = ChronoUnit.MONTHS.between(tglmasuk, today);
        long tahun = ChronoUnit.YEARS.between(tglmasuk, today);
        long bulan = 0;

        if (hari >= 0) {
            hari = hari % 365;
            if (hari > 30 && hari < 365) {
                bulan = (hari - (hari % 30)) / 30;
                hari = (hari % 30);
            }
            tgl_masuk = String.valueOf(tanggalMasuk);
        } else {
            hari = 0;
            showAlert(root, "Error Tanggal Masuk", "Tanggal Masuk Tidak Boleh Lebih Besar dari Waktu Sekarang", null);
            tgl_masuk = null;
        }
        masa_kerja = tahun + " thn " + bulan + " bln " + hari + " hari ";
        return masa_kerja;

    }

    //Format NUMBER
    public NumberFormat FormatUang() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String pattern = ((DecimalFormat) nf).toPattern();
        String newPattern = pattern.replace("\u00A4", "").trim();
        // String newPattern2 = pattern.replace("\u00A4", "Rp. ").trim();
        NumberFormat newFormat = new DecimalFormat(newPattern);
        return newFormat;
    }

    public DecimalFormat FormatUangRp() {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        //formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia;
    }

    //CRATE NEW NIK
    public String createNik(JFXDatePicker t, String kodekantor) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //convert String to LocalDate
        LocalDate tgllahir = LocalDate.parse(t.getValue().format(formatter), formatter);
        String nik = null;
        if (tgllahir.getMonthValue() <= 9 && tgllahir.getMonthValue() > 0) {
            nik = (String) kodekantor.concat("0").concat(String.valueOf(tgllahir.getMonthValue()).concat(String.valueOf(t.getValue().getYear())));
        } else {
            nik = (String) kodekantor.concat(String.valueOf(tgllahir.getMonthValue()).concat(String.valueOf(t.getValue().getYear())));
        }
        return nik;
    }
    //SORT DATA IN JSON ARRAY
    public JSONArray sortJsonArray(JSONArray jsonArray, String by) {
        JSONArray sortedJsonArray = new JSONArray();
        List<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonValues.add((JSONObject) jsonArray.get(i));
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            //You can change "Name" with "ID" if you want to sort by ID
            private String KEY_SORT = by;

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = new String();
                String valB = new String();


                valA = (String) a.get(KEY_SORT);
                valB = (String) b.get(KEY_SORT);


                return valA.compareTo(valB);
                //if you want to change the sort order, simply use the following:
                //return -valA.compareTo(valB);
            }
        });
        for (int i = 0; i < jsonArray.size(); i++) {
            sortedJsonArray.add(jsonValues.get(i));
        }
        return sortedJsonArray;
    }

    //Show default Response
    public void showAPIRESPONSEDIALOG(String res, ObservableList<ApiResponse> defaultapiResponses) {
        try {
            JSONArray jsonArray1 = (JSONArray) new JSONParser().parse(res);
            JSONObject jsonObject2 = (JSONObject) jsonArray1.get(0);
            System.out.println(jsonObject2);
            defaultapiResponses.clear();
            defaultapiResponses.addAll(new ApiResponse(
                    Boolean.parseBoolean(jsonObject2.get("success").toString()),
                    jsonObject2.get("message").toString(),
                    jsonObject2.get("status").toString()));
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }


    //ALERT
    public void showAlert(StackPane container, String title, String message, Node action) {

        alertlayout = new JFXDialogLayout();
        alertlayout.setHeading(new Label(title));
        alertlayout.setMaxWidth(300);
        alertlayout.setPrefWidth(300);
        alertlayout.setMinWidth(50);
        Text tmessage = new Text(message);

        Label Ltitle = new Label(title);
        FontAwesomeIcon icon = new FontAwesomeIcon();
        FontAwesomeIcon iconclose = new FontAwesomeIcon();
        iconclose.setGlyphName("CLOSE");
        iconclose.setSize("1.5em");
        iconclose.setFill(Color.rgb(74, 74, 74, 0.61));
        icon.setSize("3.5em");

        Ltitle.setFont(Font.font("Verdana", 14));
        tmessage.setFont(Font.font("Verdana", 12));

        TextFlow textFlow = new TextFlow(tmessage);
        HBox hBox = new HBox(icon, textFlow);
        hBox.setSpacing(15);
        hBox.setAlignment(Pos.CENTER_LEFT);
        JFXButton close = new JFXButton();
        close.setGraphicTextGap(0);
        close.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        close.setCursor(Cursor.HAND);
        close.setOnAction(event -> {
            alert.close();
        });
        close.setGraphic(iconclose);

        AnchorPane header = new AnchorPane(Ltitle, close);
        header.setPrefHeight(15.0);
        AnchorPane.setTopAnchor(Ltitle, 0.0);
        AnchorPane.setLeftAnchor(Ltitle, 0.0);
        AnchorPane.setTopAnchor(close, 0.0);
        AnchorPane.setRightAnchor(close, 0.0);
        header.setLayoutY(-10.0);

        alertlayout.setHeading(header);
        alertlayout.setBody(hBox);
        if (action != null) {
            alertlayout.setActions(action);
        }

        alert = new JFXDialog(container, alertlayout, JFXDialog.DialogTransition.CENTER);
        alert.setOverlayClose(false);

        if (title.toLowerCase().equals("error") || title.toLowerCase().contains("error")) {
            Ltitle.setText("Error");
            icon.setGlyphName("CLOSE");
            alertlayout.setStyle("-fx-border-color:firebrick;-fx-background-color: rgba(246, 57, 47, 0.62)");
            tmessage.setStyle("-fx-fill: red");
            Ltitle.setStyle("-fx-text-fill: red");
            icon.setFill(Color.RED);

        } else if (title.toLowerCase().equals("success") || title.toLowerCase().equals("created")) {
            Ltitle.setText("Success");
            icon.setGlyphName("CHECK");
            alertlayout.setStyle("-fx-border-color: green;-fx-background-color: rgba(92, 246, 82, 0.62)");
            tmessage.setStyle("-fx-fill: green");
            Ltitle.setStyle("-fx-text-fill: green");
            icon.setFill(Color.GREEN);

        } else if (title.toLowerCase().equals("hapus")) {
            Ltitle.setText("Hapus");
            icon.setGlyphName("TRASH");
            alertlayout.setStyle("-fx-border-color: #b4621a;-fx-background-color: #f68c34");
            tmessage.setStyle("-fx-fill: #f9ffff");
            Ltitle.setStyle("-fx-text-fill: #f9ffff");
            icon.setFill(Color.WHITESMOKE);

        }

        container.requestFocus();
        alert.show();

    }

    public void closeAlert() {
        this.alert.close();
    }


    //PRINTER

    //get List Printer
    public ObservableSet getAllPrinter() {
        ObservableSet<Printer> printers = Printer.getAllPrinters();
        return printers;
    }
    //get List Paper
    public ObservableSet<Paper> getAllPaper() {
        ObservableSet<Paper> papers = FXCollections.observableSet();
        papers.add(Paper.A0);
        papers.add(Paper.A1);
        papers.add(Paper.A2);
        papers.add(Paper.A3);
        papers.add(Paper.A4);
        papers.add(Paper.A5);
        papers.add(Paper.A6);
        papers.add(Paper.LEGAL);
        papers.add(Paper.NA_8X10);
        return papers;
    }
    public void printNode(Node node) {
        System.out.println("Creating a printer job...");
        PrinterJob job = PrinterJob.createPrinterJob();
        PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        if (job == null) {
            return;
        }

        // Show the print setup dialog
        boolean proceed = job.showPrintDialog(HomeController.getInstance().content.getScene().getWindow());
        System.out.println(proceed);
        if (proceed) {
            // Set the Job Status Message
            //jobStatus.textProperty().bind(job.jobStatusProperty().asString());
            // Print the node
            /*boolean printed = job.printPage(node);

            if (printed) {
                job.endJob();
            }*/

        }
       /* if (job != null) {
            System.out.println(job.jobStatusProperty().asString());

            boolean printed = job.printPage(node);
            if (printed) {
                job.endJob();
            } else {
                System.out.println("Printing failed.");
            }
        } else {
            System.out.println("Could not create a printer job.");
        }*/
    }

    public void print(Node node) {
        VBox vBox=new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);

        Platform.runLater(() -> {
            PrinterJob job = PrinterJob.createPrinterJob();
            PageLayout pageLayout = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            System.out.println(job.getJobSettings().getPageRanges());
            double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
            double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
            // for some reason the height doesn't fit
            scaleY *= 1.0;
            // scaleX *= 1.0;
            vBox.getChildren().add(node);
            //vBox.getTransforms().add(new Scale(scaleX, scaleY));
            // print

            /*if (job != null) {
                System.out.println(job.jobStatusProperty().asString());

                boolean printed = job.printPage(pageLayout,vBox);
                if (printed) {
                    job.endJob();
                } else {
                    System.out.println("Printing failed.");
                }
            } else {
                System.out.println("Could not create a printer job.");
            }*/
        });

    }


}
