package com.meep.prueba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.meep.prueba.model.Vehicle;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, String> {

	@Modifying
	@Query("DELETE FROM Vehicle v WHERE v.id NOT IN (:vehicleIds)")
	void deleteVehicles(@Param("vehicleIds") List<String> vehicleIds);

}
