package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;

import apap.tugas.sipes.repository.PesawatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@Component("pesawatServiceImpl")
public class PesawatServiceImpl implements PesawatService {

    @Autowired
    PesawatDb pesawatDb;
    
    @Override
    public void addPesawat(PesawatModel pesawat){
        pesawatDb.save(pesawat);
    }

    @Override
    public List<PesawatModel> getListPesawat(){
        return pesawatDb.findAll();
    }

    @Override
    public PesawatModel getPesawatById(Long id) {
        return pesawatDb.findById(id).get();
    }

    @Override
	public PesawatModel updatePesawat(PesawatModel pesawat) {
        
        PesawatModel targetPesawat = pesawatDb.findById(pesawat.getId()).get();

        // try{
            targetPesawat.setMaskapai(pesawat.getMaskapai());
            targetPesawat.setTanggal_dibuat(pesawat.getTanggal_dibuat());
            targetPesawat.setTempat_dibuat(pesawat.getTempat_dibuat());
            targetPesawat.setJenis_pesawat(pesawat.getJenis_pesawat());
            targetPesawat.setNomor_seri(pesawat.getNomor_seri());
           

            pesawatDb.save(targetPesawat);

            return targetPesawat;

        // }catch (NullPointerException nullException){
        //     return null;
        // }
    }
    
}
