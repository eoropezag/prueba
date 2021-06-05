package com.meep.prueba.schedule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meep.prueba.model.Vehicle;
import com.meep.prueba.service.VehicleService;

@Component
public class ScheduledTasks {

	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ScheduledTasks.class);
	private static RestTemplate restTemplate = new RestTemplate();
	private static final String baseURL = "https://apidev.meep.me/tripplan/api/v1/routers/lisboa/resources";

	@Autowired
	private VehicleService vehicleService;

	@Scheduled(cron = "30 * * * * *") // se ejecuta cada 30 segundos
	public void consultarVechiculoDisponibles() throws Exception {

		System.out.println("Tarea ejecutada cada 30 segundos - " + System.currentTimeMillis() / 1000);
		try {
			
			List<Vehicle> vehicleList = new ArrayList<Vehicle>();
			List<Vehicle> vehicleListSaved = new ArrayList<Vehicle>();
			List<String> idsVehicles = null;
			Vehicle[] jsonObj = null;
			String json = restTemplate.getForObject(baseURL
					+ "?lowerLeftLatLon=38.711046,-9.160096&upperRightLatLon=38.739429,-9.137115&companyZoneIds=545,467,473",
					String.class);
			LOGGER.info(json);

			ObjectMapper mapper = new ObjectMapper();
			jsonObj = mapper.readValue(json, Vehicle[].class);
			LOGGER.info(jsonObj.toString());

			for (Vehicle vehicle : jsonObj) {
				vehicleList.add(vehicle);
				System.out.println(vehicle.getId());
			}
			
			vehicleListSaved = vehicleService.saveAllVehicles(vehicleList);
			idsVehicles = vehicleListSaved.stream().map(Vehicle::getId).collect(Collectors.toList());
			vehicleService.deleteAllVehicles(idsVehicles);

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
