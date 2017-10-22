package com.example.controller;

import java.util.List;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;

@Controller
public class PendudukController {
	
	@Autowired
    PendudukService pendudukDAO;
	
	@Autowired
    KotaService kotaDAO;
	
	@Autowired
    KecamatanService kecamatanDAO;
	
	@Autowired
    KelurahanService kelurahanDAO;
	
	@Autowired
    KeluargaService keluargaDAO;
	
	@RequestMapping(value = "/penduduk", method = RequestMethod.GET)
	public String findPendudukByNIK (@RequestParam(value = "nik", required = false, defaultValue="0") String nik, Model model)
	{
		PendudukModel penduduk = pendudukDAO.findPendudukByNIK(nik);
		
		if(penduduk != null){
			AlamatModel alamat = pendudukDAO.findAlamatPendudukByNIK(nik);
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("alamat", alamat);
			
			if(penduduk.getIs_wni() == 1){
				model.addAttribute("kewarganegaraan","WNI");
				
			} else{
				model.addAttribute("kewarganegaraan","WNA");
			}
			
			if(penduduk.getIs_wafat() == 0){
				model.addAttribute("wafat","Hidup");
				
			} else{
				model.addAttribute("wafat","Wafat");
			}	
			
			return "view-findPendudukHasil";
		} else{
			return "not-found";
		}
	}
	
	@RequestMapping(value = "/penduduk/tambah/", method = RequestMethod.POST)
    public String addSubmit (Model model,
            @RequestParam(value = "nama", required = false) String nama,
            @RequestParam(value = "jenis_kelamin", required = false) int jenis_kelamin,
            @RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
            @RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
            @RequestParam(value = "golongan_darah", required = false) String golongan_darah,
            @RequestParam(value = "agama", required = false) String agama,
            @RequestParam(value = "status_perkawinan", required = false) String status_perkawinan,
            @RequestParam(value = "pekerjaan", required = false) String pekerjaan,
            @RequestParam(value = "is_wni", required = false) int is_wni,
            @RequestParam(value = "is_wafat", required = false) int is_wafat,
            @RequestParam(value = "id_keluarga", required = false) int id_keluarga,
            @RequestParam(value = "status_dalam_keluarga", required = false) String status_dalam_keluarga)
    {
		String nik = "";
		AlamatModel alamat = pendudukDAO.findAlamatKeluargaByID(id_keluarga);
		
		nik += "" + alamat.getKode_kecamatan();
		nik = nik.substring(0, nik.length()-1);
		
		if(jenis_kelamin == 0){
			nik += tanggal_lahir.substring(tanggal_lahir.length()-2,tanggal_lahir.length())  + tanggal_lahir.substring(5,7) + tanggal_lahir.substring(2,4);//2017/05/14 
		} else{
			nik += (Integer.parseInt(tanggal_lahir.substring(tanggal_lahir.length()-2,tanggal_lahir.length())) + 40) + "" + tanggal_lahir.substring(5,7) + tanggal_lahir.substring(2,4);
		}
		
		int count = pendudukDAO.countNIK("%"+nik+"%");
		count++;
		
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
		
		model.addAttribute("nik", nik);
		model.addAttribute("flag", true);
		
		pendudukDAO.addPenduduk(new PendudukModel(0,nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat));
		return "form-tambah-penduduk";
    }
	
	@RequestMapping("/penduduk/ubah/{nik}")
    public String updatePenduduk(Model model, @PathVariable(value = "nik") String nik){

		PendudukModel penduduk = pendudukDAO.findPendudukByNIK(nik);
		
    	if(penduduk != null){
    		model.addAttribute("penduduk", penduduk);
    		model.addAttribute("nik", penduduk.getNik());
    		return "form-ubah-penduduk";
    	} else{
    		return "not-found";
    	
    	}
    }
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String updatePendudukSubmit(Model model, PendudukModel penduduk, @RequestParam(value = "id_keluarga_lama", required = false) int id_keluarga_lama,
    		@RequestParam(value = "jenis_kelamin_lama", required = false) int jenis_kelamin_lama,
    		@RequestParam(value = "tanggal_lahir_lama", required = false) String tanggal_lahir_lama){
    	
		if(id_keluarga_lama != penduduk.getId_keluarga() || penduduk.getJenis_kelamin() != jenis_kelamin_lama || 
				!penduduk.getTanggal_lahir().equalsIgnoreCase(tanggal_lahir_lama)){
			
			String nik = "";
			if(id_keluarga_lama != penduduk.getId_keluarga()){
				
				AlamatModel alamat = pendudukDAO.findAlamatKeluargaByID(penduduk.getId_keluarga());	
				nik += "" + alamat.getKode_kecamatan();
				nik = nik.substring(0, nik.length()-1);
			} else{
				nik = penduduk.getNik().substring(0,6);
			}
			
			if(penduduk.getJenis_kelamin() != jenis_kelamin_lama || !penduduk.getTanggal_lahir().equalsIgnoreCase(tanggal_lahir_lama)){
				if(penduduk.getJenis_kelamin() == 0){
					nik += penduduk.getTanggal_lahir().substring(penduduk.getTanggal_lahir().length()-2,penduduk.getTanggal_lahir().length())  + 
							penduduk.getTanggal_lahir().substring(5,7) + penduduk.getTanggal_lahir().substring(2,4);//2017/05/14 
				} else{
					nik += (Integer.parseInt(penduduk.getTanggal_lahir().substring(penduduk.getTanggal_lahir().length()-2,penduduk.getTanggal_lahir().length())) 
							+ 40) + "" + penduduk.getTanggal_lahir().substring(5,7) + penduduk.getTanggal_lahir().substring(2,4);
				}
			} else{
				nik += penduduk.getNik().substring(6,12);
			}
			
			int count = pendudukDAO.countNIK("%"+nik+"%");
			count++;
			
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
			penduduk.setNik(nik);
			
			pendudukDAO.updatePenduduk(penduduk);
			model.addAttribute("flag",true);
			model.addAttribute("penduduk", penduduk);
    		model.addAttribute("nik", penduduk.getNik());
	    	return "form-ubah-penduduk";
		} else{
			model.addAttribute("nik", penduduk.getNik());
			pendudukDAO.updatePenduduk(penduduk);
			model.addAttribute("penduduk", pendudukDAO.findPendudukByNIK(penduduk.getNik()));
    		model.addAttribute("nik", penduduk.getNik());
    		model.addAttribute("flag",true);
	    	return "form-ubah-penduduk";
		}
    }
	
	@RequestMapping(value = "/penduduk", method = RequestMethod.POST)
	public String setStatusMati (@RequestParam(value = "nik", required = false, defaultValue="0") String nik, Model model)
	{
		PendudukModel penduduk = pendudukDAO.findPendudukByNIK(nik);
		
		if(penduduk != null){
			model.addAttribute("nik", nik);
			pendudukDAO.setPendudukIs_wafat(nik);
			model.addAttribute("flag",true);
			
			
			if(pendudukDAO.countAnggotaKeluargaHidup(""+penduduk.getId_keluarga()) == 0){
				pendudukDAO.setKeluargaPendudukIs_tidak_berlaku(""+penduduk.getId_keluarga());
			}
			
			AlamatModel alamat = pendudukDAO.findAlamatPendudukByNIK(nik);
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("alamat", alamat);
		
			if(penduduk.getIs_wni() == 1){
				model.addAttribute("kewarganegaraan","WNI");
				
			} else{
				model.addAttribute("kewarganegaraan","WNA");
			}
			
			if(penduduk.getIs_wafat() == 0){
				model.addAttribute("wafat","Hidup");
				
			} else{
				model.addAttribute("wafat","Wafat");
			}
			
		
		}
		return "view-findPendudukHasil";
	}
	
	
	
	@RequestMapping("/penduduk/cari")
    public String cariDataPenduduk(@RequestParam(value = "kt", required = false) String kt, @RequestParam(value = "kc", required = false) String kc,
    		@RequestParam(value = "kl", required = false) String kl, Model model)
    {
		boolean flag = false;
		if(kl != null){
			model.addAttribute("kt_nama", kotaDAO.selectNamaKota(Integer.parseInt(kt)));
			model.addAttribute("kc_nama", kecamatanDAO.selectNamaKecamatan(Integer.parseInt(kc)));
			model.addAttribute("kl_nama", kelurahanDAO.selectNamaKelurahan(Integer.parseInt(kl)));
			model.addAttribute("flag_kl", true);
			model.addAttribute("kl", kl);
			flag=true;
			List<PendudukModel> list_penduduk = pendudukDAO.selectPendudukByIdKelurahan(Integer.parseInt(kl));
			model.addAttribute("list_penduduk", list_penduduk);
			return "filtered-penduduk";

		}
		
		List<KotaModel> list_kota = kotaDAO.selectKota();
		model.addAttribute("list_kota", list_kota);
		
		if(kt != null){
			model.addAttribute("kt_nama", kotaDAO.selectNamaKota(Integer.parseInt(kt)));
			model.addAttribute("flag_kt", true);
			model.addAttribute("kt", kt);
			if(kc == null){
				List<KecamatanModel> list_kecamatan = kecamatanDAO.selectKecamatanByIdKota(kt);
				model.addAttribute("list_kecamatan", list_kecamatan);
			}
			if(kc != null){
				model.addAttribute("kc_nama", kecamatanDAO.selectNamaKecamatan(Integer.parseInt(kc)));
				model.addAttribute("flag_kc", true);
				model.addAttribute("kc", kc);
				if(kl == null){
					List<KelurahanModel> list_kelurahan = kelurahanDAO.selectKelurahanByIdKecamatan(kc);
					model.addAttribute("list_kelurahan", list_kelurahan);
				}
				
			}
		} 
		return "cari-data-penduduk";
		
    }
}
