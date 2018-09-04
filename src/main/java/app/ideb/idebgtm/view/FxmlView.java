package app.ideb.idebgtm.view;

import java.util.ResourceBundle;

public enum FxmlView {

    HOME {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("home.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Home.fxml";
        }
    },

    USER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("user.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/User.fxml";
        }
    },
    LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Login.fxml";
        }
    },
    DASHBOARD {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("dashboard.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Dashboard.fxml";
        }
    },
    PEGAWAI {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("pegawai.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Pegawai.fxml";
        }
    },
    SETTING {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("setting.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/setting/Setting.fxml";
        }
    },
    SETTING_PARAMETER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("parameter.setting.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/setting/Parameter.fxml";
        }
    },
    SETTING_SYSTEM {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("system.setting.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/setting/System.fxml";
        }
    },
    SETTING_PPH21 {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("pph21.setting.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/setting/Pph21.fxml";
        }
    },
    SETTING_BPJS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("bpjs.setting.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/setting/Bpjs.fxml";
        }
    },
    LAPORAN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("laporan.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Laporan.fxml";
        }
    },
    PENGGAJIAN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("penggajian.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Penggajian.fxml";
        }
    },
    LOADER {
        @Override
        public String getTitle() {
            return "";
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/loader/Loader.fxml";
            //return getClass().getResource("/fxml/loader/loader.fxml").toString();
        }
    },REPORT_VIEWER {
        @Override
        public String getTitle() {
            return "";
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/report/Report_Viewer.fxml";
        }
    }

    , PRINT_VIEW {
        @Override
        public String getTitle() {
            return "";
        }

        @Override
        public String getFxmlFile() {
            return "/print/print_prev.fxml";
        }
    }, CARD_ID_LAYOUT {
        @Override
        public String getTitle() {
            return "";
        }

        @Override
        public String getFxmlFile() {
            return "/print/card_id_layout.fxml";
        }
    };
    
    public abstract String getTitle();
    public abstract String getFxmlFile();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
