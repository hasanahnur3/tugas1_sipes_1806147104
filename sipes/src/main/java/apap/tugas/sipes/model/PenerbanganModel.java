package apap.tugas.sipes.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="penerbangan")
public class PenerbanganModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=255)
    @Column(name="kode_bandara_asal", nullable = false)
    private String kode_bandara_asal;


    @NotNull
    @Size(max=255)
    @Column(name="kode_bandara_tujuan", nullable = false)
    private String kode_bandara_tujuan;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktu_berangkat;

public LocalDateTime getWaktu_berangkat() {
	return this.waktu_berangkat;
}
public void setWaktu_berangkat(LocalDateTime waktu_berangkat) {
	this.waktu_berangkat = waktu_berangkat;
}


    @NotNull
    @Size(max=255)
    @Column(name="nomor_penerbangan", nullable = false, unique = true)
    private String nomor_penerbangan;

    public String getNomor_penerbangan() {
        return this.nomor_penerbangan;
    }
    public void setNomor_penerbangan(String nomor_penerbangan) {
        this.nomor_penerbangan = nomor_penerbangan;
    }

 
    //Relationship
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pesawat", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PesawatModel pesawat;

    public PesawatModel getPesawat() {
        return this.pesawat;
    }
    public void setPesawat(PesawatModel pesawat) {
        this.pesawat = pesawat;
    }


    //Setter Getter
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getKode_bandara_asal() {
        return this.kode_bandara_asal;
    }
    public void setKode_bandara_asal(String kode_bandara_asal) {
        this.kode_bandara_asal = kode_bandara_asal;
    }

    public String getKode_bandara_tujuan() {
        return this.kode_bandara_tujuan;
    }
    public void setKode_bandara_tujuan(String kode_bandara_tujuan) {
        this.kode_bandara_tujuan = kode_bandara_tujuan;
    }
}
