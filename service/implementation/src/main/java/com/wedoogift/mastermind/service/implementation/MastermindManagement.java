package com.wedoogift.mastermind.service.implementation;

import com.wedoogift.mastermind.dao.contract.IMastermindManagementDao;
import com.wedoogift.mastermind.datamodel.ErrorPawn;
import com.wedoogift.mastermind.datamodel.GamePawn;
import com.wedoogift.mastermind.dao.contract.IMastermindManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bob
 */
@Service
public class MastermindManagement implements IMastermindManagement {

  @Autowired
  private IMastermindManagementDao mastermindManagementDao;
  private static final Logger LOGGER = LoggerFactory.getLogger(MastermindManagement.class);

  @Override
  public boolean win() {
    try {
      // on peut pas gagner avant d'avoir joué :')
      if(mastermindManagementDao.getTurnCount() == 0)
        return false;

      // on récupère les données de victoire
      List<Integer> data = mastermindManagementDao.getVictoryData();
      List<GamePawn> victoryPawns = MastermindObjectMapper.getPawnsFromData(data).stream().collect(Collectors.toList());

      // les derniers pions joués
      List<GamePawn> lastPawns = this.getPawns(mastermindManagementDao.getTurnCount()).stream().collect(Collectors.toList());

      for(int iVictory=0; iVictory<victoryPawns.size(); iVictory++) {
          if(!victoryPawns.get(iVictory).equals(lastPawns.get(iVictory)))
            return false;
      }

      return true;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
  }

  @Override
  public void nextTurn(List<GamePawn> gamePawns) {
    try {
      // check des pions
      checkGamePawns(gamePawns);

      // check du tour
      checkTurn(mastermindManagementDao.getTurnCount()+1);

      List<Integer> victoryData = mastermindManagementDao.getVictoryData();
      if(null == victoryData || victoryData.isEmpty())
        throw new RuntimeException("Please start the game before playing");

      // check si on a pas déjà gagné au tour précédent
      if(win())
        throw new RuntimeException("Game already win !!!");

      // si on a 0 tour, on commence la partie
      int iTurn = mastermindManagementDao.getTurnCount();

      // mapping datamodel vers persistence
      List<Integer> data = MastermindObjectMapper.getDatasFromPawns(gamePawns);

      // sauvegarder le tour
      mastermindManagementDao.saveTurn(data);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
  }

  /**
   * Méthode qui va vérifier la cohérence de pions
   * @param gamePawns les pions du tour
   */
  private void checkGamePawns(List<GamePawn> gamePawns) {
    if(null == gamePawns || gamePawns.size() == 0)
      throw new RuntimeException("Panws are undefined");

    // check si on a bien 4 pions
    // ici on peut rajouter une extensibilité si on veut faire un jeu à plus de 4 pions
    if( 4 != gamePawns.size())
      throw new RuntimeException("4 pawns are mandatory");

    gamePawns.stream().forEach(gamePawn -> {
      if(null == gamePawn)
        throw new RuntimeException("Pawn is undefined");

      int value = MastermindObjectMapper.getDataFromPawn(gamePawn);
      if(0 == value)
        throw new RuntimeException("Pawn is not recognized");
    });
  }

  private void checkTurn(int iTurn) {
    if(iTurn < 1)
      throw new RuntimeException("Min turn is 1");
    // ici on peut rajotuer un extensibilité possible sur le nombre de tour
    int maxTurns = 10;
    if(iTurn > maxTurns)
    throw new RuntimeException("Max turns is "+maxTurns);
  }

  @Override
  public List<GamePawn> getPawns(int iTurn) {
    try {
      // check du tour
      checkTurn(iTurn);

      if(iTurn > mastermindManagementDao.getTurnCount())
        throw new RuntimeException("Turn "+iTurn+" is not play");

      // on récupère les données
      List<Integer> data = mastermindManagementDao.getDataFromTurn(iTurn-1);

      // mapping persitence vers datamodel
      return MastermindObjectMapper.getPawnsFromData(data);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
  }

  @Override
  public void startGame(List<GamePawn> gamePawns) {
    try {
      if(null == gamePawns || gamePawns.size() == 0)
        throw new RuntimeException("Victory pawns are undefined");

      // check des pions
      checkGamePawns(gamePawns);

      mastermindManagementDao.reset();

      // mapping datamodel vers persistence
      List<Integer> data = MastermindObjectMapper.getDatasFromPawns(gamePawns);
      mastermindManagementDao.addVictoryPawns(data);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
  }

  @Override
  public void endGame() {
    mastermindManagementDao.reset();
  }

  @Override
  public List<ErrorPawn> getErrors(int iTurn) {
    try {
      // check du tour
      checkTurn(iTurn);

      if(iTurn > mastermindManagementDao.getTurnCount())
        throw new RuntimeException("Turn "+iTurn+" is not play");

      List<ErrorPawn> errorPawns = new ArrayList<>();
      List<GamePawn> pawns = getPawns(iTurn);
      List<GamePawn> victoryPawns = MastermindObjectMapper.getPawnsFromData(mastermindManagementDao.getVictoryData());

      for(int iVictory=0; iVictory<victoryPawns.size(); iVictory++) {
        if(victoryPawns.get(iVictory).equals(pawns.get(iVictory)))
          // si on a pion bien placé, on ajoute un pion blanc
          errorPawns.add(new ErrorPawn(IMastermindConstants._WHITE));
        else {
          final GamePawn pawn = pawns.get(iVictory);
          long count = victoryPawns.stream().filter(victoryPawn -> victoryPawn.equals(pawn)).count();
          // si on a un pion mal placé, on ajoute un pion noir
          if(count >= 1)
            errorPawns.add(new ErrorPawn(IMastermindConstants._DARK));
          // sinon on ajoute un pion transparent pour signifier que la couleur n'est pas présente
          else
            errorPawns.add(new ErrorPawn(IMastermindConstants._TRANSPARENT));
        }
      }

      return errorPawns;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw e;
    }
  }
}
