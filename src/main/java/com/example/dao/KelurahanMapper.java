package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.AlamatModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Many;

@Mapper
public interface KelurahanMapper {
	@Select("select id, nama_kelurahan from kelurahan")
	List<KelurahanModel> selectKelurahan ();
	
	@Select("select id, nama_kelurahan from kelurahan where id_kecamatan = #{kc}")
	List<KelurahanModel> selectKelurahanByIdKecamatan (String kc);
	
	@Select("select nama_kelurahan from kelurahan where id=#{id}")
	String selectNamaKelurahan(@Param("id") int id);
}
