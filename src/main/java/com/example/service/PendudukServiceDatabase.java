package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.AlamatModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService
{
    @Autowired
    private PendudukMapper pendudukMapper;

	@Override
	public PendudukModel findPendudukByNIK(String NIK) {
		log.info("select penduduk with nik {}", NIK);
		return pendudukMapper.findPendudukByNIK(NIK);
	}

	@Override
	public AlamatModel findAlamatPendudukByNIK(String NIK) {
		log.info("select alamat penduduk with nik {}", NIK);
		return pendudukMapper.findAlamatPendudukByNIK(NIK);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		log.info("penduduk added dengan nik {}", penduduk.getNik());
		pendudukMapper.addPenduduk(penduduk);
	}

	@Override
	public AlamatModel findAlamatKeluargaByID(int id_keluarga) {
		log.info("select for alamat keluarga by id = {}", id_keluarga);
		return pendudukMapper.findAlamatKeluargaByID(id_keluarga);
	}

	@Override
	public int countNIK(String nik) {
		log.info("counting penduduk with "+ nik + " nik");
		log.info(""+pendudukMapper.countNIK(nik));
		return pendudukMapper.countNIK(nik);
	}

	@Override
	public void updatePenduduk(PendudukModel penduduk) {
		log.info("update penduduk dengan id " + penduduk.getId() + " dan nik " + penduduk.getNik());
		pendudukMapper.updatePenduduk(penduduk);
	}

	@Override
	public int selectId(String nik) {
		log.info("get id of penduduk with nik {}", nik);
		return pendudukMapper.selectId(nik);
	}

	@Override
	public void setPendudukIs_wafat(String nik) {
		log.info("Set penduduk with nik {} is wafat value to 1", nik);
		pendudukMapper.setPendudukIs_wafat(nik);
	}

	@Override
	public int countAnggotaKeluargaHidup(String id_keluarga) {
		log.info("count how many penduduk with the same id keluarga {} that are still hidup", id_keluarga);
		return pendudukMapper.countAnggotaKeluargaHidup(id_keluarga);
	}

	@Override
	public void setKeluargaPendudukIs_tidak_berlaku(String id_keluarga) {
		log.info("make keluarga with id {} is_tidak_berlaku to 1", id_keluarga);
		pendudukMapper.setKeluargaPendudukIs_tidak_berlaku(id_keluarga);
	}

	@Override
	public void updateNik(int id, String nik) {
		log.info("update nik penduduk with id {} new nik {}", id, nik);
		pendudukMapper.updateNik(id, nik);
	}

	@Override
	public List<PendudukModel> selectPendudukByIdKelurahan(int id) {
		log.info("select all penduduk with id kelurahan of {}", id);
		return pendudukMapper.selectPendudukByIdKelurahan(id);
	}
}
