package app.ideb.idebgtm.controller.report;

import app.ideb.idebgtm.config.AppUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Controller
public class Report_ViewerController implements Initializable {
    private static Report_ViewerController instance;
    public Report_ViewerController() {
        instance = this;
    }
    public JasperPrint jasperPrint = new JasperPrint();
    public JRViewerFxMode printMode;
    private ChangeListener<Number> zoomListener;
    private Map reportParameters;
    private Popup popup;
    private Label errorLabel;
    private List<Integer> pages;
    private Double zoomFactor;
    private double imageHeight;
    private double imageWidth;
    private JsonDataSource ds;
    private Map parameters;

    @Autowired
    AppUtil appUtil;

    @FXML
    private ImageView imageView;
    @FXML
    ComboBox<Integer> pageList;
    @FXML
    Slider zoomLevel;
    @FXML
    private TitledPane resultPane;
    @FXML
    private Accordion resultAccordion;
    @FXML
    private Label resultDescription;
    @FXML
    protected Node view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            InputStream inputStream=getClass().getResourceAsStream("/fxml/report/jasper/card_id.jasper");
            jasperPrint=JasperFillManager.fillReport(inputStream,getParameters(),getDs());
            //jasperPrint = JasperFillManager.fillReport("target/classes/fxml/report/jasper/card_id.jasper", getParameters(), getDs());
            //jasperPrint = JasperFillManager.fillReport("src/resources/report/jasper/card_id.jasper", getParameters(), getDs());
        } catch (JRException e) {
            e.printStackTrace();
        }
        printMode = JRViewerFxMode.REPORT_VIEW;
        Platform.runLater(() -> {
            show();
        });
    }

    @FXML
    void pageListSelected(ActionEvent event) {
        System.out.println(pageList.getSelectionModel().getSelectedItem() - 1);
        viewPage(pageList.getSelectionModel().getSelectedItem() - 1);
    }

    @FXML
    void print(ActionEvent event)  {
        try {
            JasperPrintManager.printReport(jasperPrint, true);
        } catch (JRException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //JasperViewer jv = new JasperViewer(jasperPrint);
        // jv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //jv.setVisible(true);
    }

    @FXML
    boolean save(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Document", Arrays.asList("*.pdf", "*.PDF")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG image", Arrays.asList("*.png", "*.PNG")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("DOCX Document", Arrays.asList("*.docx", "*.DOCX")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XLSX Document", Arrays.asList("*.xlsx", "*.XLSX")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Document", Arrays.asList("*.html", "*.HTML")));
        File file = fileChooser.showSaveDialog(view.getScene().getWindow());
                /*
                if( fileChooser.getSelectedExtensionFilter() !=null && fileChooser.getSelectedExtensionFilter().getExtensions()!=null){
			List<String> selectedExtension = fileChooser.getSelectedExtensionFilter().getExtensions();
			if(selectedExtension.contains("*.pdf")){
				try {
					JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
				} catch (JRException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}  else 	if(selectedExtension.contains("*.png")){
				for(int i =0 ; i<jasperPrint.getPages().size(); i++){
					String fileNumber = "0000" +Integer.toString(i+1);
					fileNumber = fileNumber.substring(fileNumber.length()-4,fileNumber.length());
					WritableImage image = getImage(i);
					String[] fileTokens = file.getAbsolutePath().split("\\.");
					String filename="";

					//add number to filename
					if(fileTokens.length>0){
						for(int i2= 0; i2<fileTokens.length-1; i2++){
							filename = filename + fileTokens[i2] +((i2<fileTokens.length-2)?".":"");
						}
						filename=filename+fileNumber+"." +  fileTokens[fileTokens.length-1];
					}else{
						filename = file.getAbsolutePath()+fileNumber ;
					}
					System.out.println(filename);
					File imageFile = new File(filename);
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
						System.out.println(imageFile.getAbsolutePath());
					} catch (IOException e) {
						TransactionResult t  = new TransactionResult();
						t.setResultNumber(-1);
						t.setResult("Error Saving Report");
						t.setResultDescription(e.getMessage());
						setTransactionResult(t);
						e.printStackTrace();
					}

				}

			} 	else 	if(selectedExtension.contains("*.html")){
				try {
					JasperExportManager.exportReportToHtmlFile(jasperPrint, file.getAbsolutePath());
				} catch (JRException e) {
					TransactionResult t  = new TransactionResult();
					t.setResultNumber(-1);
					t.setResult("Error Saving Report");
					t.setResultDescription(e.getMessage());
					setTransactionResult(t);
					e.printStackTrace();
				}
			} else 	if(selectedExtension.contains("*.docx")){
				JRDocxExporter exporter = new JRDocxExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,file.getAbsolutePath());
				try {
					exporter.exportReport();
				} catch (JRException e) {
					TransactionResult t  = new TransactionResult();
					t.setResultNumber(-1);
					t.setResult("Error Saving Report");
					t.setResultDescription(e.getMessage());
					setTransactionResult(t);
					e.printStackTrace();
				}
				System.out.println("docx");
			} else 	if(selectedExtension.contains("*.xlsx")){
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, file.getAbsolutePath());
				try {
					exporter.exportReport();
				} catch (JRException e) {
					TransactionResult t  = new TransactionResult();
					t.setResultNumber(-1);
					t.setResult("Error Saving Report");
					t.setResultDescription(e.getMessage());
					setTransactionResult(t);
					e.printStackTrace();
				}
				System.out.println("xlsx");
			}
		}
*/
        return false;
    }

    @FXML
    void close_viewer(ActionEvent event) {
        view.setVisible(false);
        view.toBack();
    }

    private void show() {
        if (reportParameters == null) reportParameters = new HashMap();
        if (printMode == null || printMode == JRViewerFxMode.REPORT_VIEW) {
            popup = new Popup();
            errorLabel = new Label("Error");
            errorLabel.setWrapText(true);
            errorLabel.setMaxHeight(200);
            errorLabel.setMinSize(100, 100);
            errorLabel.setMaxWidth(100);
            errorLabel.setAlignment(Pos.TOP_LEFT);
            // errorLabel.getStyleClass().add("errorToastLabel");
            popup.getContent().add(errorLabel);
            errorLabel.opacityProperty().bind(popup.opacityProperty());
            zoomFactor = 1d;
            zoomLevel.setValue(100d);
            imageView.setX(0);
            imageView.setY(0);

            imageHeight = jasperPrint.getPageHeight();
            imageWidth = jasperPrint.getPageWidth();
            if (zoomListener != null) {
                zoomLevel.valueProperty().removeListener(zoomListener);
            }
            zoomListener = new ChangeListener<Number>() {

                public void changed(ObservableValue<? extends Number> observable,
                                    Number oldValue, Number newValue) {
                    zoomFactor = newValue.doubleValue() / 100;
                    imageView.setFitHeight(imageHeight * zoomFactor);
                    imageView.setFitWidth(imageWidth * zoomFactor);
                }
            };

            zoomLevel.valueProperty().addListener(zoomListener);
            if (jasperPrint.getPages().size() > 0) {
                viewPage(0);
                pages = new ArrayList<Integer>();
                for (int i = 0; i < jasperPrint.getPages().size(); i++)
                    pages.add(i + 1);
            }
            pageList.setItems(FXCollections.observableArrayList(pages));
            pageList.getSelectionModel().select(0);
        }
    }


    private void viewPage(int pageNumber) {
        imageView.setFitHeight(imageHeight * zoomFactor);
        imageView.setFitWidth(imageWidth * zoomFactor);
        imageView.setImage(getImage(pageNumber));
    }

    private WritableImage getImage(int pageNumber) {
        BufferedImage image = null;
        try {
            image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, pageNumber, 2);
        } catch (JRException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        WritableImage fxImage = new WritableImage(jasperPrint.getPageWidth(), jasperPrint.getPageHeight());
        return SwingFXUtils.toFXImage(image, fxImage);

    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public JsonDataSource getDs() {
        return ds;
    }

    public void setDs(JsonDataSource ds) {
        this.ds = ds;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public static Report_ViewerController getInstance() {
        return instance;
    }
}
