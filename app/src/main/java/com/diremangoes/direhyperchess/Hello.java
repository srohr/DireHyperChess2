// a small class for running tests

import java.util.*;

public class Hello {
    public static void main(String[] args) {
        System.out.println("hello\n");
        // Tile cell = new Tile();
        Tile cell1 = new Tile(PieceType.BISHOP, 1, 1, 1);
        Tile cell2 = new Tile(PieceType.QUEEN, 2, 4, 5);
        System.out.println();
        
        /*
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
        System.out.println();
        */
        
        // test rook
        // moveTo(new Tile(PieceType.ROOK, 1, 1,1), new Tile(PieceType.QUEEN, 2, 1,6));
        // moveTo(new Tile(PieceType.ROOK, 1, 1,1), new Tile(PieceType.QUEEN, 2, 4,1));
        // moveTo(new Tile(PieceType.ROOK, 1, 4,4), new Tile(PieceType.QUEEN, 2, 5,5));
        // moveTo(new Tile(PieceType.ROOK, 1, 4,4), new Tile(PieceType.QUEEN, 2, 5,3));
        // moveTo(new Tile(PieceType.ROOK, 1, 4,4), new Tile(PieceType.QUEEN, 2, 3,5));
        // moveTo(new Tile(PieceType.ROOK, 1, 4,4), new Tile(PieceType.QUEEN, 2, 2,4));
        // moveTo(new Tile(PieceType.ROOK, 2, 4,4), new Tile(PieceType.QUEEN, 2, 5,4));
        // System.out.println();
        // System.out.println();
        
        
        // test move generation
        // ArrayList<Tile> moves = getMoves(new Tile(PieceType.KNIGHT, 1, 4,4));
        ArrayList<Tile> moves = getMoves(new Tile(PieceType.QUEEN, 1, 3,0));
        
        for (Tile t : moves)
            t.printInfo();
            
        
    }
    
    
    
    
    
    // just shovel these functions into where you want them
    
    // generates all possible movement locations, does not consider occlusion
    static ArrayList<Tile> getMoves(Tile t) {
        ArrayList<Tile> ret      = new ArrayList();
        PieceType ptype = t.getPieceType();
        int colour      = t.getPieceOwner();
        int xpos        = t.getxpos();
        int ypos        = t.getypos();
        
        switch(ptype) {
            case EMPTY: {
                break;
            }
            case PAWN: { // DONE
                // ret.add(new Tile(ptype, colour, xpos-1, ypos-1));
                // ret.add(new Tile(ptype, colour, xpos-1, ypos  ));
                // ret.add(new Tile(ptype, colour, xpos-1, ypos+1));
                
                int[] arr = {-1, 0, 1};
                for (int i : arr) {
                    if (inBounds(xpos+i, ypos-1))
                        ret.add(new Tile(ptype, colour, xpos+i, ypos-1));
                }
                break;
            }
            case ROOK: { // DONE
                // x-translations
                for (int x=0; x<8; ++x) {
                    if (x == xpos) continue;
                    ret.add(new Tile(ptype, colour, x, ypos));
                }
                for (int y=0; y<8; ++y) {
                    if (y == ypos) continue;
                    ret.add(new Tile(ptype, colour, xpos, y));
                }
                break;
            }
            case BISHOP: { // DONE
                // for (int i=0; i<8; ++i) {
                //     ret.add(new Tile(ptype, colour, xpos+i, ypos+i));
                //     ret.add(new Tile(ptype, colour, xpos+i, ypos-i));
                //     ret.add(new Tile(ptype, colour, xpos-i, ypos+i));
                //     ret.add(new Tile(ptype, colour, xpos-i, ypos-i));
                // }
                
                // look in each diagonal until out of bounds
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos+i, ypos+i))
                        ret.add(new Tile(ptype, colour, xpos+i, ypos+i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos+i, ypos-i))
                        ret.add(new Tile(ptype, colour, xpos+i, ypos-i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos-i, ypos+i))
                        ret.add(new Tile(ptype, colour, xpos-i, ypos+i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos-i, ypos-i))
                        ret.add(new Tile(ptype, colour, xpos-i, ypos-i));
                    else break;
                }
                
                
                break;
            }
            case KNIGHT: { // DONE
                // ret.add(new Tile(ptype, colour, xpos-1, ypos-2));
                // ret.add(new Tile(ptype, colour, xpos-2, ypos-1));
                // ret.add(new Tile(ptype, colour, xpos+1, ypos-2));
                // ret.add(new Tile(ptype, colour, xpos+2, ypos-1));
                // ret.add(new Tile(ptype, colour, xpos-1, ypos+2));
                // ret.add(new Tile(ptype, colour, xpos-2, ypos+1));
                // ret.add(new Tile(ptype, colour, xpos+1, ypos+2));
                // ret.add(new Tile(ptype, colour, xpos+2, ypos+1));
                
                int[] arr = {-2, -1, 1, 2};
                for (int i : arr) {
                    for (int j : arr) {
                        if (i == j || i == -j) continue;
                        if (inBounds(xpos+i, ypos+j))
                            ret.add(new Tile(ptype, colour, xpos+i, ypos+j));
                    }
                }
                
                break;
            }
            case QUEEN: { // DONE
                // x-translations
                for (int x=0; x<8; ++x) {
                    if (x == xpos) continue;
                    ret.add(new Tile(ptype, colour, x, ypos));
                }
                for (int y=0; y<8; ++y) {
                    if (y == ypos) continue;
                    ret.add(new Tile(ptype, colour, xpos, y));
                }
                    
                // look in each diagonal until out of bounds
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos+i, ypos+i))
                        ret.add(new Tile(ptype, colour, xpos+i, ypos+i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos+i, ypos-i))
                        ret.add(new Tile(ptype, colour, xpos+i, ypos-i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos-i, ypos+i))
                        ret.add(new Tile(ptype, colour, xpos-i, ypos+i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos-i, ypos-i))
                        ret.add(new Tile(ptype, colour, xpos-i, ypos-i));
                    else break;
                }
                
                break;
            }
            case KING: { // DONE
                /*
                ret.add(new Tile(ptype, colour, xpos-1, ypos-1));
                ret.add(new Tile(ptype, colour, xpos-1, ypos  ));
                ret.add(new Tile(ptype, colour, xpos-1, ypos+1));
                ret.add(new Tile(ptype, colour, xpos,   ypos-1));
                ret.add(new Tile(ptype, colour, xpos,   ypos+1));
                ret.add(new Tile(ptype, colour, xpos+1, ypos-1));
                ret.add(new Tile(ptype, colour, xpos+1, ypos  ));
                ret.add(new Tile(ptype, colour, xpos+1, ypos+1));
                */
                int[] arr = {-1, 0, 1};
                for (int i : arr) {
                    for (int j : arr) {
                        if (i == 0 && j == 0) continue;
                        if (inBounds(xpos+i, ypos+j))
                            ret.add(new Tile(ptype, colour, xpos+i, ypos+j));
                    }
                }
                
                break;
            }
        }
        
        return ret;
    }
    
    
    // attempts to move a piece
    static boolean moveTo(Tile from, Tile to) {
        return from.movePieceTo(to);
    }
    
    // determines who (if anyone) is in check
    int checkCheck() {
        return 0;
    }
    
    // determines who (if anyone) is in checkmate
    int checkCheckMate() {
        return 0;
    }
    
    // returns true if coordinates in [0,7]
    static boolean inBounds(int xpos, int ypos) {
        return (xpos<8 && xpos>=0 && ypos<8 && ypos>=0);
    }
}
