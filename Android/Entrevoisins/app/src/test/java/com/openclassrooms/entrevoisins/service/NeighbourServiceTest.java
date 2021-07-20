package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class
NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getFavoriteNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getFavoriteNeighbours();
        assert(neighbours.size() == 0);
    }

    @Test
    public void addFavoriteNeighbourWithSuccess() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        Neighbour neighbour = service.getNeighbours().get(0);
        service.addFavoriteNeighbour(neighbour.getId());
        assert(favoriteNeighbours.size() == 1);
        assert(favoriteNeighbours.contains(neighbour));
    }

    @Test
    public void addFavoriteNeighbourWithFailure() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        long unexistantId = service.getNeighbours().size()+2;
        service.addFavoriteNeighbour(unexistantId);
        assert(favoriteNeighbours.size() == 0);
    }
}
