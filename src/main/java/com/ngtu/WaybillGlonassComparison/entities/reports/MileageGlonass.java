package com.ngtu.WaybillGlonassComparison.entities.reports;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "mileage_glonass")
public class MileageGlonass implements ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mileage_glonass_id")
    private Long id;
    @Column(name = "trip_date")
    private Date tripDate;
    @Column(name = "vehicle_number")
    private String vehicleNumber;
    @Column(name = "max_speed")
    private int maxSpeed;
    @Column(name = "average_speed")
    private int averageSpeed;
    @Column(name = "mileage")
    private Double mileage;
    @Column(name = "convoy_number")
    private int convoyNumber;
    @ManyToOne
    @JoinColumn(name = "comparative_report_id")
    private ComparativeReport comparativeReport;

    public MileageGlonass() {
    }

    public MileageGlonass(Long id, Date tripDate, String vehicleNumber, int maxSpeed, int averageSpeed, Double mileage, int convoyNumber) {
        this.id = id;
        this.tripDate = tripDate;
        this.vehicleNumber = vehicleNumber;
        this.maxSpeed = maxSpeed;
        this.averageSpeed = averageSpeed;
        this.mileage = mileage;
        this.convoyNumber = convoyNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
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
        MileageGlonass that = (MileageGlonass) o;
        return maxSpeed == that.maxSpeed && averageSpeed == that.averageSpeed && convoyNumber == that.convoyNumber && Objects.equals(id, that.id) && Objects.equals(tripDate, that.tripDate) && Objects.equals(vehicleNumber, that.vehicleNumber) && Objects.equals(mileage, that.mileage) && Objects.equals(comparativeReport, that.comparativeReport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tripDate, vehicleNumber, maxSpeed, averageSpeed, mileage, convoyNumber, comparativeReport);
    }

    @Override
    public String toString() {
        return "(" + id +
                ", " + tripDate +
                ", " + vehicleNumber +
                ", " + maxSpeed +
                ", " + averageSpeed +
                ", " + mileage +
                ", " + convoyNumber +
                ')';
    }
}
