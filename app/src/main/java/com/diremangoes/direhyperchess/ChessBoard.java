// provides for the ChessBoard class, whick links game systems

import java.util.*;

public class ChessBoard {
    
    // simple constructor
    public ChessBoard() {
        
        _tiles = new Tile[8][8];
        
        // initialize empty board cells
        for (int x=0; x<8; ++x) {
            for(int y=2; y<6; ++y) {
                _tiles[x][y] = new Tile(PieceType.EMPTY, 0, x,y);
            }
        }
        // add white pawns
        for (int x=0; x<8; ++x)
            _tiles[x][0] = new Tile(PieceType.PAWN, 1, x,0);
        // add white pieces
        _tiles[0][1] = new Tile(PieceType.ROOK,   1, 0,1);
        _tiles[1][1] = new Tile(PieceType.KNIGHT, 1, 1,1);
        _tiles[2][1] = new Tile(PieceType.BISHOP, 1, 2,1);
        _tiles[3][1] = new Tile(PieceType.KING,   1, 3,1);
        _tiles[4][1] = new Tile(PieceType.QUEEN,  1, 4,1);
        _tiles[5][1] = new Tile(PieceType.BISHOP, 1, 5,1);
        _tiles[6][1] = new Tile(PieceType.KNIGHT, 1, 6,1);
        _tiles[7][1] = new Tile(PieceType.ROOK,   1, 7,1);
        
        // add black pawns
        for (int x=0; x<8; ++x)
            _tiles[x][7] = new Tile(PieceType.PAWN, 2, x,7);
        // add black pieces
        _tiles[0][6] = new Tile(PieceType.ROOK,   2, 0,6);
        _tiles[1][6] = new Tile(PieceType.KNIGHT, 2, 1,6);
        _tiles[2][6] = new Tile(PieceType.BISHOP, 2, 2,6);
        _tiles[3][6] = new Tile(PieceType.KING,   2, 3,6);
        _tiles[4][6] = new Tile(PieceType.QUEEN,  2, 4,6);
        _tiles[5][6] = new Tile(PieceType.BISHOP, 2, 5,6);
        _tiles[6][6] = new Tile(PieceType.KNIGHT, 2, 6,6);
        _tiles[7][6] = new Tile(PieceType.ROOK,   2, 7,6);
        
        
        // print the current board
        for (int i=0; i<8; ++i) {
            for(int j=0; j<8; ++j) {
                _tiles[i][j].printInfo();
            }
        }
        System.out.println();
        
        // and verify the movement calculations
        // ArrayList<Tile> moves = getMoves(new Tile(PieceType.KNIGHT, 1, 4,4));
        ArrayList<Tile> moves = getMoves(_tiles[1][1]);
        
        for (Tile t : moves)
            t.printInfo();
        
    }
    
    
    
    
    // returns true if a tile has a piece on it
    boolean hasPiece(Tile t) {
        return t.getPieceOwner() != 0;
    }
    // returns true if a tile has a piece on it of the specified colour
    boolean hasPiece(Tile t, int color) {
        return t.getPieceOwner() == color;
    }
    
    
    // generates all possible movement locations, does not consider occlusion
    ArrayList<Tile> getMoves(Tile t) {
        // get some local names for target data
        ArrayList<Tile> ret = new ArrayList();
        PieceType ptype     = t.getPieceType();
        int colour          = t.getPieceOwner();
        int xpos            = t.getxpos();
        int ypos            = t.getypos();
        
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
    
    
    // returns all the valid moves for a given target piece
    ArrayList<Tile> getValidMoves(Tile t) {
        ArrayList<Tile> ret = new ArrayList();
        
        return ret;
    }
    
    
    // attempts to move a piece
    boolean moveTo(Tile from, Tile to) {
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
    boolean inBounds(int xpos, int ypos) {
        return (xpos<8 && xpos>=0 && ypos<8 && ypos>=0);
    }
    
    
    // member data
    Tile[][] _tiles;
    
}