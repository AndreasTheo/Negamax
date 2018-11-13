package bathprj;

import java.util.List;
import bathprj.controller.*;

/*
IConnectN
---------------------------
This Interface provides an abstract representation of a connectN game.
*/
public interface IConnectN {

    public List<Agent> getAgents();

    public IBoard getBoard();

}
