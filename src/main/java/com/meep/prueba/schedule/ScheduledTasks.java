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
//    		LOGGER.info("The time is now {}", dateFormatRecuperados.format(new Date()));
//    		//1ro obtener vehiculos diponibles llamando al servicio de meep
//      		ListaNegraServiceImpl listaNegraService = new ListaNegraServiceImpl();
//      		//obtiene alertas(robados)
//      		List<Alerta> alertasOpenALPR = listaNegraService.getRegistries(urlOpenALPRDesarrollo, consultaAlertasOpenALPR, idGrupoAlertas, companyId);
//      		//2do obtener placa y guardar en string[]
//      		 ArrayList<String> listaPlacasRobadosOpenALPR = new ArrayList<String>();
//      		 for(Alerta autoRobadoAlerta : alertasOpenALPR) {
//      			listaPlacasRobadosOpenALPR.add(autoRobadoAlerta.getPlate_number());
//    			LOGGER.info("Se agrega placa de Auto Robado a la lista de OPENALPR: "+ autoRobadoAlerta.getPlate_number());
//    		}
//      		
//      		//3ro obtener robados de dgi
//    		WSVehiculos_Service service = new WSVehiculos_Service();
//    		WSVehiculos serviceImpl = service.getWSVehiculosPort();
//    		
//    		System.out.println("Parametros: "+ userDgdii + " " + passwordDgdii + " " + dateFormatRobados.format(new Date()) + " 00:00:01" +" "+ dateFormatRobados.format(new Date()) + " 23:59:59");
//    		List<StringArray> respuestaRobados = serviceImpl.consultaVehiRobados(userDgdii, passwordDgdii, dateFormatRobados.format(new Date()) + " 00:00:01", dateFormatRobados.format(new Date()) + " 23:59:59");
//    		List<AlertaSOAP> listaRobados = new ArrayList<AlertaSOAP>();
//    		for(StringArray alerta : respuestaRobados) {
//    			AlertaSOAP autoRobado = new AlertaSOAP();
//    			
//    			autoRobado.setEstatus(alerta.getItem().get(0));
//    			autoRobado.setPlaca(alerta.getItem().get(1));
//    			autoRobado.setSerie(alerta.getItem().get(2));
//    			autoRobado.setNoMotor(alerta.getItem().get(3));
//    			autoRobado.setModelo(alerta.getItem().get(4));
//    			autoRobado.setMarca(alerta.getItem().get(5));
//    			autoRobado.setSubmarca(alerta.getItem().get(6));
//    			autoRobado.setColor(alerta.getItem().get(7));
//    			autoRobado.setFechaRobo(alerta.getItem().get(8));
//    			autoRobado.setAveriguacion(alerta.getItem().get(9));
//    			autoRobado.setEntidadRobo(alerta.getItem().get(10));
//    			autoRobado.setMunicipioRobo(alerta.getItem().get(11));
//    			autoRobado.setCalleRobo(alerta.getItem().get(12));
//    			autoRobado.setNumExt(alerta.getItem().get(13));
//    			autoRobado.setNumInt(alerta.getItem().get(14));
//    			autoRobado.setFechaActualizacion(alerta.getItem().get(15));
//    			autoRobado.setFuenteInfo(alerta.getItem().get(17));
//    			autoRobado.setIdAlterna(alerta.getItem().get(18));
//    			
//    			listaRobados.add(autoRobado);
//    		}
//    		//4to obtener placa y guardar en string[]
//    		List<String> listaPlacasRobados = new ArrayList<String>();
//    		for(AlertaSOAP autoRobado : listaRobados) {
//    			listaPlacasRobados.add(autoRobado.getPlaca());
//    			LOGGER.info("Se agrega placa de Auto Robado a la lista de DGDII: "+ autoRobado.getPlaca());
//    		}
//    		//5to comparar e insertar nuevos id en String[] robados
//    		if(listaPlacasRobadosOpenALPR != null && listaPlacasRobados != null) {
//    			LOGGER.info("Se comparan las listas para agregar nuevas placa de Auto Robado a OpenALPR");
//    			Set<String> inputSet = new HashSet<String>(listaPlacasRobadosOpenALPR);
//    	        for (String str : listaPlacasRobados) {
//    	        	//si no deja agregarlo es por que esta repetido por lo tanto lo retiramos de la lista
//    	            if (!inputSet.add(str)) {
//    	            	
//    	            		  for(int i = 0; i<listaRobados.size(); i++){
//    	            		   if(listaRobados.get(i).getPlaca().equals(str)){
//    	            		    	listaRobados.remove(i);
//    	            		    	LOGGER.info("Placa existente en OpenALPR se elimina de la lista: "+ str);
//    	            		    }
//    	            		  }
//    	            }
//    	            else LOGGER.info("Placa inexistente en OpenALPR se mantiene en la lista: "+ str);
//    	        }
//    	    }
//    		
//    		//6to insertar nuevos id robados atrave de servicio en openalpr (guardar datos del registro en el LOG)
//    		//guarda alertas(robados)
//    		ListaNegraDto listaNegraDto = new ListaNegraDto();
//    	    List<String[]> registries = new ArrayList<String[]>();
//    	    for(int i = 0; i<listaRobados.size(); i++){
//    	    	if(!listaRobados.get(i).getPlaca().equals("") && !listaRobados.get(i).getPlaca().equals("SN") && !listaRobados.get(i).getPlaca().equals("SP")  && !listaRobados.get(i).getPlaca().equals("SIN INFORMACION")) {
//    	    		String[] datos = {listaRobados.get(i).getPlaca(),listaRobados.get(i).getMarca()+ " " + listaRobados.get(i).getSubmarca()+ " " + listaRobados.get(i).getModelo()};
//    	    		
//    	    		LOGGER.info("Item valido SE intentara REGISTRAR en OpenALPR: "+ datos.toString());
//    	    		registries.add(datos);
//    	    	}
//    	    	else {
//    	    		LOGGER.info("Item SIN PLACA NO SE intentara REGISTRAR en OpenALPR: "+ listaRobados.get(i).getPlaca(),listaRobados.get(i).getMarca()+ " " + listaRobados.get(i).getSubmarca()+ " " + listaRobados.get(i).getModelo());
//    	    	}
//    	    	
//    		}
//    		
//    		listaNegraDto.setRegistries(registries);
//    	    listaNegraService.postRegistries(listaNegraDto, idGrupoAlertas, urlOpenALPRDesarrollo, registroAlertasOpenALPR, companyId);
//    
//    }
//    
//	//@Scheduled(cron="*/1200 * * * * *") //se ejecuta cada media noche
//	@Scheduled(cron="0 0/30 11,23 * * *") //se ejecuta cada doce horas
//    public void eliminarVehiculosRecuperadosDGI() {
//
//    	//3ro obtener robados de dgi
//			WSVehiculos_Service service = new WSVehiculos_Service();
//			WSVehiculos serviceImpl = service.getWSVehiculosPort();
//			
//    		//7mo obtener recuperados de dgi
//	  		System.out.println("Parametros: "+ userDgdii + " " + passwordDgdii + " " + dateFormatRobados.format(new Date()) + " 00:00:01" +" "+ dateFormatRecuperados.format(new Date()));
//    		List<StringArray> respuestaRecuperados = serviceImpl.consultaVehiRecuperados(userDgdii, passwordDgdii,  dateFormatRobados.format(new Date()) + " 00:00:01" , dateFormatRecuperados.format(new Date()) );
//     		List<AlertaSOAP> listaRecuperados = new ArrayList<AlertaSOAP>();
//    		for(StringArray alerta : respuestaRecuperados) {
//    			AlertaSOAP autoRecuperado = new AlertaSOAP();
//    			
//    			autoRecuperado.setEstatus(alerta.getItem().get(0));
//    			autoRecuperado.setPlaca(alerta.getItem().get(1));
//    			autoRecuperado.setSerie(alerta.getItem().get(2));
//    			autoRecuperado.setNoMotor(alerta.getItem().get(3));
//    			autoRecuperado.setModelo(alerta.getItem().get(4));
//    			autoRecuperado.setMarca(alerta.getItem().get(5));
//    			autoRecuperado.setSubmarca(alerta.getItem().get(6));
//    			autoRecuperado.setColor(alerta.getItem().get(7));
//    			autoRecuperado.setFechaRobo(alerta.getItem().get(8));
//    			autoRecuperado.setAveriguacion(alerta.getItem().get(9));
//    			autoRecuperado.setEntidadRobo(alerta.getItem().get(10));
//    			autoRecuperado.setMunicipioRobo(alerta.getItem().get(11));
//    			autoRecuperado.setCalleRobo(alerta.getItem().get(12));
//    			autoRecuperado.setNumExt(alerta.getItem().get(13));
//    			autoRecuperado.setNumInt(alerta.getItem().get(14));
//    			autoRecuperado.setFechaActualizacion(alerta.getItem().get(15));
//    			autoRecuperado.setFuenteInfo(alerta.getItem().get(17));
//    			autoRecuperado.setIdAlterna(alerta.getItem().get(18));
//    			
//    			listaRecuperados.add(autoRecuperado);
//    		}
//    		
//    		//8vo obtener id y guardar en string[]
//    		List<String> listaPlacasRecuperados = new ArrayList<String>();
//    		for(AlertaSOAP autoRecuperado : listaRecuperados) {
//    			
//    			listaPlacasRecuperados.add(autoRecuperado.getPlaca());
//    			LOGGER.info("Se agrega placa de Auto Recuperado a la lista de DGDII: "+ autoRecuperado.getPlaca());
//    		}
//    		//9no eliminar nuevos id de recuperados atravez del servicio en openalpr (guardar datos de la eliminacion en el LOG)
//    		//obtiene alertas(robados)
//    		ListaNegraServiceImpl listaNegraService = new ListaNegraServiceImpl();
//    		List<Alerta> alertasOpenALPRNuevos = listaNegraService.getRegistries(urlOpenALPRDesarrollo, consultaAlertasOpenALPR, idGrupoAlertas, companyId);
//      		//2do obtener placa y guardar en string[]
//      		 ArrayList<String> listaPlacasRobadosOpenALPRNuevos = new ArrayList<String>();
//      		 for(Alerta autoRobadoAlerta : alertasOpenALPRNuevos) {
//    			
//      			listaPlacasRobadosOpenALPRNuevos.add(autoRobadoAlerta.getPlate_number());
//    			LOGGER.info("Se agrega placa de Auto Robado a la lista de OpenALPR: "+ autoRobadoAlerta.getPlate_number());
//    			
//    		}
//    		
//      		LOGGER.info("**Se TERMINA DE agregar placas de Autos Robados a la lista de OpenALPR.**");
//    		
//      		//10mo eliminar nuevos id de recuperados atravez del servicio en openalpr (guardar datos de la eliminacion en el LOG) elimina(recuperados) de alertas(robados)
//      		//10mo comparar y eliminar nuevos id en String[] recuperados
//     		if(listaPlacasRobadosOpenALPRNuevos != null && listaPlacasRecuperados != null) {
//     			LOGGER.info("Se comparan las listas para eliminar placas de Auto Recuperados a OpenALPR");
//     			int recuperados = 0;
//     	        for (String str : listaPlacasRecuperados) {
//     	            if (listaPlacasRobadosOpenALPRNuevos.contains(str)) {
//     	            	 for(Alerta autoRobadoAlerta : alertasOpenALPRNuevos) {
//     	            		 if (autoRobadoAlerta.getPlate_number().equals(str)) {
//         	          			String idAlert = autoRobadoAlerta.getId() + "";
//             	            	listaNegraService.deleteRegistries(idAlert, urlOpenALPRDesarrollo, borrarAlertasOpenALPR, companyId);
//             	                LOGGER.info("Se intenta eliminar placa existente en OpenALPR por RECUPERACION DE AUTO id: "+ str);
//             	               recuperados++;
//     	            		 }
//     	            	 }
//     	        	}
//     	        }
//     	       LOGGER.info("Se TERMINA DE comparan las listas para eliminar placas de Auto Recuperados a se recuperon: " + recuperados + " autos en esta hora.");
//     	    }
	}
}
