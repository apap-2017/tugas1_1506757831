package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.dao.KelurahanMapper;
import com.example.dao.KotaMapper;
import com.example.dao.PendudukMapper;
import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService
{
    @Autowired
    private KelurahanMapper kelurahanMapper;

	@Override
	public List<KelurahanModel> selectKelurahanByIdKecamatan(String kc) {
		log.info("select kelurahan by id kecamatan {}",kc);
		return kelurahanMapper.selectKelurahanByIdKecamatan(kc);
	}

	@Override
	public String selectNamaKelurahan(int id) {
		log.info("select nama kelurahan by id {}", id);
		return kelurahanMapper.selectNamaKelurahan(id);
	}

	@Override
	public List<KelurahanModel> selectKelurahan() {
		log.info("select all kelurahan");
		return kelurahanMapper.selectKelurahan();
	}

}
