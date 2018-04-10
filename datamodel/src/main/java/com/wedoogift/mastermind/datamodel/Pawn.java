package com.wedoogift.mastermind.datamodel;

/**
 * @author Bob
 */
public class Pawn{

  String color;

  protected Pawn(String color) {
    this.color = color;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Pawn gamePawn = (Pawn) o;

    return color != null ? color.equals(gamePawn.color) : gamePawn.color == null;
  }

  @Override
  public int hashCode() {
    return color != null ? color.hashCode() : 0;
  }

  public String getColor() {
    return this.color;
  }
}
