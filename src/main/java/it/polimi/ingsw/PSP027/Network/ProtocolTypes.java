package it.polimi.ingsw.PSP027.Network;

public class ProtocolTypes {

    public enum protocolCommand {
        undefined,
        clt_Hello,
        srv_Hello,
        clt_Register,
        srv_Registered,
        clt_Deregister,
        srv_Deregistered,
        srv_ChooseMatchType,
        clt_SearchMatchOfGivenType,
        srv_EnteringMatch,
        srv_EnteredMatch,
        clt_LeaveMatch,
        srv_LeftMatch,
        srv_ChooseGod,
        clt_ChosenGods,
        srv_ChooseGods,
        clt_ChooseGod,
        srv_StartTurn,
        srv_DrawBoard,
        srv_ChooseWorkerStartPosition,
        clt_WorkerStartPositionChosen,
        clt_ChooseWorker,
        srv_CandidateCellsForMove,
        clt_MoveWorker,
        srv_CandidateCellsForBuild,
        clt_Build,
        srv_UseGodPower,
        clt_UseGodPower,
        srv_BoardUpdated,
        srv_Loser,
        srv_Winner
    }
}
