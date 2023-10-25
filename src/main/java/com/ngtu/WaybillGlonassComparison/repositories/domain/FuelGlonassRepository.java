package com.ngtu.WaybillGlonassComparison.repositories.domain;

import com.ngtu.WaybillGlonassComparison.entities.reports.FuelGlonass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface FuelGlonassRepository extends JpaRepository<FuelGlonass, Long> {
    List<FuelGlonass> findByVehicleNumberAndTripDateBetween(String number, Date date1, Date date2);

    List<FuelGlonass> findByVehicleNumberAndConvoyNumberAndTripDateBetween(String number, int convoy, Date date1, Date date2);

    List<FuelGlonass> findByConvoyNumber(int convoyNumber);

    void deleteByConvoyNumber(int convoyNumber);
}
