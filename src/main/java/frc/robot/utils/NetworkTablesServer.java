package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTableInstance;
import java.net.InetAddress;
import java.util.Arrays;

public class NetworkTablesServer {
    public NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();

    public void setUpNetworkTables() {
        ntInstance.startServer();
        System.out.println("networktables is up");

        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("IP ADDR" + ip.getHostAddress() + ":5810");
        } catch (Exception e) {
            e.printStackTrace();
        }


        ntInstance.getTable("controls").getEntry("moveForward").setBoolean(false);
        ntInstance.getTable("controls").getEntry("moveBackward").setBoolean(false);
        ntInstance.getTable("controls").getEntry("moveLeft").setBoolean(false);
        ntInstance.getTable("controls").getEntry("moveRight").setBoolean(false);

        System.out.println(ntInstance.getTable("controls").getValue("moveRight"));

        // simple loop
        while (true) {
            try {
                Thread.sleep(1000);
//                System.out.println(Arrays.toString(ntInstance.getConnections()));
            } catch (InterruptedException ex) {
                System.out.println("Interrupted!");
                return;
            }
        }
    }
}
