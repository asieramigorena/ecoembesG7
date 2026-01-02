package com.ecoembes.external;

public class GatewayFactory {
    public static Gateway getGateway(String planta){
        if (planta.equals("PlasSB")){
            return PlasSBServiceProxy.getInstance();
        } else if (planta.equals("ContSocket")){
            return SocketEcoembes.getInstance();
        } else {
            System.err.println("La planta introducida no existe");
        }
        return null;
    }
}
