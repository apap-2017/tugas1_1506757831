package com.example.service;

import java.util.List;

import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.PendudukModel;

public interface KecamatanService
{
	List<KecamatanModel> selectKecamatanByIdKota (String kt);
	String selectNamaKecamatan (int id);
	List<KecamatanModel> selectKecamatan();
}
