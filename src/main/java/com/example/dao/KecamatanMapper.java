package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.AlamatModel;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Many;

@Mapper
public interface KecamatanMapper {
	@Select("select id, nama_kecamatan from kecamatan")
	List<KecamatanModel> selectKecamatan ();
	
	@Select("select id, nama_kecamatan from kecamatan where id_kota = #{kt}")
	List<KecamatanModel> selectKecamatanByIdKota(String kt);
	
	@Select("select nama_kecamatan from kecamatan where id=#{id}")
	String selectNamaKecamatan(@Param("id") int id);
}
