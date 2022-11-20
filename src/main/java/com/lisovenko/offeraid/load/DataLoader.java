package com.lisovenko.offeraid.load;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lisovenko.offeraid.entities.Area;
import com.lisovenko.offeraid.entities.Category;
import com.lisovenko.offeraid.entities.DTOs.AreaDTO;
import com.lisovenko.offeraid.entities.DTOs.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(value = "dataloader.enabled", havingValue = "true")
@Transactional
@RequiredArgsConstructor
@EnableJpaRepositories
public class DataLoader implements CommandLineRunner {
  private final Map<Integer, Area> areaMap;
  private final Map<Integer, Category> categoryMap;
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public void run(String... args) throws Exception {
    //loadUsers();
    loadAreas();
    loadCategories();
  }

  private void loadUsers() throws IOException {
//    List<UserDTO> users =
//            mapper.readValue(
//                    //@TODO define
//                    ResourceUtils.getFile("classpath:data/.json"),
//                    mapper.getTypeFactory().constructCollectionType(List.class, AreaDTO.class));
  }

  private void loadAreas() throws IOException {
    List<AreaDTO> areas =
        mapper.readValue(
            ResourceUtils.getFile("classpath:data/areas.json"),
            mapper.getTypeFactory().constructCollectionType(List.class, AreaDTO.class));
  }


  private void loadCategories() throws IOException {
    List<CategoryDTO> categories =
        mapper.readValue(
            ResourceUtils.getFile("classpath:data/categories.json"),
            mapper.getTypeFactory().constructCollectionType(List.class, CategoryDTO.class));
  }
}
