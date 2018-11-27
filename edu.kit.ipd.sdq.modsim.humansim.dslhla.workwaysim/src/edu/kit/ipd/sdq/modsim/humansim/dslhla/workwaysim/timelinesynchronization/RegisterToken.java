package edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.timelinesynchronization;


import de.uka.ipd.sdq.simulation.abstractsimengine.AbstractSimEntityDelegator;
import de.uka.ipd.sdq.simulation.abstractsimengine.AbstractSimEventDelegator;
import edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.component.WorkwayModel;
import edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.entities.Queue;
import edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.entities.Token;
import edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.entities.Position;
import edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.util.Utils;

public class RegisterToken extends SynchroniseToken{

	private Position position;
	private Position destination;
	
	public RegisterToken(AbstractSimEventDelegator returnEvent,
			AbstractSimEntityDelegator entity, 
			double timestep, 
			double returnEventTimestep,
			Position position,
			Position destination) {
		super(returnEvent, entity, SynchronisedActionTypen.RTI_ACTION, timestep, entity.getModel().getSimulationControl().getCurrentSimulationTime(), returnEventTimestep);
		this.position = position;
		this.destination = destination;
	}

	@Override
	public void executeAction() {
		((WorkwayModel)(getEntity().getModel())).registerHumanAtBusStop((Token)getEntity(), (Queue) position, (Queue) destination, this.getTimeStep());
	}

}