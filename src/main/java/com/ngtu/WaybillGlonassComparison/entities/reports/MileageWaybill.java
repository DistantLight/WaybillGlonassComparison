package com.ngtu.WaybillGlonassComparison.entities.reports;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "mileage_waybill")
public class MileageWaybill implements ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mileage_waybill_id")
    private Long id;
    @Column(name = "vehicle_id")
    private Long vehicleId;
    @Column(name = "convoy_number")
    private int convoyNumber;
    @Column(name = "transport_column")
    private String column;
    @Column(name = "vehicle_modification")
    private String vehicleModification;
    @Column(name = "vehicle_number")
    private String vehicleNumber;
    @Column(name = "driver_name")
    private String driverName;
    @Column(name = "waybill_number")
    private Long waybillNumber;
    @Column(name = "departure_date")
    private Date departureDate;
    @Column(name = "return_date")
    private Date returnDate;
    @Column(name = "mileage")
    private Double mileage;
    @Column(name = "planned_route")
    private String plannedRoute;
    @Column(name = "actual_route")
    private String actualRoute;

    @OneToOne(mappedBy = "mileageWaybill")
    private ComparativeReport comparativeReport;

    public MileageWaybill() {
    }

    public MileageWaybill(Long id, Long vehicleId, int convoyNumber, String column, String vehicleModification, String vehicleNumber, String driverName, Long waybillNumber, Date departureDate, Date returnDate, Double mileage, String plannedRoute, String actualRoute) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.convoyNumber = convoyNumber;
        this.column = column;
        this.vehicleModification = vehicleModification;
        this.vehicleNumber = vehicleNumber;
        this.driverName = driverName;
        this.waybillNumber = waybillNumber;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.mileage = mileage;
        this.plannedRoute = plannedRoute;
        this.actualRoute = actualRoute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getVehicleModification() {
        return vehicleModification;
    }

    public void setVehicleModification(String vehicleModification) {
        this.vehicleModification = vehicleModification;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Long getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(Long waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public String getPlannedRoute() {
        return plannedRoute;
    }

    public void setPlannedRoute(String plannedRoute) {
        this.plannedRoute = plannedRoute;
    }

    public String getActualRoute() {
        return actualRoute;
    }

    public void setActualRoute(String actualRoute) {
        this.actualRoute = actualRoute;
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
        MileageWaybill that = (MileageWaybill) o;
        return convoyNumber == that.convoyNumber && Objects.equals(id, that.id) && Objects.equals(vehicleId, that.vehicleId) && Objects.equals(column, that.column) && Objects.equals(vehicleModification, that.vehicleModification) && Objects.equals(vehicleNumber, that.vehicleNumber) && Objects.equals(driverName, that.driverName) && Objects.equals(waybillNumber, that.waybillNumber) && Objects.equals(departureDate, that.departureDate) && Objects.equals(returnDate, that.returnDate) && Objects.equals(mileage, that.mileage) && Objects.equals(plannedRoute, that.plannedRoute) && Objects.equals(actualRoute, that.actualRoute) && Objects.equals(comparativeReport, that.comparativeReport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicleId, convoyNumber, column, vehicleModification, vehicleNumber, driverName, waybillNumber, departureDate, returnDate, mileage, plannedRoute, actualRoute, comparativeReport);
    }

    @Override
    public String toString() {
        return  "(" + id +
                ", " + vehicleId +
                ", " + convoyNumber +
                ", " + column +
                ", " + vehicleModification +
                ", " + vehicleNumber +
                ", " + driverName +
                ", " + waybillNumber +
                ", " + departureDate +
                ", " + returnDate +
                ", " + mileage +
                ", " + plannedRoute +
                ", " + actualRoute +
                ')';
    }
}
