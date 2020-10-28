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
public class PenerbanganController{
    
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


    // @GetMapping("/pesawat/daftar_pesawat")
    // private String daftar_pesawat(Model model){
    //     List<PesawatModel> listPesawat = pesawatService.getListPesawat();
    //     model.addAttribute("listPesawat", listPesawat);
    //     return "daftar-pesawat";
    // }

    @GetMapping("/penerbangan/add")
    public String addPenerbanganFormPage(Model model){
        PenerbanganModel penerbangan = new PenerbanganModel();
        model.addAttribute("penerbangan", penerbangan);

        return "form-add-penerbangan";
    }

    @PostMapping("/penerbangan/add")
    public String addPenerbanganSubmit(
        @ModelAttribute PenerbanganModel penerbangan,
        Model model
    ){
        if (penerbangan.getWaktu_berangkat() == null) {
            penerbangan.setWaktu_berangkat(LocalDateTime.now());
        }

        penerbanganService.addPenerbangan(penerbangan);
        model.addAttribute("penerbangan", penerbangan);

        return "add-penerbangan";
    }

    @GetMapping("/penerbangan/view/{id}")
    public String viewPenerbangan(
        @PathVariable Long id,
        Model model
    ){
        try{
            PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
            model.addAttribute("penerbangan", penerbangan);
            return "view-penerbangan";

        }catch(Exception e){
            model.addAttribute("id", id);
            return "error";
        }
    }

    @GetMapping("/penerbangan/daftar_penerbangan")
    private String daftar_pesawat(Model model){
        List<PenerbanganModel> listPenerbangan = penerbanganService.getListPenerbangan();
        model.addAttribute("listPenerbangan", listPenerbangan);
        return "daftar-penerbangan";
    }

    @GetMapping("/penerbangan/edit/{id}")
    public String editPenerbanganFormPage(
        @PathVariable Long id,
        Model model
    ){
        try{
            PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
            model.addAttribute("penerbangan", penerbangan);
            return "form-edit-penerbangan";

        }catch(Exception e){
            model.addAttribute("id", id);
            return "error";
        }
    }

    @PostMapping("/penerbangan/edit")
    public String editPenerbanganSubmit(
        @ModelAttribute PenerbanganModel penerbangan,
        Model model
    ){
        PenerbanganModel penerbanganUpdated = penerbanganService.updatePenerbangan(penerbangan);
        model.addAttribute("penerbangan", penerbanganUpdated);
        return "update-penerbangan";
    }

    @GetMapping("/penerbangan/delete/{id}")
    public String deleteHotel(
        @PathVariable(value="id") Long id,
        Model model
    ){
        // try{
            PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
            penerbanganService.deletePenerbangan(penerbangan);
            model.addAttribute("penerbangan", penerbangan);
            return "delete-penerbangan";

        // }catch(Exception e){
        //     model.addAttribute("id", id);
        //     return "sad";
        // }
    }
}