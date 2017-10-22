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
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Many;

@Mapper
public interface KotaMapper {
	@Select("select id, nama_kota from kota")
	List<KotaModel> selectKota ();
	
	@Select("select nama_kota from kota where id=#{id}")
	String selectNamaKota(@Param("id") int id);
}
