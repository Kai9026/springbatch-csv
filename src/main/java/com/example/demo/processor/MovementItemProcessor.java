package com.example.demo.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import com.example.demo.beans.MovementDTO;

public class MovementItemProcessor implements ItemProcessor<MovementDTO, MovementDTO> {
  private static final Logger log = LoggerFactory.getLogger(MovementItemProcessor.class);

  @Override
  public MovementDTO process(MovementDTO movementDTO) throws Exception {
    final String valueDate = movementDTO.getValueDate();
    final String category = movementDTO.getCategory();
    final String subcategory = movementDTO.getSubcategory();
    final String description = movementDTO.getDescription();
    final String comment = movementDTO.getComment();
    final String image = movementDTO.getImage();
    final Float amount = movementDTO.getAmount();
    final Float balance = movementDTO.getBalance();

    final MovementDTO transformedAnimeDTO =
        new MovementDTO(
            valueDate, category, subcategory, description, comment, image, amount, balance);

    log.info("Converting (" + movementDTO + ") into (" + transformedAnimeDTO + ")");

    return transformedAnimeDTO;
  }
}
