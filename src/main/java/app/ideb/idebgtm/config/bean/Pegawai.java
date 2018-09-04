package app.ideb.idebgtm.config.bean;


import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class Pegawai extends RecursiveTreeObject<Pegawai> {
    private SimpleStringProperty _id =new SimpleStringProperty(this,"","");
    private SimpleStringProperty nik =new SimpleStringProperty(this,"","");
    private SimpleStringProperty nama =new SimpleStringProperty(this,"","");
    private SimpleStringProperty tempat_lahir =new SimpleStringProperty(this,"","");
    private SimpleStringProperty tgl_lahir =new SimpleStringProperty(this,"","");
    private SimpleStringProperty jenkel =new SimpleStringProperty(this,"","");
    private SimpleStringProperty email =new SimpleStringProperty(this,"","");
    private SimpleStringProperty alamat =new SimpleStringProperty(this,"","");
    private SimpleStringProperty agama =new SimpleStringProperty(this,"","");
    private SimpleStringProperty status =new SimpleStringProperty(this,"","");
    private SimpleStringProperty status_karyawan =new SimpleStringProperty(this,"","");
    private SimpleStringProperty jenis_karyawan =new SimpleStringProperty(this,"","");
    private SimpleStringProperty golongan =new SimpleStringProperty(this,"","");
    private SimpleStringProperty pendidikan =new SimpleStringProperty(this,"","");
    private SimpleStringProperty jabatan=new SimpleStringProperty(this,"","");
    private SimpleStringProperty tgl_masuk =new SimpleStringProperty(this,"","");
    private SimpleStringProperty masa_kerja =new SimpleStringProperty(this,"","");
    private SimpleStringProperty notelp =new SimpleStringProperty(this,"","");
    private SimpleStringProperty kode_kantor =new SimpleStringProperty(this,"","");
    private SimpleStringProperty photo =new SimpleStringProperty(this,"","iVBORw0KGgoAAAANSUhEUgAAAgAAAAIAAgMAAACJFjxpAAAADFBMVEXFxcX////p6enW1tbAmiBwAAAFiElEQVR4AezAgQAAAACAoP2pF6kAAAAAAAAAAAAAAIDbu2MkvY0jiuMWWQoUmI50BB+BgRTpCAz4G6C8CJDrC3AEXGKPoMTlYA/gAJfwETawI8cuBs5Nk2KtvfiLW+gLfK9m+r3X82G653+JP/zjF8afP1S//y+An4/i51//AsB4aH+/QPD6EQAY/zwZwN8BAP50bh786KP4+VT+3fs4/noigEc+jnHeJrzxX+NWMDDh4g8+EXcnLcC9T8U5S/CdT8bcUeBEIrwBOiI8ki7Ba5+NrePgWUy89/nYyxQ8Iw3f+pWY4h1gb3eAW7sDTPEOsLc7wK1TIeDuDB+I/OA1QOUHv/dFsZQkhKkh4QlEfOULYz2nGj2/Nn1LmwR/86VxlCoAW6kCsHRGANx1RgCMo5Qh2EsZgrXNQZZShp5Liv7Il8eIc5C91EHY2hxk6bwYmNscZIReDBwtCdhbErC1JGBpScBcOgFMLQsZMQs5Whayd+UQsLYsZGlZyNyykKllISNmIUfAwifw8NXvTojAjGFrdYi11SGWVoeYWx1i6lmQCiEjFkKOVgjZ+xxIhZCtFULWHkCqxCw9gNQKmP9vNHzipdEPrRcxtVbAeDkAvve0iM2QozVD9hfjhp4YP/UrkJYDbD2AtBxgfSkAvvHEeNcDSAsilgtAWxIy91J8AXgZAJ5e33+4tuACcAG4AFwALgBXRXQB6AFcB5MXAuA6nl9/0Vx/011/1V5/1/dfTPJvRtdnu/zL6beeFO/7r+fXBYbrEkt/j+i6ytXfpuvvE/ZXOnsA/a3a/l5xf7O6v1t+Xe/vOyz6HpO8yyboM8o7rfJes77bru83THk48p7TvOs27zvOO6/73vO++z7l4cgnMPQzKPopHC0N9noSSz6LJp/Gk88jyicy5TOp6qlc+VyyfDJbPpuuns6XzyfMJzTmMyrrKZ35nNJ8Ums+q7af1tvPK+4nNodEnPKp3fnc8npyez67/qVP7+/fL8hfcMjfsOhf8cjfMclfcnn9+BkOnLECP8Q58OYeyJ40eoyF6Ee/En/JHlP6mIlRVXprF4BxtAvArV0AxtEuALd2ARhHuwDc2gVgHPX/hFv9fMBddjIGeKg/WCxlCsI46u+Ga5mCcJd+sIG9UkGAW32ZbApFAHhod4Bb3eo04h3god0BbiUHYApVCNjbHeBW+QDAXT4a7qg7r7e214057vg0QhkEHkoSwq0kIdydXw4/Q3H8hjYJ3vL0WConBJhCHQaOToeBrU0BljYFmEoVgHGUKgAPnREAt84IgLuqFgAYSUEOAHszDwuAtSkHAZhLGYIpdCLgKGUIHtocZG1zkLmUIRhxDnJU1RDA1uYga5uDzKUOwhTnIEfnxcDe5iBrcyQAYGlzkKkUYhhxDrKXQgxbSwLWUohhbknA1JKAEZOAvSUBW0sC1pYEzC0JmFoSMMJyCDhaFrK3JGDtyiFgaVnI3LKQqWUhI2YhR8tC9paFrC0LWVoWMrcsZGpZyIhZyNGykL2rSIGtlQHWVgZYWhlgbmWAqZUBRiwDHK0MsLcywNbKAGsOoNUhllaHmFsdYmp1iBHrEEerQ+w5gFYI2VodYm11iKXVIeYcQCuETK0QMmIh5MgBtELI3gohWyuErDmAVolZWiFkzgG0SszUKjGjfj6gVmKOVonZcwCtFbB9HQC+ozWDbz1bvGu9iKW1AuYcQOtFTLEX1GbIaFegN0OOHEBrhuw5gNYM2XIArRuz5gDacoB3bTnAEktxXQ4wfw0AvveM8b4tiJjSJOwLIsbXsAKeNeKCiOO3D+AVbUl0AfjGs8ZPbUnIdgFoa1LWC0BblfMuB9AeC1j6gqQE0J9LmC8AOYD2ZMb7i4bt2ZTpWoHfPoB7Tj2fXzT8N1X41vkq/QHOAAAAAElFTkSuQmCC");
    private SimpleStringProperty createdAt=new SimpleStringProperty(this,"","");
    private SimpleStringProperty updatedAt=new SimpleStringProperty(this,"","");

    public Pegawai() {
    }

  /*  public Pegawai(String _id,String nik, String nama, String tempat_lahir, String tgl_lahir,
                    String jenkel, String email, String alamat, String agama, String status,
                    String status_karyawan, String jenis_karyawan, String golongan,
                    String pendidikan, String tgl_masuk, String masa_kerja, String notelp,
                    String kode_kantor, String photo) {
        this._id = new SimpleStringProperty(_id);
        this.nik = new SimpleStringProperty(nik);
        this.nama = new SimpleStringProperty(nama);
        this.tempat_lahir = new SimpleStringProperty(tempat_lahir);
        this.tgl_lahir = new SimpleStringProperty(tgl_lahir);
        this.jenkel = new SimpleStringProperty(jenkel);
        this.email = new SimpleStringProperty(email);
        this.alamat = new SimpleStringProperty(alamat);
        this.agama = new SimpleStringProperty(agama);
        this.status = new SimpleStringProperty(status);
        this.status_karyawan = new SimpleStringProperty(status_karyawan);
        this.jenis_karyawan = new SimpleStringProperty(jenis_karyawan);
        this.golongan = new SimpleStringProperty(golongan);
        this.pendidikan = new SimpleStringProperty(pendidikan);
        this.tgl_masuk = new SimpleStringProperty(tgl_masuk);
        this.masa_kerja = new SimpleStringProperty(masa_kerja);
        this.notelp = new SimpleStringProperty(notelp);
        this.kode_kantor = new SimpleStringProperty(kode_kantor);
        this.photo = new SimpleStringProperty(photo);
    }*/

    public Pegawai(String _id, String nik, String nama, String tempat_lahir, String tgl_lahir,
                   String jenkel, String email, String alamat, String agama, String status,
                   String status_karyawan, String jenis_karyawan, String golongan,
                   String pendidikan, String jabatan, String tgl_masuk, String masa_kerja, String notelp,
                   String kode_kantor, String photo, String createdAt, String updatedAt) {
        this._id = new SimpleStringProperty(_id);
        this.nik = new SimpleStringProperty(nik);
        this.nama = new SimpleStringProperty(nama);
        this.tempat_lahir = new SimpleStringProperty(tempat_lahir);
        this.tgl_lahir = new SimpleStringProperty(tgl_lahir);
        this.jenkel = new SimpleStringProperty(jenkel);
        this.email = new SimpleStringProperty(email);
        this.alamat = new SimpleStringProperty(alamat);
        this.agama = new SimpleStringProperty(agama);
        this.status = new SimpleStringProperty(status);
        this.status_karyawan = new SimpleStringProperty(status_karyawan);
        this.jenis_karyawan = new SimpleStringProperty(jenis_karyawan);
        this.golongan = new SimpleStringProperty(golongan);
        this.pendidikan = new SimpleStringProperty(pendidikan);
        this.jabatan = new SimpleStringProperty(jabatan);
        this.tgl_masuk = new SimpleStringProperty(tgl_masuk);
        this.masa_kerja = new SimpleStringProperty(masa_kerja);
        this.notelp = new SimpleStringProperty(notelp);
        this.kode_kantor = new SimpleStringProperty(kode_kantor);
        this.photo = new SimpleStringProperty(photo);
        this.createdAt = new SimpleStringProperty(createdAt);
        this.updatedAt = new SimpleStringProperty(updatedAt);
    }


    public String get_id() {
        return _id.get();
    }

    public SimpleStringProperty _idProperty() {
        return _id;
    }

    public void set_id(String _id) {
        this._id.set(_id);
    }

    public String getNik() {
        return nik.get();
    }

    public SimpleStringProperty nikProperty() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik.set(nik);
    }

    public String getNama() {
        return nama.get();
    }

    public SimpleStringProperty namaProperty() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    public String getTempat_lahir() {
        return tempat_lahir.get();
    }

    public SimpleStringProperty tempat_lahirProperty() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir.set(tempat_lahir);
    }

    public String getTgl_lahir() {
        return tgl_lahir.get();
    }

    public SimpleStringProperty tgl_lahirProperty() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir.set(tgl_lahir);
    }

    public String getJenkel() {
        return jenkel.get();
    }

    public SimpleStringProperty jenkelProperty() {
        return jenkel;
    }

    public void setJenkel(String jenkel) {
        this.jenkel.set(jenkel);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getAlamat() {
        return alamat.get();
    }

    public SimpleStringProperty alamatProperty() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat.set(alamat);
    }

    public String getAgama() {
        return agama.get();
    }

    public SimpleStringProperty agamaProperty() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama.set(agama);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getStatus_karyawan() {
        return status_karyawan.get();
    }

    public SimpleStringProperty status_karyawanProperty() {
        return status_karyawan;
    }

    public void setStatus_karyawan(String status_karyawan) {
        this.status_karyawan.set(status_karyawan);
    }



    public String getGolongan() {
        return golongan.get();
    }

    public SimpleStringProperty golonganProperty() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan.set(golongan);
    }

    public String getPendidikan() {
        return pendidikan.get();
    }

    public SimpleStringProperty pendidikanProperty() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan.set(pendidikan);
    }

    public String getTgl_masuk() {
        return tgl_masuk.get();
    }

    public SimpleStringProperty tgl_masukProperty() {
        return tgl_masuk;
    }

    public void setTgl_masuk(String tgl_masuk) {
        this.tgl_masuk.set(tgl_masuk);
    }

    public String getMasa_kerja() {
        return masa_kerja.get();
    }

    public SimpleStringProperty masa_kerjaProperty() {
        return masa_kerja;
    }

    public void setMasa_kerja(String masa_kerja) {
        this.masa_kerja.set(masa_kerja);
    }

    public String getNotelp() {
        return notelp.get();
    }

    public SimpleStringProperty notelpProperty() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp.set(notelp);
    }

    public String getKode_kantor() {
        return kode_kantor.get();
    }

    public SimpleStringProperty kode_kantorProperty() {
        return kode_kantor;
    }

    public void setKode_kantor(String kode_kantor) {
        this.kode_kantor.set(kode_kantor);
    }

    public String getPhoto() {
        return photo.get();
    }

    public SimpleStringProperty photoProperty() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo.set(photo);
    }

    public String getCreatedAt() {
        return createdAt.get();
    }

    public SimpleStringProperty createdAtProperty() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt.set(createdAt);
    }

    public String getUpdatedAt() {
        return updatedAt.get();
    }

    public SimpleStringProperty updatedAtProperty() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt.set(updatedAt);
    }

    public String getJenis_karyawan() {
        return jenis_karyawan.get();
    }

    public SimpleStringProperty jenis_karyawanProperty() {
        return jenis_karyawan;
    }

    public void setJenis_karyawan(String jenis_karyawan) {
        this.jenis_karyawan.set(jenis_karyawan);
    }

    public String getJabatan() {
        return jabatan.get();
    }

    public SimpleStringProperty jabatanProperty() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan.set(jabatan);
    }

    @Override
    public String toString() {
        return "Pegawai{" +
                "_id=" + _id +
                ", nik=" + nik +
                ", nama=" + nama +
                ", tempat_lahir=" + tempat_lahir +
                ", tgl_lahir=" + tgl_lahir +
                ", jenkel=" + jenkel +
                ", email=" + email +
                ", alamat=" + alamat +
                ", agama=" + agama +
                ", status=" + status +
                ", status_karyawan=" + status_karyawan +
                ", jenis_karyawan=" + jenis_karyawan +
                ", golongan=" + golongan +
                ", pendidikan=" + pendidikan +
                ", jabatan=" + jabatan +
                ", tgl_masuk=" + tgl_masuk +
                ", masa_kerja=" + masa_kerja +
                ", notelp=" + notelp +
                ", kode_kantor=" + kode_kantor +
                ", photo=" + photo +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
