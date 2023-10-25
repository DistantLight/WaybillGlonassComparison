package com.ngtu.WaybillGlonassComparison.entities.reports;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "fuel_glonass")
public class FuelGlonass implements ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_glonass_id")
    private Long id;
    @Column(name = "vehicle_number")
    private String vehicleNumber;
    @Column(name = "trip_date")
    private Date tripDate;
    @Column(name = "fuel_start")
    private Double fuelStart;
    @Column(name = "fuel_end")
    private Double fuelEnd;
    @Column(name = "refill_total")
    private Double refillTotal;
    @Column(name = "convoy_number")
    private int convoyNumber;
    @ManyToOne
    @JoinColumn(name = "comparative_report_id")
    private ComparativeReport comparativeReport;

    public FuelGlonass() {
    }

    public FuelGlonass(Long id, String vehicleNumber, Date tripDate,
                       Double fuelStart, Double fuelEnd, Double refillTotal,int convoyNumber) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.tripDate = tripDate;
        this.fuelStart = fuelStart;
        this.fuelEnd = fuelEnd;
        this.refillTotal = refillTotal;
        this.convoyNumber = convoyNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public Double getFuelStart() {
        return fuelStart;
    }

    public void setFuelStart(Double fuelStart) {
        this.fuelStart = fuelStart;
    }

    public Double getFuelEnd() {
        return fuelEnd;
    }

    public void setFuelEnd(Double fuelEnd) {
        this.fuelEnd = fuelEnd;
    }

    public Double getRefillTotal() {
        return refillTotal;
    }

    public void setRefillTotal(Double refillTotal) {
        this.refillTotal = refillTotal;
    }

    public int getConvoyNumber() {
        return convoyNumber;
    }

    public void setConvoyNumber(int convoyNumber) {
        this.convoyNumber = convoyNumber;
    }

    public ComparativeReport getComparativeReport() {
        return comparativeReport;
    }

    public void setComparativeReport(ComparativeReport comparativeReport) {
        this.comparativeReport = comparativeReport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuelGlonass that = (FuelGlonass) o;
        return convoyNumber == that.convoyNumber && Objects.equals(id, that.id) && Objects.equals(vehicleNumber, that.vehicleNumber) && Objects.equals(tripDate, that.tripDate) && Objects.equals(fuelStart, that.fuelStart) && Objects.equals(fuelEnd, that.fuelEnd) && Objects.equals(refillTotal, that.refillTotal) && Objects.equals(comparativeReport, that.comparativeReport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicleNumber, tripDate, fuelStart, fuelEnd, refillTotal, convoyNumber, comparativeReport);
    }

    @Override
    public String toString() {
        return "FuelGlonass{" +
                "id=" + id +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", tripDate=" + tripDate +
                ", fuelStart=" + fuelStart +
                ", fuelEnd=" + fuelEnd +
                ", fuelTotal=" + refillTotal +
                '}';
    }
}
