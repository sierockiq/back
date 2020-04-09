package com.quentin.sierocki.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quentin.sierocki.controller.model.CommandDTO;
import com.quentin.sierocki.exception.fonctionnal.FunctionnalException;
import com.quentin.sierocki.service.CommandService;
import com.quentin.sierocki.service.converter.ConvertionException;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
public class CommandController {

	@Autowired
	CommandService commandService;

	@GetMapping("/user/{idUser}/command")
	public String getProduct(@PathVariable String idUser) {
		return null;
	}

	@PostMapping("/user/{idUser}/command")
	public CommandDTO postProduct(@PathVariable String idUser, @Valid @RequestBody CommandDTO command)
			throws FunctionnalException, ConvertionException {
		return commandService.save(idUser, command);
	}

}