package apap.tugas.sipes.service;

import java.util.List;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;

public interface TeknisiService {
    void addTeknisi(TeknisiModel teknisi);
    List<TeknisiModel> getListTeknisi();
    TeknisiModel getTeknisiById(Long id);
}
