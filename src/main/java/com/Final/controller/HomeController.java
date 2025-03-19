package com.Final.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Final.repository.*;
import com.Final.entity.*;

@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired
	PartidoPoliticoRepository partidoPoliticoRepository;
	
	@GetMapping("/gestionPartidoPolitico")
	public String gestionPartidoPolitcos(Model model){
		List<PartidoPolitico> listaPartidoPolitico = partidoPoliticoRepository.findAll();
		model.addAttribute("listaPartidoPolitico", listaPartidoPolitico);
		return "gestionarPartidos";
	}
}