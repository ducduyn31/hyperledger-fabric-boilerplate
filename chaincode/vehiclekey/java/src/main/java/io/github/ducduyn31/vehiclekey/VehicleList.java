package io.github.ducduyn31.vehiclekey;

import io.github.ducduyn31.vehiclekey.ledgerapi.StateList;
import org.hyperledger.fabric.contract.Context;

public class VehicleList {

    private StateList vehicleList;

    public VehicleList(Context ctx) {
        this.vehicleList = StateList.getStateList(ctx, VehicleList.class.getSimpleName(), VehicleState::deserialize);
    }

    public VehicleList addVehicle(VehicleState vehicle) {
        vehicleList.addState(vehicle);
        return this;
    }

    public VehicleState getVehicle(String key) {
        return (VehicleState) this.vehicleList.getState(key);
    }

    public VehicleList updateVehicle(VehicleState vehicle) {
        this.vehicleList.updateState(vehicle);
        return this;
    }

}
