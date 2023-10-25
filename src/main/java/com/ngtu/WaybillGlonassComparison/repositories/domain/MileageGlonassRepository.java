package com.ngtu.WaybillGlonassComparison.repositories.domain;

import com.ngtu.WaybillGlonassComparison.entities.reports.MileageGlonass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MileageGlonassRepository extends JpaRepository<MileageGlonass, Long> {
    List<MileageGlonass> findByVehicleNumberAndTripDateBetween(String number, Date date1, Date date2);

    List<MileageGlonass> findByVehicleNumberAndConvoyNumberAndTripDateBetween(String number, int convoy, Date date1, Date date2);

    List<MileageGlonass> findByConvoyNumber(int convoyNumber);

    void deleteByConvoyNumber(int convoyNumber);

}
