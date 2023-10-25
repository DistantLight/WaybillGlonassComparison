package com.ngtu.WaybillGlonassComparison.entities.reports;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "fuel_waybill")
public class FuelWaybill implements ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_waybill_id")
    private Long id;
    @Column(name = "waybill_number")
    private Long waybillNumber;
    @Column(name = "fuel_grade")
    private String fuelGrade;
    @Column(name = "fuel_start")
    private Double fuelStart;
    @Column(name = "fuel_end")
    private Double fuelEnd;
    @Column(name = "consumption_rate")
    private Double consumptionRate;
    @Column(name = "fueling_own_weight")
    private Double fuelingOwnWeight;
    @Column(name = "fueling_branch_weight")
    private Double fuelingBranchWeight;
    @Column(name = "fueling_own_volume")
    private Double fuelingOwnVolume;
    @Column(name = "fueling_branch_volume")
    private Double fuelingBranchVolume;
    @Column(name = "fueling_outside_volume")
    private Double fuelingOutsideVolume;
    @Column(name = "fuel_consumption")
    private Double fuelConsumption;
    @Column(name = "fuel_total")
    private Double fuelTotal;
    @Column(name = "convoy_number")
    private int convoyNumber;

    @OneToOne(mappedBy = "fuelWaybill")
    private ComparativeReport comparativeReport;
    public FuelWaybill() {
    }

    public FuelWaybill(Long id, Long waybillNumber, String fuelGrade, Double fuelStart,
                       Double fuelEnd, Double consumptionRate, Double fuelingOwnWeight,
                       Double fuelingBranchWeight, Double fuelingOwnVolume, Double fuelingBranchVolume,
                       Double fuelingOutsideVolume, Double fuelConsumption, Double fuelTotal, int convoyNumber) {
        this.id = id;
        this.waybillNumber = waybillNumber;
        this.fuelGrade = fuelGrade;
        this.fuelStart = fuelStart;
        this.fuelEnd = fuelEnd;
        this.consumptionRate = consumptionRate;
        this.fuelingOwnWeight = fuelingOwnWeight;
        this.fuelingBranchWeight = fuelingBranchWeight;
        this.fuelingOwnVolume = fuelingOwnVolume;
        this.fuelingBranchVolume = fuelingBranchVolume;
        this.fuelingOutsideVolume = fuelingOutsideVolume;
        this.fuelConsumption = fuelConsumption;
        this.fuelTotal = fuelTotal;
        this.convoyNumber = convoyNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(Long waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public String getFuelGrade() {
        return fuelGrade;
    }

    public void setFuelGrade(String fuelGrade) {
        this.fuelGrade = fuelGrade;
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

    public Double getConsumptionRate() {
        return consumptionRate;
    }

    public void setConsumptionRate(Double consumptionRate) {
        this.consumptionRate = consumptionRate;
    }

    public Double getFuelingOwnWeight() {
        return fuelingOwnWeight;
    }

    public void setFuelingOwnWeight(Double fuelingOwnWeight) {
        this.fuelingOwnWeight = fuelingOwnWeight;
    }

    public Double getFuelingBranchWeight() {
        return fuelingBranchWeight;
    }

    public void setFuelingBranchWeight(Double fuelingBranchWeight) {
        this.fuelingBranchWeight = fuelingBranchWeight;
    }

    public Double getFuelingOwnVolume() {
        return fuelingOwnVolume;
    }

    public void setFuelingOwnVolume(Double fuelingOwnVolume) {
        this.fuelingOwnVolume = fuelingOwnVolume;
    }

    public Double getFuelingBranchVolume() {
        return fuelingBranchVolume;
    }

    public void setFuelingBranchVolume(Double fuelingBranchVolume) {
        this.fuelingBranchVolume = fuelingBranchVolume;
    }

    public Double getFuelingOutsideVolume() {
        return fuelingOutsideVolume;
    }

    public void setFuelingOutsideVolume(Double fuelingOutsideVolume) {
        this.fuelingOutsideVolume = fuelingOutsideVolume;
    }

    public Double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public Double getFuelTotal() {
        return fuelTotal;
    }

    public void setFuelTotal(Double fuelTotal) {
        this.fuelTotal = fuelTotal;
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

        FuelWaybill that = (FuelWaybill) o;

        if (convoyNumber != that.convoyNumber) return false;

        if (Math.abs(fuelStart - that.fuelStart) > 0.001) return false;

        if (Math.abs(fuelEnd - that.fuelEnd) > 0.001) return false;

        if (Math.abs(consumptionRate - that.consumptionRate) > 0.001) return false;

        if (Math.abs(fuelingOwnWeight - that.fuelingOwnWeight) > 0.001) return false;

        if (Math.abs(fuelingBranchWeight - that.fuelingBranchWeight) > 0.001) return false;

        if (Math.abs(fuelingOwnVolume - that.fuelingOwnVolume) > 0.001) return false;

        if (Math.abs(fuelingBranchVolume - that.fuelingBranchVolume) > 0.001) return false;

        if (Math.abs(fuelingOutsideVolume - that.fuelingOutsideVolume) > 0.001) return false;

        if (Math.abs(fuelConsumption - that.fuelConsumption) > 0.001) return false;

        if (Math.abs(fuelTotal - that.fuelTotal) > 0.001) return false;

        return Objects.equals(id, that.id) &&
                Objects.equals(waybillNumber, that.waybillNumber) &&
                Objects.equals(fuelGrade, that.fuelGrade) &&
                Objects.equals(comparativeReport, that.comparativeReport);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, waybillNumber, fuelGrade, fuelStart, fuelEnd, consumptionRate, fuelingOwnWeight, fuelingBranchWeight, fuelingOwnVolume, fuelingBranchVolume, fuelingOutsideVolume, fuelConsumption, fuelTotal, convoyNumber, comparativeReport);
    }

    @Override
    public String toString() {
        return "(" + id +
                ", " + waybillNumber +
                ", " + fuelGrade +
                ", " + fuelStart +
                ", " + fuelEnd +
                ", " + consumptionRate +
                ", " + fuelingOwnWeight +
                ", " + fuelingBranchWeight +
                ", " + fuelingOwnVolume +
                ", " + fuelingBranchVolume +
                ", " + fuelingOutsideVolume +
                ", " + fuelConsumption +
                ", " + fuelTotal +
                ", " + convoyNumber +
                ')';
    }
}
