// provides for the Tile class and its game-piece logic
package com.diremangoes.direhyperchess;

import java.util.*;

public class Tile {
    public Tile() {}

    // simple constructor
    public Tile(PieceType ptype, int powner, int xpos, int ypos) {
        _ptype  = ptype;
        _powner = powner;
        _xpos   = xpos;
        _ypos   = ypos;
        
        _is_selected = false;
        _cooldown = 0;
        
        // System.out.format("%s %d (%d,%d)\n", ptype, powner, xpos, ypos);
    }
    
    
    // returns true if piece at this 
    boolean testMove(Tile target) {
        boolean ret = false;
        
        
        // return false if selected tile is unowned, or no piece on tile
        if (_powner == 0 || _ptype == PieceType.EMPTY) {
            System.out.println("cannot move NONEXISTENT piece");
            return false;
        }
            
        // return false if moving piece to owned location
        if (_powner == target.getPieceOwner()) {
            System.out.println("cannot move to OWNED tile");
            return false;
        }
        
        // return false if moving piece to self
        if (target.getxpos() == _xpos && target.getypos() == _ypos) {
            System.out.println("cannot move to OWN tile");
            return false;
        }
        
        // TODO: check for if move would put player into check
        
        
        // based on piece type, determine if target is a valid location
        switch(_ptype) {
            case EMPTY: {
                System.out.println("cannot move EMPTY piece");
                break;
            }
            case PAWN: { // forward 1, horizontal deviation of at most 1
                if (target.getypos() != _ypos -1)   // can only move forward
                    break;
                else if ( Math.abs(target.getxpos() - _xpos) >1 )
                    break;
                else
                    ret = true;
                
                break;
            }
            case ROOK: {    // straight line in cardinal directions
                if (target.getxpos() == _xpos) {
                    ret = true;
                    break;
                }
                else if (target.getypos() == _ypos) {
                    ret = true;
                    break;
                }
                
                break;
            }
            case BISHOP: {  // diagonal
                System.out.println("TODO: Bishop");
                break;
            }
            case KNIGHT: {  // L-shape (2,1)
                System.out.println("TODO: Knight");
                break;
            }
            case QUEEN: {   // straight line or diagnal
                System.out.println("TODO: Queen");
                break;
            }
            case KING: {    // deviation of 1 in x and/or y
                System.out.println("TODO: King");
                break;
            }
            
            default: {
                System.out.println("cannot move UNKNOWN piece");
                break;
            }
        }
        return ret;
    }
    
    
    // attempts to move a piece located at calling tile to target tile
    public boolean movePieceTo(Tile target) {
        System.out.printf("moving %s from %d:%d to %d:%d\n", 
                          _ptype, _xpos, _ypos, 
                          target.getxpos(), target.getypos()
                          );
    
        if (!testMove(target)) {
            System.out.println("\tMove cannot be completed");
            return false;
        }
    
        if (target.getPieceType() != PieceType.EMPTY) {
            if (_powner == 1) {
                System.out.format("\tWhite %s captured black %s - (%d,%d)\n",
                                  _ptype, target.getPieceType(),
                                  target.getxpos(), target.getypos()
                                  );
            }
            else if (_powner == 2) {
                System.out.format("\tBlack %s captured white %s - (%d,%d)\n",
                                  _ptype, target.getPieceType(),
                                  target.getxpos(), target.getypos()
                                  );
            }
            else {
                System.out.println("\n\tSomething weird went down!");
                return false;
            }
            
            
            
        }
    
        // move piece to target
        target.setPieceOwner(_powner);
        target.setPieceType(_ptype);
        // set this tile to have nothing on it
        setPieceOwner(0);
        setPieceType(PieceType.EMPTY);
    
        return true;
    }
    
    
    
    public PieceType getPieceType() {return _ptype;}
    public int getPieceOwner()      {return _powner;}
    public int getxpos()            {return _xpos;}
    public int getypos()            {return _ypos;}
    public int getCooldown()        {return _cooldown;}
    public boolean getSelected()    {return _is_selected;}
    
    public void setPieceType(PieceType ptype) {_ptype = ptype;}
    public void setPieceOwner(int powner) {_powner = powner;}
    public void setCooldown(int cooldown) {_cooldown = cooldown;}
    public void setSelected(boolean s)    {_is_selected = s;}
    public void toggleSelected()          {_is_selected = !_is_selected;}
    
    
    
    public void printInfo() {
        System.out.format("Cell (%d,%d) - %s %s\n",
                          _xpos, _ypos,
                          (_powner == 1 ? "white" : 
                            (_powner == 2 ? "black" : "NONE ")),
                          _ptype);
                          
    }
    
    
    // returns true if coordinates in [0,7]
    boolean inBounds(int xpos, int ypos) {
        return (xpos<8 && xpos>=0 && ypos<8 && ypos>=0);
    }
    
    
    
    
    PieceType _ptype;   // what kind of piece is on this tile?
    int _powner;        // who owns said piece? 0/1/2 = empty/white/black
    int _xpos;
    int _ypos;
    int _cooldown;
    
    // for UI
    boolean _is_selected;
    
}



