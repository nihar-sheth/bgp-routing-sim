/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.Serializable;
import java.util.ArrayList;

public class RCU implements Serializable{
    int rcid;
    int asnsrc;
    int asndest;
    int linkcapacity;
    int linkcost;
    double compcost;
    ArrayList<Integer> path = new ArrayList();

    
    public RCU(int rc, int asns,int asnd,int cap,int cost,ArrayList<Integer> pth){
        rcid = rc;
        asnsrc = asns;
        asndest = asnd;
        linkcapacity = cap;
        linkcost = cost;
        path = pth;
        compcost = (double)linkcost/linkcapacity;
    }
    
    public String printPath(){
        String pathstr = "[";
        for(int i = path.size()-1;i>=0;i--){
            pathstr = pathstr.concat("AS"+path.get(i)+" ");
        }
        pathstr = pathstr.concat("]");
        return pathstr;
    }
    
    @Override
    public String toString(){
        return ("RCID: "+rcid+" ASNSRC: "+asnsrc+" DEST: "+asndest+"Capacity: "+linkcapacity+"Cost:"+linkcost+"Path: "+printPath());
    }
    
}
