package services; 

import java.util.Collection; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.util.Assert; 

import repositories.PositionRepository;

import domain.Position; 
import forms.PositionForm;

@Service 
@Transactional 
public class PositionService { 

	//Managed repository -------------------
	@Autowired
	private PositionRepository positionRepository;


	//Supporting Services ------------------


	//Simple CRUD methods--------------------

	public Position create(){
		return new Position();
	}

	public Collection<Position> findAll(){
		Collection<Position> result;

		result = positionRepository.findAll();

		return result;
	}

	public Position findOne(int positionId){
		Position result;

		result = positionRepository.findOne(positionId);

		return result;
	}

	public void save(Position position){
		Assert.notNull(position);

		positionRepository.save(position);
	}

	public void delete(Position position){
		Assert.isTrue(checkIfNotAssigned(position.getId()),"cannotDelete");
		positionRepository.delete(position);
	}


	//Other Methods--------------------
	
	public Boolean checkIfNotAssigned (final int positionId) {
		return this.positionRepository.checkIfNotAssigned(positionId);
	}
	
	public PositionForm toForm(final int positionId) {
		final PositionForm res = new PositionForm();
		final Position position = this.findOne(positionId);
		res.setName(position.getName());
		res.setNameEs(position.getNameEs());
		res.setPositionId(positionId);
		return res;
	}

	public Position reconstruct(final PositionForm positionForm) {
		final Position res;
		if (positionForm.getPositionId() == 0)
			res = this.create();
		else
			res = this.findOne(positionForm.getPositionId());
		res.setName(positionForm.getName());
		res.setNameEs(positionForm.getNameEs());
		return res;
	}
} 
