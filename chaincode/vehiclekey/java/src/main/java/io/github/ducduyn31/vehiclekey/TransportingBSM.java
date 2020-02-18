package io.github.ducduyn31.vehiclekey;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.security.PublicKey;

@DataType()
public final class TransportingBSM {

    @Property()
    private final PublicKey vehicleID;
    @Property()
    private final double direction;
    @Property()
    private final double velocity;

    public TransportingBSM(
            @JsonProperty("vehicle_id") final PublicKey vehicleID,
            @JsonProperty("direction") double direction,
            @JsonProperty("velocity") double velocity) {
        this.vehicleID = vehicleID;
        this.direction = direction;
        this.velocity = velocity;
    }

    public PublicKey getVehicleID() {
        return vehicleID;
    }

    public double getDirection() {
        return direction;
    }

    public double getVelocity() {
        return velocity;
    }
}
