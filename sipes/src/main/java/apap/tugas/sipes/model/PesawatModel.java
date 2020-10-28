package apap.tugas.sipes.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="pesawat")
public class PesawatModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=255)
    @Column(name="maskapai", nullable = false)
    private String maskapai;

    @NotNull
    @Size(max=255)
    @Column(name="nomor_seri", nullable = false, unique = true)
    private String nomor_seri;

    @NotNull
    @Size(max=255)
    @Column(name="tempat_dibuat", nullable = false)
    private String tempat_dibuat;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggal_dibuat;

    @NotNull
    @Size(max=255)
    @Column(name="jenis_pesawat", nullable = false)
    private String jenis_pesawat;

    //Relationship

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idTipe", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private TipeModel tipe;

    public TipeModel getTipe() {
        return this.tipe;
    }
    public void setTipe(TipeModel tipe) {
        this.tipe = tipe;
    }



    @OneToMany(mappedBy = "pesawat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PenerbanganModel> listPenerbangan;

    public List<PenerbanganModel> getListPenerbangan() {
        return this.listPenerbangan;
    }
    public void setListPenerbangan(List<PenerbanganModel> listPenerbangan) {
        this.listPenerbangan = listPenerbangan;
    }



    @ManyToMany(cascade = {
        // CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "pesawat_teknisi",
        joinColumns = @JoinColumn(name = "id_pesawat"),
        inverseJoinColumns = @JoinColumn(name = "id_teknisi")
    )
    private List<TeknisiModel> listTeknisi;
    public List<TeknisiModel> getListTeknisi() {
        return this.listTeknisi;
    }
    public void setListTeknisi(List<TeknisiModel> listTeknisi) {
        this.listTeknisi = listTeknisi;
    }


    //Setter Getter 
    public LocalDate getTanggal_dibuat() {
        return this.tanggal_dibuat;
    }
    public void setTanggal_dibuat(LocalDate tanggal_dibuat) {
        this.tanggal_dibuat = tanggal_dibuat;
    }
    public String getTempat_dibuat() {
        return this.tempat_dibuat;
    }
    public void setTempat_dibuat(String tempat_dibuat) {
        this.tempat_dibuat = tempat_dibuat;
    }
    public String getNomor_seri() {
        return this.nomor_seri;
    }
    public void setNomor_seri(String nomor_seri) {
        this.nomor_seri = nomor_seri;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMaskapai() {
    	return this.maskapai;
    }
    public void setMaskapai(String maskapai) {
    	this.maskapai = maskapai;
    }
    public String getJenis_pesawat() {
        return this.jenis_pesawat;
    }
    public void setJenis_pesawat(String jenis_pesawat) {
        this.jenis_pesawat = jenis_pesawat;
    }

}