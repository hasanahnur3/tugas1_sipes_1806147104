package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;

import apap.tugas.sipes.repository.TeknisiDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Component("teknisiServiceImpl")
public class TeknisiServiceImpl implements TeknisiService {

    @Autowired
    TeknisiDb teknisiDb;
    
    @Override
    public void addTeknisi(TeknisiModel teknisi){
        teknisiDb.save(teknisi);
    }

    @Override
    public List<TeknisiModel> getListTeknisi() {
        return teknisiDb.findAll();
    }

    @Override
    public TeknisiModel getTeknisiById(Long id) {
        return teknisiDb.findById(id).get();
    }
}
