package apap.tugas.sipes.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="teknisi")
public class TeknisiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    @NotNull
    @Size(max=255)
    @Column(name="nama", nullable = false)
    private String nama;

    public String getNama() {
        return this.nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }


    @NotNull
    @Column(name = "nomor_telepon", nullable = false)
    private Long nomor_telepon;

    public Long getNomor_telepon() {
        return this.nomor_telepon;
    }
    public void setNomor_telepon(Long nomor_telepon) {
        this.nomor_telepon = nomor_telepon;
    }

    //Relationship
    @ManyToMany(mappedBy = "listTeknisi")
    private List<PesawatModel> listPesawat;




    
}
