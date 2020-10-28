package apap.tugas.sipes.controller;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;

import apap.tugas.sipes.service.PenerbanganService;
import apap.tugas.sipes.service.PesawatService;
import apap.tugas.sipes.service.TeknisiService;
import apap.tugas.sipes.service.TipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.web.servlet.error.ErrorController;


@Controller
public class PesawatController{
    
    @Qualifier("pesawatServiceImpl")
    @Autowired
    private PesawatService pesawatService;

    @Qualifier("teknisiServiceImpl")
    @Autowired
    private TeknisiService teknisiService;

    @Qualifier("tipeServiceImpl")
    @Autowired
    private TipeService tipeService;

    @Qualifier("penerbanganServiceImpl")
    @Autowired
    private PenerbanganService penerbanganService;

    @GetMapping("/")
    private String home(){
        return "home";
    }

    @GetMapping("/pesawat/daftar_pesawat")
    private String daftar_pesawat(Model model){
        List<PesawatModel> listPesawat = pesawatService.getListPesawat();
        model.addAttribute("listPesawat", listPesawat);
        return "daftar-pesawat";
    }

    @GetMapping("/pesawat/add")
    public String addPesawatFormPage(Model model){
        PesawatModel pesawat = new PesawatModel();
        model.addAttribute("pesawat", pesawat);
        
        List<TipeModel> listTipe = tipeService.getListTipe();
        List<TeknisiModel> allTeknisi = teknisiService.getListTeknisi();
        List<TeknisiModel> listTeknisi = new ArrayList<TeknisiModel>();
        
        listTeknisi.add(new TeknisiModel());
        pesawat.setListTeknisi(listTeknisi);

        model.addAttribute("listTipe", listTipe);
        model.addAttribute("listTeknisi", listTeknisi);
        model.addAttribute("allTeknisi", allTeknisi);

        int jumlahtipe = listTipe.size();
        model.addAttribute("jumlahtipe", jumlahtipe);

        return "form-add-pesawat";
    }

    public String generateNoSeri(PesawatModel pesawat){
        //GENERATE NOMOR SERI
        String ns = "";
        if (pesawat.getJenis_pesawat().equals("Komersial")) ns+=1;
        else ns+="2";

        pesawat.setTipe(tipeService.getTipeById(pesawat.getTipe().getId()));
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

    @PostMapping("/pesawat/add")
    public String addPesawatSubmit(
        @ModelAttribute PesawatModel pesawat,
        Model model
    ){
        if (pesawat.getTanggal_dibuat() == null) {
            pesawat.setTanggal_dibuat(LocalDate.now());
        }

        String ns = generateNoSeri(pesawat);

        pesawat.setTipe(tipeService.getTipeById(pesawat.getTipe().getId()));
        pesawat.setNomor_seri(ns);
        
        for(TeknisiModel tm: pesawat.getListTeknisi()){
            tm = teknisiService.getTeknisiById(tm.getId());
        }

        pesawatService.addPesawat(pesawat);
        model.addAttribute("pesawat", pesawat);
        // model.addAttribute("id_tipe", idTipe);

        return "add-pesawat";
    }

    @PostMapping(value="/pesawat/add", params={"addteknisi"})
    public String addPesawatAddTeknisi(
        @ModelAttribute PesawatModel pesawat,
        Model model
    ){
        List<TeknisiModel> allTeknisi = teknisiService.getListTeknisi();
        pesawat.getListTeknisi().add(new TeknisiModel());
        model.addAttribute("pesawat", pesawat);

        List<TipeModel> listTipe = tipeService.getListTipe();
        model.addAttribute("listTipe", listTipe);
        model.addAttribute("allTeknisi", allTeknisi);

        return "form-add-pesawat";
    }

    @GetMapping("/pesawat/view/{id}")
    public String viewPesawat(
        @PathVariable Long id,
        Model model
    ){
        try{
            PesawatModel pesawat = pesawatService.getPesawatById(id);
            List<TeknisiModel> listTeknisi = pesawat.getListTeknisi();
            String namatipe = pesawat.getTipe().getNama();
            model.addAttribute("pesawat", pesawat);
            model.addAttribute("listTeknisi", listTeknisi);
            model.addAttribute("namatipe", namatipe);

            for(PenerbanganModel p : pesawat.getListPenerbangan()){
                System.out.println(p.getNomor_penerbangan());
            }


            return "view-pesawat";

        }catch(Exception e){
            model.addAttribute("id", id);
            return "error";
        }
    }

    @GetMapping("/pesawat/edit/{id}")
    public String editPesawatFormPage(
        @PathVariable Long id,
        Model model
    ){
        try{
            PesawatModel pesawat = pesawatService.getPesawatById(id);
            model.addAttribute("pesawat", pesawat);
            return "form-edit-pesawat";

        }catch(Exception e){
            model.addAttribute("id", id);
            return "error";
        }
    }

    @PostMapping("/pesawat/edit")
    public String editPesawatSubmit(
        @ModelAttribute PesawatModel pesawat,
        Model model
    ){
        pesawat.setTipe(tipeService.getTipeById(pesawat.getTipe().getId()));
        String ns = generateNoSeri(pesawat);
        pesawat.setNomor_seri(ns);
        PesawatModel pesawatUpdated = pesawatService.updatePesawat(pesawat);
        model.addAttribute("pesawat", pesawatUpdated);
        // model.addAttribute("pesawat", pesawat);
        return "update-pesawat";
    }

    @GetMapping("/pesawat/{id}/tambah_penerbangan")
    public String tambahPenerbangan(
        @PathVariable Long id,
        Model model
    ){
        PesawatModel pesawat = pesawatService.getPesawatById(id);
        List<TeknisiModel> listTeknisi = pesawat.getListTeknisi();
        String namatipe = pesawat.getTipe().getNama();

        List<PenerbanganModel> allPenerbangan = penerbanganService.getListPenerbangan();
        

        List<PenerbanganModel> listPenerbangan = new ArrayList<PenerbanganModel>();
        listPenerbangan.add(new PenerbanganModel());
        pesawat.setListPenerbangan(listPenerbangan);

        model.addAttribute("allPenerbangan", allPenerbangan);
        model.addAttribute("pesawat", pesawat);
        model.addAttribute("listTeknisi", listTeknisi);
        model.addAttribute("namatipe", namatipe);

        return "tambah-penerbangan";
    }

    @PostMapping("/pesawat/{id}/tambah_penerbangan")
    public String tambahPenerbanganSubmit(
        @PathVariable Long id, @ModelAttribute PesawatModel pesawat,
        Model model
    ){
        Long idpenerbangan = pesawat.getListPenerbangan().get(0).getId();
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idpenerbangan);
        PesawatModel targetPesawat = pesawatService.getPesawatById(id);
        if (targetPesawat.getListPenerbangan() == null || targetPesawat.getListPenerbangan().size() == 0) {
            targetPesawat.setListPenerbangan(new ArrayList<PenerbanganModel>());
        }
        targetPesawat.getListPenerbangan().add(penerbangan);
        pesawatService.updatePesawat(targetPesawat);
        penerbangan.setPesawat(targetPesawat);
        penerbanganService.updatePenerbangan(penerbangan);
        model.addAttribute("pesawat", targetPesawat);
        return "tambah-penerbangan-success";

    }

    @GetMapping("/pesawat/daftar_pesawat_tua")
    private String daftar_pesawat_tua(Model model){
        List<PesawatModel> listPesawat = pesawatService.getListPesawat();
        List<PesawatModel> addPesawat = new ArrayList<PesawatModel>();
        List<Integer> listTahun = new ArrayList<Integer>();
        for (PesawatModel p : listPesawat) {
            LocalDate dibuat = p.getTanggal_dibuat();
            LocalDate today = LocalDate.now();
            int year = today.getYear() - dibuat.getYear();
            // System.out.println(year);

            if (year >= 10){
                addPesawat.add(p);
                listTahun.add(year);
            }
        }

        model.addAttribute("listPesawat", addPesawat);
        model.addAttribute("listTahun", listTahun);
        return "daftar-pesawat-tua";
    }

    @GetMapping("/pesawat/bonus")
    private String viewPesawatWithAmountOfTeknisi(Model model) {
        List<PesawatModel> listPesawat = pesawatService.getListPesawat();
        List<Integer> listSumTeknisi = new ArrayList<Integer>();
        for (PesawatModel p: listPesawat){
            int sum = p.getListTeknisi().size();
            listSumTeknisi.add(sum);
        }
        model.addAttribute("listPesawat", listPesawat);
        model.addAttribute("listSumTeknisi", listSumTeknisi);
        return "bonus";
    }

    @GetMapping("/pesawat/filter")
    public String pesawatFilter(Model model){
        List<PenerbanganModel> allPenerbangan = penerbanganService.getListPenerbangan();
        List<TipeModel> allTipe = tipeService.getListTipe();
        List<TeknisiModel> allTeknisi = teknisiService.getListTeknisi();
        model.addAttribute("allPenerbangan", allPenerbangan);
        model.addAttribute("allTipe", allTipe);
        model.addAttribute("allTeknisi", allTeknisi);
        model.addAttribute("notEmpty", false);

        return "pesawat-filter";
    }

    @GetMapping("/pesawat/filter")
    public String pesawatFilterSubmit(
        // @ModelAttribute Long idPenerbangan,
        // @ModelAttribute Long idTipe,
        // @ModelAttribute Long idTeknisi,
        // @RequestParam(name="idPenerbangan") Long idPenerbangan,
        // @RequestParam(name="idTipe") Long idTipe,
        // @RequestParam(name="idTeknisi") Long idTeknisi,
        @RequestParam Map<String, String> requestParams,
        Model model
    ){
        System.out.println("AAAA");
        Long idPenerbangan = Long.valueOf("0");
        Long idTipe = Long.valueOf("0");
        Long idTeknisi = Long.valueOf("0")
        if(requestParams.get("idPenerbangan") != null) idPenerbangan = Long.parseLong(requestParams.get("idPenerbangan"));
        if(requestParams.get("idTipe") != null)idTipe = Long.parseLong(requestParams.get("idTipe"));
        if(requestParams.get("idTeknisi") != null)idTeknisi = Long.parseLong(requestParams.get("idTeknisi"));
        // System.out.println(idPenerbangan);
        // System.out.println(idTipe);
        // System.out.println(idTeknisi);

        List<PesawatModel> allPesawat = pesawatService.getListPesawat();
        List<PesawatModel> listPesawat = new ArrayList<PesawatModel>();

        for(PesawatModel p : allPesawat){
            listPesawat.add(p);
        }
        
        for(PesawatModel p : allPesawat){
            //1
            if(!(idPenerbangan == 0)){
                PenerbanganModel targetPenerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
                List<PenerbanganModel> pPenerbangan = p.getListPenerbangan();
                if (!pPenerbangan.contains(targetPenerbangan)){
                    listPesawat.remove(p);
                    continue;
                } 
            }


            //2
            if(!(idTipe == 0)){
                TipeModel targetTipe = tipeService.getTipeById(idTipe);
                TipeModel pTipe = p.getTipe();
                if(pTipe.getId() != targetTipe.getId()){
                    listPesawat.remove(p);
                    continue;
                } 
            }


            //3
            if(!(idTeknisi==0)){
                TeknisiModel targetTeknisi = teknisiService.getTeknisiById(idTeknisi);
                List<TeknisiModel> pTeknisi = p.getListTeknisi();
                if(!pTeknisi.contains(targetTeknisi)){
                    listPesawat.remove(p);
                    continue;
                }
            }
        }
        Boolean notEmpty = true;
        if(listPesawat.size()==0) notEmpty = false;
        model.addAttribute("notEmpty", notEmpty);
        model.addAttribute("listPesawat", listPesawat);
        return "pesawat-filter";
    }

}
