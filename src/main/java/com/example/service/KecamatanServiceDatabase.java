package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KecamatanMapper;
import com.example.dao.KeluargaMapper;
import com.example.dao.KotaMapper;
import com.example.dao.PendudukMapper;
import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService
{
    @Autowired
    private KecamatanMapper kecamatanMapper;

	@Override
	public List<KecamatanModel> selectKecamatanByIdKota(String kt) {
		log.info("select kecamatan by id kota {}", kt);
		return kecamatanMapper.selectKecamatanByIdKota(kt);
	}

	@Override
	public String selectNamaKecamatan(int id) {
		log.info("select nama kecamatan by id {}", id);
		return kecamatanMapper.selectNamaKecamatan(id);
	}

	@Override
	public List<KecamatanModel> selectKecamatan() {
		log.info("select all kecamatan");
		return kecamatanMapper.selectKecamatan();
	}
	
}
