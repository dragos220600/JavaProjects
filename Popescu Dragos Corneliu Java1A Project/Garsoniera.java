public class Garsoniera extends Apartament{
    
    double suprafata;
    public Garsoniera(String id, int pret, int nrCamere,double suprafata){
        super(id,pret,1);
        this.suprafata = suprafata;
    }
    
    public void setSuprafata(double suprafata){
        this.suprafata = suprafata;
    }
    
    public double getSuprafata(){
        return this.suprafata;
    }
    
    @Override
    public String toString(){
        return "Garsoniera are id-ul " + this.getID() + " pretul " + 
        this.getPret() + " nr de camere " + this.getNrCamere() + 
        " si suprafata " + this.getSuprafata();
    }
}