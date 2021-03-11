import java.util.*;

public class DemoJoc
{
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        String comanda;
        do{
            System.out.println("Introduceti comanda start");
            comanda = scan.nextLine();
        } while(!comanda.equals("start"));

        Joc joc = new Joc();
        joc.initializeazaJoc();
        while(joc.stareJoc == StareJocEnum.PLAYING) {
            joc.mutare(scan);
            joc.schimbaJucator();
        }
    }
    
}