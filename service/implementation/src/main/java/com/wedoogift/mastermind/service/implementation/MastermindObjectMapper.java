package com.wedoogift.mastermind.service.implementation;

import com.wedoogift.mastermind.datamodel.GamePawn;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bob
 */
public class MastermindObjectMapper {

  public static List<Integer> getDatasFromPawns(List<GamePawn> pawns) {
    return pawns.stream().map(pawn -> getDataFromPawn(pawn)).collect(Collectors.toList());
  }

  public static List<GamePawn> getPawnsFromData(List<Integer> data) {
    return data.stream().map(value -> getPawnFromData(value)).collect(Collectors.toList());
  }

  /**
   * Méthode servant à transformer la data pour la dao. <br>
   * Ici on choisit de transformer le datamodel métier de String vers Integer
   * @param gamePawn
   * @return
   */
  public static Integer getDataFromPawn(GamePawn gamePawn) {
    if(IMastermindConstants._YELLOW.equals(gamePawn.getColor())) {
      return 1;
    }
    else if(IMastermindConstants._BLUE.equals(gamePawn.getColor())) {
      return 2;
    }
    else if(IMastermindConstants._RED.equals(gamePawn.getColor())) {
      return 3;
    }
    else if(IMastermindConstants._GREEN.equals(gamePawn.getColor())) {
      return 4;
    }

    return 0;
  }

  private static GamePawn getPawnFromData(Integer value) {
    if(1 == value.intValue()) {
      return new GamePawn(IMastermindConstants._YELLOW);
    }
    else if(2 == value.intValue()) {
      return new GamePawn(IMastermindConstants._BLUE);
    }
    else if(3 == value.intValue()) {
      return new GamePawn(IMastermindConstants._RED);
    }
    else if(4 == value.intValue()) {
      return new GamePawn(IMastermindConstants._GREEN);
    }

    return null;
  }
}
