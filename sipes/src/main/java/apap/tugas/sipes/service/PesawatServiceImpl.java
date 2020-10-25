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

        try{
            targetPesawat.setMaskapai(pesawat.getMaskapai());
            targetPesawat.setTanggal_dibuat(pesawat.getTanggal_dibuat());
            targetPesawat.setTempat_dibuat(pesawat.getTempat_dibuat());
            targetPesawat.setJenis_pesawat(pesawat.getJenis_pesawat());

            String ns = generateNoSeri(pesawat);
            pesawat.setNomor_seri(ns);
            pesawatDb.save(targetPesawat);
            
            return targetPesawat;

        }catch (NullPointerException nullException){
            return null;
        }
    }
    
    @Override
    public String generateNoSeri(PesawatModel pesawat){
        //GENERATE NOMOR SERI
        String ns = "";
        if (pesawat.getJenis_pesawat().equals("Komersial")) ns+=1;
        else ns+="2";

        if(pesawat.getTipe().getNama().equals("BOEING")) ns+="BO";
        if(pesawat.getTipe().getNama().equals("ATR")) ns+="AT";
        if(pesawat.getTipe().getNama().equals("Airbus")) ns+="AB";
        if(pesawat.getTipe().getNama().equals("Bombardier")) ns+="BB";

        int year = pesawat.getTanggal_dibuat().getYear();
        String stryear = Integer.toString(year);
        StringBuilder sb=new StringBuilder(stryear);  
        sb.reverse();  
        String fixyear = sb.toString();
        ns+=fixyear;

        year +=8;
        stryear = Integer.toString(year);
        ns+=stryear;

        Random r = new Random();
        char c1 = (char)(r.nextInt(26) + 'a');
        char c2 = (char)(r.nextInt(26) + 'a');
        String rc1 = String.valueOf(c1).toUpperCase();
        String rc2 = String.valueOf(c2).toUpperCase();
        ns+=rc1;
        ns+=rc2;

        return ns;
    }
}
