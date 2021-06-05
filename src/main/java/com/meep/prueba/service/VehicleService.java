package com.meep.prueba.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meep.prueba.model.Vehicle;
import com.meep.prueba.repository.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	VehicleRepository vehicleRepository;

	private static final Logger log = LoggerFactory.getLogger(VehicleService.class);

	public List<Vehicle> getAllVehicles() throws Exception {

		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		vehicleRepository.findAll().forEach(vehicle -> vehicles.add(vehicle));
		log.info(vehicles.toString());
		return vehicles;

	}

	@Transactional
	public List<Vehicle> saveAllVehicles(List<Vehicle> vehicleList) {
		List<Vehicle> response = (List<Vehicle>) vehicleRepository.saveAll(vehicleList);
		log.info(response.toString());
		return response;
	}
	
	@Transactional
	public void deleteAllVehicles(List<String> vehicleList) {
		vehicleRepository.deleteVehicles(vehicleList);
	}
	

}
