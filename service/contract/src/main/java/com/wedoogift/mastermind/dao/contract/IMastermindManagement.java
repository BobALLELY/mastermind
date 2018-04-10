package com.wedoogift.mastermind.dao.contract;


import com.wedoogift.mastermind.datamodel.ErrorPawn;
import com.wedoogift.mastermind.datamodel.GamePawn;

import java.util.List;

/**
 * @author Bob
 */
public interface IMastermindManagement {

  /**
   * @return Retourne si le joueur a gagné
   */
  public boolean win();

  /**
   * Applique un nouveau tour avec les pions joués
   * @param gamePawns les pions joués durant le tour
   */
  public void nextTurn(List<GamePawn> gamePawns);

  /**
   * @param iTurn tour ou l'on souhaite avoir les pions
   * @return Retourne les pions du tour demandé
   */
  public List<GamePawn> getPawns(int iTurn);

  /**
   * Initialise la partie avec des pions de victoire
   * @param gamePawns les pions de victoire
   */
  public void startGame(List<GamePawn> gamePawns);

  /**
   * Pour signifier que la partie est terminée
   */
  public void endGame();

  /**
   * @return Retourne les errors du tour
   */
  public List<ErrorPawn> getErrors(int iTurn);
}
