package apap.tugas.sipes.service;

import java.util.List;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;

public interface PesawatService {
    void addPesawat(PesawatModel pesawat);
    List<PesawatModel>getListPesawat();
    PesawatModel getPesawatById(Long id);
    PesawatModel updatePesawat(PesawatModel pesawat);
    void deletePesawat(PesawatModel pesawat);
    List<String> getAllNoSeri();
    
}
