package project;

class RCInfo {

    private int rcid, asn;
    private String ipa;

    public RCInfo() {
        rcid = 0; asn = 0; ipa = "0.0.0.0";
    } //default constructor
    public RCInfo(int rcid, int asn, String ipa){
        this.rcid = rcid;
        this.asn = asn;
        this.ipa = ipa;
    }

    
    
    public void setValues(int rcid, int asn, String ipa){
        this.rcid = rcid;
        this.asn = asn;
        this.ipa = ipa;
    }
    
    @Override
    public String toString(){
        return "rcid: " + rcid + "\nasn: " + asn + "\nipa: " + ipa + "\n";
    }
    /**
     * @return the rcid
     */
    public int getRcid() {
        return rcid;
    }

    /**
     * @param rcid the rcid to set
     */
    public void setRcid(int rcid) {
        this.rcid = rcid;
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
     * @return the ipa
     */
    public String getIpa() {
        return ipa;
    }

    /**
     * @param ipa the ipa to set
     */
    public void setIpa(String ipa) {
        this.ipa = ipa;
    }
    
    
}
