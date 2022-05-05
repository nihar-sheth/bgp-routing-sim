/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class RC {

    ArrayList<ObjectInputStream> inStreams = new ArrayList();
    ArrayList<ObjectOutputStream> outStreams = new ArrayList();
    ArrayList<ASNinfo> asnlist = new ArrayList();
    ArrayList<RCInfo> rclist = new ArrayList();
    ArrayList<RoutingTable> table = new ArrayList();
    protected Semaphore mutex = new Semaphore(1);
    RCU receive;
    RCU send;
    RCInfo myrc;

    public void sendData() {
        outStreams.forEach((s) -> {

        });
    }

    public void getRoutingTable() {
        System.out.println("\nROUTING TABLE");
        for (RoutingTable e : table) {
            if (e != null) {
                System.out.println(e);
            }
        }
    }

    public void readData(RCU rec) {
        ASNinfo srcasn = null;
        int asnsrc = 0, srccost = 0,srccap = 0;
        double compcost=0;
        //System.out.println("ReadData(): "+rec);
        try {
            //mutex.acquire();
            //System.out.println("------Mutex Acquired,GETTING ASN INFO-------");
            for (int i = 0; i < rclist.size(); i++) {
                if (rclist.get(i).getRcid() == rec.rcid) {
                    //System.out.println("FOUND ASN for SENDER RC");
                    asnsrc = rclist.get(i).getAsn();
                    break;
                }
            }
            //System.out.println("GETTING ASN OBJECT & COST");
            for (int i = 0; i < asnlist.size(); i++) {
                if (asnlist.get(i).getAsn() == asnsrc) {
                    //System.out.println("FOUND OBJECT");
                    srcasn = asnlist.get(i);
                    srccost = srcasn.getLinkcost();
                    srccap = srcasn.getLinkcapacity();
                    compcost = (double)srccost/srccap;
                }
            }

            for (int j = 0; j < table.size(); j++) {
                RoutingTable currentRoute = table.get(j);
                if(myrc.getAsn() == rec.asnsrc){
                    //System.out.println("No need to add");
                    break;
                }
                if (currentRoute.asn == rec.asnsrc) {
                    //an existing route has been found for this RCU
                    if ((rec.compcost+ compcost) < currentRoute.compcost) {
                    //if (rec.compcost+ srccost < currentRoute.linkcost) {    
                        //System.out.println("Composite Cost:"+(rec.linkcost/rec.linkcapacity)+ (srccost)/srccap);
                        //replace the route
                        //System.out.println("REPLACING ROUTE");
                        rec.path.add(0,myrc.getAsn());
                        currentRoute.rcsrc = rec.rcid;
                        currentRoute.linkcapacity = rec.linkcapacity;
                        currentRoute.linkcost = rec.linkcost;
                        currentRoute.src = srcasn.getAsn();
                        currentRoute.path = rec.path;
                        currentRoute.compcost = compcost+rec.compcost;
                        break; //no need to go through entries
                    }else{
                        //System.out.println("COST IS HIGHER, DISCARDING");
                        break;
                    }
                }
                if ((j == table.size() - 1) && currentRoute.asn != rec.asnsrc) {
                    //add a new entry
                    //System.out.println("ADDING NEW ROUTE");
                    rec.path.add(0,myrc.getAsn());
                    table.add(new RoutingTable(rec.rcid, rec.asnsrc, srcasn.getAsn(), rec.linkcapacity, rec.linkcost,rec.path,rec.compcost+(double)srccost/srccap));
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION OCCURED");
        } finally {
            //System.out.println("COMPLETED FUNCTION \n\n");

            
            //mutex.release();
        }
       
        //find the src asn info
    }
}
