package apap.tugas.sipes.service;

import java.util.List;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;

public interface PenerbanganService {
    void addPenerbangan(PenerbanganModel penerbangan);
    PenerbanganModel getPenerbanganById(Long id);
    List<PenerbanganModel> getListPenerbangan();
    PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan);
    void deletePenerbangan(PenerbanganModel penerbangan);
}
