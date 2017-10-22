package com.example.service;

import java.util.List;

import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

public interface KotaService
{
	List<KotaModel> selectKota ();
	String selectNamaKota (int id);
}
