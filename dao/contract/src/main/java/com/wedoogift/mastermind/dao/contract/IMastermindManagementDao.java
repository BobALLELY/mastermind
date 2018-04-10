package com.wedoogift.mastermind.dao.contract;


import java.util.List;

/**
 * @author Bob
 */
public interface IMastermindManagementDao {

  /**
   * @param data les données du tour
   * @return Sauvegarde les données du tour
   */
  public void saveTurn(List<Integer> data);

  /**
   * Retourne les données d'un tour
   * @param iTurn
   * @return
   */
  public List<Integer> getDataFromTurn(int iTurn);

  /**
   * @return le nombre de tours effectués
   */
  public int getTurnCount();

  /**
   * Reset la partie
   */
  public void reset();

  /**
   * @return Retourne le jeu de données créé au début de la partie
   */
  public List<Integer> getVictoryData();

  /**
   * Ajoute les pions de victoire
   * @param data
   */
  public void addVictoryPawns(List<Integer> data);
}
