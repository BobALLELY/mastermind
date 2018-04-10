package com.wedoogift.mastermind.dao.implementation;

import com.wedoogift.mastermind.dao.contract.IMastermindManagementDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bob
 */
@Service
public class MastermindManagementDao implements IMastermindManagementDao {

  // stockage dans des variables
  private List<List<Integer>> storageData = null;
  private List<Integer> victoryData = null;

  @Override
  public void saveTurn(List<Integer> data) {
    if(null == storageData)
      reset();

    storageData.add(data);
  }

  @Override
  public List<Integer> getDataFromTurn(int iTurn) {
    return storageData.get(iTurn);
  }

  @Override
  public int getTurnCount() {
    return storageData.size();
  }

  @Override
  public void reset() {
    victoryData = new ArrayList<>();
    storageData = new ArrayList<>();
  }

  public void addVictoryPawns(List<Integer> data) {
    if(null == victoryData)
      this.reset();

    victoryData = data;
  }

  @Override
  public List<Integer> getVictoryData() {
    return victoryData;
  }
}
