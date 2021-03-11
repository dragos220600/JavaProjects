public class Apartament{
    private String id;
    private int pret;
    private int nrCamere;
    
    public Apartament(String id, int pret, int nrCamere){
        this.id = id;
        this.pret = pret;
        this.nrCamere = nrCamere;
    }
    
    public void setID(String id){
        this.id = id;
    }
    
    public void setPret(int pret){
        this.pret = pret;
    }
    
    public void setNrCamere(int nrCamere){
        this.nrCamere = nrCamere;
    }
    
    public String getID(){
        return this.id;        
    }
    
    public int getPret(){
        return this.pret;        
    }
    
    public int getNrCamere(){
        return this.nrCamere    ;        
    }
}