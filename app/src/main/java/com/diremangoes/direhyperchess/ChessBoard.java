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
            _tiles[x][1] = new Tile(PieceType.PAWN, 1, x,1);
        // add white pieces
        _tiles[0][0] = new Tile(PieceType.ROOK,   1, 0,0);
        _tiles[1][0] = new Tile(PieceType.KNIGHT, 1, 1,0);
        _tiles[2][0] = new Tile(PieceType.BISHOP, 1, 2,0);
        _tiles[3][0] = new Tile(PieceType.KING,   1, 3,0);
        _tiles[4][0] = new Tile(PieceType.QUEEN,  1, 4,0);
        _tiles[5][0] = new Tile(PieceType.BISHOP, 1, 5,0);
        _tiles[6][0] = new Tile(PieceType.KNIGHT, 1, 6,0);
        _tiles[7][0] = new Tile(PieceType.ROOK,   1, 7,0);
        
        // add black pawns
        for (int x=0; x<8; ++x)
            _tiles[x][6] = new Tile(PieceType.PAWN, 2, x,6);
        // add black pieces
        _tiles[0][7] = new Tile(PieceType.ROOK,   2, 0,7);
        _tiles[1][7] = new Tile(PieceType.KNIGHT, 2, 1,7);
        _tiles[2][7] = new Tile(PieceType.BISHOP, 2, 2,7);
        _tiles[3][7] = new Tile(PieceType.KING,   2, 3,7);
        _tiles[4][7] = new Tile(PieceType.QUEEN,  2, 4,7);
        _tiles[5][7] = new Tile(PieceType.BISHOP, 2, 5,7);
        _tiles[6][7] = new Tile(PieceType.KNIGHT, 2, 6,7);
        _tiles[7][7] = new Tile(PieceType.ROOK,   2, 7,7);
        
        
        // print the current board
        for (int i=0; i<8; ++i) {
            for(int j=0; j<8; ++j) {
                _tiles[i][j].printInfo();
            }
        }
        System.out.println();
        
        // and verify the movement calculations
        // ArrayList<Tile> moves = getMoves(new Tile(PieceType.KNIGHT, 1, 4,4));
        ArrayList<Tile> moves;
        
        System.out.println("moves:");
        moves = getMoves(_tiles[1][1]);
        for (Tile t : moves)
            t.printInfo();
        
        System.out.println("valid moves:");
        moves = getValidMoves(_tiles[1][1]);
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
        int color           = t.getPieceOwner();
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
                        ret.add(new Tile(ptype, color, xpos+i, ypos-1));
                }
                break;
            }
            case ROOK: { // DONE
                // x-translations
                for (int x=0; x<8; ++x) {
                    if (x == xpos) continue;
                    ret.add(new Tile(ptype, color, x, ypos));
                }
                for (int y=0; y<8; ++y) {
                    if (y == ypos) continue;
                    ret.add(new Tile(ptype, color, xpos, y));
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
                        ret.add(new Tile(ptype, color, xpos+i, ypos+i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos+i, ypos-i))
                        ret.add(new Tile(ptype, color, xpos+i, ypos-i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos-i, ypos+i))
                        ret.add(new Tile(ptype, color, xpos-i, ypos+i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos-i, ypos-i))
                        ret.add(new Tile(ptype, color, xpos-i, ypos-i));
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
                            ret.add(new Tile(ptype, color, xpos+i, ypos+j));
                    }
                }
                
                break;
            }
            case QUEEN: { // DONE
                // x-translations
                for (int x=0; x<8; ++x) {
                    if (x == xpos) continue;
                    ret.add(new Tile(ptype, color, x, ypos));
                }
                for (int y=0; y<8; ++y) {
                    if (y == ypos) continue;
                    ret.add(new Tile(ptype, color, xpos, y));
                }
                    
                // look in each diagonal until out of bounds
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos+i, ypos+i))
                        ret.add(new Tile(ptype, color, xpos+i, ypos+i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos+i, ypos-i))
                        ret.add(new Tile(ptype, color, xpos+i, ypos-i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos-i, ypos+i))
                        ret.add(new Tile(ptype, color, xpos-i, ypos+i));
                    else break;
                }
                for (int i=1; i<8; ++i) {
                    if (inBounds(xpos-i, ypos-i))
                        ret.add(new Tile(ptype, color, xpos-i, ypos-i));
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
                            ret.add(new Tile(ptype, color, xpos+i, ypos+j));
                    }
                }
                
                break;
            }
        }
        
        return ret;
    }
    
    
    // returns all the valid moves for a given target piece
    ArrayList<Tile> getValidMoves(Tile t) {
        // get some local names for target data
        ArrayList<Tile> ret = new ArrayList();
        PieceType ptype     = t.getPieceType();
        int color           = t.getPieceOwner();
        int ocolor          = (t.getPieceOwner() == 1 ? 2 : 1);
        int xpos            = t.getxpos();
        int ypos            = t.getypos();
        
        switch(ptype) {
            case EMPTY: {
                break;
            }
            case PAWN: {
                int[] arr = {-1, 0, 1};
                for (int i : arr) {
                    if (inBounds(xpos+i, ypos-1)) {
                        if (!hasPiece(_tiles[xpos+1][ypos-1], color))
                            ret.add(new Tile(ptype, color, xpos+i, ypos-1));
                    }
                }
                break;
            }
            case ROOK: {
                // x-translations
                for (int x=xpos+1; x<8; ++x) {
                    if (hasPiece(_tiles[x][ypos], color)) break;
                    ret.add(new Tile(ptype, color, x, ypos));
                }
                for (int x=xpos-1; x>=0; --x) {
                    if (hasPiece(_tiles[x][ypos], color)) break;
                    ret.add(new Tile(ptype, color, x, ypos));
                }
                // y-translations
                for (int y=ypos+1; y<8; ++y) {
                    if (hasPiece(_tiles[xpos][y], color)) break;
                    ret.add(new Tile(ptype, color, xpos, y));
                }
                for (int y=ypos-1; y>=0; --y) {
                    if (hasPiece(_tiles[xpos][y], color)) break;
                    ret.add(new Tile(ptype, color, xpos, y));
                }
                break;
            }
            case BISHOP: {
                // look in each diagonal until out of bounds
                int newx, newy;
                
                // for each diagonal, seek until out of bounds or piece hit
                for (int i=0; ; ++i) {
                    newx = xpos+i;
                    newy = ypos+i;
                    if (!inBounds(newx, newy)) break;
                    else if (hasPiece(_tiles[xpos][ypos], color)) break;
                    else { // if enemy piece found, you can take it
                        ret.add(new Tile(ptype, color, newx, newy));
                        if (hasPiece(_tiles[xpos][ypos], ocolor)) break;
                    }
                }
                for (int i=0; ; ++i) {
                    newx = xpos+i;
                    newy = ypos-i;
                    if (!inBounds(newx, newy)) break;
                    else if (hasPiece(_tiles[xpos][ypos], color)) break;
                    else { // if enemy piece found, you can take it
                        ret.add(new Tile(ptype, color, newx, newy));
                        if (hasPiece(_tiles[xpos][ypos], ocolor)) break;
                    }
                }
                for (int i=0; ; ++i) {
                    newx = xpos-i;
                    newy = ypos+i;
                    if (!inBounds(newx, newy)) break;
                    else if (hasPiece(_tiles[xpos][ypos], color)) break;
                    else { // if enemy piece found, you can take it
                        ret.add(new Tile(ptype, color, newx, newy));
                        if (hasPiece(_tiles[xpos][ypos], ocolor)) break;
                    }
                }
                for (int i=0; ; ++i) {
                    newx = xpos-i;
                    newy = ypos-i;
                    if (!inBounds(newx, newy)) break;
                    else if (hasPiece(_tiles[xpos][ypos], color)) break;
                    else { // if enemy piece found, you can take it
                        ret.add(new Tile(ptype, color, newx, newy));
                        if (hasPiece(_tiles[xpos][ypos], ocolor)) break;
                    }
                }
                break;
            }
            case KNIGHT: {
                int[] arr = {-2, -1, 1, 2};
                for (int i : arr) {
                    for (int j : arr) {
                        if (i == j || i == -j) continue;
                        if (inBounds(xpos+i, ypos+j))
                            if (!hasPiece(_tiles[xpos+i][ypos+j], color))
                                ret.add(new Tile(ptype, color, xpos+i, ypos+j));
                    }
                }
                break;
            }
            case QUEEN: {
                // look in each diagonal until out of bounds
                int newx, newy;
                
                // x-translations
                for (int x=xpos+1; x<8; ++x) {
                    if (hasPiece(_tiles[x][ypos], color)) break;
                    ret.add(new Tile(ptype, color, x, ypos));
                }
                for (int x=xpos-1; x>=0; --x) {
                    if (hasPiece(_tiles[x][ypos], color)) break;
                    ret.add(new Tile(ptype, color, x, ypos));
                }
                // y-translations
                for (int y=ypos+1; y<8; ++y) {
                    if (hasPiece(_tiles[xpos][y], color)) break;
                    ret.add(new Tile(ptype, color, xpos, y));
                }
                for (int y=ypos-1; y>=0; --y) {
                    if (hasPiece(_tiles[xpos][y], color)) break;
                    ret.add(new Tile(ptype, color, xpos, y));
                }
                
                // for each diagonal, seek until out of bounds or piece hit
                for (int i=0; ; ++i) {
                    newx = xpos+i;
                    newy = ypos+i;
                    if (!inBounds(newx, newy)) break;
                    else if (hasPiece(_tiles[xpos][ypos], color)) break;
                    else { // if enemy piece found, you can take it
                        ret.add(new Tile(ptype, color, newx, newy));
                        if (hasPiece(_tiles[xpos][ypos], ocolor)) break;
                    }
                }
                for (int i=0; ; ++i) {
                    newx = xpos+i;
                    newy = ypos-i;
                    if (!inBounds(newx, newy)) break;
                    else if (hasPiece(_tiles[xpos][ypos], color)) break;
                    else { // if enemy piece found, you can take it
                        ret.add(new Tile(ptype, color, newx, newy));
                        if (hasPiece(_tiles[xpos][ypos], ocolor)) break;
                    }
                }
                for (int i=0; ; ++i) {
                    newx = xpos-i;
                    newy = ypos+i;
                    if (!inBounds(newx, newy)) break;
                    else if (hasPiece(_tiles[xpos][ypos], color)) break;
                    else { // if enemy piece found, you can take it
                        ret.add(new Tile(ptype, color, newx, newy));
                        if (hasPiece(_tiles[xpos][ypos], ocolor)) break;
                    }
                }
                for (int i=0; ; ++i) {
                    newx = xpos-i;
                    newy = ypos-i;
                    if (!inBounds(newx, newy)) break;
                    else if (hasPiece(_tiles[xpos][ypos], color)) break;
                    else { // if enemy piece found, you can take it
                        ret.add(new Tile(ptype, color, newx, newy));
                        if (hasPiece(_tiles[xpos][ypos], ocolor)) break;
                    }
                }
                break;
            }
            case KING: {
                int[] arr = {-1, 0, 1};
                for (int i : arr) {
                    for (int j : arr) {
                        if (i == 0 && j == 0) continue;
                        if (inBounds(xpos+i, ypos+j))
                            if (!hasPiece(_tiles[xpos+i][ypos+j], color))
                                ret.add(new Tile(ptype, color, xpos+i, ypos+j));
                    }
                }
                break;
            }
        }
        
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