package com.example.service;

import java.util.List;

import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

public interface KeluargaService
{
	KeluargaModel findKeluargaByNKK(String NKK);
	AlamatModel findAlamatKeluargaByNKK(String NKK);
	String getKodeKecamatanByIdKecamatan(int id);
	String getIdKelurahanByIdKelurahan(int id);
	int countNKK (String nkk);
	int selectId (String nkk);
	int selectIs_tidak_berlaku (String nkk);
	void addKeluarga(String nomor_kk, String alamat, String rt, String rw, String id_kelurahan, int is_tidak_berlaku);
	void updateKeluarga(int id, String nkk, String alamat, String rt, String rw, String id_kelurahan, int is_tidak_berlaku);
	List<PendudukModel> selectAnggotaKeluarga(int id);
}
