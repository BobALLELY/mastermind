package com.wedoogift.mastermind.datamodel;

/**
 * @author Bob
 */
public class GamePawn extends Pawn {

  public GamePawn(String color) {
    super(color);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GamePawn gamePawn = (GamePawn) o;

    return color != null ? color.equals(gamePawn.color) : gamePawn.color == null;
  }

  @Override
  public int hashCode() {
    return color != null ? color.hashCode() : 0;
  }

}
