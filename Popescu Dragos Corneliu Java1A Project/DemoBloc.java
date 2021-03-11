import java.util.*;

public class DemoBloc{
    
    public static void main(String[] args){
        Bloc b = Bloc.getInstance();
        Scanner sc = new Scanner(System.in);
        boolean oprireThread = false;
        
        Thread t = new Thread(){
            public void run(){
                while(true){
                    try{
                        System.out.println("Nr de apartamente este " + b.apartamente.size());
                        Thread.sleep(120000);
                    } catch(Exception e){
                    }
                }
            }
        };
        
        t.start();
        while(true){
            String comanda = sc.nextLine();
            String[] cuvinte = comanda.split("\\s+");
            switch(cuvinte[0]){
                case "EXIT":
                    t.stop();
                    return;
                 
                case "AD_GARSONIERA":
                    Integer pret = Integer.parseInt(cuvinte[2]);
                    Double sup = Double.parseDouble(cuvinte[3]);
                    Apartament a1 = new Garsoniera(cuvinte[1],pret,1,sup);
                    try{
                        b.adaugare(a1);
                    } catch(PreaMulteApartamenteException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                    
                case "AD_DUPLEX":
                    Integer pret1 = Integer.parseInt(cuvinte[2]);
                    Boolean terasa = Boolean.parseBoolean(cuvinte[3]);
                    Apartament a2 = new Duplex(cuvinte[1],pret1,4,terasa);
                    try{
                        b.adaugare(a2);
                    } catch(PreaMulteApartamenteException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                    
                case "AF_AP":
                     if(cuvinte[1].equals("garsoniera") == true)
                        b.afisareGarsoniere();
                     else if(cuvinte[1].equals("duplex") == true)
                        b.afisareDuplex();
                     break;
                     
                case "AF_AP_SORTATE":
                     b.sortarePret();
                     break;
                     
                case "AF_PRET":
                     try{
                         b.afisarePret(cuvinte[1]);
                     } catch(ApartamentInexistentException e){
                         System.out.println(e.getMessage());
                     }
            }
        }
    }   
}