package com.exemple.batch.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import com.exemple.batch.entities.ActeurEntity;

public class CustomItemWriterListener implements ItemWriteListener<ActeurEntity> {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomItemWriterListener.class);


	@Override
	public void beforeWrite(List<? extends ActeurEntity> items) {
	}

	@Override
	public void afterWrite(List<? extends ActeurEntity> items) {
	}

	@Override
	public void onWriteError(Exception exception, List<? extends ActeurEntity> items) {
		//Trace in errors file
		LOGGER.error("Error en ecriture en bdd de {} avec le premier element id: {}", items.size(), items.get(0).getId());
		LOGGER.error("Error en ecriture en bdd.", exception);
	}

}
