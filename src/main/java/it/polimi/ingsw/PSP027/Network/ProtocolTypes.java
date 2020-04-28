package it.polimi.ingsw.PSP027.Network;

import javax.lang.model.type.TypeMirror;

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
        clt_ChosenGods,
        clt_ChosenGod,
        srv_ChooseGods,
        srv_ChooseGod,
        srv_ChooseFirstPlayer,
        clt_ChosenFirstPlayer,
        srv_ChooseWorkerStartPosition,
        clt_ChosenWorkersFirstPositions,
        srv_ChooseWorker,
        clt_ChosenWorker,
        srv_AskBeforeApplyingGod,
        clt_AnswerApplyOrNotGod,
        srv_CandidateCellsForMove,
        srv_CandidateCellsForOptMove,
        clt_Move,
        clt_MovePassed,
        clt_MoveWorker,
        srv_CandidateCellsForBuild,
        srv_CandidateCellsForOptBuild,
        clt_Build,
        clt_BuildPassed,
        srv_CandidateCellsForOptEnd,
        srv_BoardUpdated,
        srv_Loser,
        srv_Winner,
        clt_EndPassed,
        clt_EndAction
    }
}
