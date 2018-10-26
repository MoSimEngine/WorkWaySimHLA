package edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.events;

import java.util.Random;

import de.uka.ipd.sdq.simulation.abstractsimengine.AbstractSimEventDelegator;
import de.uka.ipd.sdq.simulation.abstractsimengine.ISimulationModel;
import edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.component.Duration;
import edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.component.WorkwayModel;
import edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.entities.Human;
import edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.entities.Human.HumanState;
import edu.kit.ipd.sdq.modsim.humansim.dslhla.workwaysim.util.Utils;

public class PickUpTimeoutEvent extends AbstractSimEventDelegator<Human>{

	protected PickUpTimeoutEvent(ISimulationModel model, String name) {
		super(model, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void eventRoutine(Human human) {
		// TODO Auto-generated method stub
		WorkwayModel m = (WorkwayModel)this.getModel();
		
		if(!human.isCollected() && (human.getState().equals(HumanState.AT_BUSSTOP_HOME) || human.getState().equals(HumanState.AT_BUSSTOP_WORK))){
			boolean changeToWalking = false;
			
			if(changeToWalking){
				Utils.log(human, "Human has enough! Time to walk!");
				new HumanArrivesAtWorkEvent(this.getModel(), "Human Arrives At Work Walking after waiting at BS").schedule(human, human.WALK_DIRECTLY.toSeconds().value());
				return;
			} else {
				Utils.log(human, "Human waits again");
				PickUpTimeoutEvent e = new PickUpTimeoutEvent(getModel(), getName());
				m.getComponent().synchronisedAdvancedTime(Duration.minutes(20).toSeconds().value(), e, human);
				return;
			}
		} else if (human.isCollected()) {
			Utils.log(human, "Human sits in bus, time for next waiting!");
			DrivingTimeoOutEvent e = new DrivingTimeoOutEvent(getModel(), "Driving Timeout Event");
			m.getComponent().synchronisedAdvancedTime(Duration.minutes(20).toSeconds().value(), e, human);
			return;
		}
		
		
		
	}

}