package acceptance.tile;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import game.world.exceptions.AirBelowTileException;
import game.world.exceptions.NoHexAtLocationException;
import game.world.World;
import org.junit.Assert;
import tile.Location;
import tile.Terrain;
import tile.Tile;
import tile.orientation.TileOrientationRelativeToVolcano;

public class IllegallyPlacingATileOnAHigherLayerDueToGapBetweenLayers {
    private World world;
    private Tile tileOne;
    private Tile tileTwo;
    private Tile tileThree;

    @Given("^a board with two tiles or more$")
    public void a_board_with_two_tiles_or_more() throws Throwable {
        world = new World();
        tileOne = new Tile(Terrain.JUNGLE, Terrain.ROCKY);
        tileTwo = new Tile(Terrain.LAKE, Terrain.GRASSLANDS);

        world.placeFirstTile(tileOne, TileOrientationRelativeToVolcano.SOUTHWEST_SOUTHEAST);
        world.insertTileIntoWorld(tileTwo, new Location(1,0,0), TileOrientationRelativeToVolcano.SOUTHEAST_EAST);
    }

    @When("^I attempt to place the tile on the layer higher than the existing tiles$")
    public void i_attempt_to_place_the_tile_on_the_layer_higher_than_the_existing_tiles() throws Throwable {
        tileThree = new Tile(Terrain.LAKE, Terrain.ROCKY);

        try {
            world.insertTileIntoWorld(tileThree, new Location(1,0,1), TileOrientationRelativeToVolcano.EAST_NORTHEAST);
        }
        catch (Exception e) {

        }
    }

    @When("^there is a gap between tiles$")
    public void there_is_a_gap_between_tiles() throws Throwable {
        boolean airGapExists = false;
        try {
            world.insertTileIntoWorld(tileThree, new Location(1,0,1), TileOrientationRelativeToVolcano.EAST_NORTHEAST);
        }
        catch (AirBelowTileException e) {
            airGapExists = true;
        }

        Assert.assertTrue(airGapExists);
    }

    @Then("^the tile should not be placed there$")
    public void the_tile_should_not_be_placed_there() throws Throwable {
        boolean tileWasNotPlaced = false;
        try {
            world.getHexByCoordinate(1,0,1);
        }
        catch (NoHexAtLocationException e) {
            tileWasNotPlaced = true;
        }

        Assert.assertTrue(tileWasNotPlaced);
    }

}
