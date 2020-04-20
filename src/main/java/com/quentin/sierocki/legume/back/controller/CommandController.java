package com.quentin.sierocki.legume.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.quentin.sierocki.legume.back.controller.converter.ConvertionException;
import com.quentin.sierocki.legume.back.controller.converter.DAOToDTOConverter;
import com.quentin.sierocki.legume.back.controller.converter.DTOToDAOConverter;
import com.quentin.sierocki.legume.back.controller.model.CommandDTO;
import com.quentin.sierocki.legume.back.controller.model.ValidationException;
import com.quentin.sierocki.legume.back.service.CommandService;
import com.quentin.sierocki.legume.back.service.ServiceException;

@RestController
public class CommandController {

	@Autowired
	CommandService commandService;

	@PostMapping("/command/save")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CommandDTO postCommand(@RequestHeader long idUser, @RequestBody CommandDTO commandDTO)
			throws ControllerException,ValidationException  {
		try {
			commandDTO.validate();
			return DAOToDTOConverter.commandDAOToCommandDTO(
					commandService.save(idUser, DTOToDAOConverter.commandDTOToCommandDAO(commandDTO)));
		} catch (ServiceException | ConvertionException e) {
			throw new ControllerException(e.getMessageRetour(),"ProductController->addNewProduct" + e.getPathMethod(), e.getMessage(), e);
		}
	}

	@GetMapping("/command/{idCommand}/status/{status}")
	public CommandDTO cancelCommand(@RequestHeader long idUser, @PathVariable long idCommand,
			@PathVariable String status) throws ControllerException {
		try {
			return DAOToDTOConverter.commandDAOToCommandDTO(commandService.cancelCommand(idCommand, status));
		} catch (ServiceException | ConvertionException e) {
			throw new ControllerException(e.getMessageRetour(), "ProductController->addNewProduct" + e.getPathMethod(),
					e.getMessage(), e);
		}
	}

}