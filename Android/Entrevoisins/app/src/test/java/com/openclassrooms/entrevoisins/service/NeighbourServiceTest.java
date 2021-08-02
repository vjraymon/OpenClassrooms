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
    public void createNeighbourWithSuccess() {
        Neighbour neighbourToCreate =  new Neighbour(13, "Foo1", "https://i.pravatar.cc/150?u=a042581f4e29026704d", "Foo2",
                "+33 1 23 45 67 89",  "Foo3");
        service.createNeighbour(neighbourToCreate);
        Neighbour neighbourRegistered = service.getNeighbours().get(service.getNeighbours().size()-1);
        assert(service.getNeighbours().contains(neighbourToCreate));
        assert(neighbourToCreate.getId() == neighbourRegistered.getId());
        assert(neighbourToCreate.getName() == neighbourRegistered.getName());
        assert(neighbourToCreate.getAvatarUrl() == neighbourRegistered.getAvatarUrl());
        assert(neighbourToCreate.getAddress() == neighbourRegistered.getAddress());
        assert(neighbourToCreate.getPhoneNumber() == neighbourRegistered.getPhoneNumber());
        assert(neighbourToCreate.getAboutMe() == neighbourRegistered.getAboutMe());
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

    @Test
    public void deleteFavoriteNeighbourWithSuccess() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        Neighbour neighbour = service.getNeighbours().get(0);
        service.addFavoriteNeighbour(neighbour.getId());
        service.deleteFavoriteNeighbour(neighbour.getId());
        assert(favoriteNeighbours.size() == 0);
    }

    @Test
    public void deleteFavoriteNeighbourWithFailure() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        long unexistantId = service.getNeighbours().size()+2;
        service.deleteFavoriteNeighbour(unexistantId);
        assert(favoriteNeighbours.size() == 0);
    }

    @Test
    public void isFavoriteAddedReturnTrue() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        Neighbour neighbour = service.getNeighbours().get(0);
        service.addFavoriteNeighbour(neighbour.getId());
        assert(service.isFavoriteAdded(neighbour.getId()));
    }

    @Test
    public void isFavoriteAddedReturnFalse() {
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();
        Neighbour neighbour = service.getNeighbours().get(0);
        assert(!service.isFavoriteAdded(neighbour.getId()));
    }

}
