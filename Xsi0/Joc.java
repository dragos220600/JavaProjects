import java.util.*;

public class Joc{
    
    char jucatorCurent;
    TablaDeJoc t = new TablaDeJoc();
    StareJocEnum stareJoc;
    
    public void initializeazaJoc(){
        this.jucatorCurent = 'x';
        this.t.initializeazaTableDeJoc();
        this.stareJoc = StareJocEnum.PLAYING;
        this.t.afiseazaTablaDeJoc();
    }
    
    public void mutare(Scanner sc){
        int row, col;
        do{
           System.out.println(this.jucatorCurent); 
           System.out.println("Introduceti randul");
           row = sc.nextInt();
           System.out.println("Introduceti coloana");
           col = sc.nextInt();
        } while(!isMutareValida(row, col));
        
        this.t.mutare(this.jucatorCurent,row,col);
        this.t.afiseazaTablaDeJoc();
        if(this.aCastigat(row,col) == true){
            this.schimbaStareJoc(StareJocEnum.WON);
            System.out.println(this.jucatorCurent);
            return;
        }
        else if(this.isEgalitate() == true){
            this.schimbaStareJoc(StareJocEnum.DRAW);
            System.out.println("Jocul s-a incheiat cu egalitate");
            return;
        }
    }
    
    private boolean aCastigat(int row,int col){
        int contor = 0;
        int i = 0, j = 0;
        row = this.t.tabla.length;
        col = this.t.tabla[row - 1].length;
        while(i < row && j < col){
            if(this.t.tabla[i][j] == this.jucatorCurent)
                contor++;
            i++;
            j++;
        }
        
        if(contor == 3){
            return true;
        }
            
        contor = 0;
        i = 0;
        j = col - 1;
        while(i < row && j >= 0){
            if(this.t.tabla[i][j] == this.jucatorCurent)
                contor++;
            
            i++;
            j--;
        }
        
        if(contor == 3){
            return true;
        }
        contor = 0;
        
        i = 0;
        while(i < row){
            contor = 0;
            for(j = 0;j < col;j++){
                if(this.t.tabla[i][j] == this.jucatorCurent)
                    contor++;
            }
            
            if(contor == 3){
                return true;
            }
            i++;
        }
        
        j = 0;
        while(j < col){
            contor = 0;
            for(i = 0;i < row;i++){
                if(this.t.tabla[i][j] == this.jucatorCurent)
                    contor++;
            }
            
            if(contor == 3){
                return true;
            }
            j++;
        }
        return false;
    }
    
    private boolean isEgalitate(){
        int contor = 0;
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++)
                if(this.t.tabla[i][j] == 'x' || this.t.tabla[i][j] == '0')
                    contor++;
        }
        
        if(contor == 9){
            System.out.println("Tabla este plina");
            return true;
        }
        return false;
    }
    
    public void schimbaJucator(){
        if(this.jucatorCurent == 'x')
            this.jucatorCurent = '0';
        else
            this.jucatorCurent = 'x';
    }
    
    private void schimbaStareJoc(StareJocEnum stareNoua){
        this.stareJoc = stareNoua;
    }
    
    public boolean isMutareValida(int row, int col){
        if(row > 2 || col > 2){
            System.out.println("Mutare invalida");
            return false;
        }
        return true;
    }
}