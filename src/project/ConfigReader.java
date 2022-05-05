package project;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class ConfigReader {

    private static final int MAX_ADJ = 10;

    public static void reader(RC c, String path){
        c.myrc = new RCInfo();
        c.rclist = new ArrayList();
        c.asnlist = new ArrayList();
        String input;
        int num, tempI, num1, num2,num3;
        String[] values;

        
        try {
            File fl = new File(path);
            Scanner sc = new Scanner(fl);
            //read myRC info
            input = sc.nextLine();
            values = input.split(" ");
            c.myrc.setValues(Integer.parseInt(values[0]),
                    Integer.parseInt(values[1]), values[2]);

            //get number of RC connected
            num = Integer.parseInt(sc.nextLine()); 
//            System.out.println(num);
            //read the RCs
            for (tempI = 0; num > 0; num--) {
                input = sc.nextLine();
//                System.out.println(input);
                values = input.split(" ");
                num1 = Integer.parseInt(values[0]);
                num2 = Integer.parseInt(values[1]);
                c.rclist.add(new RCInfo(num1, num2, values[2]));
            }//for
            
            //get number of ASN connected
            num = Integer.parseInt(sc.nextLine()); 
            //read the given number of ASNs
            for (tempI = 0; num > 0; num--) {
                input = sc.nextLine();
                values = input.split(" ");
                num1 = Integer.parseInt(values[0]);
                num2 = Integer.parseInt(values[1]);
                num3 = Integer.parseInt(values[2]);
                c.asnlist.add(new ASNinfo(num1, num2, num3));
                c.table.add(new RoutingTable(c.myrc.getRcid(),num1,c.myrc.getAsn(),num2,num3));
            }//for
            
            //PRINTING ALL VALUES THIS IS JUST TEST REMOVE AS REQUIRED
            System.out.println("MYRC");
            System.out.println(c.myrc);
            System.out.println("\nRCLIST");
            for(RCInfo e: c.rclist){
                if(e != null)
                System.out.println(e);
            }
            System.out.println("\nASNLIST");
            for(ASNinfo e: c.asnlist){
                if(e != null)
                System.out.println(e);
            }
            System.out.println("\nROUTING TABLE");
            for(RoutingTable e: c.table){
                if(e != null)
                System.out.println(e);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }//try catch
    }//main
}
