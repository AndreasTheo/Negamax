package bathprj;

import java.util.List;
import bathprj.controller.*;

/*
IConnect4
---------------------------
This Interface provides an abstract representation of a connect4 game.
*/
public interface IConnect4 {

    public List<Agent> getAgents();

    public IBoard getBoard();

}
