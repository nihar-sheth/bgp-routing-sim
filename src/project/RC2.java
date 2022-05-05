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

public class RC2 extends RC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //Socket sock2 = new Socket("127.0.0.1", 5012);
        RC2 rc = new RC2();
        RCU x;
        ServerSocket ss = new ServerSocket(5023);
        Socket sock1 = new Socket("127.0.0.1", 5012);
        Socket sock4 = new Socket("127.0.0.1", 5024);
        Socket sock3 = ss.accept();
        //Socket sock3 = new Socket("127.0.0.1", 5013);

        System.out.println("Running RC2");
        ObjectOutputStream doutsock1 = new ObjectOutputStream(sock1.getOutputStream());
        ObjectInputStream dinsock1 = new ObjectInputStream(sock1.getInputStream());
        System.out.println("Input Stream");
        ObjectInputStream dinsock3 = new ObjectInputStream(sock3.getInputStream());
        ObjectOutputStream doutsock3 = new ObjectOutputStream(sock3.getOutputStream());
        ObjectOutputStream doutsock4 = new ObjectOutputStream(sock4.getOutputStream());
        ObjectInputStream dinsock4 = new ObjectInputStream(sock4.getInputStream());
        // System.out.println("ConfigReader");
        ConfigReader.reader(rc, "./config/rc2.txt");
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
                            doutsock3.writeObject(new RCU(rc.myrc.getRcid(), tab.asn, 300, tab.linkcapacity, tab.linkcost,tab.path));
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
        }, 5*1000, 180*1000);
        RCU rec;
        while (true) {
            //System.out.println("Waiting for Write from R1: ");
            rec = (RCU) dinsock1.readObject();
            try {
                rc.mutex.acquire();
                rc.readData(rec);
                //System.out.println("Waiting for Write FROM R3: ");
                rc.readData((RCU) dinsock3.readObject());
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
