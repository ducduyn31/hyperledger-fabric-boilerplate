package io.github.ducduyn31.vehiclekey;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

@Contract(
        name = "vehiclekey"
)
@Default()
public class VehicleKeyContract implements ContractInterface {

    @Transaction()
    public void initLedger(final Context ctx) {

    }

    @Transaction()
    public void queryAll(final TransportContext ctx) {
        
    }

    @Transaction()
    public void registerVehicle(final TransportContext ctx, final String vehicleId, final String registeringSM) {
        VehicleState vehicleState = VehicleState.createInstance(null, registeringSM, vehicleId);

        ctx.vehicleList.addVehicle(vehicleState);
    }

    @Transaction()
    public void exchangeKey(final TransportContext ctx, final String SMdest, final String SMthis, final String vehicleId, final String payload, final String signature) throws NoSuchPaddingException, NoSuchAlgorithmException {
        VehicleState vs = ctx.vehicleList.getVehicle(vehicleId);

        assert vs != null;
        assert vs.getCurrentSM().toString().equals(SMthis);

        VehicleState newVs = VehicleState.createInstance(SMthis, SMdest, vehicleId);

        ctx.vehicleList.updateVehicle(newVs);
    }

}
