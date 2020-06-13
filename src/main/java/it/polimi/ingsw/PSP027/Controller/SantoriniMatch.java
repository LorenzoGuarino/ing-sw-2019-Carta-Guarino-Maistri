package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Board;
import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Network.ProtocolTypes;
import it.polimi.ingsw.PSP027.Network.Server.Lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Elisa Maistri
 * @author Lorenzo Guarino
 * @author danielecarta
 *
 * This class manages the game's match. It has the board of the game, the list of players,
 * the turns played in order to save them, the list of the gods and a godsInUse list used by the first
 * player to choose the gods they will play with (from godsList) and from which each player will choose
 * their own god card.
 */

public class SantoriniMatch implements Runnable{

    private Board gameBoard;
    private int requiredPlayers;
    private List<Player> players;
    private UUID matchID;
    private List<GodCard> godCardsList;
    private List<GodCard> godCardsInUse;
    private boolean matchStarted;
    private boolean matchEnded;
    private Lobby owner;
    private Turn turn = null;

    private enum TurnState {
        WaitForBeingReadyToPlayTurns,
        CreateTurn,
        WaitForTurnTerminated
    }

    private TurnState turnState; //enum variable that will change the state of the santorini match


    /**
     * Constructor: it creates a new match, creating a list for the players that will be filled as the players are added,
     * the same for the list of turns, the gods that will be used in the match and the list of all gods, generates the random unique
     * id of the match, creates a board and sets the variable that tells if the match has started to false
     * @param lobby the lobby in which the game is contained
     */

    public SantoriniMatch(Lobby lobby) {
        owner = lobby;
        matchID = UUID.randomUUID();
        requiredPlayers = 2;
        players = new ArrayList<Player>();
        godCardsInUse = new ArrayList<GodCard>();
        gameBoard = new Board();
        matchStarted = false;
        matchEnded = false;
        godCardsList = new ArrayList<GodCard>();
        // when the match is instantiated it must be in the state of the thread where it just waits for the setup
        // (done with a communication question-answer with the client) to be over. Then it will be changed to the
        // state which passes the control over to the turn.
        turnState = TurnState.WaitForBeingReadyToPlayTurns;

        // base gods
        godCardsList.add(new GodCard(GodCard.GodsType.Apollo, GodCard.WhereToApply.Move, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Artemis, GodCard.WhereToApply.ExtraMove, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Athena,  GodCard.WhereToApply.Move, GodCard.ToWhomIsApplied.Opponent));
        godCardsList.add(new GodCard(GodCard.GodsType.Atlas,  GodCard.WhereToApply.Build, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Demeter,  GodCard.WhereToApply.ExtraBuild, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Hephaestus, GodCard.WhereToApply.ExtraBuild, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Minotaur,  GodCard.WhereToApply.Move, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Pan, GodCard.WhereToApply.WinCondition, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Prometheus, GodCard.WhereToApply.Move, GodCard.ToWhomIsApplied.Owner));

        // advanced gods
        godCardsList.add(new GodCard(GodCard.GodsType.Ares, GodCard.WhereToApply.EndTurn, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Hestia, GodCard.WhereToApply.ExtraBuild, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Medusa, GodCard.WhereToApply.EndTurn, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Poseidon, GodCard.WhereToApply.EndTurn, GodCard.ToWhomIsApplied.Owner));
        godCardsList.add(new GodCard(GodCard.GodsType.Zeus, GodCard.WhereToApply.Build, GodCard.ToWhomIsApplied.Owner));
    }

    /**
     * SantoriniMatch Thread. It controls the various states that the turn passes in its life cycle.
     * This thread creates a turn and until it is completed it checks for winning and losing instances of the playing players.
     * When a turn is completed the cycle restarts for the next player and so on. It continues to do so until the end of the match.
     */

    @Override
    public void run() {
        // here goes what SantoriniMatch does, so the controller part
        try {
            while(!matchEnded) {

                if (matchStarted) {

                    switch(turnState)
                    {
                        case WaitForBeingReadyToPlayTurns:
                        {
                            TimeUnit.MILLISECONDS.sleep(200);
                        }
                            break;
                        case CreateTurn:
                        {
                            turn = newTurn();
                            turnState = TurnState.WaitForTurnTerminated;
                            // debugging purposes
                            System.out.println("**************************************************");
                            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
                            System.out.println("Starting turn for player " + turn.getPlayingPlayer().getNickname());
                            // debugging purposes
                        }
                            break;
                        case WaitForTurnTerminated:
                        {
                            if(turn.IsCompleted())
                            {
                                // debugging purposes
                                System.out.println(gameBoard.boardToXMLString());

                                System.out.println("Ended player " + turn.getPlayingPlayer().getNickname() + " turn");
                                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
                                System.out.println("**************************************************");
                                // debugging purposes

                                if(turn.CurrentPlayerHasWon())
                                {
                                    endGame(turn.getPlayingPlayer());
                                }
                                else if(turn.CurrentPlayerHasLost())
                                {
                                    removePlayer(turn.getPlayingPlayer());
                                    turnState = TurnState.CreateTurn;
                                }
                                else {

                                    boolean bDeleted = false;

                                    do{
                                        bDeleted = false;
                                        for(Player player : players) {
                                            if (player.HasLost()) {
                                                removePlayer(player);
                                                bDeleted=true;
                                                break;
                                            }
                                        }
                                    } while(bDeleted);

                                    if(players.size() == 1) {
                                        players.get(0).setHasWon(true);
                                        endGame(players.get(0));
                                    }
                                    else {
                                        turnState = TurnState.CreateTurn;
                                        rotatePlayers();
                                        sendUpdatedBoard(players.get(0).getNickname());
                                    }
                                }
                            }
                            else
                                TimeUnit.MILLISECONDS.sleep(200);
                        }
                    }

                } else {
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // when this point is reached, the thread will die
        // lobby can now destroy this SantoriniMatch object
        owner.removeMatch(this);
    }

    /**
     * Method that creates a new turn for the first player in the list
     * @return the turn created
     */

    public Turn newTurn(){
        return new Turn(this.getFirstPlayer(),this);
    }

    /**
     * Method to get the current turn being played in the match
     * @return the turn
     */

    public Turn getTurn() {
        return turn;
    }

    /**
     * Method that sets a turn as the current turn of the match
     * @param turn turn to set as the match's turn
     */

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    /**
     * Method to use in order to check if there are no players in the match
     * @return true if the match has players, otherwise false
     */

    public boolean hasPlayers() { return !players.isEmpty(); }

    /**
     * Method to get the number of players currently in this match
     * @return the number of players in the match
     */

    public int numberOfPlayers() { return players.size(); }

    /**
     * Method to get the id of this match
     * @return the match's id
     */

    public UUID getMatchId () { return matchID; }

    /**
     * Method that checks if the match has already started
     * @return true if it has started, otherwise false
     */

    public boolean isStarted() { return matchStarted; }

    /**
     * Method to get the first player of the list of players
     * @return the first player
     */

    public Player getFirstPlayer() { return players.get(0); }

    /**
     * Method to get the Board of the game
     * @return the board
     */

    public Board getGameBoard() {
        return gameBoard;
    }

    /**
     * Method that changes the list of players in order to rotate them.
     * EXAMPLE: players A , B and C will be rotated to B, C and A.
     * The players will be rotated with each turn in order to have each time as the first player the one that is playing the turn
     */

    public void rotatePlayers(){

        Player p = players.get(0);
        players.remove(p);
        players.add(p);
    }

    /**
     * Method that sets the list of players
     * @param players players to set as the match's players
     */

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Method to gets the list of players in the match
     * @return the list of players
     */

    public List<Player> getPlayers() { return players; }

    /**
     * Method to get the list of god cards
     * @return the list of god cards
     */

    public List<GodCard> getGodCardsList() { return godCardsList; }

    /**
     * Method to get the number of players that will play this match
     * @return the required players, which can be 2 or 3
     */
    public int GetRequiredNumberOfPlayers()  { return requiredPlayers; }

    /**
     * Method that sets the required number of players as the number of players that will play in the match (chosen by the gamer)
     * @param playersCount number of players that this match will require
     */

    public void SetRequiredNumberOfPlayers(int playersCount) {
        if(!matchStarted)
            requiredPlayers = playersCount;
    }

    /**
     * Method that checks if a player is left alone in the match due to the others losing or leaving and therefore
     * triggers the ending of the match.
     * @param playersInGame current players in the game to check if there's only one left.
     */

    public void checkLoseCondition(List<Player> playersInGame){
        if(playersInGame.size() == 1){
            endGame(playersInGame.get(0));
        }
    }


    /* ********************************************************************************************************************
     *                                 METHODS WITH WHICH THE SEND COMMAND CYCLE BEGINS                                   *
     **********************************************************************************************************************/

    /**
     * Method used to add a player to the match, if it has not started yet
     * @param player is the player that is to be added to the list of players
     */

    public synchronized void addPlayer(Player player) {
        if(matchStarted)
            return;

        players.add(player);

        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_EnteringMatch.toString() + "</id><data><players>";
        for(int i = 0; i < players.size(); i++){

            cmd += "<player>" + players.get(i).getNickname() + "</player>";
        }

        cmd += "</players></data></cmd>";

        System.out.println("SantoriniMatch addPlayer cmd: " + cmd);


        for(int i = 0; i < players.size(); i++){

            players.get(i).SendCommand(cmd);
        }

        if(players.size() == requiredPlayers)
            startGame();
    }

    /**
     * Method that removes the player received and all his attributes from the game.
     * If the match had already started it processes the removing of the player considering that it will loose the game and therefore notifying the other players of this.
     * If the match had not started yet, it proceeds to notify the players that they have to wait again for another player to join before starting the match.
     * @param playerToRemove player to be removed
     */
    public synchronized void removePlayer(Player playerToRemove) {

        if (matchStarted) {

            for (int i = 0; i < playerToRemove.getPlayerWorkers().size(); i++) {
                playerToRemove.getPlayerWorkers().get(i).changePosition(null);
            }

            GodCard cardToRemove = playerToRemove.getPlayerGod();

            for (int j = 1; j < players.size(); j++) {
                Player tempPlayer = players.get(j);
                for (int p = 0; p < tempPlayer.getOpponentsGodCards().size(); p++) {
                    if (tempPlayer.getOpponentsGodCards().get(p).getGodName().equals(cardToRemove.getGodName())) {
                        tempPlayer.getOpponentsGodCards().remove(tempPlayer.getOpponentsGodCards().get(p));
                    }
                }
            }

            playerToRemove.removeOpponentGodCards();

            // notify removed player of having lost game
            String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Loser.toString()  + "</id></cmd>";
            System.out.println("SantoriniMatch removePlayer notify loser players cmd: " + cmd);
            playerToRemove.SendCommand(cmd);

            Player firstPlayer = getFirstPlayer();

            players.remove(playerToRemove);

            // notify remainig players that one player has been removed
            cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_LeftMatch.toString()  + "</id><data><player>" + playerToRemove.getNickname() + "</player></data></cmd>";
            System.out.println("SantoriniMatch removePlayer notify remaining players of player removal cmd: " + cmd);

            for (Player player : players) {

                player.SendCommand(cmd);
            }

            checkLoseCondition(players);
            if(playerToRemove == firstPlayer) {
                turn.setCompleted(true);
            }
        }

        else {

            players.remove(playerToRemove);

            String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_EnteringMatch.toString()  + "</id><data><players>";
            for(int i = 0; i < players.size(); i++){

                cmd += "<player>" + players.get(i).getNickname() + "</player>";
            }

            cmd += "</players></data></cmd>";

            System.out.println("SantoriniMatch removePlayer cmd: " + cmd);

            for (Player player : players) {

                player.SendCommand(cmd);
            }
        }
    }


    /**
     * Method that does the setup of the game. It notifies the players that they have entered the match and
     * the communication with the client regarding the playing of the match actually starts with the server that asks
     * the first player that entered the match to choose #numberofplayers gods among the possible god cards
     */

    public void startGame(){

        // notify players that game has started
        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_EnteredMatch.toString()  + "</id><data><players>";
        for(int i = 0; i < players.size(); i++){

            cmd += "<player>" + players.get(i).getNickname() + "</player>";
        }

        cmd += "</players></data></cmd>";

        System.out.println("SantoriniMatch startGame cmd: " + cmd);

        for(int i = 0; i < players.size(); i++){

            players.get(i).SendCommand(cmd);
        }

        // perform starting game stuffs here: choose gods card at first

        cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_ChooseGods.toString()  + "</id><data><requiredgods>" + Integer.toString(requiredPlayers) + "</requiredgods><gods>";
        for(int i = 0; i < godCardsList.size(); i++){

            cmd += "<god>" + godCardsList.get(i).getGodName() + "</god>";
        }

        cmd += "</gods></data></cmd>";

        players.get(0).SendCommand(cmd);

    }

    /**
     * Method that ends the game if the win conditions are verified
     * @param playerWinner player that has won the game
     */

    public void endGame(Player playerWinner) {
        System.out.println("Player " + playerWinner.getNickname() + " has Won!");

        // send to all players the winner one
        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_Winner.toString()  + "</id><data><player>" + playerWinner.getNickname() + "</player></data></cmd>";

        for(int i = 0; i < players.size(); i++){

            players.get(i).SendCommand(cmd);
        }

        matchEnded = true;
    }

    /**
     * Method that sets the god cards in use with the god cards chosen by the user (2 or 3 cards depending of the required number of players of the match)
     * @param chosengods list of the names of the gods chosen by the user
     */

    public void setGodCardsInUse (List<String> chosengods) {
        for (String chosengod : chosengods) {
            for (GodCard god : godCardsList) {
                if(god.getGodName().equals(chosengod)) {
                    godCardsInUse.add(god);
                    break;
                }
            }
        }

        rotatePlayers();

        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_ChooseGod.toString()  + "</id><data><gods>";
        for(int i = 0; i < godCardsInUse.size(); i++){

            cmd += "<god>" + godCardsInUse.get(i).getGodName() + "</god>";
        }

        cmd += "</gods></data></cmd>";

        players.get(0).SendCommand(cmd);
    }

    /**
     * Method that assigns the chosen god card by the user to its player's god card
     * @param player player that has chosen the god card
     * @param chosengod name of the god that the player has chosen
     */

    public void setGodCardForPlayer (Player player, String chosengod) {

        for(GodCard god : godCardsInUse) {
            if(god.getGodName().equals(chosengod)) {
                godCardsInUse.remove(god);
                player.setPlayerGodCard(god);
                break;
            }
        }

        rotatePlayers();

        if(players.get(0).getPlayerGod() == null) {
            String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_ChooseGod.toString()  + "</id><data><gods>";
            for(int i = 0; i < godCardsInUse.size(); i++){

                cmd += "<god>" + godCardsInUse.get(i).getGodName() + "</god>";
            }

            cmd += "</gods></data></cmd>";
            players.get(0).SendCommand(cmd);
        }
        else {
            // all players have a god card. START HERE TO ASK TO CHOOSE FOR THE FIRST PLAYER
            // NOTE: since we should send the command to the master player, that was the last one
            // choosing god card, actually this user is the last in the list of players
            // due to former rotatePlayers(). so we need to rotate list to put him in front
            rotatePlayers();

            if(players.size()==3)
                rotatePlayers();

            String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_ChooseFirstPlayer.toString()  + "</id><data><players>";
            for(int i = 0; i < players.size(); i++){

                cmd += "<player>" + players.get(i).getNickname() + "</player>";
            }

            cmd += "</players></data></cmd>";
            players.get(0).SendCommand(cmd);

        }
    }

    /**
     * Method that sets the chosen first player by the user as the first player of the game
     * @param chosenplayer name of the player that the player has chosen
     */

    public void setFirstPlayer(String chosenplayer) {

        // rotate players till the first one matches given one
        while(true) {
            if(chosenplayer.equals(players.get(0).getNickname())) {
                break;
            }
            else {
                rotatePlayers();
            }
        }

        // The first player who is going to place the workers (and then start the game) is set as the first player in the list of players.
        // Start here the process of asking to place the workers

        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_ChooseWorkerStartPosition.toString()  + "</id><data>";
        cmd += boardToXMLString();
        cmd += "</data></cmd>";

        players.get(0).SendCommand(cmd);

    }


    /**
     * Method that sets the chosen starting positions of a player and updates the board
     * @param chosenpositions positions chosen where to place the workers
     * @param player player who is placing its workers
     */

    public void setWorkersStartPosition(Player player, List<String> chosenpositions) {

        for(int i = 0; i < 2; i++) {

            int chosencellindex;

            chosencellindex = Integer.parseInt(chosenpositions.get(i));

            player.getPlayerWorkers().get(i).changePosition(gameBoard.getCell(chosencellindex));
        }

        rotatePlayers();

        if(players.get(0).getPlayerWorkers().get(0).getWorkerPosition() == null) {

            String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_ChooseWorkerStartPosition.toString()  + "</id><data>";
            cmd += boardToXMLString();
            cmd += "</data></cmd>";

            players.get(0).SendCommand(cmd);
        }
        else {

            // THE SETUP OF THE GAME IS DONE. THE TURNS CAN NOW BE PLAYED.
            // The thread of SantoriniMatch needs to change the state from WaitForBeingReadyToPlayTurns to CreateTurn
            // and the controller continues from now on passes on to the Turn until someone has won
            matchStarted = true;
            this.turnState = TurnState.CreateTurn;
        }

    }

    /* ******************* METHODS CALLED WHEN RECEIVING DATA FROM THE CLIENT WHEN THE TURN HAS STARTED ************** */

    /**
     * Method that receives the worker from the client and passes it to the turn that will set is as the chosen worker of
     * the turn with the method setChosenWorker.
     * @param worker int representing the id of the cell containing the worker chosen by the user
     */
    public void setChosenWorker(String worker) {
        int chosencellindex;

        chosencellindex = Integer.parseInt(worker);
        Worker tempWorker = getGameBoard().getCell(chosencellindex).getOccupyingWorker();
        Worker tempWorker2;

        if(tempWorker.getWorkerIndex()==0) {
            tempWorker2 = tempWorker.getWorkerOwner().getPlayerWorkers().get(1);
        }
        else{
            tempWorker2 = tempWorker.getWorkerOwner().getPlayerWorkers().get(0);
        }

        if((turn != null) && (tempWorker != null)) {

            tempWorker.ResetWorkerTurnVars();
            turn.setChosenWorker(tempWorker);
            if(tempWorker2!=null){
                tempWorker2.ResetWorkerTurnVars();
            }

        }
    }

    /**
     * Method that receives the chosen cell from the client and passes it to the turn that will set the new position for the worker with the method MoveWorker()
     * @param chosenCell int representing the id of the chosen cell
     */
    public void MoveWorker(String chosenCell){
        int chosenCellIndex;
        chosenCellIndex = Integer.parseInt(chosenCell);

        turn.MoveWorker(chosenCellIndex);
    }

    /**
     * Method called by the client that lets the player skip the Move phase calling the turn's method passMove()
     */
    public void passMove(){

        turn.passMove();
    }

    /**
     * Method that receives the chosen cell from the client and passes it to the turn that will perform
     * the build on the chosen cell with the method doBuild()
     * @param chosenCell int representing the id of the chosen cell
     */
    public void Build(String chosenCell){
        int chosenCellIndex;
        chosenCellIndex = Integer.parseInt(chosenCell);

        turn.doBuild(chosenCellIndex);
    }

    /**
     * Method that receives the chosen cell from the client of the player who has Atlas as god card and passes it
     * to the turn that will build a block or a dome with the method doBuildForAtlas()
     * @param chosenCell int representing the id of the chosen cell
     * @param build_BorD string that states whether the player wants to build a block or a dome
     */
    public void BuildForAtlas(String chosenCell, String build_BorD){
        int chosenCellIndex;
        chosenCellIndex = Integer.parseInt(chosenCell);

        turn.doBuildForAtlas(chosenCellIndex, build_BorD);
    }

    /**
     * Method called by the client that lets the player skip the Build phase calling the turn's method passBuild()
     */
    public void passBuild(){

        turn.passBuild();
    }

    /**
     * Method that receives the chosen cell from the client and passes it to the turn that will perform the designated specific
     * end action allowed by the player's god card with the method endAction()
     * @param chosenCell int representing the id of the chosen cell
     */
    public void endAction(String chosenCell) {
        int chosenCellIndex;
        chosenCellIndex = Integer.parseInt(chosenCell);

        turn.endAction(chosenCellIndex);
    }

    /**
     * Method called by the client that lets the player skip the End phase calling the turn's method passEnd()
     */
    public void passEnd() {
        turn.passEnd();
    }



    /* ************************************************ UTILITY METHODS ************************************************ */

    /**
     * Method that builds the string in xml format that represents the board with the players
     * @return the string to attach in the data node of the command string to send to the client
     */

    public String boardToXMLString() {

        String xmlBoard = gameBoard.boardToXMLString();

        xmlBoard += "<players>";

        for (int i = 0; i < players.size(); i++) {
            xmlBoard += "<player nickname=\"" + players.get(i).getNickname() + "\" god=\"" + players.get(i).getPlayerGod().getGodName() + "\" />";
        }

        xmlBoard += "</players>";

        return xmlBoard;
    }

    /**
     * Method that sends the updated board to each player, useful for those who are not currently playing their turn as it
     * gives them the possibility to watch the progression of the game in between their turn.
     * @param nicknamePlayingPlayer player who is playing its turn, used by the View to display who is currently playing the turn visible by everyone.
     */

    public void sendUpdatedBoard(String nicknamePlayingPlayer) {

        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_BoardUpdated.toString() + "</id><data>";
        cmd += boardToXMLString();
        cmd += "<playingPlayer nickname=\"" + nicknamePlayingPlayer + "\" />";
        cmd += "</data></cmd>";

        for (int i = 1; i < players.size(); i++) {
            players.get(i).SendCommand(cmd);
        }

    }

}
