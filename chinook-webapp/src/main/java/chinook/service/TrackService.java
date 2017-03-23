package chinook.service;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import chinook.entity.Track;

@Stateful
public class TrackService {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	public Track findOneById(int trackId) {
		return entityManager.find(Track.class, trackId);
	}
	
}
