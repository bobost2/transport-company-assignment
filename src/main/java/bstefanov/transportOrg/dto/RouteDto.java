package bstefanov.transportOrg.dto;

import java.time.LocalDateTime;

public class RouteDto {
    private String startingPoint;
    private String destinationPoint;
    private int distance;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private long companyId;
    private long vehicleId;
    private long employeeId;

    public RouteDto(String startingPoint, String destinationPoint, int distance, LocalDateTime departureTime,
                    LocalDateTime arrivalTime, long companyId, long vehicleId, long employeeId) {
        this.startingPoint = startingPoint;
        this.destinationPoint = destinationPoint;
        this.distance = distance;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.companyId = companyId;
        this.vehicleId = vehicleId;
        this.employeeId = employeeId;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "RouteDto{" +
                "startingPoint='" + startingPoint + '\'' +
                ", destinationPoint='" + destinationPoint + '\'' +
                ", distance=" + distance +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", companyId=" + companyId +
                ", vehicleId=" + vehicleId +
                ", employeeId=" + employeeId +
                '}';
    }
}
