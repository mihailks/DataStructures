package renovation.core;

import renovation.models.Laminate;
import renovation.models.Tile;
import renovation.models.WoodType;

import java.util.*;
import java.util.stream.Collectors;

public class RenovationImpl implements Renovation {

    //FIXME: needs more work

    Stack<Tile> tileStack = new Stack<>();
    Stack<Laminate> laminateStack = new Stack<>();

    private double totalTilesArea = 0;

    @Override
    public void deliverTile(Tile tile) {
        double singleTileArea = tile.width * tile.height;
        if (totalTilesArea + singleTileArea > 30) {
            throw new IllegalArgumentException();
        }
        totalTilesArea += singleTileArea;
        tileStack.push(tile);
    }

    @Override
    public void deliverFlooring(Laminate laminate) {
        laminateStack.push(laminate);
    }

    @Override
    public double getDeliveredTileArea() {
        return totalTilesArea;
    }

    @Override
    public boolean isDelivered(Laminate laminate) {
        return laminateStack.contains(laminate);
    }

    @Override
    public void returnTile(Tile tile) {
        if (!tileStack.contains(tile)) {
            throw new IllegalArgumentException();
        }
        tileStack.remove(tile);
        totalTilesArea-= tile.width * tile.height;
    }

    @Override
    public void returnLaminate(Laminate laminate) {
        if (!laminateStack.contains(laminate)) {
            throw new IllegalArgumentException();
        }
        laminateStack.remove(laminate);
    }

    @Override
    public Collection<Laminate> getAllByWoodType(WoodType wood) {
        return laminateStack
                .stream()
                .filter(l -> l.woodType.equals(wood))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Tile> getAllTilesFitting(double width, double height) {
        return tileStack
                .stream()
                .filter(t -> t.width <= width)
                .filter(t -> t.height <= height)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Tile> sortTilesBySize() {
        return tileStack
                .stream()
                .sorted((t1, t2) -> {
                    double first = t1.height * t1.width;
                    double second = t2.height * t2.width;
                    int result = Double.compare(first, second);
                    if (result == 0) {
                        result = Double.compare(t1.depth, t2.depth);
                    }
                    return result;
                }).collect(Collectors.toList());
    }

    @Override
    public Iterator<Laminate> layFlooring() {
        List<Laminate> result = new ArrayList<>();
        while (laminateStack.isEmpty()) {
            result.add(laminateStack.pop());
        }
        return result.iterator();
    }
}
