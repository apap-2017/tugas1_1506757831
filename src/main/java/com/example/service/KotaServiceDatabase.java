package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.dao.KotaMapper;
import com.example.dao.PendudukMapper;
import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KotaServiceDatabase implements KotaService
{
    @Autowired
    private KotaMapper kotaMapper;

	@Override
	public List<KotaModel> selectKota() {
		log.info("select all kota");
		return kotaMapper.selectKota();
	}

	@Override
	public String selectNamaKota(int id) {
		log.info("select kota where id {}", id);
		return kotaMapper.selectNamaKota(id);
	}

	
}
