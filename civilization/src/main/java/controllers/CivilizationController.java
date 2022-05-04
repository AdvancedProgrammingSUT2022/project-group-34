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
    private final static int INF = 100000;
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
            if (getTileByPosition(position).getCombatUnit() == null)
                return "no such unit";
            else return unitType;
        } else if (unitType.equals("noncombat")) {
            if (getTileByPosition(position).getNonCombatUnit() == null)
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

    private Stack<Tile> extractPathFromParentsHashMap(HashMap<Tile, Tile> previousInShortestPath, Tile destinationTile) {
        Stack<Tile> ans = new Stack<>();
        Tile pointerTile = destinationTile;
        while (pointerTile != null) {
            ans.push(pointerTile);
            pointerTile = previousInShortestPath.get(pointerTile);
        }
        return ans;
    }

    private HashMap<Tile, Integer> doBFSAndReturnDistances(Tile originTile) {
        HashMap<Tile, Integer> distance = new HashMap<>();
        HashMap<Tile, Boolean> mark = new HashMap<>();
        distance.put(originTile, 0);
        mark.put(originTile, false);
        LinkedList<Tile> BFSQueue = new LinkedList<>();
        BFSQueue.add(originTile);
        while (!BFSQueue.isEmpty()) {
            Tile currentVertex = BFSQueue.poll();
            if (mark.get(currentVertex)) continue;
            mark.put(currentVertex, true);
            ArrayList<Tile> adjacentVertices = currentVertex.getAdjacentTiles();
            for (Tile adjacentVertex : adjacentVertices) {
                if (adjacentVertex.isUnmovable() || mark.get(adjacentVertex)) continue;
                int newDistance = distance.get(currentVertex) + 1;
                if (distance.get(adjacentVertex) == null || distance.get(adjacentVertex) > newDistance) {
                    distance.put(adjacentVertex, newDistance);
                    mark.put(adjacentVertex, false);
                }
            }
        }
        return distance;
    }

    private Object doDijkstra(Tile originTile, Tile destinationTile, int motionPointLimit, boolean returnThePath) {
        HashMap<Tile, Integer> distance = new HashMap<>();
        HashMap<Tile, Boolean> mark = new HashMap<>();
        HashMap<Tile, Tile> previousInShortestPath = new HashMap<>();
        distance.put(originTile, 0);
        previousInShortestPath.put(originTile, null);
        mark.put(originTile, false);
        while (true) {
            Tile currentVertex = null;
            for (Tile tile : mark.keySet()) {
                if (mark.get(tile)) continue;
                else if (currentVertex == null) currentVertex = tile;
                else if (distance.get(tile) < distance.get(currentVertex)) currentVertex = tile;
            }
            if (currentVertex == null) break;

            mark.put(currentVertex, true);
            if (distance.get(currentVertex) >= motionPointLimit) break;
            if (returnThePath && currentVertex.equals(destinationTile)) break;
            ArrayList<Tile> adjacentVertices = currentVertex.getAdjacentTiles();
            ArrayList<Boolean> isRiver = currentVertex.getIsRiver();
            for (int i = 0; i < adjacentVertices.size(); i++) {
                Tile adjacentVertex = adjacentVertices.get(i);
                if (adjacentVertex.isUnmovable() || mark.get(adjacentVertex)) continue;
                if (GameController.getInstance().getCivilization().isInFog(adjacentVertex)) continue;
                int newDistance = distance.get(currentVertex) + calculateMotionCost(currentVertex, adjacentVertex);
                if (isRiver.get(i) && !returnThePath) newDistance = motionPointLimit;
                if (distance.get(adjacentVertex) != null && newDistance >= distance.get(adjacentVertex)) continue;
                distance.put(adjacentVertex, newDistance);
                mark.put(adjacentVertex, false);
                previousInShortestPath.put(adjacentVertex, currentVertex);
            }
        }
        if (mark.get(destinationTile) == null) return null;
        else if (!returnThePath) return distance;
        else return extractPathFromParentsHashMap(previousInShortestPath, destinationTile);
    }

    private int calculateMotionCost(Tile originTile, Tile destinationTile) {
        return destinationTile.getMovingCost();
        //TODO handle river and road or railroad on river;
    }

    private void moveToAdjacent(Unit unit, Tile tile) {
        Tile currentTile = unit.getPosition();
        if ((unit instanceof CombatUnit) && currentTile.getCombatUnit().equals(unit))
            currentTile.setCombatUnit((CombatUnit) null);
        else if ((unit instanceof NonCombatUnit) && currentTile.getNonCombatUnit().equals(unit))
            currentTile.setNonCombatUnit((NonCombatUnit) null);
        unit.setPosition(tile);
        if (unit instanceof CombatUnit && tile.getCombatUnit() == null) tile.setCombatUnit((CombatUnit)unit);
        else if (unit instanceof NonCombatUnit && tile.getNonCombatUnit() == null) tile.setNonCombatUnit((NonCombatUnit)unit);
        //TODO exit from fog of war
    }

    private boolean isPathValid(Stack<Tile> path, Tile originTile, Tile destinationTile, Unit unit) {
        if (path == null || path.size() == 0 || path.get(0) != originTile || path.get(path.size() - 1) != destinationTile) return false;
        else if (unit instanceof CombatUnit && destinationTile.getCombatUnit() != null) return false;
        else if (unit instanceof NonCombatUnit && destinationTile.getNonCombatUnit() != null) return false;
        return true;
    }

    private void cancelMove(Unit unit) {
        unit.setDestination(null);
    }

    private boolean continueMoveForOneTurn(Unit unit) {
        if (unit.getDestination() == null) return false;
        HashMap<Tile, Integer> distancesFromDestination = doBFSAndReturnDistances(unit.getDestination());
        Tile temporaryDestination = unit.getPosition();
        HashMap<Tile, Integer> distancesFromOriginByMP = (HashMap<Tile, Integer>)doDijkstra(unit.getPosition(), unit.getDestination(), unit.getMotionPoint(), false);
        for (Tile tile : distancesFromOriginByMP.keySet()) {
            if (tile == null) continue;
            if (unit instanceof CombatUnit && tile.getCombatUnit() != null) continue;
            if (unit instanceof NonCombatUnit && tile.getNonCombatUnit() != null) continue;
            Integer tileDistance = distancesFromDestination.get(tile);
            if (tileDistance != null && tileDistance < distancesFromDestination.get(temporaryDestination)) temporaryDestination = tile;
        }
        if (temporaryDestination == null || temporaryDestination == unit.getPosition()) {
            cancelMove(unit);
            return false;
        }
        Stack<Tile> path = (Stack<Tile>)doDijkstra(unit.getPosition(), temporaryDestination, unit.getMotionPoint(), true);
        continueMoveByPath(unit, path);
        return true;
    }

    private void continueMoveByPath(Unit unit, Stack<Tile> path) {
        if (path == null) return;
        if (!isPathValid(path, unit.getPosition(), unit.getDestination(), unit) || path.size() == 0) {
            cancelMove(unit);
            return;
        }
        while (unit.getMotionPoint() > 0 && !path.isEmpty()) {
            Tile tile = path.pop();
            if (tile == unit.getPosition()) continue;
            int newMotionPoint = 0;
            int motionCost = calculateMotionCost(unit.getPosition(), tile);
            newMotionPoint = Math.max(0, unit.getMotionPoint() - motionCost);
            //TODO handle river
            unit.setMotionPoint(newMotionPoint);
            moveToAdjacent(unit, tile);
        }
    }

    public String moveUnit(Unit unit, int[] destination) {
        String ans = isMoveValid(unit, destination);
        if (!ans.equals("true")) return ans;
        Tile originTile = unit.getPosition();
        Tile destinationTile = getTileByPosition(destination);
        Stack<Tile> path = (Stack<Tile>)doDijkstra(originTile, destinationTile, INF, true);
        if (!isPathValid(path, originTile, destinationTile, unit)) return "no valid path";
        unit.setDestination(destinationTile);
        if (!continueMoveForOneTurn(unit)) return "no valid path";
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

    private void continueMoves() {
        for (Unit unit : GameController.getInstance().getCivilization().getUnits()) {
            continueMoveForOneTurn(unit);
        }
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
        int[] position = {worker.getPosition().getX(), worker.getPosition().getX()};
        Tile tile = GameController.getInstance().getGame().getMainGameMap().getTileByXY(position[0], position[1]);
        Civilization civilization = GameController.getInstance().getCivilization();
        civilization.addWork(new Work(tile.getCity(), tile, worker, "Build Improvement", Improvement.getAllImprovements().get(improvement)));
    }

    public String foundCity(Settler settler, String name) {
        Tile position = settler.getPosition();
        ArrayList<Tile> adjacentTiles = position.getAdjacentTiles();
        Civilization civilization = GameController.getInstance().getCivilization();

        //Checks if settler is far enough from another civilization borders to found city
        for (Tile adjacentTile : adjacentTiles) {
            if (adjacentTile.getCity() != null &&
                    !civilization.getCities().contains(adjacentTile.getCity()))
                return "too close";
        }

        ArrayList<Tile> territory = new ArrayList<>(adjacentTiles);
        territory.add(position);
        civilization.addCities(new City(name, civilization, position, territory));
        civilization.removeUnit(settler);
        return "ok";
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