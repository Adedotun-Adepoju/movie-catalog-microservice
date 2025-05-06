package com.example.movie_catalog_service.controller;

import com.example.movie_catalog_service.models.CatalogItem;
import com.example.movie_catalog_service.models.Movie;
import com.example.movie_catalog_service.models.Rating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {
  private final RestTemplate restTemplate;

  public MovieCatalogController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @GetMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

    List<Rating> ratings = Arrays.asList(
            new Rating("12345", 4),
            new Rating("5678", 3)
    );

    return ratings.stream().map(rating -> {
      String url = String.format("http://localhost:8082/movies/%s", rating.getMovieId());
      Movie movie = restTemplate.getForObject(url, Movie.class);
      return new CatalogItem(movie.getName(), "Text", rating.getRating());
    })
            .collect(Collectors.toList());
    // For each movie ID, call movie info service and get details

    // put them all together
  }
}
