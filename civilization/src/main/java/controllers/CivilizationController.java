//besmellah

package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import models.Civilization;
import models.tile.Improvement;
import models.unit.*;
import models.City;
import models.tile.Tile;
import models.Technology;

public class CivilizationController {
    private static CivilizationController instance = null;

    private CivilizationController() {
    }

    public static CivilizationController getInstance() {
        if (instance == null) instance = new CivilizationController();
        return instance;
    }

    public String selectUnit(int[] position, String unitType) {
        if (!isPositionValid(position))
            return "invalid position";
        if (unitType.equals("combat")) {
            if (getTileByPosition(position).getCombatUnit()==null)
                return "no such unit";
            else return unitType;
        }else if (unitType.equals("noncombat")){
            if (getTileByPosition(position).getNonCombatUnit()==null)
                return "no such unit";
            else return unitType;
        }
        return "ok";
    }

    public CombatUnit getCombatUnitByPosition(int[] position) {
        //TODO
        return null;
    }

    public NonCombatUnit getNonCombatUnitByPosition(int[] position) {
        //TODO
        return null;
    }

    public City getCityByPosition(int[] position) {
        //TODO
        return null;
    }

    public Tile getTileByPosition(int[] position) {
        //TODO
        return null;
    }

    public boolean isPositionValid(int[] position) {
        //TODO
        return false;
    }


    private Stack<Tile> getShortestPath(Tile originTile, Tile destinationTile) {
        HashMap<Tile, Integer> distance = new HashMap<Tile, Integer>();
        HashMap<Tile, Boolean> mark = new HashMap<Tile, Boolean>();
        HashMap<Tile, Tile> previousInShortestPath = new HashMap<Tile, Tile>();
        distance.put(originTile, 0);
        previousInShortestPath.put(originTile, null);
        mark.put(originTile, false);
        LinkedList <Tile> BFSQueue = new LinkedList<>();
        BFSQueue.add(originTile);
        while (!BFSQueue.isEmpty()) {
            Tile currentVertex = BFSQueue.poll();
            if (mark.get(currentVertex)) continue;
            mark.put(currentVertex, true);
            if (currentVertex.equals(destinationTile)) break;
            ArrayList<Tile> adjacentVertices = currentVertex.getAdjacentTiles();
            ArrayList<Boolean> isRiver = currentVertex.getIsRiver();
            for (int i = 0; i < adjacentVertices.size(); i++) {
                Tile adjacentVertex = adjacentVertices.get(i);
                if (adjacentVertex.isUnmovable()) continue;
                if (mark.get(adjacentVertex)) continue;
                if (GameController.getInstance().getCivilization().isInFog()) continue;
                int newDistance = distance.get(currentVertex) + 1;
                if (distance.get(adjacentVertex) != null && newDistance >= distance.get(adjacentVertex)) continue;
                distance.put(adjacentVertex, newDistance);
                mark.put(adjacentVertex, false);
                previousInShortestPath.put(adjacentVertex, currentVertex);
            }
        }
        Stack<Tile> ans = new Stack<Tile>();
        if (mark.get(destinationTile) == null) return null;
        Tile pointerTile = destinationTile;
        while (pointerTile != null) {
            ans.push(pointerTile);
            pointerTile = previousInShortestPath.get(pointerTile);
        }
        return ans;
    }

    private int calculateMotionCost(Tile originTile, Tile destinationTile) {
        return destinationTile.getMovingCost();
        //TODO handle river and road or railroad on river;
    }

   private void moveToAdjacent(Unit unit, Tile tile) {
        Tile currentTile = unit.getPosition();
        if ((unit instanceof CombatUnit) && currentTile.getCombatUnit().equals(unit)) currentTile.setCombatUnit((CombatUnit)null);
        else if ((unit instanceof NonCombatUnit) && currentTile.getNonCombatUnit().equals(unit)) currentTile.setNonCombatUnit((NonCombatUnit)null);
        unit.setPosition(tile);
        if (unit instanceof CombatUnit && tile.getCombatUnit() == null) tile.setCombatUnit((CombatUnit)unit);
        else if (unit instanceof NonCombatUnit && tile.getNonCombatUnit() == null) tile.setNonCombatUnit((NonCombatUnit)unit);

   }

    private void completeMoveForOneTurn(Unit unit) {
        Stack<Tile> path = unit.getPath();
        if (path == null) return;
        while (!unit.getPath().isEmpty()) {
            Tile tile = path.pop();
            if (tile == unit.getPosition()) continue;
            int newMotionPoint = unit.getMotionPoint();
            int motionCost = calculateMotionCost(unit.getPosition(), tile);
            newMotionPoint = Math.max(0, unit.getMotionPoint() - motionCost);
            unit.setMotionPoint(newMotionPoint);
            moveToAdjacent(unit, tile);
        }

        //TODO handle multi-step movement
    }

    public String moveUnit(Unit unit, int[] destination) {
        String ans = isMoveValid(unit, destination);
        if (!ans.equals("true")) return ans;
        Tile originTile = unit.getPosition();
        Tile destinationTile = getTileByPosition(destination);
        Stack<Tile> path = getShortestPath(originTile, destinationTile);
        if (path == null || path.size() == 0 || path.get(0) != originTile || path.get(path.size() - 1) != destinationTile) return "no valid path";
        unit.setPath(path);
        completeMoveForOneTurn(unit);

        //TODO
        return "success";
    }

    private String isMoveValid(Unit unit, int[] destination) {
        Tile destinationTile = getTileByPosition(destination);
        if (!isPositionValid(destination)) return "invalid destination";
//        else if (GameController.getInstance().getCivilization().isFogOfWar(destinationTile)) return "fog of war";
        else if (unit.getPosition() == destinationTile) return "already at the same tile";
        else if (unit instanceof CombatUnit && destinationTile.getCombatUnit() != null) return "destination occupied";
        else if (unit instanceof NonCombatUnit && destinationTile.getNonCombatUnit() != null)
            return "destination occupied";
        return "true";
    }

    public ArrayList<Tile> getVisibleTiles(Tile tile) {
        HashSet<Tile> ans = new HashSet<>();
        ans.add(tile);
        for (Tile adjacentTile : tile.getAdjacentTiles()) {
            ans.add(adjacentTile);
            if (!adjacentTile.isBlock()) {
                for (Tile visibleTile : adjacentTile.getAdjacentTiles()) {
                    ans.add(visibleTile);
                }
            }
        }
        return new ArrayList<>(ans);
    }

    public ArrayList<Tile> getVisibleTiles(Unit unit) {
        return getVisibleTiles(unit.getPosition());
    }

    //TODO: exit from fog of war


    public void makeUnitSleep(Unit unit) {
        //TODO
    }

    public void alertUnit(Unit unit) {
        //TODO
    }

    public void fortifyUnit(Unit unit) {
        //TODO
    }

    public void fullyFortifyUnit(Unit unit) {
        //TODO
    }

    public void garrisonUnit(Unit unit) {
        //TODO
    }

    public void setupUnit(Unit unit) {
        //TODO
    }

    public void cancelMission(Unit unit) {
        //TODO
    }

    public void wakeUnit(Unit unit) {
        //TODO
    }

    public void removeUnit(Unit unit) {
        //TODO
    }

    public void build(Worker worker, String improvement) {
        int[] position = {worker.getPosition().getX(),worker.getPosition().getX()};
        Tile tile = GameController.getInstance().getGame().getMainGameMap().getTileByXY(position[0], position[1]);
        Civilization civilization = GameController.getInstance().getCivilization();
        civilization.addWork(new Work(tile.getCity(),tile,worker,"Build Improvement", Improvement.getAllImprovements().get(improvement)));
    }

    public void foundCity(Settler settler, String name) {

        Tile position = settler.getPosition();
        Civilization civilization = GameController.getInstance().getCivilization();
        civilization.addCities(name,civilization,position);
        civilization.removeUnit(settler);

    }


    public void removeItem(int[] position, String item) {
        //TODO
    }

    public void repair(int[] position) {
        //TODO
    }


    public void cityFortify(City city) {
        //TODO
    }

    public void purchaseTile(City city, int[] position) {
        //TODO
    }

    public void chooseCityProduction(City city, int index) {
        //TODO
    }

    public void updateCivilizationAttributes() {
        //TODO
    }

    public void research(Technology technology) {
        //TODO
    }
}