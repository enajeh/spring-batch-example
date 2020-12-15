package com.exemple.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.exemple.batch.dto.Acteur;
import com.exemple.batch.entities.ActeurEntity;

public class ActeurProcessor implements ItemProcessor<Acteur, ActeurEntity> {
	
    private static final Logger log = LoggerFactory.getLogger(ActeurProcessor.class);
    
    @Override
    public ActeurEntity process(final Acteur acteur) throws Exception {
    	
    	String id = acteur.getId();
    	String film = acteur.getFilm();
    	int age = acteur.getAge();
    	String[] nomComplet = acteur.getNomComplet().split(" ");
    	

        final ActeurEntity transformedActeurEntity = new ActeurEntity(id, nomComplet[0],nomComplet[1], age, film);

        log.info("Converting (" + acteur + ") into (" + transformedActeurEntity + ")");

        return transformedActeurEntity;
    }

}
