package com.wedoogift.mastermind.service.implementation;

import com.wedoogift.mastermind.dao.contract.IMastermindManagement;
import com.wedoogift.mastermind.datamodel.ErrorPawn;
import com.wedoogift.mastermind.datamodel.GamePawn;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Bob
 */
@SuppressWarnings({ "nls" })
@SpringBootTest(classes = ServiceClientTestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CoreMastermindManagementTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  @Autowired
  IMastermindManagement mastermindManagement;

  @After
  public void reset() {
    mastermindManagement.endGame();
  }

  @Test
  public void testStartGameError_NoVictoryPawns() {
    thrown.expect(Exception.class);
    thrown.expectMessage("undefined"); //$NON-NLS-1$
    mastermindManagement.startGame(null);
  }

  @Test
  public void testStartGameError_3VictoryPawns() {
    thrown.expect(Exception.class);
    thrown.expectMessage("4 pawns are mandatory"); //$NON-NLS-1$

    List<GamePawn> pawns = new ArrayList<>();
    pawns.add(new GamePawn(IMastermindConstants._BLUE));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));
    pawns.add(new GamePawn(IMastermindConstants._RED));

    mastermindManagement.startGame(pawns);
  }

  @Test
  public void testStartGameError_BadVictoryPawns() {
    thrown.expect(Exception.class);
    thrown.expectMessage("Pawn is not recognized"); //$NON-NLS-1$

    List<GamePawn> pawns = new ArrayList<>();
    pawns.add(new GamePawn(IMastermindConstants._BLUE));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));
    pawns.add(new GamePawn(IMastermindConstants._RED));
    pawns.add(new GamePawn("azerty"));

    mastermindManagement.startGame(pawns);
  }

  @Test
  public void testNominalGame() {
    List<GamePawn> victoryPawns = getVictoryPawns();

    mastermindManagement.startGame(victoryPawns);
    boolean gameWin = mastermindManagement.win();
    Assert.assertTrue("Impossible to win at start game", !gameWin);

    List<GamePawn> pawns = new ArrayList<>();
    pawns.add(new GamePawn(IMastermindConstants._BLUE));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));
    pawns.add(new GamePawn(IMastermindConstants._YELLOW));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));

    mastermindManagement.nextTurn(pawns);

    // on check que les erreurs sont correctes
    List<ErrorPawn> errors = mastermindManagement.getErrors(1);
    Assert.assertTrue("Number of error pawns is not correct", errors.size() == 4);
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._WHITE.equals(errors.get(0).getColor()));
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._WHITE.equals(errors.get(1).getColor()));
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._TRANSPARENT.equals(errors.get(2).getColor()));
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._DARK.equals(errors.get(3).getColor()));


    mastermindManagement.nextTurn(victoryPawns);
    gameWin = mastermindManagement.win();
    Assert.assertTrue("Game not win ...", gameWin);
  }

  private  List<GamePawn> getVictoryPawns() {
    List<GamePawn> pawns = new ArrayList<>();
    pawns.add(new GamePawn(IMastermindConstants._BLUE));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));
    pawns.add(new GamePawn(IMastermindConstants._RED));
    pawns.add(new GamePawn(IMastermindConstants._RED));

    return pawns;
  }

  @Test
  public void testTurns_11Turns() {
    thrown.expect(Exception.class);
    thrown.expectMessage("Max turns is 10"); //$NON-NLS-1$

    List<GamePawn> victoryPawns = getVictoryPawns();
    List<GamePawn> pawns = new ArrayList<>();
    pawns.add(new GamePawn(IMastermindConstants._BLUE));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));
    pawns.add(new GamePawn(IMastermindConstants._RED));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));

    mastermindManagement.startGame(victoryPawns);

    // 11 turns
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns);
  }

  @Test
  public void testTurnsError_GameNotStarted() {
    thrown.expect(Exception.class);
    thrown.expectMessage("Please start the game before playing"); //$NON-NLS-1$

    List<GamePawn> pawns = new ArrayList<>();
    pawns.add(new GamePawn(IMastermindConstants._BLUE));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));
    pawns.add(new GamePawn(IMastermindConstants._RED));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));

    mastermindManagement.nextTurn(pawns);
  }

  @Test
  public void testGetPawnsError_NotPlay() {
    thrown.expect(Exception.class);
    thrown.expectMessage("Turn 1 is not play"); //$NON-NLS-1$

    mastermindManagement.getPawns(1);
  }

  @Test
  public void testNominalGetPawns() {
    List<GamePawn> victoryPawns = getVictoryPawns();
    List<GamePawn> pawns = new ArrayList<>();
    pawns.add(new GamePawn(IMastermindConstants._BLUE));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));
    pawns.add(new GamePawn(IMastermindConstants._RED));
    pawns.add(new GamePawn(IMastermindConstants._GREEN));

    List<GamePawn> pawns2 = new ArrayList<>();
    pawns2.add(new GamePawn(IMastermindConstants._BLUE));
    pawns2.add(new GamePawn(IMastermindConstants._BLUE));
    pawns2.add(new GamePawn(IMastermindConstants._BLUE));
    pawns2.add(new GamePawn(IMastermindConstants._BLUE));

    mastermindManagement.startGame(victoryPawns);
    mastermindManagement.nextTurn(pawns);
    mastermindManagement.nextTurn(pawns2);

    List<GamePawn> pawnsTurn1 = mastermindManagement.getPawns(1);
    Assert.assertTrue("Number of error pawns is not correct", pawnsTurn1.size() == 4);
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._BLUE.equals(pawnsTurn1.get(0).getColor()));
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._GREEN.equals(pawnsTurn1.get(1).getColor()));
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._RED.equals(pawnsTurn1.get(2).getColor()));
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._GREEN.equals(pawnsTurn1.get(3).getColor()));

    List<GamePawn> pawnsTurn2 = mastermindManagement.getPawns(2);
    Assert.assertTrue("Number of error pawns is not correct", pawnsTurn2.size() == 4);
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._BLUE.equals(pawnsTurn2.get(0).getColor()));
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._BLUE.equals(pawnsTurn2.get(1).getColor()));
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._BLUE.equals(pawnsTurn2.get(2).getColor()));
    Assert.assertTrue("Error pawn is not correct", IMastermindConstants._BLUE.equals(pawnsTurn2.get(3).getColor()));


  }

  @Test
  public void testGetErrorsPawnsError_NotPlay() {
    thrown.expect(Exception.class);
    thrown.expectMessage("Turn 1 is not play"); //$NON-NLS-1$

    mastermindManagement.getErrors(1);
  }
}
