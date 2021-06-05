package com.meep.prueba.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meep.prueba.error.GlobalException;
import com.meep.prueba.model.Vehicle;
import com.meep.prueba.service.VehicleService;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/meep/api")
public class VehicleController {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

	@Autowired
	 private VehicleService vehicleService;

	@RequestMapping(value = "/getVehicles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Iterable<Vehicle>> getVehicles() throws GlobalException {
		try {
			LOGGER.info("::: - Obtener recursos disponibles: {}");
			return new ResponseEntity<Iterable<Vehicle>>(vehicleService.getAllVehicles(),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new GlobalException("Error al listar datos", e);
		}
	}

}
