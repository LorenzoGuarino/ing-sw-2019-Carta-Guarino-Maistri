package it.polimi.ingsw.PSP027.Controller;

import it.polimi.ingsw.PSP027.Model.Game.Cell;
import it.polimi.ingsw.PSP027.Model.Game.GodCard;
import it.polimi.ingsw.PSP027.Model.Game.Player;
import it.polimi.ingsw.PSP027.Model.Game.Worker;
import it.polimi.ingsw.PSP027.Model.Gods.*;
import it.polimi.ingsw.PSP027.Network.ProtocolTypes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author danielecarta
 * @author Elisa Maistri
 */

public class Turn {
    private List<Phase> phaseList;
    private Worker chosenWorker;
    private Player playingPlayer;
    private SantoriniMatch santoriniMatch;
    private boolean bCompleted;

    /**
     * Constructor: it is called by santorini match when the setup of the game is done and the turn of the first player can start.
     * It is given the player who is going to play the turn and the santorini match that called it.
     * With the constructor begins question-answer communication with the client that one by one triggers the instantiation of each phase with the data
     * received form the client
     *
     * @param playingPlayer player that plays this turn created by the match
     * @param santoriniMatch match which the turn belongs to
     */

    public Turn(Player playingPlayer, SantoriniMatch santoriniMatch) {
        this.santoriniMatch = santoriniMatch;
        this.playingPlayer = playingPlayer;
        this.phaseList = new ArrayList<>();
        this.bCompleted = false;

        //First communication with the client done by the Turn, asking to choose a worker to play the turn with.
        ChooseWorker();
    }

    /**
     * Method that prepares the command to send to the client when asking to choose the worker to play the turn with and actually sends the command
     */

    public void ChooseWorker()
    {
        String cmd = "<cmd><id>" + ProtocolTypes.protocolCommand.srv_ChooseWorker.toString() + "</id><data>";
        cmd += this.santoriniMatch.boardToXMLString();
        cmd += "</data></cmd>";

        playingPlayer.SendCommand(cmd);
    }

    /* ***************************************************************************************************************
     *                METHODS THAT RECEIVE THE CLIENT RESPONSE AND TRIGGER AN ACTION ON THE TURN                     *
     *                          RESULTING IN ANOTHER COMMUNICATION WITH THE CLIENT                                   *
     * ***************************************************************************************************************/

    /**
     * Method that sets the chosen worker by the user that will play the turn and instantiates the move phase
     *
     * @param chosenWorker worker chosen by the user
     */

    public void setChosenWorker(Worker chosenWorker) {

        this.chosenWorker = chosenWorker;

        if(getPlayingPlayer().getPlayerGod().getToWhomIsApplied() == GodCard.ToWhomIsApplied.Owner)
        {
            // prometheus case
            if(getPlayingPlayer().getPlayerGod().AllowExtraBuildBeforeMove())
            {
                // ask player if wanna build before moving (this will decorate the move subsequent !!!)
                CreateBuildPhase(false);

                // and leave function.
                return;
            }
        }

        CreateMovePhase(true);
    }

    /**
     * Method that updates the board with the new position of the worker
     * It checks if the movement caused the player to win and if so ends the turn
     * It creates the next phase (optional move or build depending on what the player's god allows)
     * @param chosenCellIndex cell where the worker is moving onto
     */
    public void MoveWorker(int chosenCellIndex) {

        Cell cell = santoriniMatch.getGameBoard().getCell(chosenCellIndex);

        if(phaseList.size()>0)
        {
            phaseList.get(phaseList.size()-1).performActionOnCell(cell);

            if(phaseList.get(phaseList.size()-1).PlayerHasWon())
            {
                System.out.println(getPlayingPlayer().getNickname() + " has won !!");
                bCompleted = true;
            }
        }

        if(!bCompleted) {
            if (playingPlayer.getPlayerGod().AllowExtraMove() && cell.getOccupyingWorker().getMoveCounter() == 1) {
                CreateMovePhase(false);
            } else {
                CreateBuildPhase(true);
            }

            santoriniMatch.sendUpdatedBoard(santoriniMatch.getPlayers().get(0).getNickname());
        }
    }

    /**
     * Method that skips the Move phase and therefore creates the next phase (Build)
     */
    public void passMove() {
        CreateBuildPhase(true);
    }

    /**
     * Method that updates the board with the new build done
     * It creates the next phase (optional build or end depending on what the player's god allows)
     * @param chosenCellIndex cell where the worker is building
     */
    public void doBuild(int chosenCellIndex) {

        Cell cell = santoriniMatch.getGameBoard().getCell(chosenCellIndex);

        if(phaseList.size()>0)
        {
            phaseList.get(phaseList.size()-1).performActionOnCell(cell);
        }

        if(playingPlayer.getPlayerGod().AllowExtraBuildAfterMove() && this.chosenWorker.getBuildCounter() == 1) {
            CreateBuildPhase(false);
        }
        else if(getPlayingPlayer().getPlayerGod().AllowExtraBuildBeforeMove() && this.chosenWorker.getMoveCounter() == 0) {
            //Prometheus case
            CreateMovePhase(true);
        }
        else {
            CreateEndPhase(false);
        }

        santoriniMatch.sendUpdatedBoard(santoriniMatch.getPlayers().get(0).getNickname());
    }

    /**
     * Method that updates the board with the new build done by Atlas
     * It creates the next phase (end)
     * @param chosenCellIndex cell where the worker is building
     * @param build_BordD string whose value indicates if the player wants to build a block (B) or a dome (D)
     */
    public void doBuildForAtlas(int chosenCellIndex, String build_BordD) {

        Cell cell = santoriniMatch.getGameBoard().getCell(chosenCellIndex);
        if(build_BordD.equals("D")) {
            this.chosenWorker.setBuildDomeOnNextBuild(true);
        }
        else if(build_BordD.equals("B")){
            this.chosenWorker.setBuildDomeOnNextBuild(false);
        }

        if(phaseList.size()>0)
        {
            phaseList.get(phaseList.size()-1).performActionOnCell(cell);
        }

        CreateEndPhase(false);

        santoriniMatch.sendUpdatedBoard(santoriniMatch.getPlayers().get(0).getNickname());
    }

    /**
     * Method that skips the Build and creates the next phase (End or Move depending on what the god allows)
     */
    public void passBuild() {
        if (getPlayingPlayer().getPlayerGod().AllowExtraBuildBeforeMove()) {
            CreateMovePhase(true); //Prometheus case
        }
        else {
            CreateEndPhase(false);
        }
    }

    /**
     * Method that updates the board with the end action done
     * It ends the turn
     * It creates another end phase if the god power allows it
     * It clears the player's opponent gods, so that they will not influence again the next turn unless
     * they reset themselves as this player's opponent gods
     * @param chosenCellIndex cell where the worker is performing the end action
     */
    public void endAction(int chosenCellIndex) {

        Cell cell = santoriniMatch.getGameBoard().getCell(chosenCellIndex);

        if(phaseList.size()>0)
        {
            phaseList.get(phaseList.size()-1).performActionOnCell(cell);
        }

        if(playingPlayer.getPlayerGod().AllowExtraEnd() && this.chosenWorker.getBuildCounter() <= 3) {
            CreateEndPhase(false);
        }
        else {
            //removing opponentGodsCards
            getPlayingPlayer().removeOpponentGodCards();
            System.out.println("Cleared opponents gods");
            bCompleted = true;
        }

        santoriniMatch.sendUpdatedBoard(santoriniMatch.getPlayers().get(0).getNickname());
    }

    /**
     * Method that skips the end phase.
     * It ends the turn and clears the opponent gods, so that they will not influence again the next turn unless
     * they reset themselves as this player's opponent gods
     */
    public void passEnd() {
        //removing opponentGodsCards
        getPlayingPlayer().removeOpponentGodCards();
        System.out.println("Cleared opponents gods");
        bCompleted = true;
    }


    /* ************************************************* UTILITY METHODS OF THE TURN ************************************************* */

    /**
     * Method to get the player that is playing the turn
     *
     * @return the playing player
     */

    public Player getPlayingPlayer() {
        return playingPlayer;
    }

    /**
     * Method to get the worker chosen by the user at the instantiation of the turn
     *
     * @return the chosen worker
     */

    public Worker getChosenWorker() {
        return chosenWorker;
    }

    /**
     * Method to get the match who created the turn
     *
     * @return the match
     */

    public SantoriniMatch getSantoriniMatch() {
        return santoriniMatch;
    }

    /**
     * Method to get the phase list of the turn
     *
     * @return the phase list
     */

    public List<Phase> getPhaseList() {
        return phaseList;
    }

    /**
     * Method to set the turn's phase list with a phase list
     *
     * @param phaseList phase list to set as this turn's list
     */

    public void setPhaseList(List<Phase> phaseList) {
        this.phaseList = phaseList;
    }

    /**
     * Method to query in order to tell if the turn is completed
     *
     * @return true if it is completed, otherwise false
     */

    public boolean IsCompleted() {

        return bCompleted;
    }

    /**
     * Method that sets the state of the turn to completed if true, or not completed if false
     * @param completed boolean to set for stating whether the turn is completed or not
     */

    public void setCompleted(Boolean completed) {
        this.bCompleted = completed;
    }

    /**
     * Method that checks if the player playing this turn has won
     * @return true if it has won, otherwise false
     */

    public boolean CurrentPlayerHasWon() {

        return playingPlayer.HasWon();
    }

    /**
     * Method that checks if the current player has lost
     * @return true if it has lost, otherwise false
     */

    public boolean CurrentPlayerHasLost(){

        return playingPlayer.HasLost();
    }

    /**
     * Method that creates a move phase, applying the right decorator to it (also the opponent ones, applied to the already decorated
     * phase by the player's own god)
     * @param bMandatory this tells the phase if it's a mandatory phase or an optional one, deciding therefore whether the player
     *                   loses the game when not able to perform the phase
     */

    public void CreateMovePhase(boolean bMandatory)
    {
        // create move phase and apply decorator to it.
        // the decorated resulting phase is the one that is stored on the phase list
        MovePhase phase = new MovePhase();
        phase.Init(this.chosenWorker, this.santoriniMatch.getGameBoard(), bMandatory);

        // start applying player's own god
        Phase playergodphase = applyDecorator(phase, playingPlayer.getPlayerGod().getGodType(), false);

        // and then apply opponent gods to the already decorated phase by the player's own god card
        if(getPlayingPlayer().getOpponentsGodCards().size() > 0)
        {
            Phase opponentgodphase1 = applyDecorator(playergodphase, getPlayingPlayer().getOpponentsGodCards().get(0).getGodType(), true);
            if(getPlayingPlayer().getOpponentsGodCards().size()>1)
            {
                Phase opponentgodphase2 = applyDecorator(opponentgodphase1, getPlayingPlayer().getOpponentsGodCards().get(1).getGodType(), true);
                phaseList.add(opponentgodphase2);
            }
            else
                phaseList.add(opponentgodphase1);
        }
        else
            phaseList.add(playergodphase);

        boolean bCanPerformPhase = phaseList.get(phaseList.size()-1).startPhase();

        //if the player cannot perform the phase and it is a mandatory phase the player loses and the turn is completed
        if(!bCanPerformPhase && bMandatory){
            // player has lost !!!
            getPlayingPlayer().setHasLost(true);
            bCompleted = true;
        }
    }

    /**
     * Method that creates a build phase, applying the right decorator to it (also the opponent ones, applied to the already decorated
     * phase by the player's own god)
     * @param bMandatory this tells the phase if it's a mandatory phase or an optional one, deciding therefore whether the player
     *                   loses the game when not able to perform the phase
     */

    public void CreateBuildPhase(boolean bMandatory)
    {
        // create build phase and apply decorator to it.
        // the decorated resulting phase is the one that is stored on the phase list
        BuildPhase phase = new BuildPhase();
        phase.Init(this.chosenWorker, this.santoriniMatch.getGameBoard(), bMandatory);

        Phase playergodphase = applyDecorator(phase, playingPlayer.getPlayerGod().getGodType(), false);

        // and then apply opponent gods
        if(getPlayingPlayer().getOpponentsGodCards().size()>0)
        {
            Phase opponentgodphase1 = applyDecorator(playergodphase, getPlayingPlayer().getOpponentsGodCards().get(0).getGodType(), true);
            if(getPlayingPlayer().getOpponentsGodCards().size()>1)
            {
                Phase opponentgodphase2 = applyDecorator(opponentgodphase1, getPlayingPlayer().getOpponentsGodCards().get(1).getGodType(), true);
                phaseList.add(opponentgodphase2);
            }
            else
                phaseList.add(opponentgodphase1);
        }
        else
            phaseList.add(playergodphase);

        boolean bCanPerformPhase = phaseList.get(phaseList.size()-1).startPhase(); //actually calls the method startPhase of the player's own decorator

        if(!bCanPerformPhase && bMandatory){
            // player has lost !!!
            getPlayingPlayer().setHasLost(true);
            bCompleted = true;
        }
    }
    /**
     * Method that creates an end phase, applying the right decorator to it (also the opponent ones, applied to the already decorated
     * phase by the player's own god)
     * @param bMandatory this tells the phase if it's a mandatory phase or an optional one, deciding therefore whether the player
     *                   loses the game when not able to perform the phase
     */

    public void CreateEndPhase(boolean bMandatory)
    {
        //create end phase and apply decorator to it.
        // the decorated resulting phase is the one that is stored on the phase list
        EndPhase phase = new EndPhase();
        phase.Init(this.chosenWorker, this.santoriniMatch.getGameBoard(), bMandatory);

        Phase playergodphase = applyDecorator(phase, playingPlayer.getPlayerGod().getGodType(), false);

        // and then apply opponent gods
        if(getPlayingPlayer().getOpponentsGodCards().size()>0)
        {
            Phase opponentgodphase1 = applyDecorator(playergodphase, getPlayingPlayer().getOpponentsGodCards().get(0).getGodType(), true);
            if(getPlayingPlayer().getOpponentsGodCards().size()>1)
            {
                Phase opponentgodphase2 = applyDecorator(opponentgodphase1, getPlayingPlayer().getOpponentsGodCards().get(1).getGodType(), true);
                phaseList.add(opponentgodphase2);
            }
            else
                phaseList.add(opponentgodphase1);
        }
        else
            phaseList.add(playergodphase);

        boolean bCanPerformPhase = phaseList.get(phaseList.size()-1).startPhase(); //actually calls the method startPhase of the player's own decorator

        if(!bCanPerformPhase){
            //player doesn't have a god that acts in the end phase, so its turn can end.
            //removing opponentGodsCards
            getPlayingPlayer().removeOpponentGodCards();
            System.out.println("Cleared opponents gods");

            bCompleted = true;
        }
    }

    /**
     * Method to call when applying a decorator
     * @param phasetodecorate phase to decorate
     * @param godType god with which to decorate the phase
     * @param bActAsOpponentGod boolean that indicates if the decorator is applied by an opponent
     * @return the decorated phase
     */

    public Phase applyDecorator(Phase phasetodecorate, GodCard.GodsType godType, boolean bActAsOpponentGod) {
        if (godType == GodCard.GodsType.Apollo) {
            return new ApolloDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Ares) {
            return new AresDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Artemis) {
            return new ArtemisDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Athena) {
            return new AthenaDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Atlas) {
            return new AtlasDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Demeter) {
            return new DemeterDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Hephaestus) {
            return new HephaestusDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Hestia) {
            return new HestiaDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Medusa) {
            return new MedusaDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Minotaur) {
            return new MinotaurDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Pan) {
            return new PanDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Poseidon) {
            return new PoseidonDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Prometheus) {
            return new PrometheusDecorator(phasetodecorate, bActAsOpponentGod);
        }
        if (godType == GodCard.GodsType.Zeus) {
            return new ZeusDecorator(phasetodecorate, bActAsOpponentGod);
        }

        return phasetodecorate;
    }
}