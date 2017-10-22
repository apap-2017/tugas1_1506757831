package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.dao.PendudukMapper;
import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService
{
    @Autowired
    private KeluargaMapper keluargaMapper;

	@Override
	public KeluargaModel findKeluargaByNKK(String NKK) {
		log.info("find keluarga with nkk {}", NKK);
		return keluargaMapper.findKeluargaByNKK(NKK);
	}

	@Override
	public AlamatModel findAlamatKeluargaByNKK(String NKK) {
		log.info("find alamat keluarga with nkk {}", NKK);
		return keluargaMapper.findAlamatKeluargaByNKK(NKK);
	}

	@Override
	public String getKodeKecamatanByIdKecamatan(int id) {
		log.info("find kode kecamatan by id kecamatan {}", id);
		return keluargaMapper.getKodeKecamatanByIdKecamatan(id);
	}

	@Override
	public int countNKK(String nkk) {
		log.info("count how many nkk with {}", nkk);
		log.info("" + keluargaMapper.countNKK(nkk));
		return keluargaMapper.countNKK(nkk);
	}

	@Override
	public void addKeluarga(String nomor_kk, String alamat, String rt, String rw, String id_kelurahan, int is_tidak_berlaku) {
		log.info("add keluarga with nkk {} {} {} {} {} {}",nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku);
		keluargaMapper.addKeluarga(nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku);
	}

	@Override
	public String getIdKelurahanByIdKelurahan(int id) {
		log.info("find id kelurahan with id kelurahan {}", id);
		return keluargaMapper.getIdKelurahanByIdKelurahan(id);
	}

	@Override
	public int selectId(String nkk) {
		log.info("find id keluarga by nkk {}", nkk);
		log.info(""+keluargaMapper.selectId(nkk));
		return keluargaMapper.selectId(nkk);
	}

	@Override
	public int selectIs_tidak_berlaku(String nkk) {
		log.info("select status of is_tidak_berlaku from keluarga with nkk {}", nkk);
		return keluargaMapper.selectIs_tidak_berlaku(nkk);
	}

	@Override
	public void updateKeluarga(int id, String nkk, String alamat, String rt, String rw, String id_kelurahan,
			int is_tidak_berlaku) {
		log.info("update keluarga with {} with values {} {} {} {} {} {}", id, nkk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku);
		keluargaMapper.updateKeluarga(id, nkk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku);
	}

	@Override
	public List<PendudukModel> selectAnggotaKeluarga(int id) {
		log.info("Select anggota keluarga by id {}", id);
		return keluargaMapper.selectAnggotaKeluarga(id);
	}
}
