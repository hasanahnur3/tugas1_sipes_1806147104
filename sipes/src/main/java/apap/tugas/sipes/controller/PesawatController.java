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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    // public String generateNoSeri(PesawatModel pesawat){
    //     //GENERATE NOMOR SERI
    //     String ns = "";
    //     if (pesawat.getJenis_pesawat().equals("Komersial")) ns+=1;
    //     else ns+="2";

    //     pesawat.setTipe(tipeService.getTipeById(pesawat.getTipe().getId()));
    //     if(pesawat.getTipe().getNama().equals("BOEING")) ns+="BO";
    //     if(pesawat.getTipe().getNama().equals("ATR")) ns+="AT";
    //     if(pesawat.getTipe().getNama().equals("Airbus")) ns+="AB";
    //     if(pesawat.getTipe().getNama().equals("Bombardier")) ns+="BB";

    //     int year = pesawat.getTanggal_dibuat().getYear();
    //     String stryear = Integer.toString(year);
    //     StringBuilder sb=new StringBuilder(stryear);  
    //     sb.reverse();  
    //     String fixyear = sb.toString();
    //     ns+=fixyear;

    //     year +=8;
    //     stryear = Integer.toString(year);
    //     ns+=stryear;

    //     Random r = new Random();
    //     char c1 = (char)(r.nextInt(26) + 'a');
    //     char c2 = (char)(r.nextInt(26) + 'a');
    //     String rc1 = String.valueOf(c1).toUpperCase();
    //     String rc2 = String.valueOf(c2).toUpperCase();
    //     ns+=rc1;
    //     ns+=rc2;

    //     return ns;
    // }

    @PostMapping("/pesawat/add")
    public String addPesawatSubmit(
        @ModelAttribute PesawatModel pesawat,
        Model model
    ){
        if (pesawat.getTanggal_dibuat() == null) {
            pesawat.setTanggal_dibuat(LocalDate.now());
        }

        String ns = pesawatService.generateNoSeri(pesawat);

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
        pesawat.getListTeknisi().add(new TeknisiModel());
        model.addAttribute("pesawat", pesawat);

        List<TipeModel> listTipe = tipeService.getListTipe();
        List<TeknisiModel> allTeknisi = teknisiService.getListTeknisi();
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
        PesawatModel pesawatUpdated = pesawatService.updatePesawat(pesawat);
        model.addAttribute("pesawat", pesawatUpdated);
        // model.addAttribute("pesawat", pesawat);
        return "update-pesawat";
    }


}