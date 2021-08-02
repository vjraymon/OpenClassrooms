package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> favoriteNeighbours = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }
    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        return favoriteNeighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
        favoriteNeighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFavoriteNeighbour(long id) {
        if (!isFavoriteAdded(id)) return;
        for (Neighbour i: neighbours) {
            if (id == i.getId()) favoriteNeighbours.remove(i);
        }
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }
    @Override
    public void addFavoriteNeighbour(long id) {
        if (isFavoriteAdded(id)) return;
        for (Neighbour i: neighbours) {
            if (id == i.getId()) favoriteNeighbours.add(i);
        }
    }
    @Override
    public boolean isFavoriteAdded(long id) {
        for (Neighbour i: favoriteNeighbours) {
            if (id == i.getId()) return true;
        }
        return false;
    }
}
