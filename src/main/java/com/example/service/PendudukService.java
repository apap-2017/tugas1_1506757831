package com.example.service;

import java.util.List;

import com.example.model.AlamatModel;
import com.example.model.PendudukModel;

public interface PendudukService
{
	PendudukModel findPendudukByNIK(String NIK);
	AlamatModel findAlamatPendudukByNIK(String NIK);
	void addPenduduk(PendudukModel penduduk);
	AlamatModel findAlamatKeluargaByID(int id_keluarga);
	int countNIK(String nik);
	int selectId(String nik);
	 void updatePenduduk(PendudukModel penduduk);
	 void setPendudukIs_wafat(String nik);
	int countAnggotaKeluargaHidup(String id_keluarga);
	void setKeluargaPendudukIs_tidak_berlaku(String id_keluarga);
	void updateNik (int id, String nik);
	List<PendudukModel> selectPendudukByIdKelurahan(int id);
}
