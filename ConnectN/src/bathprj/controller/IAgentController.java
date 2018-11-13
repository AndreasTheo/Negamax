package bathprj.controller;

import bathprj.IConnectN;

/*
IAgentController
---------------------------
This Interface provides an abstract representation of an agent controller
*/
public interface IAgentController {
    public int getMove(IConnectN connectN);
}
