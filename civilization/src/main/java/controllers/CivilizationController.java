//besmellah

package controllers;

import java.awt.print.Book;
import java.util.
import models.units.Unit;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.City;
import models.tile.Tile;
import models.Technology;

public class CivilizationController {
    private static CivilizationController instance = null;

    public static CivilizationController getInstance() {
        if (instance == null) instance = new CivilizationController;
        return instance;
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


    private ArrayList<Tile> getShortestPath(Tile originTile, Tile destinationTile) {
        HashMap<Tile, Integer> distance = new HashMap<Tile, Integer>();
        HashMap<Tile, Boolean> mark = new HashMap<Tile, Boolean>();
        HashMap<Tile, Tile> previousInShortestPath = new HashMap<Tile, Tile>();
        distance.put(originTile, 0);
        previousInShortestPath.put(originTile, null);
        mark.put(originTile, false);
        Queue <Tile> BFSQueue = new Queue<Tile>();
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
                if (GameController.getInstance().getCivilization().isFogOfWar()) continue;
                int newDistance = distance.get(currentVertex) + 1;
                if (distance.get(adjacentVertex) != null && newDistance >= distance.get(adjacentVertex)) continue;
                distance.put(adjacentVertex, newDistance);
                mark.put(adjacentVertex, false);
                previousInShortestPath.put(adjacentVertex, currentVertex);
            }
        }
        ArrayList<Tile> ans = new ArrayList<Tile>();
        if (mark.get(destinationTile) == null) return null;
        Tile pointerTile = destinationTile;
        while (pointerTile != null) {
            ans.add(0, pointerTile);
            pointerTile = previousInShortestPath.get(pointerTile);
        }
        return ans;
    }

    public String moveUnit(Unit unit, int[] destination) {
        String ans = isMoveValid(unit, destination);
        if (!ans.equals("true")) return ans;
        Tile originTile = unit.getPosition();
        Tile destinationTile = getTileByPosition(destination);
        ArrayList<Tile> path = getShortestPath(originTile, destinationTile);
        if (path == null || path.size() == 0 || path.get(0) != originTile || path.get(path.size() - 1) != destinationTile) return "no valid path";
        //TODO
        return "success";
    }

    private String isMoveValid(Unit unit, int[] destination) {
        Tile destinationTile = getTileByPosition(destination);
        if (!isPositionValid(destination)) return "invalid destination";
        else if (GameController.getInstance().getCivilization().isFogOfWar(destinationTile)) return "fog of war";
        else if (unit.getPosition() == destinationTile) return "already at the same tile";
        else if (unit instanceof CombatUnit && destinationTile.getCombatUnit() != null) return "destination occupied";
        else if (unit instanceof NonCombatUnit && destinationTile.getNonCombatUnit != null) return "destination occupied";
        return "true";
    }


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


    public void foundCity(Settler settler) {
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

    public void build(int[] position, String improvement) {
        //TODO
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