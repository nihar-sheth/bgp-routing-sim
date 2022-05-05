/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.Serializable;
import java.util.ArrayList;

public class RoutingTable implements Serializable {

    int rcsrc;
    int asn;
    int src;
    int linkcapacity;
    int linkcost;
    double compcost;
    ArrayList<Integer> path = new ArrayList();

    public RoutingTable(int rc, int assn, int srcc, int cap, int cost) {
        rcsrc = rc;
        asn = assn;
        src = srcc;
        linkcapacity = cap;
        linkcost = cost;
        compcost = (double)cost/cap;
        path.add(srcc);
        if(assn != srcc){
            path.add(assn);
        }
    }

    public RoutingTable(int rc, int assn, int srcc, int cap, int cost, ArrayList<Integer> pth,double comp) {
        rcsrc = rc;
        asn = assn;
        src = srcc;
        linkcapacity = cap;
        linkcost = cost;
        path = pth;
        compcost = comp;
    }

    public String printPath() {
        String pathstr = "[";
        for (int i = 0; i < path.size(); i++) {
            pathstr = pathstr.concat("AS" + path.get(i) + " ");
        }
        pathstr = pathstr.concat("]");
        return pathstr;
    }

    @Override
    public String toString() {
        return ("rcid: " + rcsrc + ",asn: " + asn + ",NH: " + src + ",cap:" + linkcapacity + ",cost:" + linkcost+",composite cost:"+compcost+", PATH:"+printPath());
    }

}
