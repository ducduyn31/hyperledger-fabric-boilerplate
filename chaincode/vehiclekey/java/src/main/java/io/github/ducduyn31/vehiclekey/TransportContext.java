package io.github.ducduyn31.vehiclekey;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;

public class TransportContext extends Context {

    /**
     * Creates new client identity and sets it as a property of the stub.
     *
     * @param stub Instance of the {@link ChaincodeStub} to use
     */
    public TransportContext(ChaincodeStub stub) {
        super(stub);
        this.vehicleList = new VehicleList(this);
    }
    
    public VehicleList vehicleList;
}
