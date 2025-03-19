package com.Final.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utilitario {

	public static String guardarImagen(MultipartFile archivo, String ruta) {
		String nombreArchivo = archivo.getOriginalFilename();
		File objArchivo = new File(ruta + nombreArchivo);
		try {
			archivo.transferTo(objArchivo);
			return nombreArchivo;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
