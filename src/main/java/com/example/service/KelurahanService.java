package com.example.service;

import java.util.List;

import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.PendudukModel;

public interface KelurahanService
{
	List<KelurahanModel> selectKelurahanByIdKecamatan (String kc);
	String selectNamaKelurahan(int id);
	List<KelurahanModel> selectKelurahan();
}
