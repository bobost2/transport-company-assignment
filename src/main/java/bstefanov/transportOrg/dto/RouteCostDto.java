package bstefanov.transportOrg.dto;

import java.math.BigDecimal;

public class RouteCostDto {
    private long routeId;
    private BigDecimal costs;

    public RouteCostDto(long routeId, BigDecimal costs) {
        this.routeId = routeId;
        this.costs = costs;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public BigDecimal getCosts() {
        return costs;
    }

    public void setCosts(BigDecimal costs) {
        this.costs = costs;
    }

    @Override
    public String toString() {
        return "RouteCostDto{" +
                "routeId=" + routeId +
                ", costs=" + costs +
                '}';
    }
}
