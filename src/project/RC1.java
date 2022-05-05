/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.util.Timer;
import java.util.TimerTask;

public class RC1 extends RC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        RC1 rc = new RC1();
        ServerSocket ss = new ServerSocket(5012);
        ServerSocket ss2 = new ServerSocket(5013);
        Socket sock2 = ss.accept();
        Socket sock3 = ss2.accept();      
        System.out.println("Running RC1");
        ObjectOutputStream doutsock2 = new ObjectOutputStream(sock2.getOutputStream());
        ObjectInputStream dinsock2 = new ObjectInputStream(sock2.getInputStream());
        ObjectOutputStream doutsock3 = new ObjectOutputStream(sock3.getOutputStream());
        ObjectInputStream dinsock3 = new ObjectInputStream(sock3.getInputStream());
        // System.out.println("ConfigReader");

        ConfigReader.reader(rc, "./config/rc1.txt");
        rc.outStreams.add(doutsock2);
        rc.inStreams.add(dinsock2);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                try {
                    rc.mutex.acquire();
                    System.out.println("Mutex Acquired Trying To Send Multiple RCU's");
                    rc.table.forEach((tab) -> {
                        try {
                            doutsock2.writeObject(new RCU(rc.myrc.getRcid(), tab.asn, 200, tab.linkcapacity, tab.linkcost,tab.path));
                            doutsock3.writeObject(new RCU(rc.myrc.getRcid(),tab.asn,300,tab.linkcapacity,tab.linkcost,tab.path));
                        } catch (Exception e) {

                        }
                    });
                } catch (Exception e) {
                    System.out.println("ERROR");
                } finally {
                    rc.mutex.release();
                    //rc.getRoutingTable();
                }
                //doutsock3.write();
            }
        }, 5*1000, 180*1000);

        RCU rec;
        while (true) {
            //System.out.println("Waiting for Write from R2: ");
            rec = (RCU) dinsock2.readObject();
            try {
                rc.mutex.acquire();
                rc.readData(rec);
                //System.out.println("Waiting for Write FROM R3: ");
                rc.readData((RCU) dinsock3.readObject());
            } catch (Exception e) {

            } finally {
                rc.getRoutingTable();
                rc.mutex.release();
            }
        }
    }
}
