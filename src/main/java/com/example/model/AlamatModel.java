package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlamatModel {
	private String alamat;
	private String rt;
	private String rw;
	private String kelurahan;
	private int id_kelurahan;
	private String kecamatan;
	private int id_kecamatan;
	private String kota;
	private String kode_pos;
	private int kode_kecamatan;
}
