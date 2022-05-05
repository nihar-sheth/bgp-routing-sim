package project;

class ASNinfo {

    private int asn, linkcapacity, linkcost;
    
    public ASNinfo(){super();};//default constructor
    
    public ASNinfo(int a, int cap, int cost){
        this.asn = a;
        this.linkcapacity = cap;
        this.linkcost = cost;
    }//constructor

    public void setValues(int a, int cap, int cost){
        this.asn = a;
        this.linkcapacity = cap;
        this.linkcost = cost;
    }
    
        @Override
    public String toString(){
        return "asn: " + asn + "\nlinkcapacity: " 
                + linkcapacity + "\nlinkcost: " + linkcost + "\n";
    }
    
    /**
     * @return the asn
     */
    public int getAsn() {
        return asn;
    }

    /**
     * @param asn the asn to set
     */
    public void setAsn(int asn) {
        this.asn = asn;
    }

    /**
     * @return the linkcapacity
     */
    public int getLinkcapacity() {
        return linkcapacity;
    }

    /**
     * @param linkcapacity the linkcapacity to set
     */
    public void setLinkcapacity(int linkcapacity) {
        this.linkcapacity = linkcapacity;
    }

    /**
     * @return the linkcost
     */
    public int getLinkcost() {
        return linkcost;
    }

    /**
     * @param linkcost the linkcost to set
     */
    public void setLinkcost(int linkcost) {
        this.linkcost = linkcost;
    }
    
    
    
}
