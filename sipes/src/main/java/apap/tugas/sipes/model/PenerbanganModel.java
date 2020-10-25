package apap.tugas.sipes.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
 
    //Relationship
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idPesawat", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PesawatModel pesawat;

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
