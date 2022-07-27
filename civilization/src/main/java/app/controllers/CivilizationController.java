//besmellah

package app.controllers;

import app.models.*;
import app.models.map.CivilizationMap;
import app.models.map.GameMap;
import app.models.tile.*;
import app.models.unit.*;

import java.nio.file.ClosedFileSystemException;
import java.util.*;
import java.util.function.Predicate;

public class CivilizationController {
    private final static int INF = 100000;

    private static CivilizationController instance = null;

    private CivilizationController() {
    }

    public static CivilizationController getInstance() {
        if (instance == null) instance = new CivilizationController();
        return instance;
    }

    public String finishResearch(Civilization civilization) {
        Technology technology = civilization.getStudyingTechnology();
        if (technology == null) {
            return "You are not studying any technology";
        }
        technology.setResearching(false);
        technology.setResearched (true );
        civilization.getCivilizationResearchedTechnologies().put(TechnologyEnum.getTechnologyEnumByName(technology.getName()),technology);
        civilization.setStudyingTechnology(null);
        return "Research on " + technology.getName() + " technology is complete.";

    }

    public int researchTechnology(Civilization civilization, String name) {
        TechnologyEnum technologyEnum = TechnologyEnum.getTechnologyEnumByName(name);
        if (technologyEnum == null) return -1;

        if (civilization.getStudyingTechnology() != null
        && civilization.getStudyingTechnology().getName().equals(technologyEnum.getName())){
            finishResearch(civilization);
            return 0;
        }

        Technology technology = civilization.getCivilizationNotResearchedTechnologies().get(technologyEnum);
        if (technology == null) return -2;
        civilization.getCivilizationNotResearchedTechnologies() .remove(technologyEnum);
        civilization.getCivilizationResearchedTechnologies()    .put(technologyEnum,technology);

        technology.setResearching(false);
        technology.setResearched (true );

        return 0;
    }


    public String selectUnit(int[] position, String unitType) {
        if (!isPositionValid(position))
            return "invalid position";

        Unit unit = null;
        if (unitType.equals("combat"))
            unit = getTileByPosition(position).getCombatUnit();
        else if (unitType.equals("noncombat"))
            unit = getTileByPosition(position).getNonCombatUnit();

        if (unit == null)
            return "no such unit";
        else if (!GameController.getInstance().getCivilization().getUnits().contains(unit))
            return "not yours";
        else return unitType;
    }

    public CombatUnit getCombatUnitByPosition(int[] position) {
        Tile tile = getTileByPosition(position);
        if (tile == null) return null;
        else return tile.getCombatUnit();
    }

    public NonCombatUnit getNonCombatUnitByPosition(int[] position) {
        Tile tile = getTileByPosition(position);
        if (tile == null) return null;
        else return tile.getNonCombatUnit();
    }

    public City getCityByPosition(int[] position) {
        Tile tile = getTileByPosition(position);
        if (tile == null) return null;
        else return tile.getCity();
    }

    public Tile getTileByPosition(int[] position) {
        GameMap gameMap = GameController.getInstance().getGame().getMainGameMap();
        return gameMap.getTileByXY(position[0], position[1]);
    }

    public boolean isPositionValid(int[] position) {
        Tile tile = getTileByPosition(position);
        return tile != null;
    }

    private Stack<Tile> extractPathFromParentsHashMap(HashMap<Tile, Tile> previousInShortestPath, Tile destinationTile) {
        Stack<Tile> ans = new Stack<>();
        Tile pointerTile = destinationTile;
        while (pointerTile != null) {
            ans.push(pointerTile);
            pointerTile = previousInShortestPath.get(pointerTile);
        }
        System.out.println("EXTRACT " + ans + " " + destinationTile.getX() + " " + destinationTile.getY() + " " + previousInShortestPath);
        return ans;
    }


    public HashMap<Tile, Integer> doBFSAndReturnDistances(Tile originTile, boolean canMoveOnUnmovable) {
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
                Boolean markGet = mark.get(adjacentVertex);
                if ((adjacentVertex.isUnmovable() && !canMoveOnUnmovable) || (markGet != null && markGet)) continue;
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

            System.out.println("SALAm" + currentVertex + " " + distance.get(currentVertex));
            System.out.println(currentVertex.getX() + " " + currentVertex.getY());

            mark.put(currentVertex, true);
            if (distance.get(currentVertex) >= motionPointLimit) break;
            if (returnThePath && currentVertex.equals(destinationTile)) break;
            ArrayList<Tile> adjacentVertices = currentVertex.getAdjacentTiles();
            ArrayList<Boolean> isRiver = currentVertex.getIsRiver();
            for (int i = 0; i < adjacentVertices.size(); i++) {
                Tile adjacentVertex = adjacentVertices.get(i);
                Boolean markGet = mark.get(adjacentVertex);
//                System.out.println(mark.get(adjacentVertex));
                if (adjacentVertex.isUnmovable() || (markGet != null && markGet)) continue;
                if (GameController.getInstance().getCivilization().isInFog(adjacentVertex)) continue;
                int newDistance = distance.get(currentVertex) + calculateMotionCost(currentVertex, adjacentVertex);
                if (isRiver.get(i) && !returnThePath) newDistance = motionPointLimit;
                if (distance.get(adjacentVertex) != null && newDistance >= distance.get(adjacentVertex)) continue;
                distance.put(adjacentVertex, newDistance);
                mark.put(adjacentVertex, false);
                previousInShortestPath.put(adjacentVertex, currentVertex);
            }
        }
        System.out.println(distance + " " + mark + " " + motionPointLimit);
        boolean flag = true;
        for (Tile tile: mark.keySet()) {
                System.out.println(tile.getX() + " " + tile.getY() + " " + destinationTile.getX() + " " + destinationTile.getY());
            if (tile.equals(destinationTile)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            System.out.println(":||||||||");
            return new HashMap<>();
        }
        else if (!returnThePath) {
            System.out.println(":-");
            return distance;
        }
        else {
            System.out.println(":(");
            return extractPathFromParentsHashMap(previousInShortestPath, destinationTile);
        }
    }

    private int calculateMotionCost(Tile originTile, Tile destinationTile) {
        return destinationTile.getMovingCost();
        //TODO handle river and road or railroad on river;
    }

    private void moveToAdjacent(Unit unit, Tile tile) {
        Tile originTile = unit.getPosition();
        forcedMove(unit, tile);
        int newMotionPoint = 0;
        int motionCost = calculateMotionCost(unit.getPosition(), tile);
        newMotionPoint = Math.max(0, unit.getMotionPoint() - motionCost);
        if (isRiverBetween(originTile, tile)) newMotionPoint = 0;
        unit.setMotionPoint(newMotionPoint);
    }

    public void forcedMove(Unit unit, Tile tile) {
        Tile currentTile = unit.getPosition();
        if ((unit instanceof CombatUnit) && currentTile.getCombatUnit().equals(unit))
            currentTile.setCombatUnit(null);
        else if ((unit instanceof NonCombatUnit) && Objects.equals(currentTile.getNonCombatUnit(), unit))
            currentTile.setNonCombatUnit(null);
        unit.setPosition(tile);
        if (unit instanceof CombatUnit && tile.getCombatUnit() == null) tile.setCombatUnit((CombatUnit) unit);
        else if (unit instanceof NonCombatUnit && tile.getNonCombatUnit() == null)
            tile.setNonCombatUnit((NonCombatUnit) unit);
        reveal(getVisibleTiles(unit));
    }

    private boolean isPathValid(Stack<Tile> path, Tile originTile, Tile destinationTile, Unit unit) {
        if (path == null || path.size() == 0 || path.get(0) != destinationTile || path.get(path.size() - 1) != originTile) {
            System.out.println("HOY");
            return false;
        }
        else if (unit instanceof CombatUnit && destinationTile.getCombatUnit() != null) {
            System.out.println("HEY");
            return false;
        }
        else return !(unit instanceof NonCombatUnit) || destinationTile.getNonCombatUnit() == null;
    }

    private void cancelMove(Unit unit) {
        unit.setDestination(null);
    }

    private boolean continueMoveForOneTurn(Unit unit) {
        if (unit == null) return false;
        if (unit.getDestination() == null) return false;
        System.out.println("dest: " + unit.getDestination());
        HashMap<Tile, Integer> distancesFromDestination = doBFSAndReturnDistances(unit.getDestination(), false);
        System.out.println("BFS DONE. DISTANCES: " + distancesFromDestination);
        Tile temporaryDestination = unit.getPosition();
        HashMap<Tile, Integer> distancesFromOriginByMP = (HashMap<Tile, Integer>) doDijkstra(unit.getPosition(), unit.getDestination(), unit.getMotionPoint(), false);
        System.out.println("DIJKSTRA: " + distancesFromOriginByMP);
        for (Tile tile : distancesFromOriginByMP.keySet()) {
            if (tile == null) continue;
            if (unit instanceof CombatUnit && tile.getCombatUnit() != null) continue;
            if (unit instanceof NonCombatUnit && tile.getNonCombatUnit() != null) continue;
            Integer tileDistance = distancesFromDestination.get(tile);
            System.out.println(tileDistance + " " + distancesFromDestination.get(temporaryDestination));
            if (tileDistance != null && distancesFromDestination.get(temporaryDestination) != null && tileDistance <= distancesFromDestination.get(temporaryDestination))
                temporaryDestination = tile;
        }
        if (temporaryDestination == null || temporaryDestination == unit.getPosition()) {
            System.out.println(temporaryDestination.getX() + " " + temporaryDestination.getY() + " " + unit.getPosition());
            cancelMove(unit);
            return false;
        }
        Stack<Tile> path = (Stack<Tile>) doDijkstra(unit.getPosition(), temporaryDestination, unit.getMotionPoint(), true);
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
            moveToAdjacent(unit, tile);
        }
    }

    public String moveUnit(Unit unit, int[] destination) {
        String ans = isMoveValid(unit, destination);
        if (!ans.equals("true")) return ans;
        Tile originTile = unit.getPosition();
        Tile destinationTile = getTileByPosition(destination);
        Stack<Tile> path = (Stack<Tile>) doDijkstra(originTile, destinationTile, INF, true);
        if (!isPathValid(path, originTile, destinationTile, unit)) {
            System.out.println("SALAM!!!!!");
            System.out.println(path);
            for (Tile tile: path) {
                System.out.println(tile.getX() + " " + tile.getY());
            }
            System.out.println(originTile.getX() + " " + originTile.getY() + " " + destinationTile.getX() + " " + destinationTile.getY());
            return "no valid path";
        }
        unit.setDestination(destinationTile);
        if (!continueMoveForOneTurn(unit)) {
            System.out.println("?!");
            return "no valid path";
        }
        return "success";
    }

    public String isMoveValid(Unit unit, int[] destination) {
        Tile destinationTile = getTileByPosition(destination);
        if (!isPositionValid(destination)) return "invalid destination";
        else if (GameController.getInstance().getCivilization().isInFog(destinationTile)) return "fog of war";
        else if (unit.getPosition() == destinationTile) return "already at the same tile";
        else if (unit instanceof CombatUnit && destinationTile.getCombatUnit() != null) return "destination occupied";
        else if (unit instanceof NonCombatUnit && destinationTile.getNonCombatUnit() != null)
            return "destination occupied";
        else if (destinationTile.isUnmovable()) return "destination unmovable";
        return "true";
    }

    public ArrayList<AbstractTile> getVisibleTiles(Tile tile) {
        HashSet<Tile> ans = new HashSet<>();
        ans.add(tile);
        for (Tile adjacentTile : tile.getAdjacentTiles()) {
            ans.add(adjacentTile);
            if (!adjacentTile.isBlock() || tile.isBlock() || tile.getTerrain() == Terrain.Hills) {
                for (Tile visibleTile : adjacentTile.getAdjacentTiles()) {
                    ans.add(visibleTile);
                }
            }
        }
        return new ArrayList<>(ans);
    }

    private void continueMoves(Civilization civilization) {
        for (Unit unit : civilization.getUnits()) {
            continueMoveForOneTurn(unit);
        }
    }

    public boolean isRiverBetween(Tile tile1, Tile tile2) {
        if (tile1 == null || tile2 == null) return false;

        if (!tile1.getAdjacentTiles().contains(tile2)) return false;
        return tile1.getIsRiver().get(tile1.getAdjacentTiles().indexOf(tile2));

    }

    public ArrayList<AbstractTile> getVisibleTiles(Unit unit) {
        return getVisibleTiles(unit.getPosition());
    }

    public void reveal(int x, int y) {
        Tile tile = getTileByPosition(new int[]{x, y});
        reveal(tile);
    }

    public void reveal(ArrayList<AbstractTile> tiles) {
        for (AbstractTile tile : tiles) {
            reveal(tile);
        }
    }

    public void reveal(AbstractTile tile) {
        CivilizationMap map = GameController.getInstance().getCivilization().getPersonalMap();
        map.setTileByXY(tile.getX(), tile.getY(), new VisibleTile(tile, false));
        ArrayList<AbstractTile> tileList = new ArrayList<>();
        tileList.add(tile);
        map.addTransparentTiles(tileList);
    }


    public String garrisonCity(CombatUnit unit) {
        City city;
        if (unit == null)
            return "not military";
        else if ((city = unit.getPosition().getCity()) == null)
            return "no city";
        else if (unit.getDestination() != null && !unit.getPosition().equals(unit.getDestination()))
            return "in movement";
        unit.makeUnitAwake();
        unit.setGarrisonCity(city);
        city.setGarrison(true);
        return "ok";
    }

    public void deleteUnit(Unit unit) {
        Civilization civilization = GameController.getInstance().getCivilization();
        civilization.setGold(civilization.getGold() + unit.getCost() / 10);
        civilization.removeUnit(unit);
    }


    public void build(Worker worker, String improvement) {
        Tile tile = worker.getPosition();
        Civilization civilization = GameController.getInstance().getCivilization();
        tile.setImprovement(null);
        if (civilization.getWorkByTile(tile) != null)
            civilization.getWorks().remove(civilization.getWorkByTile(tile));

        if (improvement.equals("rail"))
            civilization.addWork(new Work(tile, worker, "build rail", 3));
        else if (improvement.equals("road"))
            civilization.addWork(new Work(tile, worker, "build road", 3));
        else {
            if (improvement.equals("Farm") || improvement.equals("Mine")) {
                if (tile.getFeature().equals(Feature.Forests))
                    civilization.addWork(new Work(tile, worker, "build improvement", improvement, 10));
                else if (tile.getFeature().equals(Feature.Jungle))
                    civilization.addWork(new Work(tile, worker, "build improvement", improvement, 13));
                else if (tile.getFeature().equals(Feature.Marsh))
                    civilization.addWork(new Work(tile, worker, "build improvement", improvement, 12));
                else
                    civilization.addWork(new Work(tile, worker, "build improvement", improvement, 6));
            } else
                civilization.addWork(new Work(tile, worker, "build improvement", improvement, 6));
        }
    }

    public ArrayList<String> getPossibleImprovements(Tile tile) {
        ArrayList<ImprovementEnum> improvements;
        if (tile.getFeature() != null) improvements = Improvement.getImprovementsByFeature(tile.getFeature());
        else improvements = Improvement.getImprovementsByTerrain(tile.getTerrain());

        improvements.removeIf(improvement -> !GameController.getInstance().getCivilization().
                getCivilizationResearchedTechnologies().containsValue(
                        Improvement.getAllImprovements().get(improvement).getRequiredTechnology()));

        ArrayList<String> improvementsString = new ArrayList<>();
        for (ImprovementEnum improvement : improvements) {
            improvementsString.add(improvement.name);
        }
        improvementsString.add("road");
        improvementsString.add("rail");

        return improvementsString;
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

        //Checks if city name is new
        for (Civilization civilization1 : GameController.getInstance().getGame().getCivilizations()) {
            if (civilization1.getCityByName(name) != null) return "duplicate name";
        }

        ArrayList<Tile> territory = new ArrayList<>(adjacentTiles);
        territory.add(position);
        City city = new City(name, civilization, position, territory);
        for (Tile tile : territory)
            tile.setCity(city);
        civilization.addCities(city);
        civilization.setMainCapital(city);
        civilization.setCurrentCapital(city);
        position.setCity(city);
        civilization.removeUnit(settler);
        System.out.println("Done");
        getTileByPosition(new int[]{settler.getPosition().getX(), settler.getPosition().getY()}).setNonCombatUnit(null);
        return "ok";
    }

    public String removeFeature(NonCombatUnit selectedNonCombatUnit, String type) {
        Tile tile;
        if (!(selectedNonCombatUnit instanceof Worker))
            return "not worker";
        else if (selectedNonCombatUnit.getDestination() != null && !selectedNonCombatUnit.getPosition().equals(selectedNonCombatUnit.getDestination()))
            return "in movement";
        else if ((tile = selectedNonCombatUnit.getPosition()).getFeature() != Feature.Forests ||
                tile.getFeature() != Feature.Jungle || tile.getFeature() != Feature.Marsh)
            return "irremovable feature";
        else {
            selectedNonCombatUnit.makeUnitAwake();
            tile.setImprovement(null);
            Work work = GameController.getInstance().getCivilization().getWorkByTile(tile);
            if (work == null) {
                if (type.equals("jungle"))
                    work = new Work(selectedNonCombatUnit.getPosition(), (Worker) selectedNonCombatUnit, "remove feature", 7);
                else if (type.equals("forest"))
                    work = new Work(selectedNonCombatUnit.getPosition(), (Worker) selectedNonCombatUnit, "remove feature", 4);
                else
                    work = new Work(selectedNonCombatUnit.getPosition(), (Worker) selectedNonCombatUnit, "remove feature", 6);
                GameController.getInstance().getCivilization().addWork(work);
            } else {
                if (type.equals("jungle"))
                    work.changeWork((Worker) selectedNonCombatUnit, 7, "remove feature");
                else if (type.equals("forest"))
                    work.changeWork((Worker) selectedNonCombatUnit, 4, "remove feature");
                else
                    work.changeWork((Worker) selectedNonCombatUnit, 6, "remove feature");
            }
            return "ok";
        }
    }

    public String repair(NonCombatUnit selectedNonCombatUnit) {
        Tile tile;
        if (!(selectedNonCombatUnit instanceof Worker))
            return "not worker";
        else if (selectedNonCombatUnit.getDestination() != null && !selectedNonCombatUnit.getPosition().equals(selectedNonCombatUnit.getDestination()))
            return "in movement";
        else if ((tile = selectedNonCombatUnit.getPosition()).getImprovement() == null)
            return "no improvement";
        else {
            selectedNonCombatUnit.makeUnitAwake();
            Work work = GameController.getInstance().getCivilization().getWorkByTile(tile);
            if (work == null)
                GameController.getInstance().getCivilization().addWork(new Work(tile, (Worker) selectedNonCombatUnit, "repair", 3));
            else
                work.changeWork((Worker) selectedNonCombatUnit, 3, "repair");
            return "ok";
        }
    }



    public void purchaseTile(City city, Tile tile) {
        city.addTerritory(tile);
        tile.setCity(city);
        Civilization civilization = GameController.getInstance().getCivilization();
        civilization.setGold(civilization.getGold()-50);
    }

    public ArrayList<UnitEnum> getProducibleUnits(){
        ArrayList<UnitEnum> unitEnums = new ArrayList<>();
        Civilization civilization = GameController.getInstance().getCivilization();

        unitEnums.addAll(civilization.getProducibleUnitEnums());
        unitEnums.removeIf(unitEnum -> !civilization.getCivilizationUsableUnits().contains(unitEnum));

        return unitEnums;
    }

    public void cityFortify(City city) {
        //TODO
    }



    public void updateCivilizationAttributes() {
        //TODO
    }

    public void research(Technology technology) {
        //TODO
    }

    public void updateCivilization(Civilization civilization) {

        updateNumberOfResources(civilization);

        civilization.setHappiness();
        civilization.updateGold();

        for (City city : civilization.getCities()) {
            updateCity(city);
        }

        for (Work work : civilization.getWorks()) {
            if (work.update()) {
                work.doWork();
                //TODO : Notification
            }
        }

        Technology technology = civilization.getStudyingTechnology();
        if (technology != null)
            if (technology.updateTechnology(civilization.getCivilizationResearchedTechnologies()) == 1) {
                civilization.getCivilizationResearchedTechnologies().put(TechnologyEnum.getTechnologyEnumByName(technology.getName()),technology);
            //TODO : Notification
            }


        continueMoves(civilization);

        CivilizationController.getInstance().updateTransparentTiles(civilization);
        CivilizationController.getInstance().updatePersonalMap(civilization, GameController.getInstance().getGame().getMainGameMap());
    }

/*    private ArrayList<AbstractTile> getAllVisibleTiles(Civilization civilization) {
        Set<AbstractTile> visibleMap = new HashSet<>();
        for (City city : civilization.getCities()) {
            visibleMap.addAll(city.getTerritory());
        }

        for (Unit unit : civilization.getUnits()) {
            visibleMap.addAll(mainGameMap.getAdjacentTiles(unit.getPosition()));
        }

        ArrayList<AbstractTile> visibleMapArray = new ArrayList<>();
        for (Object o : visibleMap.toArray()) {
            visibleMapArray.add((AbstractTile) o);
        }
        return visibleMapArray;
    }*/

    private void updateNumberOfResources(Civilization civilization) {

        civilization.resetResource();
        for (City city : civilization.getCities())
            for (Tile tile : city.getTerritory())
                if (tile.isUsableResource())
                    civilization.addResource(tile.getResource());


    }

    public void updateCity(City city) {

        Civilization civilization = city.getCivilization();

        city.updateFood(civilization.isUnHappy());
        city.updateProduction();
        city.updateCitizen();
        boolean unitISReady = city.updateProductUnit();
        if (unitISReady)
            civilization.getUnits().add(city.getUnitUnderProduct());
    }

    public void updateTransparentTiles(Civilization civilization) {
        civilization.getPersonalMap().removeTransparentTiles();
        civilization.getUnits().removeIf(new Predicate<Unit>() {
            @Override
            public boolean test(Unit unit) {
                return unit == null;
            }
        });
        for (Unit unit : civilization.getUnits()) {
            if (unit == null) continue;
            ArrayList<AbstractTile> visibleTiles = new ArrayList<>();
            visibleTiles.addAll(CivilizationController.getInstance().getVisibleTiles(unit));
            civilization.getPersonalMap().addTransparentTiles(visibleTiles);
        }
        civilization.getPersonalMap().addTransparentTiles(civilization.getTerritory());
        for (AbstractTile territoryTile : civilization.getTerritory()) {
            for (AbstractTile adjacentTile : territoryTile.getAdjacentTiles()) {
                ArrayList<AbstractTile> tiles = new ArrayList<>();
                tiles.add(adjacentTile);
                if (adjacentTile != null) civilization.getPersonalMap().addTransparentTiles(tiles);
            }
        }
    }

    public void updatePersonalMap(Civilization civilization, GameMap mainMap) {
        for (int i = 0; i < civilization.getPersonalMap().getMapHeight(); i++) {
            for (int j = 0; j < civilization.getPersonalMap().getMapWidth(); j++) {
                Tile tile = mainMap.getTileByXY(i, j);
                if (tile == null) civilization.getPersonalMap().setTileByXY(i, j, null);
                else if (civilization.getPersonalMap().isTransparent(civilization.getPersonalMap().getTileByXY(i, j))) {
                    civilization.getPersonalMap().setTileByXY(i, j, new VisibleTile(tile, false));
                }
            }
        }
    }

}