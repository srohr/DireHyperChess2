// a small class for running tests

import java.util.*;

public class Hello {
    public static void main(String[] args) {
        System.out.println("hello\n");
        // Tile cell = new Tile();
        Tile cell1 = new Tile(PieceType.BISHOP, 1, 1, 1);
        Tile cell2 = new Tile(PieceType.QUEEN, 2, 4, 5);
        System.out.println();
        
        cell1.printInfo();
        cell2.printInfo();
        cell1.movePieceTo(cell2);
        cell1.printInfo();
        cell2.printInfo();
        System.out.println();
        
        // test a failed pawn capture
        cell1 = new Tile(PieceType.PAWN, 1, 5, 5);
        cell1.printInfo();
        cell1.movePieceTo(new Tile(PieceType.QUEEN, 2, 4, 5));
        cell1.printInfo();
        System.out.println();
        
        // test a working capture
        cell1 = new Tile(PieceType.PAWN, 1, 5, 5);
        cell1.printInfo();
        cell1.movePieceTo(new Tile(PieceType.QUEEN, 2, 4, 4));
        cell1.printInfo();
        System.out.println();
    }
    
    
    
    
    
    // just shovel these functions into where you want them
    Vector getMoves(Tile t) {
        Vector ret = new Vector();
        
        return ret;
    }
    
    boolean moveTo(Tile from, Tile to) {
        return from.movePieceTo(to);
    }
    
    int checkCheck() {
        return 0;
    }
    
    int checkCheckMate() {
        return 0;
    }
    
}
