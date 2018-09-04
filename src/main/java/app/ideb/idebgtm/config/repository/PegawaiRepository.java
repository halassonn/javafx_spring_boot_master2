package app.ideb.idebgtm.config.repository;

import app.ideb.idebgtm.config.bean.Pegawai;
import javafx.collections.ObservableList;

public interface PegawaiRepository {
    ObservableList<Pegawai> getAll();
    ObservableList<Pegawai> findByNik(String nik);
    ObservableList<String> getAgama();
    ObservableList<String> getPendidikan();
    ObservableList<String> getGolongan();
    ObservableList<String> getStatusKawin();
    ObservableList<String> getJenisKaryawan();
    ObservableList<String> getJabatan();
    String save(Pegawai karyawan);
    String delete(String id);
    String update(String id, Pegawai karyawan);

}
