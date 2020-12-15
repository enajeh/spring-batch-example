package com.exemple.batch.listener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

import com.exemple.batch.dto.Acteur;
import com.exemple.batch.utils.CSVUtils;

public class CustomItemReaderListener implements ItemReadListener<Acteur> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomItemReaderListener.class); 
	
    @Override
    public void beforeRead() {
        // default
    }

    @Override
	public void afterRead(Acteur acteur) {
		try {
			// Apres la lecture de ligne depuis le CSV
			// On valide la ligne si OK passe en BDD
			// Sinon on le trace dans le ficher des ligne non valide
			if(isNotValidActor(acteur)) {
				CSVUtils.traceNoValidActor(acteur);
			}
			
		} catch (Exception e) {
			LOGGER.error("Error lors de l'ecreture de l'acteur: {}", acteur,  e);
		}
	}

    @Override
    public void onReadError(Exception ex) {
    	// default
    }

    private boolean isNotValidActor (Acteur acteur) {
    	boolean  valid = false;
    	
    	if(StringUtils.isBlank(acteur.getId()) || StringUtils.isBlank(acteur.getNomComplet())) valid = true;
    	if(acteur.getAge() <= 0 ) valid = true;
    	return valid;
    }
}