package game.settlements.exceptions;

/**
 * Created by thomasbaldwin on 4/9/17.
 */

public class NoSettlementOnAdjacentHexesException extends BuildConditionsNotMetException {
    public NoSettlementOnAdjacentHexesException(String errorMessage) {
        super(errorMessage);
    }
}