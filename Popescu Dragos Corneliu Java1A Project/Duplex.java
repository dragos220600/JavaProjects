public class Duplex extends Apartament{
    
    boolean terasa;
    public Duplex(String id, int pret, int nrCamere,boolean terasa){
        super(id,pret,4);
        this.terasa = terasa;
    }
    
    public void setTerasa(boolean terasa){
        this.terasa = terasa;
    }
    
    public boolean isTerasa(){
        return this.terasa;
    }
    
    @Override
    public String toString(){
        return "Duplexul are id-ul " + this.getID() + " pretul " + 
        this.getPret() + " nr de camere " + this.getNrCamere() + 
        " si terasa: " + this.isTerasa();
    }
}