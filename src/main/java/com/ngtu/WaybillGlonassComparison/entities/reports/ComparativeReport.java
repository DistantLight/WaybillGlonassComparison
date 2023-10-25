package com.ngtu.WaybillGlonassComparison.entities.reports;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "comparative_report")
public class ComparativeReport implements ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne()
    @JoinColumn(name = "fuel_waybill_id", referencedColumnName = "fuel_waybill_id")
    private FuelWaybill fuelWaybill;

    @OneToOne()
    @JoinColumn(name = "mileage_waybill_id", referencedColumnName = "mileage_waybill_id")
    private MileageWaybill mileageWaybill;

    @OneToMany(mappedBy = "comparativeReport")
    private List<MileageGlonass> mileageGlonasses;

    @OneToMany(mappedBy = "comparativeReport")
    private List<FuelGlonass> fuelGlonasses;

    @Column(name = "max_speed")
    private int maxSpeed;

    @Column(name = "average_speed")
    private int averageSpeed;

    @Column(name = "mileage")
    private Double mileage;

    @Column(name = "fuel_start")
    private Double fuelStart;

    @Column(name = "fuel_end")
    private Double fuelEnd;

    @Column(name = "refill_total")
    private Double refillTotal;

    @Column(name = "mileage_difference")
    private Double mileageDifference;

    @Column(name = "fuel_difference_start")
    private Double fuelDifferenceStart;

    @Column(name = "fuel_difference_end")
    private Double fuelDifferenceEnd;

    @Column(name = "refill_difference")
    private Double refillDifference;

    @Column(name = "consumption_total")
    private Double consumptionTotal;

    @Column(name = "consumption_difference")
    private Double consumptionDifference;

    @Column(name = "fuel_deviation_reason")
    private String fuelDeviationReason;

    @Column(name = "fuel_deviation_action")
    private String fuelDeviationAction;

    @Column(name = "mileage_deviation_reason")
    private String mileageDeviationReason;

    @Column(name = "mileage_deviation_action")
    private String mileageDeviationAction;

    @Column(name = "note")
    private String note;

    @Column(name = "convoy_number")
    private int convoyNumber;

    public ComparativeReport() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FuelWaybill getFuelWaybill() {
        return fuelWaybill;
    }

    public void setFuelWaybill(FuelWaybill fuelWaybill) {
        this.fuelWaybill = fuelWaybill;
    }

    public MileageWaybill getMileageWaybill() {
        return mileageWaybill;
    }

    public void setMileageWaybill(MileageWaybill mileageWaybill) {
        this.mileageWaybill = mileageWaybill;
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

    public Double getMileageDifference() {
        return mileageDifference;
    }

    public void setMileageDifference(Double mileageDifference) {
        this.mileageDifference = mileageDifference;
    }

    public Double getFuelDifferenceStart() {
        return fuelDifferenceStart;
    }

    public void setFuelDifferenceStart(Double fuelDifferenceStart) {
        this.fuelDifferenceStart = fuelDifferenceStart;
    }

    public Double getFuelDifferenceEnd() {
        return fuelDifferenceEnd;
    }

    public void setFuelDifferenceEnd(Double fuelDifferenceEnd) {
        this.fuelDifferenceEnd = fuelDifferenceEnd;
    }

    public Double getRefillDifference() {
        return refillDifference;
    }

    public void setRefillDifference(Double refillDifference) {
        this.refillDifference = refillDifference;
    }

    public Double getConsumptionDifference() {
        return consumptionDifference;
    }

    public void setConsumptionDifference(Double consumptionDifference) {
        this.consumptionDifference = consumptionDifference;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getConvoyNumber() {
        return convoyNumber;
    }

    public void setConvoyNumber(int convoyNumber) {
        this.convoyNumber = convoyNumber;
    }

    public Double getConsumptionTotal() {
        return consumptionTotal;
    }

    public void setConsumptionTotal(Double consumptionTotal) {
        this.consumptionTotal = consumptionTotal;
    }

    public List<MileageGlonass> getMileageGlonasses() {
        return mileageGlonasses;
    }

    public void setMileageGlonasses(List<MileageGlonass> mileageGlonasses) {
        this.mileageGlonasses = mileageGlonasses;
    }

    public List<FuelGlonass> getFuelGlonasses() {
        return fuelGlonasses;
    }

    public void setFuelGlonasses(List<FuelGlonass> fuelGlonasses) {
        this.fuelGlonasses = fuelGlonasses;
    }

    public String getFuelDeviationReason() {
        return fuelDeviationReason;
    }

    public void setFuelDeviationReason(String fuelDeviationReason) {
        this.fuelDeviationReason = fuelDeviationReason;
    }

    public String getFuelDeviationAction() {
        return fuelDeviationAction;
    }

    public void setFuelDeviationAction(String fuelDeviationAction) {
        this.fuelDeviationAction = fuelDeviationAction;
    }

    public String getMileageDeviationReason() {
        return mileageDeviationReason;
    }

    public void setMileageDeviationReason(String mileageDeviationReason) {
        this.mileageDeviationReason = mileageDeviationReason;
    }

    public String getMileageDeviationAction() {
        return mileageDeviationAction;
    }

    public void setMileageDeviationAction(String mileageDeviationAction) {
        this.mileageDeviationAction = mileageDeviationAction;
    }

    @Override
    public String toString() {
        return "ComparativeReport{" +
                "id=" + id +
                ", " + mileageWaybill.getVehicleNumber() +
                ", " + mileageWaybill.getWaybillNumber() +
                ", maxSpeed=" + maxSpeed +
                ", averageSpeed=" + averageSpeed +
                ", mileage=" + mileage +
                ", fuelStart=" + fuelStart +
                ", fuelEnd=" + fuelEnd +
                ", refillTotal=" + refillTotal +
                '}';
    }
}
