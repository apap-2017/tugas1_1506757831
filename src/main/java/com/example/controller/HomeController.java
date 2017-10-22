package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.service.KecamatanService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;

@Controller
public class HomeController {
	
	@Autowired
    KotaService kotaDAO;
	
	@Autowired
    KecamatanService kecamatanDAO;
	
	@Autowired
    KelurahanService kelurahanDAO;
	
	@RequestMapping("/")
    public String index ()
    {
        return "index";
    }
	
	@RequestMapping("/penduduk/tambah")
    public String tambahPenduduk()
    {
        return "form-tambah-penduduk";
    }
	
	@RequestMapping("/keluarga/tambah")
    public String tambahKeluarga(Model model)
    {
		List<KotaModel> list_kota = kotaDAO.selectKota();
		model.addAttribute("list_kota", list_kota);
		
		List<KecamatanModel> list_kecamatan = kecamatanDAO.selectKecamatan();
		model.addAttribute("list_kecamatan", list_kecamatan);
		
		List<KelurahanModel> list_kelurahan = kelurahanDAO.selectKelurahan();
		model.addAttribute("list_kelurahan", list_kelurahan);
		
        return "form-tambah-keluarga";
    }
	
	
	/*@RequestMapping("/penduduk")
    public String findPendudukHome ()
    {
        return "findPenduduk";
    }
	*/
}
