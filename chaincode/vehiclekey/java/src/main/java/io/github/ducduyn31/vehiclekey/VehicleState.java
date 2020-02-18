package io.github.ducduyn31.vehiclekey;

import io.github.ducduyn31.vehiclekey.ledgerapi.State;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@DataType()
public class VehicleState extends State {

    @Property()
    private String currentSM;
    @Property()
    private String lastSM;
    @Property()
    private String vehicleId;

    public static VehicleState deserialize(byte[] data) {
        JSONObject json = new JSONObject(new String(data, UTF_8));

        String lastSM = json.getString("lastSM");
        String currentSM = json.getString("currentSM");
        String vehicleId = json.getString("vehicleId");
        return createInstance(lastSM, currentSM, vehicleId);
    }

    public static byte[] serialize(VehicleState vs) {
        return State.serialize(vs);
    }

    public static VehicleState createInstance(String lastSM, String currentSM, String vehicleId) {

        assert currentSM != null;
        assert vehicleId != null;

        PublicKey lsm = strToPK(lastSM);
        PublicKey csm = strToPK(currentSM);
        PublicKey v = strToPK(vehicleId);

        return new VehicleState().setLastSM(lsm).setSM(csm).setVehicleId(v);
    }

    public static PublicKey strToPK(String stringPK) {
        try {
            byte[] bs = Base64.getDecoder().decode(stringPK);
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(bs);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(x509);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String pkToStr(PublicKey publicKey) {
        return publicKey != null ? publicKey.toString() : "";
    }

    public PublicKey getCurrentSM() {
        return strToPK(currentSM);
    }

    public VehicleState setSM(PublicKey SMpublickKey) {
        this.lastSM = currentSM;
        this.currentSM = pkToStr(SMpublickKey);
        return this;
    }

    public PublicKey getVehicleId() {
        return strToPK(vehicleId);
    }

    public VehicleState setVehicleId(PublicKey vehicleId) {
        this.vehicleId = pkToStr(vehicleId);
        return this;
    }

    public PublicKey getLastSM() {
        return strToPK(lastSM);
    }

    public VehicleState setLastSM(PublicKey lastSM) {
        this.lastSM = pkToStr(lastSM);
        return this;
    }
}
