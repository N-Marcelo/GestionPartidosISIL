package com.Final.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Final.entity.*;
import com.Final.repository.*;

import com.Final.utils.*;


@Controller
@RequestMapping("/partidoPolitico")
public class PartidoPoliticoController {
	
	@Autowired
	PartidoPoliticoRepository partidoPoliticoRepository;
	
	@Value("${Final.ruta.imagenes}")
	private String rutaImagenes;
	
	@RequestMapping(value="/buscarPartidoPolitico", method = RequestMethod.GET)
	public String buscarPartidoPolitico(@RequestParam("nombre") String nombre, Model model) {
		List<PartidoPolitico> listaPartidoPolitico = partidoPoliticoRepository.findByNombreContaining(nombre);
		model.addAttribute("listaPartidoPolitico", listaPartidoPolitico);
		return "gestionarPartidos";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarPartidoPolitico(@PathVariable("id")int id, Model model) {
		partidoPoliticoRepository.deleteById(id);
		List<PartidoPolitico> listaPartidoPolitico = partidoPoliticoRepository.findAll();
		model.addAttribute("listaPartidoPolitico", listaPartidoPolitico);
		return "gestionarPartidos";
	}

	@GetMapping("/editar/{id}")
	public String editarPartidoPolitico(@PathVariable("id")int id, Model model) {
		PartidoPolitico objPartidoPolitico = partidoPoliticoRepository.findById(id);
		model.addAttribute("objPartidoPolitico", objPartidoPolitico);
		return "editarPartido";
	}
	
	@PostMapping("/nuevo")
	public String nuevoPartidoPolitico(Model model) {
		PartidoPolitico objPartidoPolitico = new PartidoPolitico();
		model.addAttribute("objPartidoPolitico", objPartidoPolitico);
		return "nuevoPartido";
	}
	
	@RequestMapping(value="/registrar", method = RequestMethod.POST, params="cancelar")
	public String cancelar() {
		return "redirect:/home/gestionarPartidos";
	}
	
	@RequestMapping(value="/registrar", method = RequestMethod.POST, params="grabar")
	public String registrar(@ModelAttribute("objPartidoPolitico")PartidoPolitico objPartidoPolitico, @RequestParam("archivo") MultipartFile archivo, Model model) {
	    PartidoPolitico objPartidoPoliticoBD = partidoPoliticoRepository.findByNombre(objPartidoPolitico.getNombre());
	    if (objPartidoPoliticoBD != null) {
	        model.addAttribute("objPartidoPolitico", objPartidoPolitico);
	        model.addAttribute("mensaje", "El nombre ya se encuentra registrado en el sistema");
	        return "nuevoPartido";
	    } else {
	        String nombreFoto = Utilitario.guardarImagen(archivo, rutaImagenes);
	        if (nombreFoto != null) {
	            objPartidoPolitico.setSimbolo(nombreFoto);
	            partidoPoliticoRepository.save(objPartidoPolitico);
	            return "redirect:/home/gestionarPartidos";
	        } else {
	            model.addAttribute("objPartidoPolitico", objPartidoPolitico);
	            model.addAttribute("mensaje", "No se pudo registrar el usuario por un problema con su simbolo");
	            return "nuevoPartido";
	        }
	    }
	}

	
	@RequestMapping(value="/editar", method=RequestMethod.POST)
	public String editar(@ModelAttribute("objPartidoPolitico")PartidoPolitico objPartidoPolitico, @RequestParam("archivo")MultipartFile archivo, Model model) {
		PartidoPolitico objPartidoPoliticoBD = partidoPoliticoRepository.findById(objPartidoPolitico.getId());
		objPartidoPoliticoBD.setNombre(objPartidoPolitico.getNombre());
		objPartidoPoliticoBD.setFechaCreacion(objPartidoPolitico.getFechaCreacion());
		objPartidoPoliticoBD.setAlumno(objPartidoPolitico.getAlumno());
		if (archivo.getOriginalFilename().compareTo("")!=0) {
			/*Aqui vamos a grabar la foto del usuario, previo a guardar el usuario*/
			String nombreFoto = Utilitario.guardarImagen(archivo, rutaImagenes);
			if (nombreFoto!=null) {
				objPartidoPoliticoBD.setSimbolo(nombreFoto);
			}
			else {
				/*Aqui significa que el usuario con ese correo ya existe*/
				model.addAttribute("objPartidoPolitico", objPartidoPolitico);
				model.addAttribute("mensaje", "No se pudo actualizar el usuario por un problema con su foto");
				return "editarPartido";
			}
		}
		partidoPoliticoRepository.save(objPartidoPoliticoBD);
		return "redirect:/home/gestionarPartidos";				
	}	
}
