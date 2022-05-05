/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class RC3 extends RC {

    public static void main(String[] args) throws Exception {
        RC3 rc = new RC3();
        Socket sock1 = new Socket("127.0.0.1", 5013);
        Socket sock2 = new Socket("127.0.0.1", 5023);
        Socket sock4 = new Socket("127.0.0.1", 5034);
        System.out.println("Running RC3");
        ObjectOutputStream doutsock2 = new ObjectOutputStream(sock2.getOutputStream());
        ObjectInputStream dinsock2 = new ObjectInputStream(sock2.getInputStream());
        ObjectOutputStream doutsock1 = new ObjectOutputStream(sock1.getOutputStream());
        ObjectInputStream dinsock1 = new ObjectInputStream(sock1.getInputStream());
        ObjectOutputStream doutsock4 = new ObjectOutputStream(sock4.getOutputStream());
        ObjectInputStream dinsock4 = new ObjectInputStream(sock4.getInputStream());

        // System.out.println("ConfigReader");
        ConfigReader.reader(rc, "./config/rc3.txt");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    rc.mutex.acquire();
                    System.out.println("Mutex Acquired Trying To Send Multiple RCU's");
                    rc.table.forEach((tab) -> {
                        try {
                            doutsock1.writeObject(new RCU(rc.myrc.getRcid(), tab.asn, 100, tab.linkcapacity, tab.linkcost,tab.path));
                            doutsock2.writeObject(new RCU(rc.myrc.getRcid(), tab.asn, 200, tab.linkcapacity, tab.linkcost,tab.path));
                            doutsock4.writeObject(new RCU(rc.myrc.getRcid(), tab.asn, 400, tab.linkcapacity, tab.linkcost,tab.path));
                        } catch (Exception e) {

                        }
                    });
                } catch (Exception e) {
                    System.out.println("ERROR");
                } finally {
                    rc.mutex.release();
                    //rc.getRoutingTable();
                }
            }
        }, 6*1000, 180*1000);
        RCU rec;
        while (true) {
            //System.out.println("Waiting for Write from R1: ");
            rec = (RCU) dinsock1.readObject();
            try {
                rc.mutex.acquire();
                rc.readData(rec);
                //System.out.println("Waiting for Write FROM R2: ");
                rc.readData((RCU) dinsock2.readObject());
                //System.out.println("Waiting for Write FROM R4: ");
                rc.readData((RCU) dinsock4.readObject());
            } catch (Exception e) {

            } finally {
                rc.getRoutingTable();
                rc.mutex.release();
            }
        }
    }

}
