package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;

@Controller
public class KeluargaController {
	@Autowired
    KeluargaService keluargaDAO;
	@Autowired
    PendudukService pendudukDAO;
	@Autowired
    KotaService kotaDAO;
	
	@Autowired
    KecamatanService kecamatanDAO;
	
	@Autowired
    KelurahanService kelurahanDAO;
	
	@RequestMapping("/keluarga")
	public String findKeluargaByNKK (@RequestParam(value = "nkk", required = false, defaultValue="0") String nkk, Model model)
	{
		KeluargaModel keluarga = keluargaDAO.findKeluargaByNKK(nkk);
		
		if(keluarga != null){
			AlamatModel alamat = keluargaDAO.findAlamatKeluargaByNKK(nkk);
			
			model.addAttribute("alamat", alamat);
			model.addAttribute("keluarga", keluarga);
			
			return "view-findKeluargaHasil";
		} else{
			return "not-found";
		}
	}
	
	@RequestMapping(value = "/keluarga/tambah/", method = RequestMethod.POST)
    public String addSubmitKeluarga (Model model,
            @RequestParam(value = "alamat", required = false) String alamat,
            @RequestParam(value = "rt", required = false) String rt,
            @RequestParam(value = "rw", required = false) String rw,
            @RequestParam(value = "id_kelurahan", required = false) int id_kelurahan,
            @RequestParam(value = "id_kecamatan", required = false) int id_kecamatan,
            @RequestParam(value = "nama_kota", required = false) String nama_kota)
    {
		String nkk = "";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		
		String kode_kecamatan = keluargaDAO.getKodeKecamatanByIdKecamatan(id_kecamatan);
		
		String dateKeluaran = dateFormat.format(date);
		dateKeluaran = dateKeluaran.substring(dateKeluaran.length()-2,dateKeluaran.length())  + dateKeluaran.substring(5,7) + dateKeluaran.substring(2,4);
		
		nkk += "" + kode_kecamatan;
		nkk = nkk.substring(0, nkk.length()-1);
		nkk += dateKeluaran;
		
		int count = keluargaDAO.countNKK("%"+nkk+"%");
		count++;
		
		if(count%10 == 0){
			nkk += "00"+count;
		} else if(count%100 == 0){
			nkk += "0"+count;
		} else if(count%1000 == 0){
			nkk += ""+count;
		} else{
			nkk += "000"+count;
		}
		count = keluargaDAO.countNKK("%"+nkk+"%");
		
		if(count >= 1){
			nkk = "" + (Integer.parseInt(nkk) + 1);
		}
		
		model.addAttribute("nkk", nkk);
		model.addAttribute("flag", true);
		
		keluargaDAO.addKeluarga(nkk, alamat, rt, rw, ""+id_kelurahan, 0);
        return "form-tambah-keluarga";
    }
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String updatePendudukSubmit(Model model, PendudukModel penduduk, @RequestParam(value = "alamat", required = false) String alamat_baru,
    		@RequestParam(value = "rt", required = false) String rt,
    		@RequestParam(value = "rw", required = false) String rw,
    		@RequestParam(value = "id_kelurahan", required = false) int id_kelurahan,
    		@RequestParam(value = "id_kecamatan", required = false) int id_kecamatan,
    		@RequestParam(value = "nama_kota", required = false) String nama_kota, 
    		@RequestParam(value = "id_kecamatan_lama", required = false) int id_kecamatan_lama,
    		@RequestParam(value = "id_kelurahan_lama", required = false) int id_kelurahan_lama,
    		@RequestParam(value = "nama_kota_lama", required = false) String nama_kota_lama,
    		@RequestParam(value = "nomor_kk_lama", required = false) String nomor_kk_lama,
    		@RequestParam(value = "is_tidak_berlaku", required = false) int is_tidak_berlaku){
    	
		if(id_kelurahan != id_kelurahan_lama || id_kecamatan != id_kecamatan_lama || 
				!nama_kota.equalsIgnoreCase(nama_kota_lama)){
			int id = keluargaDAO.selectId(nomor_kk_lama);
			List<PendudukModel> anggotaKeluarga = keluargaDAO.selectAnggotaKeluarga(id);
			String nkk = "";
			
			nkk += "" + keluargaDAO.getKodeKecamatanByIdKecamatan(id_kecamatan).substring(0, 6);
			
			for(int i=0; i<anggotaKeluarga.size(); i++){
				String nik = anggotaKeluarga.get(i).getNik();
				nik = nik.substring(6, 12);
				nik = nkk + nik;
				
				int count = pendudukDAO.countNIK("%"+nik+"%")+1;
				
				if(count/10 == 0){
					nik += "000"+count;
				} else if(count/100 == 0){
					nik += "00"+count;
				} else if(count/1000 == 0){
					nik += "0"+count;
				} else{
					nik += ""+count;
				}
				
				count = pendudukDAO.countNIK("%"+nik+"%");
				
				if(count >= 1){
					nik = "" + (Integer.parseInt(nik) + 1);
				}
				
				pendudukDAO.updateNik(anggotaKeluarga.get(i).getId(), nik);
			}
			
			nkk = nkk.substring(0, nkk.length()-1);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			
			String dateKeluaran = dateFormat.format(date);
			dateKeluaran = dateKeluaran.substring(dateKeluaran.length()-2,dateKeluaran.length())  + dateKeluaran.substring(5,7) + dateKeluaran.substring(2,4);

			nkk += dateKeluaran;
			
			int count = keluargaDAO.countNKK("%"+nkk+"%");
			count++;
			
			if(count/10 == 0){
				nkk += "000"+count;
			} else if(count/100 == 0){
				nkk += "00"+count;
			} else if(count/1000 == 0){
				nkk += "0"+count;
			} else{
				nkk += ""+count;
			}
			
			count = keluargaDAO.countNKK("%"+nkk+"%");
			
			if(count >= 1){
				nkk = "" + (Integer.parseInt(nkk) + 1);
			}
			
			
			keluargaDAO.updateKeluarga(id, nkk, alamat_baru, rt, rw, ""+id_kelurahan, is_tidak_berlaku);
			
			model.addAttribute("nomor_kk", nkk);
    		model.addAttribute("alamat", keluargaDAO.findAlamatKeluargaByNKK(nkk));
    		model.addAttribute("is_tidak_berlaku", is_tidak_berlaku);
    		model.addAttribute("flag",true);
    		
    		List<KotaModel> list_kota = kotaDAO.selectKota();
    		model.addAttribute("list_kota", list_kota);
    		
    		List<KecamatanModel> list_kecamatan = kecamatanDAO.selectKecamatan();
    		model.addAttribute("list_kecamatan", list_kecamatan);
    		
    		List<KelurahanModel> list_kelurahan = kelurahanDAO.selectKelurahan();
    		model.addAttribute("list_kelurahan", list_kelurahan);
    		
	    	return "form-ubah-keluarga";
		} else{
			int id = keluargaDAO.selectId(nomor_kk_lama);
			model.addAttribute("nomor_kk", nomor_kk_lama);
			String nkk = nomor_kk_lama;
			keluargaDAO.updateKeluarga(id, nkk, alamat_baru, rt, rw, ""+id_kelurahan, is_tidak_berlaku);
			model.addAttribute("nomor_kk", nkk);
    		model.addAttribute("alamat", keluargaDAO.findAlamatKeluargaByNKK(nkk));
    		model.addAttribute("is_tidak_berlaku", is_tidak_berlaku);
    		model.addAttribute("flag",true);
    		
    		List<KotaModel> list_kota = kotaDAO.selectKota();
    		model.addAttribute("list_kota", list_kota);
    		
    		List<KecamatanModel> list_kecamatan = kecamatanDAO.selectKecamatan();
    		model.addAttribute("list_kecamatan", list_kecamatan);
    		
    		List<KelurahanModel> list_kelurahan = kelurahanDAO.selectKelurahan();
    		model.addAttribute("list_kelurahan", list_kelurahan);
    		
	    	return "form-ubah-keluarga";
		}
    }
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.GET)
    public String updatePenduduk(Model model, @PathVariable(value = "nkk") String nkk){
    	if((keluargaDAO.findKeluargaByNKK(nkk)) != null){
    		model.addAttribute("nomor_kk", nkk);
    		model.addAttribute("alamat", keluargaDAO.findAlamatKeluargaByNKK(nkk));
    		model.addAttribute("is_tidak_berlaku", keluargaDAO.selectIs_tidak_berlaku(nkk));
    		List<KotaModel> list_kota = kotaDAO.selectKota();
    		model.addAttribute("list_kota", list_kota);
    		
    		List<KecamatanModel> list_kecamatan = kecamatanDAO.selectKecamatan();
    		model.addAttribute("list_kecamatan", list_kecamatan);
    		
    		List<KelurahanModel> list_kelurahan = kelurahanDAO.selectKelurahan();
    		model.addAttribute("list_kelurahan", list_kelurahan);
    		return "form-ubah-keluarga";
    	} else{
    		return "not-found";
    	
    	}
    }
	
	
}
