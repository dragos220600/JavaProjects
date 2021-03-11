import java.util.*;

public class Bloc{
    List <Apartament> apartamente = new ArrayList<>();
    private static Bloc SINGLETON;
    
    private Bloc(){
    }
    
    public static final Bloc getInstance(){
        if(SINGLETON == null){
            SINGLETON = new Bloc();
        }
        return SINGLETON;
    }
    
    public void adaugare(Apartament a) throws PreaMulteApartamenteException{
        if(this.apartamente.size() < 120)
            this.apartamente.add(a);
        else
            throw new PreaMulteApartamenteException("Prea multe apartamente!");
    }
    
    public void afisareDuplex(){
        this.apartamente.stream()
                        .filter(a -> a instanceof Duplex)
                        .forEach(a -> System.out.println(a));
    }
    
    public void afisareGarsoniere(){
        this.apartamente.stream()
                        .filter(a -> a instanceof Garsoniera)
                        .forEach(a -> System.out.println(a));
    }
    
    public void sortarePret(){
        Apartament[] array = new Apartament[this.apartamente.size()];
        int i, nr = 0,j;
        
        for(Apartament a : this.apartamente){
            array[nr] = a;
            nr++;
        }
        Apartament aux; 
        
        for(i = 0;i < nr;i++){
            for(j = i + 1;j < nr;j++)
                if(array[i].getPret() > array[j].getPret()){
                    aux = array[i];
                    array[i] = array[j];
                    array[j] = aux;
                }
        }
        
        for(i = 0;i < nr;i++)
            System.out.println(array[i]);
    }
    
    public void afisarePret(String id) throws ApartamentInexistentException{
        int pretAp = 0;
        for(Apartament a : this.apartamente){
            if(a.getID().equals(id) == true)
                pretAp = a.getPret();
        }
        
        if(pretAp == 0)
            throw new ApartamentInexistentException("Nu exista acest apartament!");
            
        System.out.println(pretAp);
    }
}