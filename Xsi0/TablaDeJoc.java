public class TablaDeJoc{
    
    char[][] tabla = new char [3][3];
    
    public void initializeazaTableDeJoc(){
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++)
                tabla[i][j] = '_';
        }
    }
    
    public void afiseazaTablaDeJoc(){
        for(int i = 0;i < 3;i++){
            for(int j = 0;j < 3;j++)
                System.out.print(tabla[i][j]);
            System.out.println();
        }
    }
    
    public void mutare(char jucator, int row, int col){
        tabla[row][col] = jucator;
    }
}