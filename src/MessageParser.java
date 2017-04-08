import thread.GameCommandMessage;
import tile.Terrain;
import tile.Tile;

import java.util.DoubleSummaryStatistics;

import static tile.Terrain.*;

public class MessageParser {
    private String tournamentPassword = "<tournament_password>";
    private String userName = "<userName>";
    private String userPassword = "<password>";
    private String enterDome = "ENTER THUNDERDOME "+tournamentPassword;
    private String introduction = "I AM "+ userName +" "+ userPassword;
    private String pid = "<pid>";
    private String cid = "<cid>";
    private String response = "WAITING";
    private String rid;
    private String numOfRounds;
    private String opponent;
    private String activeGame;
    private int moveNumber = 0;
    public GameCommandMessage commandMessage;

    public String parseServerInput(String s){
        System.out.println(s);
        String parts[] = s.split(" ");
        if (s.equals("WELCOME TO ANOTHER EDITION OF THUNDERDOME!")){
            //System.out.println(enterDome);
            return enterDome;
        }
        else if (s.equals("TWO SHALL ENTER, ONE SHALL LEAVE")){
            //System.out.println(introduction);
            return introduction;
        }
        else if (parts[0].equals("WAIT") && parts[1].equals("FOR")){
            pid = parts[6];
            //call to subroutine to name player(pid)
            return "WAITING";
        }
        else if (parts[0].equals("NEW") && parts[1].equals("CHALLENGE")){
            cid = parts[2];
            //call to subroutine to name challenge(cid)
        }
        else if (parts[0].equals("BEGIN")){
            rid = parts[2];
            numOfRounds = parts[4];
        }
        else if (parts[0].equals("NEW") && parts[1].equals("MATCH")){
            opponent = parts[parts.length-1];
        }
        else if (parts[0].equals("MAKE")){
            moveNumber = Integer.parseInt(parts[10]);
            activeGame = parts[5];
            Double moveTime = Double.parseDouble(parts[7]);
            String tile = parts[parts.length-1];
            //call to subroutine to make move in activeGame
            sendActionPromptToGame(activeGame, moveTime, tile);
            String move = "GAME " +activeGame+ " MOVE " + moveNumber +
                    " PLACE <tile> AT <x> <y> <z> <orientation> " +
                    " FOUND SETTLEMENT AT <x> <y> <z> ";

            return move;
        }
        else if (parts[0].equals("GAME") && parts[2].equals("MOVE")){

            String observeGame = parts[1];
            if(!observeGame.equals(activeGame)){
                //sendMoveToGame();
            }
            //call subroutine to log a move in
        }
        else if (parts[0].equals("GAME") && parts[2].equals("OVER")){
            String gameToEnd = parts[1];
            //call subroutine to kill gameToEnd
        }
      return response;
    }

    private void sendActionPromptToGame(String gid, Double moveTime, String tile) {
        String types[] = tile.split("\\+");
        Terrain A = toTerrain(types[0]);
        Terrain B = toTerrain(types[1]);
        Tile tileToBePlaced = new Tile(A,B);
        commandMessage = new GameCommandMessage(gid,moveTime,tileToBePlaced);
    }

    private Terrain toTerrain(String type) {
        if(type.equals("JUNGLE"))
            return JUNGLE;
        if(type.equals("LAKE"))
            return LAKE;
        if(type.equals("GRASS"))
            return GRASSLANDS;
        else//(type.equals("ROCK"))
            return ROCKY;
    }
}
